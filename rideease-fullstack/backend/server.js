const express = require('express');
const cors = require('cors');
require('dotenv').config();

const app = express();

app.use(cors({ origin: 'http://localhost:3000', credentials: true }));
app.use(express.json());

app.use('/api/auth', require('./routes/auth'));
app.use('/api/rides', require('./routes/rides'));
app.use('/api/admin', require('./routes/admin'));

app.get('/', (req, res) => res.json({ message: 'RideEase API running' }));

app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ error: 'Something went wrong.' });
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));
