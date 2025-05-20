import React, { useEffect, useState } from 'react';
import './App.css';
import config from '../config';

function Operator() {
  const [operators, setOperators] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchOperators = async () => {
      try {
        const response = await fetch(`${config.API_URL}/operator`);
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
          <div key={operator.id} className="card">
            <h2 className="card-title">{operator.name}</h2>
            <p className="card-description">{operator.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Operator;