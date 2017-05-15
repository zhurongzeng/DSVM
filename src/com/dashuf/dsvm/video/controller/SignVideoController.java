package com.dashuf.dsvm.video.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dashuf.dsvm.common.util.CommonPathUtils;
import com.dashuf.dsvm.usermanagement.dto.UserManagementDTO;
import com.dashuf.dsvm.video.service.SignVideoService;

@Controller
@SessionAttributes("session_user_info")
public class SignVideoController {

	private static String QUERY_SIGN_VIDEO_LIST_VIEW = "video/video_list.jsp";
	private static String ONLINE_VIDEO_VIEW = "video/online_video.jsp";
	
	@Autowired
	private SignVideoService signVideoService;
	
	@RequestMapping(value = "querySignVideoList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String querySignVideoList(
			@RequestParam("lineId") String lineId,
			@RequestParam("clientName") String clientName,
			@RequestParam("idNo") String idNo,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) throws Exception {
		Map<String, String> inputMap = new HashMap<String, String>();
		List<Map<String, String>> resultList = null;
		JSONObject jsonModel = new JSONObject();
		try {
			
			if(lineId!=null && !"".equals(lineId)){
				inputMap.put("lineId", lineId);
				inputMap.put("customerName", clientName);
				inputMap.put("certId", idNo);
				inputMap.put("startDate", startDate);
				inputMap.put("endDate", endDate);
				System.out.println("querySignVideoList传入参数："+inputMap);
				Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
				resultList = signVideoService.querySignVideoList(inputMap);
				map.put("resultList", resultList);
				jsonModel.put("scs", true);
				jsonModel.put("msg", "");
				
			}else{
				jsonModel.put("scs", false);
				jsonModel.put("msg", "银行编码不能为空");
			}
			jsonModel.put("resultList", resultList);
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonModel.put("scs", false);
			jsonModel.put("msg", "查询视频列表异常："+e.getMessage());
			jsonModel.put("resultList", resultList);
		}
		
		return jsonModel.toString();
	}
	
	@RequestMapping(value = "insertVideoOperationRecord",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public String insertVideoOperationRecord(@RequestParam("cosPath") String cosPath,
			@RequestParam("serialNo") String serialNo,@RequestParam("customerName") String customerName,
			@RequestParam("certId") String certId,@RequestParam("partnerName") String partnerName,
			@RequestParam("inputOrgId") String inputOrgId,@RequestParam("orgName") String orgName,
			@RequestParam("signuserid") String signuserid,@RequestParam("signUserName") String signUserName,
			@RequestParam("startDate") String startDate,@RequestParam("signAddressName") String signAddressName,
			@RequestParam("flag") String flag,@ModelAttribute("session_user_info") UserManagementDTO loginUserDTO)throws Exception{
		System.out.println("获取登录用户："+loginUserDTO.getUserId());
		Map<String,String> inputMap = new HashMap<String,String>();
		inputMap.put("serialNo", serialNo);
		inputMap.put("client_name", customerName);
		inputMap.put("idNo", certId);
		inputMap.put("partner_name", partnerName);
		inputMap.put("region_code", inputOrgId);
		inputMap.put("region_name", orgName);
		inputMap.put("sign_user_id", signuserid);
		inputMap.put("sign_user_name", signUserName);
		inputMap.put("video_path", URLDecoder.decode(cosPath, "UTF-8"));
		inputMap.put("start_date", startDate);
		inputMap.put("flag", flag);
		inputMap.put("operation_user", loginUserDTO.getUserId());
		JSONObject jsonModel = new JSONObject();

		Map<String,String> resultMap = signVideoService.insertVideoOperationRecord(inputMap);
		String operationFlag = resultMap.get("operationFlag");
		String msg = resultMap.get("msg");
		if(operationFlag!=null && "Y".equals(operationFlag)){
			jsonModel.put("scs", true);
		}else{
			jsonModel.put("scs", false);
			jsonModel.put("msg", msg);
		}
		System.out.println(jsonModel.toString());
		return jsonModel.toString();
	}
	
	@RequestMapping("onlineVideo")
	public ModelAndView onlineVideo(@RequestParam("cosPath") String sVideoLink)throws Exception{
		ModelAndView mv = new ModelAndView(ONLINE_VIDEO_VIEW);
		String cosPath = sVideoLink.substring(sVideoLink.indexOf("/qly_store/"), sVideoLink.indexOf("?sign")).trim();
		System.out.println("onlineVideo ------------------1:"+cosPath);
		try {
			cosPath = sVideoLink.substring(0,sVideoLink.indexOf("/qly_store/")).trim()+CommonPathUtils.encodeRemotePath(cosPath)+sVideoLink.substring(sVideoLink.indexOf("?sign"),sVideoLink.length()).trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("onlineVideo ------------------2:"+cosPath);
		mv.addObject("cospath",cosPath);
		
		System.out.println(mv);
		return mv;
	}
	
	@RequestMapping("signVideoListView")
	public ModelAndView signVideoListView(HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView(QUERY_SIGN_VIDEO_LIST_VIEW);
		return mv;
	}

}
