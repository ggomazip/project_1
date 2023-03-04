package com.project.www.dto;

public class BoardDto {
	private int no;
	private String id;
	private String contents;
	private String contentsText;
	private int contentsTextLengh;
	private String filePath;
	private String fileName;
	private String fileSrc;
	private String wtime;
	private int hit;
	private int goodHit;
	private int badHit;
	private String profileImgSrc;
	private String hashTagCombination;
	private boolean isFollow;
	private boolean isGoodHit;
	private int replyCnt;
	
	
	public int getReplyCnt() {
		return replyCnt;
	}
	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}
	public int getContentsTextLengh() {
		return contentsTextLengh;
	}
	public void setContentsTextLengh(int contentsTextLengh) {
		this.contentsTextLengh = contentsTextLengh;
	}
	public boolean getIsGoodHit() {
		return isGoodHit;
	}
	public void setIsGoodHit(boolean isGoodHit) {
		this.isGoodHit = isGoodHit;
	}
	public boolean getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}
	public String getHashTagCombination() {
		return hashTagCombination;
	}
	public void setHashTagCombination(String hashTagCombination) {
		this.hashTagCombination = hashTagCombination;
	}
	public String getContentsText() {
		return contentsText;
	}
	public void setContentsText(String contentsText) {
		this.contentsText = contentsText;
	}
	public String getProfileImgSrc() {
		return profileImgSrc;
	}
	public void setProfileImgSrc(String profileImgSrc) {
		this.profileImgSrc = profileImgSrc;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getWtime() {
		return wtime;
	}
	public void setWtime(String wtime) {
		this.wtime = wtime;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getGoodHit() {
		return goodHit;
	}
	public void setGoodHit(int goodHit) {
		this.goodHit = goodHit;
	}
	public int getBadHit() {
		return badHit;
	}
	public void setBadHit(int badHit) {
		this.badHit = badHit;
	}
	
	public String getFileSrc() {
		return fileSrc;
	}
	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}
	
		
}
