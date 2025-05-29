import React from 'react';
import ChargeUnityLogo from '/ChargeUnityLogo.png';
import './App.css';
import { useNavigate } from 'react-router-dom';

function Home() {
    const navigate = useNavigate();

    return (
        <div className="app-container">
            <div className="app-content">
                <img
                    src={ChargeUnityLogo}
                    alt="ChargeUnity Logo"
                    className="app-logo"
                />
                <h1 className="app-title">Welcome to ChargeUnity</h1>
                <div className="button-group">
                    <button
                        onClick={() => navigate('/driver')}
                        id="driver-button"
                        className="button driver-button"
                    >
                        Driver
                    </button>
                    <button
                        onClick={() => navigate('/operator')}
                        id="operator-button"
                        className="button operator-button"
                    >
                        Operator
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Home;