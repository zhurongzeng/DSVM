package com.dashuf.dsvm.common.threadQuartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dashuf.dsvm.video.service.SignVideoService;
import com.dashuf.support.job.DashufJob;

@Component
public class QueryPartnerListSynchronize {
	
	@Autowired
	private SignVideoService signVideoService;
	
	@DashufJob("0 0 6 * * ?")
	public void queryPartnerListSynchronize(){
		System.out.println("queryPartnerListSynchronize开始");
		signVideoService.queryPartnerListSynchronize();
		System.out.println("queryPartnerListSynchronize结束");
	}
}
