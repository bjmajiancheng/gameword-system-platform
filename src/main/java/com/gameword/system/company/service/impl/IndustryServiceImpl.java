/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.service.impl;

import java.util.List;

import com.gameword.system.common.baseservice.impl.BaseService;
import com.gameword.system.company.dao.IndustryMapper;
import com.gameword.system.company.model.IndustryModel;
import com.gameword.system.company.service.IIndustryService;
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

@Service("industryService")
public class IndustryServiceImpl extends BaseService<IndustryModel> implements IIndustryService {
	@Autowired
	private IndustryMapper industryMapper;
	
	@Override
	public IndustryModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return industryMapper.findById(id);
	}


	public List<IndustryModel> find(Map<String, Object> param) {
		return industryMapper.find(param);
	}

	@Override
	public PageInfo<IndustryModel> selectByFilterAndPage(IndustryModel industryModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<IndustryModel> list = this.selectByFilter(industryModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<IndustryModel> selectByFilter(IndustryModel industryModel) {
		Example example = new Example(IndustryModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(industryModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(industryModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
