package com.pay.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.*;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.google.common.collect.Maps;
import com.pay.demo.entity.OrderReq;
import com.pay.demo.pay.alipay.AlipayConstants;
import com.pay.demo.pay.wechatpay.WechatPayConstants;
import com.pay.demo.pay.wechatpay.WechatPayInit;
import com.pay.demo.pay.wechatpay.WechatPaySign;
import com.pay.demo.utils.QRCodeUtils;
import com.pay.demo.utils.Utils;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-03-29 17:04
 */
@RestController
@Slf4j
public class PayController {

    /**
     * 支付成功后的回调页面路径
     */
    @Value("${pay.return_url}")
    private String returnUrl;

    /**************************** 支付宝 ***********************************/


    /**
     * 支付宝当面付扫码接口
     *
     * @return
     */
    @PostMapping("/pay/alipay/preCreate")
    public String preCreate(@RequestBody(required = false) OrderReq order) throws Exception {
        String orderNo = Utils.generateOrderNo();
        AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                .agent("202108BBc1f6ff94a1634c3f82da0bddf56e9X02")
                .preCreate(order.getSubject(), orderNo, order.getTotal().toString());
        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        if (!response.getCode().equals("10000")) {
            return JSON.toJSONString("二维码生成失败,请联系管理员");
        }
        // 这里设置自定义网站url或文字
        String text = response.getQrCode();
        // 二维码图片
        String logoPath = "F:\\image\\zhifubao.png";
        // 保存地址
        String destPath = "F:\\image\\";
        // 调用工具类
        String imgUrl = QRCodeUtils.encode(text, logoPath, destPath, true);
        imgUrl = "/image/" + imgUrl;

        return JSON.toJSONString(imgUrl);
    }


    /**
     * 支付宝支付接口
     *
     * @return
     */
    @PostMapping("/pay/alipay/pay")
    public String alipayPay(@RequestBody(required = false) OrderReq order) throws Exception {
        String orderNo = Utils.generateOrderNo();
        AlipayTradePagePayResponse response = Factory.Payment.Page()
                .agent("202108BBde48649df0dd4538b392766af7ef2B34")
                .pay(order.getSubject(), orderNo, order.getTotal().toString(), returnUrl);
//        AlipayTradePagePayResponse response1 = Factory.Payment.Page()
//                .pay("订单标题", Utils.generateOrderNo(), order.getTotal().toString(), returnUrl);
        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response.body);
    }

    /**
     * 支付宝支付成功的回调
     *
     * @return
     */
    @RequestMapping("/pay/alipay/notify")
    public String alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<>(16);
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("支付宝异步验签回调参数：{}", JSON.toJSONString(params));
        boolean signVerified = Factory.Payment.Common().verifyNotify(params);
        log.info("支付宝异步验签结果：{}", signVerified);
        if (signVerified) {
            return "success";
        }
        return null;
    }


    /**
     * 支付宝支付查询交易
     *
     * @param orderNo 订单号（我们系统生成的）
     * @return
     * @throws Exception
     */
    @PostMapping("/pay/alipay/query")
    public String alipayQuery(@RequestParam(value = "orderNo", required = false, defaultValue = "") String orderNo) throws Exception {
        log.info("支付宝交易查询接口：订单号={}", orderNo);
        if (!"".equals(orderNo)) {
            // 订单号不为空时，提供订单号查询交易记录
            AlipayTradeQueryResponse response = Factory.Payment.Common().query(orderNo);
            log.info("支付宝交易查询回调结果：{}", JSON.toJSONString(response));
            return JSON.toJSONString(response);
        }
        return JSON.toJSONString("订单号错误");
    }

    /**
     * 支付宝支付退款申请，退款金额不能大于支付总金额
     * <p>
     * 这次只实现一次性退款全部金额，不分批退款
     *
     * @param orderNo
     * @param refundAmount
     * @return
     * @throws Exception
     */
    @PostMapping("/pay/alipay/refund")
    public String alipayRefund(@RequestParam(value = "orderNo", required = true) String orderNo,
                               @RequestParam(value = "refundAmount", required = true) String refundAmount) throws Exception {
        log.info("支付宝退款申请接口：订单号={}，退款金额={}", orderNo, refundAmount);
        if (!"".equals(orderNo) && !"".equals(refundAmount)) {
            AlipayTradeRefundResponse response = Factory.Payment.Common().refund(orderNo, refundAmount);
            log.info("支付宝退款申请回调结果：{}", JSON.toJSONString(response));
            return JSON.toJSONString(response);
        }
        return JSON.toJSONString("订单号错误或退款金额错误");
    }


    /**
     * 支付宝支付关闭交易接口
     *
     * @param orderNo
     * @return
     * @throws Exception
     */
    @PostMapping("/pay/alipay/close")
    public String alipayClose(@RequestParam(value = "orderNo", required = false, defaultValue = "") String orderNo) throws Exception {
        log.info("支付宝交易关闭接口：订单号={}", orderNo);
        if (!"".equals(orderNo)) {
            AlipayTradeCloseResponse response = Factory.Payment.Common().close(orderNo);
            log.info("支付宝交易关闭回调结果：{}", JSON.toJSONString(response));
            return JSON.toJSONString(response);
        }
        return JSON.toJSONString("订单号错误");
    }


    /**
     * 支付宝支付交易撤销接口
     * <p>
     * 该接口仅限于 系统支付超时 或者 支付结果未知时可调用
     *
     * @param orderNo
     * @return
     * @throws Exception
     */
    @PostMapping("/pay/alipay/cancel")
    public String alipayCancel(@RequestParam(value = "orderNo", required = false, defaultValue = "") String orderNo) throws Exception {
        log.info("支付宝交易撤销接口：订单号={}", orderNo);
        if (!"".equals(orderNo)) {
            AlipayTradeCancelResponse response = Factory.Payment.Common().cancel(orderNo);
            log.info("支付宝交易撤销回调结果：{}", JSON.toJSONString(response));
            return JSON.toJSONString(response);
        }
        return JSON.toJSONString("订单号错误");
    }

    /**
     * 支付宝退款查询
     *
     * @param orderNo
     * @return
     * @throws Exception
     */
    @PostMapping("/pay/alipay/queryRefund")
    public String alipayQueryRefund(@RequestParam(value = "orderNo", required = false, defaultValue = "") String orderNo) throws Exception {
        log.info("支付宝退款查询参数：订单号={},请求号={}", orderNo, orderNo);
        if (!"".equals(orderNo)) {
            AlipayTradeFastpayRefundQueryResponse response = Factory.Payment.Common()
                    .queryRefund(orderNo, orderNo);
            log.info("支付宝交易退款查询回调结果：{}", JSON.toJSONString(response));
            return JSON.toJSONString(response);
        }
        return JSON.toJSONString("订单号错误");
    }


    /**
     * @param billType 账单类型 ：trade：商户基于支付宝交易收单的业务账单，signcustomer：基于商户支付宝余额收入及支出等资金变动的业务账单
     * @param billTime 账单时间，支持日账单：yyyy-MM-dd 最早支持时间 2016-01-01， 月账单：yyyy-MM 最早支持时间 2016-01
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/pay/alipay/downloadBill", produces = {"application/json;charset=UTF-8"})
    public String alipayDownloadBill(@RequestParam(value = "billType", required = false) String billType,
                                     @RequestParam(value = "billTime", required = false) String billTime) throws Exception {
        if (!billType.equals(AlipayConstants.BILL_TYPE_TRADE)) {
            billType = AlipayConstants.BILL_TYPE_SIGNCUSTOMER;
        }
        log.info("支付宝查询对账单参数：账单类型={},账单时间={}", billType, billTime);
        AlipayDataDataserviceBillDownloadurlQueryResponse response = Factory.Payment.Common().downloadBill(billType, billTime);
        log.info("支付宝查询对账单回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }

/*********************************************** wechatpay ***************************************************************/


    /**
     * 微信支付 统一下单接口
     *
     * @param order
     * @return
     */
    @PostMapping("/pay/wechatpay/pay")
    public String wechatpayPay(@RequestBody(required = false) OrderReq order) {
        Map<String, Object> reqData = new HashMap<>(16);
        reqData.put("appid", WechatPayConstants.appId);
        reqData.put("mchid", WechatPayConstants.mchId);
        reqData.put("description", order.getSubject());
        // 订单号生成
        String orderNo = Utils.generateOrderNo();
        log.info("order:{}",orderNo);
        reqData.put("out_trade_no", orderNo);
        reqData.put("notify_url", WechatPayConstants.notifyUrl);
        Map<String, Object> amountMap = new HashMap<>(16);
        // 微信支付 最小金额单位为： 分  所以 0.01元 -> 1 分转换
        amountMap.put("total", order.getTotal().multiply(new BigDecimal(100)).intValue());
        amountMap.put("currency", "CNY");
        reqData.put("amount", amountMap);
        try {
            StringEntity entity = new StringEntity(JSON.toJSONString(reqData), "utf-8");
            entity.setContentType("application/json");
            HttpPost httpPost = new HttpPost(WechatPayConstants.payNativeUrl);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");

            CloseableHttpResponse response = new WechatPayInit().getHttpClient().execute(httpPost);
            log.info("微信支付统一下单回调结果：{}", JSON.toJSONString(response));

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == WechatPayConstants.SUCCESS_CODE) {
                String result = EntityUtils.toString(response.getEntity());
                log.info("200状态下body: {}", result);
                return result;
            } else if (statusCode == WechatPayConstants.SUCCESS_CODE_NO_BODY) {
                log.info("204状态下无body");
            } else {
                String errorResult = EntityUtils.toString(response.getEntity());
                log.info("其他状态码下body：{}", errorResult);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 微信支付 回调接口
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/pay/wechatpay/notify")
    public Map<String, String> wechatpayNotify(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, String> resultMap = new HashMap<>(2);
        String body = getRequestBody(request);
        log.info("微信支付回调实体：{}", body);
        // 进行签名验证
        log.info("微信支付验签处理");
        boolean verify = verifySign(request, body);
        if (verify) {
            log.info("微信支付验签成功");
            // 将body转成JSONObject类型
            JSONObject jsonBody = JSON.parseObject(body);
            // 获得 resource
            JSONObject resource = jsonBody.getJSONObject("resource");
            // 获得 通知内容
            String original_type = resource.getString("original_type");
            String algorithm = resource.getString("algorithm");
            String ciphertext = resource.getString("ciphertext");
            String associated_data = resource.getString("associated_data");
            String nonce = resource.getString("nonce");
            // APIv3密钥
            AesUtil aesUtil = new AesUtil(WechatPayConstants.apiV3.getBytes());

            try {
                String decryptContent = aesUtil.decryptToString(associated_data.getBytes(), nonce.getBytes(), ciphertext);
                log.info("微信支付回调解密结果：{}", decryptContent);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            resultMap.put("code", "SUCCESS");
            resultMap.put("message", "");
        } else {
            log.error("微信支付验签失败");
            log.info("Help: 请前往官网：https://pay.weixin.qq.com/index.php/core/home/login?return_url=%2F，\"\n"
                    + "          + \"查看API证书是否有效，保证证书序列号与证书的一致性！");
        }
        return resultMap;
    }


    /**
     * 微信支付订单号查询
     *
     * @param orderNo
     * @return
     */
    @PostMapping("/pay/wechatpay/query")
    public String wechatPayQuery(@RequestParam(value = "orderNo", required = false, defaultValue = "") String
                                         orderNo)
            throws IOException {
        log.info("微信支付订单查询订单号：{}", orderNo);
        if (!"".equals(orderNo)) {
            String url = WechatPayConstants.payQueryUrl.replace("ORDER_NO", orderNo);

            log.info("url:{}",url);
            // 修改
            url = url + WechatPayConstants.mchId;
            HttpGet httpGet = new HttpGet(url);
            // 修改
            log.info("url:{}",url);
            httpGet.setHeader("Accept", "application/json");
            log.error("url:{}", url);
            CloseableHttpResponse response = new WechatPayInit().getHttpClient().execute(httpGet);
            log.info("微信支付订单查询回调结果：{}", JSON.toJSONString(response));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == WechatPayConstants.SUCCESS_CODE) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(result);
                // 支付状态
                String tradeState = (String) jsonObject.get("trade_state");
                // 交易类型
                String tradeType = (String) jsonObject.get("trade_type");
                // 交易描述
                String tradeStateDesc = (String) jsonObject.get("trade_state_desc");
                Map<String, Object> map = Maps.newHashMap();
                map.put("tradeState", tradeState);
                map.put("tradeType", tradeType);
                map.put("tradeStateDesc", tradeStateDesc);
                log.info("200状态下body: {}", result);
                return JSON.toJSONString(map);
            } else {
                String errorResult = EntityUtils.toString(response.getEntity());
                log.info("其他状态码下body：{}", errorResult);
            }
        }
        return JSON.toJSONString("订单号错误");
    }


    /**
     * 微信支付交易关闭
     *
     * @param orderNo
     * @return
     * @throws IOException
     */
    @PostMapping("/pay/wechatpay/close")
    public String wechatPayClose(@RequestParam(value = "orderNo", required = false, defaultValue = "") String orderNo)
            throws IOException {
        if (!"".equals(orderNo)) {
            String url = WechatPayConstants.payCloseUrl.replace("ORDER_NO", orderNo);
            HttpPost httpPost = new HttpPost(url);
            Map<String, String> map = new HashMap<>(2);
            map.put("mchid", WechatPayConstants.mchId);
            StringEntity entity = new StringEntity(JSON.toJSONString(map), "utf-8");
            entity.setContentType("application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = new WechatPayInit().getHttpClient().execute(httpPost);
            log.info("微信支付订单关闭回调结果：{}", JSON.toJSONString(response));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == WechatPayConstants.SUCCESS_CODE_NO_BODY) {
                log.info("204状态下无应答内容");
            } else {
                String errorResult = EntityUtils.toString(response.getEntity());
                log.info("其他状态码下body：{}", errorResult);
            }
        }
        return JSON.toJSONString("订单号错误");
    }


    /**
     * 微信支付 退款接口
     *
     * @param orderNo
     * @param refundAmount
     * @return
     * @throws IOException
     */
    @PostMapping("/pay/wechatpay/refund")
    public String wechatPayRefund(@RequestParam(value = "orderNo", required = true) String orderNo,
                                  @RequestParam(value = "refundAmount", required = true) String refundAmount)
            throws IOException {
        if (!"".equals(orderNo) && !"".equals(refundAmount)) {
            Map<String, Object> refundMap = new HashMap<>(16);
            refundMap.put("out_trade_no", orderNo);
            // 退款号
            String refundNo = UUID.randomUUID().toString().replace("-", "");
            refundMap.put("out_refund_no", refundNo);
            refundMap.put("reason", "商家主动发起退款");
            Map<String, Object> amountMap = new HashMap<>(16);
            // 微信支付 最小金额单位为： 分  所以 0.01元 -> 1 分转换
            amountMap.put("total", 1);
            amountMap.put("currency", "CNY");
            amountMap.put("refund", 1);
            refundMap.put("amount", amountMap);

            StringEntity entity = new StringEntity(JSON.toJSONString(refundMap), "utf-8");
            entity.setContentType("application/json");
            HttpPost httpPost = new HttpPost(WechatPayConstants.payRefundUrl);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            CloseableHttpResponse response = new WechatPayInit().getHttpClient().execute(httpPost);
            log.info("微信支付退款回调结果：{}", JSON.toJSONString(response));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == WechatPayConstants.SUCCESS_CODE) {
                String result = EntityUtils.toString(response.getEntity());
                log.info("微信支付退款200状态body：{}", result);
                return result;
            } else {
                String errorResult = EntityUtils.toString(response.getEntity());
                log.info("其他状态码下body：{}", errorResult);
            }
        }
        return JSON.toJSONString("微信支付退款订单号或者退款金额错误");
    }


    /**
     * 微信支付 退款查询
     *
     * @param refundNo
     * @return
     */
    @PostMapping("/pay/wechatpay/queryRefund")
    public String wechatPayQueryRefund(@RequestParam(value = "refundNo", required = false, defaultValue = "") String refundNo)
            throws IOException {
        log.info("微信支付订单查询退款号：{}", refundNo);
        if (!"".equals(refundNo)) {
            String url = WechatPayConstants.payQueryRefundUrl.replace("REFUND_NO", refundNo);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "application/json");
            CloseableHttpResponse response = new WechatPayInit().getHttpClient().execute(httpGet);
            log.info("微信支付退款查询回调结果：{}", JSON.toJSONString(response));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == WechatPayConstants.SUCCESS_CODE) {
                String result = EntityUtils.toString(response.getEntity());
                log.info("200状态下body: {}", result);
                return result;
            } else {
                String errorResult = EntityUtils.toString(response.getEntity());
                log.info("其他状态码下body：{}", errorResult);
            }
        }
        return JSON.toJSONString("退款号错误");
    }


    /**
     * 申请资金账单API
     *
     * @param billDate
     * @param accountType
     * @return
     * @throws IOException
     */
    @PostMapping("/pay/wechatpay/downloadFundBill")
    public void downloadFundBill(String billDate, String accountType, HttpServletResponse servletResponse) throws IOException {
        String url = WechatPayConstants.downloadFundUrl.replace("BILL_DATE", billDate).replace("ACCOUNT_TYPE", accountType);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        CloseableHttpResponse response = new WechatPayInit().getHttpClient().execute(httpGet);
        log.info("微信支付获取资金账单回调结果：{}", JSON.toJSONString(response));
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == WechatPayConstants.SUCCESS_CODE) {
            String result = EntityUtils.toString(response.getEntity());
            log.info("200状态下body: {}", result);
            JSONObject jsonObject = JSON.parseObject(result);
            String downloadUrl = jsonObject.getString("download_url");
            HttpGet dHttpGet = new HttpGet(downloadUrl);
            dHttpGet.setHeader("Accept", "application/json");
            HttpUrl dHttpUrl = HttpUrl.parse(downloadUrl);
            String token = new WechatPaySign().getToken("GET", dHttpUrl, "");
            dHttpGet.setHeader("Authorization", token);
            HttpClient dHttpClient = HttpClients.createDefault();
            HttpResponse downloadResponse = dHttpClient.execute(dHttpGet);
            log.info("文件下载回调：{}", JSON.toJSONString(downloadResponse));
            if (downloadResponse.getStatusLine().getStatusCode() == WechatPayConstants.SUCCESS_CODE) {
                String dResult = EntityUtils.toString(downloadResponse.getEntity());
                log.info("微信支付申请资金账单，获取到数据: {}", dResult);
                servletResponse.setContentType("application/ms-txt.numberformat:@");
                servletResponse.setCharacterEncoding("UTF-8");
                String fileName = "微信支付-资金账单.csv";
                servletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
                servletResponse.getOutputStream().write(dResult.getBytes("UTF-8"));
            } else {
                String dErrorResult = EntityUtils.toString(downloadResponse.getEntity());
                log.info("其他状态码下body：{}", dErrorResult);
            }
        } else {
            String errorResult = EntityUtils.toString(response.getEntity());
            log.info("其他状态码下body：{}", errorResult);
        }
    }


    /**
     * 微信支付，获得请求回调的实体
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getRequestBody(HttpServletRequest request) throws IOException {
        ServletInputStream stream = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
        return stringBuilder.toString();
    }


    /**
     * 微信支付 回调签名验证
     *
     * @param request
     * @param body
     * @return
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    private boolean verifySign(HttpServletRequest request, String body) {
        String serialNo = request.getHeader("Wechatpay-Serial");
        String nonceStr = request.getHeader("Wechatpay-Nonce");
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        String wechatSign = request.getHeader("Wechatpay-Signature");
        String signStr = Stream.of(timestamp, nonceStr, body).collect(Collectors.joining("\n", "", "\n"));

        log.info("微信支付回调头部内容如下：");
        log.info("Wechatpay-Serial: {}", serialNo);
        log.info("Wechatpay-Nonce: {}", nonceStr);
        log.info("Wechatpay-Timestamp: {}", timestamp);
        log.info("Wechatpay-Signature: {}", wechatSign);
        log.info("根据[Wechatpay-Timestamp]、[Wechatpay-Nonce]、[body]构建的待签名字符串: {}", signStr);

        try {
            // 加载微信支付证书
            InputStream certStream = new FileInputStream(WechatPayConstants.platformCertPath);
            X509Certificate x509Certificate = PemUtil.loadCertificate(certStream);
            // SHA256withRSA
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(x509Certificate);
            signature.update(signStr.getBytes());
            // 返回验签结果
            return signature.verify(Base64Utils.decodeFromString(wechatSign));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;

    }


}
