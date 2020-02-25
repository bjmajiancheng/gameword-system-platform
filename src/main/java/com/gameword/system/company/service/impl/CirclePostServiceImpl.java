/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.service.impl;

import java.util.List;

import com.gameword.system.common.baseservice.impl.BaseService;
import com.gameword.system.company.dao.CirclePostMapper;
import com.gameword.system.company.model.CirclePostModel;
import com.gameword.system.company.service.ICirclePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service("circlePostService")
public class CirclePostServiceImpl extends BaseService<CirclePostModel> implements ICirclePostService {
	@Autowired
	private CirclePostMapper circlePostMapper;
	
	@Override
	public CirclePostModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return circlePostMapper.findById(id);
	}


	public List<CirclePostModel> find(Map<String, Object> param) {
		return circlePostMapper.find(param);
	}

	@Override
	public PageInfo<CirclePostModel> selectByFilterAndPage(CirclePostModel circlePostModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CirclePostModel> list = this.selectByFilter(circlePostModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CirclePostModel> selectByFilter(CirclePostModel circlePostModel) {
		Example example = new Example(CirclePostModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(circlePostModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(circlePostModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
