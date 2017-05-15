package com.dashuf.dsvm.usermanagement.dto;

import java.util.Date;

public class UserManagementDTO {

	//用户ID
	private String userId;
	//用户姓名
	private String userName;
	//用户密码
	private String userPwd;
	//机构
	private String regionCode;
	//合作方
	private String partnerId;
	//
	private String partnerName;
	//最近登录时间
	private Date loginDate;
	//生效时间
	private Date startDate;
	//失效时间
	private Date endDate;
	//操作用户
	private String loginUser;
	//角色
	private RoleInfoDTO roleInfoDTO;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public RoleInfoDTO getRoleInfoDTO() {
		return roleInfoDTO;
	}
	public void setRoleInfoDTO(RoleInfoDTO roleInfoDTO) {
		this.roleInfoDTO = roleInfoDTO;
	}

}
