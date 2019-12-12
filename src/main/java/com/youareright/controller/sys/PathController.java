package com.youareright.controller.sys;

public class PathController {
	private static final String path="G:/git/wh-web";//本机运行
	
//	private static final String path="/root/tomcat9/webapps";//服务器运行
	private static final String pythonPath="E:/Python/Python38/python.exe";  //python.exe的地址
	
	public String getPath() {
		return path;
	}
	
	public String getPythonPath() {
		return pythonPath;
	}
	
}
