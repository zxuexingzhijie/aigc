package com.aigc.exception;

/**
 * @author ：jiang
 * @date ：2024/4/15 15:24
 * @description ：账户不存在
 */
public class AccountNotFoundException extends BaseException{
    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
