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
	
	public void insertShelf(ShelfEntity shelfEntity) {
		shelfDao.insertShelf(shelfEntity);
	}

	public void updateShelf(ShelfEntity shelfEntity) {
		shelfDao.updateShelf(shelfEntity);
	}
	
	public void del(ShelfEntity shelfEntity) {
		shelfDao.del(shelfEntity);
	}
	
	public void deleteShelfes(List<Integer> groupId) {
		shelfDao.deleteShelves(groupId);
	}
	
	public List<ShelfEntity> shelvesList(String searchCondition, int pageSize, int start) {
		return shelfDao.shelvesList(searchCondition, pageSize, start);
	}
	
	public Integer shelvesSize(String searchCondition, int pageSize, int start) {
		return shelfDao.shelvesSize(searchCondition, pageSize, start);
	}
	

	public Integer maxShelfID() {
		return shelfDao.maxShelfID();
	}
	

	public String getSrc(int shelfID) {
		return shelfDao.getSrc(shelfID);
	}
	

	public Integer getClassIDByShelfID(int id) {
		return shelfDao.getClassIDByShelfID(id);
	}
}
