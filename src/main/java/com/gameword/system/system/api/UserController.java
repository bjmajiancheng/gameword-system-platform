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
import com.gameword.system.core.model.RoleModel;
import com.gameword.system.core.model.UserRoleModel;
import com.gameword.system.security.security.SystemUserCache;
import com.gameword.system.security.service.IRoleService;
import com.gameword.system.security.utils.SecurityUtil;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.model.CountryModel;
import com.gameword.system.system.model.UserModel;
import com.gameword.system.system.model.UserPassMappingModel;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import com.gameword.system.system.service.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Controller("appUserController")
@RequestMapping("/api/system/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private SystemUserCache systemUserCache;

	@Autowired
	private ISystemUserRoleService systemUserRoleService;

	@Autowired
	private IUserPassMappingService userPassMappingService;

	@Autowired
	private ICountryService countryService;

	@Autowired
	private ICityService cityService;

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map list(UserModel userModel,
					@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
					@RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {

		PageInfo<UserModel> pageInfo = userService.selectByFilterAndPage(userModel, pageNum, pageSize);
		if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
			List<Integer> userIds = new ArrayList<Integer>(pageInfo.getList().size());
			List<Integer> countryIds = new ArrayList<Integer>();
			List<Integer> cityIds = new ArrayList<>();
			for(UserModel tmpUserModel : pageInfo.getList()) {
				userIds.add(tmpUserModel.getId());
				countryIds.add(tmpUserModel.getCountryId());
				cityIds.add(tmpUserModel.getCityId());
			}

			//集中查询数据库
			Map<Integer, CountryModel> countryModelMap = countryService.findMapByIds(countryIds);
			Map<Integer, CityModel> cityModelMap = cityService.findMapByIds(cityIds);

			for(UserModel tmpUserModel : pageInfo.getList()) {
				CountryModel tmpCountryModel = countryModelMap.get(tmpUserModel.getCountryId());
				if(tmpCountryModel != null)
					tmpUserModel.setCountryName(tmpCountryModel.getCountryCnName());
				else
					tmpUserModel.setCountryName("未知");

				CityModel tmpCityModel = cityModelMap.get(tmpUserModel.getCityId());
				if(tmpCityModel != null)
					tmpUserModel.setCityName(tmpCityModel.getCityCn());
				else
					tmpUserModel.setCityName("未知");
			}

//            Map<Integer, List<UserRoleModel>> userRoleModelMap = roleService.findUserRoles(userIds);
//            for(UserModel tmpUserModel : pageInfo.getList()) {
//                List<UserRoleModel> userRoleModels = userRoleModelMap.get(tmpUserModel.getId());
//                StringBuffer sb = new StringBuffer();
//                if(CollectionUtils.isNotEmpty(userRoleModels)) {
//                    for(UserRoleModel userRoleModel : userRoleModels) {
//                        if(sb.length() > 0) {
//                            sb.append("、");
//                        }
//                        sb.append(userRoleModel.getRoleName());
//                    }
//                }
//
//                tmpUserModel.setRoleName(sb.toString());
//            }
		}


		return PageConvertUtil.grid(pageInfo);
	}

	@ResponseBody
	@RequestMapping(value="/getUser", method = RequestMethod.GET)
	public Result getUser(@RequestParam("id") int id) {
		UserModel userModel = userService.findById(id);

		if(userModel == null)
			return  ResponseUtil.error("不存在的用户id：" + id);

		return ResponseUtil.success(userModel);
	}

	@ResponseBody
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public Result resetPassword(@RequestParam("id") int id){

		return ResponseUtil.success();
	}

	@ResponseBody
	@RequestMapping(value="/updateUserEnabled", method = RequestMethod.GET)
	public Result updateUserEnabled(@RequestParam("id") int id, @RequestParam("enabled") Integer enabled) {
		UserModel userModel = new UserModel();
		userModel.setId(id);
		userModel.setEnabled(enabled);
		int updateCnt = userService.updateNotNull(userModel);

		//systemUserCache.removeUserFromCacheByUserId(id);

		return ResponseUtil.success(userModel);
	}

	@ResponseBody
	@RequestMapping(value="/updateUserStatus", method = RequestMethod.GET)
	public Result updateUserStatus(@RequestParam("id") int id, @RequestParam("status") Integer status) {
		UserModel userModel = new UserModel();
		userModel.setId(id);
		userModel.setStatus(status);
		int updateCnt = userService.updateNotNull(userModel);

		//systemUserCache.removeUserFromCacheByUserId(id);

		return ResponseUtil.success(userModel);
	}

	@ResponseBody
	@RequestMapping(value="/updateUser", method = RequestMethod.POST)
	public Result updateUser(UserModel userModel) {

		if(StringUtils.isNotEmpty(userModel.getPassword())) {
			String passwordEncode = SecurityUtil.encodeString(userModel.getPassword());

			UserPassMappingModel userPassMappingModel = new UserPassMappingModel();
			userPassMappingModel.setPassword(userModel.getPassword());
			userPassMappingModel.setPasswordEncode(passwordEncode);
			userPassMappingService.saveNotNull(userPassMappingModel);
			userModel.setPassword(passwordEncode);
		}

		int updateCnt = userService.updateNotNull(userModel);

		int delCnt = systemUserRoleService.deleteByUserId(userModel.getId());
		if(CollectionUtils.isNotEmpty(userModel.getRoleIds())) {
			for(Integer roleId : userModel.getRoleIds()) {
				UserRoleModel userRoleModel = new UserRoleModel();
				userRoleModel.setUserId(userModel.getId());
				userRoleModel.setRoleId(roleId);
				systemUserRoleService.saveNotNull(userRoleModel);
			}
		}

		systemUserCache.removeUserFromCacheByUserId(userModel.getId());

		return ResponseUtil.success();
	}

	@ResponseBody
	@RequestMapping(value="/saveUser", method = RequestMethod.POST)
	public Result saveUser(UserModel userModel) {
		try{
			if(StringUtils.isNotEmpty(userModel.getPassword())) {
				String passwordEncode = SecurityUtil.encodeString(userModel.getPassword());

				UserPassMappingModel userPassMappingModel = new UserPassMappingModel();
				userPassMappingModel.setPassword(userModel.getPassword());
				userPassMappingModel.setPasswordEncode(passwordEncode);
				userPassMappingService.saveNotNull(userPassMappingModel);
				userModel.setPassword(passwordEncode);
			}

			int saveCnt = userService.saveNotNull(userModel);

			if(CollectionUtils.isNotEmpty(userModel.getRoleIds())) {
				for(Integer roleId : userModel.getRoleIds()) {
					UserRoleModel userRoleModel = new UserRoleModel();
					userRoleModel.setUserId(userModel.getId());
					userRoleModel.setRoleId(roleId);
					systemUserRoleService.saveNotNull(userRoleModel);
				}
			}
		} catch(DuplicateKeyException e) {
			e.printStackTrace();
			return ResponseUtil.error("用户名已占用, 请更换其他用户名!!");
		} catch(Exception e) {
			return ResponseUtil.error("系统异常, 请稍后重试!!");
		}

		return ResponseUtil.success();
	}

	/**
	 * 获取用户角色信息
	 *
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserRoles", method = RequestMethod.GET)
	public Result getUserRoles(@RequestParam("id") int userId) {
		Map<Integer, List<UserRoleModel>> userRoleMap = roleService.findUserRoles(Collections.singletonList(userId));

		return ResponseUtil.success(userRoleMap.get(userId));
	}

	@ResponseBody
	@RequestMapping(value = "/getCurrUserRoles", method = RequestMethod.GET)
	public Result getCurrUserRoles() {
		RoleModel roleModel = new RoleModel();
		roleModel.setStatus(1);
		List<RoleModel> roleModels = roleService.selectByFilter(roleModel);

		return ResponseUtil.success(PageConvertUtil.grid(roleModels));
	}

	/**
	 * 更新用户密码
	 *
	 * @param userModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserPass", method = RequestMethod.POST)
	public Result updateUserPass(UserModel userModel) {

		try {
			UserModel nativeUserModel = userService.findUserByUserId(SecurityUtil.getCurrentUserId());

			if(! SecurityUtil.matchString(userModel.getPassword(), nativeUserModel.getPassword())) {
				return ResponseUtil.error("原始密码不正确, 请修改原始密码。");
			}

			userModel.setId(SecurityUtil.getCurrentUserId());
			if(StringUtils.isNotEmpty(userModel.getNewPassword())) {
				String passwordEncode = SecurityUtil.encodeString(userModel.getNewPassword());

				UserPassMappingModel userPassMappingModel = new UserPassMappingModel();
				userPassMappingModel.setPassword(userModel.getNewPassword());
				userPassMappingModel.setPasswordEncode(passwordEncode);
				userPassMappingService.saveNotNull(userPassMappingModel);
				userModel.setPassword(passwordEncode);
			}

			int updateCnt = userService.updateNotNull(userModel);

			systemUserCache.removeUserFromCacheByUserId(userModel.getId());

			return ResponseUtil.success();
		} catch(Exception e) {
			e.printStackTrace();

			return ResponseUtil.error("系统异常, 请售后重试。");
		}
	}
}
