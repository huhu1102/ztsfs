
package com.zt.model;

/**
 * @author whl
 * @date 2019年4月22日 
 * 错误码 （异常）
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS("0", "success"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR("0x10001", "请联系后台！"),

    /**
     * 用户名错误或不存在
     */
    USERNAME_ERROR("0x10002", "用户名错误或不存在"),



    /**
     * 密码错误
     */
    PASSWORD_ERROR("0x10003", "密码错误"),

    /**
     * 用户名不能为空
     */
     USERNAME_EMPTY("0x10004", "用户名不能为空"),
     /**
      * 操作失败
      */
	OPER_FAILED("0X10005","操作失败");
	
    /**
     * 结果码
     */
    private String code;

    /**
     * 结果码描述
     */
    private String msg;


    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

