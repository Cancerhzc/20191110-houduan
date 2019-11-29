package com.youareright.utils;

import java.io.File;

public class FileProcess {
	
	//以下函数是对文件的处理
	
		public void moveFile(String srcFilePath,String dstPath)            //可移动文件，也可移动一个文件夹里的文件
		{
			try
			{
				File srcFile=new File(srcFilePath); //源文件或文件夹路径
				File dstPathOpen=new File(dstPath);  
				if(!dstPathOpen.exists()) {
					dstPathOpen.mkdirs();
				}
				String newPath=dstPath+"/"+srcFile.getName();
				System.out.println(newPath);
				if (srcFile.renameTo(new File(dstPath+"/"+srcFile.getName()))) //源文件移动至目标文件目录
				{
					System.out.println("File is moved successful!");//输出移动成功
				}
				else
				{
					System.out.println("File is failed to move!");//输出移动失败
				}	
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void moveAllDirfileToANewDir(String srcFilePath,String dstPath)
		{
			try
			{
				File srcFile=new File(srcFilePath); //源文件
				File dstPathOpen=new File(dstPath);
				if(!dstPathOpen.exists()) {
					dstPathOpen.mkdirs();
				}
				String newPath=dstPath+"/"+srcFile.getName();
				System.out.println(newPath);
				if (srcFile.renameTo(new File(dstPath+"/"+srcFile.getName()))) //源文件移动至目标文件目录
				{
					System.out.println("File is moved successful!");//输出移动成功
				}
				else
				{
					System.out.println("File is failed to move !");//输出移动失败
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		public boolean deleteFile(String fileName) {    //删除文件或文件夹
			File file = new File(fileName);
	        if (!file.exists()) {
	            return false;
	        }

	        if (file.isDirectory()) {
	            File[] files = file.listFiles();
	            for (File f : files) {
	            	System.out.println("您删除了："+f.getAbsolutePath());
	            	String currentPath=f.getAbsolutePath();
	            	deleteFile(currentPath);
	            }
	        }
	        boolean isSuccess=file.delete();
	        return isSuccess;
	    }

}
