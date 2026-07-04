package com.cherrybite.payload.response;

import java.math.BigDecimal;

public class PublicUserProfileResponse {

    private String fullName;
    
    private String userName;
    
    private String profileImageUrl;
    
    private String bio;

    private BigDecimal trustScore;
    
    private Integer reviewCount;
    
    private Integer followers;
    
    private Integer following;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public BigDecimal getTrustScore() {
		return trustScore;
	}

	public void setTrustScore(BigDecimal trustScore) {
		this.trustScore = trustScore;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Integer getFollowers() {
		return followers;
	}

	public void setFollowers(Integer followers) {
		this.followers = followers;
	}

	public Integer getFollowing() {
		return following;
	}

	public void setFollowing(Integer following) {
		this.following = following;
	}

}
