package com.youareright.dao;

import org.apache.ibatis.annotations.Mapper;

import com.youareright.model.sys.PathEntity;

@Mapper
public interface PathDao {
	public PathEntity runningPath();
}
