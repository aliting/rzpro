package com.itqf.controller;

import com.itqf.entity.SysMenu;
import com.itqf.log.Mylog;
import com.itqf.service.SysMenuService;
import com.itqf.utils.R;
import com.itqf.utils.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @Date: 2019/3/26
 * @Time: 上午9:09
 */
@RestController
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;


    /**
     * {
     *     total:xxxx,
     *     rows:[{},{}]
     * }
     */
    @Mylog(value = "查询菜单信息",description = "分页查询并且按照名称查询菜单列表")

    @RequestMapping("/sys/menu/list")//?sort=menuId&order=asc&limiset=0
    /**
     * int limit , int offset:
     * String search:条件
     * String sort:排序字段
     * String order:排序方式
     */
    @RequiresPermissions("sys:menu:list")
    public ResultData menuList(int limit , int offset,String search,String sort,String order){

        return  sysMenuService.findByPage(limit,offset,search,sort,order);
    }

    @Mylog(value = "删除菜单",description = "根据菜单编号删除菜单")
    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("/sys/menu/del")
    public R del(@RequestBody List<Long> ids){

        return sysMenuService.del(ids);

    }

    /**
     * 查询目录和菜单
     */
    @Mylog(value = "查询菜单和目录",description = "查询菜单和目录")
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/select")
    public  R selectMenu(){

        //r.menuList
        return sysMenuService.selectMenu();
    }

    @Mylog(value = "新增菜单,目录,按钮",description = "新增菜单,目录,按钮")
    @RequiresPermissions("sys:menu:save")
    @RequestMapping("/sys/menu/save")
    public R saveMenu(@RequestBody SysMenu sysMenu){
        return  sysMenuService.save(sysMenu);
    }
    @Mylog(value = "查询菜单",description = "查询菜单")
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/info/{menuId}")//
    public R findMenu(@PathVariable long menuId){

     return  sysMenuService.findMenu(menuId);
    }
    @Mylog(value = "修改菜单",description = "根据菜单编号修改菜单")
    @RequiresPermissions("sys:menu:update")
    @RequestMapping("/sys/menu/update")
    public R  update(@RequestBody SysMenu sysMenu){

        return sysMenuService.update(sysMenu);
    }


    /**
     * 查询当前用户能够方法的菜单  type：0 目录  1  菜单  2  按钮
     * {
     *   "menuList": [{
     *     "menuId": 1,
     *     "parentId": 0,
     *     "parentName": null,
     *     "name": "系统管理",
     *     "url": null,
     *     "perms": null,
     *     "type": 0,
     *     "icon": "fa fa-cog",
     *     "orderNum": 0,
     *     "open": null,
     *     "list": [{
     *       "menuId": 2,
     *       "parentId": 1,
     *       "parentName": null,
     *       "name": "用户管理",
     *       "url": "sys/user.html",
     *       "perms": null,
     *       "type": 1,
     *       "icon": "fa fa-user",
     *       "orderNum": 1,
     *       "open": null,
     *       "list": null
     *     }],
     *   "code": 0,
     *   "permissions": ["sys:schedule:info", "sys:menu:update", "sys:menu:delete", "sys:config:info", "sys:generator:list", "sys:menu:list", "sys:config:save", "sys:menu:perms", "sys:config:update", "sys:schedule:resume", "sys:user:delete", "sys:config:list", "sys:user:update", "sys:role:list", "sys:menu:info", "sys:menu:select", "sys:schedule:update", "sys:schedule:save", "sys:role:select", "sys:user:list", "sys:menu:save", "sys:role:save", "sys:schedule:log", "sys:role:info", "sys:schedule:delete", "sys:role:update", "sys:schedule:list", "sys:user:info", "sys:generator:code", "sys:schedule:run", "sys:config:delete", "sys:role:delete", "sys:user:save", "sys:schedule:pause", "sys:log:list"]
     * }
     * @return
     */
    @Mylog(value = "查询用户能访问的菜单",description = "根据菜单编号查询用户能访问的菜单等信息")
    @RequestMapping("/sys/menu/user")
    public  R menuUser(){

        return  sysMenuService.findUserMenu();

    }



}
