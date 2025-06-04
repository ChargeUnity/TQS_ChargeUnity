import React, { useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import config from '../config';
import './DriverStations.css';

function DriverStations() {
    const [mode, setMode] = useState('coordinates');
    const [coords, setCoords] = useState({ lat: '', lng: '', radius: '' });
    const [city, setCity] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [stations, setStations] = useState([]);

    const handleModeChange = (e) => {
        setMode(e.target.value);
        setError(null);
        setStations([]);
    };

    const handleCoordsChange = (e) => {
        setCoords({ ...coords, [e.target.name]: e.target.value });
    };

    const handleCoordsSearch = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setStations([]);
        try {
            const { lat, lng, radius } = coords;
            if (!lat || !lng || !radius) {
                setError('Please provide latitude, longitude, and radius.');
                setLoading(false);
                return;
            }
            const res = await fetch(
                `${config.API_URL}/station/coordinates/${lat}/${lng}/${radius}`
            );
            if (!res.ok) {
                const msg = await res.text();
                throw new Error(msg || 'Could not fetch stations.');
            }
            const data = await res.json();
            setStations(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleCitySearch = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setStations([]);
        try {
            if (!city.trim()) {
                setError('Please enter a city.');
                setLoading(false);
                return;
            }
            const res = await fetch(`${config.API_URL}/station/city/${encodeURIComponent(city.trim())}`);
            if (!res.ok) {
                const msg = await res.text();
                throw new Error(msg || 'Could not fetch stations.');
            }
            const stations = await res.json();
            setStations(stations);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="driverstations-container" id="driverstations-container">
            <div className="driverstations-form-container" id="driverstations-form-container">
                <h1 className="driverstations-title" id="search-stations-title">Search Stations</h1>
                <div className="driverstations-mode-toggle" id="search-mode-toggle">
                    <label>
                        <input
                            type="radio"
                            name="mode"
                            value="coordinates"
                            checked={mode === 'coordinates'}
                            onChange={handleModeChange}
                            id="coordinates-radio"
                        />{' '}
                        By Coordinates & Radius
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="mode"
                            value="city"
                            checked={mode === 'city'}
                            onChange={handleModeChange}
                            id="city-radio"
                        />{' '}
                        By City
                    </label>
                </div>
                {mode === 'coordinates' ? (
                    <form onSubmit={handleCoordsSearch} id="coordinates-search-form">
                        <div className="driverstations-form-group" id="lat-group">
                            <label htmlFor="lat-input">Latitude:</label>
                            <input
                                type="number"
                                name="lat"
                                className="driverstations-input"
                                value={coords.lat}
                                onChange={handleCoordsChange}
                                step="any"
                                placeholder="e.g. 40.7128"
                                id="lat-input"
                            />
                        </div>
                        <div className="driverstations-form-group" id="lng-group">
                            <label htmlFor="lng-input">Longitude:</label>
                            <input
                                type="number"
                                name="lng"
                                className="driverstations-input"
                                value={coords.lng}
                                onChange={handleCoordsChange}
                                step="any"
                                placeholder="e.g. -74.0060"
                                id="lng-input"
                            />
                        </div>
                        <div className="driverstations-form-group" id="radius-group">
                            <label htmlFor="radius-input">Radius (km):</label>
                            <input
                                type="number"
                                name="radius"
                                className="driverstations-input"
                                value={coords.radius}
                                onChange={handleCoordsChange}
                                min="0.1"
                                step="any"
                                placeholder="e.g. 5"
                                id="radius-input"
                            />
                        </div>
                        <button
                            className="driverstations-btn"
                            type="submit"
                            id="search-by-coordinates-btn"
                            disabled={loading}
                        >
                            {loading ? 'Searching...' : 'Search'}
                        </button>
                    </form>
                ) : (
                    <form onSubmit={handleCitySearch} id="city-search-form">
                        <div className="driverstations-form-group" id="city-group">
                            <label htmlFor="city-input">City:</label>
                            <input
                                type="text"
                                className="driverstations-input"
                                value={city}
                                onChange={e => setCity(e.target.value)}
                                placeholder="Enter city"
                                id="city-input"
                            />
                        </div>
                        <button
                            className="driverstations-btn"
                            type="submit"
                            id="search-by-city-btn"
                            disabled={loading}
                        >
                            {loading ? 'Searching...' : 'Search'}
                        </button>
                    </form>
                )}
                {error && <div className="driverstations-error" id="search-error">{error}</div>}
            </div>
            <div className="driverstations-results-container" id="results-container">
                {stations.length > 0 && (
                    <>
                        <h2 id="results-title">Results</h2>
                        <ul className="driverstations-list" id="station-results-list">
                            {stations.map(station => (
                                <li
                                    key={station.id}
                                    className="driverstations-station-card"
                                    id={`station-card-${station.id}`}
                                    style={{ cursor: 'pointer' }}
                                    tabIndex={0}
                                >
                                    <Link
                                        to={`./${station.id}/chargers`}
                                        id={`station-link-${station.id}`}
                                        style={{ textDecoration: 'none', color: 'inherit', display: 'block' }}
                                    >
                                        <strong id={`station-name-${station.id}`}>{station.name}</strong><br />
                                        <span id={`station-city-${station.id}`}>City: {station.city}</span><br />
                                        <span id={`station-address-${station.id}`}>Address: {station.address}</span><br />
                                        <span id={`station-coords-${station.id}`}>Coordinates: {station.latitude}, {station.longitude}</span>
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </>
                )}
                {!loading && stations.length === 0 && (
                    <p id="no-stations-message">No stations found for the search criteria.</p>
                )}
            </div>
        </div>
    );
}

export default DriverStations;