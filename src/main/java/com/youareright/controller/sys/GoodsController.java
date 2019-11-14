package com.youareright.controller.sys;

import org.springframework.web.bind.annotation.RestController;

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

import com.youareright.model.sys.GoodsEntity;
import com.youareright.service.sys.GoodsService;

@RestController
public class GoodsController {
	
	private Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	
	@PostMapping("/goods/good")
	/**
	 * 新建商品
	 * 
	 * @param goodsEntity
	 * @param 
	 * @return
	 */
	
	public GoodsEntity insertGoods(@RequestBody GoodsEntity goodsEntity) {
		goodsService.insertGoods(goodsEntity);
		log.debug("The method is ending");
		return goodsEntity;
	}
	
	
	public GoodsEntity updateGoods(@RequestBody GoodsEntity goodsEntity, @PathVariable int id) {
		if (goodsEntity.getGoodsID() == id) {
			goodsService.updateGoods(goodsEntity);
		}
		log.debug("The method is ending");
		return goodsEntity;
	}
	
	
	/**
	 * 删除用户信息
	 * 
	 * @param goodsID
	 * @return
	 */
	@DeleteMapping("/goodses")
	public List<String> deleteGoodses(@RequestBody List<String> groupID) {
		goodsService.deleteGoodses(groupID);
		return groupID;
	}
	
	/**
	 * 获取goods表数据
	 * 
	 * @param goodsFilename
	 * @param pageSize
	 * @param page
	 * @return
	 */
	@GetMapping("/goods")
	public PageResult goodsList(String goodsFilename, int pageSize, int page) {
		PageResult pageResult = new PageResult();
		pageResult.setData(goodsService.goodsList(goodsFilename, pageSize, page * pageSize));
		pageResult.setTotalCount(goodsService.goodsSize( pageSize, page * pageSize));
		log.debug("The method is ending");
		return pageResult;
	}
	

}

