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
import com.gameword.system.system.model.LabelModel;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gameword.system.system.model.SensitiveWordModel;

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
@RequestMapping(value = "/api/common/sensitiveWord")
public class SensitiveWordController {

	@Autowired
	private ISensitiveWordService sensitiveWordService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map list(SensitiveWordModel sensitiveWordModel,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
		sensitiveWordModel.setIsDel(0);
		PageInfo<SensitiveWordModel> pageInfo = sensitiveWordService.selectByFilterAndPage(sensitiveWordModel, pageNum, pageSize);

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
		SensitiveWordModel sensitiveWordModel = sensitiveWordService.findById(id);

		return ResponseUtil.success(sensitiveWordModel);
	}

	/**
	 * 添加
	 *
	 * @param sensitiveWordModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(SensitiveWordModel sensitiveWordModel) {

		int addCnt = sensitiveWordService.saveNotNull(sensitiveWordModel);

		return ResponseUtil.success();
	}

	/**
	 * 修改
	 *
	 * @param sensitiveWordModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(SensitiveWordModel sensitiveWordModel) {

		int updateCnt = sensitiveWordService.updateNotNull(sensitiveWordModel);

		return ResponseUtil.success();
	}


	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Result delete(@RequestParam(value = "id") Integer id) {
		SensitiveWordModel sensitiveWordModel = new SensitiveWordModel();
		sensitiveWordModel.setId(id);
		sensitiveWordModel.setIsDel(1);
		int updateCnt = sensitiveWordService.updateNotNull(sensitiveWordModel);

		return ResponseUtil.success();
	}

}
