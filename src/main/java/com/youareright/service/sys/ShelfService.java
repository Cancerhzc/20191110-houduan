package com.youareright.service.sys;
import com.youareright.model.sys.ShelfEntity;
import java.util.*;

public interface ShelfService {
	public void insertShelf(ShelfEntity shelfEntity);
	
	public void updateShelf(ShelfEntity shelfEntity);
	
	public void del(ShelfEntity shelfEntity);
	
	public void deleteShelfes(List<String> groupId);

	public List<ShelfEntity> shelvesList(String searchCondition, int pageSize, int start);
	
	public Integer shelvesSize(String searchCondition,int pageSize, int start);
	
	public Integer maxShelfID();
	
	public String getSrc(int shelfID);
	
	public Integer getClassIDByShelfID(int id);

}
