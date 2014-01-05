package com.baidu.tieba.guess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Calculator {

	/**
	 * @param args
	 * args[0] url
	 * args[1-9] result
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		MatchScore ms = new MatchScore("瓦伦西亚-马竞：3-1");
		
		
		// the result
		// the url
		// Guess List Users
		// Ranking List
//		Map<String, Integer> userHisotry = UserHistoryManager.loadAllUserForm(UserHistoryManager.MARK_HISTORY_USER_HISTORY_TXT);

		Map<String, String> html = HTMLAnalyzer.urlAnalyzer("http://tieba.baidu.com/p/2798082175");
		Map<String, Integer> m = UserHistoryManager.loadAllUserForm(UserHistoryManager.MARK_HISTORY_USER_HISTORY_TXT);
		String result = "瓦伦西亚-马竞：0-0" + "/n" +
				"塞尔塔-瓦伦西亚： 0-0" + "/n" +
				"马竞-瓦伦西亚：0-0" + "/n" +
				"马拉加- 瓦伦西亚： 0-0" + "/n" +
				"瓦伦西亚- 西班牙人：0-0";
		
		calculatorAndAdd(m, html, result);
		
		UserHistoryManager.saveUserMarks(m, UserHistoryManager.MARK_HISTORY_USER_HISTORY_TXT1);
		System.out.print(html);
		
		
	}

	
	private static void calculatorAndAdd(Map<String, Integer> userList,
			Map<String, String> guessList, String result) throws IOException {
		
		String[] matchs = result.split("/n");
		List<MatchScore> mss = new ArrayList<MatchScore>();
		for( String match: matchs){
			mss.add( new MatchScore(match) );
		}
		
		for(String guessor: guessList.keySet() ){
			String guessStr = guessList.get(guessor);
			int guessMark = 0;
			try{
				guessMark = userList.get(guessor);
			}catch (Exception e) {
				System.out.println(guessor + " is not registered in Sign-up Page");
			}
			
			String[] guessMatchs = guessStr.split("/n");
			for( String match: guessMatchs){
				userList.put(guessor, guessMark + getMatchMarkForOne( match, mss));
			}
			
		}
		
		
	}


	private static int getMatchMarkForOne(String match, List<MatchScore> mss) {
		int m = 0;
		try{
			MatchScore ms = new MatchScore(match);
			for( MatchScore matchResult: mss){
				m =  ms.getMark(matchResult) + m;
			}
		}catch( Exception e){
			
		}
		return m;
	}

}
