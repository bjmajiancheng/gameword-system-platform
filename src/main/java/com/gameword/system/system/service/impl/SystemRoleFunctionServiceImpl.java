/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.service.impl;

import com.gameword.system.common.baseservice.impl.BaseService;
import com.gameword.system.system.service.ISystemRoleFunctionService;
import com.gameword.system.core.dao.RoleFunctionMapper;
import com.gameword.system.core.model.RoleFunctionModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("systemRoleFunctionService")
public class SystemRoleFunctionServiceImpl extends BaseService<RoleFunctionModel> implements
		ISystemRoleFunctionService {
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	

	public List<RoleFunctionModel> find(Map<String, Object> param) {
		return roleFunctionMapper.find(param);
	}

	@Override
	public PageInfo<RoleFunctionModel> selectByFilterAndPage(RoleFunctionModel roleFunctionModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<RoleFunctionModel> list = this.selectByFilter(roleFunctionModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<RoleFunctionModel> selectByFilter(RoleFunctionModel roleFunctionModel) {
		Example example = new Example(RoleFunctionModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(roleFunctionModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(roleFunctionModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}


	public int delByRoleId(int roleId) {
		return roleFunctionMapper.delByRoleId(roleId);
	}
}
