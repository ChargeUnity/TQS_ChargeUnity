import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import config from '../config';

function ChargerStatus() {
    const { chargerId } = useParams();
    const navigate = useNavigate();

    const [charger, setCharger] = useState(null);
    const [status, setStatus] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [notification, setNotification] = useState(null); // For messages

    useEffect(() => {
        const fetchCharger = async () => {
            try {
                const res = await fetch(`${config.API_URL}/charger/${chargerId}`);
                if (!res.ok) throw new Error('Failed to load charger');
                const data = await res.json();
                setCharger(data);
                setStatus(data.status);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };
        fetchCharger();
    }, [chargerId]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await fetch(`${config.API_URL}/charger/${chargerId}/status?status=${status}`, {
                method: 'PATCH',
            });
            if (!res.ok) {
                const errMsg = await res.text();
                throw new Error(errMsg);
            }
            setNotification({ type: 'success', message: 'Status updated successfully!' });

            setTimeout(() => navigate(-1), 2000);
        } catch (err) {
            setNotification({ type: 'error', message: 'Failed to update status: ' + err.message });
        }
    };

    if (loading) return <div className="app-container"><h1>Loading...</h1></div>;
    if (error) return <div className="app-container"><h1>Error: {error}</h1></div>;
    if (!charger) return null;

    const statuses = [
        { value: 'AVAILABLE', label: 'Available' },
        { value: 'UNAVAILABLE', label: 'Unavailable' },
        { value: 'UNDER_MAINTENANCE', label: 'Under Maintenance' },
        { value: 'OUT_OF_SERVICE', label: 'Out of Service' },
        { value: 'UNKNOWN', label: 'Unknown' },
    ];

    return (
        <div className="app-container">
            <div className="charger-status-wrapper" style={{ maxWidth: '360px', width: '100%' }}>
                <h1>Edit Charger #{charger.id}</h1>

                {notification && (
                    <div
                        className={`notification-box ${
                            notification.type === 'success' ? 'notification-success' : 'notification-error'
                        }`}
                    >
                        {notification.message}
                    </div>
                )}

                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                    <label style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start', fontWeight: '600' }}>
                        Status:
                        <select
                            value={status}
                            onChange={e => setStatus(e.target.value)}
                            style={{
                                marginTop: '0.5rem',
                                padding: '0.4rem 0.6rem',
                                borderRadius: '0.3rem',
                                border: '1px solid #ccc',
                                width: '100%',
                                fontSize: '1rem',
                            }}
                        >
                            {statuses.map(s => (
                                <option key={s.value} value={s.value}>
                                    {s.label}
                                </option>
                            ))}
                        </select>
                    </label>

                    <button
                        className="operator-button"
                        type="submit"
                        style={{ padding: '0.7rem 1.5rem', fontSize: '1rem', alignSelf: 'center', width: '100%' }}
                    >
                        Update Status
                    </button>
                </form>
            </div>
        </div>
    );
}

export default ChargerStatus;
