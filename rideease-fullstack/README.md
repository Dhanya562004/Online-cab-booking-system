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
    *   `controller/`: Maps REST endpoints.
    *   `model/`: Entity classes representing database tables.
    *   `repository/`: Database interface interfaces for database queries.
    *   `security/`: Custom filters validating JWT token authenticity.
    *   `service/`: Core business operations logic.

## Technologies Used
This project is built using:
*   **Frontend:** React.js, HTML, CSS, JavaScript
*   **Backend:** Spring Boot
*   **Database:** PostgreSQL

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
