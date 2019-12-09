package com.youareright.model.sys;

import java.util.Objects;

public class RoleMenuEntity {
    /**
     * 角色id
     */
    private int roleId;
    /**
     * 菜单id
     */
    private int menuId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleMenuEntity that = (RoleMenuEntity) o;
        return roleId == that.roleId &&
                menuId == that.menuId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, menuId);
    }
}