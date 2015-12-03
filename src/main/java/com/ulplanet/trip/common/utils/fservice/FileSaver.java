package com.ulplanet.trip.common.utils.fservice;

import java.io.File;

/**
 * 
 * 文件处理器.处理文件的保存,删除,更新等操作.
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public interface FileSaver {

	/**
	 * 默认的文件存放的目录名称 files
	 */
	String DEFAULT_FOLDERNAME = FileGetter.DEFAULT_FOLDERNAME;
	
	/**
	 * 删除一个文件 
	 * 
	 * @param realName 相对路径名.
	 */
	void deleteFile(String realName);
	
	/**
	 * 将一个文件,由旧名称路径转移到新文件路径上去.
	 * 
	 * @param oldName 原来的文件名称.
	 * @param newName 现在的文件名称.
	 */
	void moveFile(String oldName, String newName);
	
	/**
	 * 
	 * @param file 文件
	 * @param realName 相对路径名(访问时候使用,也是存放在数据库中的字段) 
	 */
	void saveFile(File file, String realName);
	
	/**
	 * 更新一个文件.
	 * 
	 * @param file
	 * @param realName
	 */
	void updateFile(File file, String realName);
	
}


