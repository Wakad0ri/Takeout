package com.atguigu.service.impl;

import com.atguigu.Entity.Address;
import com.atguigu.constant.StatusConstant;
import com.atguigu.context.BaseContext;
import com.atguigu.mapper.AddressBookMapper;
import com.atguigu.service.AddressBookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Tag(name = "地址簿服务实现类")
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址簿
     * @param address （
     */
    @Override
    public void save(Address address) {
        Address ars = new Address();
        BeanUtils.copyProperties(address, ars);
        ars.setUserId(BaseContext.getCurrentId());
        ars.setIsDefault(StatusConstant.NOT_DEFAULT);
        addressBookMapper.insert(ars);
    }

    /**
     * 条件查询当前登录用户的地址信息
     * @return List<Address>
     */
    @Override
    public List<Address> list() {
        Address ars = new Address();
        ars.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.list(ars);
    }

    @Override
    /**
     * 查询当前登录用户的默认地址
     * @return Address
     */
    public Address getDefault() {
        Address ars = new Address();
        ars.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.getDefault(ars);
    }

    /**
     * 根据id修改地址簿
     * @param address
     */
    @Override
    public void update(Address address) {
        // 确保只能修改当前用户的地址
        address.setUserId(BaseContext.getCurrentId());
        addressBookMapper.update(address);
    }

    /**
     * 根据id删除地址簿
     * @param id （Long）
     */
    @Override
    @Transactional
    public void delete(Long id) {
        // 安全检查：确保只能删除当前用户的地址
        Address address = addressBookMapper.getById(id);
        if (address != null && address.getUserId().equals(BaseContext.getCurrentId())) {
            addressBookMapper.delete(id);
        }
    }

    /**
     * 根据id查询地址簿
     * @param id （Long）
     * @return Address
     */
    @Override
    public Address getById(Long id) {
        // 安全检查：确保只能查询当前用户的地址
        Address address = addressBookMapper.getById(id);
        if (address != null && address.getUserId().equals(BaseContext.getCurrentId())) {
            return address;
        }
        return null;
    }

    /**
     * 设置默认地址
     * 业务逻辑：
     * 1. 先获取该用户之前的默认地址，将其设置为非默认
     * 2. 将指定的地址设置为默认地址
     * @param address 包含地址ID的对象
     */
    @Override
    @Transactional
    public void setDefault(Address address) {
        Long currentUserId = BaseContext.getCurrentId();

        // 1. 先获取该用户当前的默认地址
        Address queryCondition = Address.builder()
                .userId(currentUserId)
                .isDefault(StatusConstant.DEFAULT)
                .build();
        Address oldDefault = addressBookMapper.getDefault(queryCondition);

        // 2. 如果存在旧的默认地址，将其设置为非默认
        if (oldDefault != null) {
            oldDefault.setIsDefault(StatusConstant.NOT_DEFAULT);
            addressBookMapper.updateDefault(oldDefault);
            log.info("将用户{}的旧默认地址{}设置为非默认", currentUserId, oldDefault.getId());
        }

        // 3. 将指定的地址设置为默认地址
        address.setIsDefault(StatusConstant.DEFAULT);
        addressBookMapper.updateDefault(address);
        log.info("将用户{}的地址{}设置为默认地址", currentUserId, address.getId());
    }
}
package com.atguigu.service.impl;

import com.atguigu.Entity.Address;
import com.atguigu.constant.StatusConstant;
import com.atguigu.context.BaseContext;
import com.atguigu.mapper.AddressBookMapper;
import com.atguigu.service.AddressBookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Tag(name = "地址簿服务实现类")
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址簿
     * @param address （
     */
    @Override
    public void save(Address address) {
        Address ars = new Address();
        BeanUtils.copyProperties(address, ars);
        ars.setUserId(BaseContext.getCurrentId());
        ars.setIsDefault(StatusConstant.NOT_DEFAULT);
        addressBookMapper.insert(ars);
    }

    /**
     * 条件查询当前登录用户的地址信息
     * @return List<Address>
     */
    @Override
    public List<Address> list() {
        Address ars = new Address();
        ars.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.list(ars);
    }

    @Override
    /**
     * 查询当前登录用户的默认地址
     * @return Address
     */
    public Address getDefault() {
        Address ars = new Address();
        ars.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.getDefault(ars);
    }

    /**
     * 根据id修改地址簿
     * @param address
     */
    @Override
    public void update(Address address) {
        // 确保只能修改当前用户的地址
        address.setUserId(BaseContext.getCurrentId());
        addressBookMapper.update(address);
    }

    /**
     * 根据id删除地址簿
     * @param id （Long）
     */
    @Override
    @Transactional
    public void delete(Long id) {
        // 安全检查：确保只能删除当前用户的地址
        Address address = addressBookMapper.getById(id);
        if (address != null && address.getUserId().equals(BaseContext.getCurrentId())) {
            addressBookMapper.delete(id);
        }
    }

    /**
     * 根据id查询地址簿
     * @param id （Long）
     * @return Address
     */
    @Override
    public Address getById(Long id) {
        // 安全检查：确保只能查询当前用户的地址
        Address address = addressBookMapper.getById(id);
        if (address != null && address.getUserId().equals(BaseContext.getCurrentId())) {
            return address;
        }
        return null;
    }

    /**
     * 设置默认地址
     * 业务逻辑：
     * 1. 先获取该用户之前的默认地址，将其设置为非默认
     * 2. 将指定的地址设置为默认地址
     * @param address 包含地址ID的对象
     */
    @Override
    @Transactional
    public void setDefault(Address address) {
        Long currentUserId = BaseContext.getCurrentId();

        // 1. 先获取该用户当前的默认地址
        Address queryCondition = Address.builder()
                .userId(currentUserId)
                .isDefault(StatusConstant.DEFAULT)
                .build();
        Address oldDefault = addressBookMapper.getDefault(queryCondition);

        // 2. 如果存在旧的默认地址，将其设置为非默认
        if (oldDefault != null) {
            oldDefault.setIsDefault(StatusConstant.NOT_DEFAULT);
            addressBookMapper.updateDefault(oldDefault);
            log.info("将用户{}的旧默认地址{}设置为非默认", currentUserId, oldDefault.getId());
        }

        // 3. 将指定的地址设置为默认地址
        address.setIsDefault(StatusConstant.DEFAULT);
        addressBookMapper.updateDefault(address);
        log.info("将用户{}的地址{}设置为默认地址", currentUserId, address.getId());
    }
}
