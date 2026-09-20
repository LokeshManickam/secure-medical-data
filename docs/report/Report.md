
# SECURE MEDICAL DATA

## Project Report

### Project Title

**SPECIALIZED ENCIPHERED ACCESS THROUGH TEXT STEGANOGRAPHY**

### Implementation Project Name

**Secure Medical Data**

### Domain

**Healthcare / Medical Data Security**

### Author

**Lokesh Manickam**

---

# 1. Abstract

Healthcare applications handle sensitive information that requires
protection against unauthorized access and disclosure.

Secure Medical Data is a full-stack web application developed to
demonstrate the protection of sensitive medical information using
authentication, encryption, and text steganography.

The application uses React for the frontend, Spring Boot for the
backend, and MySQL for persistent user and patient information.

The security workflow first encrypts medical information using
AES-GCM and then conceals the encrypted payload within carrier text
using text steganography.

An authorized user can subsequently extract the encrypted payload
and decrypt it to recover the original medical information.

The project uses fictional/test patient data only.

---

# 2. Introduction

Healthcare systems process sensitive information such as patient
details, diagnoses, and medical information.

Unauthorized access to such information can result in privacy and
security risks.

This project demonstrates a software-based approach for protecting
medical information through authentication, encryption, and text
steganography.

The application provides a web-based interface through which an
authorized user can authenticate, manage fictional patient
information, and demonstrate secure medical message handling.

---

# 3. Problem Statement

Sensitive medical information should only be accessible to
authorized users.

The project therefore demonstrates:

- Authentication
- Authorization
- Secure password handling
- Encryption
- Text steganography
- Protected REST APIs
- Controlled access to patient information

The system combines these concepts into a full-stack web
application.

---

# 4. Objectives

The main objectives are:

1. Develop a full-stack healthcare-oriented security application.
2. Implement authenticated access.
3. Protect passwords using BCrypt hashing.
4. Protect REST APIs using Spring Security and JWT.
5. Manage fictional patient information.
6. Encrypt sensitive medical information using AES-GCM.
7. Conceal encrypted information using text steganography.
8. Extract and decrypt protected information.
9. Provide a React-based user interface.
10. Store user and patient information using MySQL.

---

# 5. Original GrandTwin Requirement

The original project concept is:

**SPECIALIZED ENCIPHERED ACCESS THROUGH TEXT STEGANOGRAPHY**

The project focuses on securing and transferring sensitive
patient/medical information using encryption and text
steganography.

The original project document is the primary source for the
original project requirements.

The original document does not specify:

- A complete database schema
- A specific steganography algorithm
- A specific modern frontend framework
- A specific modern backend implementation

Therefore, implementation choices made during development are
documented separately below.

---

# 6. Technical Implementation Decisions

The project is implemented using:

## Backend

- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- Maven

## Database

- MySQL 8.4

## Frontend

- React
- JavaScript
- HTML
- CSS
- Axios
- Vite

## Security

- BCrypt password hashing
- JWT authentication
- Spring Security
- AES-GCM encryption
- Text steganography

---

# 7. System Architecture

The application follows a layered full-stack architecture.

```text
React Frontend
      |
      | REST API
      v
Spring Boot Backend
      |
      v
Controller Layer
      |
      v
Service Layer
      |
      v
Repository Layer
      |
      v
JPA / Hibernate
      |
      v
MySQL
```
