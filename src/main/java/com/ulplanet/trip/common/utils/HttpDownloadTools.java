package com.ulplanet.trip.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public abstract class HttpDownloadTools {

	public static void downloadFile(String serverurl, String realpath, File target) throws IOException {
		downloadFile(new URL(new URL(serverurl), realpath), target);
	}

	public static void downloadFile(String url, File target) throws IOException {
		downloadFile(new URL(url), target);
	}

	public static void downloadFile(URL url, File target) throws IOException {
		String f = url.getFile();
		f = URLEncoder.encode(f, "utf-8");
		f = f.replaceAll("%2F", "/");
		f = StringUtils.replace(f, "+", "%20");
		url = new URL(url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + f);
		
		FileOutputStream fos = new FileOutputStream(target);		
		InputStream is = url.openStream();
		byte[] buf = new byte[102400];
		int rc;
		while ((rc = is.read(buf)) != -1) {
			fos.write(buf, 0, rc);
		}
		FileUtils.close(is);
        FileUtils.close(fos);
	}
}