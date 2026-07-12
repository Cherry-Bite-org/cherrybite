package com.cherrybite.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.cherrybite.enums.FoodReactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "food_post_reactions",
    schema = "cb",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "food_post_id",
            "user_id"
        })
    }
)
public class FoodPostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reaction_id")
    private UUID reactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_post_id", nullable = false)
    private FoodPost foodPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false)
    private FoodReactionType reactionType;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

	public UUID getReactionId() {
		return reactionId;
	}

	public void setReactionId(UUID reactionId) {
		this.reactionId = reactionId;
	}

	public FoodPost getFoodPost() {
		return foodPost;
	}

	public void setFoodPost(FoodPost foodPost) {
		this.foodPost = foodPost;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FoodReactionType getReactionType() {
		return reactionType;
	}

	public void setReactionType(FoodReactionType reactionType) {
		this.reactionType = reactionType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
