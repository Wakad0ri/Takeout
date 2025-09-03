package com.atguigu.mapper;

import com.atguigu.DTO.EmployeeType.EmployeePageQueryDTO;
import com.atguigu.Entity.Employee;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 员工管理
 * 注解：@Mapper——告诉MyBatis这个接口是一个Mapper
 * 作用：告诉MyBatis这个接口是Mapper接口
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     *
     * 若sql非常简单，就用@Select注解的方式实现
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     * @param employee
     */
    @Insert("insert into employee(username, name, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{username}, #{name}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return com.github.pagehelper.Page<Employee>
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据id修改员工账号启用状态
     * @param employee
     */
    void update(Employee employee);
}
