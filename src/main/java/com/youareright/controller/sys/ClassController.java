package com.youareright.controller.sys;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.ClassEntity;
import com.youareright.service.sys.ClassService;
import com.youareright.service.sys.GoodsService;
import com.youareright.utils.FileProcess;

@RestController
/*@PreAuthorize("hasRole('ADMI')")*/
public class ClassController {

	private Logger log = LoggerFactory.getLogger(ClassController.class);

	@Resource(name = "classServiceImpl")
	private ClassService classService;
	
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	private FileProcess fileProcess=new FileProcess();


	/**
	 * 获取class表数据
	 * 
	 * @param goodsClass
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GetMapping("/classes")
	public PageResult classesList(String searchCondition, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		pageResult.setData(classService.classesList(searchCondition, pageSize, page * pageSize));
		pageResult.setTotalCount(classService.classesSize(searchCondition, pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}

	/**
	 * 新建标签信息
	 * 
	 * @param classEntity
	 * @return
	 */
	@PostMapping("/classes/class")
	public ClassEntity insertClass(@RequestBody ClassEntity classEntity) {
		classService.insertClass(classEntity);
		log.debug("The method is ending");
		return classEntity;
	}

	/**
	 * 更新标签信息
	 * 
	 * @param classEntity
	 * @param id
	 * @return
	 */
	@PutMapping("/class/{id}")
	public int updateClass(@RequestBody ClassEntity classEntity) {		
		String newClassName=classEntity.getGoodsClass();
		String newGoodsName=classEntity.getGoodsName();
		int selectClassID=classEntity.getClassID();
		if(classService.checkClassIsExisted(newClassName)==0) {
			classService.modifyClass(selectClassID,newClassName,newGoodsName);	//修改class类即可
			System.out.println("直接修改了class，无需修改goods");
		}
		else {
			int newClassID=classService.getClassID(newClassName);            //通过goodsClass得到classID
			String newClassIDToString=Integer.toString(newClassID);
			String existGoodsName=classService.getGoodsNameByClassID(newClassID); 
			System.out.println(existGoodsName);
			String goodsName=classEntity.getGoodsName();
			if(selectClassID==newClassID) {                //如果没修改label但修改了goodsName，则直接修改class表即可
				classService.modifyClass(selectClassID,newClassName,newGoodsName);	//修改class类即可
			}
			else if(goodsName.equals(existGoodsName)) {
				//先移动文件，再在sys_goods数据库中更改classID，然后再在goods_class中删去原来的class
				String selectClassIDString=Integer.toString(selectClassID);
				String srcDirPath="G:/git/wh-web/src/images/"+selectClassIDString;
				String dstPath="G:/git/wh-web/src/images/"+newClassIDToString;
				File file=new File(srcDirPath);
				File[] fileList=file.listFiles();
				for(File f:fileList) {
					String currentFileName=f.getName();
					String srcFilePath=srcDirPath+"/"+currentFileName;
					String src="/src/images/"+newClassIDToString+"/"+currentFileName;
					goodsService.modifyGoods(selectClassID, newClassID, src);
					fileProcess.moveFile(srcFilePath,dstPath);
				}
				classService.del(selectClassID);
				System.out.println("goods表改了，原来的class删了");
			}
			else {
				return -1;
			}
		}	
		log.debug("The method is ending");
		return 1;
	}

	/**
	 * 删除标签
	 * 
	 * @param groupId
	 * @return
	 */
	@DeleteMapping("/classes")
	public List<String> deleteClasses(@RequestBody List<String> groupID) {
		int groupIDSize = groupID.size();
		if(groupID != null && groupIDSize != 0) {
			for(int i=0;i<groupIDSize;i++) {
				String currentClassIDString=groupID.get(i);
				String path="G:/git/wh-web/src/images/"+currentClassIDString;
				fileProcess.deleteFile(path);
			}
		}
		classService.deleteClasses(groupID);
		return groupID;
	}
	
	public Integer getClassID(String labelName) {
		return classService.getClassID(labelName);
	}
	
}


