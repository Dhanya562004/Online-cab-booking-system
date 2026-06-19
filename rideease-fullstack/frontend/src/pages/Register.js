import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api';
import './Auth.css';

export default function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: '', email: '', password: '', accountType: 'rider' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError('');
  };

  const handleSubmit = async () => {
    setLoading(true);
    try {
      await api.register(form);
      navigate('/login?registered=1');
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card">
        <button className="back-link" onClick={() => navigate('/')}>← Back to Home</button>
        <h2>Create Account</h2>
        <div className="form-group">
          <label>Username</label>
          <input name="username" value={form.username} onChange={handleChange} placeholder="Your name" />
        </div>
        <div className="form-group">
          <label>Email</label>
          <input type="email" name="email" value={form.email} onChange={handleChange} placeholder="Enter your email" />
        </div>
        <div className="form-group">
          <label>Password</label>
          <input type="password" name="password" value={form.password} onChange={handleChange} placeholder="Min 6 chars, 1 uppercase, 1 digit" />
        </div>
        <div className="form-group">
          <label>Account Type</label>
          <select name="accountType" value={form.accountType} onChange={handleChange}>
            <option value="rider">Rider (Book cabs)</option>
            <option value="driver">Driver (Offer rides)</option>
          </select>
        </div>
        {error && <p className="error-msg">{error}</p>}
        <button className="submit-btn" onClick={handleSubmit} disabled={loading}>
          {loading ? 'Registering...' : 'Register'}
        </button>
        <p className="auth-footer">
          Already have an account?{' '}
          <span className="auth-link" onClick={() => navigate('/login')}>Login here</span>
        </p>
      </div>
    </div>
  );
}
