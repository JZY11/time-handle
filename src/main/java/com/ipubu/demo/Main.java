package com.ipubu.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @ClassName Main
 * @Description		音乐领域 和 电台领域 中，与 儿歌领域 重名的词条，将全部删除，只保留 儿歌领域 的相应词条
 * @Author jzy
 */
public class Main {

	public static String path = "D:\\liuhong\\儿歌(歌曲、歌手、专辑).txt";
	
	
	public static List<String> list = new ArrayList<String>();
	
	public static Set<String> sets = new HashSet<String>();
	
	static int begin = 0;
	static String original = "歌曲数据\\";
//	static int count = 0;
	
	public static void main(String[] args) throws IOException {
		String musicPath = "D:\\liuhong\\data_multi_1.25\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
		String radioPath = "D:\\liuhong\\data_multi_1.25\\wctxt\\领域词汇\\电台\\";
		String musicPathOriginal = "D:\\liuhong\\0917\\data_multi_1.25cs\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
		String radioPathOriginal = "D:\\liuhong\\0917\\data_multi_1.25cs\\wctxt\\领域词汇\\电台\\";
		duplicateRemovalMusic(musicPathOriginal,musicPath);
//		getPath(musicPathOriginal,list);
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(musicPath),"UTF-8"));
		
		String line;
		while ((line = reader.readLine()) != null) {
			if (line != null && line != "") {
				sets.add(line);
			}
		}
		reader.close();
		
		return sets;
	}
	
	/*
	 * 音乐领域中若有与儿歌领域重名的词条的话则删除
	 */
	public static void duplicateRemovalMusic(String musicPathOriginal,String musicPath) throws IOException{
		
		
		/*File musicFile = new File(musicPathOriginal);
		File file = new File(musicPath);
		
//		if (!musicFile.exists()) {
//			musicFile.createNewFile();
//		}
//		if (!file.exists()) {
//			file.createNewFile();
//		}
		
		
		String line;
		File[] fileNames = musicFile.listFiles();
		for (File fileName : fileNames) {
			
			String name = fileName.getName() + "\\";
//			original = name;
			begin = begin + original.length();
			fileName.getPath().length();
			String substring = fileName.getPath().substring(fileName.getPath().indexOf(original) + begin) + "\\";
			musicPath += substring;
			musicPathOriginal += substring;
			
			
			
			File fileOfMusic = new File(musicPath);
			File ff = new File(musicPathOriginal);
			String path2 = ff.getPath() + "//";
			if (ff.isDirectory()) {
//				File fileOfMusic = new File(musicPath);
				fileOfMusic.mkdir();	// 自己定义的路径中新建相应的目录
				
				duplicateRemovalMusic(ff.getPath() + "//");
			}
			original += name;
			begin = 0;
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOfMusic  + "//"),"UTF-8"));
			
			if (!fileOfMusic.exists()) {
				fileOfMusic.createNewFile();
			}
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ff  + "//"),"UTF-8"));
			
			
			for (Iterator<String> iterator = sets.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				while ((line = reader.readLine()) != null) {
					if (string.equals(line)) {
						count ++;
					}else {
						writer.write(line + "\r\n");

					}
				}
			}
			original = "歌曲数据\\";
			musicPath = "D:\\liuhong\\data_multi_1.25\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
			musicPathOriginal = "D:\\liuhong\\0917\\data_multi_1.25cs\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
			reader.close();
			writer.close();
		}
		System.out.println("相同的词条共：" + count + "条");*/
	
//		getPath(musicPathOriginal,list);
		
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
//				musicPath = "D:\\liuhong\\data_multi_1.25\\wctxt\\领域词汇\\音乐\\歌曲名词\\歌曲数据\\";
				duplicateRemovalMusic(musicPathOriginal,musicPath);
			}else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
//				musicPath += name;
				File fileOfMusic = new File(musicPath);
				if (!fileOfMusic.exists()) {
					fileOfMusic.createNewFile();
				}
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOfMusic),"UTF-8"));
				for (Iterator<String> iterator = sets.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					while ((line = reader.readLine()) != null) {
						if (string.equals(line)) {
							count ++;
						}else {
							writer.write(line + "\r\n");

						}
					}
				}
				reader.close();
				writer.close();
			}
			System.out.println("相同的词条共：" + count + "条");
		}
		
		
		
		
	}
	
	public static List<String> getPath(String dir,List<String> list) {
		  File file=new File(dir);
			File[] filelist= file.listFiles();
			for(int i=0;i<filelist.length;i++){
				if(filelist[i].isFile()){
					System.out.println(filelist[i].getPath());
				list.add(dir+System.getProperty(file.separator)+filelist[i].getName());
				}else if(filelist[i].isDirectory()){
					System.out.println(filelist[i].getPath());
					getPath(filelist[i].getPath(),list);
					continue;
				}
			}
			return list;
	  }
}
