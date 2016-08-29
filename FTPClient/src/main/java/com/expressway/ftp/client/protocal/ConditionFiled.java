package com.expressway.ftp.client.protocal;

/**
 * 数据过滤条件字段
 * 
 * @author FXStudio.Ajaxfan
 */
public class ConditionFiled {
	/** 开始日期 */
	private String beginDate;
	/** 结束日期 */
	private String endDate;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
