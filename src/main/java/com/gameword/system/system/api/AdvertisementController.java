/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.api;

import java.util.Map;

import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.service.IUserService;
import com.gameword.system.security.utils.SecurityUtil;
import com.gameword.system.system.model.StationModel;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gameword.system.system.model.AdvertisementModel;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
@RequestMapping(value = "/api/advertise")
public class AdvertisementController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IAdvertisementService advertisementService;

	/*
	*
	* */
	@ResponseBody
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public Map list(AdvertisementModel advertiseModel,
					@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
					@RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize){
		PageInfo<AdvertisementModel> pageInfo = advertisementService.selectByFilterAndPage(advertiseModel, pageNum, pageSize);

		if(CollectionUtils.isNotEmpty(pageInfo.getList())){
			Set<Integer> userIds = new HashSet<Integer>();

			for (AdvertisementModel model :
					pageInfo.getList()) {
				userIds.add(model.getUserId());
			}

			Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(new ArrayList<Integer>(userIds));

			for (AdvertisementModel model:
				 pageInfo.getList()) {
				UserModel userModel = userMap.get(model.getUserId());
				if(userModel != null){
					model.setCreateUserName(userModel.getDisplayName());
				}
			}
		}

		return PageConvertUtil.grid(pageInfo);
	}


	/**
	 * 获取开屏页信息
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Result get(@RequestParam("id") int id) {
		AdvertisementModel model = advertisementService.findById(id);

		return ResponseUtil.success(model);
	}

	/**
	 * 添加
	 *
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(AdvertisementModel model) {
		if(model == null) {
			return ResponseUtil.error("系统异常, 请稍后重试");
		}

		try {
			AdvertisementModel tmpModel =  advertisementService.findById(model.getId());

			if(tmpModel != null) {
				return ResponseUtil.error("开屏页信息已存在，请重新添加。");
			}

			model.setStatus(1);
			model.setUserId(SecurityUtil.getCurrentUserId());


			int addCnt = advertisementService.saveNotNull(model);

			return ResponseUtil.success();

		} catch(Exception e) {
			e.printStackTrace();

			return ResponseUtil.error("系统异常, 请稍后重试");
		}
	}

	/**
	 * 更新
	 *
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(AdvertisementModel model) {
		int updateCnt = advertisementService.updateNotNull(model);

		return ResponseUtil.success();
	}

	/**
	 * 更新
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	public Result updateStatus(@RequestParam("id")Integer id, @RequestParam("status")Integer status) {

		AdvertisementModel model = new AdvertisementModel();
		model.setId(id);
		model.setStatus(status);
//		model.setUpdateUserId(SecurityUtil.getCurrentUserId());
		int updateCnt = advertisementService.updateNotNull(model);

		return ResponseUtil.success();
	}

	/**
	 * 更新
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Result delete(@RequestParam(value="id") Integer id) {
		AdvertisementModel model = new AdvertisementModel();

		model.setId(id);
//		model.setIsDel(1);
//		model.setUpdateUserId(SecurityUtil.getCurrentUserId());
		int delCnt = advertisementService.delete(model);

		return ResponseUtil.success();
	}
}
