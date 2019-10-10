package com.chirping.gmall.user.mapper;

import com.chirping.gmall.user.pojo.UmsMember;

/**
 * @author 刘志鹏
 * @date 2019/10/10
 */
public interface UmsMemberMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UmsMember record);

    int insertSelective(UmsMember record);

    UmsMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsMember record);

    int updateByPrimaryKey(UmsMember record);
}