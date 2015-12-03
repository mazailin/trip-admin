package com.ulplanet.trip.common.utils.fservice;

import com.ulplanet.trip.common.config.Global;
import com.ulplanet.trip.common.utils.ServletContextHolder;

import java.io.File;
import java.io.IOException;


/**
 * 
 * 抽象类,将文件"下载"到app临时目录下.
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public abstract class AbstractDownloadGetter extends AbstractFileGetter implements FileGetter {

	private File tmpfolder;
	private String tmpfolderName;

	public AbstractDownloadGetter() {
		// 默认使用目录.
		setTmpfolderName(DEFAULY_TMPFOLDERNAME);
	}

	public File getFile(String realpath) {
	    File dfile = new File(tmpfolder, realpath);
	    if (dfile.exists()) {
	        return dfile;
	    }
		try {
			if (!dfile.getParentFile().exists()) {
				dfile.getParentFile().mkdirs();
			}
			downloadFile(realpath, dfile);
			return dfile;
		} catch (Exception ex) {
			return null;
		}
	}

	public String getFileUrl(String realpath) {
		File dfile = new File(tmpfolder, realpath);
		if (dfile.exists()) {
            return calFileUrl(dfile);
        }
		try {
			if (!dfile.getParentFile().exists()) {
				dfile.getParentFile().mkdirs();
			}
			downloadFile(realpath, dfile);
			return calFileUrl(dfile);
		} catch (Exception ex) {
			return null;
		}
	}

	public File getTmpfolder() {
		return tmpfolder;
	}

	public String getTmpfolderName() {
		return tmpfolderName;
	}

	/**
	 * 设置临时文件存放目录.(非必须,默认是当前appPath下的tmp_files
	 * 
	 * @param tmpfolder
	 */
	public void setTmpfolder(File tmpfolder) {
		this.tmpfolder = tmpfolder;
	}

	/**
	 * 设置临时文件存放目录的名称,是一个相对于当前AppPath的路径.
	 *  (非必须,默认值为tmp_files)
	 * 
	 * @param tmpfolderName
	 */
	public void setTmpfolderName(String tmpfolderName) {
		if (FserviceUtil.isAbsolutePath(tmpfolderName)) {
			throw new RuntimeException("Not a absolute path:" + tmpfolderName);
		}
		this.tmpfolderName = tmpfolderName;
		setTmpfolder(FserviceUtil.getFile(tmpfolderName));
	}

	/**
	 * 下载文件到指定位置上.
	 * 
	 * @param realpath
	 * @param target
	 */
	protected abstract void downloadFile(String realpath, File target) throws IOException;

	//
	// //////////////////////////////////////////
	//   getters & setters
	// 

	/**
	 * 计算文件的访问url.
	 * 
	 * @param file 文件,位于WebApp目录下.
	 * @return
	 */
	private String calFileUrl(File file) {
        String appPath = ServletContextHolder.getAppPath();
		return Global.getConfig("file.url") + file.getAbsolutePath().substring(appPath.length()).replace('\\', '/');
	}

}
