package com.youareright.model.sys;

public class PhotoMergeEntity {
	private int mergeID;
	private int mergeUserID;
	private String submitTime;
	private int mergePictureNum;
	private String downloadUrl;
	private int state;
	
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
	public int getMergePictureNum() {
		return mergePictureNum;
	}
	public void setMergePictureNum(int mergePictureNum) {
		this.mergePictureNum = mergePictureNum;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}		
}
