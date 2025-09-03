package com.atguigu.service;

import com.atguigu.DTO.EmployeeType.EmployeeDTO;
import com.atguigu.DTO.EmployeeType.EmployeeLoginDTO;
import com.atguigu.DTO.EmployeeType.EmployeePageQueryDTO;
import com.atguigu.Entity.Employee;
import com.atguigu.result.PageResult;
import com.github.pagehelper.Page;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void saveEmployee(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void startOrStop(Integer status, Long id);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}
