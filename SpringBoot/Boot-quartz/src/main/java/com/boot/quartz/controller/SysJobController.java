package com.boot.quartz.controller;

import com.boot.quartz.domain.AjaxResult;
import com.boot.quartz.domain.SysJob;
import com.boot.quartz.exception.TaskException;
import com.boot.quartz.service.ISysJobService;
import com.boot.quartz.util.CronUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 调度任务信息操作处理
 *
 * @author binSin
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController {

    @Autowired
    private ISysJobService jobService;

    /**
     * 查询定时任务列表
     */
//    @GetMapping("/list")
//    public TableDataInfo list(SysJob sysJob)
//    {
//        List<SysJob> list = jobService.selectJobList(sysJob);
//        return getDataTable(list);
//    }


    /**
     * 获取定时任务详细信息
     */
    @GetMapping(value = "/{jobId}")
    public AjaxResult getInfo(@PathVariable("jobId") Long jobId)
    {
        return AjaxResult.success(jobService.selectJobById(jobId));
    }



    /**
     * 修改定时任务
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SysJob sysJob) throws SchedulerException, TaskException
    {
        if (!CronUtils.isValid(sysJob.getCronExpression()))
        {
            return AjaxResult.error("cron表达式不正确");
        }
        sysJob.setUpdateBy("张三");
        return toAjax(jobService.updateJob(sysJob));
    }

    /**
     * 定时任务状态修改
     */
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException
    {
        SysJob newJob = jobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return toAjax(jobService.changeStatus(newJob));
    }

    /**
     * 定时任务立即执行一次
     */
    @PutMapping("/run")
    public AjaxResult run(@RequestBody SysJob job) throws SchedulerException
    {
        jobService.run(job);
        return AjaxResult.success();
    }

    /**
     * 删除定时任务
     */
    @DeleteMapping("/{jobIds}")
    public AjaxResult remove(@PathVariable Long[] jobIds) throws SchedulerException, TaskException
    {
        jobService.deleteJobByIds(jobIds);
        return AjaxResult.success();
    }
    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
