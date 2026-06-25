# Online Cab Booking System

## Overview
RideEase is a full-stack online cab booking system that makes it easy for riders to book rides and for admins to manage the entire platform. I built the frontend using React.js and the backend using Spring Boot, with PostgreSQL handling all the database storage. 

With RideEase, riders can pin their pickup and drop-off points directly on an interactive map, get instant fare estimates based on the vehicle type they choose, go through a simulated checkout, and view their ride history. There's also an admin dashboard built-in so you can track total revenue, manage users, and view booking logs in real-time.

## Features
Here are the main features of the application:
*   **Interactive Map Booking:** Pick your pickup and drop-off locations by pinning them on an interactive map (built using Leaflet and OpenStreetMap).
*   **Automatic Address Finder:** The app automatically converts your map pins into readable addresses so you know exactly where you're booking.
*   **Instant Fare Calculator:** Calculates fares on the spot using the Haversine formula. You can compare rates between Eco, Standard, SUV, and Premium options.
*   **Authentication & Roles:** Safe signup and login with distinct roles for Riders and Admins.
*   **Mock Checkout:** A simulated checkout flow to experience booking confirmation.
*   **Ride History & Star Ratings:** Riders can look back at their past trips and leave reviews or star ratings for their experience.
*   **Admin Dashboard:** A dedicated space for admins to check platform stats, monitor total revenue, see user metrics, and view booking history.

## Technologies Used
This project is built using:
*   **Frontend:** React.js, HTML, CSS, JavaScript
*   **Backend:** Spring Boot
*   **Database:** PostgreSQL

## Project Structure
Here's how the repository is structured:
```
online-cab-booking/
├── rideease-fullstack/
│   ├── backend/                         # Spring Boot Backend API Server
│   │   ├── src/main/java/com/rideease/  # Source code (Controllers, Services, Models)
│   │   └── src/main/resources/          # Application configuration properties
│   │
│   └── frontend/                        # React.js Client Application
│       ├── public/                      # Static assets and index.html
│       └── src/                         # React components, routing, and views
│
└── README.md                            # Main project documentation
```

## Installation & Setup

### Prerequisites
Before you get started, make sure you have these installed on your machine:
*   Node.js (v16.x or newer)
*   Java Development Kit (JDK 17 or newer)
*   PostgreSQL

### Step 1: Set up the Database
1. Open your PostgreSQL terminal or client (like pgAdmin) and create a database:
   ```sql
   CREATE DATABASE rideease;
   ```
2. Set up your tables and configure your database settings as described below.

### Step 2: Set up the Backend (Spring Boot)
1. Go to the backend folder:
   ```bash
   cd rideease-fullstack/backend
   ```
2. Open `src/main/resources/application.properties` and update the database configuration with your PostgreSQL credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/rideease
   spring.datasource.username=your_postgres_username
   spring.datasource.password=your_postgres_password
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   The backend API should now be running at `http://localhost:8080`.

### Step 3: Set up the Frontend (React)
1. Open a new terminal window and head to the frontend folder:
   ```bash
   cd rideease-fullstack/frontend
   ```
2. Install the npm packages:
   ```bash
   npm install
   ```
3. Start the local development server:
   ```bash
   npm start
   ```
   This will open the application in your default browser at `http://localhost:3000`.

## Usage
*   **As a Rider:** Create a new account or log in. Pin your start and end points on the map, choose your vehicle type, check the estimated fare, and complete the simulated checkout. You can also view your trip history and rate past rides.
*   **As an Admin:** Log in with administrative access to view system analytics, track revenue, manage users, and view ride logs.

## Future Improvements
There are a few key features I'd like to add in the future:
*   **Real-time Cab Tracking:** Adding WebSockets to show driver locations moving on the map live.
*   **Real Payments:** Integrating a test sandbox for Stripe or PayPal to handle actual mock transactions.
*   **Driver Dashboard:** Creating a dedicated portal for drivers to accept or decline rides.
*   **Discounts & Promos:** Adding a coupon verification module during checkout.

## Challenges & Learning
*   **Synchronizing Map State in React:** Managing map clicks, route calculations, and marker states within React's render cycles was tricky. I learned how to use refs and hook into Leaflet's lifecycle without breaking React's virtual DOM.
*   **Spring Security & JWT:** Setting up role-based, stateless authorization with Spring Security to protect administrative endpoints was a great learning experience.
*   **Relational Database Queries:** Writing JPA queries and mapping entities to fetch aggregates (like total revenue and average ratings) taught me a lot about relational database design and optimization.

## Contributing
I'd love to make this project even better. If you have any suggestions or find bugs, feel free to contribute!
1. Fork this repository.
2. Create a new branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to your branch: `git push origin feature-name`.
5. Open a Pull Request.

## Author
Dhanya K

GitHub: @Dhanya562004
Email: kdhanya762@gmail.com
