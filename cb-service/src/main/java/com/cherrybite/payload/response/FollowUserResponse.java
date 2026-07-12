package com.cherrybite.payload.response;

import java.math.BigDecimal;
import java.util.UUID;

public class FollowUserResponse {

    private UUID userId;

    private String userName;

    private String fullName;

    private String profileImageUrl;

    private Boolean verified;

    private BigDecimal trustScore;

    private Boolean following;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public BigDecimal getTrustScore() {
		return trustScore;
	}

	public void setTrustScore(BigDecimal trustScore) {
		this.trustScore = trustScore;
	}

	public Boolean getFollowing() {
		return following;
	}

	public void setFollowing(Boolean following) {
		this.following = following;
	}

}
