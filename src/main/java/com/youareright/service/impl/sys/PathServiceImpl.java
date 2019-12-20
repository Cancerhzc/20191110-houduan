package com.youareright.service.impl.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youareright.dao.PathDao;
import com.youareright.model.sys.PathEntity;
import com.youareright.service.sys.PathService;

@Service(value = "pathServiceImpl")
public class PathServiceImpl implements PathService {
	@Autowired
	private PathDao pathDao;
	
	public PathEntity runningPath() {
		return pathDao.runningPath();
	}
}
