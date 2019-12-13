package com.youareright.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeProcess {
    public String nowTime() { 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String now=df.format(new Date());
		return now;
    }
}
