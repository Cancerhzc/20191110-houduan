package com.youareright.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youareright.model.sys.ClassEntity;

@Mapper
public interface ClassDao {
	/**
	 * 新建标签1
	 * @param classEntity
	 */
	public void insertClass(@Param("classEntity") ClassEntity classEntity);
	
	
	/**
	 * 新建标签2
	 */
	public void insertClass2(String goodsClass,String goodsName);
	
	/**
	 * 批量删除标签
	 * @param classEntity
	 */
	public void deleteClasses(@Param("groupId") List<String> groupID);
	
	public void del(int classID);
	/**
	 * 更新标签
	 * @param classEntity
	 */
	public void updateClass(@Param("classEntity") ClassEntity classEntity);
	
	
	public Integer classesSize(@Param("searchCondition")String searchCondition,@Param("pageSize") int pageSize,@Param("start") int start);
	
	public ArrayList<ClassEntity> classesList(@Param("searchCondition")String searchCondition,@Param("pageSize")int pageSize, @Param("start")int start);
	
	public Integer checkClassIsExisted(String labelName);
	
	public Integer maxClassID();
	
	public Integer getClassID(@Param("goodsClass")String labelName);
	
	public String getGoodsNameByClassID(int classID);
	
	public void modifyClass(int classID,String newClassName,String newGoodsName);
	
	
}
