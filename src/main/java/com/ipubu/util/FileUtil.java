package com.ipubu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
/**
 * @ClassName FileUtil
 * @Description
 * @Author jzy
 */
public class FileUtil {

	public static void saveToFile(String filepath, ArrayList<String> source) throws IOException{
		File file = new File(filepath);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
		for (String str : source) {
			bufferedWriter.write(str);
			bufferedWriter.write("\n");
		}
		bufferedWriter.close();
	}
	
	public static void saveToFilCsv(String filepath, ArrayList<String> source)
			throws IOException {
		File file = new File(filepath);
		if (!file.exists()) {
			file.createNewFile();
		}
		byte [] header = new byte []{(byte)0xEF,(byte)0xBB,(byte)0xBF};
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(header);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream,"utf-8"));
		for (String str : source) {
			writer.write(str);
			writer.write("\n");
		}
		fileOutputStream.close();
		writer.close();
	}
	
	public static String getRootPath(){
		return System.getProperty("user.dir") + File.separator;
	}
	
	/**
	 * 从文件中读取信息（一行行的读取）
	 * @param  路径地址
	 * @return  
	 */
	public static List<String> readStringFromFile(String filepath) throws IOException, FileNotFoundException {
		List<String> lines = new ArrayList<String>();
		String line;
		File file = new File(filepath);
		if (!file.exists()) {
			return null;
		} else {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); // 创建字符缓冲输入流
			
			while ((line = bufferedReader.readLine()) != null) {
				if(line.startsWith("//")) continue;
				if(line.length() == 0) continue;
				lines.add(line);
			}
			bufferedReader.close();
		}
		return lines;
	}
}
