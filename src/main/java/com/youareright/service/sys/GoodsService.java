package com.youareright.service.sys;

import java.util.List;

import com.youareright.model.sys.GoodsEntity;
import com.youareright.model.sys.UserEntity;

public interface GoodsService {
	
	public void insertGoods(GoodsEntity goodsEntity);
	
	
	public void updateGoods(GoodsEntity goodsEntity);
	
	public void del(GoodsEntity goodsEntity);
	
	public void deleteGoodses(List<String> groupID);
	

	public List<GoodsEntity> goodsList(String goodsClass, int pageSize, int start);
	
	public Integer goodsSize(int pageSize, int start);

}
