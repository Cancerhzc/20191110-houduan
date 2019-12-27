package com.youareright.service.sys;

import java.util.List;

import com.youareright.model.sys.GoodsEntity;

public interface GoodsService {
	
	public void insertGoods(GoodsEntity goodsEntity);
	
	public void updateGoods(GoodsEntity goodsEntity);
	
	public void del(GoodsEntity goodsEntity);
	
	public void deleteGoodses(List<Integer> groupId);

	public List<GoodsEntity> goodsesList13(String searchCondition, int pageSize, int start);
	
	public List<GoodsEntity> goodsesList02(String searchCondition, int pageSize, int start);
	
	public Integer goodsesSize13(String searchCondition,int pageSize, int start);
	
	public Integer goodsesSize02(String searchCondition,int pageSize, int start);
	
	public Integer maxGoodsID();
	
	public String getSrc(int goodsID);
	
	public Integer getClassIDByGoodsID(int id);
	
	public void modifyGoods(int goodsID,int newClassID,String newGoodsPath);
	
	public GoodsEntity getGoodsEntityByGoodsID(int goodsID);
	
}
