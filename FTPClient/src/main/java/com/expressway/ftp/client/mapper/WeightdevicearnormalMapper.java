package com.expressway.ftp.client.mapper;

import org.apache.ibatis.annotations.Update;

import com.expressway.ftp.client.inters.IMtcMapper;
import com.expressway.ftp.client.models.MtcModel;

/**
 * 记录数据
 * 
 * @author Ajaxfan
 */
public interface WeightdevicearnormalMapper extends IMtcMapper  {
	@Update("UPDATE freeway_weightdevicearnormal SET adm_id = #{admId}, tagid = nvl(tagid + 1, 1) WHERE record_no = #{recordNo}")
	int updateByPrimaryKey(MtcModel model);
}
