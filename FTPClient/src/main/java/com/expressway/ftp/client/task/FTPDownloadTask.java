package com.expressway.ftp.client.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
	/** 日期工具 */
	private DateFormat df = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 定时作业
	 */
	public void process() {
		// 当前日期
		Calendar cal = GregorianCalendar.getInstance();
		// 每次执行默认处理前一天的数据，因此这里获得前一天的日期对象
		cal.add(Calendar.DAY_OF_MONTH, -1);

		// 我们要获取的数据日期
		String queryDate = df.format(cal.getTime());

		// 应为查询需要提供时间范围，所以默认的时间范围为一天
		ftpDownloader.download(analyticalMapper.getRecords(queryDate, queryDate));
	}
}
