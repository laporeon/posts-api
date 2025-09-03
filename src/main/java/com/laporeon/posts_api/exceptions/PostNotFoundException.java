package com.laporeon.posts_api.exceptions;

public class PostNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "No posts found.";
    private static final String MESSAGE_WITH_ID = "Post with id %s not found.";

    public PostNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PostNotFoundException(String id) {
        super(MESSAGE_WITH_ID.formatted(id));
    }

}