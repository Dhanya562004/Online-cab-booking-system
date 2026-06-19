# RideEase — Online Cab Booking System

RideEase is a modern, full-stack online cab booking application built using **React**, **Node.js (Express)**, and **PostgreSQL**. The project features comprehensive user booking flows, ride history, rating systems, and a dedicated admin dashboard for monitoring platform analytics and transactions.

---

## 🚀 Key Features

*   **Secure Authentication:** User registration and login utilizing JSON Web Tokens (JWT) for secure session management.
*   **Cab Booking & Fare Estimation:** Calculate route distances and fares, process mockup payments, and persist ride records.
*   **Ride History:** View all past ride bookings, search details, check fares, and review ratings.
*   **Interactive Rating System:** Submit post-ride reviews and ratings (1 to 5 stars) for ride experiences.
*   **Admin Dashboard:** Dedicated admin page featuring aggregate stats (total users, total revenue, average ratings) and administrative tables for all users and rides.

---

## 🛠️ Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Frontend** | React, CSS, Fetch API |
| **Backend** | Node.js, Express, JWT, CORS |
| **Database** | PostgreSQL |

---

## 📁 Repository Structure

The codebase is organized as follows:

```text
online-cab-booking/
├── .gitignore
├── README.md                          # Root project documentation (this file)
└── rideease-fullstack/                # Core full-stack application folder
    ├── README.md                      # Detailed setup and API specifications
    ├── backend/                       # Express server and DB scripts
    │   ├── server.js                  # Main entry point
    │   ├── db/                        # Database configuration and schema
    │   ├── middleware/                # JWT and authorization helpers
    │   └── routes/                    # API endpoints (auth, rides, admin)
    └── frontend/                      # React SPA
        ├── package.json
        └── src/                       # Components, pages, and API handlers
```

For detailed configurations, API documentation, and folder breakdowns, see the [rideease-fullstack/README.md](file:///c:/Users/Deeksha/OneDrive/Documents/online%20cab%20booking/rideease-fullstack/README.md).

---

## ⚡ Quick Start Guide

### 1. Database Setup
Ensure PostgreSQL is installed and running, then execute the database setup:
```sql
CREATE DATABASE rideease;
-- Connect to the database and run backend/db/schema.sql
\c rideease
\i rideease-fullstack/backend/db/schema.sql
```

### 2. Backend Setup
Navigate to the backend directory, install packages, set up your `.env`, and start the server:
```bash
cd rideease-fullstack/backend
cp .env.example .env
# Edit .env and configure DATABASE_URL and JWT_SECRET
npm install
npm run dev
```
The server will run on `http://localhost:5000`.

### 3. Frontend Setup
Navigate to the frontend directory, install dependencies, and run the React app:
```bash
cd rideease-fullstack/frontend
npm install
npm start
```
The client app will launch at `http://localhost:3000`.

---

## 👥 Admin Access
Default admin credentials for system verification:
*   **Username:** `admin`
*   **Password:** `password`

*(Please remember to update default administrator passwords in production environments.)*
