package com.ipubu.cic.chat.segment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.elasticsearch.common.util.DoubleArray;

/**
 * @ClassName SegmentDomain
 * @Description
 * @Author jzy
 */
public class SegmentDomain {

	private static Set<String> systemSupportDomains = new HashSet<String>(); // 本系统支持的所有领域集合
    private static Set<String> dynamicSupportDomains = new HashSet<String>();// 动态新加的领域集合
    private static Map<String, Set<String>> deviceTypeSupportDomains = new HashMap<String, Set<String>>();// 不同deviceType支持的领域集合
    private static Map<String, HashSet<String>> serviceLimitedWClassMap = Collections.synchronizedMap(new TreeMap<String, HashSet<String>>()); // domain service 所有grammar里涉及到的词类集合
    private static Map<String, List<String>> serviceAnswerLimitedWClassMap = Collections.synchronizedMap(new TreeMap<String, List<String>>()); // domain service answer对于本服务所返回的词类的限制
    private static Map<String, TreeMap<String, String>> domainWordWClassMap = Collections.synchronizedMap(new TreeMap<String, TreeMap<String, String>>()); // key:领域;value:kvs
    private static Map<String, DoubleArray> domainForwardDoubleArrayMap = new HashMap<String, DoubleArray>(); // 各领域词典的正序double array
    private static Map<String, DoubleArray> domainBackwardDoubleArrayMap = new HashMap<String, DoubleArray>();// 各领域词典的反序double array

    
    
    
    
    /**
     * 获取某个词所有可能的词类路由信息或者某个领域的词类路由信息
     * @param word
     *             词
     * @param domain
     *             领域(可以为空)
     * @return List wc1#wc2#wc3
     */
    public static List<String> getWordClassRoutes(String word, String domain) {
    	if (domain == null || "".equals(domain)) {
			return getAllWordClassRoutes(word);
		} else if (!systemSupportDomains.contains(domain)) {
			domain = "other";
		}
    	
    	return getAllWordClassRoutesByDomain(word, domain);
    }
    
    
    /**
     * 获取某个词的所有可能的词类路由信息
     * 
     * @param word
     *            词
     * @return List wc1#wc2#wc3
     */
    private static List<String> getAllWordClassRoutes(String word) {
    	if (word == null || "".equals(word)) {
			return null;
		}
    	
    	List<String> wordClassRoutesList = new ArrayList<String>();
        List<String> wClassList = null;
        
        for (String domain : serviceLimitedWClassMap.keySet()) {
        	wClassList = getAllWordClassRoutesByDomain(word, domain);
        	if (wClassList != null) {
				for (String wClassRoute : wClassList) {
					if (!wordClassRoutesList.contains(wClassRoute)) {
						wordClassRoutesList.add(wClassRoute);
					}
				}
			}
        }
        
        return wordClassRoutesList;
    }
    
    
    /**
     * 获取某个词在某一领域的词类路由信息
     * 
     * @param word
     *            词
     * @param domain
     *            领域
     * @return List wc1#wc2#wc3
     */
    private static List<String> getAllWordClassRoutesByDomain(String word, String domain) {
    	if (word == null || word.length() == 0 || domain == null || domain.length() == 0) {
			return null;
		}
    	
    	TreeMap<String, String> wordskv = domainWordWClassMap.get(domain);
    	if (wordskv == null || wordskv.size() == 0) {
			return null;
		}
    	String wcstring = wordskv.get(word);
    	if (wcstring == null || wcstring.length() == 0) {
			return null;
		}
    	
    	String[] wcs = wcstring.split("##");
    	if (wcs == null || wcs.length == 0) {
			return null;
		}
    	
    	List<String> wordClassRoutesList = new ArrayList<String>();
    	for (String wc : wcs) {
    		wc = wc.trim();
    		if (wc.length() == 0) {
				continue;
			}
    		wordClassRoutesList.add(wc);
    	}
    	
    	return wordClassRoutesList;
    }
    
    
    
}
