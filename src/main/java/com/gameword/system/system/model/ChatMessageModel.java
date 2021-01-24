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
@Table(name = "d_chat_message")
public class ChatMessageModel extends Sortable {
	private static final long serialVersionUID = 1L;

	//columns START
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ExcelCell(index = 0, head = "序号")
	private Integer id;//"主键"

	@Column(name = "message_type")
	private Integer messageType;//"消息类型"

	@Column(name = "chatroom_id")
	@ExcelCell(index = 2, head = "聊天室ID")
	private String chatroomId;//"聊天室ID"

	@Column(name = "chatroom_name")
	@ExcelCell(index = 1, head = "聊天室")
	private String chatroomName;//"聊天室名称"

	@Column(name = "sensitive_word")
	@ExcelCell(index = 3, head = "敏感词")
	private String sensitiveWord;//"敏感词"

	@Column(name = "content")
	@ExcelCell(index = 4, head = "发言内容")
	private String content;//"发言内容"

	@Column(name = "user_id")
	@ExcelCell(index = 7, head = "发言人ID")
	private Integer userId;//"发言人ID"

	@Column(name = "user_name")
	@ExcelCell(index = 5, head = "发言人姓名")
	private String userName;//"发言人姓名"

	@Column(name = "nick_name")
	@ExcelCell(index = 6, head = "昵称")
	private String nickName;//"昵称"

	@Column(name = "chat_time")
	@ExcelCell(index = 8, head = "发言时间")
	private Date chatTime;//"发言时间"

	@Column(name = "has_voice")
	private Integer hasVoice;//"是否有语音"

	@Column(name = "chat_user_id")
	@ExcelCell(index = 11, head = "交流对象ID")
	private Integer chatUserId;//"交流对象ID"

	@Column(name = "chat_user_name")
	private String chatUserName;//"交流对象姓名"

	@Column(name = "chat_nick_name")
	@ExcelCell(index = 10, head = "交流对象昵称")
	private String chatNickName;//"交流对象昵称"

	@Column(name = "c_time")
	private Date ctime;//"创建时间"

	@Column(name = "u_time")
	private Date utime;//"更新时间"

	//columns END

	@Transient
	private String startDate;

	@Transient
	private String endDate;

	@Transient
	@ExcelCell(index = 9, head = "语音（有/无）")
	private String hasVoiceStr;
		
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}
		
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getMessageType() {
		return this.messageType;
	}
		
	public void setChatroomId(String chatroomId) {
		this.chatroomId = chatroomId;
	}

	public String getChatroomId() {
		return this.chatroomId;
	}
		
	public void setChatroomName(String chatroomName) {
		this.chatroomName = chatroomName;
	}

	public String getChatroomName() {
		return this.chatroomName;
	}
		
	public void setSensitiveWord(String sensitiveWord) {
		this.sensitiveWord = sensitiveWord;
	}

	public String getSensitiveWord() {
		return this.sensitiveWord;
	}
		
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
		
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return this.userId;
	}
		
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
		
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}
		
	public void setChatTime(Date chatTime) {
		this.chatTime = chatTime;
	}

	public Date getChatTime() {
		return this.chatTime;
	}
		
	public void setHasVoice(Integer hasVoice) {
		this.hasVoice = hasVoice;
	}

	public Integer getHasVoice() {
		return this.hasVoice;
	}
		
	public void setChatUserId(Integer chatUserId) {
		this.chatUserId = chatUserId;
	}

	public Integer getChatUserId() {
		return this.chatUserId;
	}
		
	public void setChatUserName(String chatUserName) {
		this.chatUserName = chatUserName;
	}

	public String getChatUserName() {
		return this.chatUserName;
	}
		
	public void setChatNickName(String chatNickName) {
		this.chatNickName = chatNickName;
	}

	public String getChatNickName() {
		return this.chatNickName;
	}
		
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getCtime() {
		return this.ctime;
	}
		
	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public Date getUtime() {
		return this.utime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getHasVoiceStr() {
		this.hasVoiceStr = "无";
		if (this.hasVoice == 1) {
			this.hasVoiceStr = "有";
		}
		return hasVoiceStr;
	}

	public void setHasVoiceStr(String hasVoiceStr) {
		this.hasVoiceStr = hasVoiceStr;
	}
}

