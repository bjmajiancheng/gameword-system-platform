/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.service.impl;

import java.util.List;

import com.gameword.system.company.dao.CircleMemberMapper;
import com.gameword.system.company.model.CircleMemberModel;
import com.gameword.system.company.service.ICircleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import com.gameword.system.common.baseservice.impl.BaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("circleMemberService")
public class CircleMemberServiceImpl extends BaseService<CircleMemberModel> implements ICircleMemberService {
	@Autowired
	private CircleMemberMapper circleMemberMapper;
	
	@Override
	public CircleMemberModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circleMemberMapper.findById(id);
	}


	public List<CircleMemberModel> find(Map<String, Object> param) {
		return circleMemberMapper.find(param);
	}

	@Override
	public PageInfo<CircleMemberModel> selectByFilterAndPage(CircleMemberModel circleMemberModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CircleMemberModel> list = this.selectByFilter(circleMemberModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CircleMemberModel> selectByFilter(CircleMemberModel circleMemberModel) {
		Example example = new Example(CircleMemberModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circleMemberModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circleMemberModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
