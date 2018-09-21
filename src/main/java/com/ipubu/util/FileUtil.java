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
	
	/**
	 * 读取目标文件中的第一行
	 * @param 地址路径
	 * @return 
	 */
	public static String readFirstFromFile(String filepath) throws IOException {
		String line;
		File f = new File(filepath);
		if (!f.exists()) {
			return null;
		} else {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8")); // 创建字符缓冲输入流
			while ((line = bufferedReader.readLine()) != null) {
				if(line.startsWith("//")) continue;
				if(line.length()==0) continue;
				break;
			}
			bufferedReader.close();
			return line;
		}
	}
	
	public static void removeDupLines(String filepath) throws IOException {
		Map<String,Integer> map = readFile2Map(filepath);
		writeMap(map,filepath);
	}
	
	/**
	 * 将文件中的资源存储在Map对象中 
	 * @param  一个文件地址的字符串
	 * @return 返回一个保存了文件资源的Map对象
	 */
	public static Map<String,Integer> readFile2Map(String filepath) throws IOException {
		Map<String,Integer> lines= Collections.synchronizedMap(new TreeMap<String,Integer>());
		String line;
		File f = new File(filepath);
		if (!f.exists()) {
			return null;
		} else {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
			//TreeMap<String,String> map = new TreeMap<String,String>();
			while ((line = br.readLine()) != null) {
				if(line.startsWith("//")) continue;
				line = line.toLowerCase().trim();
				if(line.length()==0) continue;	
				String[] secs = line.split("\t");
				if(secs.length>1&&secs[1].matches("[0-9]+")) lines.put(secs[0], new Integer(secs[1]));
				// string的matches()方法用来检测字符串是否匹配给定的正则表达式，方法返回一个boolean 值
				
				/**
				 * 1、int与Integer进行比较的时候，Integer会进行自动拆箱，转为int值再与int进行比较
				 * 2、Integer与Integer进行比较的时候，由于直接进行复制的时候会进行自动的装箱，那么这里就需要注意两个问题，一个是-128<= x<=127的整数，将会直接缓存在IntegerCache中
				 * 	 那么当复制在这个区间的时候，不会创建新的Integer对象。二：当大于这个范围的时候，直接new Integer来创建Integer对象
				 * 3、new Integer(1)与Integer a = 1不同，前者会创建对象存储在堆内存中，而后者因为在-128到127的范围内，所以不会创建新的对象，而是从IntegerCache中获取的
				 *   那么Integer a = 128, 大于该范围的话才会直接通过new Integer（128）创建对象，进行装箱。
				 */
				
				else lines.put(line,null);
			}
			br.close();
			return lines;
		}
	}
	
	/**
	 * 将Map对象中的Key写入到文件中
	 * @param 
	 * @return
	 */
	public static void writeMap(Map<String, Integer> keymap,String outfile){
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile),"utf-8"));
			String line = "";
			Set<String> key = keymap.keySet();
	        
	        for (Iterator<String> it = key.iterator(); it.hasNext();) {         
	            String keystr = (String) it.next();
	            writer.write(keystr+"\n");
	        }
			writer.close();
		} catch (IOException e) {
			Log.logger.error("Exception",e);
		}
	}
}
