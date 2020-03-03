/**
 * 
 */
package com.zt.serviceImp;

import com.zt.dao.ClientDao;
import com.zt.dao.ClientTypeDao;
import com.zt.dao.SalesPlanDao;
import com.zt.dao.UploadFileDao;
import com.zt.model.BusinessRuntimeException;
import com.zt.model.ResultCode;
import com.zt.model.ResultObject;
import com.zt.model.ResultPage;
import com.zt.po.Client;
import com.zt.po.ClientType;
import com.zt.po.SalesPlan;
import com.zt.po.UploadFile;
import com.zt.service.ClientService;
import com.zt.vo.ClientModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wl
 * @date 2019年4月15日 
 * 客户信息业务层的实现类
 */
 @Service("clientService")
// @CacheConfig(cacheNames = "zt_caches_client")
public class ClientServiceImp implements ClientService{
	 
	@Autowired
	ClientDao clientDao;
	@Autowired
	ClientTypeDao typeDao;
	@Autowired
	SalesPlanDao salesPlanDao;
	@Autowired
	UploadFileDao uploadFileDao;
	/*
	 * 新建
	 */
	@Override
	public ResultObject<Client> saveClient(ClientModel clientModel) throws BusinessRuntimeException {
		ResultObject<Client> ro =  new ResultObject<>();
		ClientModel mo=new ClientModel();
		Client client =mo.v2p(clientModel);

		Client parent=	clientDao.findById(clientModel.getParentClientId());
		if(clientModel.getParentClientId()!=0) {
			client.setParent(parent);
			parent.getChild().add(client);
		}
		List<UploadFile> uploadFileList = new ArrayList<>();
		String uploadFiles = clientModel.getUploadFiles();
		if(uploadFiles !=null && !uploadFiles.equals("")){
			uploadFileList=addUploadList(uploadFileList,uploadFiles);
		}
		client.setUploadFiles(uploadFileList);
		client = clientDao.saveAndFlush(client);
			if(client!=null) {
				ro.setSuccess(true);
				ro.setMsg("添加成功");
			}else {
				ro.setSuccess(false);
				ro.setMsg("操作失败");
				throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
			}
		return ro;
	}


	/*
	 * 修改
	 */
	@Override
	@Transactional
	public ResultObject<Client> update(ClientModel clientModel) throws BusinessRuntimeException {
		ResultObject<Client> ro =  new ResultObject<>();
		//获取该客户修改之前的信息
		ClientModel mo = new ClientModel();
		Client client = mo.v2p(clientModel);
		String uploadFilesStr = clientModel.getUploadFiles();
		List<UploadFile> uploadFileList = client.getUploadFiles();
		//去除之前有的关系
		if(uploadFileList!=null&&uploadFileList.size()>0){
			uploadFileList.removeAll(uploadFileList);
		}else{
			uploadFileList=new ArrayList<>();
		}
		uploadFileList=addUploadList(uploadFileList,uploadFilesStr);
		client.setUploadFiles(uploadFileList);
		client = clientDao.saveAndFlush(client);
		if(client!=null){
		    ro.setSuccess(true);
		    ro.setMsg("修改成功");
        }else{
		    ro.setSuccess(false);
		    ro.setMsg("修改失败");
        }
		return ro;
	}
    private List<UploadFile> addUploadList(List<UploadFile> uploadFileList,String uploadFiles){
        JSONArray array = JSONArray.fromObject(uploadFiles);
        if(array.size()>0){
            for (int i = 0,n=array.size(); i < n; i++) {
                JSONObject object = array.getJSONObject(i);
                String url = String.valueOf(object.get("url"));
                UploadFile uploadFile = uploadFileDao.findByUrl(url);
                uploadFileList.add(uploadFile);
            }
        }
        return uploadFileList;
    }


	/*
     * 分页模糊条件查询
     */
	@Override
	public ResultPage<Client> findSearch(String queryName, int page, int size,String types,String shopType)
			throws BusinessRuntimeException {
		ResultPage<Client> ro=new ResultPage<Client>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<Client> pages = clientDao.findAllByPages(shopType,queryName,types,pageable);
		
		 if (pages.getContent()!=null) {
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
	 * 删除
	 */
	@Override
	@Transactional
	public ResultObject<Client> deletCli(long id) throws BusinessRuntimeException {
		ResultObject<Client> ro=new ResultObject<>(); 
		Client client=clientDao.findById(id);
		 if (client!=null) {
			 client.setEnabled(false);
			if (client.getParentClientId()!=0) {
				Client parent=clientDao.findById(client.getParentClientId());
				List<Client> children=parent.getChild();
				for (int n=children.size()-1;  n>=0; n--) {
					Client child=children.get(n);
					if (child.getId()==id) {
						children.remove(n);
					}
				}
				clientDao.saveAndFlush(parent);
			}else{
			  clientDao.saveAndFlush(client);
			}
			ro.setSuccess(true);
			ro.setMsg("操作成功");
		}else {
			ro.setSuccess(false);
			ro.setMsg("操作失败");
			throw new BusinessRuntimeException(ResultCode.OPER_FAILED);
		}  
		return ro;
	}

	@Override
	public ResultObject<Client> getbasdata() throws BusinessRuntimeException {
		ResultObject<Client> ro=new ResultObject<Client>();
		Set<Client> clist=clientDao.findAllParent();
		List<Client> list=new ArrayList<>(clist);
		Map<String,Object> map=new HashMap<>();
		Set<Object> typelist=typeDao.findallprodutType();
        List<ClientType> alltyelist=typeDao.findAll();
		map.put("typelist",typelist);
		map.put("typeslist",alltyelist);
		if (clist!=null) {
			ro.setData(list);
			ro.setRoot(map);
			ro.setMsg("查询成功！");
			ro.setSuccess(true);
		}else {
			ro.setSuccess(false);
			ro.setMsg("查询失败！");
			throw new  BusinessRuntimeException(ResultCode.OPER_FAILED);
		}
		return ro;
	}
	/*
	查询某个客户最近三次的销售计划
	 */
	@Override
	public ResultPage<SalesPlan> findSalesPlan(long clientId, int page, int size) {
		ResultPage<SalesPlan> ro = new ResultPage<>();
		Pageable pageable = PageRequest.of(page-1,size);
		Page<SalesPlan> pages = salesPlanDao.findByClient(clientId,pageable);

		if (pages.getContent()!=null) {
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


}
