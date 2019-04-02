package com.itqf.quartz.task;

import com.itqf.entity.SysUser;
import com.itqf.service.SysUsersService;
import com.itqf.utils.Lg;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
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
 * @Date: 2019/4/1
 * @Time: 上午9:10
 */
@Component(value = "unLockAccount")//spring容器中
public class UnLockAccount {

    @Resource
    private SysUsersService sysUsersService;

    public void unLock(){//status =   1  正常  0 冻结
       // System.out.println("解封账户！");
        Lg.log("解封账户开始");
        List<SysUser> list =  sysUsersService.findLockAccount();
        for (SysUser user : list) {
            Date date = user.getLockdate();
            Date now = new Date();
            long time = now.getTime() - date.getTime();
            long day = time/(1000*60*60*24);
            if (day>=3){
            //时间到了   解封账户
                Lg.log("准备解封账户！");
                SysUser sysUser = new SysUser();
                sysUser.setUserId(user.getUserId());
                sysUser.setStatus((byte)1);
                sysUsersService.unLockAccount(sysUser);
                Lg.log("解封账户成功");
            }else{
            //时间没到
                Lg.log("-->未到解封时间");
            }

        }
    }

}
