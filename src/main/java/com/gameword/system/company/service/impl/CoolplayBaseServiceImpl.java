/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.service.impl;

import java.util.List;

import com.gameword.system.common.baseservice.impl.BaseService;
import com.gameword.system.company.dao.CoolplayBaseMapper;
import com.gameword.system.company.service.ICoolplayBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gameword.system.company.model.CoolplayBaseModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author davdian
 * @version 1.0
 * @since 1.0
 */

@Service("coolplayBaseService")
public class CoolplayBaseServiceImpl extends BaseService<CoolplayBaseModel> implements ICoolplayBaseService {

    @Autowired
    private CoolplayBaseMapper coolplayBaseMapper;

    @Override
    public CoolplayBaseModel findById(Integer id) {
        if (id == null) {
            return null;
        }
        return coolplayBaseMapper.findById(id);
    }

    public List<CoolplayBaseModel> find(Map<String, Object> param) {
        return coolplayBaseMapper.find(param);
    }

    @Override
    public PageInfo<CoolplayBaseModel> selectByFilterAndPage(CoolplayBaseModel coolplayBaseModel, int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CoolplayBaseModel> list = this.selectByFilter(coolplayBaseModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<CoolplayBaseModel> selectByFilter(CoolplayBaseModel coolplayBaseModel) {
        Example example = new Example(CoolplayBaseModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(coolplayBaseModel.getBaseName())) {
            criteria.andLike("baseName", "%" + coolplayBaseModel.getBaseName() + "%");
        }

        if (coolplayBaseModel.getIsClose() != null) {
            criteria.andEqualTo("isClose", coolplayBaseModel.getIsClose());
        }

        if (coolplayBaseModel.getCompanyId() != null) {
            criteria.andEqualTo("companyId", coolplayBaseModel.getCompanyId());
        }

        if(coolplayBaseModel.getIsDel() != null) {
            criteria.andEqualTo("isDel", coolplayBaseModel.getIsDel());
        }

        if (StringUtils.isNotEmpty(coolplayBaseModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(coolplayBaseModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }
}
