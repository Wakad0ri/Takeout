package com.atguigu.mapper;

import com.atguigu.Entity.Address;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@Tag(name = "地址簿Mapper")
public interface AddressBookMapper {

    /**
     * 新增地址簿
     * @param address （
     */
    @Insert("insert into address (user_id, consignee, sex, phone, province_code, city_code, district_code, detail, label, is_default) values " +
            "(#{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{cityCode}, #{districtCode}, #{detail}, #{label}, #{isDefault})")
    void insert(Address address);

    /**
     * 条件查询当前登录用户的地址信息
     * @param address （
     * @return List<Address>
     */
    List<Address> list(Address  address);

    /**
     * 查询当前登录用户的默认地址信息
     * @param address （
     * @return Address
     */
    @Select("select * from address where user_id = #{userId} and is_default = #{isDefault}")
    Address getDefault(Address address);

    /**
     * 根据id修改地址簿
     * @param address （
     */
    void update(Address address);

    /**
     * 根据id删除地址簿
     * @param id （
     */
    @Delete("delete from address where id = #{id}")
    void delete(Long id);

    /**
     * 根据id查询地址簿
     * @param id （
     * @return Address
     */
    @Select("select * from address where id = #{id}")
    Address getById(Long id);

    /**
     * 修改地址为默认/非默认
     * @param address （
     */
    void updateDefault(Address address);
}
