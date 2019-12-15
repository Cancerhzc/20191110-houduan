package com.youareright.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.service.sys.ShelfService;
import com.youareright.service.sys.UserService;
import com.youareright.model.sys.PhotoMergeEntity;
import com.youareright.service.sys.PhotoMergeService;
import com.youareright.utils.FileProcess;
import com.youareright.utils.TimeProcess;

class Choose {
	private List<Integer> chooseClass;
	private List<Integer> chooseShelves;
	private List<Integer> chooseShelvesClass;
	private String submitLoginName;
	public String getSubmitLoginName() {
		return submitLoginName;
	}
	public void setSubmitLoginName(String submitLoginName) {
		this.submitLoginName = submitLoginName;
	}
	private int pictureNumber;
	public int getPictureNumber() {
		return pictureNumber;
	}
	public void setPictureNumber(int pictureNumber) {
		this.pictureNumber = pictureNumber;
	}
	public List<Integer> getChooseClass() {
		return chooseClass;
	}
	public void setChooseClass(List<Integer> chooseClass) {
		this.chooseClass = chooseClass;
	}
	public List<Integer> getChooseShelves() {
		return chooseShelves;
	}
	public void setChooseShelves(List<Integer> chooseShelves) {
		this.chooseShelves = chooseShelves;
	}
	public List<Integer> getChooseShelvesClass() {
		return chooseShelvesClass;
	}
	public void setChooseShelvesClass(List<Integer> chooseShelvesClass) {
		this.chooseShelvesClass = chooseShelvesClass;
	}

	
}

@RestController
public class ComposeController {
	@Resource(name = "shelfServiceImpl")
	private ShelfService shelfService;
	
	@Resource(name = "userServiceImpl")
	private UserService userService;//获取合成图片的人的ID
	
	@Resource(name = "photoMergeServiceImpl")
	private PhotoMergeService photoMergeService;
	
	private PathController pathController=new PathController();
	String absolutePath=pathController.getPath();
	
	private FileProcess fileProcess=new FileProcess();
	
	private TimeProcess timeProcess=new TimeProcess();
	
	private PhotoMergeEntity photoMergeEntity=new PhotoMergeEntity();
	
	/**
	 * 图像合成
	 * 
	 * @param 
	 * @return
	 */
	@PostMapping("/compose/submit")
	public String composePicture(@RequestBody Choose choose) {
		List<Integer> classList=choose.getChooseClass();
		List<Integer> shelvesList=choose.getChooseShelves();
		List<Integer> shelvesClassList=choose.getChooseShelvesClass();
		
		
		//提交时间
		String submitTime=timeProcess.nowTime().get(0);

		//获取提交合成人员的信息
		int currentUserID=userService.getUserEntityByLoginName(choose.getSubmitLoginName()).getId();
		String currentUserIDString=Integer.toString(currentUserID);
		
		//三个txt文本的内容初始化
		String goodsContent=new String();
		String shelvesContent=new String();
		String mergePictureNumberString=new String();
		
		//用户-时间专属对应的文件夹
		String timePath=timeProcess.nowTime().get(1);
		String userTimeDir=currentUserIDString+"-"+timePath;
		
		//三个txt文本的路径
		fileProcess.makeDirectory(pathController.getIniBasicPath()+"/"+userTimeDir);
		String goodsTextPath=pathController.getIniBasicPath()+"/"+userTimeDir+"/goods_path.txt";
		String shelvesTextPath=pathController.getIniBasicPath()+"/"+userTimeDir+"/shelves_path.txt";
		String pictureNumberPath=pathController.getIniBasicPath()+"/"+userTimeDir+"/picture_number.txt";
		
	
		//输出路径与下载地址
		String outPath=pathController.getPath()+"/userMergePhotos";
		String downloadPath=pathController.getPath()+"/userMergePhotos"+"/"+userTimeDir+".zip";
		String downloadUrl="/userMergePhotos"+"/"+userTimeDir+".zip";
		String doneNumberPath=pathController.getIniBasicPath()+"/"+userTimeDir+"/done_number.txt";
		
		//以下对图像文本的处理
		for(Integer classNo:classList) {
			String currentClassNoString=Integer.toString(classNo);
			goodsContent+=absolutePath+"/myimages/"+currentClassNoString+"\n";
		}
		fileProcess.writeFile(goodsContent, goodsTextPath);
		
		//以下是对货架文本的处理
		for(int i=0;i<shelvesList.size();i++) {
			int currentShelfClassID=shelvesClassList.get(i);
			int currentShelfID=shelvesList.get(i);
			String shelfSourcePath=shelfService.getSrc(currentShelfID);
			String currentShelfIDSuffix = shelfSourcePath.substring(shelfSourcePath.lastIndexOf("."));
			String currentShelfClassIDString=Integer.toString(currentShelfClassID);
			String currentShelfIDString=Integer.toString(currentShelfID);
			shelvesContent+=absolutePath+"/myimages/shelf/"+currentShelfClassIDString+"/"+currentShelfIDString+currentShelfIDSuffix+",";
			shelvesContent+=absolutePath+"/myimages/shelf/"+currentShelfClassIDString+"/"+currentShelfIDString+".xml\n";
		}
		fileProcess.writeFile(shelvesContent, shelvesTextPath);
		
		
		//以下是对合成图片数量的处理
		int mergePictureNumber=choose.getPictureNumber();
		mergePictureNumberString=Integer.toString(mergePictureNumber);
		fileProcess.writeFile(mergePictureNumberString, pictureNumberPath);
		
		//获得执行路径
		String pythonPath=pathController.getPythonPath();
		String pyPath=pathController.getPyPath();
		
		//执行python
		fileProcess.makeDirectory(outPath);
		fileProcess.runPython(pythonPath,pyPath,goodsTextPath,shelvesTextPath,pictureNumberPath,downloadPath,doneNumberPath);
		
		//对添加到数据库实体的处理
		photoMergeEntity.setSubmitTime(submitTime);
		photoMergeEntity.setMergeUserID(currentUserID);
		photoMergeEntity.setMergePictureNum(mergePictureNumber);
		photoMergeEntity.setState(0);
		photoMergeEntity.setDownloadUrl(downloadUrl);
		photoMergeService.insertPhotoMerge(photoMergeEntity);
		
		return "已交给后台处理";
	}
}
