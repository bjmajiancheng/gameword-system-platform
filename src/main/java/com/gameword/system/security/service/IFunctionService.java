package com.gameword.system.security.service;

import com.gameword.system.common.baseservice.IBaseService;
import com.gameword.system.common.utils.TreeNode;
import com.gameword.system.core.model.FunctionModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by majiancheng on 2019/9/18.
 */
public interface IFunctionService extends IBaseService<FunctionModel> {

    public PageInfo<FunctionModel> selectByFilterAndPage(FunctionModel functionModel, int pageNum, int pageSize);

    public List<FunctionModel> selectByFilter(FunctionModel functionModel);

    public FunctionModel selectById(int id);

    List<TreeNode> getFunctionTreeNodes(FunctionModel functionModel);
}
