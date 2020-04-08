package com.gameword.system.system.api.common;

import com.gameword.system.common.utils.PageConvertUtil;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.system.model.CityModel;
import com.gameword.system.system.model.ContactModel;
import com.gameword.system.system.model.CountryModel;
import com.gameword.system.system.service.IContactService;
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
 * Created by majiancheng on 2020/4/7.
 */
@Controller
@RequestMapping("/api/common/contact")
public class ContactController {

    @Autowired
    private IContactService contactService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(ContactModel contactModel,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ContactModel> pageInfo = contactService.selectByFilterAndPage(contactModel, pageNum, pageSize);

        return PageConvertUtil.grid(pageInfo);
    }
}
