/**
 * 
 */
package com.ulplanet.trip.common.utils.fservice;

import com.ulplanet.trip.common.utils.ServletContextHolder;
import com.ulplanet.trip.common.utils.StringUtils;

import java.io.File;

/**
 * 
 * 辅助类,仅限于本包内部使用.
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public abstract class FserviceUtil {

	public static String addEndSlash(String name) {
		return StringUtils.isEmpty(name) || name.endsWith("/") ? name : name + "/";
	}

	public static String clearEndSlash(String name) {
		return StringUtils.isEmpty(name) || !name.endsWith("/") ? name : name.substring(0, name.length() - 1);
	}

	/**
	 * 是否是一个绝对路径.
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isAbsolutePath(String path) {
		return path.startsWith("/") || path.startsWith("\\") || path.contains(":");
	}

	/**
	 * 校正url.
	 * 
	 * @param url
	 * @return
	 */
	public static String adjustUrl(String url) {
		// 如果为null,或""(即当前目录),则不处理.
		if (StringUtils.isEmpty(url)) {
			return url;
		}

		return url.endsWith("/") ? url : url + "/";
	}

	/**
	 * 
	 * @param folder
	 * @param name
	 * @return
	 */
	public static File getFile(File folder, String name) {
		return isAbsolutePath(name) ? new File(name) : new File(folder, name);
	}

	/**
	 * 获取文件,如果是相对路径,则以AppPath为Parent
	 * 
	 * @param name
	 * @return
	 */
	public static File getFile(String name) {
        String appPath = ServletContextHolder.getAppPath();
		return isAbsolutePath(name) ? new File(name) : new File(new File(appPath), name);
	}

}
