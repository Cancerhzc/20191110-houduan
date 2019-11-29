package com.youareright.service.sys;

import java.util.List;

import com.youareright.model.sys.GoodsEntity;

public interface GoodsService {
	
	public void insertGoods(GoodsEntity goodsEntity);
	
	public void updateGoods(GoodsEntity goodsEntity);
	
	public void del(GoodsEntity goodsEntity);
	
	public void deleteGoodses(List<String> groupId);

	public List<GoodsEntity> goodsesList(String searchCondition, int pageSize, int start);
	
	public Integer goodsesSize(String searchCondition,int pageSize, int start);
	
	public Integer maxGoodsID();
	
	public String getSrc(int goodsID);
	
	public Integer getClassIDByGoodsID(int id);
	
	public void modifyGoods(int oldClassID,int newClassID,String newGoodsPath);
	
}
