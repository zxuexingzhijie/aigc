package com.aigc.vo.reqvo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：jiang
 * @date ：2024/4/15 14:41
 * @description ：
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginReqVo {
    private String username;
    private String password;
//    /**
//     * 验证码
//     */
//    private String code;

//    /**
//     * 会话ID
//     */
//    private String sessionId;

}
