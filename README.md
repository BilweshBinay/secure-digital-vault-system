# Secure Digital Vault System

A Spring Boot application to securely store personal information such as:

- Secure Notes
- Password Records
- Personal Documents
- Bank Details

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Features Implemented

- User Entity
- Role Enum
- User Repository
- User Service
- User Registration API
- Security Configuration

## API

### Register User

POST /api/auth/register

Request Body:

{
  "name": "Bilwesh",
  "email": "bilwesh@gmail.com",
  "password": "password123"
}

## Future Features

- BCrypt Password Encryption
- Login API
- JWT Authentication
- Secure Notes
- Password Vault
- Document Storage
- Admin Dashboard
- File Upload
- Email Verification
