package com.boot.activiti;

import com.alibaba.fastjson.JSON;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
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
import java.util.List;
import java.util.Map;

/**
 * @author binSin
 * @date 2021/11/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test5 {

    private Logger logger = LoggerFactory.getLogger(Test5.class);

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
     * 历史活动实例查询
     */
    @org.junit.Test
    public void queryHistoryTask() {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery() // 创建历史活动实例查询
//                .processInstanceId("b188b6fb-5672-11ec-981d-2cf05d0fe3e3") // 执行流程实例id
                .orderByTaskCreateTime()
                .asc()
                .list();

        for (HistoricTaskInstance hai : list) {
            System.out.println("活动ID:" + hai.getId());
            System.out.println(hai.getProcessVariables());
            System.out.println(hai.getTaskLocalVariables());
            System.out.println(hai.getTaskLocalVariables());
            List<HistoricVariableInstance> varInstanceList = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(hai.getId()).list();
            System.out.println(varInstanceList);
            System.out.println("流程实例ID:" + hai.getProcessInstanceId());
            System.out.println("活动名称：" + hai.getName());
            System.out.println("办理人：" + hai.getAssignee());
            System.out.println("开始时间：" + hai.getStartTime());
            System.out.println("结束时间：" + hai.getEndTime());
        }
    }

    /**
     * 查询用户的任务列表
     */
    @org.junit.Test
    public void taskQuery() {
        // 根据流程定义的key,负责人assignee来实现当前用户的任务列表查询
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myProcess_5")
//                .taskAssignee("zhangsan10001")
//                .taskCandidateOrAssigned("department")
//                .active()
                .list();
        System.out.println("*****************************");
        System.out.println(list);
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("getOwner:" + task.getOwner());
                System.out.println("getCategory:" + task.getCategory());
                System.out.println("getDescription:" + task.getDescription());
                System.out.println("getFormKey:" + task.getFormKey());
                Map<String, Object> map = task.getProcessVariables();
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    System.out.println("key:" + m.getKey() + " value:" + m.getValue());
                }
                for (Map.Entry<String, Object> m : task.getTaskLocalVariables().entrySet()) {
                    System.out.println("key:" + m.getKey() + " value:" + m.getValue());
                }

            }
        }
    }

    /**
     * 上级审批
     */
    @org.junit.Test
    public void departmentAudit() {
        String instanceId = "55be78f9-566e-11ec-8bff-2cf05d0fe3e3"; // 任务ID
        String departmentalOpinion = "早去早回";
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
//        task.setAssignee("张三");
        System.out.println(task.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentalOpinion", departmentalOpinion);
        taskService.complete(task.getId(), map);
        logger.info("添加审批意见,请假流程结束");
    }

    /**
     * 流程变量查看
     */
    @org.junit.Test
    public void showTaskVariable() {
        String instanceId = "41b51686-5672-11ec-9431-2cf05d0fe3e3"; // 任务ID

        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        if (task == null) {
            System.out.println("当前任务为空");
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
     * 填写请假单
     */
    @org.junit.Test
    public void employeeApply() {
        String instanceId = "3b6168b9-5673-11ec-902a-2cf05d0fe3e3"; // 任务ID
        String leaveDays = "3"; // 请假天数
        String leaveReason = "回老家结婚"; // 请假原因
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        if (task == null) {
            logger.info("任务ID:{}查询到任务为空！", instanceId);
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("days", leaveDays);
        map.put("date", new Date());
        map.put("reason", leaveReason);
//        map.put("user2","master");
//
//        taskService.setAssignee(task.getId(), "zhangsan");
        taskService.complete(task.getId(), map);

//        Task task1 = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
//        taskService.setAssignee(task1.getId(), "lisibanli");
        logger.info("执行【员工申请】环节，流程推动到【上级审核】环节");
    }


    /**
     * 开始请假流程
     */
    @org.junit.Test
    public void start() {
        String instanceKey = "myProcess_5";
        logger.info("开启请假流程...");
        Map<String, Object> map = new HashMap<String, Object>();
        // 在holiday.bpmn中,填写请假单的任务办理人为动态传入的userId,此处模拟一个id
        map.put("user1", "zhangsan");
        map.put("user2", "lisi");
        map.put("user3", "wangwu");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(instanceKey, map);
        logger.info("启动流程实例成功:{}", instance);
        logger.info("流程实例ID:{}", instance.getId());
        logger.info("流程定义ID:{}", instance.getProcessDefinitionId());
    }

}
