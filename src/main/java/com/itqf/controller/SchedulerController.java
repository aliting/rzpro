package com.itqf.controller;

import com.itqf.entity.ScheduleJob;
import com.itqf.service.ScheduleService;
import com.itqf.utils.Pager;
import com.itqf.utils.R;
import com.itqf.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @Date: 2019/3/30
 * @Time: 上午9:33
 */
@RestController
public class SchedulerController {
    @Autowired
    private ScheduleService scheduleService;


    @RequestMapping("/schedule/job/list")
    public ResultData scheduList(Pager pager, String search){

        return scheduleService.scheduleList(pager,search);
    }

    @RequestMapping("/schedule/job/info/{jobId}")
    public R info(@PathVariable long jobId){
        return scheduleService.scheduleInfo(jobId);
    }

    @RequestMapping("/schedule/job/save")
    public R save(@RequestBody ScheduleJob scheduleJob){
        return scheduleService.save(scheduleJob);
    }

    @RequestMapping("/schedule/job/update")
    public R update(@RequestBody ScheduleJob scheduleJob){
        return scheduleService.update(scheduleJob);
    }

    @RequestMapping("/schedule/job/del")
    public R delete(@RequestBody List<Long> jobIds){
        return scheduleService.delete(jobIds);
    }

    @RequestMapping("/schedule/job/pause")
    public R pause(@RequestBody List<Long> jobIds){
        return scheduleService.pause(jobIds);
    }
    @RequestMapping("/schedule/job/resume")
    public R resume(@RequestBody List<Long> jobIds){
        return scheduleService.resume(jobIds);
    }
    @RequestMapping("/schedule/job/run")
    public R run(@RequestBody List<Long> jobIds){
        return scheduleService.run(jobIds);
    }

}
