package com.baidu.tieba.guess;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchScore {
//	/**
//	 * @param args
//	 * @throws IOException 
//	 */
//	public static void main(String[] args) throws IOException {
//		MatchScore ms = new MatchScore("瓦伦西亚-马竞：0-0");
//		MatchScore ms1 = new MatchScore("瓦伦1-1马竞");
//		MatchScore ms2 = new MatchScore("马竞:瓦伦西亚: 2-0");
//		
//		System.out.println("");
//	}



//瓦伦西亚-马竞：0-0
//	瓦伦1-1马竞
//	马竞:瓦伦西亚: 2-0
	MatchScore(String matchStr){
		matchStr = matchStr.replaceAll(" ", "");
		if( matchStr.contains("-")  && matchStr.replaceFirst("-", "+").contains("-")   && (matchStr.contains(":")|| matchStr.contains("："))){
			//The first easy mode
			int a1 = matchStr.indexOf('-');
			int a2 = matchStr.indexOf(':');
			if(a2<1){
				a2 = matchStr.indexOf("：");
			}
			
			int a3 = matchStr.substring(a2).indexOf('-') + a2;
			homeTeam = matchStr.substring(0,a1).trim();
			awayTeam = matchStr.substring(a1+1,a2).trim();
			homeScore = Integer.parseInt( matchStr.substring(a2+1,a3).trim() );
			awayScore = Integer.parseInt( matchStr.substring(a3+1).trim() );
		}else{

			if( matchStr.contains("-")  && ( matchStr.replaceFirst(":", "+").contains(":") || matchStr.replaceFirst("：", "+").contains("：")) 
					&& (matchStr.contains(":")|| matchStr.contains("："))){
				//The first easy mode
				int a1 = matchStr.indexOf(':');
				if(a1 < 1){
					a1 = matchStr.indexOf("：");
				}
				int a2 = matchStr.substring(a1+1).indexOf(':') + a1 +1;
				if(a2 < 1){
					a2 =  matchStr.substring(a1+1).indexOf("：") + a1 +1;
				}
				
				int a3 = matchStr.substring(a2).indexOf('-') + a2;
				homeTeam = matchStr.substring(0,a1).trim();
				awayTeam = matchStr.substring(a1+1,a2).trim();
				homeScore = Integer.parseInt( matchStr.substring(a2+1,a3).trim() );
				awayScore = Integer.parseInt( matchStr.substring(a3+1).trim() );
			}else{
				//瓦伦1-1马竞
				int vs = matchStr.indexOf('-');
				String begin = matchStr.substring(0, vs);
				String end = matchStr.substring( vs );
				homeTeam = begin.substring(0, begin.length() - 1 ).trim();
				awayTeam = end.substring(2).trim();
				homeScore = Integer.parseInt( begin.substring(begin.length()-1));
				awayScore = Integer.parseInt( end.substring(1,2));
				
			}
		}		
	}
	

	String homeTeam;
	String awayTeam;
	int homeScore;
	int awayScore;

	
	public String getHomeTeam() {
		if( homeTeam.equals("瓦伦")){
			return "瓦伦西亚";
		}
		if( homeTeam.equals("瓦伦")){
			return "瓦伦西亚";
		}
		if( homeTeam.equals("瓦伦")){
			return "瓦伦西亚";
		}
		return homeTeam;
	}



	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}



	public String getAwayTeam() {
		if( awayTeam.equals("瓦伦")){
			return "瓦伦西亚";
		}
		if( awayTeam.equals("瓦伦")){
			return "瓦伦西亚";
		}
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
		if(ms.getHomeTeam().equals(homeTeam) && ms.getAwayTeam().equals(awayTeam)){
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
