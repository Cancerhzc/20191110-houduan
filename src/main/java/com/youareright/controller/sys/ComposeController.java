package com.youareright.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.service.sys.ShelfService;
import com.youareright.service.sys.UserService;
import com.youareright.model.sys.PhotoMergeEntity;
import com.youareright.service.sys.ClassService;
import com.youareright.service.sys.OperationService;
import com.youareright.service.sys.PathService;
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
	
	@Resource(name = "classServiceImpl")
	private ClassService classService;//获取合成图片的人的ID
	
	@Resource(name = "photoMergeServiceImpl")
	private PhotoMergeService photoMergeService;
	
	@Resource(name = "pathServiceImpl")
	private PathService pathService;
	
	@Resource(name = "operationServiceImpl")
	private OperationService operationService;
	
	
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
		String absolutePath=pathService.runningPath().getPath();
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
		fileProcess.makeDirectory(pathService.runningPath().getIniBasicPath()+"/"+userTimeDir);
		String goodsTextPath=pathService.runningPath().getIniBasicPath()+"/"+userTimeDir+"/goods_path.txt";
		String shelvesTextPath=pathService.runningPath().getIniBasicPath()+"/"+userTimeDir+"/shelves_path.txt";
		String pictureNumberPath=pathService.runningPath().getIniBasicPath()+"/"+userTimeDir+"/picture_number.txt";
		
	
		//输出路径与下载地址
		String outPath=pathService.runningPath().getPath()+"/userMergePhotos";
		String downloadPath=pathService.runningPath().getPath()+"/userMergePhotos"+"/"+userTimeDir+".zip";
		String downloadUrl="/userMergePhotos"+"/"+userTimeDir+".zip";
		String doneNumberPath=pathService.runningPath().getIniBasicPath()+"/"+userTimeDir+"/done_number.txt";
		
		//以下对图像文本的处理
		for(Integer classNo:classList) {
			String currentClassNoString=Integer.toString(classNo);
			String className=classService.getClassNameByClassID(classNo);
			goodsContent+=absolutePath+"/myimages/"+currentClassNoString+","+className+"\n";
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
		String pythonPath=pathService.runningPath().getPythonPath();
		String pyPath=pathService.runningPath().getPyPath();
		
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
		

		//日志
		String operationString="合成图片[输出图片数量："+Integer.toString(mergePictureNumber)+"]";
		String operationTime=timeProcess.nowTime().get(0);
		operationService.insertOperation(currentUserID, operationString, operationTime);
		
		return "已交给后台处理";
	}
}
