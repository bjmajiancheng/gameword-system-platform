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
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.service.*;
import com.gameword.system.system.model.SensitiveWordModel;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gameword.system.system.model.PriceConfRecordModel;

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
@RequestMapping(value = "/api/common/priceConfRecord")
public class PriceConfRecordController {

	@Autowired
	private IPriceConfRecordService priceConfRecordService;

	@Autowired
	private com.gameword.system.security.service.IUserService userService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map list(PriceConfRecordModel priceConfRecordModel,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
		PageInfo<PriceConfRecordModel> pageInfo = priceConfRecordService.selectByFilterAndPage(priceConfRecordModel, pageNum, pageSize);
		if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
			Set<Integer> userIds = new HashSet<Integer>();
			for(PriceConfRecordModel tmpPriceConfRecord : pageInfo.getList()) {
				userIds.add(tmpPriceConfRecord.getUserId());
			}

			Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(new ArrayList<Integer>(userIds));
			for(PriceConfRecordModel tmpPriceConfRecord : pageInfo.getList()) {
				UserModel tmpUserModel = userMap.get(tmpPriceConfRecord.getUserId());

				if(tmpUserModel != null) {
					tmpPriceConfRecord.setUserName(tmpUserModel.getUserName());
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
		PriceConfRecordModel priceConfRecordModel = priceConfRecordService.findById(id);

		return ResponseUtil.success(priceConfRecordModel);
	}

	/**
	 * 添加
	 *
	 * @param priceConfRecordModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(PriceConfRecordModel priceConfRecordModel) {

		int addCnt = priceConfRecordService.saveNotNull(priceConfRecordModel);

		return ResponseUtil.success();
	}

	/**
	 * 修改
	 *
	 * @param priceConfRecordModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(PriceConfRecordModel priceConfRecordModel) {

		int updateCnt = priceConfRecordService.updateNotNull(priceConfRecordModel);

		return ResponseUtil.success();
	}

}
