-- Run this file in PostgreSQL to create all tables
-- psql -U postgres -d rideease -f schema.sql

CREATE DATABASE rideease;
\c rideease;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  account_type VARCHAR(20) DEFAULT 'rider',
  role VARCHAR(20) DEFAULT 'user',
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE rides (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
  pickup VARCHAR(255) NOT NULL,
  drop_location VARCHAR(255) NOT NULL,
  distance DECIMAL(10,2),
  duration_min INTEGER,
  ride_type VARCHAR(50),
  fare INTEGER,
  payment_method VARCHAR(50),
  status VARCHAR(50) DEFAULT 'completed',
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE ratings (
  id SERIAL PRIMARY KEY,
  ride_id INTEGER REFERENCES rides(id) ON DELETE CASCADE,
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
  stars INTEGER CHECK (stars >= 1 AND stars <= 5),
  review TEXT,
  created_at TIMESTAMP DEFAULT NOW()
);

-- Insert a default admin user (password: Admin@123)
INSERT INTO users (username, email, password, account_type, role)
VALUES ('admin', 'admin@rideease.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'rider', 'admin');
