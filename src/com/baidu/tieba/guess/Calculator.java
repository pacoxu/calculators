package com.baidu.tieba.guess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Calculator {

	/**
	 * @param args
	 * args[0] url
	 * args[1-9] result
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		System.out.println("load web page...");
		Map<String, String> html = HTMLAnalyzer.urlAnalyzer("http://tieba.baidu.com/p/2798082175");
		Map<String, String> html1 = HTMLAnalyzer.urlAnalyzer("http://tieba.baidu.com/p/2798082175?pn=2");
		html.putAll(html1);
		
		System.out.println("load user list...");
		Map<String, Integer> m = UserHistoryManager.loadAllUserForm(UserHistoryManager.MARK_HISTORY_USER_HISTORY_TXT);
		String result = "瓦伦西亚-马竞：1-1";
//		 + "/n" +
//			"塞尔塔-瓦伦西亚： 2-3" + "/n" +
//			"马竞-瓦伦西亚：2-0" + "/n" +
//			"马拉加- 瓦伦西亚： 1-0" + "/n" +
//			"瓦伦西亚- 西班牙人：1-0"

		System.out.println("The result is "+ result);
		System.out.println("calculate ...");
		calculatorAndAdd(m, html, result);

		
		System.out.println("save user list...");
		
		UserHistoryManager.saveUserMarks(m, UserHistoryManager.MARK_HISTORY_USER_HISTORY_TXT + "2");
		
        ByValueComparator bvc = new ByValueComparator(m);
		System.out.print(" sort ");
		List<String> keys = new ArrayList<String>(m.keySet());
		
		Collections.sort(keys, bvc);

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Sheet 1");
		int i = 0;
		File excelTemp1  = new File( "markHistory/score1.csv");
		FileOutputStream fileOut1 = new FileOutputStream( excelTemp1);
		
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOut1, "UTF-8");
		BufferedWriter bw = new BufferedWriter(new BufferedWriter(outputStreamWriter));		

		
		
		for(String key : keys) {
			System.out.printf("%s -> %d\n", key, m.get(key));
//			finalResult.put( key , String.valueOf( m.get(key) ) );
			Row output = sheet.createRow(i++);
				
			bw.append(key+","+ m.get(key)+"\n");
			Cell keyCell = output.createCell(0);
			keyCell.setCellValue(key);
			Cell markCell = output.createCell(1);
			markCell.setCellValue(m.get(key));
		}
		bw.close();

		File excelTemp  = new File( "markHistory/score1.xls");
		FileOutputStream fileOut = new FileOutputStream( excelTemp);
		wb.write(fileOut);
		fileOut.close();
	    
		System.out.println( excelTemp.getAbsolutePath() );

	}

	
	private static void calculatorAndAdd(Map<String, Integer> userList,
			Map<String, String> guessList, String result) throws IOException {
		
		String[] matchs = result.split("/n");
		List<MatchScore> mss = new ArrayList<MatchScore>();
		for( String match: matchs){
			mss.add( new MatchScore(match) );
		}
		
		for(String allguess: userList.keySet()){
			userList.put(allguess, ((Integer)userList.get(allguess)).intValue()-1);
		}
		
		for(String guessor: guessList.keySet() ){
			
			if( "我爱瓦伦西亚".equals(guessor) ){
				guessor = "清虚7探戈蝙蝠 ";
			}

			
			String guessStr = guessList.get(guessor);
			int guessMark = 0;
			try{
				guessMark = userList.get(guessor);
			}catch (Exception e) {
				System.out.println(guessor + " is not registered in Sign-up Page");
				continue;
			}
			guessMark++;
			
			
			String[] guessMatchs = guessStr.split("/n");
			for( String match: guessMatchs){
				userList.put(guessor, guessMark + getMatchMarkForOne( match, mss));
				guessMark = guessMark + getMatchMarkForOne( match, mss);
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

	static class ByValueComparator implements Comparator<String> {
		Map<String, Integer> base_map;
		 
        public ByValueComparator(Map<String, Integer> base_map) {
            this.base_map = base_map;
        }
 
        public int compare(String arg0, String arg1) {
            if (!base_map.containsKey(arg0) || !base_map.containsKey(arg1)) {
                return 0;
            }
 
            if (base_map.get(arg0) < base_map.get(arg1)) {
                return 1;
            } else if (base_map.get(arg0) == base_map.get(arg1)) {
                return 0;
            } else {
                return -1;
            }
        }
	}
}
