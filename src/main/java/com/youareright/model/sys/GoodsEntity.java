package com.youareright.model.sys;

public class GoodsEntity {
    private int goodsID;
    private int classID;
    private String goodsClass;
    private String goodsPath;
    private String goodsFilename;
    private int goodsState;
    private int uploadUser;
    private int markUserID;

    
    
    public String getGoodsClass() {
		return goodsClass;
	}

	public void setGoodsClass(String goodsClass) {
		this.goodsClass = goodsClass;
	}

	public int getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(int goodsID) {
        this.goodsID = goodsID;
    }

    public Integer getClassID() {
		return classID;
	}

	public void setClassID(Integer classID) {
		this.classID = classID;
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

    public int getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(int uploadUser) {
        this.uploadUser = uploadUser;
    }

    public int getMarkUserID() {
        return markUserID;
    }

    public void setMarkUserID(int markUserID) {
        this.markUserID = markUserID;
    }
    public int getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(int goodsState) {
        this.goodsState = goodsState;
    }
    
    
}
