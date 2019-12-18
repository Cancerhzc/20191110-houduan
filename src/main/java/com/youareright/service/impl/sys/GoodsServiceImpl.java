package com.youareright.service.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.GoodsDao;
import com.youareright.model.sys.GoodsEntity;
import com.youareright.service.sys.GoodsService;

@Service(value = "goodsServiceImpl")
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;
	

	public void insertGoods(GoodsEntity goodsEntity) {
		goodsDao.insertGoods(goodsEntity);
	}


	public void updateGoods(GoodsEntity goodsEntity) {
		goodsDao.updateGoods(goodsEntity);
	}
	

	public void del(GoodsEntity goodsEntity) {
		goodsDao.del(goodsEntity);
	}
	

	public void deleteGoodses(List<Integer> groupId) {
		goodsDao.deleteGoodses(groupId);
	}
	

	public List<GoodsEntity> goodsesList13(String searchCondition, int pageSize, int start) {
		return goodsDao.goodsesList13(searchCondition, pageSize, start);
	}
	
	public List<GoodsEntity> goodsesList02(String searchCondition, int pageSize, int start) {
		return goodsDao.goodsesList02(searchCondition, pageSize, start);
	}

	public Integer goodsesSize13(String searchCondition, int pageSize, int start) {
		return goodsDao.goodsesSize13(searchCondition, pageSize, start);
	}
	
	public Integer goodsesSize02(String searchCondition, int pageSize, int start) {
		return goodsDao.goodsesSize02(searchCondition, pageSize, start);
	}
	

	public Integer maxGoodsID() {
		return goodsDao.maxGoodsID();
	}
	

	public String getSrc(int goodsID) {
		return goodsDao.getSrc(goodsID);
	}
	

	public Integer getClassIDByGoodsID(int id) {
		return goodsDao.getClassIDByGoodsID(id);
	}
	

	public void modifyGoods(int oldClassID,int newClassID,String newGoodsPath) {
		goodsDao.modifyGoods(oldClassID, newClassID, newGoodsPath);
	}
	
	public GoodsEntity getGoodsEntityByGoodsID(int goodsID) {
		return goodsDao.getGoodsEntityByGoodsID(goodsID);
	}
}
