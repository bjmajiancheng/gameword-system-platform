
/*
* 北京果敢时代科技有限公司
* 北京市朝阳区望京SOHO T3 B座1607
* 邮编：100022
* 网址：www.davdian.com
 */
package com.gameword.system.system.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gameword.system.common.baseservice.impl.BaseService;
import com.gameword.system.system.service.ISystemUserRoleService;
import com.gameword.system.core.dao.UserRoleMapper;
import com.gameword.system.core.model.UserRoleModel;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */
@Service("systemUserRoleService")
public class SystemUserRoleServiceImpl extends BaseService<UserRoleModel> implements ISystemUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public int deleteByUserId(Integer userId) {
        return userRoleMapper.deleteByUserId(userId);
    }

    public List<UserRoleModel> find(Map<String, Object> param) {
        return userRoleMapper.find(param);
    }

    @Override
    public List<UserRoleModel> selectByFilter(UserRoleModel userRoleModel) {
        Example          example  = new Example(UserRoleModel.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(userRoleModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(userRoleModel.getSortWithOutOrderBy());
        }

        return getMapper().selectByExample(example);
    }

    @Override
    public PageInfo<UserRoleModel> selectByFilterAndPage(UserRoleModel userRoleModel, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<UserRoleModel> list = this.selectByFilter(userRoleModel);

        return new PageInfo<>(list);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
