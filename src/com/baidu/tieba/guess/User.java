package com.baidu.tieba.guess;

public class User {

	/*
	 * 报名帖：
	 * http://tieba.baidu.com/p/2793165587
	 * 
	 */
	User(String userName){
		tiebaID = userName;
		mark = 0;
	}
	
	private String tiebaID;
	private int mark;
	public String getTiebaID() {
		return tiebaID;
	}
	public void setTiebaID(String tiebaID) {
		this.tiebaID = tiebaID;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}

	
	
}
