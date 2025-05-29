import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import config from '../config';

function DriverPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [driver, setDriver] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDriver = async () => {
            try {
                const response = await fetch(`${config.API_URL}/driver/${id}`);
                if (!response.ok) throw new Error('Failed to fetch driver');
                const data = await response.json();
                setDriver(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchDriver();
    }, [id]);

    if (loading) return <p>Loading...</p>;
    if (error || !driver) return <p>Error: {error}</p>;

    return (
        <div className="app-container">
            <h1 className="app-title">Hello, {driver.name},!</h1>
            <p>Balance: {driver.balance}€</p>
            <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
                <button onClick={() => navigate(`/driver/${id}/cars`)}>My cars</button>
                <button onClick={() => navigate(`/driver/${id}/bookings`)}>My bookings</button>
            </div>
        </div>
    );
}

export default DriverPage;
