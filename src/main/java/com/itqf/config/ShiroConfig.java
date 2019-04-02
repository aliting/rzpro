package com.itqf.config;

import com.itqf.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

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
 * @Description: 等价于在ssm环境下：applicationContext-shiro.xml
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/3/27
 * @Time: 上午9:45
 */
//@ImportResource(value = "classpath:applicationContext-shiro.xml")
@Configuration  //标记这个类是spring的配置文件
//@SpringBootConfiguration
public class ShiroConfig {

    @Bean(name = "sessionManager") //<bean class="xxx.XXX">
    public SessionManager sessionManager(){

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //l;JSESSIONID=2666f5da-89d7-4186-96aa-c74f1162bd13
        sessionManager.setSessionIdUrlRewritingEnabled(false);//禁止url栏拼接sessionid

        sessionManager.setGlobalSessionTimeout(1000*60*60);//session默认过期时间是半小时
        //定时清除过期会话
        sessionManager.setSessionValidationSchedulerEnabled(true);

        return  sessionManager;
    }

    @Bean(value = "securityManager")//方法的参数相当于传传入spring容器中创建的对象
    public SecurityManager securityManager(UserRealm userRealm,SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(userRealm);

        //缓存管理
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");

        securityManager.setCacheManager(ehCacheManager);

        //cookie
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        Cookie cookie = rememberMeManager.getCookie();
        cookie.setMaxAge(60*60*24*30);

        securityManager.setRememberMeManager(rememberMeManager);

        return  securityManager;
    }
    //shiro注解在spring容器中生效
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();

        advisorAutoProxyCreator.setProxyTargetClass(true);//aop  cglib

        return  advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();

        advisor.setSecurityManager(securityManager);

        return advisor;
    }

    //@Bean("/logoutFilter")
    public LogoutFilter logoutFilter(){
        //shiro退出
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login.html");

        return  logoutFilter;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //设置成功页面
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //没有权限页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.html");

        Map<String,Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("logout",logoutFilter());
        //指定过滤器
        shiroFilterFactoryBean.setFilters(filterMap);

       // shiroFilterFactoryBean.set
        //什么 Map能保证存取顺序?
        //LinkedHashMap
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        /**
         *                 /login = anon
         *                 /logout = logout
         *                 /admin/** = perms["sys:user:*"]
         *                 #多个角色同时满足
         *                 /users/** = roles["admin"]
         *                 /menu/**  = user
         *                 #其他请求都需要认证后才能访问
         *                 /** = authc
         */
        map.put("/public/**","anon");//静态js  css
        //map.put("/json/**","anon");//假数据
        map.put("/demo/**","anon");//练习页面
        map.put("/captcha.jpg","anon");//验证码
        map.put("/del","anon");
        map.put("/sys/login","anon"); // 前端不是是ajax请求
        //map.put("/logout","logout");//退出过滤器 前端不能使用ajax退出
        //map.put("/sys/menu/*","perms[\"sys:menu\"]");
        map.put("/**","user");//选中记住我能访问的资源
        map.put("/**","authc");//登录后才能访问
       // FormAuthenticationFilter


        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);


        return shiroFilterFactoryBean;
    }



}
