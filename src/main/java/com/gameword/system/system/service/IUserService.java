/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.service;

import com.gameword.system.common.baseservice.IBaseService;
import com.gameword.system.security.dto.FunctionDto;
import com.gameword.system.system.model.UserModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.*;
import com.gameword.system.system.dao.*;
import com.gameword.system.system.service.*;
import org.springframework.security.core.userdetails.User;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface IUserService extends IBaseService<UserModel> {

	public UserModel findById(Integer id);

	public List<UserModel> find(Map<String, Object> param);

	public PageInfo<UserModel> selectByFilterAndPage(UserModel userModel, int pageNum,
		int pageSize);

	public List<UserModel> selectByFilter(UserModel userModel);

	/**
	 * 根据登录名获取用户信息
	 *
	 * @param loginName
	 * @return
	 */
	public UserModel findUserByLoginName(String loginName);

	/**
	 * 根据用户ID获取用户角色
	 *
	 * @param userId
	 * @return
	 */
	List<Integer> findUserRoleByUserId(int userId);

	/**
	 * 根据登录名获取用户权限信息
	 *
	 * @param loginName
	 * @return
	 */
	public List<FunctionDto> findUserFunctionByLoginName(String loginName);

	/**
	 * 根据用户ID获取登录名
	 *
	 * @param userId
	 * @return
	 */
	public String findLoginNameByUserId(Integer userId);

	void updateLastLoginInfoByUserName(String username, Date date, String remoteAddr);

	public UserModel findUserByUserId(int userId);

	public List<UserModel> selectUserRoleByUserId(int userId);

	public Map<Integer, UserModel> findUserMapByUserIds(List<Integer> userIds);
}
