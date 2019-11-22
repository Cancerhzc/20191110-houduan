package com.youareright.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youareright.model.sys.PageResult;
import com.youareright.model.sys.ClassEntity;
import com.youareright.service.sys.ClassService;

@RestController
/*@PreAuthorize("hasRole('ADMI')")*/
public class ClassController {

	private Logger log = LoggerFactory.getLogger(ClassController.class);

	@Resource(name = "classServiceImpl")
	private ClassService classService;


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
	public ClassEntity updateClass(@RequestBody ClassEntity classEntity, @PathVariable String goodsClass) {
		if (classEntity.getGoodsClass() == goodsClass) {
			classService.updateClass(classEntity);
		}
		log.debug("The method is ending");
		return classEntity;
	}

	/**
	 * 删除标签
	 * 
	 * @param groupId
	 * @return
	 */
	@DeleteMapping("/classes")
	public List<String> deleteClasses(@RequestBody List<String> groupID) {
		classService.deleteClasses(groupID);
		return groupID;
	}
	
	public Integer getClassID(String labelName) {
		return classService.getClassID(labelName);
	}
	
	
}


