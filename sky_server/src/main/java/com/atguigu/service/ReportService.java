package com.atguigu.service;

import com.atguigu.VO.OrderReportVO;
import com.atguigu.VO.SalesTop10ReportVO;
import com.atguigu.VO.TurnoverReportVO;
import com.atguigu.VO.UserReportVO;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

public interface ReportService {

    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    UserReportVO userStatistics(LocalDate begin, LocalDate end);

    OrderReportVO ordersStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO top10(LocalDate begin, LocalDate end);

    void exportBusinessData(HttpServletResponse response);
}
