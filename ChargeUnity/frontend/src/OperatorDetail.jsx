import React, { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import config from '../config';

function OperatorDetail() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [stations, setStations] = useState([]);
    const [operatorName, setOperatorName] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOperatorData = async () => {
            try {
                const opRes = await fetch(`${config.API_URL}/operator/${id}`);
                if (!opRes.ok) throw new Error('Failed to fetch operator');
                const operator = await opRes.json();
                setOperatorName(operator.name);

                const stationRes = await fetch(`${config.API_URL}/operator/${id}/station`);
                if (!stationRes.ok) throw new Error('Failed to fetch stations');
                const data = await stationRes.json();
                setStations(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchOperatorData();
    }, [id]);

    const handleRegisterStation = () => {
        navigate(`/operators/${id}/stations/new`);
    };

    if (loading) return <div className="app-container"><h1>Loading...</h1></div>;
    if (error) return <div className="app-container"><h1>Error: {error}</h1></div>;

    return (
        <div className="app-container">
            <h1>{operatorName} - Stations</h1>
            <button onClick={handleRegisterStation} id="register-new-station-button" className="btn">
                Register New Station
            </button>
            {stations.length === 0 ? (
                <p>No stations available for this operator.</p>
            ) : (
                <ul className="station-list">
                    {stations.map((station) => (
                        <li key={station.id} id={`station-list-item-${station.id}`}>
                            <Link to={`/stations/${station.id}/chargers`}>
                                <strong>{station.name}</strong>
                            </Link><br />
                            City: {station.city}<br />
                            Address: {station.address}<br />
                            Coordinates: {station.latitude}, {station.longitude}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default OperatorDetail;