package com.ipubu.cic.chat.segment.impl;

import java.util.ArrayList;
import java.util.List;

import com.ipubu.cic.chat.segment.IWordSegTerm;
import com.ipubu.cic.chat.segment.SegmentDomain;

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
    	isUserDefineWord =otherWord.isUserDefineWord(); 
		isHideWord = otherWord.isHideWord();
    }
    
    
    
    
    
    
    public WordSegTermImpl(String word,int pos,String domain){
		this.word = word;
		this.pose = pos;
		this.domain =domain;
	}
	
	public WordSegTermImpl(String word,String nerWordClass,String domain){
		this.word = word;
		this.domain =domain;
		this.extWordClass = nerWordClass;
	}
	
	/**
	 * 获取词的词类路由信息
	 * wc1#wc2#wc3;wc4#wc5#wc6
	 * @return
	 */
	public List<String> getWordClassRoutes() {
		if (wordClassRoutes == null) {
			wordClassRoutes = SegmentDomain.getWordClassRoutes(word, domain);
			
			if (extWordClass != null) {
				if (wordClassRoutes == null) {
					wordClassRoutes = new ArrayList<String>();
				}
				
				wordClassRoutes.add(extWordClass);
			}
		}
		return wordClassRoutes;
	}
	
	
	/**
	 * 获取词的词类信息:wc1;wc2;wc3
	 * @return
	 */
	public List<String> getWordClasses() {
		if (wordClasses == null) {
			getWordClasses();
			
			if (wordClassRoutes != null && wordClassRoutes.size() > 0) {
				String[] wcstrs = null;
				wordClasses = new ArrayList<String>();
				
				for (String s : wordClassRoutes) {
					wcstrs = s.split("#");
					if (wcstrs == null || wcstrs.length == 0) {
						continue;
					}
					
					for (String wordClass : wcstrs) {
						wordClasses.add(wordClass);
					}
				}
			}
		}
		
		return wordClasses;
	}
	
	
	
	
    
	public String getExtWordClass() {
		return extWordClass;
	}
	
	/**
	 * 给词赋值一个扩展词类信息
	 * @param extWordClass
	 */
	public void setExtWordClass(String extWordClass) {
		if(extWordClass == null || extWordClass.length() == 0){
			return;
		}
		
		this.extWordClass = extWordClass;
		
		/** 要先保证原有的词类加载信息 */
		getWordClassRoutes();
		getWordClasses();
		
		/** 接下来判断是否需要追加新的词类信息 */
		if (wordClassRoutes.contains(wordClasses)) {
			wordClassRoutes.addAll(wordClasses);
		}
		
		if (!wordClasses.contains(extWordClass)) {
			wordClasses.add(extWordClass);
		}
	}
	
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setWordClassRoutes(List<String> wordClassRoutes) {
		this.wordClassRoutes = wordClassRoutes;
	}

	public void setWordClasses(List<String> wordClasses) {
		this.wordClasses = wordClasses;
	}

	/**
	 * 是否是用户自定义的词,在es或者其他地方定义(ZNJJ领域)
	 * @return
	 */
	public boolean isUserDefineWord() {
		return isUserDefineWord;
	}

	public void setUserDefineWord(boolean isUserDefineWord) {
		this.isUserDefineWord = isUserDefineWord;
	}

	/**
	 * 是否为隐含词，即原始语句中没有出现的词（ZNJJ领域）
	 * @return
	 */
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
