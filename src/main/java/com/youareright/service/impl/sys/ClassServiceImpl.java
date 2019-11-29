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
	
	@Override
	public void insertClass(ClassEntity classEntity) {
		classDao.insertClass(classEntity);
	}
	public void insertClass2(String goodsClass,String goodsName) {
		classDao.insertClass2(goodsClass,goodsName);
	}

	@Override
	public void deleteClasses(List<String> className) {
		classDao.deleteClasses(className);
	}
	
	@Override
	public void del(int classID) {
		classDao.del(classID);
	}

	@Override
	public void updateClass(ClassEntity classEntity) {
		classDao.updateClass(classEntity);
	}

	@Override
	public List<ClassEntity> classesList(String searchCondition, int pageSize, int start) {
		return classDao.classesList(searchCondition, pageSize,  start);
	}

	@Override
	public Integer classesSize(String searchCondition, int pageSize, int start) {
		return classDao.classesSize(searchCondition, pageSize, start);
	}
	
	@Override
	public Integer checkClassIsExisted(String labelName) {
		return classDao.checkClassIsExisted(labelName);
	}
	
	@Override
	public Integer maxClassID() {
		return classDao.maxClassID();
	}
	
	@Override
	public Integer getClassID(String labelName) {
		return classDao.getClassID(labelName);
	}
	
	@Override
	public String getGoodsNameByClassID(int classID) {
		return classDao.getGoodsNameByClassID(classID);
	}
	
	@Override
	public void modifyClass(int classID,String newClassName,String newGoodsName) {
		classDao.modifyClass(classID,newClassName,newGoodsName);
	}


}
