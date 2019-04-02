package com.itqf.quartz;

import com.alibaba.fastjson.JSON;
import com.itqf.entity.ScheduleJob;
import com.itqf.entity.ScheduleJobLog;
import com.itqf.service.ScheduleJobLogSerice;
import com.itqf.utils.Lg;
import com.itqf.utils.SpringContextUtils;
import com.itqf.utils.StringUtils;
import com.itqf.utils.SysConstant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;
import java.util.Date;

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
 * //                  不见满街漂亮妹，哪个归得程序员？、
 * @Description:
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/3/30
 * @Time: 上午11:19
 */
public class MyJob  implements Job {

   // @Resource
   // private BackUpDBTask backUpDBTask;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
         //System.out.println("helloworld!!!!");

         //备份数据库
//        BackUpDBTask task = new BackUpDBTask();
//        task.backUp();


//        UnLockAccount a  = new UnLockAccount();
//        a.unLock();

        //取出jobDetail封装参数
        ScheduleJobLog log = new ScheduleJobLog();
        long start = System.currentTimeMillis();
        try{

            String json  = (String)context.getJobDetail().getJobDataMap().get(SysConstant.SCHEDULE_DATA_KEY);
            System.out.println(json);
            //{"beanName":"testTask","cronExpression":"* * * * * ?","jobId":23,"methodName":"test","remark":"测试"}
            //{"beanName":"backUpDB","cronExpression":"* * * * * ?","jobId":24,"methodName":"backUp"}
            ScheduleJob scheduleJob = JSON.parseObject(json,ScheduleJob.class);
            String beanName = scheduleJob.getBeanName();
            String methodName = scheduleJob.getMethodName();
            String params = scheduleJob.getParams();//参数
            //在spring容器中，如何根据bean的名称得到这个对象
            //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
            //ac.getBean(beanName);

            Object  object =  SpringContextUtils.getBean(beanName);

            //已知方法名称，object表示的对象 ---> 调用方法
            Class clazz =  object.getClass();
            Method method = null;
            if (StringUtils.isEmpty(params)){//无参方法
                method = clazz.getDeclaredMethod(methodName);
                method.invoke(object);
            }else{
                //带参数
                method = clazz.getDeclaredMethod(methodName,String.class);
                method.invoke(object,params);
            }

            log.setBeanName(beanName);
            log.setMethodName(beanName);
            log.setJobId(scheduleJob.getJobId());
            log.setCreateTime(new Date());
            log.setParams(params);
            log.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());



        }catch(Exception e){
            e.printStackTrace();
            log.setError(e.getMessage());
        }
        long end = System.currentTimeMillis();
        ScheduleJobLogSerice scheduleJobLogSerice = (ScheduleJobLogSerice) SpringContextUtils.getBean("scheduleJobLogServiceImpl");
        log.setTimes(end-start);

        try{
            scheduleJobLogSerice.insertLog(log);
            Lg.log("定时任务日志记录成功！");
        }catch(Exception e){
            Lg.log("定时任务日志记录失败！"+e.getMessage());
        }



    }
}
