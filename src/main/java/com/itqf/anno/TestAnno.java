package com.itqf.anno;

import java.lang.reflect.Method;

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
 * @Time: 上午11:18
 */
@MyFirstAnno
public class TestAnno {

    @MySecondAnno(value = "展示用户信息",name="seAnno")
    public  void show(){
        System.out.println("hello 注解！！！！");
    }

    public static void main(String[]args) throws  Exception{

//        MyAnno anno = new MyAnno();
//        anno.setAge(11);
//
//        int  count ;

        TestAnno testAnno = new TestAnno();
        Class c = testAnno.getClass();
//
//        AnnotatedType annotatedType = c.getAnnotatedSuperclass();
//        System.out.println(annotatedType instanceof  MyFirstAnno);

         MyFirstAnno  anno =(MyFirstAnno) c.getAnnotation(MyFirstAnno.class);

          System.out.println(anno);

          System.out.println(anno.name()+"---"+anno.value());

          //反射让注解具有灵魂

         Method method = c.getDeclaredMethod("show");

         //得到方法的注解
        MySecondAnno mySecondAnno =   method.getDeclaredAnnotation(MySecondAnno.class);

        System.out.println(mySecondAnno.value()+"--"+mySecondAnno.name());
        if (mySecondAnno!=null){
                //new
        }

    }
}
