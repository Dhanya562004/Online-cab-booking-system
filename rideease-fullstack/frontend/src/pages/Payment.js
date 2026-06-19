import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api';
import './Payment.css';

export default function Payment() {
  const navigate = useNavigate();
  const [booking, setBooking] = useState(null);
  const [payMethod, setPayMethod] = useState('credit');
  const [card, setCard] = useState({ cardNumber: '', expiry: '', cvv: '', cardholderName: '' });
  const [upiId, setUpiId] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [savedRideId, setSavedRideId] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const data = localStorage.getItem('re_booking');
    if (data) setBooking(JSON.parse(data));
    else navigate('/dashboard');
  }, [navigate]);

  const handleCardChange = e => {
    let val = e.target.value;
    if (e.target.name === 'cardNumber') val = val.replace(/\D/g,'').slice(0,16).replace(/(.{4})/g,'$1 ').trim();
    if (e.target.name === 'expiry') {
      val = val.replace(/\D/g,'').slice(0,4);
      if (val.length > 2) val = val.slice(0,2) + '/' + val.slice(2);
    }
    if (e.target.name === 'cvv') val = val.replace(/\D/g,'').slice(0,3);
    setCard({ ...card, [e.target.name]: val });
    setError('');
  };

  const validate = () => {
    if (payMethod === 'credit' || payMethod === 'debit') {
      if (card.cardNumber.replace(/\s/g,'').length !== 16) return 'Enter a valid 16-digit card number.';
      if (!card.expiry.match(/^\d{2}\/\d{2}$/)) return 'Enter expiry as MM/YY.';
      if (card.cvv.length !== 3) return 'Enter a valid 3-digit CVV.';
      if (!card.cardholderName.trim()) return 'Enter cardholder name.';
    }
    if (payMethod === 'upi' && !upiId.includes('@')) return 'Enter a valid UPI ID (e.g. name@upi).';
    return '';
  };

  const handlePay = async () => {
    const err = validate();
    if (err) { setError(err); return; }
    setLoading(true);
    try {
      const result = await api.saveRide({
        pickup: booking.pickup,
        drop: booking.drop,
        distance: booking.distance,
        durationMin: booking.durationMin,
        rideType: booking.rideType,
        fare: booking.fare,
        paymentMethod: payMethod,
      });
      setSavedRideId(result.ride.id);
      setSuccess(true);
      localStorage.removeItem('re_booking');
      setTimeout(() => navigate(`/rate/${result.ride.id}`), 3000);
    } catch (err) {
      setError('Payment failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  if (!booking) return null;

  if (success) {
    return (
      <div className="payment-page">
        <div className="success-card">
          <div className="success-icon">✅</div>
          <h2>Payment Successful!</h2>
          <p>Your ride has been booked. Taking you to rate your ride...</p>
          <div className="success-summary">
            <p><strong>{booking.pickup}</strong> → <strong>{booking.drop}</strong></p>
            <p>Amount paid: <strong>₹{booking.fare}</strong></p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="payment-page">
      <div className="payment-container">
        <h1>Payment</h1>
        <div className="summary-card">
          <h3>Booking Summary</h3>
          <div className="sum-row"><span>Pickup:</span><span>{booking.pickup}</span></div>
          <div className="sum-row"><span>Drop:</span><span>{booking.drop}</span></div>
          <div className="sum-row"><span>Distance:</span><span>{booking.distance} km</span></div>
          <div className="sum-row"><span>Ride Type:</span><span>{booking.rideType}</span></div>
          <div className="sum-row sum-total"><span>Total Amount:</span><span>₹{booking.fare}</span></div>
        </div>

        <div className="pay-section">
          <h3>Payment Method</h3>
          <div className="radio-group">
            {[['credit','Credit Card'],['debit','Debit Card'],['upi','UPI'],['wallet','Wallet']].map(([val,label]) => (
              <label key={val} className="radio-label">
                <input type="radio" name="pay" value={val} checked={payMethod===val}
                  onChange={() => { setPayMethod(val); setError(''); }} />
                <span>{label}</span>
              </label>
            ))}
          </div>

          {(payMethod === 'credit' || payMethod === 'debit') && (
            <div className="card-fields">
              <h4>{payMethod === 'credit' ? 'Credit' : 'Debit'} Card Details</h4>
              <input name="cardNumber" value={card.cardNumber} onChange={handleCardChange} placeholder="Card Number" />
              <div className="card-row">
                <input name="expiry" value={card.expiry} onChange={handleCardChange} placeholder="MM/YY" />
                <input name="cvv" value={card.cvv} onChange={handleCardChange} placeholder="CVV" />
              </div>
              <input name="cardholderName" value={card.cardholderName}
                onChange={e => { setCard({...card, cardholderName: e.target.value}); setError(''); }}
                placeholder="Cardholder Name" />
            </div>
          )}

          {payMethod === 'upi' && (
            <div className="upi-field">
              <input value={upiId} onChange={e => { setUpiId(e.target.value); setError(''); }}
                placeholder="Enter UPI ID (e.g. name@upi)" />
            </div>
          )}

          {payMethod === 'wallet' && (
            <div className="wallet-info">💳 Wallet balance will be deducted automatically.</div>
          )}
        </div>

        {error && <p className="error-msg">{error}</p>}
        <button className="pay-btn" onClick={handlePay} disabled={loading}>
          {loading ? 'Processing...' : `Pay ₹${booking.fare}`}
        </button>
        <button className="back-btn" onClick={() => navigate('/dashboard')}>← Back to Dashboard</button>
      </div>
    </div>
  );
}
