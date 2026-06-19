import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Home.css';

export default function Home() {
  const navigate = useNavigate();
  return (
    <div className="home">
      <section className="hero">
        <div className="hero-car-icon">
          <svg viewBox="0 0 40 28" fill="none" xmlns="http://www.w3.org/2000/svg" width="40" height="28">
            <rect x="2" y="10" width="36" height="14" rx="4" fill="white"/>
            <rect x="8" y="4" width="24" height="10" rx="3" fill="white" opacity="0.8"/>
            <circle cx="10" cy="24" r="4" fill="#3b82f6" stroke="white" strokeWidth="2"/>
            <circle cx="30" cy="24" r="4" fill="#3b82f6" stroke="white" strokeWidth="2"/>
            <rect x="12" y="6" width="8" height="6" rx="1" fill="#3b82f6" opacity="0.5"/>
            <rect x="22" y="6" width="8" height="6" rx="1" fill="#3b82f6" opacity="0.5"/>
          </svg>
        </div>
        <h1>Your Ride, Your Way</h1>
        <p>Experience the future of transportation with RideEase. Safe, reliable, and eco-friendly rides at your fingertips.</p>
        <div className="hero-btns">
          <button className="btn-login" onClick={() => navigate('/login')}>
            🔑 LOGIN
          </button>
          <button className="btn-register" onClick={() => navigate('/register')}>
            👤 REGISTER
          </button>
        </div>
      </section>

      <section className="features">
        <h2>Why Choose RideEase?</h2>
        <p className="features-sub">Revolutionizing urban transportation with cutting-edge tech and uncompromising safety standards.</p>
        <div className="features-grid">
          <div className="feat">
            <div className="feat-icon">📍</div>
            <h3>Easy Booking</h3>
            <p>Book rides in seconds with our intuitive interface.</p>
          </div>
          <div className="feat">
            <div className="feat-icon">🔒</div>
            <h3>Safe & Secure</h3>
            <p>Verified drivers and secure payments.</p>
          </div>
          <div className="feat">
            <div className="feat-icon">⏱</div>
            <h3>Quick Rides</h3>
            <p>Get matched with drivers instantly.</p>
          </div>
          <div className="feat">
            <div className="feat-icon">⭐</div>
            <h3>Top Rated</h3>
            <p>Consistently rated 5 stars by our riders.</p>
          </div>
          <div className="feat">
            <div className="feat-icon">🌿</div>
            <h3>Eco-Friendly</h3>
            <p>Carbon offset on every ride you take.</p>
          </div>
          <div className="feat">
            <div className="feat-icon">👥</div>
            <h3>Community</h3>
            <p>Join millions of happy RideEase users.</p>
          </div>
        </div>
      </section>
    </div>
  );
}
