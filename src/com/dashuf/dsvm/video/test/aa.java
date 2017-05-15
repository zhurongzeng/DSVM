package com.dashuf.dsvm.video.test;

import com.dashuf.dsvm.common.util.CommonPathUtils;


public class aa {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String sVideoLink = "http://dashuf-10046673.file.myqcloud.com/qly_store/2016-09-19/廊坊银行_田媛_RL20160919000283_20160919_1474273461100.mp4?sign=/OW  7PZIJFYGL QSqtWXIlEKllhPTEwMDQ2NjczJms9QUtJRHA1d2FoTzZrZWVabFBBdWppeWJmUkZhVXpQMEFjWUVCJmU9MTQ3Njk1NzExOSZ0PTE0NzQzNjUxMTkmcj0xNDY4NzYwNDMwJmY9L3FseV9zdG9yZS8yMDE2LTA5LTE5LyVFNSVCQiU4QSVFNSU5RCU4QSVFOSU5MyVCNiVFOCVBMSU4Q18lRTclOTQlQjAlRTUlQUElOUJfUkwyMDE2MDkxOTAwMDI4M18yMDE2MDkxOV8xNDc0MjczNDYxMTAwLm1wNCZiPWRhc2h1Zg==";
		String cosPath = sVideoLink.substring(sVideoLink.indexOf("/qly_store/"), sVideoLink.indexOf("?sign")).trim();
		System.out.println(cosPath);
		cosPath = sVideoLink.substring(0,sVideoLink.indexOf("/qly_store/")).trim()+CommonPathUtils.encodeRemotePath(cosPath)+sVideoLink.substring(sVideoLink.indexOf("?sign"),sVideoLink.length());
		System.out.println(cosPath);
	}

}
