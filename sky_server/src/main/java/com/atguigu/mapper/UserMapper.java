package com.atguigu.mapper;

import com.atguigu.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openid （String）
     * @return  User
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 新增用户
     * @param user （User）
     */
    @Insert("insert into user (openid, name, phone, sex, id_number, avatar, create_time) values " +
            "(#{openid}, #{name}, #{phone}, #{sex}, #{idNumber}, #{avatar}, #{createTime})")
    void insert(User user);

    /**
     * 根据id查询用户
     * @param id （Long）
     */
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    /**
     * 根据条件查询用户数量
     * @param map （Map）
     */
    Integer getUserCountByMap(Map<String, Object> map);
}
