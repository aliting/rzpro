package com.itqf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.entity.SysUser;
import com.itqf.entity.SysUserExample;
import com.itqf.mapper.SysUserMapper;
import com.itqf.service.SysUsersService;
import com.itqf.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
 * @Description:
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/3/25
 * @Time: 下午3:03
 */
@Service(value = "sysUserServiceImpl" )
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor =RuntimeException.class )//隔离级别  传播行为  rollback-for
public class SysUserServiceImpl implements SysUsersService {

    @Resource
    private SysUserMapper sysUserMapper;


    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.selectByExample(null);
    }

    @Override
    public R login(SysUser sysUser) {

        //方法一：select * from sys_User where username=#{username}

        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria =  example.createCriteria();
        //方法二 使用example
        criteria.andUsernameEqualTo(sysUser.getUsername());


       List<SysUser> list = sysUserMapper.selectByExample(example);
       if(list==null||list.size()==0){
            return R.error("账号不存在");
       }
       SysUser u = list.get(0);
       if (!u.getPassword().equals(sysUser.getPassword())){
           return R.error("密码错误");
       }
        if (u.getStatus()==0){
            return R.error("账号被冻结");
        }


        return R.ok().put("u",u);
    }

    @Override
    public SysUser findByUname(String name) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria =  example.createCriteria();
        //方法二 使用example
        criteria.andUsernameEqualTo(name);
        List<SysUser> list = sysUserMapper.selectByExample(example);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public ResultData findByPage(Pager pager, String search, Sorter sorter) {

        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysUserExample example = new SysUserExample();

        if (sorter!=null&&StringUtils.isNotEmpty(sorter.getSort())){
            example.setOrderByClause("user_id "+sorter.getOrder());
        }

        SysUserExample.Criteria criteria = example.createCriteria();

        if (search!=null&&!"".equals(search)){
            criteria.andUsernameLike("%"+search+"%");
        }


        List<SysUser> list = sysUserMapper.selectByExample(example);

        PageInfo info = new PageInfo(list);

        ResultData data = new ResultData(info.getTotal(),info.getList());

        return data;
    }

    @Override
    public List<SysUser> findLockAccount() {
        return sysUserMapper.findLockAccount();
    }

    @Override
    public int unLockAccount(SysUser user) {
        return sysUserMapper.unLockAccount(user);
    }


    @Override
    public R findPieData() {
        List<Map<String,Object>> list = sysUserMapper.findPieData();

        List list1 = new ArrayList();
        for (Map<String, Object> map : list) {
            String name = map.get("name")+"";
            list1.add(name);
        }

        return R.ok().put("pieData",list).put("legendData",list1);
    }

    /**
     *
     * xAxis : [
     *         {
     *             type : 'category',
     *             data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
     *         }
     *     ],
     *   series : [
     *         {
     *             name:'蒸发量',
     *             type:'bar',
     *             data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],}
     *
     * {
     *             name:'降水量',
     *             type:'bar',
     *             data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
     *             markPoint : {
     *                 data : [
     *                     {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183},
     *                     {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
     *                 ]
     *
     *
     * @return
     */

    @Override
    public R findBarData() {

        List<Map<String,Object>> list = sysUserMapper.findBarData();

        List xAxisData = new ArrayList();//x坐标
        List series0Data = new ArrayList();//男
        List series1Data = new ArrayList();//女

        for (Map<String, Object> map : list) {
            String deptName = map.get("deptName")+"";
            Object boy = map.get("boy");
            Object girl = map.get("girl");

            xAxisData.add(deptName);
            series0Data.add(boy);
            series1Data.add(girl);
        }



        return R.ok().put("xAxisData",xAxisData).
                put("series0Data",series0Data).put("series1Data",series1Data);
    }

    @Override
    public List<Map<String, Object>> exportExcel() {
         return sysUserMapper.findUserForExport();
    }
}
