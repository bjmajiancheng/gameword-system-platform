/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.core.model;

import com.gameword.system.common.handler.Sortable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_system_user")
public class UserModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "user_name")
	private String userName;//"姓名"

	@Column(name = "nickname")
	private String nickname;//"昵称"

	@Column(name = "mobile_phone")
	private String mobilePhone;//"手机号"

	@Column(name = "sex")
	private Integer sex;//"性别"

	@Column(name = "age")
	private Integer age;//"年龄"

	@Column(name = "country_id")
	private Integer countryId;//"国籍"

	@Column(name = "city_id")
	private Integer cityId;//"城市"

	@Column(name = "agency_name")
	private String agencyName;//"机构名称"

	@Column(name = "language")
	private Integer language;//"语种（中文/英文/中英双语）"

	@Column(name = "register_time")
	private java.util.Date registerTime;//"注册时间"

	@Column(name = "user_type")
	private Integer userType;//"角色（全部/管理员/供应商/教师）"

	@Column(name = "password")
	private String password;//"密码"

	@Column(name = "is_online")
	private Integer isOnline;//"是否在线"

	@Column(name = "enabled")
	private Integer enabled;//"状态，0=冻结，1=正常"

	@Column(name = "last_login_ip")
	private String lastLoginIp;//"最后登录IP"

	@Column(name = "last_login_time")
	private Date lastLoginTime;//"最后登录时间"

	@Column(name = "account_non_locked")
	private boolean accountNonLocked;//"未锁定状态，0=正常，1=锁定"

	@Column(name = "account_non_expired")
	private boolean accountNonExpired;//"账号过期状态，1=正常，0=过期"

	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;//"密码失效状态：1：未失效 0：已失效"

	@Column(name = "last_password_reset")
	private Date lastPasswordReset;//"上次密码重置时间"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	@Column(name = "u_time")
	private Date utime;//"更新时间"

	//columns END

	@Transient
	private String roleName;

	@Transient
	private List<Integer> roleIds;//"角色Ids"

	@Transient
	private String newPassword;//新密码

	@Transient
	private String confirmPassword;//确认密码

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Date getLastPasswordReset() {
		return lastPasswordReset;
	}

	public void setLastPasswordReset(Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
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
}

