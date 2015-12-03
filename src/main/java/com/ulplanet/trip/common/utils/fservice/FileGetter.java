package com.ulplanet.trip.common.utils.fservice;

import java.io.File;

/**
 * 
 * 文件获取器.
 * FileGetter为两个情况下服务,第一是浏览器客户端访问文件时候所需url(getFileUrl()函数为此目的)
 * 第二为App服务器端运行时候,要获取文件处理(getFile()函数为此目的)
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public interface FileGetter {

	/**
	 * 默认的文件存放的目录名称 files
	 */
	String	DEFAULT_FOLDERNAME		= "files";
	
	/**
	 * 默认的临时文件存放目录.
	 */
	String	DEFAULY_TMPFOLDERNAME	= "tmp_files";

	/**
	 * 获得一个文件..
	 *  <B>getFileUrl和getFile函数,不能保证每次返回的对象都是同一个,因为有可能要使用临时文件.</B>
	 *  
	 * @param realpath
	 * @return
	 */
	File getFile(String realpath);

	/**
	 * 获得一个文件的web访问url
	 * 
	 * @param realpath 文件的存放路径,在数据库中保存该信息.
	 * @return
	 */
	String getFileUrl(String realpath);

}
