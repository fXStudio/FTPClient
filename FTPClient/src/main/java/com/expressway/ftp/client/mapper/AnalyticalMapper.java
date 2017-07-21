package com.expressway.ftp.client.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Update;

import com.expressway.ftp.client.models.EtcModel;

/**
 * 记录数据
 * 
 * @author Ajaxfan
 */
public interface AnalyticalMapper {
	public List<EtcModel> getRecords(String beginDate, String endDate);

	public List<EtcModel> getRecords();
	
	@Update("")
	public int updateRecord(EtcModel model);
}
