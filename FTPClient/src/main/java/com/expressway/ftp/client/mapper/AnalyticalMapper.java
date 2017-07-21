package com.expressway.ftp.client.mapper;

import java.util.List;

import com.expressway.ftp.client.models.EtcModel;

import tk.mybatis.mapper.common.base.BaseUpdateMapper;

/**
 * 记录数据
 * 
 * @author Ajaxfan
 */
public interface AnalyticalMapper extends BaseUpdateMapper<EtcModel> {
	public List<EtcModel> getRecords(String beginDate, String endDate);

	public List<EtcModel> getRecords();
}
