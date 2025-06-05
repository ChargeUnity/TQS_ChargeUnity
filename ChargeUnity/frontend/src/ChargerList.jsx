import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import config from '../config';

function ChargersList() {
    const { stationId } = useParams();
    const [chargers, setChargers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchChargers = async () => {
            try {
                const res = await fetch(`${config.API_URL}/charger/station/${stationId}`);
                if (!res.ok) throw new Error('Failed to fetch chargers');
                const data = await res.json();
                setChargers(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchChargers();
    }, [stationId]);

    // Cores para cada estado
    const statusColors = {
        AVAILABLE: '#4CAF50',           // verde
        UNAVAILABLE: '#F44336',         // vermelho
        UNDER_MAINTENANCE: '#FFC107',   // amarelo
        OUT_OF_SERVICE: '#9E9E9E',      // cinzento
        UNKNOWN: '#607D8B'              // azul acinzentado
    };

    if (loading) return <div>Loading chargers...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
        <div className="app-container">
            <h1>Chargers in Station {stationId}</h1>

            <div style={{ marginBottom: '1rem' }}>
                <Link
                    to={`/stations/${stationId}/chargers/new`}
                    className="button"
                    id="add-charger-button"
                    style={{
                        padding: '0.6rem 1.2rem',
                        backgroundColor: '#ff9100',
                        color: '#fff',
                        borderRadius: '0.3rem',
                        textDecoration: 'none',
                        fontWeight: '600'
                    }}
                >
                    ➕ Add New Charger
                </Link>
            </div>

            {chargers.length === 0 ? (
                <p>No chargers found for this station.</p>
            ) : (
                <ul style={{ listStyle: 'none', padding: 0 }}>
                    {chargers.map(charger => {
                        const bgColor = statusColors[charger.status] || '#cccccc';

                        return (
                            <li
                                key={charger.id}
                                style={{
                                    backgroundColor: bgColor,
                                    padding: '0.5rem 1rem',
                                    marginBottom: '0.5rem',
                                    borderRadius: '0.4rem',
                                    color: '#fff'
                                }}
                            >
                                <Link
                                    to={`/chargers/${charger.id}/edit`}
                                    id={`edit-charger-link-${charger.id}`}
                                    style={{ color: 'inherit', textDecoration: 'none', fontWeight: '600' }}
                                >
                                    Charger #{charger.id} – Status: {charger.status}
                                </Link>
                            </li>
                        );
                    })}
                </ul>
            )}
        </div>
    );
}

export default ChargersList;
