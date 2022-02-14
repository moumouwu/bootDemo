package com.zhty.inspect.entity.auth;

import com.zhty.inspect.utils.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-14 15:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserinfoVo {

  private Long id;
  private String username;
  private String avatar;
  private String gender;
  private String uniqueId;
  private String nickname;
  private String locked;
  private String expired;
  private String credExpired;
  private String enabled;
  private String createTime;
  private String updateTime;

  public static UserinfoVo buildUserinfo(AuthUserDetails user){
    return
        user==null?
            invalid(-1L):
        UserinfoVo.builder().id(user.getId())
        .username(user.getUsername())
        .avatar(user.getAvatar()==null?"":user.getAvatar())
        .gender(genderFormat(user.getGender()))
        .uniqueId(user.getUniqueId())
        .nickname(user.getNickname())
        .locked(user.getLocked()==0?"未锁定":"已锁定")
        .expired(user.getExpired()==0?"未过期":"已过期")
        .credExpired(user.getCredExpired()==0?"未过期":"已过期")
        .enabled(user.getEnabled()==0?"未禁用":"已禁用")
        .createTime(Util.getDateToString(user.getCreateTime(), "yyyy-MM-dd HH:mm:ss"))
        .updateTime(Util.getDateToString(user.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"))
        .build();
  }

  private static UserinfoVo invalid(Long id){
    return new UserinfoVo(id, null, null, null,null,null,null,null,null,null,null,null);
  }

  private static String genderFormat(Integer i){
    String result = "";
    switch (i){
      case 1:
        result = "男";
        break;
      case 2:
        result = "女";
        break;
      default:
        result = "未知";
        break;
    }
    return result;
  }
}
