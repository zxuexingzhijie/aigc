package com.aigc.controller;

import com.aigc.constant.JwtClaimsConstant;
import com.aigc.properties.JwtProperties;
import com.aigc.result.Result;
import com.aigc.service.UserService;
import com.aigc.utils.JwtUtil;
import com.aigc.vo.reqvo.LoginReqVo;
import com.aigc.vo.reqvo.UserReqVo;
import com.aigc.vo.respvo.LoginRespVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author ：jiang
 * @date ：2024/4/26 17:00
 * @description ：用户controller
 */
@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用户登录
     * @param loginReqVo
     * @return
     */
    @PostMapping("/login")
    public Result<LoginRespVo> userLogin(@RequestBody LoginReqVo loginReqVo) {
        return userService.login(loginReqVo);
    }

    @PostMapping("/upload")
    // 使用@RequestParam注解来指定请求参数名file，传递到MultipartFile对象
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        // 指定文件保存路径，这里将文件保存到项目根目录下的upload目录
        String filePath = System.getProperty("user.dir") + "/upload/";
        File dest = new File(filePath + fileName);
        try {
            // 将上传的文件保存到目标文件中
            file.transferTo(dest);
            return Result.success("文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error("文件上传失败");
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public Result getUserInfo(HttpServletRequest request){
        String token = request.getHeader(jwtProperties.getUserTokenName());
        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
        String username = claims.get(JwtClaimsConstant.USERNAME).toString();
        return userService.getUserInfo(username);
    }

    /**
     * 更新用户信息
     * @param userReqVo
     * @return
     */
    @PostMapping("/update")
    public Result updateUserInfo(@RequestBody UserReqVo userReqVo){
        return userService.updateUserInfo(userReqVo);
    }

    @GetMapping("/check-username/{username}")
    public Map<String, Boolean> checkUsernameExists(@PathVariable String username) {
        return userService.checkUsernameExists(username);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Map map){
        return userService.register(map);
    }
}
