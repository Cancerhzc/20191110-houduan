package com.youareright.controller.sys;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.model.sys.PageResult;
import com.youareright.service.sys.SourceDownloadService;


@RestController
public class SourceDownloadController {
	
	@Resource(name = "sourceDownloadServiceImpl")
	private SourceDownloadService sourceDownloadService;
	
	@GetMapping("/sourceDownload")
	public PageResult sourceDownloadList(String searchCondition, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		pageResult.setData(sourceDownloadService.sourceList(searchCondition, pageSize, page * pageSize));
		pageResult.setTotalCount(sourceDownloadService.sourceListSize(searchCondition, pageSize, page * pageSize));
		return pageResult;
	}
	

}
