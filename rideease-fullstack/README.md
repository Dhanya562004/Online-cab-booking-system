# RideEase Fullstack App Architecture & Reference

This directory holds the main application code, separated into the React frontend and Express backend.

## Architecture Overview

### Frontend (`/frontend`)
The frontend is a React application that communicates with the backend via JSON APIs.
*   **`src/api.js`**: Contains centralized fetch helper functions for auth, rides, and admin requests. Rather than scattered fetch calls, all HTTP calls route through here.
*   **`src/App.js`**: Main component that manages global user state (authenticated user object, JWT token) and handles routing/views.
*   **`src/pages/`**: Single-page views:
    *   `Home.js`: Initial view where riders can select pickup/drop-off locations and choose a cab type to estimate fares.
    *   `Payment.js`: Simulates card payment and triggers the API call to save the ride.
    *   `RideHistory.js`: Displays a list of all past rides completed by the logged-in user.
    *   `RateRide.js`: Simple rating view allowing users to leave star ratings and feedback for their rides.
    *   `AdminDashboard.js`: Dashboard restricted to admin users, displaying platform analytics (total users, total revenue, average ratings) and tables listing all users and rides in the system.

### Backend (`/backend`)
The backend is a Node.js/Express application connecting to a PostgreSQL database.
*   **`server.js`**: Entry point. Sets up Express, configures CORS and JSON parsing, mounts the API routes, and starts listening on port 5000.
*   **`db/index.js`**: Initializes the pg-pool connection using the `DATABASE_URL` environment variable.
*   **`middleware/auth.js`**: Custom middleware verifying the `Authorization` header's JWT. It decodes the token and attaches the user payload to `req.user`. It also includes a secondary `adminOnly` helper that checks the user's role before allowing access.
*   **`routes/`**: Route handlers:
    *   `auth.js`: User signup and login endpoints. Password hashes are generated and verified using `bcryptjs`.
    *   `rides.js`: Fetching and saving ride details, and rating specific rides.
    *   `admin.js`: Administrative metrics and full listings of platform data.

---

## Database Model

The database uses PostgreSQL and consists of three related tables:

1.  **`users`**: Stores credentials and roles.
    *   `id` (serial, primary key)
    *   `username` (unique, varchar)
    *   `email` (unique, varchar)
    *   `password` (hashed with bcrypt, varchar)
    *   `account_type` (defaults to 'rider', varchar)
    *   `role` (defaults to 'user', admin users set to 'admin')
2.  **`rides`**: Stores booking records.
    *   `id` (serial, primary key)
    *   `user_id` (foreign key referencing `users.id`)
    *   `pickup` & `drop_location` (varchar)
    *   `distance` (decimal)
    *   `fare` (integer)
    *   `status` (defaults to 'completed')
3.  **`ratings`**: Stores feedback for completed rides.
    *   `id` (serial, primary key)
    *   `ride_id` (foreign key referencing `rides.id`)
    *   `user_id` (foreign key referencing `users.id`)
    *   `stars` (integer, checked 1 to 5)
    *   `review` (text)

---

## API Documentation

All routes expect JSON payloads and return JSON responses. Protected routes require a valid JWT passed in the `Authorization` header (`Bearer <token>`).

### Authentication Routes (`/api/auth`)
*   **`POST /register`**: Register a new user account.
    *   Payload: `{ username, email, password }`
    *   Returns: `{ token, user: { id, username, email, account_type, role } }`
*   **`POST /login`**: Logs in an existing user.
    *   Payload: `{ username, password }`
    *   Returns: JWT token and user info block.

### Ride Routes (`/api/rides`)
*   **`POST /`** (Protected): Create and save a new completed ride.
    *   Payload: `{ pickup, drop_location, distance, duration_min, ride_type, fare, payment_method }`
*   **`GET /history`** (Protected): Fetch all past rides for the authenticated user.
*   **`POST /:id/rate`** (Protected): Submit a rating and optional text review for a completed ride.
    *   Payload: `{ stars, review }`

### Admin Routes (`/api/admin`)
*   **`GET /stats`** (Admin Only): Returns overall platform metrics (total users, total revenue, average system rating).
*   **`GET /users`** (Admin Only): Returns a list of all registered users in the database.
*   **`GET /rides`** (Admin Only): Returns a list of all booked rides in the database.
