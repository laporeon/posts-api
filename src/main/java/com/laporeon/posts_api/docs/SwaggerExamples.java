package com.laporeon.posts_api.docs;

public class SwaggerExamples {

        public static final String POST_REQUEST_EXAMPLE = """
        {
            "title": "Getting Started with Spring Boot",
            "description": "A comprehensive guide to building REST APIs with Spring Boot framework.",
            "body": "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications."
        }
        """;

        public static final String SINGLE_POST_RESPONSE_EXAMPLE = """
        {
            "id": "507f1f77bcf86cd799439011",
            "title": "Getting Started with Spring Boot",
            "description": "A comprehensive guide to building REST APIs with Spring Boot framework.",
            "body": "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.",
            "createdAt": "2025-01-15T10:30:00",
            "updatedAt": "2025-01-15T10:30:00"
        }
        """;

        public static final String POSTS_LIST_RESPONSE_EXAMPLE = """
        {
            "content": [
                {
                "id": "507f1f77bcf86cd799439011",
                "title": "Getting Started with Spring Boot",
                "description": "A comprehensive guide to building REST APIs with Spring Boot framework.",
                "body": "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can just run. In this post, we'll explore the fundamentals of creating a REST API...",
                "createdAt": "2025-01-15T10:30:00Z",
                "updatedAt": "2025-01-15T10:35:00Z"
                },
                {
                "id": "507f1f77bcf86cd799439012", 
                "title": "MongoDB with Spring Data",
                "description": "Learn how to integrate MongoDB with Spring Data for seamless database operations.",
                "body": "MongoDB is a popular NoSQL database that works perfectly with Spring Boot applications. In this tutorial, we'll set up MongoDB integration using Spring Data MongoDB...",
                "createdAt": "2025-02-20T14:45:00Z",
                "updatedAt": "2025-02-21T09:15:00Z"
                },
                {
                "id": "507f1f77bcf86cd799439013",
                "title": "API Documentation with Swagger",
                "description": "Best practices for documenting REST APIs using OpenAPI and Swagger UI.",
                "body": "Good API documentation is crucial for developer experience. Swagger UI provides an interactive interface for testing and understanding your APIs. Here's how to implement it properly...",
                "createdAt": "2025-03-10T16:20:00Z",
                "updatedAt": "2025-03-10T16:20:00Z"
                }
            ],
            "pageNumber": 0,
            "pageSize": 10,
            "totalPages": 1,
            "totalElements": 3,
            "numberOfElements": 3,
            "isFirstPage": true,
            "isLastPage": true,
            "isEmpty": false,
            "isSorted": false,
            "isUnsorted": true
        }
        """;

        public static final String VALIDATION_ERROR_MESSAGE = """
        {
            "status": 400,
            "name": "BAD_REQUEST",
            "errors": [
                "Title must be between 3 and 100 characters long.",
                "Description is required."
            ],
            "timestamp": "2025-09-04T18:19:52.121160501Z"
            }
        """;

        public static final String NOT_FOUND_ERROR_MESSAGE = """
        {
            "status": 404,
            "name": "NOT_FOUND",
            "errors": [
                "Post with id 68b88e452c073f3290787d65 not found"
            ],
            "timestamp": "2025-09-04T18:19:52.121160501Z"
        }
        """;

}
