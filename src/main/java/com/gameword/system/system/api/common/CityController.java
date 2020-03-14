package com.gameword.system.system.api.common;

import com.alibaba.druid.util.StringUtils;
import com.gameword.system.common.utils.Option;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.FunctionModel;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.service.IUserService;
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
    @RequestMapping(value = "/getCity", method = RequestMethod.GET)
    public Result getCity(@RequestParam("id") int id) {
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
    @RequestMapping(value = "/saveCity", method = RequestMethod.POST)
    public Result saveCity(CityModel cityModel) {
        int addCnt = cityService.save(cityModel);

        return ResponseUtil.success();
    }

    /**
     * 添加公司部门信息
     *
     * @param cityModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCity", method = RequestMethod.POST)
    public Result updateCity(CityModel cityModel) {
        int updateCnt = cityService.updateNotNull(cityModel);

        return ResponseUtil.success();
    }


    @ResponseBody
    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public Result options(CityModel cityModel) {
        List<CityModel> cityModels = cityService.selectAll();

        List<Option> options = new ArrayList<Option>();
        if(CollectionUtils.isNotEmpty(cityModels)) {
            for(CityModel tmpCity : cityModels) {
                options.add(new Option(String.format("%s/%s", tmpCity.getCityCn(), tmpCity.getCityEn()), tmpCity.getId()));
            }
        }

        return ResponseUtil.success(options);
    }
}
