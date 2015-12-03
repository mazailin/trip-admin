package com.ulplanet.trip.common.utils;

import com.ulplanet.trip.common.utils.fservice.FileGetter;
import com.ulplanet.trip.common.utils.fservice.FileIndex;
import com.ulplanet.trip.common.utils.fservice.FileSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 * 文件管理助手类.
 * 提供文件的保存，获取，删除等与文件管理相关的方法.
 * 为了实现统一的文件处理方案，所有要交由系统保存的文件均通过此方式处理.
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public class FileManager {
	
	/**
	 * 删除文件,包括数据库中的记录和实际文件.
	 * 
	 * @param filepath
	 * @return
	 */
	public static boolean delete(String filepath) {

		// 调用文件存储器删除文件.
		getFileSaver().deleteFile(filepath);
			
		return true;
	}

	/**
	 * 删除文件集合,包括数据库中的记录和实际文件.
	 * 
	 * @param filePathList
	 * @return
	 */
	public static boolean deleteList(final List<String> filePathList) {
		if (filePathList == null || filePathList.size() == 0) {
			throw new RuntimeException("Parameter filePathList must not null");
		}
		
		if (filePathList.size() == 1) {
			return delete(filePathList.get(0));
		}
		
		for (int i = 0; i < filePathList.size(); i++) {
			String path = filePathList.get(i);
			// 调用文件存储器删除文件.
			getFileSaver().deleteFile(path);
		}
		
		return true;
	}
	
	/**
	 * 将一个"未保存"的文件保存,返回保存后的信息.
	 * 
	 * @param ufi
	 * @return 
	 */
	public static FileIndex save(FileIndex ufi) {
		FileIndex[] fs = saves(ufi);
		return fs[0];
	}
	
	/**
	 * 保存文件到文件管理系统.
	 * @param ufis
	 * @return
	 */
	public static FileIndex[] saves(FileIndex... ufis) {
		
		List<FileIndex> arr = new ArrayList<>();
		for (FileIndex ufi : ufis) {

			String trueName = Objects.toString(ufi.getTruename());
			
			if (StringUtils.isEmpty(trueName)) {
				continue;
			}
			
			String id = IdGen.uuid();
			
			ufi.setFileid(id);
			
			String folderPath = ufi.getMcode() + "/" + id.substring(0, 2);
			String suffix = "";
			int pos = trueName.lastIndexOf(".");
			if (pos > -1) {
				suffix = trueName.substring(pos + 1);
			}
			String saveFile = id + (StringUtils.isNotEmpty(suffix) ? ("." + suffix) : "");
			String filePath = folderPath + "/" + saveFile;
			ufi.setPath(filePath);
			
			File upFile = ufi.getUpfile();
			if (upFile == null || !upFile.exists()) {
				upFile = new File(saveFile);
				try {
					ufi.getmUpfile().transferTo(upFile);
				} catch (IllegalStateException | IOException ex) {
					throw new IllegalStateException(ex.getMessage());
				}
            }
			
			getFileSaver().saveFile(upFile, filePath);
			if (ufi.getUpfile() == null && ufi.getmUpfile() != null) {
				upFile.delete();
			}
			
			arr.add(ufi);
		}
		return arr.toArray(new FileIndex[0]);
	}
	
	/**
	 * 根据文件的"真实""相对路径名",获取文件的File.
	 * 
	 * @param realpath
	 * @return
	 */
	public static File getFileByRealpath(String realpath) {
		return getFileGetter().getFile(realpath);
	}

	/**
	 * 获得系统当前配置的文件获取方式.
	 * 系统当前支持的文件获取方式有：硬盘(相对或绝对路径)，http.
	 * 硬盘方式支持 局域网通过共享文件夹的方式.
	 * @return
	 */
	public static FileGetter getFileGetter() {
		initWithInnerFileActors();
		return fileGetter;
	}

	/**
	 * 获得系统当前配置的文件保存方式.
	 * 系统当前支持的文件获取方式有：硬盘(相对或绝对路径)，http.
	 * 硬盘方式支持 局域网通过共享文件夹的方式.
	 * @return
	 */
	public static FileSaver getFileSaver() {
		initWithInnerFileActors();
		return fileSaver;
	}

	/**
	 * 获得文件的访问路径.
	 * 
	 * @param realpath
	 * @return
	 */
	public static String getFileUrlByRealpath(String realpath) {
		return getFileGetter().getFileUrl(realpath);
	}

	
	/**
	 * 设置(修改)文件获取方式.
	 * @param fileGetter
	 */
	public static void setFileGetter(FileGetter fileGetter) {
		FileManager.fileGetter = fileGetter;
	}
	
	/**
	 * 设置(修改)文件保存方式.
	 * @param fileSaver
	 */
	public static void setFileSaver(FileSaver fileSaver) {
		FileManager.fileSaver = fileSaver;
	}
	
	/**
	 * 初始化内部默认的FileGetter和FileSetter
	 *
	 */
	private static void initWithInnerFileActors() {
		if (fileSaver != null && fileGetter != null) {
			return;
		}

		String appPath = ServletContextHolder.getAppPath();
		if (StringUtils.isEmpty(appPath)) {
			throw new RuntimeException("Application Path not set.");
		}

		if (fileSaver == null) {
			fileSaver = SpringContextHolder.getBean("defaultSaver");
		}
		if (fileGetter == null) {
			fileGetter = SpringContextHolder.getBean("defaultGetter");
		}
	}

	private static FileSaver	fileSaver;

	private static FileGetter	fileGetter;

}
