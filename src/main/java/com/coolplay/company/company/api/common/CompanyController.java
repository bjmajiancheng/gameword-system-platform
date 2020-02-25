package com.coolplay.company.company.api.common;

import com.coolplay.company.common.utils.PageConvertUtil;
import com.coolplay.company.common.utils.ResponseUtil;
import com.coolplay.company.common.utils.Result;
import com.coolplay.company.company.model.CompanyDeptModel;
import com.coolplay.company.company.model.CompanyIndustryModel;
import com.coolplay.company.company.model.CompanyModel;
import com.coolplay.company.company.service.ICompanyDeptService;
import com.coolplay.company.company.service.ICompanyIndustryService;
import com.coolplay.company.company.service.ICompanyService;
import com.coolplay.company.core.model.RoleModel;
import com.coolplay.company.security.security.SecurityUser;
import com.coolplay.company.security.service.IRoleService;
import com.coolplay.company.security.utils.SecurityUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by majiancheng on 2019/9/19.
 */
@Controller
@RequestMapping("/api/common/company")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ICompanyDeptService companyDeptService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ICompanyIndustryService companyIndustryService;

    @ResponseBody
    @RequestMapping(value="/companyInfo", method = RequestMethod.GET)
    public Result companyInfo(HttpServletRequest request) {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();

        CompanyModel companyModel = companyService.findCompanyById(securityUser.getCompanyId());

        List<CompanyIndustryModel> companyIndustrys = companyIndustryService.findByCompanyId(companyModel.getId());
        if(CollectionUtils.isNotEmpty(companyIndustrys)) {
            List<Integer> industryIds = new ArrayList<Integer>();
            for(CompanyIndustryModel companyIndustry : companyIndustrys) {
                industryIds.add(companyIndustry.getIndustryId());
            }

            companyModel.setIndustryIds(industryIds);
        }

        return ResponseUtil.success(companyModel);
    }

    @ResponseBody
    @RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
    public Result updateCompany(CompanyModel companyModel) {
        companyModel.setReviewStatus(0);
        companyModel.setStatus(0);
        companyModel.setRejectReason("");
        int cnt = companyService.updateNotNull(companyModel);

        int delCnt = companyIndustryService.delByCompanyId(companyModel.getId());
        if(CollectionUtils.isNotEmpty(companyModel.getIndustryIds())) {
            for(Integer industryId : companyModel.getIndustryIds()) {
                CompanyIndustryModel companyIndustry = new CompanyIndustryModel();
                companyIndustry.setCompanyId(companyModel.getId());
                companyIndustry.setIndustryId(industryId);

                companyIndustryService.saveNotNull(companyIndustry);
            }
        }

        return ResponseUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/getCurrUserCompanyRoles", method = RequestMethod.GET)
    public Result getCurrUserCompanyRoles() {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        RoleModel roleModel = new RoleModel();
        roleModel.setCompanyId(securityUser.getCompanyId());
        roleModel.setStatus(1);
        List<RoleModel> roleModels = roleService.selectByFilter(roleModel);

        return ResponseUtil.success(PageConvertUtil.grid(roleModels));
    }

    @ResponseBody
    @RequestMapping(value = "/getCurrUserCompanyDepts", method = RequestMethod.GET)
    public Result getCurrUserCompanyDepts() {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        CompanyDeptModel companyDeptModel = new CompanyDeptModel();
        companyDeptModel.setCompanyId(securityUser.getCompanyId());
        companyDeptModel.setStatus(1);
        List<CompanyDeptModel> companyDeptModels = companyDeptService.selectByFilter(companyDeptModel);

        return ResponseUtil.success(PageConvertUtil.grid(companyDeptModels));
    }


}
