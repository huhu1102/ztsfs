/**  
* 
*/  
 
package com.zt.model;

/**
 * @author whl
 * @date 2019年4月22日 
 * api统一返回结果类 (异常)
 */
public class ApiResult {

    /**
     * 结果码
     */
    private String code;

    /**
     * 结果码描述
     */
    private String msg;


    public ApiResult() {

    }

    public ApiResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    /**
     * 生成一个ApiResult对象, 并返回
     *
     * @param resultCode
     * @return
     */                        
    public static ApiResult of(ResultCode resultCode) {
        return new ApiResult(resultCode);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
