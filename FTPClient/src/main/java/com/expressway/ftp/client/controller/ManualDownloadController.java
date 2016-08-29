package com.expressway.ftp.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.expressway.ftp.client.FTPDownloader;
import com.expressway.ftp.client.mapper.AnalyticalMapper;
import com.expressway.ftp.client.messages.FeedBackMessage;
import com.expressway.ftp.client.protocal.ConditionFiled;

/**
 * @author Ajaxfan
 */
@RestController
@RequestMapping(value = "services", method = RequestMethod.POST)
public class ManualDownloadController {
	/** ETC稽查数据接口 */
	private @Autowired AnalyticalMapper analyticalMapper;
	/** FTP文件下载 */
	private @Autowired FTPDownloader ftpDownloader;
	
	/**
	 * @param request
	 * @param cf
	 * @return
	 */
	@RequestMapping("manualdownload")
	public Object systemLogin(HttpServletRequest request, ConditionFiled cf) {
		return new FeedBackMessage(ftpDownloader.download(analyticalMapper.getRecords(cf.getBeginDate(), cf.getEndDate())));
	}
}
