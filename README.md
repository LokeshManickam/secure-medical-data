
# Secure Medical Data

## Specialized Enciphered Access Through Text Steganography

Secure Medical Data is a full-stack healthcare-oriented application
designed to demonstrate the protection of sensitive medical information
using authentication, encryption, and text steganography.

The application uses React for the frontend, Spring Boot for the
backend, and MySQL for persistent user and patient information.

> **Important:** This project uses fictional/test patient data only.
> No real patient medical information should be used.

---

# 1. Project Overview

Healthcare applications handle sensitive information that requires
appropriate protection.

This project demonstrates a secure workflow in which medical
information is:

```text
Medical Data
     |
     v
AES-GCM Encryption
     |
     v
Encrypted Payload
     |
     v
Text Steganography
     |
     v
Stego Text
     |
     v
Authorized User
     |
     v
Extract Payload
     |
     v
AES-GCM Decryption
     |
     v
Original Medical Data
```
