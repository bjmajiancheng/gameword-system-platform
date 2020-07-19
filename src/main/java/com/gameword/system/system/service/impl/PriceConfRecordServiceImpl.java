/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gameword.system.common.baseservice.impl.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gameword.system.system.model.PriceConfRecordModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import com.gameword.system.system.dao.*;
import com.gameword.system.system.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("priceConfRecordService")
public class PriceConfRecordServiceImpl extends BaseService<PriceConfRecordModel> implements IPriceConfRecordService{
	@Autowired
	private PriceConfRecordMapper priceConfRecordMapper;
	
	@Override
	public PriceConfRecordModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return priceConfRecordMapper.findById(id);
	}


	public List<PriceConfRecordModel> find(Map<String, Object> param) {
		return priceConfRecordMapper.find(param);
	}

	@Override
	public PageInfo<PriceConfRecordModel> selectByFilterAndPage(PriceConfRecordModel priceConfRecordModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<PriceConfRecordModel> list = this.selectByFilter(priceConfRecordModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<PriceConfRecordModel> selectByFilter(PriceConfRecordModel priceConfRecordModel) {
		Example example = new Example(PriceConfRecordModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(priceConfRecordModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(priceConfRecordModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
