package com.zt.serviceImp;

import com.zt.dao.SpecificationDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Specification;
import com.zt.service.SpecificationService;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("specificationService")
public class SpecificationServiceImp implements SpecificationService {

    @Autowired
    SpecificationDao  specificationDao;

    /*
    分页模糊查询
     */
    @Override
    public ResultPage<Specification> findSearch(String queryName, int page, int size) throws BusinessRuntimeException {
        ResultPage<Specification> ro=new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Specification> pages = specificationDao.findSearch(queryName, pageable);
		 if (pages!=null) {
	        	int totals=(int) pages.getTotalElements();
	        	 ro.setData(pages.getContent());
	    	     ro.setTotal(totals);
	    	     ro.setTotalPages(pages.getTotalPages());
	    	     ro.setSuccess(true);
			} else {
				ro.setSuccess(false);
				throw new BusinessRuntimeException(ResultCode.UNKNOWN_ERROR);
			}
		return ro;
    }
    /*
    新建和更新
     */
    @Override
    public ResultObject<Specification> create(Specification specification) throws BusinessRuntimeException {
        ResultObject<Specification> ro = new ResultObject<>();

        if(specificationDao.findName(specification.getName())!=null){
            ro.setSuccess(false);
            ro.setMsg("该规格已存在不可以重复添加");
        }else {
            if (Long.valueOf(specification.getId()) == null || specification.getId() == 0) {
                specification.setCreateDate(new Date());
            }
            specification.setEnable(true);
            String nn = specification.getName();
            if (getChinese(nn) != null) {
                specification.setCode(getPinYinHeadChar(nn));
            } else {
                specification.setCode(nn);
            }
            specification = specificationDao.saveAndFlush(specification);
            if (specification != null) {
                ro.setSuccess(true);
                ro.setMsg("操作成功");
            } else {
                ro.setSuccess(false);
                ro.setMsg("操作失败");
                throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
            }
        }
        return ro;
    }
    /*
    删除
     */
    @Override
    public ResultObject<Specification> delete(long id) throws BusinessRuntimeException {
        ResultObject<Specification> ro = new ResultObject<>();

        Specification specification = specificationDao.findById(id);
        if(specification!=null){
            specification.setEnable(false);
            specificationDao.saveAndFlush(specification);
            ro.setSuccess(true);
            ro.setMsg("操作成功");
        }else{
            ro.setSuccess(false);
            ro.setMsg("操作失败");
            throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
        }
        return ro;
    }
    // 返回中文的首字母
    public  String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert.toUpperCase();
    }
    /*
    中英文混合的字符串找出中文
     */
    public  String getChinese( String str) {
        StringBuffer sbf = new StringBuffer();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            // 获得中文
            // Java判断一个字符串是否有中文是利用Unicode编码来判断，因为中文的编码区间为：0x4e00--0x9fbb
            if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
                sbf.append(charArray[i]);
            }
        }
        return String.valueOf(sbf);
    }

}
