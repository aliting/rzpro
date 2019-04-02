package com.itqf.utils;

import com.alibaba.fastjson.JSON;
import com.itqf.entity.ScheduleJob;
import com.itqf.exception.RZException;
import com.itqf.quartz.MyJob;
import org.quartz.*;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖镇楼                  BUG辟易
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 *
 * @Description: 工具类
 * 增（创建）、删除、修改、暂停 、恢复
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/3/30
 * @Time: 上午11:15
 */
public class ScheduleUtils {

    public  static void createScheduleTask(Scheduler scheduler, ScheduleJob scheduleJob){//QuartzConfig

        try{
            //1,ScheduleFactoryBean-->Scheduler对象
            //2,JobDetail
            //在mapper.xml文件中 insert语句： useGeneratedKeys="true" keyColumn="job_id" keyProperty="jobId"
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(SysConstant.JOB_KEY_PREFIX+scheduleJob.getJobId()).build();
            //如何向任务类传递参数？
            //usingJobData() 方法一，使用usingJobData()方法传值
            String json = JSON.toJSONString(scheduleJob);
            jobDetail.getJobDataMap().put(SysConstant.SCHEDULE_DATA_KEY,json);

            //3,trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(SysConstant.TRIGGER_KEY_PREFIX+scheduleJob.getJobId()).
                    withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())).build();

            scheduler.scheduleJob(jobDetail,cronTrigger);

        }catch(Exception e){
            e.printStackTrace();
            throw new RZException("创建定时任务失败");
        }


    }

    public  static void deleteScheduleTask(Scheduler scheduler, long jobId){//QuartzConfig

        try{

            JobKey jobKey = JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+jobId);
            //删除定时任务
            scheduler.deleteJob(jobKey);

        }catch(Exception e){
            throw new RZException("删除定时任务失败");
        }


    }

    public  static void updateScheduleTask(Scheduler scheduler,ScheduleJob scheduleJob){
        try{

            //trigger
            TriggerKey triggerKey = TriggerKey.triggerKey(SysConstant.TRIGGER_KEY_PREFIX+scheduleJob.getJobId());

            //获得原来触发器
           CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);

           //修改触发器的表达式
            CronTrigger newCronTrigger =  cronTrigger.getTriggerBuilder().withSchedule
                   (CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())).build();

           //重置触发器
            scheduler.rescheduleJob(triggerKey,newCronTrigger);


        }catch(Exception e){
            throw  new  RZException("修改任务失败，请联系管理员");
        }



    }

    public  static void pause(Scheduler scheduler,long jobId){
            try{
                JobKey jobKey = JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+jobId);
                scheduler.pauseJob(jobKey);
            }catch(Exception e){
                throw  new  RZException("暂停任务失败，请联系管理员");
            }
    }

    public  static void resume(Scheduler scheduler,long jobId){
        try{
            JobKey jobKey = JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+jobId);
            scheduler.resumeJob(jobKey);
        }catch(Exception e){
            throw  new  RZException("暂停任务失败，请联系管理员");
        }
    }

    public  static void runOnce(Scheduler scheduler,long jobId){
        try{
            JobKey jobKey = JobKey.jobKey(SysConstant.JOB_KEY_PREFIX+jobId);
            scheduler.triggerJob(jobKey);

        }catch(Exception e){
            throw  new  RZException("执行任务失败，请联系管理员");
        }
    }




}
