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
@Tag(name = "员工管理")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     * @PostMapping 登录
     * @Description：员工登录
     * @Param：EmployeeLoginDTO
     * @Return：Result<EmployeeLoginVO>
     * 注解：@PostMapping
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
     * @Param：无
     * @Return：Result<String>
     * 注解：@PostMapping
     */
    @PostMapping("/logout")
    @Operation(summary = "员工登出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @Param：Employee
     * @Return：Result
     * 注解：@PostMapping，@RequestBody
     */
    @PostMapping
    @Operation(summary = "新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工，员工数据：{}", employeeDTO);

        System.out.println("当前线程的id是：" + Thread.currentThread().getId());

        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 员工分页查询
     * @Param：EmployeePageQueryDTO
     * @Return：Result<PageResult>
     */
    @GetMapping("/page")
    @Operation(summary = "员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     * @Param：Integer status
     * @Param：Long id
     * @Return：Result，若要设计是查询类型的，所以Result可以加泛型
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用员工账号")
    public Result startOrStop(@PathVariable("status") Integer status, Long id) {
        log.info("启用禁用员工账号：{}", id);
        employeeService.startOrStop(status, id);

        return Result.success();
    }
}
