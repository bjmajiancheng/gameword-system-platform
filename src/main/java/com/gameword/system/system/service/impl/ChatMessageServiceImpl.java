/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gameword.system.common.baseservice.impl.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gameword.system.system.model.ChatMessageModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import com.gameword.system.system.dao.*;
import com.gameword.system.system.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("chatMessageService")
public class ChatMessageServiceImpl extends BaseService<ChatMessageModel> implements IChatMessageService{
	@Autowired
	private ChatMessageMapper chatMessageMapper;
	
	@Override
	public ChatMessageModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return chatMessageMapper.findById(id);
	}


	public List<ChatMessageModel> find(Map<String, Object> param) {
		return chatMessageMapper.find(param);
	}

	@Override
	public PageInfo<ChatMessageModel> selectByFilterAndPage(ChatMessageModel chatMessageModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<ChatMessageModel> list = this.selectByFilter(chatMessageModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<ChatMessageModel> selectByFilter(ChatMessageModel chatMessageModel) {
		Example example = new Example(ChatMessageModel.class);
		Example.Criteria criteria = example.createCriteria();

		if (StringUtils.isNotEmpty(chatMessageModel.getStartDate())) {
			criteria.andGreaterThanOrEqualTo("chatTime", chatMessageModel.getStartDate());
		}

		if (StringUtils.isNotEmpty(chatMessageModel.getEndDate())) {
			criteria.andLessThanOrEqualTo("chatTime", chatMessageModel.getEndDate());
		}

		if (StringUtils.isNotEmpty(chatMessageModel.getUserName())) {
			String userName = chatMessageModel.getUserName();
			criteria.andCondition(" user_id like '%"+ userName +"%' or user_name like '%"+ userName +"%' or nick_name like '%" + userName + "%' ");
		}

		if (StringUtils.isNotEmpty(chatMessageModel.getChatroomId())) {
			criteria.andEqualTo("chatroomId", chatMessageModel.getChatroomId());
		}

		if (StringUtils.isNotEmpty(chatMessageModel.getContent())) {
			criteria.andLike("content", chatMessageModel.getContent());
		}

		if (StringUtils.isNotEmpty(chatMessageModel.getSensitiveWord())) {
			criteria.andLike("sensitiveWord", chatMessageModel.getSensitiveWord());
		}

		if(StringUtils.isNotEmpty(chatMessageModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(chatMessageModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
