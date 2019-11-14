package com.youareright.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youareright.model.sys.ClassEntity;
import com.youareright.model.sys.UserEntity;

@Mapper
public interface ClassDao {
	/**
	 * 新建标签
	 * @param classEntity
	 */
	public void insertClass(@Param("classEntity") ClassEntity classEntity);
	
	/**
	 * 删除标签
	 * @param classEntity
	 */
	public void deleteClasses(@Param("groupID") List<String> groupID);
	
	public void del(@Param("classEntity") ClassEntity classEntity);
	/**
	 * 更新标签
	 * @param classEntity
	 */
	public void updateClass(@Param("classEntity") ClassEntity classEntity);
	
	
	public Integer classesSize(@Param("goodsClass")String goodsClass,@Param("pageSize") int pageSize,@Param("start") int start);
	
	public ArrayList<ClassEntity> classesList(@Param("goodsClass")String goodsClass,@Param("pageSize")int pageSize, @Param("start")int start);
	
}
