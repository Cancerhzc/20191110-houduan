package com.youareright.service.sys;

import com.youareright.model.sys.RoleMenuEntity;

import java.util.List;

public interface RoleMenuService {
    /**
     * 批量修改角色菜单关系
     * @param roleMenuEntityList
     */
    public void alterEntity(List<RoleMenuEntity> roleMenuEntityList);
}
