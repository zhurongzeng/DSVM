package com.dashuf.dsvm.video.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dashuf.dsvm.common.dto.PartnerDTO;
import com.dashuf.support.dao.DashufSqlSessionDAO;

@Repository
public class SignVideoDAO extends DashufSqlSessionDAO{
	
	/**
	 * 检查每单操作几次了
	 * @param inputMap
	 * @return
	 */
	public int checkVideoOperationRecord(Map<String,String> inputMap){
		Integer count = this.getSqlSession().selectOne("video.checkVideoOperationRecord", inputMap);
		return count.intValue();
	}
	
	/**
	 * 记录每次操作下载或在线播放视频
	 * @param inputMap
	 */
	public void insertVideoOperationRecord(Map<String,String> inputMap){
		this.getSqlSession().insert("video.insertVideoOperationRecord", inputMap);
	}

	/**
	 * 查询合作方数量
	 * @return
	 */
	public int queryPartnerCount() {
		Integer count = this.getSqlSession().selectOne("video.queryPartnerCount", null);
		return count.intValue();
	}

	/**
	 * 删除合作方基表数据
	 */
	public void deletePartnerTable() {
		this.getSqlSession().delete("video.deletePartnerTable", null);
	}

	/**
	 * 现在合作方基表信息
	 * @param partnerMap
	 */
	public void insertParterTable(PartnerDTO partnerDTO) {
		this.getSqlSession().insert("video.insertParterTable", partnerDTO);
	}
}
