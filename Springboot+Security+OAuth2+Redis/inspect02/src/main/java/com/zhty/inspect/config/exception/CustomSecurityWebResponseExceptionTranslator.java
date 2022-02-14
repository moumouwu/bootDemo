package com.zhty.inspect.config.exception;

import com.zhty.inspect.config.security.Values;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-16 9:55
 */
@Component("customSecurityWebResponseExceptionTranslator")
@Slf4j
public class CustomSecurityWebResponseExceptionTranslator implements
    WebResponseExceptionTranslator<OAuth2Exception> {

  private static final String UNSUPPORTED_GRANT_TYPE_EXCEPTION = "UnsupportedGrantTypeException";
  private static final String INVALID_GRANT_EXCEPTION = "InvalidGrantException";

  private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

  public void setThrowableAnalyzer(
      ThrowableAnalyzer throwableAnalyzer) {
    this.throwableAnalyzer = throwableAnalyzer;
  }

  public ThrowableAnalyzer getThrowableAnalyzer() {
    return throwableAnalyzer;
  }

  @Override
  public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
    log.error("exception:{}",e.getClass().getSimpleName());
    Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
    // 异常栈获取 OAuth2Exception 异常
    Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
    log.error("exception2:{}",ase.getClass().getSimpleName());
    // 异常栈中有OAuth2Exception
    if (ase != null) {
      return handleOAuth2Exception((OAuth2Exception) ase);
    }
    return handleOAuth2Exception(new CustomServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
  }

  private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {

    int status = e.getHttpErrorCode();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Cache-Control", "no-store");
    headers.set("Pragma", "no-cache");
    if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
      headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
    }
    String simpleClassName = e.getClass().getSimpleName();
    log.error("exception2:{}",e.getClass().getSimpleName());
    String message = e.getMessage();
    log.error("message:{}",message);
    if (simpleClassName.equals(UNSUPPORTED_GRANT_TYPE_EXCEPTION)){
      message = Values.UNSUPPORTED_GRANT_TYPE;
    }
    if (simpleClassName.equals(INVALID_GRANT_EXCEPTION)) {
      message = Values.INVALID_CRED;
    }
    CustomSecurityOauth2Exception exception = new CustomSecurityOauth2Exception(message,e);
    ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(exception, headers,HttpStatus.valueOf(status));
    return response;
  }


}
