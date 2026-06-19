import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api';
import './RideHistory.css';

export default function RideHistory() {
  const navigate = useNavigate();
  const [rides, setRides] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    api.getRideHistory()
      .then(data => setRides(data.rides))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="history-page">
      <header className="history-header">
        <button className="back-link" onClick={() => navigate('/dashboard')}>← Dashboard</button>
        <h1>My Ride History</h1>
      </header>

      {loading && <p className="loading-msg">Loading your rides...</p>}
      {error && <p className="error-msg">{error}</p>}

      {!loading && rides.length === 0 && (
        <div className="empty-state">
          <p>No rides yet. Book your first ride!</p>
          <button className="submit-btn" onClick={() => navigate('/dashboard')}>Book a Ride</button>
        </div>
      )}

      <div className="rides-list">
        {rides.map(ride => (
          <div key={ride.id} className="ride-card">
            <div className="ride-card-header">
              <span className="ride-date">{new Date(ride.created_at).toLocaleDateString('en-IN', { day:'numeric', month:'short', year:'numeric', hour:'2-digit', minute:'2-digit' })}</span>
              <span className={`ride-status status-${ride.status}`}>{ride.status}</span>
            </div>
            <div className="ride-route">
              <div className="route-point pickup-point">
                <span className="dot green-dot"></span>
                <span>{ride.pickup}</span>
              </div>
              <div className="route-point drop-point">
                <span className="dot red-dot"></span>
                <span>{ride.drop_location}</span>
              </div>
            </div>
            <div className="ride-card-footer">
              <div className="ride-meta">
                <span>{ride.ride_type}</span>
                <span>{ride.distance} km</span>
                <span>{ride.duration_min} min</span>
                <span>{ride.payment_method}</span>
              </div>
              <span className="ride-fare">₹{ride.fare}</span>
            </div>
            {ride.stars ? (
              <div className="ride-rating">
                {'★'.repeat(ride.stars)}{'☆'.repeat(5 - ride.stars)}
                {ride.review && <span className="ride-review"> "{ride.review}"</span>}
              </div>
            ) : (
              <button className="rate-btn" onClick={() => navigate(`/rate/${ride.id}`)}>Rate this ride</button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
