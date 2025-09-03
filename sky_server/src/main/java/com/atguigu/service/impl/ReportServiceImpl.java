package com.atguigu.service.impl;


import com.atguigu.DTO.SaleType.GoodsSalesDTO;
import com.atguigu.Entity.Orders;
import com.atguigu.VO.*;
import com.atguigu.mapper.OrderMapper;
import com.atguigu.mapper.UserMapper;
import com.atguigu.service.ReportService;
import com.atguigu.service.WorkspaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Tag(name = "数据统计接口")
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 营业额统计
     * @param begin LocalDateTime
     * @param end LocalDateTime
     * @return TurnoverReportVO
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        // 创建集合存储begin到end每天的日期
        List<LocalDate> dateList = getWhatDayBeginToEnd(begin, end);
        // 处理turnoverList数据：查询date日期对应的当天产生的营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList){
            // 先获取下单具体时间：
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);// LocalTime.of(date, LocalTime.MIN) 获取指定日期的开始时刻
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);  // LocalTime.of(date, LocalTime.MAX) 表示当天的最后时刻
            Map<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnoverList.add(turnover == null ? 0.0 : turnover);
        }
        // 处理dateList数据 和 turnoverList数据 的封装
        String dateListString = StringUtils.join(dateList, ",");  // StringUtils.join作用是：拼接字符串
        String turnoverListString =  StringUtils.join(turnoverList, ",");

        return TurnoverReportVO.builder()
                .dateList(dateListString)
                .turnoverList(turnoverListString)
                .build();
    }

    /**
     * 用户统计
     * @param begin LocalDate
     * @param end LocalDate
     * @return UserReportVO
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        // 创建集合存储begin到end每天对应的用户数据
        List<LocalDate> dateList = getWhatDayBeginToEnd(begin, end);
        // 处理newUserList数据：查询date日期对应的当天新增的用户数量（根据User的createTime属性判断）
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        for (LocalDate date : dateList){
            // 先获取下单具体时间：
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map<String, Object> map = new HashMap<>();
            // 每天新增用户数量 select count(id) from user where createTime < ? and createTime >= ?
            // 每天用户总数 select count(id) from user where createTime < ? 可以用一个动态sql查询
            map.put("end", endTime);
            Integer newUser = userMapper.getUserCountByMap(map);
            map.put("begin", beginTime);
            Integer totalUser = userMapper.getUserCountByMap(map);

            newUserList.add(newUser);
            totalUserList.add(totalUser);
        }
        // 处理dateList数据 和 newUserList数据 的封装
        String dateListString = StringUtils.join(dateList, ",");
        String newUserListString = StringUtils.join(newUserList, ",");
        String totalUserListString = StringUtils.join(totalUserList, ",");
        return UserReportVO.builder()
                .dateList(dateListString)
                .newUserList(newUserListString)
                .totalUserList(totalUserListString)
                .build();
    }

    /**
     * 订单统计
     * @param begin LocalDate
     * @param end LocalDate
     * @return OrderReportVO
     */
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        // 创建集合存储begin到end每天对应的订单数据
        List<LocalDate> dateList = getWhatDayBeginToEnd(begin, end);

        // 处理orderList、validOrderList、orderCompletionRate数据
        List<Integer> totalOrderList = new ArrayList<>();
        List<Integer> validOrderList = new ArrayList<>();
        Integer totalOrderCount = 0;
        Integer validOrderCount = 0;
        for (LocalDate date : dateList){
            // 先获取下单具体时间：
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // 传递Map查询信息，根据sql获取每天的 totalCount validCount
            Map<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            Integer orderCount = orderMapper.getOrderCountByMap(map);
            map.put("status", Orders.COMPLETED);
            Integer validCount = orderMapper.getOrderCountByMap(map);
            // 计算 totalOrderCount validOrderCount
            totalOrderCount += orderCount;
            validOrderCount += validCount;
            // 并 add 进集合totalOrderList validOrderList
            totalOrderList.add(orderCount);
            validOrderList.add(validCount);
        }

        // 处理dateList数据 和 totalOrderList数据 的封装
        String dateListString = StringUtils.join(dateList, ",");
        String totalOrderListString = StringUtils.join(totalOrderList, ",");
        String validOrderListString = StringUtils.join(validOrderList, ",");
        return OrderReportVO.builder()
                .dateList(dateListString)
                .orderCountList(totalOrderListString)
                .validOrderCountList(validOrderListString)

                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(validOrderCount.doubleValue() / totalOrderCount)
                .build();
    }

    /**
     * 销量排名
     * @param begin LocalDate
     * @param end LocalDate
     * @return List<Sale>
     */
    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        // 先获取下单具体时间：
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        // 传递Map查询信息，根据sql获取每天的销量数据（name和number数据封装进GoodSalesDTO）
        Map<String, Object> map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);
        map.put("status", Orders.COMPLETED);
        // 查询这个时间段的 OrderDetails.name&number 字段（+ Order 的多表查询
     /*
        select od.name, sum(od.number)  from order_detail od, orders o where od.order_id = o.id and o.order_time >= ? and o.order_time < ? and o.status = 5
        group by od.name order by sum(od.number) desc limit 0,10
    */
        List<GoodsSalesDTO> salesTop10 = orderMapper.getTop10ByMap(map);
        // 获取所有的name, number数据
        List<String> nameList = salesTop10.stream().map(GoodsSalesDTO::getName).toList();
        List<Integer> numberList = salesTop10.stream().map(GoodsSalesDTO::getNumber).toList();

        // 处理 nameList, numberList数据 的封装
        String nameListString = StringUtils.join(nameList, ",");
        String numberListString = StringUtils.join(numberList, ",");
        return SalesTop10ReportVO.builder()
                .nameList(nameListString)
                .numberList(numberListString)
                .build();
        }

        /**
         * 导出营业数据
         * @param response HttpServletResponse
         */
        @Override
        public void exportBusinessData(HttpServletResponse response) {
            // 设计Excel文件
            // 查询近30天的营业数据
            LocalDate begin = LocalDate.now().minusDays(30);
            LocalDate end = LocalDate.now().minusDays(1);

            LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
            // 查询30天的总营业数据
            BusinessDataVO ThirtyDaysBusiness = workspaceService.businessData(beginTime, endTime);

            // 将查询到的运营数据写入模板文件
            /*
             * this.getClass()  ：表示当前类
             * getClassLoader() ：表示获取类加载器
             * getResourceAsStream("xxx") 获取指定模板文件输入流（在resources里，这里表示在当前类所处的加载器的resources中的template文件夹）
             */
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
            try {
                XSSFWorkbook excel = new XSSFWorkbook(is);
                XSSFSheet sheet = excel.getSheet("Sheet1");
                sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end); // 填充时间
                sheet.getRow(3).getCell(2).setCellValue(ThirtyDaysBusiness.getTurnover() != null ? ThirtyDaysBusiness.getTurnover() : 0.0);    // 填充营业额
                sheet.getRow(3).getCell(4).setCellValue(ThirtyDaysBusiness.getValidOrderCount() != null ? ThirtyDaysBusiness.getValidOrderCount() : 0); // 填充有效订单数
                sheet.getRow(3).getCell(6).setCellValue(ThirtyDaysBusiness.getOrderCompletionRate() != null ? ThirtyDaysBusiness.getOrderCompletionRate() : 0.0); // 填充订单完成率
                sheet.getRow(4).getCell(2).setCellValue(ThirtyDaysBusiness.getNewUsers() != null ? ThirtyDaysBusiness.getNewUsers() : 0);    // 填充新增用户数
                sheet.getRow(4).getCell(4).setCellValue(ThirtyDaysBusiness.getUnitPrice() != null ? ThirtyDaysBusiness.getUnitPrice() : 0.0);   // 填充平均单价
                for (int i = 0; i < 30; i++){
                    LocalDate date = begin.plusDays(i);
                    // 获取指定日期的营业数据
                    BusinessDataVO OneDayBusinessData = workspaceService.businessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                    sheet.getRow(7 + i).getCell(1).setCellValue(date.toString());   // 填充日期
                    sheet.getRow(7 + i).getCell(2).setCellValue(OneDayBusinessData.getTurnover() != null ? OneDayBusinessData.getTurnover() : 0.0);    // 填充营业额
                    sheet.getRow(7 + i).getCell(3).setCellValue(OneDayBusinessData.getValidOrderCount() != null ? OneDayBusinessData.getValidOrderCount() : 0); // 填充有效订单数
                    sheet.getRow(7 + i).getCell(4).setCellValue(OneDayBusinessData.getOrderCompletionRate() != null ? OneDayBusinessData.getOrderCompletionRate() : 0.0);   // 填充订单完成率
                    sheet.getRow(7 + i).getCell(5).setCellValue(OneDayBusinessData.getUnitPrice() != null ? OneDayBusinessData.getUnitPrice() : 0.0); // 填充平均单价
                    sheet.getRow(7 + i).getCell(6).setCellValue(OneDayBusinessData.getNewUsers() != null ? OneDayBusinessData.getNewUsers() : 0);  // 填充新增用户数
                }


            // 通过输出流将Excel文件下载到客户端浏览器
                ServletOutputStream out = response.getOutputStream();
                excel.write(out);

                // 关闭流
                out.close();
                excel.close();

            } catch (IOException e){
                e.printStackTrace();
            }


        }

    /**
     * 获取指定日期区间内的日期（重复代码提取方法）
     * @param begin LocalDate
     * @param end LocalDate
     * @return List<LocalDate>
     */
    private static List<LocalDate> getWhatDayBeginToEnd(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        while (begin.isBefore(end)){
            // 日期计算，计算指定日期后一天对应的日期
            dateList.add(begin);
            begin = begin.plusDays(1);
        }
        return dateList;
    }
}
package com.atguigu.service.impl;


import com.atguigu.DTO.SaleType.GoodsSalesDTO;
import com.atguigu.Entity.Orders;
import com.atguigu.VO.*;
import com.atguigu.mapper.OrderMapper;
import com.atguigu.mapper.UserMapper;
import com.atguigu.service.ReportService;
import com.atguigu.service.WorkspaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Tag(name = "数据统计接口")
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 营业额统计
     * @param begin LocalDateTime
     * @param end LocalDateTime
     * @return TurnoverReportVO
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        // 创建集合存储begin到end每天的日期
        List<LocalDate> dateList = getWhatDayBeginToEnd(begin, end);
        // 处理turnoverList数据：查询date日期对应的当天产生的营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList){
            // 先获取下单具体时间：
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);// LocalTime.of(date, LocalTime.MIN) 获取指定日期的开始时刻
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);  // LocalTime.of(date, LocalTime.MAX) 表示当天的最后时刻
            Map<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnoverList.add(turnover == null ? 0.0 : turnover);
        }
        // 处理dateList数据 和 turnoverList数据 的封装
        String dateListString = StringUtils.join(dateList, ",");  // StringUtils.join作用是：拼接字符串
        String turnoverListString =  StringUtils.join(turnoverList, ",");

        return TurnoverReportVO.builder()
                .dateList(dateListString)
                .turnoverList(turnoverListString)
                .build();
    }

    /**
     * 用户统计
     * @param begin LocalDate
     * @param end LocalDate
     * @return UserReportVO
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        // 创建集合存储begin到end每天对应的用户数据
        List<LocalDate> dateList = getWhatDayBeginToEnd(begin, end);
        // 处理newUserList数据：查询date日期对应的当天新增的用户数量（根据User的createTime属性判断）
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        for (LocalDate date : dateList){
            // 先获取下单具体时间：
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map<String, Object> map = new HashMap<>();
            // 每天新增用户数量 select count(id) from user where createTime < ? and createTime >= ?
            // 每天用户总数 select count(id) from user where createTime < ? 可以用一个动态sql查询
            map.put("end", endTime);
            Integer newUser = userMapper.getUserCountByMap(map);
            map.put("begin", beginTime);
            Integer totalUser = userMapper.getUserCountByMap(map);

            newUserList.add(newUser);
            totalUserList.add(totalUser);
        }
        // 处理dateList数据 和 newUserList数据 的封装
        String dateListString = StringUtils.join(dateList, ",");
        String newUserListString = StringUtils.join(newUserList, ",");
        String totalUserListString = StringUtils.join(totalUserList, ",");
        return UserReportVO.builder()
                .dateList(dateListString)
                .newUserList(newUserListString)
                .totalUserList(totalUserListString)
                .build();
    }

    /**
     * 订单统计
     * @param begin LocalDate
     * @param end LocalDate
     * @return OrderReportVO
     */
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        // 创建集合存储begin到end每天对应的订单数据
        List<LocalDate> dateList = getWhatDayBeginToEnd(begin, end);

        // 处理orderList、validOrderList、orderCompletionRate数据
        List<Integer> totalOrderList = new ArrayList<>();
        List<Integer> validOrderList = new ArrayList<>();
        Integer totalOrderCount = 0;
        Integer validOrderCount = 0;
        for (LocalDate date : dateList){
            // 先获取下单具体时间：
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // 传递Map查询信息，根据sql获取每天的 totalCount validCount
            Map<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            Integer orderCount = orderMapper.getOrderCountByMap(map);
            map.put("status", Orders.COMPLETED);
            Integer validCount = orderMapper.getOrderCountByMap(map);
            // 计算 totalOrderCount validOrderCount
            totalOrderCount += orderCount;
            validOrderCount += validCount;
            // 并 add 进集合totalOrderList validOrderList
            totalOrderList.add(orderCount);
            validOrderList.add(validCount);
        }

        // 处理dateList数据 和 totalOrderList数据 的封装
        String dateListString = StringUtils.join(dateList, ",");
        String totalOrderListString = StringUtils.join(totalOrderList, ",");
        String validOrderListString = StringUtils.join(validOrderList, ",");
        return OrderReportVO.builder()
                .dateList(dateListString)
                .orderCountList(totalOrderListString)
                .validOrderCountList(validOrderListString)

                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(validOrderCount.doubleValue() / totalOrderCount)
                .build();
    }

    /**
     * 销量排名
     * @param begin LocalDate
     * @param end LocalDate
     * @return List<Sale>
     */
    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        // 先获取下单具体时间：
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        // 传递Map查询信息，根据sql获取每天的销量数据（name和number数据封装进GoodSalesDTO）
        Map<String, Object> map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);
        map.put("status", Orders.COMPLETED);
        // 查询这个时间段的 OrderDetails.name&number 字段（+ Order 的多表查询
     /*
        select od.name, sum(od.number)  from order_detail od, orders o where od.order_id = o.id and o.order_time >= ? and o.order_time < ? and o.status = 5
        group by od.name order by sum(od.number) desc limit 0,10
    */
        List<GoodsSalesDTO> salesTop10 = orderMapper.getTop10ByMap(map);
        // 获取所有的name, number数据
        List<String> nameList = salesTop10.stream().map(GoodsSalesDTO::getName).toList();
        List<Integer> numberList = salesTop10.stream().map(GoodsSalesDTO::getNumber).toList();

        // 处理 nameList, numberList数据 的封装
        String nameListString = StringUtils.join(nameList, ",");
        String numberListString = StringUtils.join(numberList, ",");
        return SalesTop10ReportVO.builder()
                .nameList(nameListString)
                .numberList(numberListString)
                .build();
        }

        /**
         * 导出营业数据
         * @param response HttpServletResponse
         */
        @Override
        public void exportBusinessData(HttpServletResponse response) {
            // 设计Excel文件
            // 查询近30天的营业数据
            LocalDate begin = LocalDate.now().minusDays(30);
            LocalDate end = LocalDate.now().minusDays(1);

            LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
            // 查询30天的总营业数据
            BusinessDataVO ThirtyDaysBusiness = workspaceService.businessData(beginTime, endTime);

            // 将查询到的运营数据写入模板文件
            /*
             * this.getClass()  ：表示当前类
             * getClassLoader() ：表示获取类加载器
             * getResourceAsStream("xxx") 获取指定模板文件输入流（在resources里，这里表示在当前类所处的加载器的resources中的template文件夹）
             */
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
            try {
                XSSFWorkbook excel = new XSSFWorkbook(is);
                XSSFSheet sheet = excel.getSheet("Sheet1");
                sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end); // 填充时间
                sheet.getRow(3).getCell(2).setCellValue(ThirtyDaysBusiness.getTurnover() != null ? ThirtyDaysBusiness.getTurnover() : 0.0);    // 填充营业额
                sheet.getRow(3).getCell(4).setCellValue(ThirtyDaysBusiness.getValidOrderCount() != null ? ThirtyDaysBusiness.getValidOrderCount() : 0); // 填充有效订单数
                sheet.getRow(3).getCell(6).setCellValue(ThirtyDaysBusiness.getOrderCompletionRate() != null ? ThirtyDaysBusiness.getOrderCompletionRate() : 0.0); // 填充订单完成率
                sheet.getRow(4).getCell(2).setCellValue(ThirtyDaysBusiness.getNewUsers() != null ? ThirtyDaysBusiness.getNewUsers() : 0);    // 填充新增用户数
                sheet.getRow(4).getCell(4).setCellValue(ThirtyDaysBusiness.getUnitPrice() != null ? ThirtyDaysBusiness.getUnitPrice() : 0.0);   // 填充平均单价
                for (int i = 0; i < 30; i++){
                    LocalDate date = begin.plusDays(i);
                    // 获取指定日期的营业数据
                    BusinessDataVO OneDayBusinessData = workspaceService.businessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                    sheet.getRow(7 + i).getCell(1).setCellValue(date.toString());   // 填充日期
                    sheet.getRow(7 + i).getCell(2).setCellValue(OneDayBusinessData.getTurnover() != null ? OneDayBusinessData.getTurnover() : 0.0);    // 填充营业额
                    sheet.getRow(7 + i).getCell(3).setCellValue(OneDayBusinessData.getValidOrderCount() != null ? OneDayBusinessData.getValidOrderCount() : 0); // 填充有效订单数
                    sheet.getRow(7 + i).getCell(4).setCellValue(OneDayBusinessData.getOrderCompletionRate() != null ? OneDayBusinessData.getOrderCompletionRate() : 0.0);   // 填充订单完成率
                    sheet.getRow(7 + i).getCell(5).setCellValue(OneDayBusinessData.getUnitPrice() != null ? OneDayBusinessData.getUnitPrice() : 0.0); // 填充平均单价
                    sheet.getRow(7 + i).getCell(6).setCellValue(OneDayBusinessData.getNewUsers() != null ? OneDayBusinessData.getNewUsers() : 0);  // 填充新增用户数
                }


            // 通过输出流将Excel文件下载到客户端浏览器
                ServletOutputStream out = response.getOutputStream();
                excel.write(out);

                // 关闭流
                out.close();
                excel.close();

            } catch (IOException e){
                e.printStackTrace();
            }


        }

    /**
     * 获取指定日期区间内的日期（重复代码提取方法）
     * @param begin LocalDate
     * @param end LocalDate
     * @return List<LocalDate>
     */
    private static List<LocalDate> getWhatDayBeginToEnd(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        while (begin.isBefore(end)){
            // 日期计算，计算指定日期后一天对应的日期
            dateList.add(begin);
            begin = begin.plusDays(1);
        }
        return dateList;
    }
}
