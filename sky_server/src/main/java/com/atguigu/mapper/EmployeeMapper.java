package com.atguigu.mapper;

import com.atguigu.DTO.EmployeeType.EmployeePageQueryDTO;
import com.atguigu.Entity.Employee;
import com.atguigu.annotation.AutoFill;
import com.atguigu.enumeration.OperationType;
import com.github.pagehelper.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 员工管理
 * 注解：@Mapper——告诉MyBatis这个接口是一个Mapper
 * 作用：告诉MyBatis这个接口是Mapper接口
 */
@Mapper
@Tag(name = "员工管理-数据访问层")
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username （用户名）
     * @return Employee
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工数据
     * @param employee （
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 分页查询
     * @param employeePageQueryDTO （封装了查询条件）
     * @return com.github.pagehelper.Page<Employee>
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据id修改员工账号启用状态
     * @param employee（包含员工id和员工状态）
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateStatus(Employee employee);

    /**
     * 根据id查询员工信息
     * @param id （Long）
     */
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);

    /**
     * 修改员工信息
     * @param employee （员工信息）
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateInfo(Employee employee);


}
