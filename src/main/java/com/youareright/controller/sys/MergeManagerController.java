package com.youareright.controller.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.PhotoMergeEntity;
import com.youareright.model.sys.UserEntity;
import com.youareright.service.sys.OperationService;
import com.youareright.service.sys.PathService;
import com.youareright.service.sys.PhotoMergeService;
import com.youareright.service.sys.UserService;
import com.youareright.utils.FileProcess;
import com.youareright.utils.TimeProcess;

class MergePhotoInfomation {
	private int mergeID;         //自增的ID
	private String mergeUsername;    //提交合成的用户
	private String submitTime;    //提交时间
	private int mergePhotoNum;   //所需合成张数
	private int currentMergeDoneNum;   //已合成张数
	private int state;      //状态
	private String waitTime; //预计等待时间
	private String downloadUrl;//下载链接
	public int getMergeID() {
		return mergeID;
	}
	public void setMergeID(int mergeID) {
		this.mergeID = mergeID;
	}
	public String getMergeUsername() {
		return mergeUsername;
	}
	public void setMergeUsername(String mergeUsername) {
		this.mergeUsername = mergeUsername;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public int getMergePhotoNum() {
		return mergePhotoNum;
	}
	public void setMergePhotoNum(int mergePhotoNum) {
		this.mergePhotoNum = mergePhotoNum;
	}
	public int getCurrentMergeDoneNum() {
		return currentMergeDoneNum;
	}
	public void setCurrentMergeDoneNum(int currentMergeDoneNum) {
		this.currentMergeDoneNum = currentMergeDoneNum;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

}

class MergeDeleteInfo {
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

@RestController
public class MergeManagerController {
	private Logger log = LoggerFactory.getLogger(MergeManagerController.class);

	private FileProcess fileProcess=new FileProcess();
	
	private TimeProcess timeProcess=new TimeProcess();
	
	
	@Resource(name = "photoMergeServiceImpl")
	private PhotoMergeService photoMergeService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;
	
	@Resource(name = "pathServiceImpl")
	private PathService pathService;
	
	@Resource(name = "operationServiceImpl")
	private OperationService operationService;
	
	/**
	 * 删除合并的图片信息
	 * 
	 * @param groupId
	 * @return
	 */
	@DeleteMapping("/deleteMergePictures")
	public List<String> deleteMergePicture(@RequestBody MergeDeleteInfo mergeDeleteInfo) {
		List<String> groupId=mergeDeleteInfo.getGroupId();
		String currentLoginName=mergeDeleteInfo.getCurrentLoginName();
		UserEntity currentUser=userService.getUserEntityByLoginName(currentLoginName);
		int currentUserID=currentUser.getId();
		int groupIDSize = groupId.size();
		if(groupId != null && groupIDSize != 0) {
			for(int i=0;i<groupIDSize;i++) {
				String currentMergeIDString=groupId.get(i);
				int currentMergeID=Integer.valueOf(currentMergeIDString);
				String currentMergePath=pathService.runningPath().getPath()+photoMergeService.getMergeUrlByMergeID(currentMergeID);
				fileProcess.deleteFile(currentMergePath);
			}
		}
		photoMergeService.deleteMerges(groupId);
		
		//日志
		String operationString="删除了"+Integer.toString(groupIDSize)+"项合成图片的压缩包";
		String operationTime=timeProcess.nowTime().get(0);
		operationService.insertOperation(currentUserID, operationString, operationTime);
		
		return groupId;	
	}
	
	
	/**
	 * 获取并处理merge表数据
	 * 
	 * @return
	 */
	@GetMapping("/getMergePictures")
	public PageResult mergesList(String searchCondition,int pageSize, int page) {
		PageResult pageResult = new PageResult();
		List<MergePhotoInfomation> mergePhotoMoreInfoList=new ArrayList<MergePhotoInfomation>();  //用于给前端的完整信息
		List<PhotoMergeEntity> photoMergeInfoList=photoMergeService.mergesList(searchCondition,pageSize, page * pageSize);
		int photoMergeInfoListSize=photoMergeInfoList.size();
		for(int i=0;i<photoMergeInfoListSize;i++) {
			MergePhotoInfomation tempInfo=new MergePhotoInfomation();
			String downloadPath=photoMergeInfoList.get(i).getDownloadUrl();
			int state=photoMergeInfoList.get(i).getState();
			int mergePictureNumber=photoMergeInfoList.get(i).getMergePictureNum();
			int mergeUserID=photoMergeInfoList.get(i).getMergeUserID();
			String mergeUsername=userService.getUsernameByUserID(mergeUserID);
			tempInfo.setMergeID(photoMergeInfoList.get(i).getMergeID());
			tempInfo.setMergeUsername(mergeUsername);
			tempInfo.setSubmitTime(photoMergeInfoList.get(i).getSubmitTime());
			tempInfo.setState(state);
			tempInfo.setMergePhotoNum(mergePictureNumber);
			String userTimeDir=downloadPath.substring(downloadPath.lastIndexOf("/")+1, downloadPath.lastIndexOf("."));
			if(state==0) {
				String doneTxtPath=pathService.runningPath().getIniBasicPath()+"/"+userTimeDir+"/done_number.txt";
				int donePictureNumber=fileProcess.countNumberInAZip(doneTxtPath);//获取已完成图片数量
				tempInfo.setCurrentMergeDoneNum(donePictureNumber);
				if(donePictureNumber==mergePictureNumber) {
					photoMergeService.updateMerges(photoMergeInfoList.get(i).getMergeID(),1);
					tempInfo.setState(1);
					tempInfo.setDownloadUrl(downloadPath);
					fileProcess.deleteFile(pathService.runningPath().getIniBasicPath()+"/"+userTimeDir);
				}
				else {
					int toMergePictureNumber=mergePictureNumber-donePictureNumber;
					String ExpectedWaitTime=timeProcess.waitTimeString(toMergePictureNumber);
					tempInfo.setWaitTime(ExpectedWaitTime);
				}
			}
			else {
				tempInfo.setCurrentMergeDoneNum(mergePictureNumber);
				tempInfo.setDownloadUrl(downloadPath);
			}
			mergePhotoMoreInfoList.add(tempInfo);
		}
		
		pageResult.setData(mergePhotoMoreInfoList);
		pageResult.setTotalCount(photoMergeService.mergesSize(searchCondition, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}
}
