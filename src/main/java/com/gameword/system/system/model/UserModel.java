/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.model;

import com.gameword.system.common.handler.Sortable;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_user")
public class UserModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
//	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private java.lang.Integer id;//"主键"

	@Column(name = "user_type")
	private java.lang.Integer userType;//"用户类型；0：学生，1：商务会员"

	@Column(name = "user_name")
	private java.lang.String userName;//"用户名"

	@Column(name = "mobile_phone")
	private java.lang.String mobilePhone;//"手机号"

	@Column(name = "email")
	private java.lang.String email;//"email"

	@Column(name = "password")
	private java.lang.String password;//"密码"

	@Column(name = "country_id")
	private java.lang.Integer countryId;//"国籍"

	@Column(name = "city_id")
	private java.lang.Integer cityId;//"所在城市"

	@Column(name = "last_name")
	private java.lang.String lastName;//"姓"

	@Column(name = "first_name")
	private java.lang.String firstName;//"名"

	@Column(name = "birthday")
	private java.lang.String birthday;//"出生日期"

	@Column(name = "sex")
	private java.lang.Integer sex;//"性别"

	@Column(name = "nick_name")
	private java.lang.String nickName;//"昵称"

	@Column(name = "agency_name")
	private java.lang.String agencyName;//"机构/学校名称"

	@Column(name = "user_desc")
	private java.lang.String userDesc;//"个人简介（一段文字）"

	@Column(name = "head_image")
	private java.lang.String headImage;//"头像（照片）"

	@Column(name = "invite_code")
	private java.lang.String inviteCode;//"邀请码"

	@Column(name = "cn_balance")
	private BigDecimal cnBalance;//"人民币余额"

	@Column(name = "en_balance")
	private BigDecimal enBalance;//"美元余额"

	@Column(name = "register_time")
	private java.util.Date registerTime;//"注册时间"

	@Column(name = "status")
	private java.lang.Integer status;//"状态 0：正常，1：禁用"

	@Column(name = "enabled")
	private Integer enabled;//"状态，0=冻结，1=正常"

	@Column(name = "last_login_ip")
	private java.lang.String lastLoginIp;//"最后登录IP"

	@Column(name = "last_login_time")
	private java.util.Date lastLoginTime;//"最后登录时间"

	@Column(name = "account_non_locked")
	private Integer accountNonLocked;//"未锁定状态，1=正常，0=锁定"

	@Column(name = "account_non_expired")
	private Integer accountNonExpired;//"账号过期状态，1=正常，0=过期"

	@Column(name = "credentials_non_expired")
	private Integer credentialsNonExpired;//"密码失效状态：1：未失效 0：已失效"

	@Column(name = "last_password_reset")
	private java.util.Date lastPasswordReset;//"上次密码重置时间"

	@Column(name = "c_time")
	private java.util.Date ctime;//"创建时间"

	@Column(name = "u_time")
	private java.util.Date utime;//"更新时间"

	@Transient
	private String roleName;

	@Transient
	private List<Integer> roleIds;//"角色Ids"

	@Transient
	private String newPassword;//新密码

	@Transient
	private String confirmPassword;//确认密码

	@Transient
	private String countryName;

	@Transient
	private String cityName;

	//columns END
		
	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getId() {
		return this.id;
	}
		
	public void setUserType(java.lang.Integer userType) {
		this.userType = userType;
	}

	public java.lang.Integer getUserType() {
		return this.userType;
	}
		
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getUserName() {
		return this.userName;
	}
		
	public void setMobilePhone(java.lang.String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public java.lang.String getMobilePhone() {
		return this.mobilePhone;
	}
		
	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getEmail() {
		return this.email;
	}
		
	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getPassword() {
		return this.password;
	}
		
	public void setCountryId(java.lang.Integer countryId) {
		this.countryId = countryId;
	}

	public java.lang.Integer getCountryId() {
		return this.countryId;
	}
		
	public void setCityId(java.lang.Integer cityId) {
		this.cityId = cityId;
	}

	public java.lang.Integer getCityId() {
		return this.cityId;
	}
		
	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}

	public java.lang.String getLastName() {
		return this.lastName;
	}
		
	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}

	public java.lang.String getFirstName() {
		return this.firstName;
	}
		
	public void setBirthday(java.lang.String birthday) {
		this.birthday = birthday;
	}

	public java.lang.String getBirthday() {
		return this.birthday;
	}
		
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	public java.lang.Integer getSex() {
		return this.sex;
	}
		
	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}

	public java.lang.String getNickName() {
		return this.nickName;
	}
		
	public void setAgencyName(java.lang.String agencyName) {
		this.agencyName = agencyName;
	}

	public java.lang.String getAgencyName() {
		return this.agencyName;
	}
		
	public void setUserDesc(java.lang.String userDesc) {
		this.userDesc = userDesc;
	}

	public java.lang.String getUserDesc() {
		return this.userDesc;
	}
		
	public void setHeadImage(java.lang.String headImage) {
		this.headImage = headImage;
	}

	public java.lang.String getHeadImage() {
		return this.headImage;
	}
		
	public void setInviteCode(java.lang.String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public java.lang.String getInviteCode() {
		return this.inviteCode;
	}
		
	public void setCnBalance(BigDecimal cnBalance) {
		this.cnBalance = cnBalance;
	}

	public BigDecimal getCnBalance() {
		return this.cnBalance;
	}
		
	public void setEnBalance(BigDecimal enBalance) {
		this.enBalance = enBalance;
	}

	public BigDecimal getEnBalance() {
		return this.enBalance;
	}
		
	public void setRegisterTime(java.util.Date registerTime) {
		this.registerTime = registerTime;
	}

	public java.util.Date getRegisterTime() {
		return this.registerTime;
	}
		
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}
		
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getEnabled() {
		return this.enabled;
	}
		
	public void setLastLoginIp(java.lang.String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public java.lang.String getLastLoginIp() {
		return this.lastLoginIp;
	}
		
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
		
	public void setAccountNonLocked(Integer accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Integer getAccountNonLocked() {
		return this.accountNonLocked;
	}
		
	public void setAccountNonExpired(Integer accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Integer getAccountNonExpired() {
		return this.accountNonExpired;
	}
		
	public void setCredentialsNonExpired(Integer credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Integer getCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
		
	public void setLastPasswordReset(java.util.Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	public java.util.Date getLastPasswordReset() {
		return this.lastPasswordReset;
	}
		
	public void setCtime(java.util.Date ctime) {
		this.ctime = ctime;
	}

	public java.util.Date getCtime() {
		return this.ctime;
	}
		
	public void setUtime(java.util.Date utime) {
		this.utime = utime;
	}

	public java.util.Date getUtime() {
		return this.utime;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}
}

