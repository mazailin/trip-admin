package com.ulplanet.trip.common.utils.fservice;

import java.io.File;

/**
 * 
 * 浏览器直接通过url访问实际文件位置.
 * url getter和其它的都不一样,它的两种get方式是不同的客户端.
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public class FileGetterUrlImpl extends AbstractFileGetter implements FileGetter {
	
	private String		serverurl;
	private String		serverurlForApp;
	private FileGetter	appGetter;

	public FileGetter getAppGetter() {
		if (appGetter == null) {
			String url = serverurlForApp != null ? serverurlForApp : serverurl;
			if (url != null) {
				FileGetterHttpImpl httpGetter = new FileGetterHttpImpl();
				httpGetter.setServerurl(url);
				appGetter = httpGetter;
			}
		}
		return appGetter;
	}

	public File getFile(String realpath) {
		if (getAppGetter() == null) {
			throw new RuntimeException("All properties not setted:appGetter, serverurl, serverurlForApp");
		}
		return appGetter.getFile(realpath);
	}

	public String getFileUrl(String realpath) {
		return serverurl + realpath;
	}

	public String getServerurl() {
		return serverurl;
	}

	/**
	 * 设置app中可以访问文件的server url,
	 * 
	 * @return
	 */
	public String getServerurlForApp() {
		return serverurlForApp;
	}

	/**
	 * 设置针对App的FileGetter
	 * 
	 * @param appGetter
	 */
	public void setAppGetter(FileGetter appGetter) {
		this.appGetter = appGetter;
	}

	/**
	 * 设置文件实际存放的服务器的下载地址(这个地址将被浏览器端访问,可使用相对路径.)
	 * 
	 * @param serverUrl
	 */
	public void setServerurl(String serverUrl) {
		this.serverurl = serverUrl;
	}

	/**
	 * 设置App访问实际存放文件服务器的下载地址(这个地址将被App使用)
	 * 
	 * @return
	 */
	public void setServerurlForApp(String serverurlForApp) {
		this.serverurlForApp = serverurlForApp;
	}

}
