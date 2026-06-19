import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api, logout } from '../api';
import './AdminDashboard.css';

export default function AdminDashboard() {
  const navigate = useNavigate();
  const [tab, setTab] = useState('stats');
  const [stats, setStats] = useState(null);
  const [users, setUsers] = useState([]);
  const [rides, setRides] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    setLoading(true);
    setError('');
    if (tab === 'stats') {
      api.getAdminStats().then(setStats).catch(e => setError(e.message)).finally(() => setLoading(false));
    } else if (tab === 'users') {
      api.getAdminUsers().then(d => setUsers(d.users)).catch(e => setError(e.message)).finally(() => setLoading(false));
    } else {
      api.getAdminRides().then(d => setRides(d.rides)).catch(e => setError(e.message)).finally(() => setLoading(false));
    }
  }, [tab]);

  return (
    <div className="admin-page">
      <header className="admin-header">
        <h1>Admin Dashboard</h1>
        <button className="logout-btn" onClick={() => { logout(); navigate('/'); }}>Logout</button>
      </header>

      <div className="admin-tabs">
        {['stats','users','rides'].map(t => (
          <button key={t} className={`tab-btn ${tab === t ? 'tab-active' : ''}`} onClick={() => setTab(t)}>
            {t.charAt(0).toUpperCase() + t.slice(1)}
          </button>
        ))}
      </div>

      {loading && <p className="loading-msg">Loading...</p>}
      {error && <p className="error-msg">{error}</p>}

      {tab === 'stats' && stats && !loading && (
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-value">{stats.totalUsers}</div>
            <div className="stat-label">Total Users</div>
          </div>
          <div className="stat-card">
            <div className="stat-value">{stats.totalRides}</div>
            <div className="stat-label">Total Rides</div>
          </div>
          <div className="stat-card">
            <div className="stat-value">₹{stats.totalRevenue}</div>
            <div className="stat-label">Total Revenue</div>
          </div>
          <div className="stat-card">
            <div className="stat-value">{stats.avgRating} ★</div>
            <div className="stat-label">Avg Rating</div>
          </div>
        </div>
      )}

      {tab === 'users' && !loading && (
        <div className="admin-table-wrap">
          <table className="admin-table">
            <thead>
              <tr>
                <th>ID</th><th>Username</th><th>Email</th><th>Type</th><th>Role</th><th>Rides</th><th>Joined</th>
              </tr>
            </thead>
            <tbody>
              {users.map(u => (
                <tr key={u.id}>
                  <td>{u.id}</td>
                  <td>{u.username}</td>
                  <td>{u.email}</td>
                  <td><span className="badge">{u.account_type}</span></td>
                  <td><span className={`badge ${u.role === 'admin' ? 'badge-admin' : ''}`}>{u.role}</span></td>
                  <td>{u.total_rides}</td>
                  <td>{new Date(u.created_at).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
          {users.length === 0 && <p className="loading-msg">No users found.</p>}
        </div>
      )}

      {tab === 'rides' && !loading && (
        <div className="admin-table-wrap">
          <table className="admin-table">
            <thead>
              <tr>
                <th>ID</th><th>User</th><th>Pickup</th><th>Drop</th><th>Type</th><th>Fare</th><th>Rating</th><th>Date</th>
              </tr>
            </thead>
            <tbody>
              {rides.map(r => (
                <tr key={r.id}>
                  <td>{r.id}</td>
                  <td>{r.username}</td>
                  <td className="td-truncate">{r.pickup}</td>
                  <td className="td-truncate">{r.drop_location}</td>
                  <td><span className="badge">{r.ride_type}</span></td>
                  <td>₹{r.fare}</td>
                  <td>{r.stars ? '★'.repeat(r.stars) : '—'}</td>
                  <td>{new Date(r.created_at).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
          {rides.length === 0 && <p className="loading-msg">No rides found.</p>}
        </div>
      )}
    </div>
  );
}
