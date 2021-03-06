package com.baidu.tieba.guess;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchScore {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MatchScore ms = new MatchScore("瓦伦西亚-马竞：0-0");
		MatchScore ms1 = new MatchScore("瓦伦1-1马竞");
		MatchScore ms2 = new MatchScore("马竞:瓦伦西亚: 2-0");
		
		System.out.println("");
    	{
    		Pattern pat = Pattern.compile("([\u4e00-\u9fa5]{2,6})([0-9]{1,4})-([0-9]{1,4})([\u4e00-\u9fa5]{2,6})");
        	Matcher matcher =pat.matcher("瓦伦1-1马竞");
        	System.out.print(matcher.find());
    	}
    	{
    		Pattern pat = Pattern.compile("([\u4e00-\u9fa5]{2,6})[-:：]([\u4e00-\u9fa5]{2,6})[-:：]([0-9]{1,4})[-:：]([0-9]{1,4})");
        	Matcher matcher =pat.matcher("瓦伦西亚-马竞：0-0");
        	System.out.print(matcher.find());
    	}
    	{
    		Pattern pat = Pattern.compile("([\u4e00-\u9fa5]{2,6})[-:：]([\u4e00-\u9fa5]{2,6})[-:：]([0-9]{1,4})[-:：]([0-9]{1,4})");
        	Matcher matcher =pat.matcher("马竞:瓦伦西亚:2-0");
        	System.out.print(matcher.find());
        	pat = Pattern.compile("[-:：]");
        	matcher = pat.matcher("马竞:瓦伦西亚:2-0");

        	System.out.println(matcher.find());
        	System.out.println(matcher.start());
        	System.out.println(matcher.find());
        	System.out.println(matcher.start());
        	System.out.println(matcher.find());
        	System.out.println(matcher.start());
        	System.out.println(matcher.find());
        	System.out.println(matcher.toMatchResult());
    	}
    		
	}



//瓦伦西亚-马竞：0-0
//	瓦伦1-1马竞
//	马竞:瓦伦西亚: 2-0
	MatchScore(String matchStr){
		matchStr = matchStr.replaceAll(" ", "");

		Pattern pat = Pattern.compile("([\u4e00-\u9fa5]{2,6})[-:：]([\u4e00-\u9fa5]{2,6})[-:：]([0-9]{1,4})[-:：]([0-9]{1,4})");
    	Matcher matcher = pat.matcher( matchStr );
    	if( matcher.find() ){
        	pat = Pattern.compile("[-:：]");
        	matcher = pat.matcher(matchStr);
        	if( matcher.find() ){
        		int a1 = matcher.start();
            	if( matcher.find() ){
            		int a2 = matcher.start();
                	if( matcher.find() ){
                		int a3 = matcher.start();

            				homeScore = Integer.parseInt( matchStr.substring(a2+1,a3).trim() );
                			awayScore = Integer.parseInt( matchStr.substring(a3+1).trim() );

                			homeTeam = matchStr.substring(0,a1).trim();
                			awayTeam = matchStr.substring(a1+1,a2).trim();
               				
               			}
                	}
            	}
        	}else {
        		Pattern pattern1 = Pattern.compile("([\u4e00-\u9fa5]{2,6})([0-9]{1,4})[-:：]([0-9]{1,4})([\u4e00-\u9fa5]{2,6})");
            	Matcher matcher1 =pattern1.matcher("瓦伦1-1马竞");
            	if(matcher1.find()){
            		pattern1 = Pattern.compile("[0-9]{1,4}");
                	matcher1 =pattern1.matcher(matchStr);
                	if( matcher1.find() ){
                    	int a4 = matcher1.start();
                    	int a5 = matcher1.end();
                    	homeScore = Integer.parseInt( matchStr.substring(a4, a5) );
                	}
                	if( matcher1.find() ){
                    	int a4 = matcher1.start();
                    	int a5 = matcher1.end();
                    	awayScore = Integer.parseInt( matchStr.substring(a4, a5) );
                	}
                	pattern1 = Pattern.compile("[\u4e00-\u9fa5]{2,6}");
                	matcher1 =pattern1.matcher(matchStr);
                	if( matcher1.find() ){
                    	int a4 = matcher1.start();
                    	int a5 = matcher1.end();
                    	homeTeam = matchStr.substring(a4, a5) ;
                	}
                	if( matcher1.find() ){
                    	int a4 = matcher1.start();
                    	int a5 = matcher1.end();
                    	awayTeam =  matchStr.substring(a4, a5) ;
                	}
            		
            	}


        	}
    	}

		
//		if( matchStr.contains("-")  && matchStr.replaceFirst("-", "+").contains("-")   && (matchStr.contains(":")|| matchStr.contains("："))){
//			//The first easy mode
//			int a1 = matchStr.indexOf('-');
//			int a2 = matchStr.indexOf(':');
//			if(a2<1){
//				a2 = matchStr.indexOf("：");
//			}
//			
//			int a3 = matchStr.substring(a2).indexOf('-') + a2;
//			homeTeam = matchStr.substring(0,a1).trim();
//			awayTeam = matchStr.substring(a1+1,a2).trim();
//			homeScore = Integer.parseInt( matchStr.substring(a2+1,a3).trim() );
//			awayScore = Integer.parseInt( matchStr.substring(a3+1).trim() );
//		}else{
//
//			if( matchStr.contains("-")  && ( matchStr.replaceFirst(":", "+").contains(":") || matchStr.replaceFirst("：", "+").contains("：")) 
//					&& (matchStr.contains(":")|| matchStr.contains("："))){
//				//The first easy mode
//				int a1 = matchStr.indexOf(':');
//				if(a1 < 1){
//					a1 = matchStr.indexOf("：");
//				}
//				int a2 = matchStr.substring(a1+1).indexOf(':') + a1 +1;
//				if(a2 < 1){
//					a2 =  matchStr.substring(a1+1).indexOf("：") + a1 +1;
//				}
//				
//				int a3 = matchStr.substring(a2).indexOf('-') + a2;
//				homeTeam = matchStr.substring(0,a1).trim();
//				awayTeam = matchStr.substring(a1+1,a2).trim();
//				homeScore = Integer.parseInt( matchStr.substring(a2+1,a3).trim() );
//				awayScore = Integer.parseInt( matchStr.substring(a3+1).trim() );
//			}else{
//				//瓦伦1-1马竞
//				int vs = matchStr.indexOf('-');
//				if( vs < 1){
//					vs = matchStr.indexOf(':');
//					if( vs < 1){
//						vs = matchStr.indexOf('：');
//					}
//				}
//				String begin = matchStr.substring(0, vs);
//				String end = matchStr.substring( vs );
//				homeTeam = begin.substring(0, begin.length() - 1 ).trim();
//				awayTeam = end.substring(2).trim();
//				homeScore = Integer.parseInt( begin.substring(begin.length()-1));
//				awayScore = Integer.parseInt( end.substring(1,2));
//				
//			}
//		}		

	

	String homeTeam;
	String awayTeam;
	int homeScore;
	int awayScore;

	
	public String getHomeTeam() {
		if( homeTeam.equals("瓦伦")){
			return "瓦伦西亚";
		}
//		if( homeTeam.equals("瓦伦")){
//			return "瓦伦西亚";
//		}
//		if( homeTeam.equals("瓦伦")){
//			return "瓦伦西亚";
//		}
		return homeTeam;
	}



	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}



	public String getAwayTeam() {
//		if( awayTeam.equals("瓦伦")){
//			return "瓦伦西亚";
//		}
//		if( awayTeam.equals("瓦伦")){
//			return "瓦伦西亚";
//		}
		if( awayTeam.equals("瓦伦")){
			return "瓦伦西亚";
		}
		return awayTeam;
	}



	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}



	public int getHomeScore() {
		return homeScore;
	}



	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}



	public int getAwayScore() {
		return awayScore;
	}



	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}

	
	//win 3 draw 1 lose 0
	public int getWinDrawLose(){
		if( homeScore > awayScore){
			return 3;
		}else if( homeScore == awayScore){
			return 1;
		}
		return 0;
	}
	
	public int getMark( MatchScore ms ){
		if(ms.getHomeTeam().equals(this.getHomeTeam()) && ms.getAwayTeam().equals(this.getAwayTeam())){
			if( ms.getHomeScore() == homeScore && ms.getAwayScore() == awayScore ){
				return 3;
			}
			if( ms.getHomeScore() == ms.getAwayScore() && homeScore == awayScore ){
				return 1;
			}
			if( ms.getHomeScore() > ms.getAwayScore() && homeScore > awayScore ){
				return 1;
			}
			if( ms.getHomeScore() < ms.getAwayScore() && homeScore < awayScore ){
				return 1;
			}
		}
		return 0;
	}
	
}
