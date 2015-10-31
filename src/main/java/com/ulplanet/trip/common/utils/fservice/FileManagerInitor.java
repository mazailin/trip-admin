package com.ulplanet.trip.common.utils.fservice;

import com.ulplanet.trip.common.utils.FileManager;

/**
 * 
 * 辅助类,仅仅是为了方便spring"灌入"FileManager中的静态域.
 * @author zhangxd
 * @date 2015年7月23日
 *
 */
public class FileManagerInitor {
	
	public FileManagerInitor(FileSaver fileSaver, FileGetter fileGetter) {
		FileManager.setFileSaver(fileSaver);
		FileManager.setFileGetter(fileGetter);
	}

	public FileManagerInitor() { }

	public void setFileSaver(FileSaver fileSaver) {
		FileManager.setFileSaver(fileSaver);
	}

	public void setFileGetter(FileGetter fileGetter) {
		FileManager.setFileGetter(fileGetter);
	}
}