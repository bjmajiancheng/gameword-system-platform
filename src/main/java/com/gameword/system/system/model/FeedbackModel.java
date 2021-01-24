/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.gameword.system.system.model;

import com.gameword.system.common.handler.Sortable;
import com.gameword.system.common.utils.ExcelCell;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

/**
 * @author  shawn
 * @version 1.0
 * @since 1.0
 */
@Table(name = "d_feedback")
public class FeedbackModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ExcelCell(index = 0, head = "序号")
	private Integer id;//"id"

	@Column(name = "user_id")
	@ExcelCell(index = 1, head = "用户ID")
	private Integer userId;//"用户ID"

	@Column(name = "content")
	@ExcelCell(index = 2, head = "反馈内容")
	private String content;//"反馈内容"

	@Column(name = "language")
	private Integer language;//"语种 1：中文 2：英文"

	@Column(name = "city_id")
	private Integer cityId;//"城市ID"

	@Column(name = "status")
	private Integer status;//"状态 0:待处理, 1:已处理"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	//columns END

	@Transient
	@ExcelCell(index = 4, head = "城市")
	private String cityName = "";

	@Transient
	@ExcelCell(index = 3, head = "语种")
	private String languageStr = "";

	@Transient
	@ExcelCell(index = 5, head = "状态")
	private String statusStr = "";
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}
		
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getLanguageStr() {
		this.languageStr = "中文";
		if (this.getLanguage() == 2) {
			this.languageStr = "英文";
		}
		return languageStr;
	}

	public void setLanguageStr(String languageStr) {
		this.languageStr = languageStr;
	}

	public String getStatusStr() {
		this.statusStr = "待处理";
		if (this.getStatus() == 1) {
			this.statusStr = "已处理";
		}
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
}

