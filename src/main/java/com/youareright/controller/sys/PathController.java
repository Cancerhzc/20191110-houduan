package com.youareright.controller.sys;

public class PathController {
//	private static final String path="/root/tomcat9/webapps";//服务器运行
//	
//	private static final String pythonPath="/usr/local/bin/python3.8";  //python执行文件的地址
//	
//	private static final String pyPath="/root/PhotoMerge/PhotoMerge.py";    //服务器下的PhotoMerge.py的位置
//	
//	private static final String iniBasicPath="/root/Photo";//控制合成图片配置参数文件（4个txt文件）的基地址
	
//本机运行
	private static final String path="G:/git/wh-web";
	private static final String pythonPath="E:/Python/Python38/python.exe";
	private static final String pyPath="G:/git/wh-web/PhotoMerge.py"; 
	private static final String iniBasicPath="G:/git/wh-web/";

	public String getPath() {
		return path;
	}
	public String getPythonPath() {
		return pythonPath;
	}
	public String getPyPath() {
		return pyPath;
	}
	public String getIniBasicPath() {
		return iniBasicPath;
	}
	
}
