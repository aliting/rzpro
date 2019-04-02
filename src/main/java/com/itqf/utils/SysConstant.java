package com.itqf.utils;

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
 * @Description: 项目常量类
 * @Company: 千锋互联
 * @Author: 李丽婷
 * @Date: 2019/3/30
 * @Time: 上午10:06
 */
public class SysConstant {

    public  static final  String CAPTCHA_KEY="code";
    public  static  final String JOB_KEY_PREFIX="myJob_";
    public  static  final String TRIGGER_KEY_PREFIX="myTrigger_";
    public static final String SCHEDULE_DATA_KEY="schedule_data_key";



    //public  static final  byte NOMAL=0;
    //public  static final  byte PAUSE=1;


    public enum  ScheduleStatus{
        NOMAL((byte)0)
        ,PAUSE((byte)1);

        private byte value;
        ScheduleStatus(byte value){
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }

}
