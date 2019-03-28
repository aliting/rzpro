package com.itqf.utils;

import com.itqf.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

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
 * @Date: 2019/3/27
 * @Time: 上午11:15
 */
public class ShiroUtils {

    // SecurityUtils.getSubject().getSession().setAttribute("code",text);

    public static void logout() {
          SecurityUtils.getSubject().logout();
    }

    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    public static void setAttribute(String k,String v){
         getSession().setAttribute(k,v);
    }
    public static Object getAttribute(String k){
       return  getSession().getAttribute(k);
    }

    public static String getCaptcha(){
        return getAttribute("code")+"";
    }


    public static SysUser  getCurrentUser(){
        return  (SysUser) SecurityUtils.getSubject().getPrincipal();
    }


    public static long  getUserId(){
        return  getCurrentUser().getUserId();
    }

}
