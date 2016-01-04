package com.quix.aia.cn.imo.utilities;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

	public static void renameFolder(String dirPath,String newDirName) {
		
		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			return;
		}
		else
		{
			File newDir = new File(dir.getParent() + File.separator + newDirName);
			dir.renameTo(newDir);
		}
	}
	
	public static void deleteFileNFolder(File file)
	{
		if(file.isDirectory())
		{
			File[] files=file.listFiles();
			for(int i=0;i<files.length;i++)
			{
				deleteFileNFolder(files[i]);
			}
			if(file.exists())
				file.delete();
		}
		else
		{
			if(file.exists())
				file.delete();
		}
	}

	public static void copyDirectory(File sourceLocation , File targetLocation)
		    throws IOException {

		        if (sourceLocation.isDirectory()) {
		            if (!targetLocation.exists()) {
		                targetLocation.mkdir();
		            }

		            String[] children = sourceLocation.list();
		            for (int i=0; i<children.length; i++) {
		                copyDirectory(new File(sourceLocation, children[i]),
		                        new File(targetLocation, children[i]));
		            }
		        } else {

		            InputStream in = new FileInputStream(sourceLocation);
		            OutputStream out = new FileOutputStream(targetLocation);

		            // Copy the bits from instream to outstream
		            byte[] buf = new byte[1024];
		            int len;
		            while ((len = in.read(buf)) > 0) {
		                out.write(buf, 0, len);
		            }
		            in.close();
		            out.close();
		        }
		    }
	public static void copyFile(File source, File dest) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		}catch(Exception e){e.printStackTrace();} finally {
			if(input!=null)
			input.close();
			if(output!=null)
			output.close();
		}
	}
}
