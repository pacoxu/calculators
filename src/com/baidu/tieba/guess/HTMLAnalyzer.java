package com.baidu.tieba.guess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HTMLAnalyzer {
	
	private static void detectAndAdd(Map<String, String> guessList, String guessStr, String guessor) {
		if(guessor != null && guessor.length() > 0){
			guessList.put(guessor, guessStr);			
		}
	}
	
	public static Map<String, String> urlAnalyzer(String url) throws Exception{
		Map<String, String> guessList = new HashMap<String, String>();
//		String html = getHTML(url, "gbk");
//		int body = html.indexOf("<body>");
//		String body1 =  html.substring(body);
		
		Parser parser = new Parser (url);
		NodeList list = parser.parse (null);
		for(Node node: list.toNodeArray()){
			String text = node.getText();
			if("html".equals(text)){
				NodeList html = node.getChildren();
				for(Node tags: html.toNodeArray()){
					String bodyType = tags.getText();
					if("body".equals(bodyType)){
						NodeList tagList = tags.getChildren();
						
						Node div = tagList.toNodeArray()[1];
						Node div2 = div.getFirstChild();
						Node div3 = div2.getChildren().toNodeArray()[5];
						Node div4 = div3.getChildren().toNodeArray()[3];
						Node div5 = div4.getFirstChild();
						Node div6 = div5.getChildren().toNodeArray()[1];
						//div 6 is comments's father 
						for(Node comment: div6.getChildren().toNodeArray()){
							String guessStr = "";
							String guessor = "";
							try{
								Node c1= comment.getChildren().toNodeArray()[3];
								Node c2 = c1.getFirstChild();
								//c3 
								Node c3 = c2.getChildren().toNodeArray()[1];
								for(Node c4: c3.getChildren().toNodeArray()){
									if( c4.getText() != null && c4.getText().trim().length() > 1 && !"br".equals(c4.getText())  ){
										guessStr = guessStr + c4.getText() + "/n";										
									}
								}
								Node author= comment.getChildren().toNodeArray()[2];
								Node author1 = author.getChildren().toNodeArray()[0];
								Node author2 = author1.getChildren().toNodeArray()[2];
								Node author3 = author2.getChildren().toNodeArray()[1];
								guessor = author3.getFirstChild().getText();
							}catch(Exception e){
								
							}
							detectAndAdd(guessList, guessStr, guessor);
						}
						
//						Node div7 = div6.getFirstChild();
//						Node firstFloor= div7.getChildren().toNodeArray()[3];
//						Node falltags = firstFloor.getFirstChild();
//						Node floor = falltags.getChildren().toNodeArray()[1];
//						
//						
//						System.out.println(floor);
					}
				}
			}
		}
//		NodeList sublist = node.getChildren ();
//		System.out.println (sublist.size ());
		 
		return guessList;
	}



	public static String getHTML(String pageURL, String encoding) { 
		 StringBuilder pageHTML = new StringBuilder(); 
		 try { 
			 URL url = new URL(pageURL); 
			 HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			 connection.setRequestProperty("User-Agent", "MSIE 7.0"); 
			 BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding)); 
			 String line = null; 
			 while ((line = br.readLine()) != null) { 
				 pageHTML.append(line); 
				 pageHTML.append("\r\n"); 
			 } 
			 connection.disconnect(); 
		 } catch (Exception e) { 
			 e.printStackTrace(); 
		 } 
		 return pageHTML.toString(); 
	 } 
	 
	/*
	 * Result: 	
	 * 瓦伦西亚-马竞：1-2

		塞尔塔-瓦伦西亚： 0-2

		马竞-瓦伦西亚：2-0

		马拉加- 瓦伦西亚： 0-3

		瓦伦西亚- 西班牙人：2-1
	 */
	
}
