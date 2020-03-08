package com.gameword.system.system.api.station;

import com.alibaba.druid.util.StringUtils;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.core.model.FunctionModel;
import com.gameword.system.system.model.StationModel;
import com.gameword.system.system.service.IStationDetailService;
import com.gameword.system.system.service.IStationService;
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
@RequestMapping("/api/station/station")
public class StationController {

    @Autowired
    private IStationService stationService;

    @Autowired
    private IStationDetailService stationDetailService;


    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(StationModel stationModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        PageInfo<StationModel> pageInfo = stationService.selectByFilterAndPage(stationModel, pageNum, pageSize);

        return PageConvertUtil.grid(pageInfo);
    }

    /**
     * 获取公司部门信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStation", method = RequestMethod.GET)
    public Result getStation(@RequestParam("id") int id) {
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
    @RequestMapping(value = "/saveFunction", method = RequestMethod.POST)
    public Result saveFunction(StationModel stationModel) {
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
    @RequestMapping(value = "/updateFunction", method = RequestMethod.POST)
    public Result updateFunction(StationModel stationModel) {
        int updateCnt = stationService.updateNotNull(stationModel);

        return ResponseUtil.success();
    }


}
