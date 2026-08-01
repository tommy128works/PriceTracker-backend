# Price Tracker
A full-stack price tracking application that allows users to manage deals and organize them into personalized lists with list-specific notes.

## Screenshots

### Register Page
![Register Page](screenshots/Price%20Tracker_Register%20Page_v1.0.jpg)
Users can create an account by providing their registration details. Passwords are securely hashed before being stored in the database, ensuring that plaintext passwords are never stored.

### Login Page
![Login Page](screenshots/Price%20Tracker_Login%20Page_v1.0.jpg)
Users can securely log in and stay signed in without repeatedly entering their credentials, made possible through the implementation of short-lived access tokens and long-lived refresh tokens.

### Deal List Page
![Deal List Page](screenshots/Price%20Tracker_Deal%20Lists%20Page_v1.0.jpg)
Users can create custom lists to organize and manage their deals based on their individual shopping needs.

### Deal List Detail Page
![Deal List Detail Page](screenshots/Price%20Tracker_Deal%20List%20Detail%20Page_v1.0.jpg)
Users can view the deals saved within a selected list, including details about each deal.

## Project Purpose
Many price-tracking applications and online deal forums highlight deals from stores that are not accessible to every shopper. An item may not be sold at local stores, or the same deal may never become available in the shopper's region.

Price Tracker addresses this problem by allowing users to document and organize deals they encounter within their local market. This gives users a more personalized view of the deals available to them and makes it easier to plan future purchases.

## Key Features
- User registration and authentication
- JWT-based authentication with short-lived access tokens and HttpOnly refresh-token cookies
- Automatic access-token refresh
- User-specific deal lists
- Add and remove deals from lists
- List-specific notes for deals
- Reusable deals across multiple lists
- Protected frontend routes

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- JPA / Hibernate
- Gradle

### Frontend
- React
- TypeScript
- Axios
- React Router 

### Database
- PostgreSQL
- Neon

### DevOps / Deployment
- Docker
- Vercel
- Render

## Architecture
Price Tracker follows a layered backend architecture with a React and TypeScript frontend communicating with a Spring Boot REST API.

## Architecture

Price Tracker follows a layered backend architecture with a React and TypeScript frontend communicating with a Spring Boot REST API.

### Application Architecture
```text
React + TypeScript
        ↓
      Axios
        ↓
Spring Boot REST API
        ↓
    Controller
        ↓
     Service
        ↓
   Repository
        ↓
   PostgreSQL
```

### Authentication Flow
```text
              Login
                ↓
     Short-lived Access Token
                ↓
     Stored in Frontend Memory
                ↓
        Axios Interceptor
                ↓
Access Token Expires / API Returns 401
                ↓
Refresh Token Sent via HttpOnly Cookie
                ↓
      New Access Token Issued
                ↓
Frontend Updates Access Token in Memory
```
The backend uses a layered architecture to separate HTTP request handling, business logic, and data access, while the frontend uses Axios to communicate with the REST API and manage token-based authentication.

Copyright © 2026 tommy128works.
All rights reserved.

This code is published for viewing purposes only.
No permission is granted to use, modify, or distribute it
without explicit written consent.