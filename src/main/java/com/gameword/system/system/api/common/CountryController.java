package com.gameword.system.system.api.common;

import com.alibaba.druid.util.StringUtils;
import com.gameword.system.common.utils.Option;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.FunctionModel;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.service.IUserService;
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
                    tmpCountry.setCreateUserName(createUser.getDisplayName());
                }
                UserModel updateUser = userMap.get(tmpCountry.getUpdateUserId());
                if(updateUser != null) {
                    tmpCountry.setUpdateUserName(updateUser.getDisplayName());
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

        int addCnt = countryService.save(countryModel);

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
        int updateCnt = countryService.updateNotNull(countryModel);

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public Result options(CountryModel countryModel) {
        List<CountryModel> countryModels = countryService.selectAll();

        List<Option> options = new ArrayList<Option>();
        if(CollectionUtils.isNotEmpty(countryModels)) {
            for(CountryModel tmpCountry : countryModels) {
                options.add(new Option(String.format("%s/%s", tmpCountry.getCountryCnName(), tmpCountry.getCountryEnName()), tmpCountry.getId()));
            }
        }

        return ResponseUtil.success(options);
    }

}
