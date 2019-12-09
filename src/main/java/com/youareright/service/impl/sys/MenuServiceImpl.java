package com.youareright.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import com.youareright.dao.*;
import com.youareright.model.sys.RelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.model.sys.MenuEntity;
import com.youareright.service.sys.MenuService;

import javax.swing.*;

@Service("menuServiceImpl")
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private RelationDao relationDao;

	@Autowired
	private RoleMenuDao roleMenuDao;


	public List<MenuEntity> menuList(int id) {
		List<RelationEntity> relationEntities = relationDao.getRelationByUserId(id);
		List<String> idList = new ArrayList<String>();
		for(RelationEntity relationEntity : relationEntities ){
			List<String> tmpList = roleMenuDao.getMenuByRoleId(relationEntity.getRoleId());
			idList.addAll(tmpList);
		}
		
//		String idstemp = "";
//		for (String idtemp : idList) {
//			idstemp = idstemp + idtemp;
//		}
//		String[] ids = idstemp.split(";");
		String[] ids = idList.toArray(new String[idList.size()]);
		List<MenuEntity> parentMenuList = menuDao.getParentMenuListById(ids);
		List<MenuEntity> childrenMenuList = menuDao.getMenuListById(ids);
		List<MenuEntity> menuList = new ArrayList<MenuEntity>();

		for (MenuEntity parentMenu : parentMenuList) {
			List<MenuEntity> menuListTemp = new ArrayList<MenuEntity>();
			for (MenuEntity childrenMenu : childrenMenuList) {
				if (parentMenu.getId() == childrenMenu.getParentId()) {
					menuListTemp.add(childrenMenu);
				}
			}
			parentMenu.setChildren(menuListTemp);
			menuList.add(parentMenu);
		}

		return menuList;
	}


	public List<MenuEntity> menusList(int pageSize, int start, String menuId) {
		return menuDao.menusList(pageSize, start, menuId);
	}


	public Integer menusSize(int pageSize, int start, String menuId) {
		return menuDao.menusSize(pageSize, start, menuId);
	}


	public void insertMenu(MenuEntity menuEntity) {
		menuDao.insertMenu(menuEntity);
	}


	public void updateMenu(MenuEntity menuEntity) {
		menuDao.updateMenu(menuEntity);
	}


	public void deleteMenus(List<String> groupId) {
		menuDao.deleteMenus(groupId);
	}


	public List<MenuEntity> menusByParentId(int parentId) {
		return menuDao.menusByParentId(parentId);
	}


	public List<MenuEntity> getSubmenus() {
		return menuDao.getSubmenus();
	}

}
