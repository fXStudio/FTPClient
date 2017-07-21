package com.expressway.ftp.client.task;

import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.expressway.ftp.client.EtcDownloader;
import com.expressway.ftp.client.MtcDownloader;
import com.expressway.ftp.client.inters.IMtcMapper;
import com.expressway.ftp.client.mapper.AnalyticalMapper;
import com.expressway.ftp.client.mapper.ChangecardMapper;
import com.expressway.ftp.client.mapper.EnterthecarMapper;
import com.expressway.ftp.client.mapper.FreenotfreeMapper;
import com.expressway.ftp.client.mapper.HeavytruckMapper;
import com.expressway.ftp.client.mapper.LonglightMapper;
import com.expressway.ftp.client.mapper.LosecardMapper;
import com.expressway.ftp.client.mapper.TollcollectorMapper;
import com.expressway.ftp.client.mapper.TypenotmatchMapper;
import com.expressway.ftp.client.mapper.WeightdevicearnormalMapper;

@Component("FTPDownloadTask")
public class FTPDownloadTask {
	/** ETC稽查数据接口 */
	private @Autowired AnalyticalMapper analyticalMapper;

	private @Autowired EtcDownloader etcDownloader;

	/** MTC稽查 */
	private @Autowired ChangecardMapper changecardMapper;
	private @Autowired EnterthecarMapper enterthecarMapper;
	private @Autowired FreenotfreeMapper freenotfreeMapper;
	private @Autowired HeavytruckMapper heavytruckMapper;
	private @Autowired LonglightMapper longlightMapper;
	private @Autowired LosecardMapper losecardMapper;
	private @Autowired TollcollectorMapper tollcollectorMapper;
	private @Autowired TypenotmatchMapper typenotmatchMapper;
	private @Autowired WeightdevicearnormalMapper weightdevicearnormalMapper;

	private @Autowired MtcDownloader mtcDownloader;

	/** 日志工具类 */
	private Logger log = Logger.getLogger("com.expressway.ftp.client.task");

	/**
	 * 定时作业
	 * 
	 * @throws InterruptedException
	 */
	public void process() throws InterruptedException {
		log.debug("Execute at : " + GregorianCalendar.getInstance().getTime());

		runEtcLoader();
		runMtcLoader();
	}

	/**
	 * 开启作业拉取ETC的图片信息
	 */
	@Async
	private void runEtcLoader() {
		etcDownloader.download(analyticalMapper.getRecords());
	}

	/**
	 * 开启作业拉取MTC的图片信息
	 */
	@Async
	private void runMtcLoader() {
		for (IMtcMapper mapper : new IMtcMapper[] { changecardMapper, enterthecarMapper, freenotfreeMapper,
				heavytruckMapper, longlightMapper, losecardMapper, tollcollectorMapper, typenotmatchMapper,
				weightdevicearnormalMapper }) {
			mtcDownloader.download(mapper.getRecords(), mapper);
		}
	}
}
