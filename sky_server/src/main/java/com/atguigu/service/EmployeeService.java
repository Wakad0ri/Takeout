package com.atguigu.service;

import com.atguigu.DTO.EmployeeType.EmployeeDTO;
import com.atguigu.DTO.EmployeeType.EmployeeLoginDTO;
import com.atguigu.DTO.EmployeeType.EmployeePageQueryDTO;
import com.atguigu.Entity.Employee;
import com.atguigu.result.PageResult;
import com.github.pagehelper.Page;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return PageResult
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void startOrStop(Integer status, Long id);
}
