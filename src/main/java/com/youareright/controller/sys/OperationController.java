package com.youareright.controller.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.model.sys.GoodsEntity;
import com.youareright.model.sys.OperationEntity;
import com.youareright.model.sys.PageResult;
import com.youareright.service.sys.GoodsService;
import com.youareright.service.sys.OperationService;
import com.youareright.service.sys.UserService;

class ReturnOperation {
	int operationID;
	String userName;
	String operation;
	String time;
	public int getOperationID() {
		return operationID;
	}
	public void setOperationID(int operationID) {
		this.operationID = operationID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
@RestController
public class OperationController {
	@Resource(name = "operationServiceImpl")
	private OperationService operationService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;//获取合成图片的人的ID
	@GetMapping("/operation")
	public PageResult operatioinList(int pageSize, int page) {
		PageResult pageResult = new PageResult();
		List<OperationEntity> operations = operationService.getOperation(pageSize, page * pageSize);
		List<ReturnOperation> returnOperationList=new ArrayList<ReturnOperation>();
		int listLength=operations.size();
		for(int i=0;i<listLength;i++) {
			int operationID=operations.get(i).getOperationID();
			int userID=operations.get(i).getUserID();
			String operation=operations.get(i).getOperation();
			String time=operations.get(i).getTime();
			String operateUser=userService.getUsernameByUserID(userID);
			ReturnOperation temp=new ReturnOperation();
			temp.setOperationID(operationID);
			temp.setOperation(operation);
			temp.setTime(time);
			temp.setUserName(operateUser);
			returnOperationList.add(temp);
		}
		pageResult.setData(returnOperationList);
		pageResult.setTotalCount(operationService.operationSize(pageSize, page * pageSize));
		return pageResult;
	}
}
