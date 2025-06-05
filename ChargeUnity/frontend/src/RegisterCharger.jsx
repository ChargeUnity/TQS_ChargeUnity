import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import config from '../config';

function AddCharger() {
  const { stationId } = useParams();
  const navigate = useNavigate();

  const [status, setStatus] = useState('AVAILABLE');
  const [type, setType] = useState('STANDARD');
  const [pricePerKWh, setPricePerKWh] = useState('');
  const [error, setError] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setError(null);

    const parsedPrice = parseFloat(pricePerKWh);

    if (isNaN(parsedPrice) || parsedPrice <= 0) {
      setError('Price per kWh must be a positive number.');
      setSubmitting(false);
      return;
    }

    const chargerData = {
    stationId,
    status,
    chargerType: type,
    pricePerKWh: parsedPrice
  };

    try {
      const res = await fetch(`${config.API_URL}/charger`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(chargerData),
      });

      if (!res.ok) {
        const err = await res.text();
        throw new Error(err || 'Failed to create charger');
      }

      navigate(`/stations/${stationId}/chargers`);
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="app-container">
      <h1>Add Charger to Station #{stationId}</h1>
      <form onSubmit={handleSubmit} style={{ maxWidth: '400px' }}>
        <label>Status:</label>
        <select value={status} onChange={e => setStatus(e.target.value)} required>
          <option value="AVAILABLE">Available</option>
          <option value="UNAVAILABLE">Unavailable</option>
          <option value="UNDER_MAINTENANCE">Under Maintenance</option>
          <option value="OUT_OF_SERVICE">Out of Service</option>
        </select>

        <label>Type:</label>
        <select value={type} onChange={e => setType(e.target.value)} required>
          <option value="FAST">Fast</option>
          <option value="STANDARD">Standard</option>
          <option value="ECONOMY">Economy</option>
        </select>

        <label>Price per kWh (€):</label>
        <input
          type="number"
          step="0.01"
          value={pricePerKWh}
          onChange={e => setPricePerKWh(e.target.value)}
          required
        />

        <button type="submit" className="button" disabled={submitting}>
          {submitting ? 'Adding...' : 'Add Charger'}
        </button>

        {error && <p className="error-message">{error}</p>}
      </form>
    </div>
  );
}

export default AddCharger;
