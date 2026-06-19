import React, { useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { api } from '../api';
import './Auth.css';

export default function Login() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const registered = searchParams.get('registered') === '1';

  const [form, setForm] = useState({ username: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError('');
  };

  const handleSubmit = async () => {
    if (!form.username || !form.password) { setError('Please fill in all fields.'); return; }
    setLoading(true);
    try {
      const data = await api.login(form);
      localStorage.setItem('re_token', data.token);
      localStorage.setItem('re_current_user', JSON.stringify(data.user));
      if (data.user.role === 'admin') navigate('/admin');
      else navigate('/dashboard');
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
        <h2>Login</h2>
        {registered && <div className="success-banner">Registration successful! Please log in.</div>}
        <div className="form-group">
          <label>Username</label>
          <input name="username" value={form.username} onChange={handleChange} placeholder="Username" />
        </div>
        <div className="form-group">
          <label>Password</label>
          <input type="password" name="password" value={form.password} onChange={handleChange} placeholder="Password" />
        </div>
        {error && <p className="error-msg">{error}</p>}
        <button className="submit-btn submit-btn-blue" onClick={handleSubmit} disabled={loading}>
          {loading ? 'Logging in...' : 'Login'}
        </button>
        <p className="auth-footer">
          Don't have an account?{' '}
          <span className="auth-link" onClick={() => navigate('/register')}>Register here</span>
        </p>
      </div>
    </div>
  );
}
