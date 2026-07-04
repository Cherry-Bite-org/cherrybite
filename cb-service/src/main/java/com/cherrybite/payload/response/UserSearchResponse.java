package com.cherrybite.payload.response;

import java.math.BigDecimal;
import java.util.UUID;

public class UserSearchResponse {

	private UUID userId;

	private String username;

	private String profileImageUrl;

	private String fullName;

	private BigDecimal trustScore;

	private Boolean verified;

	private Boolean following;
	
	private Boolean isCurrentUser;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public BigDecimal getTrustScore() {
		return trustScore;
	}

	public void setTrustScore(BigDecimal trustScore) {
		this.trustScore = trustScore;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Boolean getFollowing() {
		return following;
	}

	public void setFollowing(Boolean following) {
		this.following = following;
	}

	public Boolean getIsCurrentUser() {
		return isCurrentUser;
	}

	public void setIsCurrentUser(Boolean isCurrentUser) {
		this.isCurrentUser = isCurrentUser;
	}

}
