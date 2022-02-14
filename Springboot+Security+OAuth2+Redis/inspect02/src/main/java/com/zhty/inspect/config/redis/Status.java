package com.zhty.inspect.config.redis;

import java.util.concurrent.TimeUnit;

public abstract class Status {

  public static enum ExpireEnum{

    UNREAD_MSG(30L, TimeUnit.DAYS);

    private Long time;

    private TimeUnit timeUnit;

    ExpireEnum(Long time,TimeUnit timeUnit){
      this.time = time;
      this.timeUnit = timeUnit;
    }

    public Long getTime() {
      return time;
    }

    public TimeUnit getTimeUnit() {
      return timeUnit;
    }
  }
}
