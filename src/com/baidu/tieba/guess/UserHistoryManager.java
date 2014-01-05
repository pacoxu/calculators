package com.baidu.tieba.guess;

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
import java.util.HashMap;
import java.util.Map;

public class UserHistoryManager {

	public static final String MARK_HISTORY_USER_HISTORY_TXT = "markHistory/userHistory.txt";
	public static final String MARK_HISTORY_USER_HISTORY_TXT1 = "markHistory/userHistory1.txt";

	
	public static Map<String, Integer> userHistory =new HashMap<String, Integer>();
	

	@SuppressWarnings("resource")
	public static Map<String, Integer> loadAllUserForm(String userListFilePath) throws IOException{
		if(userListFilePath == null || userListFilePath.trim().length() == 0){
			userListFilePath = MARK_HISTORY_USER_HISTORY_TXT;
		}
		File file = new File(userListFilePath);
		BufferedReader  fr = new BufferedReader (new InputStreamReader(new FileInputStream(file)));
		for(String line = fr.readLine(); line != null; line = fr.readLine() ){
			int blank = line.indexOf(" ");
			if(blank > 1)
				userHistory.put(line.substring( 0 , blank).trim(), Integer.parseInt(line.substring(blank).trim()));
		}
		return userHistory;
	}
	
	public static String saveUserMarks(Map<String, Integer> userHistoryMap, String filePath) throws IOException{
		
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(new File(filePath)), "UTF-8");
		BufferedWriter bw = new BufferedWriter(new BufferedWriter(outputStreamWriter));		

		for(String userName: userHistoryMap.keySet()){
			int mark = userHistoryMap.get(userName);
			String line = userName  + "    " + mark;
			bw.append(line+"\n");
		}
		bw.close();
		return new File(filePath).getAbsolutePath();
	}
	
}
