# RideEase Fullstack App Architecture & Reference

## Overview
This directory contains the main application code for RideEase, separated into the React frontend and Spring Boot backend. 

Here is a high-level view of how they interact:
```
                       ┌────────────────┐
                       │ React Frontend │
                       └───────┬────────┘
                               │
                      HTTP / JSON REST API
                               │
                       ┌───────▼────────┐
                       │  Spring Boot   │
                       └───────┬────────┘
                               │
                            JDBC/JPA
                               │
                      ┌────────▼────────┐
                      │   PostgreSQL    │
                      └─────────────────┘
```

## Features
*   **React Frontend (`/frontend`):**
    *   `src/api.js`: Handles API configuration and passes the JWT token from `localStorage` in API headers.
    *   `src/App.js`: Protects private routes and sets up URL routing.
    *   `src/pages/`: Houses views for Guest Home, Rider Dashboard (map & fare selector), Simulated Checkout, Ride History, Feedback, and Admin Dashboard.
*   **Spring Boot Backend (`/backend`):**
    *   `controller/`: Maps REST API endpoints (Authentication, Rides, and Admin tasks).
    *   `model/`: Entity classes representing PostgreSQL database tables.
    *   `repository/`: Database interface repositories with custom JPQL and native SQL queries.
    *   `dto/`: Request/Response Data Transfer Objects to handle structured JSON messaging.
    *   `config/`: CORS policies, JWT token authentication filters, and Spring Security configurations.

## Technologies Used

This project leverages a robust, modern full-stack web stack to deliver a seamless cab booking experience:

### Frontend (Client-Side)
* **React.js (v18.2.0)** - Powers the Single Page Application (SPA), managing responsive UI components, user auth states, routing, and history logs.
* **Leaflet.js (v1.9.4)** - Renders interactive maps, allowing users to dynamically drop markers for pickup/destination points.
* **OpenStreetMap (OSM) & Nominatim API** - Handshakes OSM graphics for tiling and coordinates geocoding, resolving pinned latitude/longitude values into readable addresses.
* **Custom Vanilla CSS** - Tailored, responsive layout designs and UI modals written entirely in vanilla CSS for optimal lightweight performance.

### Backend (Server-Side)
* **Spring Boot (v3.3.0)** - Serves as the primary REST API web engine, exposing controller endpoints for authentication, trip handling, and administration logs.
* **Spring Security** - Restricts endpoint access, sets CORS configurations, and handles role-based authorization for administrative pathways.
* **JSON Web Tokens (JJWT v0.11.5)** - Houses session parameters in cryptographically signed stateless tokens, allowing authenticated communication via HTTP headers.
* **BCrypt Hashing** - Securely hashes password credentials using salt rounds before committing them to the storage layer.

### Database (Data Persistence)
* **PostgreSQL** - Handles robust relational database management. Includes tables for `users` (credentials and authorization levels), `rides` (coordinate logs, calculations, and statuses), and `ratings` (feedback and reviews).

## Project Structure
Here's how the sub-project is structured:
```
rideease-fullstack/
├── backend/                       # Spring Boot REST API
│   ├── src/main/java/             # Source files
│   └── pom.xml                    # Maven build file
└── frontend/                      # React SPA
    ├── src/                       # Components, helpers, and page views
    └── package.json               # Frontend dependencies
```

## Installation & Setup
For detailed guide instructions on configuring PostgreSQL, importing schemas, setting properties, and starting backend/frontend run servers, please refer directly to the [Root Setup Guide](../README.md).

## Usage
*   **API Security:** All protected endpoints require a valid JWT token sent within the `Authorization: Bearer <token>` header.
*   **Auth Endpoints (`/api/auth`):**
    *   `POST /register` - Payload: `{ username, email, password }`
    *   `POST /login` - Payload: `{ username, password }`
*   **Rides Endpoints (`/api/rides`):**
    *   `POST /` - Register a completed booking
    *   `GET /history` - Fetch authenticated user's logs
    *   `POST /{id}/rate` - Submit feedback reviews
*   **Admin Endpoints (`/api/admin`):**
    *   `GET /stats` - Overall stats summary (Admin only)
    *   `GET /users` - Retrieve all accounts (Admin only)
    *   `GET /rides` - Retrieve all transaction logs (Admin only)

## Future Improvements
*   **WebSockets Integration:** Live vehicle position rendering and map tracking.
*   **Stripe Sandbox Checkout:** Real card transaction processor verification.
*   **Companion Portals:** Driver-dedicated dispatch views.
*   **Vouchers and Discounts:** Dynamic promo code validation.

## Challenges & Learning
*   **Client State Coordination:** Managing Leaflet layers and React lifecycle updates smoothly.
*   **Spring Security stateless filtering:** Applying secure interceptors correctly to authorize administrator endpoints.
*   **JPA Relational Queries:** Aggregating metrics database rows into unified performance summaries.

## Contributing
Contributions are always welcome!
1. Fork the Repository.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

## Author
Dhanya K

GitHub: @Dhanya562004
Email: kdhanya762@gmail.com
