package com.youareright.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youareright.model.sys.UpClassEntity;

@Mapper
public interface UpClassDao {
	public void deleteUpClasses(@Param("groupId") List<Integer> groupID);
	
	public Integer upClassesSize(@Param("searchCondition")String searchCondition,@Param("pageSize") int pageSize,@Param("start") int start);
	
	public ArrayList<UpClassEntity> upClassesList(@Param("searchCondition")String searchCondition,@Param("pageSize")int pageSize, @Param("start")int start);
	
	public void insertUpClass(@Param("upClassName")String upClassName);
	
	public UpClassEntity getUpClassEntityByUpClassID(@Param("upClassID")int upClassID);
	
	public UpClassEntity getUpClassEntityByUpClassName(@Param("upClassName")String upClassName);
	
	public void updateUpClass(@Param("upClassID") int upClassID,@Param("upClassName")String newUpClassName);
}
