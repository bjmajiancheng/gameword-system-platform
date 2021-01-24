package com.gameword.system.system.api.common;

import com.gameword.system.common.utils.ExcelFileUtil;
import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.security.utils.SecurityUtil;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.model.FeedbackModel;
import com.gameword.system.system.service.ICityService;
import com.gameword.system.system.service.IFeedbackService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2020/4/19.
 */
@Controller
@RequestMapping(value = "/api/common/feedback")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private ICityService cityService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(FeedbackModel feedbackModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        PageInfo<FeedbackModel> pageInfo = feedbackService.selectByFilterAndPage(feedbackModel, pageNum, pageSize);
        this.revertResult(pageInfo.getList());

        return PageConvertUtil.grid(pageInfo);
    }

    /**
     * 获取
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(@RequestParam("id") int id) {
        FeedbackModel feedbackModel = feedbackService.findById(id);

        return ResponseUtil.success(feedbackModel);
    }

    /**
     * 添加
     *
     * @param feedbackModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(FeedbackModel feedbackModel) {

        int addCnt = feedbackService.saveNotNull(feedbackModel);

        return ResponseUtil.success();
    }

    /**
     * 添加公司部门信息
     *
     * @param feedbackModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(FeedbackModel feedbackModel) {

        int updateCnt = feedbackService.updateNotNull(feedbackModel);

        return ResponseUtil.success();
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(@RequestParam(value = "id") Integer id) {
        FeedbackModel feedbackModel = new FeedbackModel();
        feedbackModel.setId(id);
        int updateCnt = feedbackService.delete(feedbackModel);

        return ResponseUtil.success();

    }

    @ResponseBody
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(FeedbackModel feedbackModel, HttpServletResponse response) {
        try {
            List<FeedbackModel> list = feedbackService.selectByFilter(feedbackModel);

            this.revertResult(list);

            ExcelFileUtil.responseXlsx(response, "用户反馈统计.xlsx", list);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 改写结果集
     *
     * @param list
     */
    private void revertResult(List<FeedbackModel> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<Integer> cityIds = new ArrayList<>();
            for (FeedbackModel feedback : list) {
                cityIds.add(feedback.getCityId());
            }

            Map<Integer, CityModel> cityModelMap = cityService.findMapByIds(cityIds);
            for (FeedbackModel feedback : list) {
                CityModel cityModel = cityModelMap.get(feedback.getCityId().intValue());
                if (cityModel != null) {
                    feedback.setCityName(cityModel.getCityCn());
                }
                feedback.getStatusStr();
                feedback.getLanguageStr();
            }
        }
    }

}
