package com.dashuf.dsvm.usermanagement.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.dashuf.dsvm.common.dto.PartnerDTO;
import com.dashuf.dsvm.common.util.CommonUtil;
import com.dashuf.dsvm.usermanagement.dto.RoleInfoDTO;
import com.dashuf.dsvm.usermanagement.dto.UserManagementDTO;
import com.dashuf.dsvm.usermanagement.service.UserManagementService;
import com.dashuf.support.tx.DashufTXUtil;

@Controller
@SessionAttributes("session_user_info")
public class UserManagementController {
	private static String INDEX_VIEW = "index.jsp";
	private static String LOGIN_VIEW = "login.html";
	private static String QUERY_USER_LIST_VIEW = "usermanagement/user_list.jsp";
	private static String ADD_USER_INFO_VIEW = "usermanagement/add_user.jsp";
	
	@Autowired
	private UserManagementService userManagementService;
	
	@RequestMapping(value="loginUser",method=RequestMethod.POST)
	public ModelAndView loginUser(UserManagementDTO userManagementDTO,ModelMap modelMap) throws Exception {
		ModelAndView mv = new ModelAndView(INDEX_VIEW);
		DashufTXUtil.startTX();
		try {
			UserManagementDTO userDTO = userManagementService.checkUserIsExist(userManagementDTO);
			if(userDTO != null){
				modelMap.addAttribute(CommonUtil.SESSION_USER_INFO, userDTO);
				mv.addObject("userDTO",userDTO);
			}else{
				mv.setViewName(LOGIN_VIEW);
			}
			DashufTXUtil.commitTX();
		} catch (Exception e) {
			e.printStackTrace();
			DashufTXUtil.rollbackTX();
		}
		

		return mv;
	}
	
	@RequestMapping(value = "logofUser")
	public ModelAndView logofUser(UserManagementDTO userManagementDTO,HttpServletRequest request,SessionStatus sessionStatus) throws Exception {
		Enumeration<String> em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		request.getSession().removeAttribute(CommonUtil.SESSION_USER_INFO);
		request.getSession().invalidate();

		sessionStatus.setComplete();
		
		ModelAndView mv = new ModelAndView(LOGIN_VIEW);
		return mv;
	}

	@RequestMapping("queryUserListView")
	public ModelAndView queryUserListView() throws Exception {
		ModelAndView mv = new ModelAndView(QUERY_USER_LIST_VIEW);
		return mv;
	}
	
	@RequestMapping(value = "queryUserList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryUserList(@ModelAttribute("session_user_info") UserManagementDTO loginUserDTO)throws Exception{
		List<UserManagementDTO> userList = null;
		JSONObject jsonModel = new JSONObject();
		try {
			Map<String,String> inputMap = new HashMap<String,String>();
			inputMap.put("roleId", loginUserDTO.getRoleInfoDTO().getRoleId());
			userList = userManagementService.queryUserList(inputMap);
			jsonModel.put("scs", true);
			jsonModel.put("msg", "");
			jsonModel.put("userList", userList);
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.put("scs", false);
			jsonModel.put("msg", "查询用户列表异常！");
			jsonModel.put("userList", userList);
		}
		System.out.println("queryUserList返回结果："+jsonModel.toString());
		return jsonModel.toString();
	}
	
	@RequestMapping("addUserInfoView")
	public ModelAndView addUserInfoView(@RequestParam("inputDate") String[] inputDate)throws Exception{
		ModelAndView mv = new ModelAndView(ADD_USER_INFO_VIEW);
		String userId = "";
		String userName = "";
		String partnerName = "";
		String roleName = "";
		String flag = "add";

		if(inputDate!=null && inputDate.length > 0){
			userId = inputDate[0];
			System.out.println("addUserInfoView:"+userId);
			userName = inputDate[1];
			partnerName = inputDate[2];
			roleName = inputDate[3];
			flag = "update";
		}
		
		mv.addObject("userId",userId);
		mv.addObject("userName",userName);
		mv.addObject("partnerName",partnerName);
		mv.addObject("roleName",roleName);
		mv.addObject("flag",flag);
		System.out.println("addUserInfoView:"+mv);
		return mv;
	}
	
	@RequestMapping(value = "queryPartnerTable",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryPartnerTable() throws Exception {
		List<PartnerDTO> partnerList = null;
		JSONObject jsonModel = new JSONObject();
		try {
			partnerList = userManagementService.queryPartnerTable();
			jsonModel.put("scs", true);
			jsonModel.put("msg", "");
			jsonModel.put("partnerList", partnerList);
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.put("scs", false);
			jsonModel.put("msg", "查询合作方异常："+e.getMessage());
			jsonModel.put("partnerList", partnerList);
		}
		System.out.println("queryPartnerTable:"+jsonModel.toString());
		return jsonModel.toString();
	}
	
	@RequestMapping(value = "addUserInfo",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addUserInfo(@RequestParam("user_id") String userId,
			@RequestParam("user_name") String userName,
			@RequestParam("user_pwd") String userPwd,
			@RequestParam("partner") String partner,
			@RequestParam("role_id") String roleId,
			@ModelAttribute("session_user_info") UserManagementDTO loginUserDTO) throws Exception {
		System.out.println("addUserInfo 传入参数："+userId+"--"+userName+"--"+userPwd+"--"+partner+"--"+roleId);
		JSONObject jsonModel = new JSONObject();
		DashufTXUtil.startTX();
		try {
			//获取登录用户
			
			UserManagementDTO userManagementDTO = new UserManagementDTO();
			userManagementDTO.setUserId(userId.trim());
			userManagementDTO.setUserName(userName);
			userManagementDTO.setUserPwd(userPwd.trim());
			userManagementDTO.setPartnerId(partner);
			RoleInfoDTO roleInfoDTO = new RoleInfoDTO();
			roleInfoDTO.setRoleId(roleId);
			roleInfoDTO.setUserId(userId.trim());
			userManagementDTO.setRoleInfoDTO(roleInfoDTO);
			if(loginUserDTO!=null){
				userManagementDTO.setLoginUser(loginUserDTO.getUserId());
			}
			boolean flag = userManagementService.addUserInfo(userManagementDTO);
			if(flag){
				jsonModel.put("scs", true);
				jsonModel.put("msg", "");
			}else{
				jsonModel.put("scs", false);
				jsonModel.put("msg", userManagementDTO.getUserId()+"用户ID已存在，请重新操作！");
			}
			
			DashufTXUtil.commitTX();
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.put("scs", false);
			jsonModel.put("msg", "新增客户信息异常："+e.getMessage());
			DashufTXUtil.rollbackTX();
		}
		System.out.println("addUserInfo:"+jsonModel.toString());
		return jsonModel.toString();
	}
	
	@RequestMapping(value = "updateUserInfo",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateUserInfo(@RequestParam("user_id") String userId,
			@RequestParam("user_name") String userName,
			@RequestParam("user_pwd") String userPwd,
			@RequestParam("partner") String partner,
			@RequestParam("role_id") String roleId,
			@ModelAttribute("session_user_info") UserManagementDTO loginUserDTO,
			ModelMap modelMap) throws Exception {
		System.out.println("updateUserInfo 传入参数："+userId+"--"+userName+"--"+userPwd+"--"+partner+"--"+loginUserDTO.getUserId());
		JSONObject jsonModel = new JSONObject();
		DashufTXUtil.startTX();
		try {
			
			UserManagementDTO userManagementDTO = new UserManagementDTO();
			userManagementDTO.setUserId(userId);
			userManagementDTO.setUserName(userName);
			userManagementDTO.setUserPwd(userPwd);
			userManagementDTO.setPartnerId(partner);
			RoleInfoDTO roleInfoDTO = new RoleInfoDTO();
			roleInfoDTO.setRoleId(roleId);
			roleInfoDTO.setUserId(userId.trim());
			userManagementDTO.setRoleInfoDTO(roleInfoDTO);
			if(loginUserDTO!=null){
				userManagementDTO.setLoginUser(loginUserDTO.getUserId());
			}
			userManagementService.updateUserInfo(userManagementDTO);
			jsonModel.put("scs", true);
			jsonModel.put("msg", "");
			System.out.println("userId:"+userId+"--loginUserDTO.getUserId():"+loginUserDTO.getUserId());
			//把更新后的信息设置到session
			if(loginUserDTO!=null && loginUserDTO.getUserId().equals(userId)){
				modelMap.addAttribute(CommonUtil.SESSION_USER_INFO, userManagementDTO);
			}
			
			DashufTXUtil.commitTX();
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.put("scs", false);
			jsonModel.put("msg", "修改客户信息异常："+e.getMessage());
			DashufTXUtil.rollbackTX();
		}
		System.out.println("updateUserInfo:"+jsonModel.toString());
		return jsonModel.toString();
	} 
	
	@RequestMapping(value = "deleteUserInfo",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteUserInfo(@RequestParam("userId") String userId,@ModelAttribute("session_user_info") UserManagementDTO loginUserDTO) throws Exception {
		System.out.println("deleteUserInfo 传入参数："+userId);
		JSONObject jsonModel = new JSONObject();
		DashufTXUtil.startTX();
		try {
			UserManagementDTO userManagementDTO = new UserManagementDTO();
			userManagementDTO.setUserId(userId);
			if(loginUserDTO!=null){
				userManagementDTO.setLoginUser(loginUserDTO.getUserId());
			}
			userManagementService.deleteUserInfo(userManagementDTO);
			jsonModel.put("scs", true);
			jsonModel.put("msg", "删除成功");
			
			DashufTXUtil.commitTX();
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.put("scs", false);
			jsonModel.put("msg", "删除客户信息异常："+e.getMessage());
			DashufTXUtil.rollbackTX();
		}
		System.out.println("deleteUserInfo:"+jsonModel.toString());
		return jsonModel.toString();
	} 
	
	@RequestMapping(value = "queryRoleInfo",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryRoleInfo(@ModelAttribute("session_user_info") UserManagementDTO loginUserDTO) throws Exception {
		List<RoleInfoDTO> roleList = null;
		JSONObject jsonModel = new JSONObject();
		try {
			System.out.println("queryRoleInfo传入参数："+loginUserDTO.getUserId());
			roleList = userManagementService.queryRoleInfo(loginUserDTO);
			jsonModel.put("scs", true);
			jsonModel.put("msg", "");
			jsonModel.put("roleList", roleList);
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.put("scs", false);
			jsonModel.put("msg", "查询角色列表异常："+e.getMessage());
			jsonModel.put("roleList", roleList);
		}
		System.out.println("queryRoleInfo:"+jsonModel.toString());
		return jsonModel.toString();
	}
}
