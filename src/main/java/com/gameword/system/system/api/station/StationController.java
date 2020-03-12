package com.gameword.system.system.api.station;

import com.alibaba.druid.util.StringUtils;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.FunctionModel;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.service.IUserService;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.model.CountryModel;
import com.gameword.system.system.model.StationModel;
import com.gameword.system.system.service.ICityService;
import com.gameword.system.system.service.ICountryService;
import com.gameword.system.system.service.IStationDetailService;
import com.gameword.system.system.service.IStationService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by majiancheng on 2020/3/8.
 */
@Controller
@RequestMapping("/api/station/station")
public class StationController {

    @Autowired
    private IStationService stationService;

    @Autowired
    private IStationDetailService stationDetailService;

    @Autowired
    private ICountryService countryService;

    @Autowired
    private ICityService cityService;

    @Autowired
    private IUserService userService;


    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(StationModel stationModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        PageInfo<StationModel> pageInfo = stationService.selectByFilterAndPage(stationModel, pageNum, pageSize);

        if(CollectionUtils.isNotEmpty(pageInfo.getList())) {

            Set<Integer> userIds = new HashSet<Integer>();
            Set<Integer> countryIds = new HashSet<Integer>();
            Set<Integer> cityIds = new HashSet<Integer>();
            for(StationModel tmpStation : pageInfo.getList()) {
                userIds.add(tmpStation.getCreateUserId());
                userIds.add(tmpStation.getUpdateUserId());
                countryIds.add(tmpStation.getCountryId());
                cityIds.add(tmpStation.getCityId());
            }

            Map<Integer, UserModel> userMap = userService.findUserMapByUserIds(new ArrayList<Integer>(userIds));
            Map<Integer, CountryModel> countryMap = countryService.findMapByIds(new ArrayList<Integer>(countryIds));
            Map<Integer, CityModel> cityMap = cityService.findMapByIds(new ArrayList<Integer>(cityIds));

            for(StationModel tmpStation : pageInfo.getList()) {
                UserModel createUser = userMap.get(tmpStation.getCreateUserId());
                if(createUser != null) {
                    tmpStation.setCreateUserName(createUser.getDisplayName());
                }

                UserModel updateUser = userMap.get(tmpStation.getUpdateUserId());
                if(updateUser != null) {
                    tmpStation.setUpdateUserName(updateUser.getDisplayName());
                }

                CountryModel countryModel = countryMap.get(tmpStation.getCountryId());
                if(countryModel != null) {
                    tmpStation.setCountryCnName(countryModel.getCountryCnName());
                    tmpStation.setCountryEnName(countryModel.getCountryEnName());
                }

                CityModel cityModel = cityMap.get(tmpStation.getCityId());
                if(cityModel != null) {
                    tmpStation.setCityCnName(cityModel.getCityCn());
                    tmpStation.setCityEnName(cityModel.getCityEn());
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
        StationModel stationModel = stationService.findById(id);

        return ResponseUtil.success(stationModel);
    }

    /**
     * 添加公司部门信息
     *
     * @param stationModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(StationModel stationModel) {
        int addCnt = stationService.save(stationModel);

        return ResponseUtil.success();
    }

    /**
     * 添加公司部门信息
     *
     * @param stationModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(StationModel stationModel) {
        int updateCnt = stationService.updateNotNull(stationModel);

        return ResponseUtil.success();
    }


}
