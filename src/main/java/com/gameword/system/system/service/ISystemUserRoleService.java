/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.service;

import com.gameword.system.common.baseservice.IBaseService;
import com.gameword.system.core.model.UserRoleModel;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface ISystemUserRoleService extends IBaseService<UserRoleModel> {

	public List<UserRoleModel> find(Map<String, Object> param);

	public PageInfo<UserRoleModel> selectByFilterAndPage(UserRoleModel userRoleModel, int pageNum, int pageSize);

	public List<UserRoleModel> selectByFilter(UserRoleModel userRoleModel);

	public int deleteByUserId(Integer userId);

}
