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

import com.gameword.system.common.utils.Option;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.utils.SecurityUtil;
import com.gameword.system.system.model.CountryModel;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gameword.system.system.model.LabelModel;

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
@RequestMapping(value = "/api/common/label")
public class LabelController {

	@Autowired
	private ILabelService labelService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map list(LabelModel labelModel,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
		labelModel.setIsDel(0);
		PageInfo<LabelModel> pageInfo = labelService.selectByFilterAndPage(labelModel, pageNum, pageSize);

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
		LabelModel labelModel = labelService.findById(id);

		return ResponseUtil.success(labelModel);
	}

	/**
	 * 添加
	 *
	 * @param labelModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(LabelModel labelModel) {

		int addCnt = labelService.saveNotNull(labelModel);

		return ResponseUtil.success();
	}

	/**
	 * 添加公司部门信息
	 *
	 * @param labelModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(LabelModel labelModel) {

		int updateCnt = labelService.updateNotNull(labelModel);

		return ResponseUtil.success();
	}


	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Result delete(@RequestParam(value = "id") Integer id) {
		LabelModel labelModel = new LabelModel();
		labelModel.setId(id);
		labelModel.setIsDel(1);
		int updateCnt = labelService.updateNotNull(labelModel);

		return ResponseUtil.success();

	}

}
