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

import com.gameword.system.common.utils.ExcelFileUtil;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.model.CountryModel;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gameword.system.system.model.ChatMessageModel;

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
@RequestMapping(value = "/api/common/chatMessage")
public class ChatMessageController {

	@Autowired
	private IChatMessageService chatMessageService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map list(ChatMessageModel chatMessageModel,
					@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
					@RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
		PageInfo<ChatMessageModel> pageInfo = chatMessageService.selectByFilterAndPage(chatMessageModel, pageNum, pageSize);

		this.revertResult(pageInfo.getList());

		return PageConvertUtil.grid(pageInfo);
	}

	@ResponseBody
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(ChatMessageModel chatMessageModel, HttpServletResponse response) {
		try {
			List<ChatMessageModel> list = chatMessageService.selectByFilter(chatMessageModel);

			ExcelFileUtil.responseXlsx(response, "聊天信息统计.xlsx", list);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void revertResult(List<ChatMessageModel> chatMessages) {
		if (CollectionUtils.isEmpty(chatMessages)) {
			return;
		}

		for (ChatMessageModel chatMessage : chatMessages) {
			chatMessage.getHasVoiceStr();
		}
	}
}
