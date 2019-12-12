package com.youareright.controller.sys;

public class PathController {
//	private static final String path="G:/git/wh-web";//本机运行	
	private static final String path="/root/tomcat9/webapps";//服务器运行
	
	private static final String pythonPath="/usr/local/bin/python3.8";  //python执行文件的地址
	
//	private static final String pyPath="./PhotoMerge.py";      //本机运行

	private static final String pyPath="/root/PhotoMerge/PhotoMerge.py";    //服务器下的PhotoMerge.py的位置
	
	
	//货架合成的三个配置文件的地址
	private static final String goodsTextPath="/root/PhotoMerge/goods_path.txt";
	private static final String shelvesTextPath="/root/PhotoMerge/shelves_path.txt";
	private static final String pictureNumberPath="/root/PhotoMerge/picture_number.txt";
	private static final String outPath="/root/PhotoMerge/out";
	
	public String getPath() {
		return path;
	}
	public String getPythonPath() {
		return pythonPath;
	}
	public String getPyPath() {
		return pyPath;
	}
	public String getGoodsTextPath() {
		return goodsTextPath;
	}
	public String getShelvesTextPath() {
		return shelvesTextPath;
	}
	public String getPictureNumberPath() {
		return pictureNumberPath;
	}
	public String getOutPath() {
		return outPath;
	}
	
}
