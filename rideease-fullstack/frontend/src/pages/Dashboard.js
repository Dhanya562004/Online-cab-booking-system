import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUser, logout } from '../api';
import './Dashboard.css';

function haversine(lat1, lon1, lat2, lon2) {
  const R = 6371;
  const dLat = ((lat2 - lat1) * Math.PI) / 180;
  const dLon = ((lon2 - lon1) * Math.PI) / 180;
  const a =
    Math.sin(dLat / 2) ** 2 +
    Math.cos((lat1 * Math.PI) / 180) * Math.cos((lat2 * Math.PI) / 180) *
    Math.sin(dLon / 2) ** 2;
  return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
}

const RATE = { Standard: 16, Premium: 24, SUV: 20, Eco: 12 };
const SPEED_KMH = 22;

export default function Dashboard() {
  const navigate = useNavigate();
  const mapContainerRef = useRef(null);
  const mapRef = useRef(null);
  const pickupMarkerRef = useRef(null);
  const dropMarkerRef = useRef(null);
  const routeLayerRef = useRef(null);
  const mapModeRef = useRef(null);

  const [mapMode, setMapMode] = useState(null);
  const [pickup, setPickup] = useState({ name: '', lat: null, lng: null });
  const [drop, setDrop] = useState({ name: '', lat: null, lng: null });
  const [rideType, setRideType] = useState('Standard');
  const [fareInfo, setFareInfo] = useState(null);

  useEffect(() => { mapModeRef.current = mapMode; }, [mapMode]);

  useEffect(() => {
    if (mapRef.current) return;

    const loadLeaflet = () => {
      if (!window.L) return setTimeout(loadLeaflet, 100);

      const L = window.L;
      const map = L.map(mapContainerRef.current, { zoomControl: true }).setView([12.9716, 77.5946], 11);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
        maxZoom: 19,
      }).addTo(map);

      map.on('click', async (e) => {
        const mode = mapModeRef.current;
        if (!mode) return;
        const { lat, lng } = e.latlng;
        const name = await reverseGeocode(lat, lng);

        if (mode === 'pickup') {
          setPickup({ name, lat, lng });
          if (pickupMarkerRef.current) {
            pickupMarkerRef.current.setLatLng([lat, lng]);
          } else {
            pickupMarkerRef.current = L.circleMarker([lat, lng], {
              radius: 10, fillColor: '#22c55e', color: '#fff',
              weight: 3, opacity: 1, fillOpacity: 1,
            }).addTo(map).bindPopup('Pickup').openPopup();
          }
        } else {
          setDrop({ name, lat, lng });
          if (dropMarkerRef.current) {
            dropMarkerRef.current.setLatLng([lat, lng]);
          } else {
            dropMarkerRef.current = L.circleMarker([lat, lng], {
              radius: 10, fillColor: '#ef4444', color: '#fff',
              weight: 3, opacity: 1, fillOpacity: 1,
            }).addTo(map).bindPopup('Drop').openPopup();
          }
        }
        setMapMode(null);
      });

      mapRef.current = map;
    };

    if (window.L) {
      loadLeaflet();
    } else {
      if (!document.getElementById('leaflet-css')) {
        const link = document.createElement('link');
        link.id = 'leaflet-css';
        link.rel = 'stylesheet';
        link.href = 'https://unpkg.com/leaflet@1.9.4/dist/leaflet.css';
        document.head.appendChild(link);
      }
      const script = document.createElement('script');
      script.src = 'https://unpkg.com/leaflet@1.9.4/dist/leaflet.js';
      script.onload = loadLeaflet;
      document.head.appendChild(script);
    }
  }, []);

  const reverseGeocode = async (lat, lng) => {
    try {
      const res = await fetch(`https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${lng}&format=json`);
      const data = await res.json();
      const a = data.address || {};
      return a.neighbourhood || a.suburb || a.village || a.town || a.city || a.county || `${lat.toFixed(4)}, ${lng.toFixed(4)}`;
    } catch {
      return `${lat.toFixed(4)}, ${lng.toFixed(4)}`;
    }
  };

  const drawRoute = (p, d) => {
    const L = window.L;
    const map = mapRef.current;
    if (!map || !L) return;
    if (routeLayerRef.current) { map.removeLayer(routeLayerRef.current); routeLayerRef.current = null; }
    const line = L.polyline([[p.lat, p.lng], [d.lat, d.lng]], {
      color: '#3b82f6', weight: 4, opacity: 0.85,
    }).addTo(map);
    routeLayerRef.current = line;
    map.fitBounds(line.getBounds(), { padding: [50, 50] });
  };

  const handleClearAll = () => {
    setPickup({ name: '', lat: null, lng: null });
    setDrop({ name: '', lat: null, lng: null });
    setFareInfo(null);
    setMapMode(null);
    const map = mapRef.current;
    if (map) {
      if (pickupMarkerRef.current) { map.removeLayer(pickupMarkerRef.current); pickupMarkerRef.current = null; }
      if (dropMarkerRef.current) { map.removeLayer(dropMarkerRef.current); dropMarkerRef.current = null; }
      if (routeLayerRef.current) { map.removeLayer(routeLayerRef.current); routeLayerRef.current = null; }
      map.setView([12.9716, 77.5946], 11);
    }
  };

  const handleCalculateFare = () => {
    if (!pickup.name.trim() || !drop.name.trim()) {
      alert('Please enter both pickup and drop locations.');
      return;
    }
    let distance;
    if (pickup.lat && drop.lat) {
      distance = haversine(pickup.lat, pickup.lng, drop.lat, drop.lng);
      drawRoute(pickup, drop);
    } else {
      distance = Math.random() * 40 + 8;
    }
    const durationMin = Math.round((distance / SPEED_KMH) * 60);
    const fare = Math.round(distance * RATE[rideType]);
    setFareInfo({ distance: distance.toFixed(2), durationMin, fare });
  };

  const handleConfirmBooking = () => {
    if (!fareInfo) return;
    localStorage.setItem('re_booking', JSON.stringify({
      pickup: pickup.name, drop: drop.name,
      distance: fareInfo.distance, durationMin: fareInfo.durationMin,
      rideType, fare: fareInfo.fare,
    }));
    navigate('/payment');
  };

  const user = getUser();

  return (
    <div className="dashboard">
      <header className="dash-header">
        <h1>Book a Ride</h1>
        <div className="dash-header-right">
          {user?.username && <span className="user-greeting">Hi, {user.username}</span>}
          <button className="logout-btn" onClick={() => navigate('/history')} style={{marginRight:'8px'}}>My Rides</button>
          <button className="logout-btn" onClick={() => { logout(); navigate('/'); }}>Logout</button>
        </div>
      </header>

      <div className="dash-body">
        <div className="map-section">
          <div className="map-controls">
            <button className={`map-btn ${mapMode === 'pickup' ? 'map-btn-active' : ''}`}
              onClick={() => setMapMode(mapMode === 'pickup' ? null : 'pickup')}>Set Pickup on Map</button>
            <button className={`map-btn ${mapMode === 'drop' ? 'map-btn-active' : ''}`}
              onClick={() => setMapMode(mapMode === 'drop' ? null : 'drop')}>Set Drop on Map</button>
            <button className="map-btn" onClick={handleClearAll}>Clear All</button>
          </div>
          <div className={`map-wrap ${mapMode ? 'map-crosshair' : ''}`} ref={mapContainerRef} />
          <div className="map-attr">© <a href="https://www.openstreetmap.org/" target="_blank" rel="noreferrer">OpenStreetMap</a> contributors</div>
        </div>

        <div className="ride-panel">
          <h2>Ride Details</h2>
          <div className="form-group">
            <label>Pickup Location</label>
            <input type="text" value={pickup.name}
              onChange={e => { setPickup({ ...pickup, name: e.target.value }); setFareInfo(null); }}
              placeholder="Enter pickup location"
              className={mapMode === 'pickup' ? 'input-highlight' : ''} />
          </div>
          <div className="form-group">
            <label>Drop Location</label>
            <input type="text" value={drop.name}
              onChange={e => { setDrop({ ...drop, name: e.target.value }); setFareInfo(null); }}
              placeholder="Enter drop location"
              className={mapMode === 'drop' ? 'input-highlight' : ''} />
          </div>
          <div className="form-group">
            <label>Ride Type</label>
            <select value={rideType} onChange={e => { setRideType(e.target.value); setFareInfo(null); }}>
              <option value="Standard">Standard</option>
              <option value="Premium">Premium</option>
              <option value="SUV">SUV</option>
              <option value="Eco">Eco</option>
            </select>
          </div>
          <button className="calc-btn" onClick={handleCalculateFare}>Calculate Fare</button>

          {fareInfo && (
            <div className="fare-box">
              <h3>Fare Details</h3>
              <div className="fare-row"><span>Distance:</span><span>{fareInfo.distance} km</span></div>
              <div className="fare-row"><span>Estimated Duration:</span><span>{fareInfo.durationMin} minutes</span></div>
              <div className="fare-row"><span>Ride Type:</span><span>{rideType}</span></div>
              <div className="fare-row fare-total"><span>Total Fare:</span><span>₹{fareInfo.fare}</span></div>
              <button className="confirm-btn" onClick={handleConfirmBooking}>Confirm Booking</button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
