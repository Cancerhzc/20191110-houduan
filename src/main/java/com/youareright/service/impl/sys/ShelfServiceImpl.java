package com.youareright.service.impl.sys;

import com.youareright.service.sys.ShelfService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.ShelfDao;
import com.youareright.model.sys.ShelfEntity;

@Service(value = "shelfServiceImpl")
public class ShelfServiceImpl implements ShelfService {

	@Autowired
	private ShelfDao shelfDao;
	
	@Override
	public void insertShelf(ShelfEntity shelfEntity) {
		shelfDao.insertShelf(shelfEntity);
	}

	@Override
	public void updateShelf(ShelfEntity shelfEntity) {
		shelfDao.updateShelf(shelfEntity);
	}
	
	@Override
	public void del(ShelfEntity shelfEntity) {
		shelfDao.del(shelfEntity);
	}
	
	@Override
	public void deleteShelfes(List<String> groupId) {
		shelfDao.deleteShelves(groupId);
	}
	
	@Override
	public List<ShelfEntity> shelvesList(String searchCondition, int pageSize, int start) {
		return shelfDao.shelvesList(searchCondition, pageSize, start);
	}
	
	@Override
	public Integer shelvesSize(String searchCondition, int pageSize, int start) {
		return shelfDao.shelvesSize(searchCondition, pageSize, start);
	}
	
	@Override
	public Integer maxShelfID() {
		return shelfDao.maxShelfID();
	}
	
	@Override
	public String getSrc(int shelfID) {
		return shelfDao.getSrc(shelfID);
	}
	
	@Override
	public Integer getClassIDByShelfID(int id) {
		return shelfDao.getClassIDByShelfID(id);
	}
}
