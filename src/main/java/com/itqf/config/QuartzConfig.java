package com.itqf.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

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
 * @Description:
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/3/30
 * @Time: 上午11:05
 */
@Configuration
public class QuartzConfig {

    /**
     * org.quartz.scheduler.instanceName=myQuartzScheduler
     * org.quartz.scheduler.instanceid=AUTO
     * # Configure ThreadPool 线程池属性
     * #线程池的实现类（一般使用SimpleThreadPool即可满足几乎所有用户的需求）
     * org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
     * #指定线程数，至少为1（无默认值）(一般设置为1-100直接的整数合适)
     * org.quartz.threadPool.threadCount=10
     * #设置线程的优先级（最大为java.lang.Thread.MAX_PRIORITY 10，最小为Thread.MIN_PRIORITY 1，默认为5）
     * org.quartz.threadPool.threadPriority=5
     * #保存job和Trigger的状态信息到内存中的类
     * org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
     * org.quartz.jobStore.tablePrefix=QRTZ_
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier(value = "druidDatasource") DataSource dataSource){

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Properties p = new Properties();
        p.setProperty("org.quartz.scheduler.instanceName","MyQuartzScheduler");
        p.setProperty("org.quartz.scheduler.instanceid","AUTO");
        p.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
        p.setProperty("org.quartz.threadPool.threadCount","10");
        p.setProperty("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
        p.setProperty(" org.quartz.jobStore.tablePrefix","QRTZ_");
        schedulerFactoryBean.setQuartzProperties(p);

        schedulerFactoryBean.setAutoStartup(true);

        schedulerFactoryBean.setOverwriteExistingJobs(true);//覆盖已知job
        schedulerFactoryBean.setStartupDelay(5);//

        schedulerFactoryBean.setDataSource(dataSource);

        return  schedulerFactoryBean;

    }

}
