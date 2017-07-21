package com.expressway.ftp.client.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.expressway.ftp.client.messages.FeedBackMessage;
import com.expressway.ftp.client.protocal.ConditionFiled;
import com.expressway.ftp.client.task.ManualDownloadTask;

/**
 * @author Ajaxfan
 */
@RestController
@RequestMapping(value = "services", method = RequestMethod.POST)
public class ManualDownloadController {
	/** ETC稽查数据接口 */
	private @Autowired ManualDownloadTask manualDownloadTask;

	/**
	 * 下载图片
	 * 
	 * @param request
	 * @param cf
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping("manualdownload")
	public Object systemLogin(HttpServletRequest request, ConditionFiled cf) throws InterruptedException {
		manualDownloadTask.process(cf.getBeginDate(), cf.getEndDate());

		return new FeedBackMessage(true);
	}
}
