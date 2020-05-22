/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.service;

import com.gameword.system.common.baseservice.IBaseService;
import com.gameword.system.system.model.PriceConfRecordModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.*;
import com.gameword.system.system.dao.*;
import com.gameword.system.system.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface IPriceConfRecordService extends IBaseService<PriceConfRecordModel> {

	public PriceConfRecordModel findById(Integer id);

	public List<PriceConfRecordModel> find(Map<String, Object> param);

	public PageInfo<PriceConfRecordModel> selectByFilterAndPage(PriceConfRecordModel priceConfRecordModel, int pageNum,
			int pageSize);

	public List<PriceConfRecordModel> selectByFilter(PriceConfRecordModel priceConfRecordModel);

}
