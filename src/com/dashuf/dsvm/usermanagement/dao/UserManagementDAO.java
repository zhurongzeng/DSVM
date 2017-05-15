package com.dashuf.dsvm.usermanagement.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dashuf.dsvm.common.dto.PartnerDTO;
import com.dashuf.dsvm.usermanagement.dto.RoleInfoDTO;
import com.dashuf.dsvm.usermanagement.dto.UserManagementDTO;
import com.dashuf.support.dao.DashufSqlSessionDAO;

@Repository
public class UserManagementDAO extends DashufSqlSessionDAO{

	/**
	 * 检查客户是否存在
	 * @param userManagementDTO
	 * @return
	 */
	public UserManagementDTO checkUserIsExist(UserManagementDTO userManagementDTO){
		UserManagementDTO userDTO = this.getSqlSession().selectOne("usermanagement.checkUserIsExist", userManagementDTO);
		return userDTO;
	}
	
	/**
	 * 更新客户登录时间
	 * @param userManagementDTO
	 */
	public void updateUserInfoLoginDate(UserManagementDTO userManagementDTO){
		this.getSqlSession().update("usermanagement.updateUserInfoLoginDate", userManagementDTO);
	}
	
	public List<UserManagementDTO> queryUserList(Map<String,String> inputMap){
		List<UserManagementDTO> returnList = this.getSqlSession().selectList("usermanagement.queryUserList", inputMap);
		return returnList;
	}
	
	public List<PartnerDTO> queryPartnerTable(){
		List<PartnerDTO> partnerList = this.getSqlSession().selectList("usermanagement.queryPartnerTable", null);
		return partnerList;
	}
	
	public void addUserInfo(UserManagementDTO userManagementDTO){
		this.getSqlSession().insert("usermanagement.addUserInfo", userManagementDTO);
	}
	
	public void updateUserInfo(UserManagementDTO userManagementDTO){
		this.getSqlSession().update("usermanagement.updateUserInfo", userManagementDTO);
	}
	
	public void deleteUserInfo(UserManagementDTO userManagementDTO){
		this.getSqlSession().update("usermanagement.deleteUserInfo", userManagementDTO);
	}
	
	public RoleInfoDTO queryUserRoleInfo(UserManagementDTO userManagementDTO){
		RoleInfoDTO roleInfoDTO = this.getSqlSession().selectOne("usermanagement.queryUserRoleInfo", userManagementDTO);
		return roleInfoDTO;
	}
	
	public List<RoleInfoDTO> queryRoleInfo(UserManagementDTO loginUserDTO){
		List<RoleInfoDTO> partnerList = this.getSqlSession().selectList("usermanagement.queryRoleInfo", loginUserDTO);
		return partnerList;
	} 
	
	public void insertUserRole(UserManagementDTO userManagementDTO){
		this.getSqlSession().insert("usermanagement.insertUserRole", userManagementDTO);
	}
	
	public void updateRoleInfo(UserManagementDTO userManagementDTO){
		this.getSqlSession().update("usermanagement.updateRoleInfo", userManagementDTO);
	}
	
	public void deleteUserRoleRelation(UserManagementDTO userManagementDTO){
		this.getSqlSession().delete("usermanagement.deleteUserRoleRelation", userManagementDTO);
	}
	
	public void insertLoginRecords(UserManagementDTO userManagementDTO){
		this.getSqlSession().insert("usermanagement.insertLoginRecords", userManagementDTO);
	}
}
