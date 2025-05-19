package com.aigc.vo.reqvo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：jiang
 * @date ：2024/5/5 20:38
 * @description ：用户请求Vo
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserReqVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String username;
    private String realName;
    private String nickName;
    private String phone;
    private String email;
    private Integer sex;
}
