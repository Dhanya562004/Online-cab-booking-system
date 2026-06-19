import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { api } from '../api';
import './RateRide.css';

export default function RateRide() {
  const { rideId } = useParams();
  const navigate = useNavigate();
  const [stars, setStars] = useState(0);
  const [hovered, setHovered] = useState(0);
  const [review, setReview] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    if (!stars) { setError('Please select a star rating.'); return; }
    setLoading(true);
    try {
      await api.rateRide(rideId, { stars, review });
      setSuccess(true);
      setTimeout(() => navigate('/history'), 2500);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (success) {
    return (
      <div className="rate-page">
        <div className="rate-card">
          <div className="success-icon">🎉</div>
          <h2>Thanks for your feedback!</h2>
          <p>Redirecting to your ride history...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="rate-page">
      <div className="rate-card">
        <h2>Rate Your Ride</h2>
        <p className="rate-subtitle">How was your experience?</p>

        <div className="stars-row">
          {[1,2,3,4,5].map(s => (
            <span
              key={s}
              className={`star ${s <= (hovered || stars) ? 'star-filled' : 'star-empty'}`}
              onClick={() => { setStars(s); setError(''); }}
              onMouseEnter={() => setHovered(s)}
              onMouseLeave={() => setHovered(0)}
            >★</span>
          ))}
        </div>
        {stars > 0 && (
          <p className="stars-label">
            {['','Poor','Fair','Good','Very Good','Excellent'][stars]}
          </p>
        )}

        <textarea
          className="review-input"
          placeholder="Write a review (optional)..."
          value={review}
          onChange={e => setReview(e.target.value)}
          rows={4}
        />

        {error && <p className="error-msg">{error}</p>}

        <button className="submit-btn" onClick={handleSubmit} disabled={loading}>
          {loading ? 'Submitting...' : 'Submit Rating'}
        </button>
        <button className="skip-btn" onClick={() => navigate('/history')}>Skip</button>
      </div>
    </div>
  );
}
