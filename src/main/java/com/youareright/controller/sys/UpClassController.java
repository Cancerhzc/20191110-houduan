package com.youareright.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.UpClassEntity;
import com.youareright.model.sys.UserEntity;
import com.youareright.service.sys.OperationService;
import com.youareright.service.sys.UpClassService;
import com.youareright.service.sys.UserService;
import com.youareright.utils.TimeProcess;

class DeleteUpClassInfo {
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
class InsertUpClassInfo {
	UpClassEntity upClassEntity;
	String currentLoginName;
	public UpClassEntity getUpClassEntity() {
		return upClassEntity;
	}
	public void setUpClassEntity(UpClassEntity upClassEntity) {
		this.upClassEntity = upClassEntity;
	}
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
	
}
class UpdateUpClassInfo {
	UpClassEntity upClassEntity;
	String currentLoginName;
	public UpClassEntity getUpClassEntity() {
		return upClassEntity;
	}
	public void setUpClassEntity(UpClassEntity upClassEntity) {
		this.upClassEntity = upClassEntity;
	}
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
}


public class UpClassController {
	@Resource(name = "upClassServiceImpl")
	private UpClassService upClassService;
	
	@Resource(name = "operationServiceImpl")
	private OperationService operationService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	private TimeProcess timeProcess=new TimeProcess();
	
	@GetMapping("/upClasses")
	public PageResult upClassesList(String searchCondition, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		pageResult.setData(upClassService.upClassesList(searchCondition, pageSize, page * pageSize));
		pageResult.setTotalCount(upClassService.upClassesSize(searchCondition, pageSize, page * pageSize));
		return pageResult;
	}
	
	@GetMapping("/upClasses/getTable")
	public PageResult upClassesListTable(String searchCondition, int pageSize, int page) {
		PageResult pageResult =upClassesList(searchCondition, pageSize, page);
		return pageResult;
	}
	
	@DeleteMapping("/deleteUpClasses")
	public List<Integer> deleteUpClasses(@RequestBody DeleteUpClassInfo deleteUpClassInfo) {
		List<Integer> groupID=deleteUpClassInfo.getGroupId();
		String currentLoginName=deleteUpClassInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		upClassService.deleteUpClasses(groupID);
		
		//日志
		String operationString="删除了"+Integer.toString(groupID.size())+"个大类别";
		String operationTime=timeProcess.nowTime().get(0);
		operationService.insertOperation(currentUserID, operationString, operationTime);
		return groupID;
	}
	
	
	@PostMapping("/upClasses/upClass")
	public String insertUpClass(@RequestBody InsertUpClassInfo insertUpClassInfo) {
		String currentLoginName=insertUpClassInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		UpClassEntity newUpClass=insertUpClassInfo.getUpClassEntity();
		String newUpClassName=newUpClass.getUpClassName();
		UpClassEntity existedUpClass=upClassService.getUpClassEntityByUpClassName(newUpClassName);
		if(existedUpClass==null) {
			upClassService.insertUpClass(newUpClassName);
			
			//日志
			String operationString="添加了大类别["+newUpClassName+"]";
			String operationTime=timeProcess.nowTime().get(0);
			operationService.insertOperation(currentUserID, operationString, operationTime);
			
			return "@Insert new upclass successfully!@";
		}
		else {
			return "@Upclass name is existed!@";
		}
	}
	
	@PutMapping("/updateUpClass")
	public String updateUpClass(@RequestBody UpdateUpClassInfo updateUpClassInfo) {	
		String currentLoginName=updateUpClassInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		UpClassEntity postUpClassEntity=updateUpClassInfo.getUpClassEntity();
		
		String postUpClassName=postUpClassEntity.getUpClassName();
		int postUpClassID=postUpClassEntity.getUpClassID();
		UpClassEntity existedUpClass=upClassService.getUpClassEntityByUpClassName(postUpClassName);
		if(existedUpClass!=null && existedUpClass.getUpClassID()!=postUpClassEntity.getUpClassID()) {
			return "@Upclass name is existed!@";
		}
		else {
			upClassService.updateUpClass(postUpClassID,postUpClassName);
			
			//日志
			String operationString="修改了大类别，类别ID为["+Integer.toString(postUpClassID)+"]，新类别名为["+postUpClassName+"]";
			String operationTime=timeProcess.nowTime().get(0);
			operationService.insertOperation(currentUserID, operationString, operationTime);
			
			return "@Update upclass successfully!@";
		}
		
		
		
	}
}
