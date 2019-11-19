package com.youareright.service.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	public void del(GoodsEntity goodsEntity) {
		goodsDao.del(goodsEntity);
	}
	
	public void deleteGoodses(List<String> groupId) {
		goodsDao.deleteGoodses(groupId);
	}
	
	public List<GoodsEntity> goodsesList(String searchCondition, int pageSize, int start) {
		return goodsDao.goodsesList(searchCondition, pageSize, start);
	}
	
	public Integer goodsesSize(String searchCondition, int pageSize, int start) {
		return goodsDao.goodsesSize(searchCondition, pageSize, start);
	}
	
	public Integer maxGoodsID() {
		return goodsDao.maxGoodsID();
	}
	


}
