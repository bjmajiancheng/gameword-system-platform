/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.dao;
import com.gameword.system.system.model.AdvertisementModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import com.gameword.system.system.dao.*;
import com.gameword.system.system.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface AdvertisementMapper extends Mapper<AdvertisementModel> {

	public List<AdvertisementModel> find(Map<String, Object> param);

	public AdvertisementModel findById(@Param("id")Integer id);

}
