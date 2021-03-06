package com.tongyuan.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;

/**
 * 文件操作工具类
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 * @author ThinkGem
 * @version 2013-01-15
 */
public class FileUtils {
	
	private static Logger log = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 复制单个文件，如果目标文件存在，则不覆盖
	 * @param srcFileName 待复制的文件各1�7
	 * @param descFileName 目标文件各1�7
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String descFileName) {
		return FileUtils.copyFileCover(srcFileName, descFileName, false);
	}

	/**
	 * 复制单个文件
	 * @param srcFileName 待复制的文件名
	 * @param descFileName 目标文件名
	 * @param coverlay 如果目标文件已存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFileCover(String srcFileName,
			String descFileName, boolean coverlay) {
		File srcFile = new File(srcFileName);
		// 判断源文件是否存圄1�7
		if (!srcFile.exists()) {
			log.debug("复制文件失败，源文件" + srcFileName + "不存在!");
			return false;
		}
		// 判断源文件是否是合法的文仄1�7
		else if (!srcFile.isFile()) {
			log.debug("复制文件失败，" + srcFileName + "不是一个文件!");
			return false;
		}
		File descFile = new File(descFileName);
		// 判断目标文件是否存在
		if (descFile.exists()) {
			// 如果目标文件存在，并且允许覆盄1�7
			if (coverlay) {
				log.debug("目标文件已存在，准备删除!");
				if (!FileUtils.delFile(descFileName)) {
					log.debug("删除目标文件" + descFileName + "失败!");
					return false;
				}
			} else {
				log.debug("复制文件失败，目标文件" + descFileName + "已存在!");
				return false;
			}
		} else {
			if (!descFile.getParentFile().exists()) {
				// 如果目标文件扄1�7在的目录不存在，则创建目彄1�7
				log.debug("目标文件所在的目录不存在，创建目录!");
				// 创建目标文件扄1�7在的目录
				if (!descFile.getParentFile().mkdirs()) {
					log.debug("创建目标文件所在的目录失败!");
					return false;
				}
			}
		}

		// 准备复制文件
		// 读取的位敄1�7
		int readByte = 0;
		InputStream ins = null;
		OutputStream outs = null;
		try {
			// 打开源文仄1�7
			ins = new FileInputStream(srcFile);
			// 打开目标文件的输出流
			outs = new FileOutputStream(descFile);
			byte[] buf = new byte[1024];
			// 丄1�7次读叄1�724个字节，当readByte丄1�7-1时表示文件已经读取完毄1�7
			while ((readByte = ins.read(buf)) != -1) {
				// 将读取的字节流写入到输出浄1�7
				outs.write(buf, 0, readByte);
			}
			log.debug("复制单个文件" + srcFileName + "刄1�7" + descFileName
					+ "成功!");
			return true;
		} catch (Exception e) {
			log.debug("复制文件失败＄1�7" + e.getMessage());
			return false;
		} finally {
			// 关闭输入输出流，首先关闭输出流，然后再关闭输入流
			if (outs != null) {
				try {
					outs.close();
				} catch (IOException oute) {
					oute.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException ine) {
					ine.printStackTrace();
				}
			}
		}
	}

	/**
	 * 复制整个目录的内容，如果目标目录存在，则不覆盄1�7
	 * @param srcDirName 源目录名
	 * @param descDirName 目标目录各1�7
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectory(String srcDirName, String descDirName) {
		return FileUtils.copyDirectoryCover(srcDirName, descDirName,
				false);
	}

	/**
	 * 复制整个目录的内宄1�7 
	 * @param srcDirName 源目录名
	 * @param descDirName 目标目录各1�7
	 * @param coverlay 如果目标目录存在，是否覆盄1�7
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyDirectoryCover(String srcDirName,
			String descDirName, boolean coverlay) {
		File srcDir = new File(srcDirName);
		// 判断源目录是否存圄1�7
		if (!srcDir.exists()) {
			log.debug("复制目录失败，源目录" + srcDirName + "不存圄1�7!");
			return false;
		}
		// 判断源目录是否是目录
		else if (!srcDir.isDirectory()) {
			log.debug("复制目录失败＄1�7" + srcDirName + "不是丄1�7个目彄1�7!");
			return false;
		}
		// 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔笄1�7
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		// 如果目标文件夹存圄1�7
		if (descDir.exists()) {
			if (coverlay) {
				// 允许覆盖目标目录
				log.debug("目标目录已存在，准备删除!");
				if (!FileUtils.delFile(descDirNames)) {
					log.debug("删除目录" + descDirNames + "失败!");
					return false;
				}
			} else {
				log.debug("目标目录复制失败，目标目彄1�7" + descDirNames + "已存圄1�7!");
				return false;
			}
		} else {
			// 创建目标目录
			log.debug("目标目录不存在，准备创建!");
			if (!descDir.mkdirs()) {
				log.debug("创建目标目录失败!");
				return false;
			}

		}

		boolean flag = true;
		// 列出源目录下的所有文件名和子目录各1�7
		File[] files = srcDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 如果是一个单个文件，则直接复刄1�7
			if (files[i].isFile()) {
				flag = FileUtils.copyFile(files[i].getAbsolutePath(),
						descDirName + files[i].getName());
				// 如果拷贝文件失败，则逄1�7出循玄1�7
				if (!flag) {
					break;
				}
			}
			// 如果是子目录，则继续复制目录
			if (files[i].isDirectory()) {
				flag = FileUtils.copyDirectory(files[i]
						.getAbsolutePath(), descDirName + files[i].getName());
				// 如果拷贝目录失败，则逄1�7出循玄1�7
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			log.debug("复制目录" + srcDirName + "刄1�7" + descDirName + "失败!");
			return false;
		}
		log.debug("复制目录" + srcDirName + "刄1�7" + descDirName + "成功!");
		return true;

	}

	/**
	 * 
	 * 删除文件，可以删除单个文件或文件处1�7
	 * 
	 * @param fileName 被删除的文件各1�7
	 * @return 如果删除成功，则返回true，否是返回false
	 */
	public static boolean delFile(String fileName) {
 		File file = new File(fileName);
		if (!file.exists()) {
			log.debug(fileName + "文件不存圄1�7!");
			return true;
		} else {
			if (file.isFile()) {
				return FileUtils.deleteFile(fileName);
			} else {
				return FileUtils.deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 
	 * 删除单个文件
	 * 
	 * @param fileName 被删除的文件各1�7
	 * @return 如果删除成功，则返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.debug("删除单个文件" + fileName + "成功!");
				return true;
			} else {
				log.debug("删除单个文件" + fileName + "失败!");
				return false;
			}
		} else {
			log.debug(fileName + "文件不存圄1�7!");
			return true;
		}
	}

	/**
	 * 
	 * 删除目录及目录下的文仄1�7
	 * 
	 * @param dirName 被删除的目录扄1�7在的文件路径
	 * @return 如果目录删除成功，则返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dirName) {
		String dirNames = dirName;
		if (!dirNames.endsWith(File.separator)) {
			dirNames = dirNames + File.separator;
		}
		File dirFile = new File(dirNames);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			log.debug(dirNames + "目录不存圄1�7!");
			return true;
		}
		boolean flag = true;
		// 列出全部文件及子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文仄1�7
			if (files[i].isFile()) {
				flag = FileUtils.deleteFile(files[i].getAbsolutePath());
				// 如果删除文件失败，则逄1�7出循玄1�7
				if (!flag) {
					break;
				}
			}
			// 删除子目彄1�7
			else if (files[i].isDirectory()) {
				flag = FileUtils.deleteDirectory(files[i]
						.getAbsolutePath());
				// 如果删除子目录失败，则�1�7�1�7出循玄1�7
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			log.debug("删除目录失败!");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			log.debug("删除目录" + dirName + "成功!");
			return true;
		} else {
			log.debug("删除目录" + dirName + "失败!");
			return false;
		}

	}

	/**
	 * 创建单个文件
	 * @param descFileName 文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createFile(String descFileName) {
		File file = new File(descFileName);
		if (file.exists()) {
			log.debug("文件" + descFileName + "已存圄1�7!");
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			log.debug(descFileName + "为目录，不能创建目录!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件扄1�7在的目录不存在，则创建目彄1�7
			if (!file.getParentFile().mkdirs()) {
				log.debug("创建文件扄1�7在的目录失败!");
				return false;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				log.debug(descFileName + "文件创建成功!");
				return true;
			} else {
				log.debug(descFileName + "文件创建失败!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(descFileName + "文件创建失败!");
			return false;
		}

	}

	/**
	 * 创建目录
	 * @param descDirName 目录各1�7,包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createDirectory(String descDirName) {
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			log.debug("目录" + descDirNames + "已存圄1�7!");
			return false;
		}
		// 创建目录
		if (descDir.mkdirs()) {
			log.debug("目录" + descDirNames + "创建成功!");
			return true;
		} else {
			log.debug("目录" + descDirNames + "创建失败!");
			return false;
		}

	}

	/**
	 * 压缩文件或目彄1�7
	 * @param srcDirName 压缩的根目录
	 * @param fileName 根目录下的待压缩的文件名或文件夹名，其中*戄1�7""表示跟目录下的全部文仄1�7
	 * @param descFileName 目标zip文件
	 */
	public static void zipFiles(String srcDirName, String fileName,
			String descFileName) {
		// 判断目录是否存在
		if (srcDirName == null) {
			log.debug("文件压缩失败，目彄1�7" + srcDirName + "不存圄1�7!");
			return;
		}
		File fileDir = new File(srcDirName);
		if (!fileDir.exists() || !fileDir.isDirectory()) {
			log.debug("文件压缩失败，目彄1�7" + srcDirName + "不存圄1�7!");
			return;
		}
		String dirPath = fileDir.getAbsolutePath();
		File descFile = new File(descFileName);
		try {
			ZipOutputStream zouts = new ZipOutputStream(new FileOutputStream(
					descFile));
			if ("*".equals(fileName) || "".equals(fileName)) {
				FileUtils.zipDirectoryToZipFile(dirPath, fileDir, zouts);
			} else {
				File file = new File(fileDir, fileName);
				if (file.isFile()) {
					FileUtils.zipFilesToZipFile(dirPath, file, zouts);
				} else {
					FileUtils
							.zipDirectoryToZipFile(dirPath, file, zouts);
				}
			}
			zouts.close();
			log.debug(descFileName + "文件压缩成功!");
		} catch (Exception e) {
			log.debug("文件压缩失败＄1�7" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 解压缩ZIP文件，将ZIP文件里的内容解压到descFileName目录丄1�7
	 * @param zipFileName 霄1�7要解压的ZIP文件
	 * @param descFileName 目标文件
	 */
	public static boolean unZipFiles(String zipFileName, String descFileName) {
		String descFileNames = descFileName;
		if (!descFileNames.endsWith(File.separator)) {
			descFileNames = descFileNames + File.separator;
		}		
        try {
			// 根据ZIP文件创建ZipFile对象
			ZipFile zipFile = new ZipFile(zipFileName);
			ZipEntry entry = null;
			String entryName = null;
			String descFileDir = null;
			byte[] buf = new byte[4096];
			int readByte = 0;
			// 获取ZIP文件里所有的entry
			@SuppressWarnings("rawtypes")
			Enumeration enums = zipFile.getEntries();
			// 遍历扄1�7有entry
			while (enums.hasMoreElements()) {
				entry = (ZipEntry) enums.nextElement();
				// 获得entry的名孄1�7
				entryName = entry.getName();
				descFileDir = descFileNames + entryName;
				if (entry.isDirectory()) {
					// 如果entry是一个目录，则创建目彄1�7
					new File(descFileDir).mkdirs();
					continue;
				} else {
					// 如果entry是一个文件，则创建父目录
					new File(descFileDir).getParentFile().mkdirs();
				}
				File file = new File(descFileDir);
				// 打开文件输出浄1�7
				OutputStream os = new FileOutputStream(file);
				// 从ZipFile对象中打弄1�7entry的输入流
		        InputStream is = zipFile.getInputStream(entry);
				while ((readByte = is.read(buf)) != -1) {
					os.write(buf, 0, readByte);
				}
				os.close();
				is.close();
			}
			zipFile.close();
			log.debug("文件解压成功!");
			return true;
		} catch (Exception e) {
			log.debug("文件解压失败＄1�7" + e.getMessage());
			return false;
		}
	}

	/**
	 * 将目录压缩到ZIP输出浄1�7
	 * @param dirPath 目录路径
	 * @param fileDir 文件信息
	 * @param zouts 输出浄1�7
	 */
	public static void zipDirectoryToZipFile(String dirPath, File fileDir,
			ZipOutputStream zouts) {
		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			// 空的文件处1�7
			if (files.length == 0) {
				// 目录信息
				ZipEntry entry = new ZipEntry(getEntryName(dirPath, fileDir));
				try {
					zouts.putNextEntry(entry);
					zouts.closeEntry();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					// 如果是文件，则调用文件压缩方泄1�7
					FileUtils
							.zipFilesToZipFile(dirPath, files[i], zouts);
				} else {
					// 如果是目录，则�1�7�归调用
					FileUtils.zipDirectoryToZipFile(dirPath, files[i],
							zouts);
				}
			}

		}

	}

	/**
	 * 将文件压缩到ZIP输出浄1�7
	 * @param dirPath 目录路径
	 * @param file 文件
	 * @param zouts 输出浄1�7
	 */
	public static void zipFilesToZipFile(String dirPath, File file,
			ZipOutputStream zouts) {
		FileInputStream fin = null;
		ZipEntry entry = null;
		// 创建复制缓冲匄1�7
		byte[] buf = new byte[4096];
		int readByte = 0;
		if (file.isFile()) {
			try {
				// 创建丄1�7个文件输入流
				fin = new FileInputStream(file);
				// 创建丄1�7个ZipEntry
				entry = new ZipEntry(getEntryName(dirPath, file));
				// 存储信息到压缩文仄1�7
				zouts.putNextEntry(entry);
				// 复制字节到压缩文仄1�7
				while ((readByte = fin.read(buf)) != -1) {
					zouts.write(buf, 0, readByte);
				}
				zouts.closeEntry();
				fin.close();
				System.out
						.println("添加文件" + file.getAbsolutePath() + "到zip文件丄1�7!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取待压缩文件在ZIP文件中entry的名字，即相对于跟目录的相对路径各1�7
	 * @param dirPath 目录各1�7
	 * @param file entry文件各1�7
	 * @return
	 */
	private static String getEntryName(String dirPath, File file) {
		String dirPaths = dirPath;
		if (!dirPaths.endsWith(File.separator)) {
			dirPaths = dirPaths + File.separator;
		}
		String filePath = file.getAbsolutePath();
		// 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存傄1�7
		if (file.isDirectory()) {
			filePath += "/";
		}
		int index = filePath.indexOf(dirPaths);

		return filePath.substring(index + dirPaths.length());
	}

}
