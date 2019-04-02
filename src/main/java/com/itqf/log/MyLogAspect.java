package com.itqf.log;

import com.alibaba.fastjson.JSON;
import com.itqf.entity.SysLog;
import com.itqf.service.SysLogService;
import com.itqf.utils.HttpContextUtils;
import com.itqf.utils.IPUtils;
import com.itqf.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
 * //                  不见满街漂亮妹，哪个归得程序员？
 *
 * @Description:通知类（增强类）
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/3/29
 * @Time: 下午2:34
 */
@Aspect
@Component
public class MyLogAspect {

    //注入service
    @Resource
    private SysLogService sysLogService;

    //@Pointcut(value = "execution(* com.itqf.service.impl.*.*(..))")
    //@Pointcut(value = "execution(* com.itqf.controller.*.*(..))")//描述方法的

    @Pointcut(value = "@annotation(com.itqf.log.Mylog)")//描述注解的 被MyLog修饰的方法会被增强
    public void myPointcut(){}
    //<aop:pointcut>

   // @Before()

    @AfterReturning(pointcut = "myPointcut()")
    public void after(JoinPoint joinPoint){//哪一个类能得到跟目标方法有关的信息？

        //System.out.println("后置增强！"+joinPoint.getTarget()+joinPoint.getSignature());
        //private String username;//操作人
        //System.out.println("操作人："+ShiroUtils.getCurrentUser().getUsername());
         //private String operation;//操作
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        Method method =  signature.getMethod();
        //
        Mylog mylog =  method.getAnnotation(Mylog.class);
        //System.out.println(mylog.description());

        //private String method;//调用的方法
        //System.out.println("method:"+method.getName());
         //       private String params;//参数
        //System.out.println("args :"+joinPoint.getArgs());
        //System.out.println("argsJSON :"+JSON.toJSONString(joinPoint.getArgs()));
        //得到客户端ip
        // private String ip;//调用者的ip
        //System.out.println(IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
        //private Date createDate; //调用时间

        String uname = ShiroUtils.getCurrentUser().getUsername();
        String opration = mylog.value();
        String methodName = joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName();
        String params = JSON.toJSONString(joinPoint.getArgs());
        String ip = IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest());

        SysLog sysLog  = new SysLog();
        sysLog.setCreateDate(new Date());
        sysLog.setIp(ip);
        sysLog.setMethod(methodName);
        sysLog.setParams(params);
        sysLog.setUsername(uname);
        sysLog.setOperation(opration);

       int i =  sysLogService.saveLog(sysLog);

        System.out.println(i>0?"保存日志成功":"失败");
    }



}
