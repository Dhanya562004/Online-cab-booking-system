const express = require('express');
const router = express.Router();
const pool = require('../db');
const { authMiddleware } = require('../middleware/auth');

// POST /api/rides — save a completed booking
router.post('/', authMiddleware, async (req, res) => {
  const { pickup, drop, distance, durationMin, rideType, fare, paymentMethod } = req.body;
  const userId = req.user.id;

  if (!pickup || !drop || !fare) {
    return res.status(400).json({ error: 'Missing required ride details.' });
  }

  try {
    const result = await pool.query(
      `INSERT INTO rides (user_id, pickup, drop_location, distance, duration_min, ride_type, fare, payment_method, status)
       VALUES ($1,$2,$3,$4,$5,$6,$7,$8,'completed') RETURNING *`,
      [userId, pickup, drop, distance, durationMin, rideType, fare, paymentMethod || 'card']
    );
    res.status(201).json({ ride: result.rows[0] });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to save ride.' });
  }
});

// GET /api/rides/history — get current user's ride history
router.get('/history', authMiddleware, async (req, res) => {
  const userId = req.user.id;
  try {
    const result = await pool.query(
      `SELECT r.*, rt.stars, rt.review
       FROM rides r
       LEFT JOIN ratings rt ON rt.ride_id = r.id
       WHERE r.user_id = $1
       ORDER BY r.created_at DESC`,
      [userId]
    );
    res.json({ rides: result.rows });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to fetch ride history.' });
  }
});

// POST /api/rides/:id/rate — rate a ride
router.post('/:id/rate', authMiddleware, async (req, res) => {
  const rideId = req.params.id;
  const userId = req.user.id;
  const { stars, review } = req.body;

  if (!stars || stars < 1 || stars > 5) {
    return res.status(400).json({ error: 'Stars must be between 1 and 5.' });
  }

  try {
    const rideCheck = await pool.query('SELECT id FROM rides WHERE id=$1 AND user_id=$2', [rideId, userId]);
    if (rideCheck.rows.length === 0) {
      return res.status(404).json({ error: 'Ride not found.' });
    }

    const existing = await pool.query('SELECT id FROM ratings WHERE ride_id=$1', [rideId]);
    if (existing.rows.length > 0) {
      return res.status(409).json({ error: 'You have already rated this ride.' });
    }

    const result = await pool.query(
      'INSERT INTO ratings (ride_id, user_id, stars, review) VALUES ($1,$2,$3,$4) RETURNING *',
      [rideId, userId, stars, review || '']
    );
    res.status(201).json({ rating: result.rows[0] });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to save rating.' });
  }
});

module.exports = router;
