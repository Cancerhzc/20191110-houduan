package com.youareright.service.impl.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youareright.dao.RoleDao;
import com.youareright.model.sys.RoleEntity;
import com.youareright.service.sys.RoleService;

@Service(value = "roleServiceImpl")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	public List<RoleEntity> rolesList(int pageSize, int start) {
		return roleDao.rolesList(pageSize, start);
	}

	public Integer rolesSize(int pageSize, int start) {
		return roleDao.rolesSize(pageSize, start);
	}

	public void insertRole(RoleEntity roleEntity) {
		roleDao.insertRole(roleEntity);
	}

	public void updateRole(RoleEntity roleEntity) {
		roleDao.updateRole(roleEntity);
	}

	public void deleteRoles(List<String> groupId) {
		roleDao.deleteRoles(groupId);
	}

	public List<RoleEntity> allRoles() {
		return roleDao.allRoles();
	}

}
