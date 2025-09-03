package com.atguigu.controller.admin;

import com.atguigu.DTO.SaleType.GoodsSalesDTO;
import com.atguigu.VO.OrderReportVO;
import com.atguigu.VO.SalesTop10ReportVO;
import com.atguigu.VO.TurnoverReportVO;
import com.atguigu.VO.UserReportVO;
import com.atguigu.result.Result;
import com.atguigu.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Tag(name = "数据统计接口")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 统计营业数据：GET
     * @param begin （开始时间）
     * @param end   （结束时间）
     * @return Result<TurnoverReportVO>
     */
    @GetMapping("/turnoverStatistics")
    @Operation(summary = "营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("营业额统计：{}到{}", begin, end);
        TurnoverReportVO turnoverReportVO = reportService.turnoverStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }

    /**
     * 统计用户数据：GET
     * @param begin （开始时间）
     * @param end   （结束时间）
     * @return Result<UserReportVO>
     */
    @GetMapping("/userStatistics")
    @Operation(summary = "用户统计")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("用户统计：{}到{}", begin, end);
        UserReportVO userReportVO = reportService.userStatistics(begin, end);
        return Result.success(userReportVO);
    }

    /**
     * 统计订单数据：GET
     * @param begin （开始时间）
     * @param end   （结束时间）
     * @return Result<OrderReportVO>
     */
    @GetMapping("/ordersStatistics")
    @Operation(summary = "订单统计")
    public Result<OrderReportVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("订单统计：{}到{}", begin, end);

        OrderReportVO orderReportVO =  reportService.ordersStatistics(begin, end);
        return Result.success(orderReportVO);
    }

    /**
     * 销量前十商品展示：GET
     * @param begin （开始时间）
     * @param end   （结束时间）
     * @return Result<SalesTop10ReportVO>
     */
    @GetMapping("/top10")
    @Operation(summary = "销量排名")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("销量排名：{}到{}", begin, end);
        SalesTop10ReportVO SalesTop10ReportVO = reportService.top10(begin, end);
        return Result.success(SalesTop10ReportVO);
    }

    /**
     * 导出运营数据Excel报表： GET
     */
    @GetMapping("/export")
    @Operation(summary = "导出运营数据Excel报表")
    public void export(HttpServletResponse response) {
        log.info("导出运营数据Excel报表");
        reportService.exportBusinessData(response);
    }

}
package com.atguigu.controller.admin;

import com.atguigu.DTO.SaleType.GoodsSalesDTO;
import com.atguigu.VO.OrderReportVO;
import com.atguigu.VO.SalesTop10ReportVO;
import com.atguigu.VO.TurnoverReportVO;
import com.atguigu.VO.UserReportVO;
import com.atguigu.result.Result;
import com.atguigu.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Tag(name = "数据统计接口")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 统计营业数据：GET
     * @param begin （开始时间）
     * @param end   （结束时间）
     * @return Result<TurnoverReportVO>
     */
    @GetMapping("/turnoverStatistics")
    @Operation(summary = "营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("营业额统计：{}到{}", begin, end);
        TurnoverReportVO turnoverReportVO = reportService.turnoverStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }

    /**
     * 统计用户数据：GET
     * @param begin （开始时间）
     * @param end   （结束时间）
     * @return Result<UserReportVO>
     */
    @GetMapping("/userStatistics")
    @Operation(summary = "用户统计")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("用户统计：{}到{}", begin, end);
        UserReportVO userReportVO = reportService.userStatistics(begin, end);
        return Result.success(userReportVO);
    }

    /**
     * 统计订单数据：GET
     * @param begin （开始时间）
     * @param end   （结束时间）
     * @return Result<OrderReportVO>
     */
    @GetMapping("/ordersStatistics")
    @Operation(summary = "订单统计")
    public Result<OrderReportVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("订单统计：{}到{}", begin, end);

        OrderReportVO orderReportVO =  reportService.ordersStatistics(begin, end);
        return Result.success(orderReportVO);
    }

    /**
     * 销量前十商品展示：GET
     * @param begin （开始时间）
     * @param end   （结束时间）
     * @return Result<SalesTop10ReportVO>
     */
    @GetMapping("/top10")
    @Operation(summary = "销量排名")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("销量排名：{}到{}", begin, end);
        SalesTop10ReportVO SalesTop10ReportVO = reportService.top10(begin, end);
        return Result.success(SalesTop10ReportVO);
    }

    /**
     * 导出运营数据Excel报表： GET
     */
    @GetMapping("/export")
    @Operation(summary = "导出运营数据Excel报表")
    public void export(HttpServletResponse response) {
        log.info("导出运营数据Excel报表");
        reportService.exportBusinessData(response);
    }

}
