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
@RequestMapping("/api/common/country")
public class CountryController {

    @Autowired
    private ICountryService countryService;

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(CountryModel countryModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        countryModel.setIsDel(0);
        PageInfo<CountryModel> pageInfo = countryService.selectByFilterAndPage(countryModel, pageNum, pageSize);

        if(CollectionUtils.isNotEmpty(pageInfo.getList())) {
            Set<Integer> userIds = new HashSet<Integer>();
            for(CountryModel tmpCountry : pageInfo.getList()) {
                userIds.add(tmpCountry.getCreateUserId());
                userIds.add(tmpCountry.getUpdateUserId());
            }

            Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(new ArrayList<Integer>(userIds));
            for(CountryModel tmpCountry : pageInfo.getList()) {
                UserModel createUser = userMap.get(tmpCountry.getCreateUserId());
                if(createUser != null) {
                    tmpCountry.setCreateUserName(createUser.getUserName());
                }
                UserModel updateUser = userMap.get(tmpCountry.getUpdateUserId());
                if(updateUser != null) {
                    tmpCountry.setUpdateUserName(updateUser.getUserName());
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
        CountryModel functionModel = countryService.findById(id);

        return ResponseUtil.success(functionModel);
    }

    /**
     * 添加公司部门信息
     *
     * @param countryModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(CountryModel countryModel) {

        countryModel.setCreateUserId(SecurityUtil.getCurrentUserId());
        countryModel.setUpdateUserId(SecurityUtil.getCurrentUserId());
        int addCnt = countryService.saveNotNull(countryModel);

        return ResponseUtil.success();
    }

    /**
     * 添加公司部门信息
     *
     * @param countryModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(CountryModel countryModel) {

        countryModel.setUpdateUserId(SecurityUtil.getCurrentUserId());
        int updateCnt = countryService.updateNotNull(countryModel);

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public Result options(CountryModel countryModel) {
        countryModel.setIsDel(0);
        List<CountryModel> countryModels = countryService.selectByFilter(countryModel);

        List<Option> options = new ArrayList<Option>();
        if(CollectionUtils.isNotEmpty(countryModels)) {
            for(CountryModel tmpCountry : countryModels) {
                options.add(new Option(String.format("%s/%s", tmpCountry.getCountryCnName(), tmpCountry.getCountryEnName()), tmpCountry.getId()));
            }
        }

        return ResponseUtil.success(options);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(@RequestParam(value = "id") Integer id) {
        CountryModel countryModel = new CountryModel();
        countryModel.setId(id);
        countryModel.setIsDel(1);
        int updateCnt = countryService.updateNotNull(countryModel);

        return ResponseUtil.success();

    }
}
