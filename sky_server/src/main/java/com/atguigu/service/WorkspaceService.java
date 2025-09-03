package com.atguigu.service;

import com.atguigu.VO.BusinessDataVO;
import com.atguigu.VO.DishOverViewVO;
import com.atguigu.VO.OrderOverViewVO;
import com.atguigu.VO.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {

    BusinessDataVO businessData(LocalDateTime begin, LocalDateTime end);

    OrderOverViewVO orderOverView();

    DishOverViewVO dishOverView();

    SetmealOverViewVO setmealOverView();
}
package com.atguigu.service;

import com.atguigu.VO.BusinessDataVO;
import com.atguigu.VO.DishOverViewVO;
import com.atguigu.VO.OrderOverViewVO;
import com.atguigu.VO.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {

    BusinessDataVO businessData(LocalDateTime begin, LocalDateTime end);

    OrderOverViewVO orderOverView();

    DishOverViewVO dishOverView();

    SetmealOverViewVO setmealOverView();
}
