import React, { useEffect, useState } from 'react';
import './App.css';
import config from '../config';

function Driver() {
  const [drivers, setDrivers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDrivers = async () => {
      try {
        const response = await fetch(`${config.API_URL}/driver`);
        if (!response.ok) {
          throw new Error(`Error: ${response.status} ${response.statusText}`);
        }
        const data = await response.json();
        setDrivers(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchDrivers();
  }, []);

  if (loading) {
    return (
      <div className="app-container">
        <h1 className="app-title">Loading...</h1>
      </div>
    );
  }

  if (error) {
    return (
      <div className="app-container">
        <h1 className="app-title">Error</h1>
        <p>{error}</p>
      </div>
    );
  }

  return (
    <div className="app-container">
      <h1 className="app-title">Driver List</h1>
      <div className="card-list">
        {drivers.map((driver) => (
          <div key={driver.id} className="card">
            <h2 className="card-title">{driver.name}</h2>
            <p className="card-description">{driver.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Driver;