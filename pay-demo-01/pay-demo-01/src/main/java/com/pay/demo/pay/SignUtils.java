package com.pay.demo.pay;


import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/**
 * @author binSin
 * @date 2021/6/21 10:14
 */
@Slf4j
public class SignUtils {


    public String getToken(String appId, String nonceStr,long timestamp,String body) throws Exception {
        String message = buildMessage(appId,timestamp, nonceStr, body);

        String signature = sign(message.getBytes("utf-8"));
        log.info("signature:{}", signature);
        return signature;
    }

    public String sign(byte[] message) throws Exception {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(getPrivateKey("C:\\wechatpay\\apiclient_key.pem"));
        sign.update(message);

        return Base64.getEncoder().encodeToString(sign.sign());
    }

    public String buildMessage(String appId, long timestamp, String nonceStr, String body) {
        return appId + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * 获取私钥。
     *
     * @param filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public PrivateKey getPrivateKey(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new SecureRandom();
    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}
