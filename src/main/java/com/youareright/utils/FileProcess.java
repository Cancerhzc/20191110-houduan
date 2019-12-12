package com.youareright.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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
				System.out.println(srcFilePath);
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
		
		public int writeFile(String content,String path) {
			try {
				File file = new File(path);
				if(!file.exists()) {
					file.createNewFile();
				}
				FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fileWriter);
				bw.write(content);
				bw.close();
				System.out.println("WriteFileSuccessfully!");
				return 1;
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	return 0;
		    }
		        
		}
		
		public void runPython(String pythonPath,String pyPath) {
			Process proc;
	        try {
	            String[] args1 = new String[] {pythonPath, pyPath};//pythonPath处为你系统中python的安装位置；pyPath为想要执行的python文件位置
	            proc=Runtime.getRuntime().exec(args1);
	            //用输入输出流来截取结果
	            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	            String line = null;
	            while ((line = in.readLine()) != null) {
	                System.out.println(line);
	            }
	            in.close();
	            proc.waitFor();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } 
		}
		
		public void makeDirectory(String path) {
			File file = new File(path);
			file.mkdir();
		}

}
