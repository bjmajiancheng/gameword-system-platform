/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.api.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.security.utils.SecurityUtil;
import com.gameword.system.system.model.PriceConfRecordModel;
import com.gameword.system.system.model.SensitiveWordModel;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gameword.system.system.model.PriceConfModel;

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
@RequestMapping(value = "/api/common/priceConf")
public class PriceConfController {

	@Autowired
	private IPriceConfService priceConfService;

	@Autowired
	private IPriceConfRecordService priceConfRecordService;

	/**
	 * 获取
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Result get(@RequestParam("id") int id) {
		PriceConfModel priceConfModel = priceConfService.findById(id);

		return ResponseUtil.success(priceConfModel);
	}

	/**
	 * 获取当前系统价格信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/currPriceConf", method = RequestMethod.GET)
	public Result currPriceConf() {
		List<PriceConfModel> priceConfModels = priceConfService.find(Collections.emptyMap());
		PriceConfModel currPriceConf = new PriceConfModel();
		if(CollectionUtils.isNotEmpty(priceConfModels)) {
			currPriceConf = priceConfModels.get(0);
		} else {
			currPriceConf.setPriceCn(BigDecimal.ZERO);
			currPriceConf.setPriceEn(BigDecimal.ZERO);
		}

		return ResponseUtil.success(currPriceConf);
	}

	/**
	 * 添加
	 *
	 * @param priceConfModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Result save(PriceConfModel priceConfModel) {

		priceConfModel.setUpdateUserId(SecurityUtil.getCurrentUserId());
		List<PriceConfModel> priceConfModels = priceConfService.find(Collections.emptyMap());
		String beforeDesc = "";
		if(CollectionUtils.isEmpty(priceConfModels)) {
			beforeDesc = "中文学员:0人民币元/分钟, 英文学员:0美元/分钟";
			int addCnt = priceConfService.saveNotNull(priceConfModel);
		} else {
			PriceConfModel tmpPriceConf = priceConfModels.get(0);
			beforeDesc = String.format("中文学员:%.2f人民币元/分钟, 英文学员:%.2f美元/分钟", tmpPriceConf.getPriceCn(), tmpPriceConf.getPriceEn());

			tmpPriceConf.setPriceCn(priceConfModel.getPriceCn());
			tmpPriceConf.setPriceEn(priceConfModel.getPriceEn());
			int updateCnt = priceConfService.updateNotNull(tmpPriceConf);
		}

		String afterDesc = String.format("中文学员:%.2f人民币元/分钟, 英文学员:%.2f美元/分钟", priceConfModel.getPriceCn(), priceConfModel.getPriceEn());
		//添加价格记录
		PriceConfRecordModel priceConfRecordModel = new PriceConfRecordModel();
		priceConfRecordModel.setBeforeDesc(beforeDesc);
		priceConfRecordModel.setAfterDesc(afterDesc);
		priceConfRecordModel.setUserId(SecurityUtil.getCurrentUserId());
		priceConfRecordService.saveNotNull(priceConfRecordModel);

		return ResponseUtil.success();
	}

	/**
	 * 修改
	 *
	 * @param priceConfModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Result update(PriceConfModel priceConfModel) {

		int updateCnt = priceConfService.updateNotNull(priceConfModel);

		return ResponseUtil.success();
	}


	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Result delete(@RequestParam(value = "id") Integer id) {
		PriceConfModel priceConfModel = new PriceConfModel();
		priceConfModel.setId(id);
		int delCnt = priceConfService.delete(priceConfModel);

		return ResponseUtil.success();
	}
}
