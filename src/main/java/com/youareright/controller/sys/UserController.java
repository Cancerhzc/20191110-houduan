package com.youareright.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.UserEntity;
import com.youareright.service.sys.OperationService;
import com.youareright.service.sys.UserService;
import com.youareright.utils.TimeProcess;

class InsertUserInfo {
	String currentLoginName;
	UserEntity userEntity;
	
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
}

class UpdateUserInfo {
	String currentLoginName;
	UserEntity userEntity;
	
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
}
class DeleteUserInfo {
	String currentLoginName;
	List<String> groupId;
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
class ChangePasswordInfo {
	String currentLoginName;
	String oldPassword;
	String newPassword;
	public String getCurrentLoginName() {
		return currentLoginName;
	}
	public void setCurrentLoginName(String currentLoginName) {
		this.currentLoginName = currentLoginName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}

@RestController
/*@PreAuthorize("hasRole('ADMI')")*/
public class UserController {

	private Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "operationServiceImpl")
	private OperationService operationService;
	
	private TimeProcess timeProcess=new TimeProcess();
	
	@GetMapping("/user/{loginName}")
	public UserEntity userGet(@PathVariable String loginName) {
		UserEntity userEntity = userService.getUserEntityByLoginName(loginName);
		log.debug("The method is ending");
		return userEntity;
	}

	/**
	 * 获取user表数据
	 * 
	 * @param loginName
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GetMapping("/users")
	public PageResult usersList(String loginName, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		pageResult.setData(userService.usersList(loginName, pageSize, page * pageSize));
		pageResult.setTotalCount(userService.usersSize(loginName, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}

	/**
	 * 新建用户信息
	 * 
	 * @param userEntity
	 * @return
	 */
	@PostMapping("/users/user")
	public String insertUser(@RequestBody InsertUserInfo insertUserInfo) {
		String currentLoginName=insertUserInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		UserEntity userEntity=insertUserInfo.getUserEntity();
		UserEntity existUserEntity=userService.getUserEntityByLoginName(userEntity.getLoginName());
		if(existUserEntity==null) {
			userService.insertUser(userEntity);
			//日志
			String operationString="新增了用户[用户名："+userEntity.getName()+"]";
			String operationTime=timeProcess.nowTime().get(0);
			operationService.insertOperation(currentUserID, operationString, operationTime);
			log.debug("The method is ending");
			return "@Insert user successfully!@";
		}
		else {
			log.debug("The method is ending");
			return "@Username is existed!@";
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param userEntity
	 * @param id
	 * @return
	 */
	@PutMapping("/users/{id}")
	public String updateUser(@RequestBody UpdateUserInfo updateUserInfo,@PathVariable int id) {
		String currentLoginName=updateUserInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		UserEntity userEntity=updateUserInfo.getUserEntity();
		UserEntity existUserEntity=userService.getUserEntityByLoginName(userEntity.getLoginName());
		
		if(existUserEntity==null) {
			if (userEntity.getId() == id) {
				userService.updateUser(userEntity);
			}
			//日志
			String operationString="修改了用户[登录名："+userEntity.getLoginName()+"]的用户信息";
			String operationTime=timeProcess.nowTime().get(0);
			operationService.insertOperation(currentUserID, operationString, operationTime);
			
			log.debug("The method is ending");
			return "@Update user successfully!@";
		}
		else {
			return "@Username is existed!@";
		}
	}
	
	/**
	 * 修改密码
	 * 
	 * @param userEntity
	 * @param id
	 * @return
	 */
	@PutMapping("/user/changePassword")
	public String changePassword(@RequestBody ChangePasswordInfo changePasswordInfo) {
		String currentLoginName=changePasswordInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		String oldPassword=changePasswordInfo.getOldPassword();
		String oldRightPassword=currentUser.getPassword().substring(8);
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		if(bcryptPasswordEncoder.matches(oldPassword,oldRightPassword)) {
			currentUser.setPassword(changePasswordInfo.getNewPassword());
			userService.updateUser(currentUser);
			
			//日志
			String operationString="用户[登录名："+currentUser.getLoginName()+"]修改了密码";
			String operationTime=timeProcess.nowTime().get(0);
			operationService.insertOperation(currentUserID, operationString, operationTime);
			
			log.debug("The method is ending");
			return "@Change password successfully!@";
		}
		else {
			return "@Old password is wrong!@";
		}	
	}
	

	/**
	 * 删除用户信息
	 * 
	 * @param groupId
	 * @return
	 */
	@DeleteMapping("/users")
	public List<String> deleteUsers(@RequestBody DeleteUserInfo deleteUserInfo) {
		String currentLoginName=deleteUserInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		List<String> groupId=deleteUserInfo.getGroupId();
		userService.deleteUsers(groupId);
		
		//日志
		String operationString="删除了"+Integer.toString(groupId.size())+"名用户";
		String operationTime=timeProcess.nowTime().get(0);
		operationService.insertOperation(currentUserID, operationString, operationTime);
		return groupId;
	}
}