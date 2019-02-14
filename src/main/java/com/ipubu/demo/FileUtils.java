package com.ipubu.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
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

	public static void main(String[] args) {
		String musicPathOriginal = "D:\\workspace3\\Lip_词典_2.10\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
		String musicPath = "D:\\workspace3\\liuhong\\data_multi_1.25\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
		duplicateRemovalMusic(musicPathOriginal,musicPath);
		
	}
	
	/**
	 * @param 
	 * @return
	 */
	private static void duplicateRemovalMusic(String musicPathOriginal,
			String musicPath) {
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
			} else {

			}
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
