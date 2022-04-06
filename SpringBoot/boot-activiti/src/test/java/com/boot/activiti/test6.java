package com.boot.activiti;

import com.alibaba.fastjson.JSON;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binSin
 * @date 2022/3/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test6 {

    private Logger logger = LoggerFactory.getLogger(test6.class);

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    HistoryService historyService;


    /**
     * 流程变量查看
     */
    @org.junit.Test
    public void showTaskVariable() {
        String instanceId = "8ed0a707-a661-11ec-83b1-2cf05d0fe3e3"; // 任务ID

        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        if (task == null) {
            System.out.println("当前任务为空");
            return;
        }
        Map<String, Object> processVariables = task.getProcessVariables();
        System.out.println(JSON.toJSONString(processVariables));
        Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();
        System.out.println(JSON.toJSONString(taskLocalVariables));
        String days = (String) taskService.getVariable(task.getId(), "days");
        Date date = (Date) taskService.getVariable(task.getId(), "date");
        String reason = (String) taskService.getVariable(task.getId(), "reason");
        String userId = (String) taskService.getVariable(task.getId(), "user2");
        System.out.println("请假天数:  " + days);
        System.out.println("请假理由:  " + reason);
        System.out.println("请假人id:  " + userId);
        System.out.println("请假日期:  " + date.toString());
    }

    /**
     * 开始请假流程
     *
     */
    @org.junit.Test
    public void start() {
        String instanceKey = "myProcess_5";

        logger.info("========================================");
        logger.info("==============开启请假流程===============");
        logger.info("========================================");
        Map<String, Object> map = new HashMap<String, Object>();
        // 在holiday.bpmn中,填写请假单的任务办理人为动态传入的userId,此处模拟一个id
        map.put("user1", "张三");
//        map.put("user2", "李四");
//        map.put("user3", "王五");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(instanceKey, map);
        logger.info("启动流程实例成功:{}", instance);
        logger.info("流程实例ID:{}", instance.getId());
        logger.info("流程定义ID:{}", instance.getProcessDefinitionId());


        String instanceId = instance.getId(); // 任务ID
        String leaveDays = "10"; // 请假天数
        String leaveReason = "回老家结婚"; // 请假原因
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        if (task == null) {
            logger.info("任务ID:{}查询到任务为空！", instanceId);
            return;
        }
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("days", leaveDays);
        map1.put("date", new Date());
        map1.put("reason", leaveReason);
        map1.put("user2","master");

        taskService.complete(task.getId(), map1);

        logger.info("========================================");
        logger.info("执行【员工申请】环节，流程推动到【上级审核】环节");
        logger.info("========================================");
    }
}
