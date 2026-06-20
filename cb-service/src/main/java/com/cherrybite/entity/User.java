package com.cherrybite.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.cherrybite.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    
    @Column(name = "fullname", nullable = false)
    private String fullName;
    
    @Column(name = "username", unique = true, nullable = false)
    private String userName;
    
    @Column(unique = true)
    private String email;
    
    @Column(name = "phonenumber", unique = true)
    private String phoneNumber;
    
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    
    private String bio;
    
    @Column(name = "trust_score", precision = 5, scale = 2)
    private BigDecimal trustScore;
    
    @Column(name = "review_count")
    private Integer reviewCount;
    
    @Column(name = "follower_count")
    private Integer followerCount;
    
    @Column(name = "following_count")
    private Integer followingCount;
    
    @Column(name = "is_verified")
    private Boolean isVerified;
    
    @Column(name = "location_permission")
    private Boolean locationPermission;
    
    @Column(name = "last_latitude", precision = 10, scale = 8)
    private BigDecimal lastLatitude;
    
    @Column(name = "last_longitude", precision = 11, scale = 8)
    private BigDecimal lastLongitude;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        
        if (this.trustScore == null)
            this.trustScore = BigDecimal.ZERO;

        if (this.reviewCount == null)
            this.reviewCount = 0;

        if (this.followerCount == null)
            this.followerCount = 0;

        if (this.followingCount == null)
            this.followingCount = 0;

        if (this.isVerified == null)
            this.isVerified = false;

        if (this.locationPermission == null)
            this.locationPermission = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public Integer getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}

	public Integer getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(Integer followingCount) {
		this.followingCount = followingCount;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getLocationPermission() {
		return locationPermission;
	}

	public void setLocationPermission(Boolean locationPermission) {
		this.locationPermission = locationPermission;
	}

	public BigDecimal getLastLatitude() {
		return lastLatitude;
	}

	public void setLastLatitude(BigDecimal lastLatitude) {
		this.lastLatitude = lastLatitude;
	}

	public BigDecimal getLastLongitude() {
		return lastLongitude;
	}

	public void setLastLongitude(BigDecimal lastLongitude) {
		this.lastLongitude = lastLongitude;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
}
