package com.youareright.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youareright.model.sys.GoodsEntity;

@Mapper
public interface GoodsDao {
	/**
	 * 新建商品图片信息
	 * @param goodsEntity
	 */
	public void insertGoods(@Param("goodsEntity") GoodsEntity goodsEntity);
	
	/**
	 * 删除商品图片信息
	 * @param goodsEntity
	 */
	public void del(@Param("goodsEntity") GoodsEntity goodsEntity);
	
	public void deleteGoodses(@Param("groupId") List<Integer> groupId);
	
	public void modifyGoods(int oldClassID,int newClassID,String newGoodsPath);
	
	/**
	 * 更新商品图片信息
	 * @param goodsEntity
	 */
	public void updateGoods(@Param("goodsEntity") GoodsEntity goodsEntity);
	
	public Integer goodsesSize13(@Param("searchCondition")String searchCondition,@Param("pageSize") int pageSize,@Param("start") int start);
	
	public Integer goodsesSize02(@Param("searchCondition")String searchCondition,@Param("pageSize") int pageSize,@Param("start") int start);
	
	public ArrayList<GoodsEntity> goodsesList13(@Param("searchCondition")String searchCondition,@Param("pageSize")int pageSize, @Param("start")int start);
	
	public ArrayList<GoodsEntity> goodsesList02(@Param("searchCondition")String searchCondition,@Param("pageSize")int pageSize, @Param("start")int start);
	
	public Integer maxGoodsID();
	
	public String getSrc(@Param("goodsID") int goodsID);
	
	public Integer getClassIDByGoodsID(@Param("goodsID")int goodsID);
	
	public GoodsEntity getGoodsEntityByGoodsID(@Param("goodsID")int goodsID);
	
}
