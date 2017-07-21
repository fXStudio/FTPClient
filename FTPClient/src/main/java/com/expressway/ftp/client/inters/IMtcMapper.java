package com.expressway.ftp.client.inters;

import java.util.List;

import com.expressway.ftp.client.models.MtcModel;

public interface IMtcMapper {
	public List<MtcModel> getRecords(String beginDate, String endDate);

	public List<MtcModel> getRecords();
	
	public int updateByPrimaryKey(MtcModel model);
}
