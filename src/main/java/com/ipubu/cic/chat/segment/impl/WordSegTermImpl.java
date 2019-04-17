package com.ipubu.cic.chat.segment.impl;

import java.util.List;

import com.ipubu.cic.chat.segment.IWordSegTerm;

/**
 * @ClassName WordSegTermImpl
 * @Description
 * @Author jzy
 */
public class WordSegTermImpl implements IWordSegTerm{
	
	private String word = null;
	private String extWordClass = null; // 以SegmentNER分词得到的词类
	private int pose = -1;
	private String domain = "system";
	private List<String> wordClassRoutes = null; // 以词典分词得到的词类路由信息:wc1#wc2#wc3
	private List<String> wordClasses = null; // 以词典分词得到的词类信息:wc1;wc2;wc3
    private boolean isUserDefineWord = false; // 是否是用户自定义的词，在ES或者其他地方定义(znja领域)
    private boolean isHideWord = false; // 是否是隐含词，即原始语句中没有出现的词(znja领域)
	
	public String getWord() {
		return word;
	}
	
	public int getPose() {
		return pose;
	}
	
}
