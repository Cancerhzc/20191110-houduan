package com.youareright.service.impl.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.OperationDao;
import com.youareright.model.sys.OperationEntity;
import com.youareright.service.sys.OperationService;

@Service(value = "operationServiceImpl")
public class OperationServiceImpl implements OperationService {
	@Autowired
	private OperationDao operationDao;
	
	public void insertOperation(int userID,String operation,String time) {
		operationDao.insertOperation(userID,operation,time);
	}
	
	public List<OperationEntity> getOperation(int pageSize,int start) {
		return operationDao.getOperation(pageSize, start);
	}
	
	public Integer operationSize(int pageSize,int start) {
		return operationDao.operationSize(pageSize, start);
	}

}
