import React, { useEffect, useState } from 'react';
import {useNavigate}  from "react-router-dom";
import './App.css';
import config from '../config';

function Operator() {
  const [operators, setOperators] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchOperators = async () => {
      try {
        const response = await fetch(`${config.API_URL}/operator`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error(`Error: ${response.status} ${response.statusText}`);
        }
        const data = await response.json();
        setOperators(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchOperators();
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
        <h1 className="app-title">Operator List</h1>
        <div className="card-list">
          {operators.map((operator) => (
              <div
                  key={operator.id}
                  className="card"
                  onClick={() => navigate(`/operators/${operator.id}`)}
                  style={{ cursor: 'pointer' }}
              >
                <h2 className="card-title">{operator.name}</h2>
                <h3>Stations:</h3>
                <ul className="station-list">
                  {operator.stations && operator.stations.length > 0 ? (
                      operator.stations.map((station) => (
                          <li key={station.id}>
                            <strong>{station.name}</strong>
                            <br />
                            City: {station.city}
                            <br />
                            Address: {station.address}
                            <br />
                            Coordinates: {station.latitude}, {station.longitude}
                          </li>
                      ))
                  ) : (
                      <p>No stations available for this operator.</p>
                  )}
                </ul>
              </div>
          ))}
        </div>
      </div>
  );
}


export default Operator;