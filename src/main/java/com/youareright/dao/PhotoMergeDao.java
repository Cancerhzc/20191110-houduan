package com.youareright.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youareright.model.sys.PhotoMergeEntity;

@Mapper
public interface PhotoMergeDao {
	/**
	 * 新建图片合成信息
	 * @param photoMergeEntity
	 */
	public void insertPhotoMerge(@Param("photoMergeEntity") PhotoMergeEntity photoMergeEntity);
	
	public void deleteMerges(@Param("groupId") List<String> groupId);
	
	public void updateMerges(@Param("mergeID") int mergeID);
	
	public Integer mergesSize(@Param("searchCondition")String searchCondition,@Param("pageSize") int pageSize,@Param("start") int start);
	
	public ArrayList<PhotoMergeEntity> mergesList(@Param("searchCondition")String searchCondition,@Param("pageSize")int pageSize,@Param("start") int start);
	
	public String getMergeUrlByMergeID(@Param("mergeID")int mergeID);

}
