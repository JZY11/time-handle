package com.ipubu.grammar.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import com.ipubu.util.Log;

/**
 * @ClassName Constants
 * @Description
 * @Author jzy
 */
public class Constants {

	public static final String DIALOGSYSTEM_VERSION = "Lip version 1.00";
	public static final int MAX_PRE_SERVICE = 4;
	public static String alias_file = "alias.txt";
	public static String dataDir = "data/";
	public static String wcdir = "wctxt";
	public static String binarywcdir = "wc";
	public static String grammarDir = "grammars";
	public static String serviceDir = "service/";
	public static String treeDir = "tree/";
	public static String chatDir ="little-chat.data.txt";
	public static int MAXRANDOMLEN = 6;
	
	public static String TYPE_QUIT = "退出";
	public static String TYPE_RANDOM = "随便";
	public static String EMPTY_PARA = "[]";
	public static int BASIC_LIVE_SECONDS = 11;
	public static int MAXPARAASK = 3;
	public static String EXTREMESTR = "MAX MIN AVG";
	public static boolean MULTIPLE_ENTITY_SUPPORT = true;
	
	public static String FULL_REPLY_PRE = "您还可以继续问";
	public static String FULL_REPLY_POST = "，或者聊些别的！";
	public static String NOT_MATCH_CONTEXT = "不好意思没听懂，您能说详细些吗？";
	public static String NOT_MATCH = "我们可以聊聊天气听听音乐！";
	public static BufferedWriter bw = null;
	
	public static void setDataDir(String dir){
		dataDir = dir;
		wcdir = dataDir+wcdir;
		binarywcdir = dataDir+binarywcdir;
		grammarDir = dataDir+grammarDir;
		serviceDir = dataDir+serviceDir;
		treeDir = dataDir+treeDir;
		alias_file = dataDir+alias_file;
		chatDir = dataDir + chatDir;
//		PosUtils.init(dir+"config/");
//		PropertyUtil.init(dir+"config/");
	}
	
}
