package com.ulplanet.trip.common.utils.fservice;

import com.ulplanet.trip.common.utils.FileUtils;

import java.io.File;

/**
 * 
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public class FileSaverDiskImpl implements FileSaver {

	private String	workfolderName;
	private File	workfolder;

	public void deleteFile(String realName) {
		File f = new File(workfolder, realName);
		FileUtils.delete(f);
	}
	
	public File getWorkfolder() {
		return workfolder;
	}

	public String getWorkfolderName() {
		return workfolderName;
	}

	public void moveFile(String oldName, String newName) {
		File oldFile = new File(workfolder, oldName);
		File newFile = new File(workfolder, newName);
        FileUtils.move(oldFile, newFile);
	}


	public void saveFile(File file, String realName) {
		File newFile = new File(workfolder, realName);
        FileUtils.copy(file, newFile);
	}

	/**
	 * 设置存放文件的目录.
	 * 
	 * @param workfolder
	 */
	public void setWorkfolder(File workfolder) {
		this.workfolder = workfolder;
	}

	/**
	 * 设置存放文件的目录名称,可以是相对于当前appPath的目录(例如../files, d:/tmp, /tmp).
	 * 
	 * @param workfolderName
	 */
	public void setWorkfolderName(String workfolderName) {
		this.workfolderName = workfolderName;	
		
		this.setWorkfolder(FserviceUtil.getFile(workfolderName));
	}

	public void updateFile(File file, String realName) {
		saveFile(file, realName);
	}

}
