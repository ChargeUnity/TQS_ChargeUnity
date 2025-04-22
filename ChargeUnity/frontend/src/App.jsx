import React from 'react';
import ChargeUnityLogo from '/ChargeUnityLogo.png';
import './App.css';

function App() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-900 text-gray-100">
      <div className="text-center">
        <img
          src={ChargeUnityLogo}
          alt="ChargeUnity Logo"
          className="w-40 h-40 mx-auto mb-6"
        />

        <h1 className="text-4xl font-extrabold mb-4">
          Welcome to the ChargeUnity Project
        </h1>

        <p className="text-lg text-gray-400">
          Frontend still in development.
        </p>
      </div>
    </div>
  );
}

export default App;