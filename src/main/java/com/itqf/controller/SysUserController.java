package com.itqf.controller;

import com.google.code.kaptcha.Producer;
import com.itqf.dto.SysUserDTO;
import com.itqf.entity.SysUser;
import com.itqf.log.Mylog;
import com.itqf.service.SysUsersService;
import com.itqf.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;

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
 * @Date: 2019/3/25
 * @Time: 下午3:07
 */
@RestController
public class SysUserController {

    @Resource
    private SysUsersService sysUsersService;

    @Resource  //config包下的KaptchaConfig类中定义
    private Producer producer;

    @RequestMapping("/findAll")
    public List<SysUser> findAll(){
        return sysUsersService.findAll();
    }

    @RequestMapping("/sys/login")
    public R  login(@RequestBody SysUserDTO sysUser){
        //服务端生成的验证码
        String code = ShiroUtils.getCaptcha();
        //用户输入的验证码
        String c = sysUser.getCaptcha();
        if (code!=null&&!code.equalsIgnoreCase(c)){
            return R.error("验证码错误");
        }
        //传统登录
       // return sysUsersService.login(sysUser);
        String s = null;
        try{
            //1，得到subject
            Subject subject = SecurityUtils.getSubject();
            String pwd = sysUser.getPassword();
            Md5Hash md5Hash = new Md5Hash(pwd,sysUser.getUsername(),1024);
            pwd = md5Hash.toString();
            //2,把用户名和密码封装成UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(),pwd);

            if (sysUser.isRememberMe()){
                token.setRememberMe(true);
            }
            //3登录
            subject.login(token);//自定义Realm的认证方法

            System.out.println(subject.hasRole("管理员"));
            System.out.println(subject.isPermitted("sys:menu:save"));

            return R.ok();
        }catch(Exception e){
            e.printStackTrace();
            s = e.getMessage();
        }

        return R.error(s);

    }

    /**
     * bootstrapTable
     */
    @Mylog(value = "用户列表",description = "分页显示用户")
    @RequiresPermissions("sys:user:list")
    @RequestMapping("/sys/user/list")
    public ResultData findUserByPage(Pager pager, String search
            , Sorter sorter){
        return  sysUsersService.findByPage(pager,search,sorter);
    }


    /**
     * 验证码
     */
    @RequestMapping("/captcha.jpg")
    public void  captcha(HttpServletResponse response){
        try{
            String  text = producer.createText();//生成的验证码
            System.out.println("验证码:--->"+text);

           // SecurityUtils.getSubject().getSession().setAttribute("code",text);

            ShiroUtils.setAttribute("code",text);

            BufferedImage bufferedImage =  producer.createImage(text);
            OutputStream os = response.getOutputStream();

            //把生成的验证码展示到客户端
            ImageIO.write(bufferedImage,"jpg",os);

        }catch(Exception e){
            e.printStackTrace();
        }


    }
   // @RequiresPermissions("sys:user:select")
   @Mylog(value = "查询用户信息",description = "显示用户信息")
   @RequestMapping("/sys/user/info")
    public  R userInfo(){

       // System.out.println("--->shiro中取出用户信息："+SecurityUtils.getSubject().getPrincipal());

        SysUser user = ShiroUtils.getCurrentUser();
        return  R.ok().put("user",user);

    }


}
