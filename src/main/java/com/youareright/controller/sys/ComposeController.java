package com.youareright.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.service.sys.ShelfService;
import com.youareright.utils.FileProcess;

class Choose {
	private List<Integer> chooseClass;
	private List<Integer> chooseShelves;
	private List<Integer> chooseShelvesClass;
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
	
	private PathController pathController=new PathController();
	String absolutePath=pathController.getPath();
	
	private FileProcess fileProcess=new FileProcess();
	
	/**
	 * 图像合成
	 * 
	 * @param 
	 * @return
	 */
	@PostMapping("/compose/submit")
	public void composePicture(@RequestBody Choose choose) {
		List<Integer> classList=choose.getChooseClass();
		List<Integer> shelvesList=choose.getChooseShelves();
		List<Integer> shelvesClassList=choose.getChooseShelvesClass();
		
		//两个txt文本的内容初始化
		String goodsContent=new String();
		String shelvesContent=new String();
		
		//三个txt文本的路径
		String goodsTextPath="./goods_path.txt";
		String shelvesTextPath="./shelves_path.txt";
		String pictureNumberPath="./picture_number.txt";
		
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
		String mergePictureNumberString=Integer.toString(mergePictureNumber);
		fileProcess.writeFile(mergePictureNumberString, pictureNumberPath);
		
		String pythonPath=pathController.getPythonPath();
		String pyPath="./PhotoMerge.py";
		fileProcess.runPython(pythonPath,pyPath);
	}
}
