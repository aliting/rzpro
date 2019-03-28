package com.itqf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.entity.SysUser;
import com.itqf.entity.SysUserExample;
import com.itqf.mapper.SysUserMapper;
import com.itqf.service.SysUsersService;
import com.itqf.utils.*;
import org.springframework.stereotype.Service;

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
 * @Date: 2019/3/25
 * @Time: 下午3:03
 */
@Service(value = "sysUserServiceImpl" )
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
}
