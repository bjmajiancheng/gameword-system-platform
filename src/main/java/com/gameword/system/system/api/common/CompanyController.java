/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.api.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.model.CompanyLabelModel;
import com.gameword.system.system.model.LabelModel;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gameword.system.system.model.CompanyModel;

import java.util.*;
import com.gameword.system.system.dao.*;
import com.gameword.system.system.service.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/api/common/company")
public class CompanyController {

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private ICompanyLabelService companyLabelService;

	@Autowired
	private ICityService cityService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map list(CompanyModel companyModel,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
		PageInfo<CompanyModel> pageInfo = companyService.selectByFilterAndPage(companyModel, pageNum, pageSize);

		if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
			List<Integer> cityIds = new ArrayList<Integer>();
			for(CompanyModel tmpCompany : pageInfo.getList()) {
				cityIds.add(tmpCompany.getCityId());
			}

			Map<Integer, CityModel> cityMap = cityService.findMapByIds(cityIds);
			for(CompanyModel tmpCompany : pageInfo.getList()) {
				CityModel cityModel = cityMap.get(tmpCompany.getCityId());
				if(cityModel != null) {
					tmpCompany.setCityCnName(cityModel.getCityCn());
					tmpCompany.setCityEnName(cityModel.getCityEn());
				}
			}
		}

		return PageConvertUtil.grid(pageInfo);
	}

	/**
	 * 获取
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Result get(@RequestParam("id") int id) {
		CompanyModel companyModel = companyService.findById(id);

		List<Integer> cnLabelIds = companyLabelService.getLabelIds(id, 1);
		if(CollectionUtils.isNotEmpty(cnLabelIds)) {
			companyModel.setCnLabelIds(cnLabelIds);
		}
		List<Integer> enLabelIds = companyLabelService.getLabelIds(id, 2);
		if(CollectionUtils.isNotEmpty(enLabelIds)) {
			companyModel.setEnLabelIds(enLabelIds);
		}

		return ResponseUtil.success(companyModel);
	}

	/**
	 * 添加
	 *
	 * @param companyModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(CompanyModel companyModel) {

		int addCnt = companyService.saveNotNull(companyModel);

		if(CollectionUtils.isNotEmpty(companyModel.getCnLabelIds())) {
			for(Integer cnLabelId : companyModel.getCnLabelIds()) {
				CompanyLabelModel companyLabelModel = new CompanyLabelModel();
				companyLabelModel.setCompanyId(companyModel.getId());
				companyLabelModel.setLabelId(cnLabelId);

				companyLabelService.saveNotNull(companyLabelModel);
			}
		}

		if(CollectionUtils.isNotEmpty(companyModel.getEnLabelIds())) {
			for(Integer enLabelId : companyModel.getEnLabelIds()) {
				CompanyLabelModel companyLabelModel = new CompanyLabelModel();
				companyLabelModel.setCompanyId(companyModel.getId());
				companyLabelModel.setLabelId(enLabelId);

				companyLabelService.saveNotNull(companyLabelModel);
			}
		}

		return ResponseUtil.success();
	}

	/**
	 * 添加公司部门信息
	 *
	 * @param companyModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(CompanyModel companyModel) {

		int updateCnt = companyService.updateNotNull(companyModel);

		int delCnt = companyLabelService.delByCompanyId(companyModel.getId());

		if(CollectionUtils.isNotEmpty(companyModel.getCnLabelIds())) {
			for(Integer cnLabelId : companyModel.getCnLabelIds()) {
				CompanyLabelModel companyLabelModel = new CompanyLabelModel();
				companyLabelModel.setCompanyId(companyModel.getId());
				companyLabelModel.setLabelId(cnLabelId);

				companyLabelService.saveNotNull(companyLabelModel);
			}
		}

		if(CollectionUtils.isNotEmpty(companyModel.getEnLabelIds())) {
			for(Integer enLabelId : companyModel.getEnLabelIds()) {
				CompanyLabelModel companyLabelModel = new CompanyLabelModel();
				companyLabelModel.setCompanyId(companyModel.getId());
				companyLabelModel.setLabelId(enLabelId);

				companyLabelService.saveNotNull(companyLabelModel);
			}
		}

		return ResponseUtil.success();
	}


	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Result delete(@RequestParam(value = "id") Integer id) {
		CompanyModel companyModel = new CompanyModel();
		companyModel.setId(id);
		int delCnt = companyService.delete(companyModel);

		return ResponseUtil.success();

	}

}
