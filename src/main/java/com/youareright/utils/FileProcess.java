package com.youareright.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileProcess {
	
	//以下函数是对文件的处理
	
		public int moveFile(String srcFilePath,String dstPath)            //可移动文件，也可移动一个文件夹里的文件
		{
			try
			{
				File srcFile=new File(srcFilePath); //源文件或文件夹路径
				File dstPathOpen=new File(dstPath);  
				if(!dstPathOpen.exists()) {
					dstPathOpen.mkdirs();
				}
				if (srcFile.renameTo(new File(dstPath+"/"+srcFile.getName()))) //源文件移动至目标文件目录
				{
					System.out.println("File is moved successful!");//输出移动成功
					return 0;
				}
				else
				{
					System.out.println("File is failed to move!");//输出移动失败
					return -1;
				}	
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return -1;
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
				return 1;
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	return 0;
		    }   
		}
		
		public void runPython(String pythonPath,String pyPath,String goodsTxtPath,String shelvesTxtPath,String pictureNumTxtPath,String outPath,String doneNumberPath) {
			Process proc;
	        try {
	            String[] args1 = new String[] {pythonPath, pyPath,goodsTxtPath,shelvesTxtPath,pictureNumTxtPath,outPath,doneNumberPath};
	            //pythonPath处为你系统中python的安装位置；pyPath为想要执行的python文件位置
	            //
	            proc=Runtime.getRuntime().exec(args1);
	            //用输入输出流来截取结果
	            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
		}
		
		public void makeDirectory(String path) {
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
		}
		
		public int countNumberInAZip(String txtPath) {
			String numString=readTxt(txtPath);
			if(numString==null) {
				return 0;
			}
			else {
				int number=Integer.valueOf(numString);
				return number;
			}
		}
		
		public static String readTxt(String txtPath) {
	        File file = new File(txtPath);
	        if(file.isFile() && file.exists()){
	            try {
	                FileInputStream fileInputStream = new FileInputStream(file);
	                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
	                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	                StringBuffer sb = new StringBuffer();
	                String text = null;
	                while((text = bufferedReader.readLine()) != null){
	                    sb.append(text);
	                }
	                bufferedReader.close();
	                return sb.toString();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	    }

}
