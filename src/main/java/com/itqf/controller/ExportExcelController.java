package com.itqf.controller;

import com.itqf.service.SysUsersService;
import com.itqf.utils.Lg;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
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
 * @Date: 2019/4/2
 * @Time: 下午2:20
 */
@RestController
public class ExportExcelController {

    @Resource
    private SysUsersService sysUsersService;


    @RequestMapping("/exportExcel")
    public void export(HttpServletResponse response){
        try{
        response.setContentType("application/octet-stream");//xxx.*
        String filename = "千锋员工信息.xls";
        filename = URLEncoder.encode(filename,"utf-8");//编码
        response.setHeader("content-disposition","attachment;filename="+filename);
        List<Map<String,Object>>  list = sysUsersService.exportExcel();
        //list--->excel
        Workbook workbook = new HSSFWorkbook();//空的excel文件
        Sheet sheet =  workbook.createSheet("千锋集团员工信息");
        String titles = "userId,username,email,mobile,createTime,sex";
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i);//行
            String t[] = titles.split(",");
            Map<String,Object> map = list.get(i);

            for (int j = 0; j < t.length; j++) {
                //单元格
                Cell cell = row.createCell(j);
                cell.setCellValue(map.get(t[j])+"");//给单元格赋值

            }

        }

            OutputStream os =  response.getOutputStream();
            workbook.write(os);//把excel文件响应到客户端
            os.flush();
            Lg.log("导出成功");
        }catch(Exception e){
            e.printStackTrace();
            Lg.log("导出失败");
        }





    }



}
