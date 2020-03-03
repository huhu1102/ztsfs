/**  
* 
*/  
 
package com.zt.common;

/**
 * @author whl
 * @date 2019年4月29日 
 */
public class PageUtil {
    /**
     * @param countNum  查询到的数据总条数 eg 100000
     * @param pageSize 页面总条数 eg. 10
     * @return
     */
    public static int getTotalPages(Integer countNum, Integer pageSize) {
        if((countNum%pageSize)==0) {
            return ((countNum/pageSize));
        }else {
            return ((countNum/pageSize)+1);
        }
    }
}
