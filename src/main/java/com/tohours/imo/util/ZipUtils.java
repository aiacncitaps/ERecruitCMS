package com.tohours.imo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	public static void zip(ZipOutputStream out, File file, String base)
			throws Exception {
		if (file.isDirectory()) {
			File[] fileList = file.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fileList.length; i++) {
				zip(out, fileList[i], base + fileList[i].getName());
			}

		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(file);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}

	}
	
	public static String zip(File file){
		try {
			String name = file.getName();
			String date = TohoursUtils.date2string(new Date(), "_yyyyMMdd_HHmmss");
			String zipName = name + date + ".zip";
			String zipFileName = file.getParent() + "/" + zipName;
			System.out.println(zipFileName);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			zip(out, file, name);
			out.close();
			return zipName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void zip(File file, String zipName){
		try {

			String zipFileName = file.getParent() + "/" + zipName;
			String name = file.getName();
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			zip(out, file, name);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		File file = new File("/Users/niejacob/Downloads/html");
//		String zipFileName = "/Users/niejacob/Downloads/tohours.zip";
//		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
//		zip(out, file, "tohours-test");
//		out.close();
		zip(file);
	}
}