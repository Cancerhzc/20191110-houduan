package com.youareright.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.PhotoMergeDao;
import com.youareright.model.sys.PhotoMergeEntity;
import com.youareright.service.sys.PhotoMergeService;;


@Service(value = "photoMergeServiceImpl")
public class PhotoMergeSeriveImpl implements PhotoMergeService {

	@Autowired
	private PhotoMergeDao photoMergeDao;
	

	public void insertPhotoMerge(PhotoMergeEntity photoMergeEntity) {
		photoMergeDao.insertPhotoMerge(photoMergeEntity);
	}
	
	public void deleteMerges(List<String> groupId) {
		photoMergeDao.deleteMerges(groupId);
	}
	
	public void updateMerges(int mergeID,int state) {
		photoMergeDao.updateMerges(mergeID,state);
	}
	
	public Integer mergesSize(String searchCondition,int pageSize,int start) {
		return photoMergeDao.mergesSize(searchCondition,pageSize,start);
	}
	
	public ArrayList<PhotoMergeEntity> mergesList(String searchCondition,int pageSize, int start) {
		return photoMergeDao.mergesList(searchCondition,pageSize, start);
	}
	
	public String getMergeUrlByMergeID(int mergeID) {
		return photoMergeDao.getMergeUrlByMergeID(mergeID);
	}
}
