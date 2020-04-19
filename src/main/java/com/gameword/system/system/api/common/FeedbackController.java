package com.gameword.system.system.api.common;

import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.system.model.FeedbackModel;
import com.gameword.system.system.service.IFeedbackService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by majiancheng on 2020/4/19.
 */
@Controller
@RequestMapping(value = "/api/common/feedback")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(FeedbackModel feedbackModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        PageInfo<FeedbackModel> pageInfo = feedbackService.selectByFilterAndPage(feedbackModel, pageNum, pageSize);

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
        int updateCnt = feedbackService.updateNotNull(feedbackModel);

        return ResponseUtil.success();

    }
}
