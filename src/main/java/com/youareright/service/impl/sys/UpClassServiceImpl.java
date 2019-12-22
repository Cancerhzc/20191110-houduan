package com.youareright.service.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.UpClassDao;
import com.youareright.model.sys.UpClassEntity;
import com.youareright.service.sys.UpClassService;

@Service(value = "upClassServiceImpl")
public class UpClassServiceImpl implements UpClassService {
	@Autowired
	private UpClassDao upClassDao;
	
	public List<UpClassEntity> upClassesList(String searchCondition, int pageSize, int start) {
		return upClassDao.upClassesList(searchCondition, pageSize, start);
	}

	public Integer upClassesSize(String searchCondition, int pageSize, int start) {
		return upClassDao.upClassesSize(searchCondition, pageSize, start);
	}
	
	public void deleteUpClasses(List<Integer> groupId) {
		upClassDao.deleteUpClasses(groupId);
	}
	
	public void insertUpClass(String upClassName) {
		upClassDao.insertUpClass(upClassName);
	}
	
	public UpClassEntity getUpClassEntityByUpClassID(int upClassID) {
		return upClassDao.getUpClassEntityByUpClassID(upClassID);
	}
	
	public UpClassEntity getUpClassEntityByUpClassName(String upClassName) {
		return upClassDao.getUpClassEntityByUpClassName(upClassName);
	}
	
	public void updateUpClass(int upClassID,String newUpClassName) {
		upClassDao.updateUpClass(upClassID,newUpClassName);
	}
}
