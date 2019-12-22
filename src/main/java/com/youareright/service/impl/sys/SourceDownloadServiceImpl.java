package com.youareright.service.impl.sys;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.SourceDownloadDao;
import com.youareright.model.sys.SourceDownloadEntity;
import com.youareright.service.sys.SourceDownloadService;


@Service(value = "sourceDownloadServiceImpl")
public class SourceDownloadServiceImpl implements SourceDownloadService {
		
	@Autowired 
	SourceDownloadDao sourceDownloadDao; 
		
	public Integer sourceListSize(String searchCondition,int pageSize,int start) {
		return sourceDownloadDao.sourceListSize(searchCondition,pageSize,start);
	}
	
	public ArrayList<SourceDownloadEntity> sourceList(String searchCondition,int pageSize,int start) {
		return sourceDownloadDao.sourceList(searchCondition,pageSize,start);
	}
}
