package org.azhell.nettyLearn.service;

import org.azhell.nettyLearn.model.vo.ResponseJson;

import javax.servlet.http.HttpSession;

public interface SecurityService {

    ResponseJson login(String username, String password, HttpSession session);
    
    ResponseJson logout(HttpSession session);
}
