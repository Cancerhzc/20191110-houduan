package com.youareright.controller.sys;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
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
import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.UserEntity;
import com.google.common.io.Files;
import com.youareright.model.sys.GoodsEntity;
import com.youareright.service.sys.ClassService;
import com.youareright.service.sys.GoodsService;
import com.youareright.service.sys.UserService;
import java.io.File;

import java.io.IOException;

@RestController
public class GoodsController {
	
	private Logger log = LoggerFactory.getLogger(GoodsController.class);

	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "classServiceImpl")
	private ClassService classService;
	/**
	 * 新建商品
	 * 
	 * @param goodsEntity
	 * @param 
	 * @return
	 */
	@PostMapping("/goods/good")
	public List<String> insertGoods(@RequestPart("formData") GoodsEntity goodsEntity,@RequestPart("loginname") String loginname,@RequestPart("file") MultipartFile [] multipartFiles) {
	    //System.err.println(multipartFiles);
		UserEntity currentUser=userService.getUserEntityByLoginName(loginname);
		int currentUserID=currentUser.getId();
		String labelName=goodsEntity.getGoodsClass();
		String goodsName=goodsEntity.getGoodsFilename();
		Boolean isLabeled=true;
		if(labelName==null||goodsName==null)
		{
			labelName="未打标商品";
			goodsName="未打标商品";
			isLabeled=false;
		}
		else if(labelName.equals("")||goodsName.equals("")) {
			labelName="未打标商品";
			goodsName="未打标商品";
			isLabeled=false;
		}
		else if(classService.checkClassIsExisted(labelName)==0) {
			classService.insertClass2(labelName,goodsName);		//如果goodsClass中无此标签名，就在goodsClass类中增加
			int thisClassID=classService.maxClassID();
			labelName = Integer.toString(thisClassID);
		}
		
		if(isLabeled==true) {
			goodsEntity.setMarkUserID(currentUserID);
		}
		
		goodsEntity.setGoodsState(0);
		goodsEntity.setUploadUser(currentUserID);
	    List<String> resultList = new ArrayList<>();
	    for (MultipartFile multipartFile : multipartFiles) {
	    	int thisGoodsID=goodsService.maxGoodsID()+1;
	        String url = copyFile(multipartFile,labelName,thisGoodsID);
	        resultList.add(url);
			goodsEntity.setGoodsPath(url);
			goodsService.insertGoods(goodsEntity);
	    }
	    log.debug("The method is ending");
	    

	    return resultList;
		

		//return goodsEntity;
	}
	@PutMapping("/goods/{id}")
	public GoodsEntity updateGoods(@RequestBody GoodsEntity goodsEntity, @PathVariable int id) {
		if (goodsEntity.getGoodsID() == id) {
			goodsService.updateGoods(goodsEntity);
		}
		log.debug("The method is ending");
		return goodsEntity;
	}
	
	
	/**
	 * 删除图片信息
	 * 
	 * @param groupId
	 * @return
	 */
	@DeleteMapping("/goodses")
	public List<String> deleteGoodses(@RequestBody List<String> groupId) {
		goodsService.deleteGoodses(groupId);
		return groupId;
	}
	
	/**
	 * 获取goods表数据
	 * 
	 * @param goodsFilename
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GetMapping("/goods")
	public PageResult goodsesList(String searchCondition, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		pageResult.setData(goodsService.goodsesList(searchCondition, pageSize, page * pageSize));
		pageResult.setTotalCount(goodsService.goodsesSize(searchCondition, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}
	
	
	private String copyFile(MultipartFile file,String labelName,int newID) {
	    String originalFilename = file.getOriginalFilename();
	    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
	    String newIDString = Integer.toString(newID); 
	    //s = s.replaceAll("-", "");
	    String newName = newIDString + suffix;
	    String url = "/src/images/"+labelName+"/"+newName;
	    String parentPath = "G:/git/wh-web/src/images/"+labelName;
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

	

}

