package com.ulplanet.trip.common.utils.fservice;


import com.ulplanet.trip.common.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public class FileGetterDiskImpl extends AbstractDownloadGetter implements FileGetter {

	private String	workfolderName;
	private File	workfolder;

	public File getWorkfolder() {
		return workfolder;
	}

	public String getWorkfolderName() {
		return workfolderName;
	}

	@Override
	public File getFile(String realpath) {
		return super.getFile(realpath);
	}

	/**
	 * 设置文件存放的目录.
	 * 
	 * @param workfolder
	 */
	public void setWorkfolder(File workfolder) {
		this.workfolder = workfolder;
	}

	/**
	 * 设置文件存放目录的路径(可以是相对当前AppPath的路径)
	 * 
	 * @param workfolderName
	 */
	public void setWorkfolderName(String workfolderName) {
		this.workfolderName = workfolderName;
		this.setWorkfolder(FserviceUtil.getFile(workfolderName));
	}

	@Override
	protected void downloadFile(String realpath, File target) throws IOException {
		File source = new File(workfolder, realpath);
		if (!FileUtils.copy(source, target)) {
			throw new IOException("Cannot copy file, source(" + source.getAbsolutePath() + "), target("
					+ target.getAbsolutePath() + ")");
		}
	}

}