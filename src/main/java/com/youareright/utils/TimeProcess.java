package com.youareright.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeProcess {
    public List<String> nowTime() { 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		Date date=new Date();
		String now=df.format(date);
		String nowPath=df2.format(date);
		List<String> timeList=new ArrayList<String>();
		timeList.add(now);
		timeList.add(nowPath);
		return timeList;
    }
    
    
    public String waitTimeString(int number) {
    	int second=5*number;
    	int minute=second/60;
    	int hour=minute/60;
    	String waitTime;
    	if(hour==0) {
    		if(minute==0) {
    			waitTime ="< 1分钟";
    		}
    		else {
    			waitTime = Integer.toString(minute)+"分钟";
    		}
    	}
    	else {
    		int newMinute = minute-hour*60;
    		waitTime = Integer.toString(hour)+"小时"+Integer.toString(newMinute)+"分钟";
    	}
    	return waitTime;
    }
}
