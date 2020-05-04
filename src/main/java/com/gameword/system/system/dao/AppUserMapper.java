/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.dao;
import com.gameword.system.security.dto.FunctionDto;
import com.gameword.system.system.model.UserModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import com.gameword.system.system.dao.*;
import com.gameword.system.system.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface AppUserMapper extends Mapper<UserModel> {

	public List<UserModel> find(Map<String, Object> param);

	public UserModel findById(@Param("id")Integer id);

	UserModel findUserByLoginName(@Param("loginName") String loginName);

	List<Integer> findUserRoleByUserId(@Param("id")int id);

	/**
	 * 根据登录名获取用户权限信息
	 *
	 * @param loginName
	 * @return
	 */
	public List<FunctionDto> findUserFunctionByLoginName(@Param("loginName")String loginName);

	/**
	 * 根据用户ID获取登录名
	 *
	 * @param id
	 * @return
	 */
	public String findLoginNameByUserId(@Param("id")Integer id);

	int updateLastLoginInfoByUserName(@Param("userName") String username, @Param("lastLoginDate") Date lastLoginDate,
									  @Param("remoteAddr") String remoteAddr);

	public UserModel findUserByUserId(@Param("id")int id);

}
