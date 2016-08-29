package com.expressway.ftp.client.mapper;

import java.util.List;

import com.expressway.ftp.client.models.Analytical;

/**
 * 记录数据
 * 
 * @author Ajaxfan
 */
public interface AnalyticalMapper {
	public List<Analytical> getRecords(String beginDate, String endDate);
}
