package com.expressway.ftp.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.expressway.ftp.client.mapper.AnalyticalMapper;
import com.expressway.ftp.client.models.Analytical;

/**
 * FTP下载工具
 * 
 * @author Ajaxfan
 */
@Component
public class FTPDownloader {
	/** 服务器地址 */
	private @Value("${com.expressway.ftp.server}") String url;
	/** 端口号 */
	private @Value("${com.expressway.ftp.port}") int port;
	/** 用户名 */
	private @Value("${com.expressway.ftp.username}") String username;
	/** 密码 */
	private @Value("${com.expressway.ftp.password}") String password;
	/** 本地路径 */
	private @Value("${com.expressway.ftp.local_directory}") String local;

	/** 日志工具类 */
	private Logger log = Logger.getLogger("com.expressway.ftp.client");
	

	/** ETC稽查数据接口 */
	private @Autowired AnalyticalMapper analyticalMapper;

	/**
	 * 下载远程服务器文件
	 * 
	 * @param remotePath
	 * @param filename
	 * @return
	 */
	public boolean download(List<Analytical> list) {
		log.debug("Analytical Object Size : " + list.size());

		try {
			// 通过数据结果集来从服务器拉取图片，如果结果集为空，则本地目录不会被创建
			for (Analytical analytical : list) {
				retriveFileFromRemote(analytical);
				analytical.setImageLoaded("1");
				
				// 更新数据状态
				analyticalMapper.updateByPrimaryKey(analytical);
			}
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取远程文件到本地
	 * 
	 * @param analytical
	 * @throws IOException
	 */
	private void retriveFileFromRemote(Analytical analytical) throws IOException {
		OutputStream out = null;
		FTPClient ftp = null;

		try {
			ftp = getClient();
			// 本地文件夹对象
			File localDir = new File(local + File.separator + analytical.getExitDate());
			// 如果文件夹不存在，需要先创建
			if (!localDir.exists()) {
				localDir.mkdirs();
			}
			// 设置当前FTP工作目录
			ftp.changeWorkingDirectory(analytical.getExitDate());

			// 数据缓存
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			// 写出文件到本地
			boolean writed = ftp.retrieveFile(analytical.getCarImage(), buffer);

			if (writed == true) {// 本地文件的流工具
				out = new FileOutputStream(localDir.getPath() + File.separator + analytical.getCarImage());
				buffer.writeTo(out);
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					out = null;
				}
			}
			if (ftp != null) {
				try {
					if (ftp.isConnected()) {
						ftp.logout();
						ftp.disconnect();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取ftp连接
	 * 
	 * @return
	 * @throws IOException
	 */
	private FTPClient getClient() throws IOException {
		FTPClient client = new FTPClient();
		client.connect(url, port);
		client.login(username, password);
		client.enterLocalPassiveMode();

		return client;
	}
}
