package com.youareright.service.impl.sys;

import com.youareright.dao.RoleMenuDao;
import com.youareright.model.sys.RoleMenuEntity;
import com.youareright.service.sys.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleMenuServiceImpl")
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;
    /**
     * 批量修改角色菜单关系
     *
     * @param roleMenuEntityList
     */
    @Override
    public void alterEntity(List<RoleMenuEntity> roleMenuEntityList) {
        if (!roleMenuEntityList.isEmpty()){
            int roleId = roleMenuEntityList.get(0).getRoleId();
            List<String> menuIds = roleMenuDao.getMenuByRoleId(roleId);
            for (String menuId:menuIds){
                RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
                roleMenuEntity.setRoleId(roleId);
                roleMenuEntity.setMenuId(Integer.parseInt(menuId));
                if (!roleMenuEntityList.contains(roleMenuEntity)){
                    roleMenuDao.delEntity(roleMenuEntity);
                }
            }
            for (RoleMenuEntity roleMenuEntity:roleMenuEntityList){
                System.out.println(roleMenuEntity.getMenuId());
                if (!menuIds.contains(String.valueOf(roleMenuEntity.getMenuId()))){
                    System.out.println(roleMenuEntity.getMenuId());
                    roleMenuDao.insertEntity(roleMenuEntity);
                }
            }
        }
    }
}