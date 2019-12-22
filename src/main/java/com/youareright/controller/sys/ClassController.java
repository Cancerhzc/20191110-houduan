package com.youareright.controller.sys;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.UserEntity;
import com.youareright.model.sys.ClassEntity;
import com.youareright.service.sys.ClassService;
import com.youareright.service.sys.GoodsService;
import com.youareright.service.sys.OperationService;
import com.youareright.service.sys.PathService;
import com.youareright.service.sys.UserService;
import com.youareright.utils.FileProcess;
import com.youareright.utils.TimeProcess;

class DeleteClassInfo {
	List<String> groupId;
	String currentLoginName;
	public List<String> getGroupId() {
		return groupId;
	}
	public void setGroupId(List<String> groupId) {
		this.groupId = groupId;
	}
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
}

class UpdateClassInfo {
	ClassEntity classEntity;
	String currentLoginName;
	public ClassEntity getClassEntity() {
		return classEntity;
	}
	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
}

class GiveClassUpClassInfo {
	List<Integer> groupId;
	Integer upClassID;
	String currentLoginName;
	public List<Integer> getGroupId() {
		return groupId;
	}
	public void setGroupId(List<Integer> groupId) {
		this.groupId = groupId;
	}
	public Integer getUpClassID() {
		return upClassID;
	}
	public void setUpClassID(Integer upClassID) {
		this.upClassID = upClassID;
	}
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
	
}

@RestController
/*@PreAuthorize("hasRole('ADMI')")*/
public class ClassController {

//	ClassController() {
//		absolutePath=pathService.runningPath().getPath();
//	}
	private Logger log = LoggerFactory.getLogger(ClassController.class);

	@Resource(name = "classServiceImpl")
	private ClassService classService;
	
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "pathServiceImpl")
	private PathService pathService;
	
	@Resource(name = "operationServiceImpl")
	private OperationService operationService;
	
	private FileProcess fileProcess=new FileProcess();
	
	private TimeProcess timeProcess=new TimeProcess();

	/**
	 * 获取class表数据
	 * 
	 * @param goodsClass
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GetMapping("/classes")
	public PageResult classesList(String searchCondition, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		pageResult.setData(classService.classesList(searchCondition, pageSize, page * pageSize));
		pageResult.setTotalCount(classService.classesSize(searchCondition, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}
	
	@GetMapping("/classes/getTable")
	public PageResult classesListTable(String searchCondition, int pageSize, int page) {
		PageResult pageResult =classesList(searchCondition, pageSize, page);
		return pageResult;
	}
	
	/**
	 * 新建标签信息
	 * 
	 * @param classEntity
	 * @return
	 */
	@PostMapping("/classes/class")
	public ClassEntity insertClass(@RequestBody ClassEntity classEntity) {
		classService.insertClass(classEntity);
		log.debug("The method is ending");
		return classEntity;
	}

	/**
	 * 更新标签信息
	 * 
	 * @param classEntity
	 * @param id
	 * @return
	 */
	@PutMapping("/class/{id}")
	public String updateClass(@RequestBody UpdateClassInfo updateClassInfo) {	
		ClassEntity classEntity=updateClassInfo.getClassEntity();
		String currentLoginName=updateClassInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		String absolutePath=pathService.runningPath().getPath();
		String newClassName=classEntity.getGoodsClass();
		String newGoodsName=classEntity.getGoodsName();
		int selectClassID=classEntity.getClassID();
		if(classService.checkClassIsExisted(newClassName)==0) {
			classService.modifyClass(selectClassID,newClassName,newGoodsName);	//修改class类即可
			System.out.println("直接修改了class，无需修改goods");
		}
		else {
			int newClassID=classService.getClassID(newClassName);            //通过goodsClass得到classID
			String newClassIDToString=Integer.toString(newClassID);
			String existGoodsName=classService.getGoodsNameByClassID(newClassID); 
			System.out.println(existGoodsName);
			String goodsName=classEntity.getGoodsName();
			if(selectClassID==newClassID) {                //如果没修改label但修改了goodsName，则直接修改class表即可
				classService.modifyClass(selectClassID,newClassName,newGoodsName);	//修改class类即可
			}
			else if(goodsName.equals(existGoodsName)) {
				//先移动文件，再在sys_goods数据库中更改classID，然后再在goods_class中删去原来的class
				String selectClassIDString=Integer.toString(selectClassID);
				String srcDirPath=absolutePath+"/myimages/"+selectClassIDString;
				String dstPath=absolutePath+"/myimages/"+newClassIDToString;
				File file=new File(srcDirPath);
				File[] fileList=file.listFiles();
				for(File f:fileList) {
					String currentFileName=f.getName();
					String srcFilePath=srcDirPath+"/"+currentFileName;
					String src="/myimages/"+newClassIDToString+"/"+currentFileName;
					goodsService.modifyGoods(selectClassID, newClassID, src);
					fileProcess.moveFile(srcFilePath,dstPath);
				}
				fileProcess.deleteFile(absolutePath+"/myimages/"+selectClassIDString);//删除原文件夹
				classService.del(selectClassID);
			}
			else {
				return existGoodsName;//如果输入的标签名在数据库中已经存在，但商品名不一致的话，需要返回数据库已有的商品名
			}
		}	
		log.debug("The method is ending");
		//日志
		String operationString="修改了标签[标签ID："+Integer.toString(selectClassID)+"]";
		String operationTime=timeProcess.nowTime().get(0);
		operationService.insertOperation(currentUserID, operationString, operationTime);
		
		return "@Class Modifies Successfully!@";
	}

	/**
	 * 删除标签
	 * 
	 * @param groupId
	 * @return
	 */
	@DeleteMapping("/classes")
	public List<String> deleteClasses(@RequestBody DeleteClassInfo deleteClassInfo) {
		List<String> groupID=deleteClassInfo.getGroupId();
		String currentLoginName=deleteClassInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		String absolutePath=pathService.runningPath().getPath();
		int groupIDSize = groupID.size();
		if(groupID != null && groupIDSize != 0) {
			for(int i=0;i<groupIDSize;i++) {
				String currentClassIDString=groupID.get(i);
				String path1=absolutePath+"/myimages/"+currentClassIDString;
				fileProcess.deleteFile(path1);
				String path2=absolutePath+"/myimages/待审核/"+currentClassIDString;
				fileProcess.deleteFile(path2);
			}
		}
		classService.deleteClasses(groupID);
		
		//日志
		String operationString="删除了"+Integer.toString(groupIDSize)+"个标签";
		String operationTime=timeProcess.nowTime().get(0);
		operationService.insertOperation(currentUserID, operationString, operationTime);
		return groupID;
	}
	
	public Integer getClassID(String labelName) {
		return classService.getClassID(labelName);
	}
	
	@PostMapping("/giveUpClassID")
	public void giveUpClassID(@RequestBody GiveClassUpClassInfo giveClassUpClassInfo) {
		List<Integer> groupID=giveClassUpClassInfo.getGroupId();
		String currentLoginName=giveClassUpClassInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		List<Integer> groupId=giveClassUpClassInfo.getGroupId();
		Integer upClassID=giveClassUpClassInfo.getUpClassID();
		classService.giveUpClassID(groupId,upClassID);
		
		//日志
		String operationString=new String();
		if(upClassID!=null) {
			operationString="给"+Integer.toString(groupID.size())+"个标签赋予类别[类别ID："+Integer.toString(upClassID)+"]";
		}
		else {
			operationString="给"+Integer.toString(groupID.size())+"个标签解除设置类别";
		}
		String operationTime=timeProcess.nowTime().get(0);
		operationService.insertOperation(currentUserID, operationString, operationTime);
	}
	
}


