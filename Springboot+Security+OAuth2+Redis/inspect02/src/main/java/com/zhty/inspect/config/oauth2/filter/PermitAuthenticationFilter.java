package com.zhty.inspect.config.oauth2.filter;

import com.zhty.inspect.config.oauth2.ResourceServerConfig;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 该拦截器先于OAuth2AuthenticationProcessingFilter
 * 如果请求路径在白名单中，则去除默认的Authorization Bearer xx等token，直接通过
 * @author Qin
 * @version 1.0
 * @date 2020-12-10 9:55
 */
@Component("permitAuthenticationFilter")
@Slf4j
public class PermitAuthenticationFilter extends OncePerRequestFilter {

  /**
   * 默认token校验请求头携带的header名
   */
  private final static String DEFAULT_AUTH_HEADER = "Authorization";

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String uri = request.getRequestURI();
    if (checkWhiteList(uri)) {
      request = new HttpServletRequestWrapper(request) {
        private Set<String> headerNameSet;

        @Override
        public Enumeration<String> getHeaderNames() {
          if (headerNameSet == null) {
            // first time this method is called, cache the wrapped request's header names:
            headerNameSet = new HashSet<>();
            Enumeration<String> wrappedHeaderNames = super.getHeaderNames();
            while (wrappedHeaderNames.hasMoreElements()) {
              String headerName = wrappedHeaderNames.nextElement();
              if (!DEFAULT_AUTH_HEADER.equalsIgnoreCase(headerName)) {
                headerNameSet.add(headerName);
              }
            }
          }
          return Collections.enumeration(headerNameSet);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
          if (DEFAULT_AUTH_HEADER.equalsIgnoreCase(name)) {
            return Collections.<String>emptyEnumeration();
          }
          return super.getHeaders(name);
        }

        @Override
        public String getHeader(String name) {
          if (DEFAULT_AUTH_HEADER.equalsIgnoreCase(name)) {
            return null;
          }
          return super.getHeader(name);
        }
      };
    }
    chain.doFilter(request, response);
  }

  /**
   * 判断白名单
   * @param uri
   * @return
   */
  private static Boolean checkWhiteList(String uri){
    String[] whitelist = ResourceServerConfig.WHITELIST;
    for (String source: whitelist) {
      if (source.contains("/**")) {
        String formatSource = source.contains("/**")?source.replace("/**",""):source;
        if (uri.lastIndexOf(formatSource) == 0) {
          return true;
        }
      } else {
        if (uri.equals(source)){
          return true;
        }
      }
    }
    return false;
  }
}
