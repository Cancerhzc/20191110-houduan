package com.youareright.service.sys;

import java.util.ArrayList;


import com.youareright.model.sys.SourceDownloadEntity;

public interface SourceDownloadService {
	public Integer sourceListSize(String searchCondition,int pageSize,int start);
	
	public ArrayList<SourceDownloadEntity> sourceList(String searchCondition,int pageSize,int start);
}
