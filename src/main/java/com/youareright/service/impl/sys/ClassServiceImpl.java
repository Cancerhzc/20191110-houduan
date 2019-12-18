package com.youareright.service.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.ClassDao;
import com.youareright.model.sys.ClassEntity;
import com.youareright.service.sys.ClassService;

@Service(value = "classServiceImpl")
public class ClassServiceImpl implements ClassService {

	@Autowired
	private ClassDao classDao;
	
	public void insertClass(ClassEntity classEntity) {
		classDao.insertClass(classEntity);
	}
	public void insertClass2(String goodsClass,String goodsName) {
		classDao.insertClass2(goodsClass,goodsName);
	}

	public void deleteClasses(List<String> className) {
		classDao.deleteClasses(className);
	}
	
	public void del(int classID) {
		classDao.del(classID);
	}

	public void updateClass(ClassEntity classEntity) {
		classDao.updateClass(classEntity);
	}

	public List<ClassEntity> classesList(String searchCondition, int pageSize, int start) {
		return classDao.classesList(searchCondition, pageSize,  start);
	}

	public Integer classesSize(String searchCondition, int pageSize, int start) {
		return classDao.classesSize(searchCondition, pageSize, start);
	}
	
	public Integer checkClassIsExisted(String labelName) {
		return classDao.checkClassIsExisted(labelName);
	}

	public Integer maxClassID() {
		return classDao.maxClassID();
	}
	
	public Integer getClassID(String labelName) {
		return classDao.getClassID(labelName);
	}

	public String getGoodsNameByClassID(int classID) {
		return classDao.getGoodsNameByClassID(classID);
	}
	
	public String getClassNameByClassID(int classID) {
		return classDao.getClassNameByClassID(classID);
	}
	
	public void modifyClass(int classID,String newClassName,String newGoodsName) {
		classDao.modifyClass(classID,newClassName,newGoodsName);
	}


}
