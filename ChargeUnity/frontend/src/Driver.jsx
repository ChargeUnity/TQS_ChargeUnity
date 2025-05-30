import React, { useEffect, useState } from 'react';
import './App.css';
import config from '../config';
import { useNavigate } from 'react-router-dom';

function Driver() {
    const [drivers, setDrivers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchDrivers = async () => {
            try {
                const response = await fetch(`${config.API_URL}/driver`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
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
            <h1 className="app-title">Drivers</h1>
            <div className="card-list">
                {drivers.map((driver) => (
                    <div key={driver.id} className="driver-card">
                        <h2 className="driver-name">{driver.name}</h2>
                        <button
                            id={`view-profile-button-${driver.id}`}
                            className="view-button"
                            onClick={() => navigate(`/driver/${driver.id}`)}
                        >
                            View Profile
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Driver;