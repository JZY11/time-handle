package com.ipubu.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.core.sym.Name;

/**
 * @ClassName PullStockNameAndId
 * @Description
 * @Author jzy
 */
public class PullStockNameAndId {
	
	public static void main(String[] args) {
		String path = "D:\\JZY\\WrongCase\\股票名称代码.txt";
		String pathName = "D:\\JZY\\WrongCase\\股票名称.txt";
		String pathCode = "D:\\JZY\\WrongCase\\股票代码.txt";
		
		File file = new File(path);
		String line;
		String lineStr;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			BufferedWriter writerN = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathName)));
			BufferedWriter writerC = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathCode)));
			Set<String> stockN = new HashSet<String>();
			Set<String> stockC = new HashSet<String>();
			
			while ((line = reader.readLine()) != null) {
				lineStr = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
				System.out.println(lineStr);
				String[] splits = lineStr.split("],");
				System.out.println(splits.length);
				for (String string : splits) {
					String[] idAndCode = string.split(",");
					for (int i = 0; i < idAndCode.length; i++) {
						if (i > 0) {
							stockN.add(idAndCode[i].replace("\"", "").trim());
//							writerN.write(idAndCode[i].replace("\"", "").trim() + "\r\n");
						}else {
							stockC.add(idAndCode[i].replace("\"", "").replace("[", "").trim());
//							writerC.write(idAndCode[i].replace("\"", "").replace("[", "").trim() + "\r\n");
							
						}
					}
				}
				Iterator<String> iterator = stockN.iterator();
				while (iterator.hasNext()) {
					writerN.write(iterator.next() + "\r\n");
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
