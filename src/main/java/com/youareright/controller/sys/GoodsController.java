package com.youareright.controller.sys;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.UserEntity;
import com.google.common.io.Files;
import com.youareright.model.sys.GoodsEntity;
import com.youareright.service.sys.ClassService;
import com.youareright.service.sys.GoodsService;
import com.youareright.service.sys.UserService;
import com.youareright.utils.FileProcess;
import java.io.File;

import java.io.IOException;

class Mark{
	String goodsClass;
	String goodsFilename;
	String currentLoginName;
	List<String> groupId;
	
	public String getGoodsClass() {
		return goodsClass;
	}
	public void setGoodsClass(String goodsClass) {
		this.goodsClass = goodsClass;
	}
	public String getGoodsFilename() {
		return goodsFilename;
	}
	public void setGoodsFilename(String goodsFilename) {
		this.goodsFilename = goodsFilename;
	}
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
	public List<String> getGroupId() {
		return groupId;
	}
	public void setGroupId(List<String> groupId) {
		this.groupId = groupId;
	}	
}
class ReturnGoodsList {
    private int goodsID;
    private String goodsClass;
    private String goodsPath;
    private String goodsFilename;
    private int goodsState;
    private String uploadUsername;
    private String markUsername;
	public int getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsClass() {
		return goodsClass;
	}
	public void setGoodsClass(String goodsClass) {
		this.goodsClass = goodsClass;
	}
	public String getGoodsPath() {
		return goodsPath;
	}
	public void setGoodsPath(String goodsPath) {
		this.goodsPath = goodsPath;
	}
	public String getGoodsFilename() {
		return goodsFilename;
	}
	public void setGoodsFilename(String goodsFilename) {
		this.goodsFilename = goodsFilename;
	}
	public int getGoodsState() {
		return goodsState;
	}
	public void setGoodsState(int goodsState) {
		this.goodsState = goodsState;
	}
	public String getUploadUsername() {
		return uploadUsername;
	}
	public void setUploadUsername(String uploadUsername) {
		this.uploadUsername = uploadUsername;
	}
	public String getMarkUsername() {
		return markUsername;
	}
	public void setMarkUsername(String markUsername) {
		this.markUsername = markUsername;
	}

}

@RestController
public class GoodsController {
	
	private Logger log = LoggerFactory.getLogger(GoodsController.class);

	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "classServiceImpl")
	private ClassService classService;
	
	private FileProcess fileProcess=new FileProcess();
	
	private PathController pathController=new PathController();
	String absolutePath=pathController.getPath();
	/**
	 * 新建商品
	 * 
	 * @param goodsEntity
	 * @param 
	 * @return
	 */
	@PostMapping("/goods/good")
	public String insertGoods(@RequestPart("formData") GoodsEntity goodsEntity,@RequestPart("loginname") String loginname,@RequestPart("file") MultipartFile [] multipartFiles) {
		UserEntity currentUser=userService.getUserEntityByLoginName(loginname);
		int currentUserID=currentUser.getId();
		int thisClassID;
		String labelName=goodsEntity.getGoodsClass();
		String goodsName=goodsEntity.getGoodsFilename();
		String classIDToString=new String();
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
		}
		if(isLabeled==true) {
			goodsEntity.setMarkUserID(currentUserID);
			thisClassID=classService.getClassID(labelName);            //通过goodsClass得到classID
			String existGoodsName=classService.getGoodsNameByClassID(thisClassID); 
			if(existGoodsName.equals(goodsName)) {
				classIDToString = Integer.toString(thisClassID);
				goodsEntity.setClassID(thisClassID);
				
			}
			else {
				return existGoodsName;  //如果输入的标签名在数据库中已经存在，但商品名不一致的话，需要返回数据库已有的商品名
			}	
		}
		goodsEntity.setGoodsState(0);
		goodsEntity.setUploadUser(currentUserID);
	    for (MultipartFile multipartFile : multipartFiles) {
	    	int thisGoodsID=goodsService.maxGoodsID()+1;
	        String url = getFile(multipartFile,classIDToString,thisGoodsID);
			goodsEntity.setGoodsPath(url);
			goodsService.insertGoods(goodsEntity);
	    }
	    log.debug("The method is ending");
	    return "@Picture(s) Upload Successfully!@";
	}
	
	
	@PutMapping("/goods/{id}")
	public GoodsEntity updateGoods(@RequestBody GoodsEntity goodsEntity, @PathVariable int id) {
		if (goodsEntity.getGoodsID() == id) {
			goodsService.updateGoods(goodsEntity);
		}
		log.debug("The method is ending");
		return goodsEntity;
	}
	
	public GoodsEntity updateGoods2(GoodsEntity goodsEntity, int id) {   //该函数不是从前端获取变量
		if (goodsEntity.getGoodsID() == id) {
			goodsService.updateGoods(goodsEntity);
		}
		log.debug("The method is ending");
		return goodsEntity;
	}
	
	public Integer getClassIDByGoodsID(int id) {
		return goodsService.getClassIDByGoodsID(id);
	}
	
	
	
	/**
	 * 删除图片信息
	 * 
	 * @param groupId
	 * @return
	 */
	@DeleteMapping("/goodses")
	public List<String> deleteGoodses(@RequestBody List<String> groupId) {
		int groupIDSize = groupId.size();
		if(groupId != null && groupIDSize != 0) {
			for(int i=0;i<groupIDSize;i++) {
				String currentGoodsIDString=groupId.get(i);
				int currentGoodsID=Integer.valueOf(currentGoodsIDString);
				int currentClassID=getClassIDByGoodsID(currentGoodsID);          //数据库查询，通过goodsID得到classID
				String currentClassIDString=Integer.toString(currentClassID);
				String srcSuffix=getSrcSuffix(currentGoodsID);
				if(currentClassID==-1) {            //如果是未打标，则在“未打标商品”文件夹下
					currentClassIDString="未打标商品";
				}
				String filePath=absolutePath+"/myimages/"+currentClassIDString+"/"+currentGoodsIDString+srcSuffix;
				fileProcess.deleteFile(filePath);
			}
		}
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
		List<GoodsEntity> goodsesList=goodsService.goodsesList(searchCondition, pageSize, page * pageSize);
		List<ReturnGoodsList> returnGoodesList=new ArrayList<ReturnGoodsList>();
		int goodsesListLength=goodsesList.size();
		for(int i=0;i<goodsesListLength;i++) {
		    int goodsID=goodsesList.get(i).getGoodsID();
		    String goodsClass=goodsesList.get(i).getGoodsClass();
		    String goodsPath=goodsesList.get(i).getGoodsPath();
		    String goodsFilename=goodsesList.get(i).getGoodsFilename();
		    int goodsState=goodsesList.get(i).getGoodsState();
		    int uploadUser=goodsesList.get(i).getUploadUser();
		    int markUserID=goodsesList.get(i).getMarkUserID();
		    String uploadUsername=userService.getUsernameByUserID(uploadUser);
		    String markUsername=userService.getUsernameByUserID(markUserID);
		    ReturnGoodsList temp=new ReturnGoodsList();
		    temp.setGoodsID(goodsID);
		    temp.setGoodsClass(goodsClass);
		    temp.setGoodsPath(goodsPath);
		    temp.setGoodsFilename(goodsFilename);
		    temp.setGoodsState(goodsState);
		    temp.setUploadUsername(uploadUsername);
		    temp.setMarkUsername(markUsername);
		    returnGoodesList.add(temp);
		}
		pageResult.setData(returnGoodesList);
		pageResult.setTotalCount(goodsService.goodsesSize(searchCondition, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}
	
	@PostMapping("/goods/modify")
	public String markGoods(@RequestBody Mark goodsModify) {
		GoodsEntity goodsTemp=new GoodsEntity();
		int thisClassID=0;
		String classIDToString=new String();
		String labelName=goodsModify.getGoodsClass();
		String goodsName=goodsModify.getGoodsFilename();
		
		UserEntity currentUser=userService.getUserEntityByLoginName(goodsModify.getCurrentLoginName());
		int currentUserID=currentUser.getId();
		
		if(classService.checkClassIsExisted(labelName)==0) {
			classService.insertClass2(labelName,goodsName);		//如果goodsClass中无此标签名，就在goodsClass类中增加
		}
		thisClassID=classService.getClassID(labelName);            //通过goodsClass得到classID
		String existGoodsName=classService.getGoodsNameByClassID(thisClassID); 
		if(existGoodsName.equals(goodsName)) {
			classIDToString = Integer.toString(thisClassID);
			goodsTemp.setClassID(thisClassID);
			goodsTemp.setMarkUserID(currentUserID);
			goodsTemp.setGoodsState(1);
			List<String> currentListOfGoodsID=goodsModify.getGroupId();
			for(int i=0;i<currentListOfGoodsID.size();i++) {		//对标记的每个图片进行操作
				String currentGoodsIDString=currentListOfGoodsID.get(i);
				int currentGoodsID=Integer.valueOf(currentGoodsIDString);   	//得到当前图片的ID
				int currentClassID=getClassIDByGoodsID(currentGoodsID);          //数据库查询，通过goodsID得到classID
				String currentClassString=new String();
				if(currentClassID==-1) {
					currentClassString="未打标商品";
				}
				else {
					currentClassString=Integer.toString(currentClassID);
				}
				
				String srcFilePath=absolutePath+"/myimages/"+currentClassString+"/"+currentGoodsIDString+getSrcSuffix(currentGoodsID);
				String dstPath=absolutePath+"/myimages/"+classIDToString;
				String goodsPath="/myimages/"+classIDToString+"/"+currentGoodsIDString+getSrcSuffix(currentGoodsID);
				fileProcess.moveFile(srcFilePath,dstPath);
				goodsTemp.setGoodsPath(goodsPath);
				goodsTemp.setGoodsID(currentGoodsID);
				updateGoods2(goodsTemp, currentGoodsID);
			}
			return "@Mark Goods Successfully!@";
		}
		else {
			return existGoodsName;
		}
	}
	
	private String getFile(MultipartFile file,String classIDString,int newID) {
		if(classIDString.equals("")) {
			classIDString="未打标商品";
		}
		
	    String originalFilename = file.getOriginalFilename();
	    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
	    String newIDString = Integer.toString(newID); 
	    //s = s.replaceAll("-", "");
	    String newName = newIDString + suffix;
	    String url = "/myimages/"+classIDString+"/"+newName;
	    String parentPath = absolutePath+"/myimages/"+classIDString;
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
	

	public String getSrcSuffix(int goodsID) {   //通过goodsID获取后缀（.jpg .png之类的）
		String temp= goodsService.getSrc(goodsID); 
		String suffix=temp.substring(temp.lastIndexOf("."));
		return suffix;
	}
	
}

