package com.ljs.main;

public class User {
	public String nickname;
	public String comment;
	public User(String nickname, String comment) {
		super();
		this.nickname = nickname;
		this.comment = comment;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
