/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.service.impl;

import java.util.List;

import com.gameword.system.common.baseservice.impl.BaseService;
import com.gameword.system.company.dao.CircleMapper;
import com.gameword.system.company.model.CircleModel;
import com.gameword.system.company.service.ICircleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service("circleService")
public class CircleServiceImpl extends BaseService<CircleModel> implements ICircleService {

    @Autowired
    private CircleMapper circleMapper;

    @Override
    public CircleModel findById(Integer id) {
        if (id == null) {
            return null;
        }
        return circleMapper.findById(id);
    }

    public List<CircleModel> find(Map<String, Object> param) {
        return circleMapper.find(param);
    }

    @Override
    public PageInfo<CircleModel> selectByFilterAndPage(CircleModel circleModel, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CircleModel> list = this.selectByFilter(circleModel);
        return new PageInfo<>(list);
    }

    @Override
    public List<CircleModel> selectByFilter(CircleModel circleModel) {
        Example example = new Example(CircleModel.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(circleModel.getSortWithOutOrderBy())) {
            example.setOrderByClause(circleModel.getSortWithOutOrderBy());
        }
        return getMapper().selectByExample(example);
    }

    public Map<Integer, CircleModel> findMapByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }

        List<CircleModel> circleModels = this.find(Collections.singletonMap("ids", ids));
        Map<Integer, CircleModel> circleModelMap = new HashMap<Integer, CircleModel>();
        if(CollectionUtils.isNotEmpty(circleModels)) {
            for(CircleModel circleModel: circleModels) {
                circleModelMap.put(circleModel.getId(), circleModel);
            }
        }

        return circleModelMap;
    }
}
