const express = require('express');
const router = express.Router();
const pool = require('../db');
const { authMiddleware, adminMiddleware } = require('../middleware/auth');

// All admin routes require auth + admin role
router.use(authMiddleware, adminMiddleware);

// GET /api/admin/stats — summary numbers
router.get('/stats', async (req, res) => {
  try {
    const users = await pool.query('SELECT COUNT(*) FROM users WHERE role != $1', ['admin']);
    const rides = await pool.query('SELECT COUNT(*), COALESCE(SUM(fare),0) AS total_revenue FROM rides');
    const avgRating = await pool.query('SELECT ROUND(AVG(stars)::numeric, 1) AS avg FROM ratings');

    res.json({
      totalUsers: parseInt(users.rows[0].count),
      totalRides: parseInt(rides.rows[0].count),
      totalRevenue: parseInt(rides.rows[0].total_revenue),
      avgRating: avgRating.rows[0].avg || '0.0'
    });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to fetch stats.' });
  }
});

// GET /api/admin/users — all users
router.get('/users', async (req, res) => {
  try {
    const result = await pool.query(
      `SELECT u.id, u.username, u.email, u.account_type, u.role, u.created_at,
              COUNT(r.id) AS total_rides
       FROM users u
       LEFT JOIN rides r ON r.user_id = u.id
       GROUP BY u.id
       ORDER BY u.created_at DESC`
    );
    res.json({ users: result.rows });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to fetch users.' });
  }
});

// GET /api/admin/rides — all rides
router.get('/rides', async (req, res) => {
  try {
    const result = await pool.query(
      `SELECT r.*, u.username,
              rt.stars, rt.review
       FROM rides r
       JOIN users u ON u.id = r.user_id
       LEFT JOIN ratings rt ON rt.ride_id = r.id
       ORDER BY r.created_at DESC
       LIMIT 100`
    );
    res.json({ rides: result.rows });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Failed to fetch rides.' });
  }
});

module.exports = router;
