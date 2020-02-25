/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.company.company.model;

import com.coolplay.company.common.handler.Sortable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_company")
public class CompanyModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//"主键"

	@Column(name = "company_full_name")
	private String companyFullName;//"公司全称"

	@Column(name = "company_name")
	private String companyName;//"公司简称"

	@Column(name = "contact_name")
	private String contactName;//"联系人"

	@Column(name = "contact_address")
	private String contactAddress;//"地址"

	@Column(name = "contact_mobile")
	private String contactMobile;//"联系电话"

	@Column(name = "company_desc")
	private String companyDesc;//"公司简介"

	@Column(name = "company_type")
	private Integer companyType;//"公司类型"

	@Column(name = "major_project")
	private String majorProject;//"主营项目"

	@Column(name = "company_email")
	private String companyEmail;//"邮箱"

	@Column(name = "legal_name")
	private String legalName;//"法人名称"

	@Column(name = "legal_idcard")
	private String legalIdcard;//"法人身份证号"

	@Column(name = "idcard_positive_photo")
	private String idcardPositivePhoto;//"身份证正面照片"

	@Column(name = "idcard_negative_photo")
	private String idcardNegativePhoto;//"身份证反面照片"

	@Column(name = "business_license_url")
	private String businessLicenseUrl;//"营业执照附件"

	@Column(name = "pos_x")
	private BigDecimal posX;//"公司x坐标"

	@Column(name = "pos_y")
	private BigDecimal posY;//"公司y坐标"

	@Column(name = "backgroud_img")
	private String backgroudImg;//"背景图片"

	@Column(name = "review_status")
	private Integer reviewStatus;//"浏览次数"

	@Column(name = "read_cnt")
	private Integer readCnt;//"审核状态（0：待审核，1：已审核）"

	@Column(name = "status")
	private Integer status;//"审核结果（1：审核通过，2：审核驳回）"

	@Column(name = "reject_reason")
	private String rejectReason;//"驳回原因"

	@Column(name = "is_del")
	private Integer isDel;//"是否删除（0：未删除，1：已删除）"

	@Column(name = "admin_user_name")
	private String adminUserName;//"管理员后台账号"

	@Column(name = "admin_password")
	private String adminPassword;//"管理员后台密码"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	@Column(name = "u_time")
	private Date utime = new Date();//"更新时间"
	//columns END

	@Transient
	private List<Integer> industryIds = new ArrayList<Integer>();//行业Ids
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setCompanyFullName(String companyFullName) {
		this.companyFullName = companyFullName;
	}

	public String getCompanyFullName() {
		return this.companyFullName;
	}
		
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return this.companyName;
	}
		
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactName() {
		return this.contactName;
	}
		
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}
		
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactMobile() {
		return this.contactMobile;
	}
		
	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}

	public String getCompanyDesc() {
		return this.companyDesc;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public void setMajorProject(String majorProject) {
		this.majorProject = majorProject;
	}

	public String getMajorProject() {
		return this.majorProject;
	}
		
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyEmail() {
		return this.companyEmail;
	}
		
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalName() {
		return this.legalName;
	}
		
	public void setLegalIdcard(String legalIdcard) {
		this.legalIdcard = legalIdcard;
	}

	public String getLegalIdcard() {
		return this.legalIdcard;
	}
		
	public void setIdcardPositivePhoto(String idcardPositivePhoto) {
		this.idcardPositivePhoto = idcardPositivePhoto;
	}

	public String getIdcardPositivePhoto() {
		return this.idcardPositivePhoto;
	}
		
	public void setIdcardNegativePhoto(String idcardNegativePhoto) {
		this.idcardNegativePhoto = idcardNegativePhoto;
	}

	public String getIdcardNegativePhoto() {
		return this.idcardNegativePhoto;
	}
		
	public void setBusinessLicenseUrl(String businessLicenseUrl) {
		this.businessLicenseUrl = businessLicenseUrl;
	}

	public String getBusinessLicenseUrl() {
		return this.businessLicenseUrl;
	}

	public BigDecimal getPosX() {
		return posX;
	}

	public void setPosX(BigDecimal posX) {
		this.posX = posX;
	}

	public BigDecimal getPosY() {
		return posY;
	}

	public void setPosY(BigDecimal posY) {
		this.posY = posY;
	}

	public String getBackgroudImg() {
		return backgroudImg;
	}

	public void setBackgroudImg(String backgroudImg) {
		this.backgroudImg = backgroudImg;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Integer getReviewStatus() {
		return this.reviewStatus;
	}
		
	public void setReadCnt(Integer readCnt) {
		this.readCnt = readCnt;
	}

	public Integer getReadCnt() {
		return this.readCnt;
	}
		
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}
		
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getRejectReason() {
		return this.rejectReason;
	}
		
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsDel() {
		return this.isDel;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public List<Integer> getIndustryIds() {
		return industryIds;
	}

	public void setIndustryIds(List<Integer> industryIds) {
		this.industryIds = industryIds;
	}
}

