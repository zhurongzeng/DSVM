package com.dashuf.dsvm.common.httpRequest;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashuf.dsvm.common.util.HttpClientUtils;
import com.dashuf.support.log.DashufLogUtil;
import com.dashuf.support.property.DashufPropertiesUtil;

@Service
public class DspmHttpRequest {
	
	@Autowired
	private HttpClientUtils httpClientUtils;
	
	/**
	 * 查询面签视频信息接口
	 * @param inputMap
	 * @return
	 */
	public JSONObject querySignVideoList(Map<String,String> inputMap){
		
		DashufLogUtil.getDashufLog(this).info("QuerySignVideoList传入参数："+inputMap);
		String url = DashufPropertiesUtil.getPropertyAsString("dspm.url");
		String lineId = "";
		String customerName = "";
		String certId = "";
		String startDate = "";
		String endDate = "";
		if(null == inputMap.get("lineId")){
			lineId = "";
		}else{
			lineId = inputMap.get("lineId");
		}
		
		if(null == inputMap.get("customerName")){
			customerName = "";
		}else{
			customerName = inputMap.get("customerName");
		}
		
		if(null == inputMap.get("certId")){
			certId = "";
		}else{
			certId = inputMap.get("certId");
		}
		
		if(null == inputMap.get("startDate")){
			startDate = "";
		}else{
			startDate = inputMap.get("startDate");
		}
		
		if(null == inputMap.get("endDate")){
			endDate = "";
		}else{
			endDate = inputMap.get("endDate");
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("transId", "QuerySignVideoList");
		jsonObject.put("lineId",lineId);
		jsonObject.put("customerName",customerName);
		jsonObject.put("certId", certId);
		jsonObject.put("startDate", startDate);
		jsonObject.put("endDate", endDate);
		JSONObject result = httpClientUtils.sendHttpPost(url, jsonObject.toString());
		DashufLogUtil.getDashufLog(this).info("QuerySignVideoList返回参数："+result);
		return result;
	}
	
	/**
	 * 查询合作方信息列表
	 * @return
	 */
	public JSONObject queryPartnerList(){
		String url = DashufPropertiesUtil.getPropertyAsString("dspm.url");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("transId", "QueryPartnerList");
		JSONObject result = httpClientUtils.sendHttpPost(url, jsonObject.toString());
		DashufLogUtil.getDashufLog(this).info("queryPartnerList返回参数："+result);
		return result;
	}
}
