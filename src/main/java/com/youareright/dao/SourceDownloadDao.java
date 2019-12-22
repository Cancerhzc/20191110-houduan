package com.youareright.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youareright.model.sys.SourceDownloadEntity;


@Mapper
public interface SourceDownloadDao {
	public Integer sourceListSize(@Param("searchCondition")String searchCondition,@Param("pageSize") int pageSize,@Param("start") int start);
	
	public ArrayList<SourceDownloadEntity> sourceList(@Param("searchCondition")String searchCondition,@Param("pageSize")int pageSize, @Param("start")int start);
	
}
