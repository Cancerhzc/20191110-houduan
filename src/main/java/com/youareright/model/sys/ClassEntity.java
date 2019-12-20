package com.youareright.model.sys;

public class ClassEntity {
	private Integer classID;
	private String goodsClass;
	private String upClassName;
	private String goodsName;
	private Integer containGoodsNumber;

	public void setContainGoodsNumber(Integer containGoodsNumber) {
		this.containGoodsNumber = containGoodsNumber;
	}
	public int getContainGoodsNumber() {
		return containGoodsNumber;
	}
	public void setContainGoodsNumber(int containGoodsNumber) {
		this.containGoodsNumber = containGoodsNumber;
	}
	public Integer getClassID() {
		return classID;
	}
	public void setClassID(Integer classID) {
		this.classID = classID;
	}
	public String getGoodsClass() {
		return goodsClass;
	}
	public void setGoodsClass(String goodsClass) {
		this.goodsClass = goodsClass;
	}
	public String getUpClassName() {
		return upClassName;
	}
	public void setUpClassName(String upClassName) {
		this.upClassName = upClassName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}
