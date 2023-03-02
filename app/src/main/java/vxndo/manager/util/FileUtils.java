package vxndo.manager.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUtils {

	public static ArrayList<File> listAll(File folder) {
		ArrayList<File> dirs = new ArrayList<>();
		ArrayList<File> fils = new ArrayList<>();
		for (File file: folder.listFiles()) {
			if (file.isDirectory()) {
				dirs.add(file);
				dirs.addAll(listAll(file));
			} else fils.add(file);
		} dirs.addAll(fils);
		return dirs;
	}

	public static ArrayList<File> list(File folder) {
		return list(folder, null);
	}

	public static ArrayList<File> list(File folder, Comparator<File> comparator) {
		ArrayList<File> dirs = new ArrayList<>();
		ArrayList<File> fils = new ArrayList<>();
		for (File file: folder.listFiles()) {
			if (file.isDirectory()) {
				dirs.add(file);
			} else fils.add(file);
		} if (comparator != null) {
			Collections.sort(dirs, comparator);
			Collections.sort(fils, comparator);
		} dirs.addAll(fils);
		return dirs;
	}

	public static ArrayList<File> listAllFiles(File folder) {
		ArrayList<File> fils = new ArrayList<>();
		for (File file: folder.listFiles()) {
			if (file.isDirectory()) {
				fils.addAll(listAllFiles(file));
			} else fils.add(file);
		} return fils;
	}

	public static ArrayList<File> listFiles(File folder) {
		ArrayList<File> fils = new ArrayList<>();
		for (File file: folder.listFiles()) {
			if (!file.isDirectory()) {
				fils.add(file);
			}
		} return fils;
	}

	public static ArrayList<File> listAllDirs(File folder) {
		ArrayList<File> dirs = new ArrayList<>();
		for (File file: folder.listFiles()) {
			if (file.isDirectory()) {
				dirs.add(file);
				dirs.addAll(listAllDirs(file));
			}
		} return dirs;
	}

	public static ArrayList<File> listDirs(File folder) {
		ArrayList<File> dirs = new ArrayList<>();
		for (File file: folder.listFiles()) {
			if (file.isDirectory()) {
				dirs.add(file);
			}
		} return dirs;
	}

	public static String read(File file) throws Exception {
		return new String(readAllBytes(file));
	}

	public static byte[] readAllBytes(File file) throws Exception {
		return Files.readAllBytes(file.toPath()); 
	}

	public static boolean write(File file, String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void bufferedCopy(InputStream in, File out) throws Exception {
		OutputStream os = new FileOutputStream(out);
		byte[] buffer = new byte[8192];
		int length;
		while ((length = in.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		} in.close();
		os.close();
	}

	public static Comparator<File> FILE_NAME_COMPARATOR = new Comparator<File>() {
		@Override
		public int compare(File p1, File p2) {
			return p1.getName().compareToIgnoreCase(p2.getName());
		}
	};
}
