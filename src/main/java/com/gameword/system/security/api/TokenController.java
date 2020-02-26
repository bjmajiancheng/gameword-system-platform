package com.gameword.system.security.api;

import com.gameword.system.common.tools.RedisCache;
import com.gameword.system.common.utils.HttpResponseUtil;
import com.gameword.system.common.utils.RequestUtil;
import com.gameword.system.common.utils.ResponseUtil;
import com.gameword.system.common.utils.Result;
import com.gameword.system.system.model.CompanyIndustryModel;
import com.gameword.system.system.model.CompanyLogModel;
import com.gameword.system.system.model.CompanyModel;
import com.gameword.system.system.service.ICompanyIndustryService;
import com.gameword.system.system.service.ICompanyLogService;
import com.gameword.system.system.service.ICompanyService;
import com.gameword.system.core.model.UserModel;
import com.gameword.system.security.constants.SecurityConstant;
import com.gameword.system.security.security.AuthenticationRequest;
import com.gameword.system.security.security.HttpAuthenticationDetails;
import com.gameword.system.security.service.IUserService;
import com.gameword.system.security.utils.SecurityUtil;
import com.gameword.system.security.utils.TokenUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by majiancheng on 2019/9/15.
 */
@Controller
@RequestMapping("/api/token")
public class TokenController {

    private final static Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ICompanyLogService companyLogService;

    @Autowired
    private ICompanyIndustryService companyIndustryService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request,
            @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationRequest.getVcode()) || StringUtils
                .isEmpty(authenticationRequest.getVkey())) {
            return ResponseEntity.ok(HttpResponseUtil.error("请输入验证码"));
        }
        if (StringUtils.isNotEmpty((String) redisCache.get(authenticationRequest.getVkey()))) {
            if (!((String) redisCache.get(authenticationRequest.getVkey())).equals(authenticationRequest.getVcode())) {
                return ResponseEntity.ok(HttpResponseUtil.error("验证码不正确"));
            }
        } else {
            return ResponseEntity.ok(HttpResponseUtil.error("验证码不存在或已过期"));
        }
        redisCache.del(authenticationRequest.getVkey());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        usernamePasswordAuthenticationToken.setDetails(new HttpAuthenticationDetails());

        Authentication authentication = null;
        try {
            authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            if (authentication == null) {
                return ResponseEntity.ok(HttpResponseUtil.error("未检测到验证信息"));
            }
        } catch (InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        } catch (AuthenticationException failed) {
            return ResponseEntity.ok(HttpResponseUtil.error(failed.getMessage()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) redisCache
                .get(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getUsername());
        if (userDetails == null)  {
            userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            redisCache.set(SecurityConstant.USER_CACHE_PREFIX + authenticationRequest.getUsername(), userDetails);
        }
        String token = this.tokenUtils.generateToken(userDetails);
        userService.updateLastLoginInfoByUserName(authenticationRequest.getUsername(), new Date(),
                RequestUtil.getIpAddress(request));

        CompanyLogModel companyLogModel = new CompanyLogModel();
        companyLogModel.setCompanyId(SecurityUtil.getCurrentCompanyId());
        companyLogModel.setIp(RequestUtil.getIpAddress(request));
        companyLogModel.setUserId(SecurityUtil.getCurrentUserId());
        companyLogModel.setUserName(authenticationRequest.getUsername());
        companyLogModel.setCtime(new Date());
        companyLogService.saveNotNull(companyLogModel);

        return ResponseEntity.ok(HttpResponseUtil.success(token));
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result updateCompany(CompanyModel companyModel) {
        companyModel.setCompanyType(1);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyFullName", companyModel.getCompanyFullName());
        param.put("isDel", 0);
        List<CompanyModel> companyModels = companyService.find(param);
        if(CollectionUtils.isNotEmpty(companyModels)) {
            return ResponseUtil.error("企业全称已存在, 请修改企业全称");
        }

        UserModel userModel = userService.findUserByLoginName(companyModel.getAdminUserName());

        param = new HashMap<String, Object>();
        param.put("adminUserName", companyModel.getAdminUserName());
        List<CompanyModel> validateCompanys = companyService.find(param);
        if(userModel != null || CollectionUtils.isNotEmpty(validateCompanys)) {
            return ResponseUtil.error("企业登录账号已占用, 请修改企业登录账号");
        }

        int cnt = companyService.saveNotNull(companyModel);
        if(CollectionUtils.isNotEmpty(companyModel.getIndustryIds())) {
            for(Integer industryId : companyModel.getIndustryIds()) {
                CompanyIndustryModel companyIndustry = new CompanyIndustryModel();
                companyIndustry.setIndustryId(industryId);
                companyIndustry.setCompanyId(companyModel.getId());

                int saveCnt = companyIndustryService.saveNotNull(companyIndustry);
            }
        }

        return ResponseUtil.success();
    }
}
