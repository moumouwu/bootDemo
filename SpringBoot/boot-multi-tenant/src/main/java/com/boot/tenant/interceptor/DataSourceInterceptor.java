//package com.boot.tenant.interceptor;
//
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author binSin
// * @date 2021/12/8
// */
//public class DataSourceInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        try{
//            String tenantHeader = request.getHeader("tenant");
//            if (StringUtils.hasText(tenantHeader)) {
//                DataSourceTenantContextHolder.setCurrentTanent(tenantHeader);
//            } else {
//                DataSourceTenantContextHolder.setDefaultTenant();
//            }
//        }catch (Exception e){
//            DataSourceTenantContextHolder.setDefaultTenant();
//        }
//        return true;
//    }
//}
