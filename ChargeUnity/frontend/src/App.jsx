import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Navbar from './Navbar';
import Home from './Home';
import Driver from './Driver';
import Operator from './Operator';
import OperatorDetail from "./OperatorDetail.jsx";
import ChargersList from "./ChargerList.jsx";
import ChargerStatus from "./ChargerStatus.jsx";


function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/driver" element={<Driver />} />
        <Route path="/operator" element={<Operator />} />
        <Route path="/operators/:id" element={<OperatorDetail />} />
        <Route path="/stations/:stationId/chargers" element={<ChargersList />} />
        <Route path="/chargers/:chargerId/edit" element={<ChargerStatus />} />
      </Routes>
    </>
  );
}

export default App;