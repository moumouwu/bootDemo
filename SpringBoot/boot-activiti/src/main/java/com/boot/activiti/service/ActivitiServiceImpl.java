package com.boot.activiti.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

/**
 * @author binSin
 * @date 2021/12/1
 */
@Service
public class ActivitiServiceImpl {

    public void test(DelegateExecution execution,String a){
        System.err.println("***************************************");
        System.err.println("***************************************");
        System.err.println("***************************************");
        System.err.println("***************************************");
        System.out.println(execution.getProcessInstanceBusinessKey()+"  "+a);
        System.err.println("***************************************");
        System.err.println("***************************************");
        System.err.println("***************************************");
        System.err.println("***************************************");
    }
}
