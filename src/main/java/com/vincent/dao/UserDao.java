package com.vincent.dao;

import com.vincent.entity.TUser;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
@CacheNamespace
public interface UserDao {

    @Insert("insert into t_user(user_name,password,phone) values(#{userName}, #{password}, #{phone})")
    void add(TUser user);

    @Select("select * from t_user where user_id = #{id}")
    TUser getById(int id);
}
