package com.itqf.quartz.task;

import com.itqf.utils.InitDatabaseUtils;
import com.itqf.utils.Lg;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

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
 * @Time: 上午9:07
 */
@Component(value = "backUpDB")
public class BackUpDBTask {

    public  void backUp(String msg){
        Lg.log("备份数据库！"+ msg);
    }

    public  void backUp(){
        Lg.log("备份数据库！");
        try{
            Properties properties = new Properties();
            InputStream is = BackUpDBTask.class.getClassLoader().getResourceAsStream("backdb.properties");

            InputStreamReader isr = new InputStreamReader(is,"utf-8");

            properties.load(isr);
           String command =  InitDatabaseUtils.getExportCommand(properties);
           //mysqldump -uroot -padmin -h127.0.01 -P3306 rzdb -r /Users/xxxx/
            String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

            command = command+fileName+".sql";

            Runtime runtime = Runtime.getRuntime();

            runtime.exec(command);
            Lg.log("备份数据库成功",properties.getProperty("jdbc.exportPath"));

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
