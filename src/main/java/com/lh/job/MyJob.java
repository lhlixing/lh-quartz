package com.lh.job;

import javax.annotation.Resource;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lh.service.MyService;

//标明在一个任务执行完毕之后就存储一次,使用有状态JOB时，状态值保存到jobDetail的jobDataMap中，集群环境下尤其需要设置
@PersistJobDataAfterExecution
// 不允许并发执行同一个jobDetail,必须等待上一次执行完成后，才能继续执行下一次调度
@DisallowConcurrentExecution
public class MyJob extends QuartzJobBean {

	@Resource
	private MyService myService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			//有状态JOB时，可以从jobDataMap中获取和保存值，这些值会被持久化存储到数据库中，保证下一下调度时能获取到
			JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
			Long id = jobDataMap.getLong("id");
			id = myService.test(id);
			jobDataMap.put("id", id);
			Thread.sleep(5000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
