package com.gameword.system.company.service.impl;

import com.gameword.system.common.baseservice.impl.BaseService;
import com.gameword.system.company.dao.CompanyMapper;
import com.gameword.system.company.model.CompanyModel;
import com.gameword.system.company.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by majiancheng on 2019/9/19.
 */
@Service("companyService")
public class CompanyServiceImpl extends BaseService<CompanyModel> implements ICompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    public CompanyModel findCompanyById(Integer id) {
        return companyMapper.findCompanyById(id);
    }


    @Override
    public List<CompanyModel> find(Map<String, Object> param) {
        return companyMapper.find(param);
    }
}
