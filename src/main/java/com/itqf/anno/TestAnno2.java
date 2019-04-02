package com.itqf.anno;

import java.sql.Connection;

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
 * @Date: 2019/3/29
 * @Time: 上午11:47
 */
@DBAnno(value = "ORACLE")//mysql  com.mysql.jdbc.Driver
public class TestAnno2 {


    //   oracle:oracle.jdbc.OracleDriver
    public Connection getConnection(){
        try{
            Class c =   TestAnno2.class;
            DBAnno dbAnno = (DBAnno) c.getAnnotation(DBAnno.class);
            String DriverNAME = null;
            if (dbAnno.value().equals("MYSQL")){
                DriverNAME = "com.mysql.jdbc.Driver";
                System.out.println("MYSQL");
            }
            if (dbAnno.value().equals("ORACLE")){
                DriverNAME = "oracle.jdbc.OracleDriver";
                System.out.println("oracle");
            }
            if (dbAnno.value().equals("SQLSERVER")){
                DriverNAME = "SQLSERVER.dDriver";
                System.out.println("SQLSERVER");
            }


            //Class.forName(DriverNAME);
            //return DriverManager.getConnection("","","");
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[]args){

        TestAnno2 anno2 = new TestAnno2();
        anno2.getConnection();


    }


}
