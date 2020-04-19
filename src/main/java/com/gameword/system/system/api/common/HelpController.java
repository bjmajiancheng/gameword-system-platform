package com.gameword.system.system.api.common;

import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.system.model.HelpModel;
import com.gameword.system.system.model.LabelModel;
import com.gameword.system.system.service.IHelpService;
import com.gameword.system.system.service.ILabelService;
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
@RequestMapping(value = "/api/common/help")
public class HelpController {

    @Autowired
    private IHelpService helpService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(HelpModel helpModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        helpModel.setIsDel(0);
        PageInfo<HelpModel> pageInfo = helpService.selectByFilterAndPage(helpModel, pageNum, pageSize);

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
        HelpModel helpModel = helpService.findById(id);

        return ResponseUtil.success(helpModel);
    }

    /**
     * 添加
     *
     * @param helpModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(HelpModel helpModel) {

        int addCnt = helpService.saveNotNull(helpModel);

        return ResponseUtil.success();
    }

    /**
     * 添加公司部门信息
     *
     * @param helpModel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(HelpModel helpModel) {

        int updateCnt = helpService.updateNotNull(helpModel);

        return ResponseUtil.success();
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result delete(@RequestParam(value = "id") Integer id) {
        HelpModel helpModel = new HelpModel();
        helpModel.setId(id);
        helpModel.setIsDel(1);
        int updateCnt = helpService.updateNotNull(helpModel);

        return ResponseUtil.success();

    }
}
