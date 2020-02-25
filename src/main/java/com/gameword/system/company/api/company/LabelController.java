package com.gameword.system.company.api.company;

import com.gameword.system.common.utils.Option;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.company.model.LabelModel;
import com.gameword.system.company.service.ILabelService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by majiancheng on 2019/9/28.
 */
@Controller
@RequestMapping("/api/company/label")
public class LabelController {

    @Autowired
    private ILabelService labelService;

    @ResponseBody
    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public Result options() {
        List<LabelModel> labelModels = labelService.find(Collections.singletonMap("isDel", 0));

        List<Option> options = new ArrayList<Option>();

        if(CollectionUtils.isNotEmpty(labelModels)) {
            for(LabelModel labelModel : labelModels) {
                options.add(new Option(labelModel.getLabelName(), String.valueOf(labelModel.getId())));
            }
        }

        return ResponseUtil.success(options);
    }

}
