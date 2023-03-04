package com.project.www.dto;

public class MemberDto {
	private String email;
	private String id;
	private String pw;
	private String profileImgPath;
	private String profileImgName;
	private String profileImgSrc;
	
	private String follower;
	private String follow;
	private Boolean isFollow;
	
	public Boolean getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(Boolean isFollow) {
		this.isFollow = isFollow;
	}
	public String getFollower() {
		return follower;
	}
	public void setFollower(String follower) {
		this.follower = follower;
	}
	public String getFollow() {
		return follow;
	}
	public void setFollow(String follow) {
		this.follow = follow;
	}
	public String getProfileImgPath() {
		return profileImgPath;
	}
	public void setProfileImgPath(String profileImgPath) {
		this.profileImgPath = profileImgPath;
	}
	public String getProfileImgName() {
		return profileImgName;
	}
	public void setProfileImgName(String profileImgName) {
		this.profileImgName = profileImgName;
	}
	public String getProfileImgSrc() {
		return profileImgSrc;
	}
	public void setProfileImgSrc(String profileImgSrc) {
		this.profileImgSrc = profileImgSrc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
}
	
	
	
