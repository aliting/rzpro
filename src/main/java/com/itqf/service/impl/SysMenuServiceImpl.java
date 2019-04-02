package com.itqf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.entity.SysMenu;
import com.itqf.entity.SysMenuExample;
import com.itqf.mapper.SysMenuMapper;
import com.itqf.service.SysMenuService;
import com.itqf.utils.R;
import com.itqf.utils.ResultData;
import com.itqf.utils.ShiroUtils;
import com.itqf.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * @Time: 上午9:19
 */
@Service(value="sysMenuServiceImpl")
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor =RuntimeException.class )//隔离级别  传播行为  rollback-for
public class SysMenuServiceImpl  implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public ResultData findByPage(int limit, int offset) {

        /**
         @param pageNum  页码
         @param pageSize 每页显示数量
         */
        //PageHelper.startPage(int pageNum,int pageSize);

        /**
         *  @param offset  起始记录
         *  @param limit 每页显示数量
         */
        PageHelper.offsetPage(offset,limit);
       List<SysMenu> list =  sysMenuMapper.selectByExample(null);

        PageInfo info = new PageInfo(list);

        long total = info.getTotal();
        List<SysMenu> list1 = info.getList();

        ResultData resultData = new ResultData(total,list1);

        return resultData;
    }

    //@RequiresPermissions("sys:menu:select")
    @Override
    public ResultData findByPage(int limit, int offset, String search) {

        PageHelper.offsetPage(offset,limit);

        SysMenuExample example = null;
       if (search!=null&&!"".equals(search)){
              example= new SysMenuExample();
           SysMenuExample.Criteria criteria = example.createCriteria();
           criteria.andNameLike("%"+search+"%");
       }
        List<SysMenu> list  =  sysMenuMapper.selectByExample(example);


        PageInfo info = new PageInfo(list);

        long total = info.getTotal();
        List<SysMenu> list1 = info.getList();

        ResultData resultData = new ResultData(total,list1);

        return resultData;
    }


    //@RequiresPermissions("sys:menu:delete")
    @Override
    public R del(List<Long> ids) {

        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria();
        for (Long id : ids) {
            if(id<31){
                return R.error("系统菜单,不能删除！请核对");
            }
        }

        //where id menu_in (?,?);
        criteria.andMenuIdIn(ids);

        int i = sysMenuMapper.deleteByExample(example);
        if(i>0){
            return  R.ok();
        }

        return R.error("删除失败");
    }

   // @RequiresPermissions("sys:menu:list")
    @Override
    public ResultData findByPage(int limit, int offset, String search, String sort, String order) {
        PageHelper.offsetPage(offset,limit);

        SysMenuExample example =  example= new SysMenuExample();

        SysMenuExample.Criteria criteria = example.createCriteria();
        if (search!=null&&!"".equals(search)){
            criteria.andNameLike("%"+search+"%");
        }
        //  order by ${orderByClause}   :${orderByClause}
        //sys/menu/list?sort=menuId&order=desc&limit=10&offset=0
        if (sort!=null&&sort.equals("menuId")){
            sort = "menu_id";
        }
        example.setOrderByClause(sort+" "+order);

        List<SysMenu> list  =  sysMenuMapper.selectByExample(example);


        PageInfo info = new PageInfo(list);

        long total = info.getTotal();
        List<SysMenu> list1 = info.getList();

        ResultData resultData = new ResultData(total,list1);

        return resultData;
    }

    /**
     *  //select  * from sys_menu
    where  type!=2;
     * @return
     */
    //@RequiresPermissions("sys:menu:select")
    @Override
    public R selectMenu() {

        List<SysMenu> list = sysMenuMapper.findMenuNotButton();

        //数据库：系统菜单：menu_id 1  parent_id 0
        //      一级目录： menu_id 0  parent_id -1
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0l);
        sysMenu.setParentId(-1l);
        sysMenu.setName("一级目录");
        sysMenu.setOrderNum(0);

        list.add(sysMenu);
        return R.ok().put("menuList",list);
    }

    //@RequiresPermissions("sys:menu:save")
    @Override
    public R save(SysMenu sysMenu) {
        int i = sysMenuMapper.insert(sysMenu);
        return i>0?R.ok():R.error("新增失败");
    }

    @Override
    public R findMenu(long menuId) {
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);

        return R.ok().put("menu",sysMenu);
    }

    @Override
    public R update(SysMenu sysMenu) {
        int i =sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        if (i>0){
            return R.ok();
        }
        return R.error("修改失败");
    }

    @Override
    public List<String> findPermsByUserId(long userId) {
        //null  或者 "sys:user:list,sys:user:info,sys:user:select"
        List<String> list = sysMenuMapper.findPermsByUserId(userId);
        Set set = new HashSet<String>();
        for (String s : list) {
            if (StringUtils.isNotEmpty(s)){
                //"sys:user:list,sys:user:info,sys:user:select"
                //"sys:user:list"
               String ss[] =  s.split(",");
                for (String s1 : ss) {
                    set.add(s1);
                }
            }
        }

        List<String> newList = new ArrayList<>();
        newList.addAll(set);

        return newList;
    }

    /**
     * {menuList:[{
     *     目录信息
     *     list:{
     *         子菜单
     *     }
     * }
     * ,code:0,
     * permissions:[{sys:menu:list},{}]
     * ]}
     * @return
     */
    @Override
    public R findUserMenu() {
        long userId = ShiroUtils.getUserId();
        //目录
        List<SysMenu> dir = sysMenuMapper.findDir(userId);
        for (SysMenu sysMenu : dir) {
            //查询菜单
         List<SysMenu> menuList =   sysMenuMapper.findMenu(sysMenu.getMenuId(),userId);
         sysMenu.setList(menuList);
        }

        List<String> permissions =this. findPermsByUserId(userId);

        return R.ok().put("menuList",dir).put("permissions",permissions);

    }
}
