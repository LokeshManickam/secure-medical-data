
# System Architecture

## Project

Secure Medical Data

## Architecture Overview

The application follows a full-stack REST API architecture.

```text
React Frontend
      |
      | HTTP / REST API
      v
Spring Boot Backend
      |
      +----------------------+
      |                      |
      v                      v
Controller Layer       Security Layer
      |                 Spring Security
      v                      |
Service Layer                JWT
      |
      v
Repository Layer
      |
      v
JPA / Hibernate
      |
      v
MySQL Database
```
