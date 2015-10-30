package com.ulplanet.trip.common.utils.fservice;

import com.ulplanet.trip.common.config.Global;

import java.io.File;

/**
 * 
 * 默认实现方式,文件的处理在本app内部完成.
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public class FileGetterInnerAppImpl extends AbstractFileGetter implements FileGetter {

	private File	workfolder;
	private String	workfolderName;

	public FileGetterInnerAppImpl() {
		setWorkfolderName(DEFAULT_FOLDERNAME);
	}

	public File getFile(String realpath) {
		return new File(workfolder, realpath);
	}

	public String getFileUrl(String realpath) {
		return Global.getConfig("file.url") + workfolderName + "/" + realpath;
	}

	public File getWorkfolder() {
		return workfolder;
	}

	public String getWorkfolderName() {
		return workfolderName;
	}


	/**
	 * 设置文件存放目录的路径名(只能是相对路径,默认值files)
	 * 
	 * @param workfolderName
	 */
	public void setWorkfolderName(String workfolderName) {
		if (FserviceUtil.isAbsolutePath(workfolderName)) {
			throw new RuntimeException("Not a relative path:" + workfolderName);
		}
		this.workfolderName = workfolderName;
		this.workfolder = FserviceUtil.getFile(workfolderName);
	}

}