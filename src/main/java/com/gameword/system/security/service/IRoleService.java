package com.gameword.system.security.service;

import com.gameword.system.common.baseservice.IBaseService;
import com.gameword.system.core.model.RoleFunctionModel;
import com.gameword.system.core.model.RoleModel;
import com.gameword.system.core.model.UserRoleModel;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/16.
 */
public interface IRoleService extends IBaseService<RoleModel> {

    List<Map> findRoleMatchUpFunctions();

    public PageInfo<RoleModel> selectByFilterAndPage(RoleModel roleModel, int pageNum, int pageSize);

    public List<RoleModel> selectByFilter(RoleModel roleModel);

    public RoleModel selectById(int id);

    public Map<Integer, List<UserRoleModel>> findUserRoles(List<Integer> userIds);

    public List<Integer> getUserIdsByRoleId(int roleId);

    public List<RoleFunctionModel> getRoleFunctionByRoleId(int roleId);

    List<RoleModel> find(Map<String, Object> param);
}
