package com.youareright.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youareright.model.sys.ShelfEntity;

@Mapper
public interface ShelfDao {
	/**
	 * 新建货架图片信息
	 * @param shelfEntity
	 */
	public void insertShelf(@Param("shelfEntity") ShelfEntity shelfEntity);
	
	/**
	 * 删除货架图片信息
	 * @param shelfEntity
	 */
	public void del(@Param("shelfEntity") ShelfEntity shelfEntity);
	
	public void deleteShelves(@Param("groupId") List<String> groupId);
	
	/**
	 * 更新货架图片信息
	 * @param shelfEntity
	 */
	public void updateShelf(@Param("shelfEntity") ShelfEntity shelfEntity);
	
	public Integer shelvesSize(@Param("searchCondition")String searchCondition,@Param("pageSize") int pageSize,@Param("start") int start);
	
	public ArrayList<ShelfEntity> shelvesList(@Param("searchCondition")String searchCondition,@Param("pageSize")int pageSize, @Param("start")int start);
	
	public Integer maxShelfID();
	
	public String getSrc(@Param("shelfID") int shelfID);
	
	public Integer getClassIDByShelfID(@Param("shelfID")int shelfID);
	
}
