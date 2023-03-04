package com.project.www.dto;

public class ReplyDto {
	private int num;
	private int no;
	private int stepNum;
	private String id;
	private String reply;
	private String profileImgSrc;
	
	public String getProfileImgSrc() {
		return profileImgSrc;
	}
	public void setProfileImgSrc(String profileImgSrc) {
		this.profileImgSrc = profileImgSrc;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getStepNum() {
		return stepNum;
	}
	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	
}
