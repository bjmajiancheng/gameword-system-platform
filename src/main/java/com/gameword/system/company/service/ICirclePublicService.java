/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.service;

import com.gameword.system.company.model.CirclePublicModel;
import com.github.pagehelper.PageInfo;
import java.util.*;

import com.gameword.system.common.baseservice.IBaseService;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICirclePublicService extends IBaseService<CirclePublicModel> {

	public CirclePublicModel findById(Integer id);

	public List<CirclePublicModel> find(Map<String, Object> param);

	public PageInfo<CirclePublicModel> selectByFilterAndPage(CirclePublicModel circlePublicModel, int pageNum,
			int pageSize);

	public List<CirclePublicModel> selectByFilter(CirclePublicModel circlePublicModel);

	public CirclePublicModel findLastPublicByCircleId(Integer circleId);

}
