import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import config from '../config';

function ChargersListUser() {
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

    if (loading) return <div>Loading chargers...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
        <div className="app-container">
            <h1>Chargers in Station {stationId}</h1>
            {chargers.length === 0 ? (
                <p>No chargers found for this station.</p>
            ) : (
                <ul>
                    {chargers.map(charger => (
                        <li key={charger.id}>
                            <Link
								to={`./${charger.id}/book`}
                                id={`edit-charger-link-${charger.id}`}
                            >
                                Charger #{charger.id} - Status: {charger.status}
                            </Link>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default ChargersListUser;