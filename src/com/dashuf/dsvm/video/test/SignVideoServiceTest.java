package com.dashuf.dsvm.video.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dashuf.dsvm.video.service.SignVideoService;
import com.dashuf.support.context.DashufContext;
import com.dashuf.support.test.TestContextUtil;

public class SignVideoServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DashufContext ct = TestContextUtil.initTestContext();
		SignVideoService a = (SignVideoService)ct.getBean("signVideoService");
		Map<String,String> inputMap = new HashMap<String,String>();
		inputMap.put("lineId", "RL20141128000002");
		List<Map<String,String>> aa = a.querySignVideoList(inputMap);
		for (int i = 0; i < aa.size(); i++) {
			System.out.println(aa.get(i));
		}
		//TestContextUtil.free();
	}

}
