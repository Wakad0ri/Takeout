package com.atguigu.service;

import com.atguigu.Entity.Address;
import java.util.List;

public interface AddressBookService {

    void save(Address address);

    List<Address> list();

    Address getDefault();

    void update(Address address);

    void delete(Long id);

    Address getById(Long id);

    /**
     * 设置默认地址
     * 将指定ID的地址设置为默认地址，自动取消其他默认地址
     * @param address 包含地址ID的对象
     */
    void setDefault(Address address);
}
