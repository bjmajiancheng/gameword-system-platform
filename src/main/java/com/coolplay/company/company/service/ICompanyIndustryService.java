/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.service;

import com.coolplay.company.common.baseservice.IBaseService;
import com.coolplay.company.company.model.CompanyIndustryModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.*;
import com.coolplay.company.company.dao.*;
import com.coolplay.company.company.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ICompanyIndustryService extends IBaseService<CompanyIndustryModel> {

	public CompanyIndustryModel findById(Integer id);

	public List<CompanyIndustryModel> find(Map<String, Object> param);

	public PageInfo<CompanyIndustryModel> selectByFilterAndPage(CompanyIndustryModel companyIndustryModel, int pageNum,
			int pageSize);

	public List<CompanyIndustryModel> selectByFilter(CompanyIndustryModel companyIndustryModel);

	/**
	 * 获取公司行业集合
	 *
	 * @param companyId
	 * @return
     */
	public List<CompanyIndustryModel> findByCompanyId(Integer companyId);

	/**
	 * 根据企业ID删除企业行业关联信息
	 *
	 * @param companyId
	 * @return
     */
	public int delByCompanyId(Integer companyId);

}
