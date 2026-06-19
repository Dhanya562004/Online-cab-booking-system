const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const pool = require('../db');

// POST /api/auth/register
router.post('/register', async (req, res) => {
  const { username, email, password, accountType } = req.body;

  if (!username || !email || !password) {
    return res.status(400).json({ error: 'All fields are required.' });
  }
  if (password.length < 6) {
    return res.status(400).json({ error: 'Password must be at least 6 characters.' });
  }
  if (!/[A-Z]/.test(password)) {
    return res.status(400).json({ error: 'Password must contain at least 1 uppercase letter.' });
  }
  if (!/\d/.test(password)) {
    return res.status(400).json({ error: 'Password must contain at least 1 digit.' });
  }

  try {
    const existing = await pool.query('SELECT id FROM users WHERE username=$1 OR email=$2', [username, email]);
    if (existing.rows.length > 0) {
      return res.status(409).json({ error: 'Username or email already taken.' });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    const result = await pool.query(
      'INSERT INTO users (username, email, password, account_type) VALUES ($1,$2,$3,$4) RETURNING id, username, email, account_type, role',
      [username, email, hashedPassword, accountType || 'rider']
    );

    const user = result.rows[0];
    const token = jwt.sign(
      { id: user.id, username: user.username, role: user.role },
      process.env.JWT_SECRET,
      { expiresIn: '7d' }
    );

    res.status(201).json({ token, user });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Server error during registration.' });
  }
});

// POST /api/auth/login
router.post('/login', async (req, res) => {
  const { username, password } = req.body;

  if (!username || !password) {
    return res.status(400).json({ error: 'Username and password are required.' });
  }

  try {
    const result = await pool.query('SELECT * FROM users WHERE username=$1', [username]);
    if (result.rows.length === 0) {
      return res.status(401).json({ error: 'Invalid username or password.' });
    }

    const user = result.rows[0];
    const validPassword = await bcrypt.compare(password, user.password);
    if (!validPassword) {
      return res.status(401).json({ error: 'Invalid username or password.' });
    }

    const token = jwt.sign(
      { id: user.id, username: user.username, role: user.role },
      process.env.JWT_SECRET,
      { expiresIn: '7d' }
    );

    res.json({
      token,
      user: {
        id: user.id,
        username: user.username,
        email: user.email,
        account_type: user.account_type,
        role: user.role
      }
    });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Server error during login.' });
  }
});

module.exports = router;
