package com.ulplanet.trip.common.utils.fservice;

import java.io.File;

/**
 * 
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public class FileSaverHttpImpl implements FileSaver {
	private String	serverurl;

	public FileSaverHttpImpl() {
		super();
	}

	public void deleteFile(String realName) {
	}

	public String getServerurl() {
		return serverurl;
	}

	public void moveFile(String oldName, String newName) {
	}

	public void saveFile(File file, String realName) {
		System.out.println(realName);
	}

	/**
	 * 设置serverurl,即另外一个作为文件服务器的app的url
	 *  (这个url访问将给予App使用)
	 *
	 * @param serverurl
	 */
	public void setServerurl(String serverurl) {
		if (!serverurl.endsWith("/")) {
			serverurl += "/";
		}

		this.serverurl = serverurl;
	}

	public void updateFile(File file, String realName) {
	}


}
