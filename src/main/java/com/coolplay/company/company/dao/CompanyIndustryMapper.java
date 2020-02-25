/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.dao;
import com.coolplay.company.company.model.CompanyIndustryModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import com.coolplay.company.company.dao.*;
import com.coolplay.company.company.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CompanyIndustryMapper extends Mapper<CompanyIndustryModel> {

	public List<CompanyIndustryModel> find(Map<String, Object> param);

	public CompanyIndustryModel findById(@Param("id") Integer id);

	/**
	 * 根据企业ID删除企业行业关联信息
	 *
	 * @param companyId
	 * @return
	 */
	public int delByCompanyId(@Param("companyId")Integer companyId);

}
