package com.expressway.ftp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请求拦截器
 *
 * @author Ajaxfan
 */
@Controller
public class GenericController {
	/**
	 * 管理系统登录页面处理
	 * 
	 * @param model
	 *            模型对象
	 * @return 要加载模板名称
	 */
	@RequestMapping(value = { "/", "index" })
	public String index() {
		return "index";
	}
}
