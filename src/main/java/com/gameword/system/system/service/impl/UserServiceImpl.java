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
import com.gameword.system.security.dto.FunctionDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gameword.system.system.model.UserModel;
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

@Service("appUserService")
public class UserServiceImpl extends BaseService<UserModel> implements IUserService{
	@Autowired
	private AppUserMapper appUserMapper;
	
	@Override
	public UserModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return appUserMapper.findById(id);
	}


	public List<UserModel> find(Map<String, Object> param) {
		return appUserMapper.find(param);
	}

	/**
	 * 根据登录名获取用户信息
	 *
	 * @param loginName
	 * @return
	 */
	public UserModel findUserByLoginName(String loginName) {
		return appUserMapper.findUserByLoginName(loginName);
	}

	@Override
	public List<Integer> findUserRoleByUserId(int id) {
		return appUserMapper.findUserRoleByUserId(id);
	}

	/**
	 * 根据登录名获取用户权限信息
	 *
	 * @param loginName
	 * @return
	 */
	public List<FunctionDto> findUserFunctionByLoginName(String loginName) {
		return appUserMapper.findUserFunctionByLoginName(loginName);
	}

	/**
	 * 根据用户ID获取登录名
	 *
	 * @param id
	 * @return
	 */
	public String findLoginNameByUserId(Integer id) {
		return appUserMapper.findLoginNameByUserId(id);
	}

	public void updateLastLoginInfoByUserName(String username, Date date, String remoteAddr) {
		appUserMapper.updateLastLoginInfoByUserName(username, date, remoteAddr);
	}

	@Override
	public PageInfo<UserModel> selectByFilterAndPage(UserModel userModel, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserModel> list = selectByFilter(userModel);
		if(CollectionUtils.isNotEmpty(list)) {
			for (UserModel user : list) {
				user.setPassword("");
			}
		}
		return new PageInfo<>(list);
	}

	@Override
	public List<UserModel> selectByFilter(UserModel userModel) {
		Example example = new Example(UserModel.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(userModel.getUserName())) {
			criteria.andLike("userName", "%" + userModel.getUserName() + "%");
		}
		if (StringUtils.isNotEmpty(userModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(userModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}

	@Override
	public UserModel findUserByUserId(int id) {
		UserModel userModel = appUserMapper.findUserByUserId(id);
		/*userModel.setPassword("");*/
		return userModel;
	}


	@Override
	public List<UserModel> selectUserRoleByUserId(int id) {
		return appUserMapper.find(Collections.singletonMap("id", id));
	}

	@Override
	public Map<Integer, UserModel> findUserMapByUserIds(List<Integer> userIds) {
		if(CollectionUtils.isEmpty(userIds)) {
			return Collections.emptyMap();
		}
		List<UserModel> userModels = appUserMapper.find(Collections.singletonMap("userIds", userIds));
		if(CollectionUtils.isEmpty(userModels)) {
			return Collections.emptyMap();
		}

		Map<Integer, UserModel> userModelMap = new HashMap<Integer, UserModel>();
		for(UserModel userModel : userModels) {
			userModelMap.put(userModel.getId(), userModel);
		}

		return userModelMap;
	}
}
