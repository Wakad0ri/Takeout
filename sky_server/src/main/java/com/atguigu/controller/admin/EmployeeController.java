package com.atguigu.controller.admin;

import com.atguigu.DTO.EmployeeType.EmployeeDTO;
import com.atguigu.DTO.EmployeeType.EmployeeLoginDTO;
import com.atguigu.DTO.EmployeeType.EmployeePageQueryDTO;
import com.atguigu.Entity.Employee;
import com.atguigu.VO.EmployeeLoginVO;
import com.atguigu.constant.JwtClaimsConstant;
import com.atguigu.properties.JwtProperties;
import com.atguigu.result.PageResult;
import com.atguigu.result.Result;
import com.atguigu.service.EmployeeService;
import com.atguigu.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理 Controller 类
 * 注解：@RestController, @RequestMapping, @Slf4j
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "员工管理-控制层")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录：POST
     * @param employeeLoginDTO （json）
     * @return Result<EmployeeLoginVO>
     */
    @PostMapping("/login")
    @Operation(summary = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        // 登录成功，生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId()); // 给JWT令牌设置一个员工id，作为载荷！
        String token = JwtUtil.createJWT(   // 根据配置文件中的参数生成JWT令牌！前端一定要携带这个令牌，才能访问有权限的接口！
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder() // 创建一个员工登录的VO对象, 将员工登录信息封装到VO对象中！给前端！
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 登出
     * @return Result
     */
    @PostMapping("/logout")
    @Operation(summary = "员工登出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工：POST
     * @param employeeDTO（json）
     * @return Result
     */
    @PostMapping
    @Operation(summary = "新增员工")
    public Result<String> saveEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}", employeeDTO);
        employeeService.saveEmployee(employeeDTO);

        return Result.success();
    }

    /**
     * 员工分页查询：GET
     * @param employeePageQueryDTO （name page pageSize）
     * @return Result<PageResult>
     */
    @GetMapping("/page")
    @Operation(summary = "员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号：POST( /status/{status} )
     * @param status (Integer）
     * @param id (Long)
     * @return Result<String>
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用员工账号")
    public Result startOrStop(@PathVariable("status") Integer status, @RequestParam("id") Long id){
        log.info("启用禁用员工账号：{}", id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 根据id查询员工信息：GET( /{id} )
     * @param id (Long)
     * @return Result<Employee>
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询员工信息")
    public Result<Employee> update(@PathVariable("id") Long id) {
        log.info("查询员工信息：{}", id);
        Employee employee = employeeService.getById(id);

        return Result.success(employee);
    }

    /**
     * 编辑员工信息：PUT
     * @param employeeDTO （json）
     * @return Result
     */
    @PutMapping
    @Operation(summary = "编辑员工信息")
    public Result<String> update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑员工信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
