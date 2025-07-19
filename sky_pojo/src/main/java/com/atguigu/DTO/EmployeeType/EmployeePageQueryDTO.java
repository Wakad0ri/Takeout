package com.atguigu.DTO.EmployeeType;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工分页查询参数
 * 作用：员工分页查询参数
 *
 * 重载：Serializable
 * 描述：员工分页查询参数
 */
@Data
public class EmployeePageQueryDTO implements Serializable {

    //员工姓名
    private String name;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

}
