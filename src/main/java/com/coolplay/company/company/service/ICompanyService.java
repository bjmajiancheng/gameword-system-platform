package com.coolplay.company.company.service;

import com.coolplay.company.common.baseservice.IBaseService;
import com.coolplay.company.company.model.CompanyModel;

import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/19.
 */
public interface ICompanyService extends IBaseService<CompanyModel> {

    public CompanyModel findCompanyById(Integer id);

    public List<CompanyModel> find(Map<String, Object> param);
}
