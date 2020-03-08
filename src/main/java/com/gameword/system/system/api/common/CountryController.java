package com.gameword.system.system.api.common;

import com.alibaba.druid.util.StringUtils;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.FunctionModel;
import com.gameword.system.system.model.CountryModel;
import com.gameword.system.system.service.ICountryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by majiancheng on 2020/3/8.
 */
@Controller
@RequestMapping("/api/common/country")
public class CountryController {

    @Autowired
    private ICountryService countryService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(CountryModel countryModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        PageInfo<CountryModel> pageInfo = countryService.selectByFilterAndPage(countryModel, pageNum, pageSize);

        return PageConvertUtil.grid(pageInfo);
    }

    /**
     * 获取公司部门信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCountry", method = RequestMethod.GET)
    public Result getCountry(@RequestParam("id") int id) {
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
    @RequestMapping(value = "/saveCountry", method = RequestMethod.POST)
    public Result saveCountry(CountryModel countryModel) {

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
    @RequestMapping(value = "/updateCountry", method = RequestMethod.POST)
    public Result updateFunction(CountryModel countryModel) {
        int updateCnt = countryService.updateNotNull(countryModel);

        return ResponseUtil.success();
    }

}
