package org.azhell.nettyLearn.web.controller;

import org.azhell.nettyLearn.model.vo.ResponseJson;
import org.azhell.nettyLearn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;
    
    @RequestMapping(value = {"login", "/"}, method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson login(HttpSession session,
            @RequestParam String username,
            @RequestParam String password) {
        return securityService.login(username, password, session);
    }
    
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson logout(HttpSession session) {
        return securityService.logout(session);
    }
}
