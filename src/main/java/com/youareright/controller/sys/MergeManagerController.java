package com.youareright.controller.sys;

import org.springframework.web.bind.annotation.RestController;

class MergePhotoInfomation {
	private int mergeID;         //自增的ID
	private int mergeUserID;    //提交合成的用户ID
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
	public int getMergeUserID() {
		return mergeUserID;
	}
	public void setMergeUserID(int mergeUserID) {
		this.mergeUserID = mergeUserID;
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
	public void setMergePhotoNum(int mergePictureNum) {
		this.mergePhotoNum = mergePictureNum;
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

@RestController
public class MergeManagerController {

}
