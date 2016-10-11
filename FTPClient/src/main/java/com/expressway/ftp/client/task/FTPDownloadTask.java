package com.expressway.ftp.client.task;

import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.expressway.ftp.client.FTPDownloader;
import com.expressway.ftp.client.mapper.AnalyticalMapper;

@Component("FTPDownloadTask")
public class FTPDownloadTask {
	/** ETC稽查数据接口 */
	private @Autowired AnalyticalMapper analyticalMapper;
	/** FTP文件下载 */
	private @Autowired FTPDownloader ftpDownloader;

	/** 日志工具类 */
	private Logger log = Logger.getLogger("com.expressway.ftp.client.task");

	/**
	 * 定时作业
	 */
	public void process() {
		log.debug("Execute at : " + GregorianCalendar.getInstance().getTime());

		// 应为查询需要提供时间范围，所以默认的时间范围为一天
		ftpDownloader.download(analyticalMapper.getRecords());
	}
}
