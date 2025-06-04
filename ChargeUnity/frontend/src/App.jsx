import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Navbar from './Navbar';
import Home from './Home';
import Driver from './Driver';
import Operator from './Operator';
import OperatorDetail from './OperatorDetail.jsx';
import ChargersList from './ChargerList.jsx';
import ChargerStatus from './ChargerStatus.jsx';
import BookingList from './BookingList.jsx';
import DriverPage from './DriverPage.jsx';
import RegisterStation from './RegisterStation.jsx';
import DriverStations from './DriverStations.jsx';
import RegisterCharger from "./RegisterCharger.jsx";
import 'leaflet/dist/leaflet.css';

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
                <Route path="/driver/:id" element={<DriverPage />} />
                <Route path="/driver/:id/bookings" element={<BookingList />} />
                <Route path="/driver/:id/stations" element={<DriverStations />} />
                <Route path="/operators/:id/stations/new" element={<RegisterStation />} />
                <Route path="/stations/:stationId/chargers/new" element={<RegisterCharger />} />
            </Routes>
        </>
    );
}

export default App;