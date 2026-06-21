# RideEase - Online Cab Booking System

RideEase is a full-stack web application designed for booking cabs. The project is split into a React frontend and a Node.js/Express backend, with PostgreSQL handling the database.

It supports user authentication, a simulated ride booking process with fare estimation, ride history tracking, rating features, and a simple admin panel to monitor system statistics.

## Project Structure

The project is structured as a monorepo containing both the frontend and backend:

*   **`rideease-fullstack/backend`**: Express server handling API requests, database queries, and JWT authentication middleware.
*   **`rideease-fullstack/frontend`**: React single-page application built with components and state management.

For more technical details on the backend endpoints and schema, see the [rideease-fullstack/README.md](file:///c:/Users/Deeksha/OneDrive/Documents/online%20cab%20booking/rideease-fullstack/README.md).

## Getting Started

### Prerequisites
Make sure you have the following installed on your machine:
- Node.js (v16+)
- npm
- PostgreSQL

---

### Step-by-Step Setup

#### 1. Setup the Database
Create a PostgreSQL database and run the schema SQL script to set up the tables:

```sql
CREATE DATABASE rideease;
\c rideease
\i rideease-fullstack/backend/db/schema.sql
```

This creates the `users`, `rides`, and `ratings` tables and inserts a default admin account.

#### 2. Start the Backend Server
1. Go to the backend directory:
   ```bash
   cd rideease-fullstack/backend
   ```
2. Copy the example environment file:
   ```bash
   cp .env.example .env
   ```
3. Update the `.env` file with your PostgreSQL connection URL and a random string for the `JWT_SECRET`:
   ```env
   DATABASE_URL=postgresql://postgres:<your_password>@localhost:5432/rideease
   JWT_SECRET=some_random_secret_string
   ```
4. Install the dependencies and start the dev server:
   ```bash
   npm install
   npm run dev
   ```
   The backend will be running on `http://localhost:5000`.

#### 3. Start the Frontend Application
1. Go to the frontend directory:
   ```bash
   cd rideease-fullstack/frontend
   ```
2. Install the frontend dependencies and run the React app:
   ```bash
   npm install
   npm start
   ```
   This will open the frontend app in your browser at `http://localhost:3000`.

---

## Testing Credentials

You can register a new user to test the rider flow. To test administrative features, use the default administrator credentials:

*   **Username:** `admin`
*   **Password:** `password` (Note: the SQL schema also references `Admin@123` depending on setup, but `password` is the default hashed value).
