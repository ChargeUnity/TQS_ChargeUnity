import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import config from '../config';

function RegisterStation() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: '',
        city: '',
        address: '',
        latitude: '',
        longitude: ''
    });

    const [error, setError] = useState(null);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`${config.API_URL}/station`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    ...formData,
                    operatorId: parseInt(id),
                    latitude: parseFloat(formData.latitude),
                    longitude: parseFloat(formData.longitude)
                })
            });

            if (!response.ok) {
                const message = await response.text();
                throw new Error(message || 'Failed to create station');
            }

            navigate(`/operators/${id}`);
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="app-container">
            <div className="form-container">
                <h1 className="app-title">Register New Station</h1>

                {error && <div className="error-message">Error: {error}</div>}

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Station Name:</label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            required
                            className="input-field"
                            id="station-name-input"
                        />
                    </div>

                    <div className="form-group">
                        <label>City:</label>
                        <input
                            type="text"
                            name="city"
                            value={formData.city}
                            onChange={handleChange}
                            required
                            className="input-field"
                            id="station-city-input"
                        />
                    </div>

                    <div className="form-group">
                        <label>Address:</label>
                        <input
                            type="text"
                            name="address"
                            value={formData.address}
                            onChange={handleChange}
                            required
                            className="input-field"
                            id="station-address-input"
                        />
                    </div>

                    <div className="form-group">
                        <label>Latitude:</label>
                        <input
                            type="number"
                            name="latitude"
                            value={formData.latitude}
                            onChange={handleChange}
                            required
                            className="input-field"
                            step="any"
                            id="station-latitude-input"
                        />
                    </div>

                    <div className="form-group">
                        <label>Longitude:</label>
                        <input
                            type="number"
                            name="longitude"
                            value={formData.longitude}
                            onChange={handleChange}
                            required
                            className="input-field"
                            step="any"
                            id="station-longitude-input"
                        />
                    </div>

                    <button type="submit" id="create-station-button" className="btn-primary">Create Station</button>
                </form>
            </div>
        </div>
    );
}

export default RegisterStation;