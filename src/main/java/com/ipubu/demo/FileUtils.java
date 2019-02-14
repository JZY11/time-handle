package com.ipubu.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @ClassName FileUtils
 * @Description
 * @Author jzy
 */
public class FileUtils {
	public static String path = "D:\\liuhong\\儿歌(歌曲、歌手、专辑).txt";
	
	public static List<String> list = new ArrayList<String>();
	
	public static Set<String> sets = new HashSet<String>();

	public static void main(String[] args) throws IOException {
		String musicPathOriginal = "D:\\workspace3\\Lip_词典_2.10\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
		String musicPath = "D:\\workspace3\\liuhong\\data_multi_1.25\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
		duplicateRemovalMusic(musicPathOriginal,musicPath);
		
	}
	
	/**
	 * @param 
	 * @return
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	private static void duplicateRemovalMusic(String musicPathOriginal,
			String musicPath) throws IOException {
		// TODO Auto-generated method stub
		File musicFile = new File(musicPathOriginal);
		
		int count = 0;
		String line;
		
		File[] fileNames = musicFile.listFiles();
		for (File file : fileNames) {
			System.out.println(file.getPath());
			String name = file.getName() + "\\";
			musicPath += name;
			musicPathOriginal += name;
			
			if (file.isDirectory()) {
				File fileMusic = new File(musicPath);
				fileMusic.mkdir();
				duplicateRemovalMusic(musicPathOriginal,musicPath);	// 递归调用
			} else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
				File fileOfMusic = new File(musicPath);
				if (!fileOfMusic.exists()) {	// 判断如果该文件不存在的话
					fileOfMusic.createNewFile();	// 新生成该文件对象
				}
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOfMusic), "utf-8"));
				
				for (Iterator<String> iterator = sets.iterator(); iterator.hasNext();){
					String next = iterator.next();
					while ((line = reader.readLine()) != null) {
						if (line.equals(next)) {
							count ++;
						} else {
							writer.write(line + "\r\n");
						}
					}
				}
				
				reader.close();
				writer.close();
			}
			
			System.out.println("相同的词条共：" + count);
		}
	}

	static{
		try {
			init(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Set<String> init(String path) throws IOException{
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
		String line;
		while((line = reader.readLine()) != null){
			if (line != null && line.length() != 0) {
				sets.add(line);
			}
		}
		reader.close();
		return sets;
	}
}
