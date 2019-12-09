package com.youareright.controller.sys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.ShelfEntity;
import com.youareright.service.sys.ShelfService;
import com.youareright.utils.FileProcess;

@RestController
public class ShelfController {
	

	private Logger log = LoggerFactory.getLogger(ShelfController.class);
	
	
	@Resource(name = "shelfServiceImpl")
	private ShelfService shelfService;
	
	private FileProcess fileProcess=new FileProcess();
	
	private PathController pathController=new PathController();
	String absolutePath=pathController.getPath();
	
	/**
	 * 新建货架商品
	 * 
	 * @param shelfEntity
	 * @param 
	 * @return
	 */
	@PostMapping("/shelves/shelf")
	public List<String> insertShelf(@RequestPart("formData") ShelfEntity shelfEntity,@RequestPart("file") MultipartFile [] multipartFiles) {
	    List<String> resultList = new ArrayList<String>();
	    int shelfClassID=shelfEntity.getShelfClassID();
	    System.out.println(shelfClassID);
	    String shelfClassIDString = Integer.toString(shelfClassID);
	    int thisShelfID=shelfService.maxShelfID()+1;
	    for (MultipartFile multipartFile : multipartFiles) {   //遍历两个文件，看哪个是.xml，哪个是图片
	    	String originalFilename = multipartFile.getOriginalFilename();
		    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		    if(suffix.equals(".xml")) {
		    	String url = getFile(multipartFile,shelfClassIDString,thisShelfID);
		    	shelfEntity.setXMLPath(url);
		    }
	    }
	    for (MultipartFile multipartFile : multipartFiles) {
	    	String originalFilename = multipartFile.getOriginalFilename();
		    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		    if(!suffix.equals(".xml")) {
		    	String url = getFile(multipartFile,shelfClassIDString,thisShelfID);
		    	resultList.add(url);
		    	shelfEntity.setShelfPath(url);
		    }
	    }
	    shelfService.insertShelf(shelfEntity);
	    log.debug("The method is ending");
	    return resultList;
		


	}
	@PutMapping("/shelf/{id}")
	public ShelfEntity updateShelf(@RequestBody ShelfEntity shelfEntity, @PathVariable int id) {
		if (shelfEntity.getShelfID() == id) {
			shelfService.updateShelf(shelfEntity);
		}
		log.debug("The method is ending");
		return shelfEntity;
	}
	
	public ShelfEntity updateShelf2(ShelfEntity shelfEntity, int id) {   //该函数不是从前端获取变量
		if (shelfEntity.getShelfID() == id) {
			shelfService.updateShelf(shelfEntity);
		}
		log.debug("The method is ending");
		return shelfEntity;
	}
	
	public Integer getClassIDByShelfID(int id) {
		return shelfService.getClassIDByShelfID(id);
	}
	
	
	
	/**
	 * 删除货架图片信息
	 * 
	 * @param groupId
	 * @return
	 */
	@DeleteMapping("/shelves")
	public List<String> deleteShelves(@RequestBody List<String> groupId) {
		System.out.println(groupId);
		int groupIDSize = groupId.size();
		System.out.println(groupIDSize);
		if(groupId != null && groupIDSize != 0) {
			for(int i=0;i<groupIDSize;i++) {
				String currentShelfIDString=groupId.get(i);
				System.out.println(currentShelfIDString);
				int currentShelfID=Integer.valueOf(currentShelfIDString);
				System.out.println(currentShelfID);
				int currentClassID=getClassIDByShelfID(currentShelfID);          //数据库查询，通过shelfID得到classID
				System.out.println(currentClassID);
				String currentClassIDString=Integer.toString(currentClassID);
				String srcSuffix=getSrcSuffix(currentShelfID);
				System.out.println(srcSuffix);
				String filePath=absolutePath+"/src/images/shelf/"+currentClassIDString+"/"+currentShelfIDString+srcSuffix;
				fileProcess.deleteFile(filePath);
			}
		}
		System.out.println("123123123213");
		shelfService.deleteShelfes(groupId);
		return groupId;
		
	}
	
	/**
	 * 获取shelf表数据
	 * 
	 * @param shelfFilename
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GetMapping("/shelf")
	public PageResult shelvesList(String searchCondition, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		pageResult.setData(shelfService.shelvesList(searchCondition, pageSize, page * pageSize));
		pageResult.setTotalCount(shelfService.shelvesSize(searchCondition, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}
	
	
	
	private String getFile(MultipartFile file,String dirName,int newID) {
	    String originalFilename = file.getOriginalFilename();
	    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
	    String newIDString = Integer.toString(newID); 
	    //s = s.replaceAll("-", "");
	    String newName = newIDString + suffix;
	    String url = "/src/images/shelf/"+dirName+"/"+newName;
	    String parentPath = absolutePath+"/src/images/shelf/"+dirName;
	    File dest = new File(parentPath, newName);
	    try {
	        //目录不存在则创建，依赖google的guava工具包
	        Files.createParentDirs(dest);
	        file.transferTo(dest);
	        return url;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	

	public String getSrcSuffix(int shelfID) {   //通过shelfID获取后缀（.jpg .png之类的）
		String temp= shelfService.getSrc(shelfID); 
		String suffix=temp.substring(temp.lastIndexOf("."));
		return suffix;
	}
		

}
