
# Database Design

## Database

Database Name:

secure_medical_data

Database Management System:

MySQL 8.4

## Tables

### users

| Column   | Type         | Description          |
| -------- | ------------ | -------------------- |
| id       | BIGINT       | Primary key          |
| username | VARCHAR(50)  | Unique username      |
| password | VARCHAR(255) | BCrypt password hash |
| role     | VARCHAR(20)  | User role            |

### patients

| Column      | Type         | Description               |
| ----------- | ------------ | ------------------------- |
| id          | BIGINT       | Primary key               |
| patientCode | VARCHAR(20)  | Unique patient identifier |
| name        | VARCHAR(100) | Patient name              |
| age         | INT          | Patient age               |
| gender      | VARCHAR(20)  | Patient gender            |
| diagnosis   | VARCHAR(500) | Diagnosis information     |

## Database Mapping

Java Entity → Database Table

User.java → users

Patient.java → patients

JPA/Hibernate is used to map Java entities to MySQL tables.

## Current Relationships

The current implementation does not define a direct
foreign-key relationship between users and patients.

## Data Safety

Only fictional test patient data should be used.

Real patient information must not be stored in this project.
