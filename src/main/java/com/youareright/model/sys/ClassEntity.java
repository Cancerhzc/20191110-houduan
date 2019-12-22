package com.youareright.model.sys;

public class ClassEntity {
	private Integer classID;
	private String goodsClass;
	private Integer upClassID;
	private String goodsName;
	private Integer containGoodsNumber;
	
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
	public Integer getUpClassID() {
		return upClassID;
	}
	public void setUpClassID(Integer upClassID) {
		this.upClassID = upClassID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getContainGoodsNumber() {
		return containGoodsNumber;
	}
	public void setContainGoodsNumber(Integer containGoodsNumber) {
		this.containGoodsNumber = containGoodsNumber;
	}
}
