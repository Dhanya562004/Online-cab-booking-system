import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Payment from './pages/Payment';
import RideHistory from './pages/RideHistory';
import RateRide from './pages/RateRide';
import AdminDashboard from './pages/AdminDashboard';
import { getToken, getUser } from './api';

function PrivateRoute({ children }) {
  return getToken() ? children : <Navigate to="/login" />;
}

function AdminRoute({ children }) {
  const user = getUser();
  return getToken() && user?.role === 'admin' ? children : <Navigate to="/dashboard" />;
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/payment" element={<PrivateRoute><Payment /></PrivateRoute>} />
        <Route path="/history" element={<PrivateRoute><RideHistory /></PrivateRoute>} />
        <Route path="/rate/:rideId" element={<PrivateRoute><RateRide /></PrivateRoute>} />
        <Route path="/admin" element={<AdminRoute><AdminDashboard /></AdminRoute>} />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  );
}
