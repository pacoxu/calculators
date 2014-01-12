package com.baidu.tieba.guess.html;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HTMLParser {
	/*
	 * code example for HTML Parser
	 */
	
	public static Map<String, String> parser(String url) throws Exception{
		Map<String, String> guessorContent = new HashMap<String, String>();
		HTMLParser hp = new HTMLParser(url);
		NodeList floors = hp.getAllFloor();
		for(Node floor: floors.toNodeArray()){
			guessorContent.put(getAuthor(floor), getContent(floor));
			System.out.println(getAuthor(floor));
			System.out.println(getContent(floor));
		}
		return guessorContent;
	}
	
	
	public static String getContent(Node floor){
		String[] paths = {"d_post_content_main", "p_content", "post_content_", "wrap2","container", "left_section", "core", "p_postlist" };
		Node node = getNodeByPath(paths , floor.getChildren());
		StringBuilder content = new StringBuilder();
		for(Node line: node.getChildren().toNodeArray()){
			if(line instanceof TextNode){
				content.append(line.getText()+"\n");
			}
		}
		return content.toString();
	}
	
	public static String getAuthor(Node floor){
		String[] paths = {"d_author", "p_author", "d_name"};
		Node node = getNodeByPath(paths , floor.getChildren());
		for(Node link: node.getChildren().toNodeArray()){
			if(link instanceof LinkTag){
				node = link;
			}
		}
		return node.getFirstChild().getText();
	}

	String url ;
	NodeList root;
	public HTMLParser(String url) throws ParserException{
		this.url = url;
		Parser parser = new Parser (url);
		root = parser.parse (null);
	}
	
	public Node getNode(String[] paths){
		return getNodeByPath(paths, root);
	}

	private static Node getNodeByPath(String[] paths, NodeList targets) {
		Node result = null;
		for(String path: paths){
			for(Node target: targets.toNodeArray()){
				if("html".equals(path)){
					if(target.getText() != null && target.getText().equals(path)){
						result = target;
						targets = result.getChildren();
						break;
					}
				}else{
					if(target.getText() != null && target.getText().contains(path)){
						result = target;
						targets = result.getChildren();
						break;
					}
				}
			}
		}
		return result;
	}
	
	public NodeList getAllFloor(){
		String[] paths = {"html", "body", "wrap1", "wrap2","container", "left_section", "core", "p_postlist" };
		Node node = getNode(paths);
		return node.getChildren();
	}	
	
}
