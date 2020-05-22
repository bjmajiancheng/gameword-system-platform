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
import com.gameword.system.system.model.SensitiveWordModel;
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

@Service("sensitiveWordService")
public class SensitiveWordServiceImpl extends BaseService<SensitiveWordModel> implements ISensitiveWordService{
	@Autowired
	private SensitiveWordMapper sensitiveWordMapper;
	
	@Override
	public SensitiveWordModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return sensitiveWordMapper.findById(id);
	}


	public List<SensitiveWordModel> find(Map<String, Object> param) {
		return sensitiveWordMapper.find(param);
	}

	@Override
	public PageInfo<SensitiveWordModel> selectByFilterAndPage(SensitiveWordModel sensitiveWordModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SensitiveWordModel> list = this.selectByFilter(sensitiveWordModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<SensitiveWordModel> selectByFilter(SensitiveWordModel sensitiveWordModel) {
		Example example = new Example(SensitiveWordModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(sensitiveWordModel.getIsDel() != null) {
			criteria.andEqualTo("isDel", sensitiveWordModel.getIsDel());
		}

		if(StringUtils.isNotEmpty(sensitiveWordModel.getSensitiveWord())) {
			criteria.andLike("sensitiveWord", "%" + sensitiveWordModel.getSensitiveWord() + "%");
		}

		if(sensitiveWordModel.getLanguage() != null) {
			criteria.andEqualTo("language", sensitiveWordModel.getLanguage());
		}

		if(StringUtils.isNotEmpty(sensitiveWordModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(sensitiveWordModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
