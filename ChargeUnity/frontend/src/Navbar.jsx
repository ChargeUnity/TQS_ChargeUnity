import React from 'react';
import { useNavigate } from 'react-router-dom';
import ChargeUnityLogo from '/ChargeUnityLogo.png';
import './App.css';

function Navbar() {
    const navigate = useNavigate();

    return (
        <div className="navbar">
            <div className="navbar-left">
                <img src={ChargeUnityLogo} alt="ChargeUnity Logo" className="navbar-logo" />
                <button onClick={() => navigate('/')} id="home-button" className="navbar-button">
                    Home
                </button>
            </div>
        </div>
    );
}

export default Navbar;