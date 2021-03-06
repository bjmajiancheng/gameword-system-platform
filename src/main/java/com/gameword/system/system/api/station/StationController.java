package com.gameword.system.system.api.station;

import com.alibaba.druid.util.StringUtils;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.FunctionModel;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.service.IUserService;
import com.gameword.system.security.utils.SecurityUtil;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.model.CountryModel;
import com.gameword.system.system.model.StationDetailModel;
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

        stationModel.setIsDel(0);
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
                    tmpStation.setCreateUserName(createUser.getUserName());
                }

                UserModel updateUser = userMap.get(tmpStation.getUpdateUserId());
                if(updateUser != null) {
                    tmpStation.setUpdateUserName(updateUser.getUserName());
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
     * 添加
     *
     * @param stationModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(StationModel stationModel) {
        if(stationModel == null) {
            return ResponseUtil.error("系统异常, 请稍后重试");
        }

        try {
            StationModel tmpStationModel =  stationService.findByCountryAndCityId(stationModel.getCountryId(), stationModel.getCityId());

            if(tmpStationModel != null) {
                return ResponseUtil.error("城市驿站已存在, 请修改国家和城市信息。");
            }

            stationModel.setStatus(1);
            stationModel.setCreateUserId(SecurityUtil.getCurrentUserId());
            stationModel.setUpdateUserId(SecurityUtil.getCurrentUserId());

            int stationId = stationService.saveNotNull(stationModel);

            return ResponseUtil.success();

        } catch(Exception e) {
            e.printStackTrace();

            return ResponseUtil.error("系统异常, 请稍后重试");
        }
    }

    /**
     * 更新
     *
     * @param stationModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(StationModel stationModel) {

        stationModel.setUpdateUserId(SecurityUtil.getCurrentUserId());
        int updateCnt = stationService.updateNotNull(stationModel);

        return ResponseUtil.success();
    }

    /**
     * 更新
     *
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
    public Result updateStatus(@RequestParam("id")Integer id, @RequestParam("status")Integer status) {

        StationModel stationModel = new StationModel();
        stationModel.setId(id);
        stationModel.setStatus(status);
        stationModel.setUpdateUserId(SecurityUtil.getCurrentUserId());
        int updateCnt = stationService.updateNotNull(stationModel);

        return ResponseUtil.success();
    }

    /**
     * 更新
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(@RequestParam(value="id") Integer id) {
        StationModel stationModel = new StationModel();

        stationModel.setId(id);
        stationModel.setIsDel(1);
        stationModel.setUpdateUserId(SecurityUtil.getCurrentUserId());
        int updateCnt = stationService.updateNotNull(stationModel);

        return ResponseUtil.success();
    }



}
