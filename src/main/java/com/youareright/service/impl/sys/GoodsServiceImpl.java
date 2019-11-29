package com.youareright.service.impl.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.GoodsDao;
import com.youareright.model.sys.GoodsEntity;
import com.youareright.service.sys.GoodsService;

@Service(value = "goodsServiceImpl")
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;
	
	@Override
	public void insertGoods(GoodsEntity goodsEntity) {
		goodsDao.insertGoods(goodsEntity);
	}

	@Override
	public void updateGoods(GoodsEntity goodsEntity) {
		goodsDao.updateGoods(goodsEntity);
	}
	
	@Override
	public void del(GoodsEntity goodsEntity) {
		goodsDao.del(goodsEntity);
	}
	
	@Override
	public void deleteGoodses(List<String> groupId) {
		goodsDao.deleteGoodses(groupId);
	}
	
	@Override
	public List<GoodsEntity> goodsesList(String searchCondition, int pageSize, int start) {
		return goodsDao.goodsesList(searchCondition, pageSize, start);
	}
	
	@Override
	public Integer goodsesSize(String searchCondition, int pageSize, int start) {
		return goodsDao.goodsesSize(searchCondition, pageSize, start);
	}
	
	@Override
	public Integer maxGoodsID() {
		return goodsDao.maxGoodsID();
	}
	
	@Override
	public String getSrc(int goodsID) {
		return goodsDao.getSrc(goodsID);
	}
	
	@Override
	public Integer getClassIDByGoodsID(int id) {
		return goodsDao.getClassIDByGoodsID(id);
	}
	
	@Override
	public void modifyGoods(int oldClassID,int newClassID,String newGoodsPath) {
		goodsDao.modifyGoods(oldClassID, newClassID, newGoodsPath);
	}


}
