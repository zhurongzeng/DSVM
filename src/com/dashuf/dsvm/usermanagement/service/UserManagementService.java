package com.dashuf.dsvm.usermanagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashuf.dsvm.common.dto.PartnerDTO;
import com.dashuf.dsvm.usermanagement.dao.UserManagementDAO;
import com.dashuf.dsvm.usermanagement.dto.RoleInfoDTO;
import com.dashuf.dsvm.usermanagement.dto.UserManagementDTO;

@Service
public class UserManagementService {

	@Autowired
	private UserManagementDAO userManagementDAO;
	
	public UserManagementDTO checkUserIsExist(UserManagementDTO userManagementDTO){
		
		UserManagementDTO userDto = userManagementDAO.checkUserIsExist(userManagementDTO);
		if(null == userDto || !userDto.getUserPwd().equals(userManagementDTO.getUserPwd())){
			userDto = null;
		}else{
			userManagementDAO.updateUserInfoLoginDate(userManagementDTO);
			RoleInfoDTO roleInfoDTO = userManagementDAO.queryUserRoleInfo(userManagementDTO);
			if(roleInfoDTO!=null){
				userDto.setRoleInfoDTO(roleInfoDTO);
			}
			userManagementDAO.insertLoginRecords(userDto);
		}
		
		return userDto;
	}
	
	public List<UserManagementDTO> queryUserList(Map<String,String> inputMap){
		List<UserManagementDTO> userList = userManagementDAO.queryUserList(inputMap);
		if(userList!=null && userList.size() > 0){
			for (int i = 0; i < userList.size(); i++) {
				UserManagementDTO userDto = userList.get(i);
				RoleInfoDTO roleInfoDTO = userManagementDAO.queryUserRoleInfo(userDto);
				if(roleInfoDTO!=null){
					userDto.setRoleInfoDTO(roleInfoDTO);
				}
				
			}
		}
		return userList;
	}
	
	public List<PartnerDTO> queryPartnerTable(){
		List<PartnerDTO> partnerList = userManagementDAO.queryPartnerTable();
		return partnerList;
	}
	
	public boolean addUserInfo(UserManagementDTO userManagementDTO){
		UserManagementDTO userDto = userManagementDAO.checkUserIsExist(userManagementDTO);
		boolean flag = true;
		if(null == userDto){
			userManagementDAO.addUserInfo(userManagementDTO);
			userManagementDAO.insertUserRole(userManagementDTO);
		}else{
			flag = false;
		}
		
		return flag; 
	}
	
	public void updateUserInfo(UserManagementDTO userManagementDTO){
		userManagementDAO.updateUserInfo(userManagementDTO);
		userManagementDAO.updateRoleInfo(userManagementDTO);
	}
	
	public void deleteUserInfo(UserManagementDTO userManagementDTO){
		userManagementDAO.deleteUserInfo(userManagementDTO);
		userManagementDAO.deleteUserRoleRelation(userManagementDTO);
	}
	
	public List<RoleInfoDTO> queryRoleInfo(UserManagementDTO loginUserDTO){
		List<RoleInfoDTO> roleList = userManagementDAO.queryRoleInfo(loginUserDTO);
		return roleList;
	}
}
