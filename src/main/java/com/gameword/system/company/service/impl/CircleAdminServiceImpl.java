/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.service.impl;

import java.util.List;

import com.gameword.system.common.baseservice.impl.BaseService;
import com.gameword.system.company.dao.CircleAdminMapper;
import com.gameword.system.company.service.ICircleAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gameword.system.company.model.CircleAdminModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("circleAdminService")
public class CircleAdminServiceImpl extends BaseService<CircleAdminModel> implements ICircleAdminService {
	@Autowired
	private CircleAdminMapper circleAdminMapper;
	
	@Override
	public CircleAdminModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circleAdminMapper.findById(id);
	}


	public List<CircleAdminModel> find(Map<String, Object> param) {
		return circleAdminMapper.find(param);
	}

	@Override
	public PageInfo<CircleAdminModel> selectByFilterAndPage(CircleAdminModel circleAdminModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CircleAdminModel> list = this.selectByFilter(circleAdminModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CircleAdminModel> selectByFilter(CircleAdminModel circleAdminModel) {
		Example example = new Example(CircleAdminModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circleAdminModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circleAdminModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
