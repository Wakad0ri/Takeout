package com.atguigu.controller.user;

import com.atguigu.Entity.Address;
import com.atguigu.result.Result;
import com.atguigu.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Tag(name = "用户端-地址簿管理")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址簿：POST
     * @param address （json）
     * @return Result<String>
     */
    @PostMapping
    @Operation(summary = "新增地址簿")
    public Result<String> save(@RequestBody Address address){
        log.info("新增地址簿：{}", address);
        addressBookService.save(address);
        return Result.success();
    }

    /**
     * 条件查询当前登录用户的地址信息： GET
     * @return Result<List<Address>>
     */
    @GetMapping("/list")
    @Operation(summary = "查询当前登录用户的地址信息")
    public Result<List<Address>> list(){
        log.info("查询当前登录用户的地址信息");
        List<Address> list = addressBookService.list();
        return Result.success(list);
    }

    /**
     * 查询当前登录用户的默认地址： GET
     * @return Result<Address>
     */
    @GetMapping("/default")
    @Operation(summary = "查询当前登录用户的默认地址")
    public Result<Address> getDefault(){
        log.info("查询当前登录用户的默认地址");
        Address address = addressBookService.getDefault();
        return Result.success(address);
    }

    /**
     * 根据id修改地址簿：PUT
     * @param address （json）
     * @return Result<String>
     */
    @PutMapping
    @Operation(summary = "根据id修改地址簿")
    public Result<String> update(@RequestBody Address address){
        log.info("根据id修改地址簿：{}", address);
        addressBookService.update(address);
        return Result.success();
    }

    /**
     * 根据id删除地址簿：DELETE
     * @param id （Long）
     * @return Result<String>
     */
    @DeleteMapping("/delete")
    @Operation(summary = "根据id删除地址簿")
    public Result<String> delete(@RequestParam Long id){
        log.info("根据id删除地址簿：{}", id);
        addressBookService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询地址簿：GET
     * @param id （Long）
     * @return Result<Address>
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询地址簿")
    public Result<Address> getById(@PathVariable Long id){
        log.info("根据id查询地址簿：{}", id);
        Address address = addressBookService.getById(id);
        return Result.success(address);
    }


    /**
     * 设置默认地址：PUT
     * @param address 包含地址ID的对象
     * @return Result<String>
     */
    @Deprecated
    @PutMapping("/default")
    @Operation(summary = "设置默认地址（兼容端点）", deprecated = true)
    public Result<String> setDefault(@RequestBody Address address){
        log.info("设置默认地址：{}", address);
        addressBookService.setDefault(address);
        return Result.success();
    }
}
package com.atguigu.controller.user;

import com.atguigu.Entity.Address;
import com.atguigu.result.Result;
import com.atguigu.service.AddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Tag(name = "用户端-地址簿管理")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址簿：POST
     * @param address （json）
     * @return Result<String>
     */
    @PostMapping
    @Operation(summary = "新增地址簿")
    public Result<String> save(@RequestBody Address address){
        log.info("新增地址簿：{}", address);
        addressBookService.save(address);
        return Result.success();
    }

    /**
     * 条件查询当前登录用户的地址信息： GET
     * @return Result<List<Address>>
     */
    @GetMapping("/list")
    @Operation(summary = "查询当前登录用户的地址信息")
    public Result<List<Address>> list(){
        log.info("查询当前登录用户的地址信息");
        List<Address> list = addressBookService.list();
        return Result.success(list);
    }

    /**
     * 查询当前登录用户的默认地址： GET
     * @return Result<Address>
     */
    @GetMapping("/default")
    @Operation(summary = "查询当前登录用户的默认地址")
    public Result<Address> getDefault(){
        log.info("查询当前登录用户的默认地址");
        Address address = addressBookService.getDefault();
        return Result.success(address);
    }

    /**
     * 根据id修改地址簿：PUT
     * @param address （json）
     * @return Result<String>
     */
    @PutMapping
    @Operation(summary = "根据id修改地址簿")
    public Result<String> update(@RequestBody Address address){
        log.info("根据id修改地址簿：{}", address);
        addressBookService.update(address);
        return Result.success();
    }

    /**
     * 根据id删除地址簿：DELETE
     * @param id （Long）
     * @return Result<String>
     */
    @DeleteMapping("/delete")
    @Operation(summary = "根据id删除地址簿")
    public Result<String> delete(@RequestParam Long id){
        log.info("根据id删除地址簿：{}", id);
        addressBookService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询地址簿：GET
     * @param id （Long）
     * @return Result<Address>
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询地址簿")
    public Result<Address> getById(@PathVariable Long id){
        log.info("根据id查询地址簿：{}", id);
        Address address = addressBookService.getById(id);
        return Result.success(address);
    }


    /**
     * 设置默认地址：PUT
     * @param address 包含地址ID的对象
     * @return Result<String>
     */
    @Deprecated
    @PutMapping("/default")
    @Operation(summary = "设置默认地址（兼容端点）", deprecated = true)
    public Result<String> setDefault(@RequestBody Address address){
        log.info("设置默认地址：{}", address);
        addressBookService.setDefault(address);
        return Result.success();
    }
}
