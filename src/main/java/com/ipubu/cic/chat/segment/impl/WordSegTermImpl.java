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
	
    /**
     * 升级某个词的分类信息
     * @param otherWord
     */
    public void update(WordSegTermImpl otherWord) {
    	if (otherWord == null || otherWord.getWord() == null || !otherWord.getWord().equalsIgnoreCase(word)) {
			return;
		}
    	
    	setExtWordClass(otherWord.getExtWordClass());
    }
    
    
    
    
    
    
    
    
	public String getExtWordClass() {
		return extWordClass;
	}
	
	public void setExtWordClass(String extWordClass) {
		this.extWordClass = extWordClass;
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<String> getWordClassRoutes() {
		return wordClassRoutes;
	}

	public void setWordClassRoutes(List<String> wordClassRoutes) {
		this.wordClassRoutes = wordClassRoutes;
	}

	public List<String> getWordClasses() {
		return wordClasses;
	}

	public void setWordClasses(List<String> wordClasses) {
		this.wordClasses = wordClasses;
	}

	public boolean isUserDefineWord() {
		return isUserDefineWord;
	}

	public void setUserDefineWord(boolean isUserDefineWord) {
		this.isUserDefineWord = isUserDefineWord;
	}

	public boolean isHideWord() {
		return isHideWord;
	}

	public void setHideWord(boolean isHideWord) {
		this.isHideWord = isHideWord;
	}


	public String getWord() {
		return word;
	}
	
	public int getPose() {
		return pose;
	}
	
}
