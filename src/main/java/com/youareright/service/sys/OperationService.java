package com.youareright.service.sys;

import java.util.List;

import com.youareright.model.sys.OperationEntity;

public interface OperationService {
	public void insertOperation(int userID,String operation,String time);
	
	public List<OperationEntity> getOperation(int pageSize,int start);
	
	public Integer operationSize(int pageSize,int start);
}
