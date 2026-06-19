# RideEase - Fullstack Cab Booking App

React + Node.js + Express + PostgreSQL

## Project Structure

```
rideease-fullstack/
├── backend/
│   ├── db/
│   │   ├── index.js        # PostgreSQL connection
│   │   └── schema.sql      # Run this first to create tables
│   ├── middleware/
│   │   └── auth.js         # JWT auth + admin middleware
│   ├── routes/
│   │   ├── auth.js         # POST /api/auth/register, /login
│   │   ├── rides.js        # POST /api/rides, GET /api/rides/history, rate
│   │   └── admin.js        # GET /api/admin/stats, users, rides
│   ├── server.js
│   ├── package.json
│   └── .env.example        # Copy to .env and fill in values
│
└── frontend/
    ├── src/
    │   ├── api.js           # All fetch() calls in one place
    │   ├── App.js
    │   └── pages/
    │       ├── Home.js           (unchanged)
    │       ├── Login.js          (updated - uses backend)
    │       ├── Register.js       (updated - uses backend)
    │       ├── Dashboard.js      (updated - logout uses JWT)
    │       ├── Payment.js        (updated - saves ride to DB)
    │       ├── RideHistory.js    (NEW)
    │       ├── RateRide.js       (NEW)
    │       └── AdminDashboard.js (NEW)
    └── package.json
```

## Setup Instructions

### Step 1 — PostgreSQL Database

1. Install PostgreSQL from https://www.postgresql.org/download/
2. Open psql and run:

```sql
CREATE DATABASE rideease;
\c rideease
\i backend/db/schema.sql
```

### Step 2 — Backend

```bash
cd backend
cp .env.example .env
```

Edit `.env` and set your PostgreSQL password:
```
DATABASE_URL=postgresql://postgres:YOUR_PASSWORD@localhost:5432/rideease
JWT_SECRET=any_long_random_string_here
```

Then:
```bash
npm install
npm run dev
```

Backend runs on http://localhost:5000

### Step 3 — Frontend

```bash
cd frontend
npm install
npm start
```

Frontend runs on http://localhost:3000

### Step 4 — Admin Login

Default admin account:
- Username: `admin`
- Password: `password`

> Change the admin password after first login.

## API Endpoints

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/auth/register | No | Register new user |
| POST | /api/auth/login | No | Login, returns JWT |
| POST | /api/rides | Yes | Save a completed booking |
| GET | /api/rides/history | Yes | Get user's ride history |
| POST | /api/rides/:id/rate | Yes | Rate a ride |
| GET | /api/admin/stats | Admin | Summary stats |
| GET | /api/admin/users | Admin | All users list |
| GET | /api/admin/rides | Admin | All rides list |

## New Features Added

- **Ride History** — `/history` page shows all past bookings with fare, route, rating
- **Rate Your Ride** — After payment, users rate their ride with stars + review
- **Admin Dashboard** — `/admin` shows total users, revenue, avg rating, all rides
- **JWT Authentication** — Secure login with tokens instead of localStorage
- **PostgreSQL** — All data stored in a real database permanently
