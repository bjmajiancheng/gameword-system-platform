package com.gameword.system.system.api.common;

import com.alibaba.druid.util.StringUtils;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.FunctionModel;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.service.ICityService;
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
@RequestMapping("/api/common/city")
public class CityController {

    @Autowired
    private ICityService cityService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(CityModel cityModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        PageInfo<CityModel> pageInfo = cityService.selectByFilterAndPage(cityModel, pageNum, pageSize);

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

}
