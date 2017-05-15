package com.dashuf.dsvm.video.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dashuf.dsvm.common.dto.PartnerDTO;
import com.dashuf.dsvm.common.httpRequest.DspmHttpRequest;
import com.dashuf.dsvm.video.dao.SignVideoDAO;
import com.dashuf.support.property.DashufPropertiesUtil;

@Service
public class SignVideoService {

	@Autowired
	private DspmHttpRequest dspmHttpRequest;
	@Autowired
	private SignVideoDAO  signVideoDAO;
	
	/**
	 * 查询面签视频列表
	 * @param inputMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> querySignVideoList(Map<String,String> inputMap){
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>();
		JSONObject result = dspmHttpRequest.querySignVideoList(inputMap);
		if(result!=null && !"{}".equals(result)){
			String code = (String)result.get("code");
			if(code!=null && "00".equals(code)){
				JSONArray resultArray =  (JSONArray) result.get("videoList");
				if(resultArray!=null && resultArray.length() > 0){
					returnList = (List)JSON.parseArray(resultArray.toString());
				}
			}else{
				returnList = null;
			}
		}
		
		return returnList;
	}
	
	/**
	 * 检查视频下载或者在线视频是否超过设置次数
	 * @param inputMap
	 * @return
	 */
	public Map<String,String> checkVideoOperationRecord(Map<String,String> inputMap){
		Map<String,String> returnMap = new HashMap<String,String>();
		String operationFlag = "Y";
		String msg = "";
		String downLoadCount = DashufPropertiesUtil.getPropertyAsString("video.download.count");
		String viewCount = DashufPropertiesUtil.getPropertyAsString("video.view.count");
		String flag = inputMap.get("flag");
		int count = signVideoDAO.checkVideoOperationRecord(inputMap);
		if(flag!=null && "1".equals(flag)){
			if(count >= Integer.parseInt(downLoadCount)){
				operationFlag = "N";
				msg = "下载次数已超过"+downLoadCount+"次，请明天再次操作！";
			}
		}else if(flag!=null && "2".equals(flag)){
			if(count >= Integer.parseInt(viewCount)){
				operationFlag = "N";
				msg = "播放次数已超过"+downLoadCount+"次，请明天再次操作！";
			}
		}
		
		returnMap.put("operationFlag", operationFlag);
		returnMap.put("msg", msg);
		return returnMap;
	}
	
	/**
	 * 记录操作视频
	 * @param inputMap
	 * @return
	 */
	public Map<String,String> insertVideoOperationRecord(Map<String,String> inputMap){
		Map<String,String> returnMap = checkVideoOperationRecord(inputMap);
		if(returnMap!=null && !returnMap.isEmpty()){
			String operationFlag = returnMap.get("operationFlag");
			if(operationFlag!=null && "Y".equals(operationFlag)){
				signVideoDAO.insertVideoOperationRecord(inputMap);
			}
		}
		
		return returnMap;
	}
	
	/**
	 * 同步合作方基表信息
	 */
	public void queryPartnerListSynchronize(){
		JSONObject result = dspmHttpRequest.queryPartnerList();
		if(result!=null && !"{}".equals(result)){
			String code = (String)result.get("code");
			if(code!=null && "00".equals(code)){
				JSONArray resultArray =  (JSONArray) result.get("partnerList");
				if(resultArray!=null && resultArray.length() > 0){
					List<Map<String,String>> returnList = (List)JSON.parseArray(resultArray.toString());
					int count = signVideoDAO.queryPartnerCount();
					System.out.println("count:"+count+"----returnList:"+returnList.size());
					if(returnList!=null && returnList.size() > 0 && returnList.size() != count){
						signVideoDAO.deletePartnerTable();
						for (int i = 0; i < returnList.size(); i++) {
							Map<String,String> partnerMap =  returnList.get(i);
							PartnerDTO partnerDTO = new PartnerDTO();
							partnerDTO.setLineId(partnerMap.get("lineId"));
							partnerDTO.setPartnerName(partnerMap.get("partnerName"));
							signVideoDAO.insertParterTable(partnerDTO);
						}
					}
				}
			}
		}
	}
}
