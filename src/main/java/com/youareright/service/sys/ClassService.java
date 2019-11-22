package com.youareright.service.sys;

import java.util.List;

import com.youareright.model.sys.ClassEntity;

public interface ClassService {
	
	public void insertClass(ClassEntity classEntity);
	
	public void insertClass2(String goodsClass,String goodsName);
	
	public void updateClass(ClassEntity classEntity);
	
	public void deleteClasses(List<String> className);
	
	public void del(ClassEntity classEntity);
	
	public List<ClassEntity> classesList(String searchCondition, int pageSize, int start) ;

	public Integer classesSize(String searchCondition, int pageSize, int start);
	
	public Integer checkClassIsExisted(String labelName);
	
	public Integer maxClassID();
	
	public Integer getClassID(String labelName);

}
