package com.gameword.system.system.api.common;

import com.alibaba.druid.util.StringUtils;
import com.gameword.system.common.utils.Option;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.FunctionModel;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.service.IUserService;
import com.gameword.system.security.utils.SecurityUtil;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.model.CountryModel;
import com.gameword.system.system.service.ICityService;
import com.gameword.system.system.service.ICountryService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by majiancheng on 2020/3/8.
 */
@Controller
@RequestMapping("/api/common/city")
public class CityController {

    @Autowired
    private ICityService cityService;

    @Autowired
    private ICountryService countryService;

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(CityModel cityModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        cityModel.setIsDel(0);
        PageInfo<CityModel> pageInfo = cityService.selectByFilterAndPage(cityModel, pageNum, pageSize);

        if(CollectionUtils.isNotEmpty(pageInfo.getList())) {

            List<Integer> countryIds = new ArrayList<Integer>();
            Set<Integer> userIds = new HashSet<Integer>();
            for(CityModel tmpCityModel : pageInfo.getList()) {
                countryIds.add(tmpCityModel.getCountryId());

                userIds.add(tmpCityModel.getCreateUserId());
                userIds.add(tmpCityModel.getUpdateUserId());
            }

            Map<Integer, CountryModel> countryMap = countryService.findMapByIds(countryIds);
            Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(new ArrayList<Integer>(userIds));

            for(CityModel tmpCityModel : pageInfo.getList()) {
                CountryModel countryModel = countryMap.get(tmpCityModel.getCountryId());

                if(countryModel != null) {
                    tmpCityModel.setCountryCnName(countryModel.getCountryCnName());
                    tmpCityModel.setCountryEnName(countryModel.getCountryEnName());
                    tmpCityModel.setCountryCode(countryModel.getCode());
                }
                UserModel createUser = userMap.get(tmpCityModel.getCreateUserId());
                if(createUser != null) {
                    tmpCityModel.setCreateUserName(createUser.getDisplayName());
                }
                UserModel updateUser = userMap.get(tmpCityModel.getUpdateUserId());
                if(updateUser != null) {
                    tmpCityModel.setUpdateUserName(updateUser.getDisplayName());
                }
            }
        }

        return PageConvertUtil.grid(pageInfo);
    }

    /**
     * 获取公司部门信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(@RequestParam("id") int id) {
        CityModel cityModel = cityService.findById(id);

        return ResponseUtil.success(cityModel);
    }

    /**
     * 添加公司部门信息
     *
     * @param cityModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(CityModel cityModel) {

        cityModel.setCreateUserId(SecurityUtil.getCurrentUserId());
        cityModel.setUpdateUserId(SecurityUtil.getCurrentUserId());
        int addCnt = cityService.saveNotNull(cityModel);

        return ResponseUtil.success();
    }

    /**
     * 添加公司部门信息
     *
     * @param cityModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(CityModel cityModel) {

        cityModel.setUpdateUserId(SecurityUtil.getCurrentUserId());
        int updateCnt = cityService.updateNotNull(cityModel);

        return ResponseUtil.success();
    }


    @ResponseBody
    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public Result options(CityModel cityModel) {
        cityModel.setIsDel(0);
        List<CityModel> cityModels = cityService.selectByFilter(cityModel);

        List<Option> options = new ArrayList<Option>();
        if(CollectionUtils.isNotEmpty(cityModels)) {
            for(CityModel tmpCity : cityModels) {
                options.add(new Option(String.format("%s/%s", tmpCity.getCityCn(), tmpCity.getCityEn()), tmpCity.getId()));
            }
        }

        return ResponseUtil.success(options);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(@RequestParam(value = "id") Integer id) {
        CityModel cityModel = new CityModel();
        cityModel.setId(id);
        cityModel.setIsDel(1);
        int updateCnt = cityService.updateNotNull(cityModel);

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/onlineCity", method = RequestMethod.GET)
    public Result onlineCity(@RequestParam(value = "id") Integer id, @RequestParam(value = "isOnline") Integer isOnline) {
        CityModel cityModel = new CityModel();
        cityModel.setId(id);
        cityModel.setIsOnline(isOnline);

        cityService.updateNotNull(cityModel);

        return ResponseUtil.success();
    }
}
