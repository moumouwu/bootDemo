package org.azhell.nettyLearn.service;

import org.azhell.nettyLearn.model.vo.ResponseJson;

public interface UserInfoService {

    ResponseJson getByUserId(String userId);
}
