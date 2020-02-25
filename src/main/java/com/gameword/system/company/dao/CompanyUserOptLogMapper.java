/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.company.dao;
import com.gameword.system.company.model.CompanyUserOptLogModel;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CompanyUserOptLogMapper extends Mapper<CompanyUserOptLogModel> {

	public List<CompanyUserOptLogModel> find(Map<String, Object> param);

}
