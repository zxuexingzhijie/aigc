package com.aigc.service.impl;

import com.aigc.constant.JwtClaimsConstant;
import com.aigc.constant.MessageConstant;
import com.aigc.entity.User;
import com.aigc.mapper.UserMapper;
import com.aigc.properties.JwtProperties;
import com.aigc.result.Result;
import com.aigc.service.UserService;
import com.aigc.utils.IdWorker;
import com.aigc.utils.JwtUtil;
import com.aigc.vo.reqvo.LoginReqVo;
import com.aigc.vo.reqvo.UserReqVo;
import com.aigc.vo.respvo.LoginRespVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：jiang
 * @date ：2024/4/26 17:04
 * @description ：用户相关接口实现
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private IdWorker idWorker;

    @Override
    public Result<LoginRespVo> login(LoginReqVo loginReqVo) {
        log.info("用户登录：{}", loginReqVo);
        // 检验参数合法性
        if (loginReqVo == null || StringUtils.isBlank(loginReqVo.getUsername()) || StringUtils.isBlank(loginReqVo.getPassword())) {
            return Result.error(MessageConstant.LOGIN_FAILED);
        }
        //根据用户名查询数据库
        User dbUser = userMapper.getUserByUsername(loginReqVo.getUsername());
        if (dbUser == null) {
            // 账号不存在
//            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
            return Result.error(MessageConstant.ACCOUNT_NOT_FOUND);
        }
//        if (!dbUser.getPassword().equals(user.getPassword())) {
//            // 密码错误
////            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
//            return Result.error(MessageConstant.PASSWORD_ERROR);
//        }
        //调用密码匹配器比对
        if (!dbUser.getPassword().equals(loginReqVo.getPassword())) {
            //密码错误
            return Result.error(MessageConstant.PASSWORD_ERROR);
        }

        //登录成功后，生成jwt令牌
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USERNAME, dbUser.getUsername());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        LoginRespVo loginRespVo = LoginRespVo.builder()
                .id(dbUser.getId())
                .username(dbUser.getUsername())
                .token(token).build();

        return Result.success(loginRespVo);
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public Result getUserInfo(String username) {
        User userByUsername = userMapper.getUserByUsername(username);
        userByUsername.setPassword("");
        return Result.success(userByUsername);
    }

    /**
     * 更新用户信息
     *
     * @param userReqVo
     * @return
     */
    @Override
    public Result updateUserInfo(UserReqVo userReqVo) {
        userMapper.updateUserById(userReqVo);
        return Result.success();
    }

    @Override
    public Map<String, Boolean> checkUsernameExists(String username) {
        User dbUser = userMapper.getUserByUsername(username);
        HashMap<String, Boolean> map = new HashMap<>();
        if (dbUser != null) {
            map.put("isExist", true);
        } else map.put("isExist", false);
        return map;
    }

    @Override
    public Result register(Map map) {
        if(map.get("username").equals("")){
            return Result.error("注册失败");
        }
        User user = User.builder().id(idWorker.nextId()).username((String) map.get("username")).password((String) map.get("password")).build();
        userMapper.insert(user);

        return Result.success("注册成功");
    }
}
