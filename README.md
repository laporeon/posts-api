<h1 align="center"> Posts API

![java](https://img.shields.io/static/v1?label=java&message=21.0.8&color=2d3748&logo=openjdk&style=flat-square)
![spring boot](https://img.shields.io/static/v1?label=spring%20boot&message=3.5.4&color=2d3748&logo=springboot&style=flat-square)
![mongodb](https://img.shields.io/badge/mongodb-latest-4b32c3?style=flat-square&logo=mongodb)
![docker](https://img.shields.io/static/v1?label=docker&message=28.5.0&color=2d3748&logo=docker&style=flat-square)

</h1>

## Table of Contents

- [About](#about)
- [Requirements](#requirements)
    - [For Local Development](#for-local-development-optional)
- [Configuring](#configuring)
    - [.env](#env-optional)
    - [Docker](#docker)
- [Troubleshooting](#troubleshooting)
- [Usage](#usage)
  - [Routes](#routes)
      - [Requests](#requests)

## About

This is my solution for the [TradeMap Code backend challenge](https://github.com/TradeMap-Code/desafio-backend). It's a comprehensive REST API that simulates a backend service for a personal blog platform.

**Key features:**

- Input validation for post request payloads.
- Full CRUD operations for posts.
- Pagination support with flexible sorting.
- Swagger documentation for all endpoints. 
- One-command deployment with Docker Compose

## Requirements:

- [Docker](https://www.docker.com/)

### For Local Development (optional)
- Java 21+ 
- Maven 3.9+ 
- MongoDB
  
**Note**: If you're using Docker, you don't need Java, Maven, or MongoDB installed locally — everything runs inside containers.

## **Configuring**

### **.env** (optional)

Environment configuration is optional. The application will run with default values. To customize these settings with your own configurations, rename the `.env.example` to `.env` and modify the variables according to your needs.

| key            | description           | default                                                                |
|----------------|-----------------------|------------------------------------------------------------------------|
| MONGO_USER     | MongoDB username      | trademap                                                               |
| MONGO_PASSWORD | MongoDB password      | dbpassword                                                             |
| MONGO_DB       | MongoDB database name | posts                                                                  |
| MONGO_HOST     | MongoDB host          | localhost (or `mongodb` if running with Docker) |

### Docker

For the fastest setup, it is recommended to use [Docker Compose](https://docs.docker.com/compose/):

```bash
# Run docker compose command to start all services
$ docker compose up -d --build
```

Once started, application will be available at `http://localhost:8080/api/v1/posts`.

## Troubleshooting

If you receive a port conflict error like:
```
Error response from daemon: failed to set up container networking: driver failed programming external connectivity on endpoint mongodb (...): failed to bind host port for 0.0.0.0:27017:172.21.0.2:27017/tcp: address already in use
```

This means MongoDB is already running locally on your system. You'll need to:
- Stop the local MongoDB service: `sudo systemctl stop mongod`
- Then retry docker compose command: `docker compose up -d --build`

## Usage

### **Routes**

| Route               | HTTP Method | Params                                                                                                                                                                                                         | Description                             | Auth Method |
|---------------------|-------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------|-------------|
| `/api/v1/docs`      | GET         | -                                                                                                                                                                                                              | Swagger documentation                   | None        |
| `/api/v1/posts`     | POST        | Body with `title`, `description` and `body`.                                                                                                                                                                   | Create a new post                       | None        |
| `/api/v1/posts`     | GET         | **Query Parameters:**<br>• `page` - Page number (default: 0)<br>• `size` - Page size (default: 10)<br>• `orderBy` - Sort field (default: "title")<br>• `direction` - Sort direction: ASC/DESC (default: "ASC") | Retrieve paginated posts with sorting   | None        |
| `/api/v1/posts/:id` | GET         | `:id`                                                                                                                                                                                                          | Retrieve existing post by its unique id. | None        |
| `/api/v1/posts/:id` | PUT         | `:id` + Body with Body with `title`, `description` and `body`.                                                                                                                                                 | Update post information                 | None        |
| `/api/v1/posts/:id` | DELETE      | `:id`                                                                                                                                                                                                          | Delete an existing post.                | None        |

#### Requests

- `POST /api/v1/posts`

Request body:

```json
{
  "title": "Getting Started with Spring Boot",
  "description": "A comprehensive guide to building REST APIs with Spring Boot framework.",
  "body": "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications."
}
```

- `PUT /api/v1/posts/:id`

Request body:

```json
{
  "title": "Getting Started with Spring Boot",
  "description": "A comprehensive guide to building REST APIs with Spring Boot framework.",
  "body": "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications."
}
```

[⬆ Back to the top](#-posts-api)