/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gameword.system.company.dao.CoolplayBaseLabelMapper;
import com.gameword.system.company.service.ICoolplayBaseLabelService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gameword.system.company.model.CoolplayBaseLabelModel;
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

@Service("coolplayBaseLabelService")
public class CoolplayBaseLabelServiceImpl extends BaseService<CoolplayBaseLabelModel> implements
		ICoolplayBaseLabelService {
	@Autowired
	private CoolplayBaseLabelMapper coolplayBaseLabelMapper;
	
	@Override
	public CoolplayBaseLabelModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return coolplayBaseLabelMapper.findById(id);
	}


	public List<CoolplayBaseLabelModel> find(Map<String, Object> param) {
		return coolplayBaseLabelMapper.find(param);
	}

	@Override
	public PageInfo<CoolplayBaseLabelModel> selectByFilterAndPage(CoolplayBaseLabelModel coolplayBaseLabelModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<CoolplayBaseLabelModel> list = this.selectByFilter(coolplayBaseLabelModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<CoolplayBaseLabelModel> selectByFilter(CoolplayBaseLabelModel coolplayBaseLabelModel) {
		Example example = new Example(CoolplayBaseLabelModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(StringUtils.isNotEmpty(coolplayBaseLabelModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(coolplayBaseLabelModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}


	public Map<Integer, List<CoolplayBaseLabelModel>> findMapByBaseIds(List<Integer> baseIds) {
		if(CollectionUtils.isEmpty(baseIds)) {
			return Collections.emptyMap();
		}

		List<CoolplayBaseLabelModel> baseLabelModels = coolplayBaseLabelMapper.find(Collections.singletonMap("coolplayBaseIds", baseIds));
		if(CollectionUtils.isEmpty(baseLabelModels)) {
			return Collections.emptyMap();
		}

		Map<Integer, List<CoolplayBaseLabelModel>> baseLabelModelMap = new HashMap<Integer, List<CoolplayBaseLabelModel>>();
		for(CoolplayBaseLabelModel baseLabelModel : baseLabelModels) {
			List<CoolplayBaseLabelModel> tmpBaseLabelModels = baseLabelModelMap.get(baseLabelModel.getCoolplayBaseId());
			if(CollectionUtils.isEmpty(tmpBaseLabelModels)) {
				tmpBaseLabelModels = new ArrayList<CoolplayBaseLabelModel>();
			}
			tmpBaseLabelModels.add(baseLabelModel);
			baseLabelModelMap.put(baseLabelModel.getCoolplayBaseId(), tmpBaseLabelModels);
		}

		return baseLabelModelMap;
	}

	public int delByBaseId(Integer baseId) {
		if(baseId == null || baseId <= 0) {
			return 0;
		}

		return coolplayBaseLabelMapper.delByBaseId(baseId);
	}
}
