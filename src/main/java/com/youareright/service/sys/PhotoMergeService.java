package com.youareright.service.sys;

import java.util.ArrayList;
import java.util.List;

import com.youareright.model.sys.PhotoMergeEntity;

public interface PhotoMergeService {
	

	public void insertPhotoMerge(PhotoMergeEntity photoMergeEntity);
	
	public void deleteMerges(List<String> groupId);
	
	public void updateMerges(int mergeID);
	
	public Integer mergesSize(String searchCondition,int pageSize,int start);
	
	public ArrayList<PhotoMergeEntity> mergesList(String searchCondition,int pageSize, int start);
}
