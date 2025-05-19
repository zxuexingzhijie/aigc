package com.aigc.exception;

/**
 * @author ：jiang
 * @date ：2024/4/15 15:24
 * @description ：密码错误
 */
public class PasswordErrorException extends BaseException{
    public PasswordErrorException() {
    }
    public PasswordErrorException(String msg) {
        super(msg);
    }
}
