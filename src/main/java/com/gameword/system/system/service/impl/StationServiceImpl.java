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
import com.gameword.system.system.model.StationModel;
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

@Service("stationService")
public class StationServiceImpl extends BaseService<StationModel> implements IStationService{
	@Autowired
	private StationMapper stationMapper;
	
	@Override
	public StationModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return stationMapper.findById(id);
	}


	public List<StationModel> find(Map<String, Object> param) {
		return stationMapper.find(param);
	}

	@Override
	public PageInfo<StationModel> selectByFilterAndPage(StationModel stationModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<StationModel> list = this.selectByFilter(stationModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<StationModel> selectByFilter(StationModel stationModel) {
		Example example = new Example(StationModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(stationModel.getCountryId() != null) {
			criteria.andEqualTo("countryId", stationModel.getCountryId());
		}

		if(stationModel.getCityId() != null) {
			criteria.andEqualTo("cityId", stationModel.getCityId());
		}

		if(stationModel.getIsDel() != null) {
			criteria.andEqualTo("isDel", stationModel.getIsDel());
		}

		if(StringUtils.isNotEmpty(stationModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(stationModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}


	public StationModel findByCountryAndCityId(Integer countryId, Integer cityId) {
		StationModel stationModel = new StationModel();
		stationModel.setCountryId(countryId);
		stationModel.setCityId(cityId);
		stationModel.setIsDel(0);

		List<StationModel> stationModels = this.selectByFilter(stationModel);
		if(CollectionUtils.isEmpty(stationModels)) {
			return null;
		}

		return stationModels.get(0);
	}
}
