package org.azhell.nettyLearn.dao;

import org.azhell.nettyLearn.model.po.UserInfo;

public interface UserInfoDao {

    void loadUserInfo();
    
    UserInfo getByUsername(String username);
    
    UserInfo getByUserId(String userId);
}
