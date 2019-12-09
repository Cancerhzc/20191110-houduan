package com.youareright.dao;

import com.youareright.model.sys.RoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMenuDao {
    /**
     * 根据role_id返回menu_id列表
     * @param roleId
     * @return
     */
    List<String> getMenuByRoleId(@Param("roleId") int roleId);

    /**
     * 删除实体
     * @param roleMenuEntity
     */
    void delEntity(@Param("entity")RoleMenuEntity roleMenuEntity);

    /**
     * 插入实体
     * @param roleMenuEntity
     */
    void insertEntity(@Param("entity")RoleMenuEntity roleMenuEntity);
}
