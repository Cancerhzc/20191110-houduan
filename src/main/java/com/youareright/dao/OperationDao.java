package com.youareright.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youareright.model.sys.OperationEntity;

@Mapper
public interface OperationDao {
	public void insertOperation(@Param("userID")int userID,@Param("operation")String operation,@Param("time")String time);
	
	public List<OperationEntity> getOperation(@Param("pageSize") int pageSize,@Param("start") int start);
	
	public Integer operationSize(@Param("pageSize") int pageSize,@Param("start") int start);
}
