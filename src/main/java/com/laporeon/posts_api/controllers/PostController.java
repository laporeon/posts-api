package com.laporeon.posts_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private Map<String, Object>[] posts = new Map[]{
            Map.of("id", 1, "title", "Using Records in Java"),
            Map.of("id", 2, "title", "Learn HTTP Status"),
    };

    @GetMapping
    public Map<String, Object>[] getPosts() {
        return posts;
    }
}
