package com.youareright.model.sys;

public class PathEntity {
	//本机运行
//	private static final String path="G:/git/wh-web";
//	private static final String pythonPath="E:/Python/Python38/python.exe";
//	private static final String pyPath="G:/git/wh-web/PhotoMerge.py"; 
//	private static final String iniBasicPath="G:/git/wh-web";
	
	private String path;//服务器运行
	private String pythonPath;  //python执行文件的地址
	private String pyPath;    //服务器下的PhotoMerge.py的位置
	private String iniBasicPath;//控制合成图片配置参数文件（4个txt文件）的基地址
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPythonPath() {
		return pythonPath;
	}
	public void setPythonPath(String pythonPath) {
		this.pythonPath = pythonPath;
	}
	public String getPyPath() {
		return pyPath;
	}
	public void setPyPath(String pyPath) {
		this.pyPath = pyPath;
	}
	public String getIniBasicPath() {
		return iniBasicPath;
	}
	public void setIniBasicPath(String iniBasicPath) {
		this.iniBasicPath = iniBasicPath;
	}
	
	
//	private static final String path="/root/tomcat9/webapps";//服务器运行
//	private static final String pythonPath="/usr/local/bin/python3.8";  //python执行文件的地址
//	private static final String pyPath="/root/PhotoMerge/PhotoMerge.py";    //服务器下的PhotoMerge.py的位置
//	private static final String iniBasicPath="/root/PhotoMerge";//控制合成图片配置参数文件（4个txt文件）的基地址
	

	
	
}
