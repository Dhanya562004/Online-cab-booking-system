const BASE = '/api';

export function getToken() {
  return localStorage.getItem('re_token');
}

export function getUser() {
  const u = localStorage.getItem('re_current_user');
  return u ? JSON.parse(u) : null;
}

export function logout() {
  localStorage.removeItem('re_token');
  localStorage.removeItem('re_current_user');
}

async function request(path, options = {}) {
  const token = getToken();
  const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const res = await fetch(BASE + path, { ...options, headers });
  const data = await res.json();
  if (!res.ok) throw new Error(data.error || 'Request failed');
  return data;
}

export const api = {
  register: (body) => request('/auth/register', { method: 'POST', body: JSON.stringify(body) }),
  login: (body) => request('/auth/login', { method: 'POST', body: JSON.stringify(body) }),
  saveRide: (body) => request('/rides', { method: 'POST', body: JSON.stringify(body) }),
  getRideHistory: () => request('/rides/history'),
  rateRide: (id, body) => request(`/rides/${id}/rate`, { method: 'POST', body: JSON.stringify(body) }),
  getAdminStats: () => request('/admin/stats'),
  getAdminUsers: () => request('/admin/users'),
  getAdminRides: () => request('/admin/rides'),
};
