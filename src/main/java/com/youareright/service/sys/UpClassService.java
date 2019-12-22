package com.youareright.service.sys;

import java.util.List;

import com.youareright.model.sys.UpClassEntity;

public interface UpClassService {
	
	public List<UpClassEntity> upClassesList(String searchCondition, int pageSize, int start);

	public Integer upClassesSize(String searchCondition, int pageSize, int start);
	
	public void deleteUpClasses(List<Integer> groupId);
	
	public void insertUpClass(String upClassName);
	
	public UpClassEntity getUpClassEntityByUpClassID(int upClassID);
	
	public UpClassEntity getUpClassEntityByUpClassName(String upClassName);
}
