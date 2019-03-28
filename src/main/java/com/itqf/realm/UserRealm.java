package com.itqf.realm;

import com.itqf.entity.SysUser;
import com.itqf.service.SysMenuService;
import com.itqf.service.SysRoleService;
import com.itqf.service.SysUsersService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
 * @Date: 2019/3/27
 * @Time: 上午10:01
 */
@Component(value = "userRealm")  // 衍生出三个注解： @Repository @Service  @Controller
public class UserRealm extends AuthorizingRealm {

    //注入service
    @Resource
    private SysUsersService sysUsersService;
    @Resource
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("----->授权");
        //得到当前登录的用户
       SysUser user = (SysUser) principals.getPrimaryPrincipal();
        //根据当前用户id查询角色名
        List<String> roles = sysRoleService.findRolesByUserId(user.getUserId());
        //再查询权限
        List<String> perms = sysMenuService.findPermsByUserId(user.getUserId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRoles(roles);
        info.addStringPermissions(perms);
        System.out.println("----->授权over!");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("----->认证");
        UsernamePasswordToken u = (UsernamePasswordToken) token;
        String uname = u.getUsername();
        String pass = new String(u.getPassword());

        //调用service层方法
        SysUser user = sysUsersService.findByUname(uname);
        if (user==null){
            throw new UnknownAccountException("账号未知！");
        }
        if (!user.getPassword().equals(pass)){
            throw new IncorrectCredentialsException("密码错误！");
        }
        if (user.getStatus()==0){
            throw new LockedAccountException("账号被冻结！");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,pass,this.getName());

        return info;
    }
}
