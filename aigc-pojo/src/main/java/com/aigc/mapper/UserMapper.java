package com.aigc.mapper;

import com.aigc.entity.User;
import com.aigc.vo.reqvo.UserReqVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author yg
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-04-26 16:58:27
* @Entity com.aigc.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    /**
     * 根据id修改用户信息
     * @param userReqVo
     */
    void updateUserById(UserReqVo userReqVo);


}




