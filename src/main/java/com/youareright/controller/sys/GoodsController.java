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
import com.youareright.service.sys.OperationService;
import com.youareright.service.sys.PathService;
import com.youareright.service.sys.UserService;
import com.youareright.utils.FileProcess;
import com.youareright.utils.TimeProcess;

import java.io.File;

import java.io.IOException;

class Mark{
	String goodsClass;
	String goodsFilename;
	String currentLoginName;
	List<Integer> groupId;
	
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
	public List<Integer> getGroupId() {
		return groupId;
	}
	public void setGroupId(List<Integer> groupId) {
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

class VerifyData { //审核图片
	String currentLoginName;
	int state;
	List<Integer> groupId;
	
	
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public List<Integer> getGroupId() {
		return groupId;
	}
	public void setGroupId(List<Integer> groupId) {
		this.groupId = groupId;
	}
}

class DeleteGoodsInfo {
	List<Integer> groupId;
	String currentLoginName;
	public List<Integer> getGroupId() {
		return groupId;
	}
	public void setGroupId(List<Integer> groupId) {
		this.groupId = groupId;
	}
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
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
	
	@Resource(name = "pathServiceImpl")
	private PathService pathService;
	
	@Resource(name = "operationServiceImpl")
	private OperationService operationService;
	
	private FileProcess fileProcess=new FileProcess();
	
	private TimeProcess timeProcess=new TimeProcess();
	

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
		int countUploadPicture=0;
	    for (MultipartFile multipartFile : multipartFiles) {
	    	int thisGoodsID=goodsService.maxGoodsID()+1;
	        String url = getFile(multipartFile,classIDToString,thisGoodsID);
			goodsEntity.setGoodsPath(url);
			goodsService.insertGoods(goodsEntity);
			countUploadPicture++;
	    }
	    
	    //日志
	    String operationString="上传了"+Integer.toString(countUploadPicture)+"张图片";
	    String operationTime=timeProcess.nowTime().get(0);
	    operationService.insertOperation(currentUserID, operationString, operationTime);
	    
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
	public List<Integer> deleteGoodses(@RequestBody DeleteGoodsInfo deleteGoodsInfo) {
		List<Integer> groupId=deleteGoodsInfo.getGroupId();
		String currentLoginName=deleteGoodsInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		String absolutePath=pathService.runningPath().getPath();
		int groupIDSize = groupId.size();
		System.out.println(currentLoginName);
		if(groupId != null && groupIDSize != 0) {
			for(int i=0;i<groupIDSize;i++) {
				int currentGoodsID=groupId.get(i);
				String currentGoodsIDString=Integer.toString(currentGoodsID);
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
		
		//日志
		String operationString="删除了"+Integer.toString(groupIDSize)+"张图片";
		String operationTime=timeProcess.nowTime().get(0);
		operationService.insertOperation(currentUserID, operationString, operationTime);
		return groupId;	
	}
	
	/**
	 * 获取goods表数据-图像审核通过 且标签审核通过
	 * 
	 * @param goodsFilename
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GetMapping("/goods/13")
	public PageResult goodsesList13(String searchCondition, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		List<GoodsEntity> goodsesList=goodsService.goodsesList13(searchCondition, pageSize, page * pageSize);
		List<ReturnGoodsList> returnGoodesList=new ArrayList<ReturnGoodsList>();
		int goodsesListLength=goodsesList.size();
		for(int i=0;i<goodsesListLength;i++) {
		    int goodsID=goodsesList.get(i).getGoodsID();
		    String goodsClass=goodsesList.get(i).getGoodsClass();
		    String goodsPath=goodsesList.get(i).getGoodsPath();
		    String goodsFilename=goodsesList.get(i).getGoodsFilename();
		    String markUsername=new String();
		    int goodsState=goodsesList.get(i).getGoodsState();
		    int uploadUser=goodsesList.get(i).getUploadUser();
		    if(goodsesList.get(i).getGoodsClass()!=null) {
		    	int markUserID=goodsesList.get(i).getMarkUserID();
		    	markUsername=userService.getUsernameByUserID(markUserID);
		    }
		    else 
		    {
		    	markUsername="[未打标]";
		    }
	    	String uploadUsername=userService.getUsernameByUserID(uploadUser);
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
		pageResult.setTotalCount(goodsService.goodsesSize13(searchCondition, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}
	
	/**
	 * 获取goods表数据-图像审核通过但标签审核未通过  或者图像待审核
	 * 
	 * @param goodsFilename
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GetMapping("/goods/02")
	public PageResult goodsesList02(String searchCondition, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		List<GoodsEntity> goodsesList=goodsService.goodsesList02(searchCondition, pageSize, page * pageSize);
		List<ReturnGoodsList> returnGoodesList=new ArrayList<ReturnGoodsList>();
		int goodsesListLength=goodsesList.size();
		for(int i=0;i<goodsesListLength;i++) {
		    int goodsID=goodsesList.get(i).getGoodsID();
		    String goodsClass=goodsesList.get(i).getGoodsClass();
		    String goodsPath=goodsesList.get(i).getGoodsPath();
		    String goodsFilename=goodsesList.get(i).getGoodsFilename();
		    String markUsername=new String();
		    int goodsState=goodsesList.get(i).getGoodsState();
		    int uploadUser=goodsesList.get(i).getUploadUser();
		    if(goodsesList.get(i).getGoodsClass()!=null) {
		    	int markUserID=goodsesList.get(i).getMarkUserID();
		    	markUsername=userService.getUsernameByUserID(markUserID);
		    }
		    else 
		    {
		    	markUsername="[未打标]";
		    }
	    	String uploadUsername=userService.getUsernameByUserID(uploadUser);
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
		pageResult.setTotalCount(goodsService.goodsesSize02(searchCondition, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}
	
	@PostMapping("/goods/modify")
	public String markGoods(@RequestBody Mark goodsModify) {
		String absolutePath=pathService.runningPath().getPath();
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
			goodsTemp.setGoodsState(2);
			List<Integer> currentListOfGoodsID=goodsModify.getGroupId();
			for(int i=0;i<currentListOfGoodsID.size();i++) {		//对标记的每个图片进行操作
				
				int currentGoodsID=currentListOfGoodsID.get(i);
				String currentGoodsIDString=Integer.toString(currentGoodsID);   	//得到当前图片的ID
				int currentClassID=getClassIDByGoodsID(currentGoodsID);          //数据库查询，通过goodsID得到classID
				String currentClassString=new String();
				if(currentClassID==-1) {
					currentClassString="未打标商品";
				}
				else {
					currentClassString=Integer.toString(currentClassID);
				}
				
				String srcFilePath=absolutePath+"/myimages/"+currentClassString+"/"+currentGoodsIDString+getSrcSuffix(currentGoodsID);
				String dstPath=absolutePath+"/myimages/waitcheck/"+classIDToString;
				String goodsPath="/myimages/waitcheck/"+classIDToString+"/"+currentGoodsIDString+getSrcSuffix(currentGoodsID);
				fileProcess.moveFile(srcFilePath,dstPath);
				goodsTemp.setGoodsPath(goodsPath);
				goodsTemp.setGoodsID(currentGoodsID);
				updateGoods2(goodsTemp, currentGoodsID);
			}
			
			
			//日志
			String operationString="打标了"+Integer.toString(currentListOfGoodsID.size())+"张图片";
			String operationTime=timeProcess.nowTime().get(0);
			operationService.insertOperation(currentUserID, operationString, operationTime);
			return "@Mark Goods Successfully!@";
		}
		else {
			return existGoodsName;
		}
	}
	
	@PostMapping("/goods/verify")
	public void verifyGoods(@RequestBody VerifyData verifyData) {
		String currentUserName=verifyData.getCurrentLoginName();
		int currentUserID=userService.getUserEntityByLoginName(currentUserName).getId();
		String absolutePath=pathService.runningPath().getPath();
		System.out.println(verifyData.getState());
		System.out.println(verifyData.getGroupId());
		int state=verifyData.getState();
		List<Integer> groupId=verifyData.getGroupId();
		int lengthOfGroupId=groupId.size();
		
		if(state==3) {
			for(int i=0;i<lengthOfGroupId;i++) {
				GoodsEntity tempGoodsEntity=goodsService.getGoodsEntityByGoodsID(groupId.get(i));
				String oldPath=tempGoodsEntity.getGoodsPath();
				String oldSrcPath=absolutePath+tempGoodsEntity.getGoodsPath();
				String newGoodsPath="/myimages"+oldPath.substring(oldPath.indexOf("waitcheck")+9);
				String dstPath=absolutePath+newGoodsPath.substring(0,newGoodsPath.lastIndexOf("/"));
//				System.out.println(oldPath);
//				System.out.println(oldSrcPath);
//				System.out.println(newGoodsPath);
//				System.out.println(dstPath);
				int fileMoveState=fileProcess.moveFile(oldSrcPath, dstPath);
				if(fileMoveState==0) {
					tempGoodsEntity.setGoodsPath(newGoodsPath);
					tempGoodsEntity.setGoodsState(state);
					goodsService.updateGoods(tempGoodsEntity);
				}
			}
			//日志
			String operationString="审核了"+Integer.toString(lengthOfGroupId)+"张图片[图像和标签均通过]";
			String operationTime=timeProcess.nowTime().get(0);
			operationService.insertOperation(currentUserID, operationString, operationTime);
		}
		else if(state==1) {
			for(int i=0;i<lengthOfGroupId;i++) {
				GoodsEntity tempGoodsEntity=goodsService.getGoodsEntityByGoodsID(groupId.get(i));
				String oldPath=tempGoodsEntity.getGoodsPath();
				String oldSrcPath=absolutePath+tempGoodsEntity.getGoodsPath();
				String newGoodsPath="/myimages/未打标商品"+oldPath.substring(oldPath.lastIndexOf("/"));
				String dstPath=absolutePath+newGoodsPath.substring(0,newGoodsPath.lastIndexOf("/"));
//				System.out.println(oldPath);
//				System.out.println(oldSrcPath);
//				System.out.println(newGoodsPath);
//				System.out.println(dstPath);
				tempGoodsEntity.setClassID(null);
				int fileMoveState=fileProcess.moveFile(oldSrcPath, dstPath);
				if(fileMoveState==0) {
					tempGoodsEntity.setGoodsPath(newGoodsPath);
					tempGoodsEntity.setGoodsState(state);
					goodsService.updateGoods(tempGoodsEntity);
				}
				
				//日志
				String operationString="审核了"+Integer.toString(lengthOfGroupId)+"张图片[图片通过，但标签不通过]";
				String operationTime=timeProcess.nowTime().get(0);
				operationService.insertOperation(currentUserID, operationString, operationTime);
			}
		}
		else if(state==4) {
			for(int i=0;i<lengthOfGroupId;i++) {
				GoodsEntity tempGoodsEntity=goodsService.getGoodsEntityByGoodsID(groupId.get(i));
				String filePath=absolutePath+tempGoodsEntity.getGoodsPath();
				fileProcess.deleteFile(filePath);
				String dirPath=filePath.substring(0,filePath.lastIndexOf("/"));
				File file=new File(dirPath);
				File[] fileList=file.listFiles();
				int countFile=0;
				for(File fileIndex:fileList){
					countFile++;
				}
				if(countFile==0) {
					fileProcess.deleteFile(dirPath);
				}
			}
			goodsService.deleteGoodses(groupId);
			//日志
			String operationString="审核了"+Integer.toString(lengthOfGroupId)+"张图片[图像、标签均不通过]";
			String operationTime=timeProcess.nowTime().get(0);
			operationService.insertOperation(currentUserID, operationString, operationTime);
		}
	}
	
	private String getFile(MultipartFile file,String classIDString,int newID) {
		String absolutePath=pathService.runningPath().getPath();
		if(classIDString.equals("")) {
			classIDString="未打标商品";
		}
		
	    String originalFilename = file.getOriginalFilename();
	    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
	    String newIDString = Integer.toString(newID); 
	    //s = s.replaceAll("-", "");
	    String newName = newIDString + suffix;
	    String url = "/myimages/waitcheck/"+classIDString+"/"+newName;
	    String parentPath = absolutePath+"/myimages/waitcheck/"+classIDString;
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

