package com.aigc.service;

import com.aigc.result.Result;
import com.aigc.vo.reqvo.LoginReqVo;
import com.aigc.vo.reqvo.UserReqVo;
import com.aigc.vo.respvo.LoginRespVo;

import java.util.Map;

/**
 * @author ：jiang
 * @date ：2024/4/26 17:02
 * @description ：用户相关接口
 */
public interface UserService {
    Result<LoginRespVo> login(LoginReqVo loginReqVo);

    /**
     * 获取用户信息
     *
     * @return
     */
    Result getUserInfo(String username);

    /**
     * 更新用户信息
     * @param userReqVo
     * @return
     */
    Result updateUserInfo(UserReqVo userReqVo);

    Map<String, Boolean> checkUsernameExists(String username);

    Result register(Map map);
}
