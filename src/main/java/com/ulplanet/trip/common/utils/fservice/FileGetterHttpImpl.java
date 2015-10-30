package com.ulplanet.trip.common.utils.fservice;

import com.ulplanet.trip.common.utils.HttpDownloadTools;

import java.io.File;
import java.io.IOException;

public class FileGetterHttpImpl extends AbstractDownloadGetter implements FileGetter {
	// server url, http
	private String	serverurl;

	public String getServerurl() {
		return serverurl;
	}

	/**
	 * 文件实际存放的服务器url(app访问使用)
	 * 
	 * @param serverurl
	 */
	public void setServerurl(String serverurl) {
		this.serverurl = FserviceUtil.adjustUrl(serverurl);
	}

	@Override
	protected void downloadFile(String realpath, File target) throws IOException {
		try {
			HttpDownloadTools.downloadFile(serverurl, realpath, target);
		} catch (IOException e) {
			throw e;
		}
	}

}
