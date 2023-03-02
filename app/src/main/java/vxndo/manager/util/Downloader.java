package vxndo.manager.util;

import java.io.*;
import java.net.*;

public class Downloader {

	public static boolean downloadFile(String url, File output) {
		try {
			URL u = new URL(url);
			FileUtils.bufferedCopy(u.openStream(), output);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
