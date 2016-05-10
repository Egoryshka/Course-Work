package com.romanovich.user.model;

/**
 * Created by Егор on 10.05.2016.
 */
public class MovieRating {

    private Long postId;
    private boolean positive;

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
