/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.dao;
import com.gameword.system.system.model.CompanyLabelModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import com.gameword.system.system.dao.*;
import com.gameword.system.system.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

public interface CompanyLabelMapper extends Mapper<CompanyLabelModel> {

	public List<CompanyLabelModel> find(Map<String, Object> param);

	public CompanyLabelModel findById(@Param("id") Integer id);

	public int delByCompanyId(@Param("companyId")Integer companyId);

	public List<Integer> getLabelIds(@Param("companyId")Integer companyId, @Param("language")Integer language);
}
