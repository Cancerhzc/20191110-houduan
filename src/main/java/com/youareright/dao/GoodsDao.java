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
	
	public void deleteGoodses(@Param("groupID") List<String> groupId);
	
	/**
	 * 更新商品图片信息
	 * @param goodsEntity
	 */
	public void updateGoods(@Param("goodsEntity") GoodsEntity goodsEntity);
	
	
	public Integer goodsSize(@Param("pageSize") int pageSize,@Param("start") int start);
	
	public ArrayList<GoodsEntity> goodsList(@Param("goodsFilename")String goodsFilename,@Param("pageSize")int pageSize, @Param("start")int start);
	

}
