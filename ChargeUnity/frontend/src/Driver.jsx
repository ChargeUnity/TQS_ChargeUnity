import React, { useEffect, useState } from 'react';
import './App.css';
import config from '../config';

function Driver() {
  const [drivers, setDrivers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDrivers = async () => {
      try {
        const response = await fetch(`${config.API_URL}/driver`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });
        if (!response.ok) {
          throw new Error(`Error: ${response.status} ${response.statusText}`);
        }
        const data = await response.json();
        setDrivers(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchDrivers();
  }, []);

  if (loading) {
    return (
      <div className="app-container">
        <h1 className="app-title">Loading...</h1>
      </div>
    );
  }

  if (error) {
    return (
      <div className="app-container">
        <h1 className="app-title">Error</h1>
        <p>{error}</p>
      </div>
    );
  }

  return (
    <div className="app-container">
      <h1 className="app-title">Driver List</h1>
      <div className="card-list">
        {drivers.map((driver) => (
          <div key={driver.id} className="card">
            <h2 className="card-title">{driver.name}</h2>
            <p className="card-description">Balance: {driver.balance ? `${driver.balance}€` : 'N/A'}</p>

            <h3>Cars:</h3>
            <ul className="car-list">
              {driver.cars && driver.cars.length > 0 ? (
                driver.cars.map((car) => (
                  <li key={car.id}>
                    <strong>{car.brand} {car.model}</strong>
                    <br />
                    Battery Capacity: {car.batteryCapacity} kWh
                    <br />
                    Battery Percentage: {car.batteryPercentage}%
                    <br />
                    Total Kilometers: {car.kilometers} km
                  </li>
                ))
              ) : (
                <p>No cars available for this driver.</p>
              )}
            </ul>

            <h3>Bookings:</h3>
            <ul className="booking-list">
              {driver.bookings && driver.bookings.length > 0 ? (
                driver.bookings.map((booking) => (
                  <li key={booking.id}>
                    <strong>Booking #{booking.id}</strong>
                    <br />
                    Start Time: {booking.startTime}
                    <br />
                    End Time: {booking.endTime}
                    <br />
                    Price: {booking.price ? `${booking.price}€` : 'N/A'}
                    <br />
                    Status: {booking.status}
                  </li>
                ))
              ) : (
                <p>No bookings available for this driver.</p>
              )}
            </ul>

            <h3>Trips:</h3>
            <ul className="trip-list">
              {driver.trips && driver.trips.length > 0 ? (
                driver.trips.map((trip) => (
                  <li key={trip.id}>
                    <strong>Trip #{trip.id}</strong>
                    <br />
                    Start Coordinates: {trip.startCoordinates}
                    <br />
                    End Coordinates: {trip.endCoordinates}
                    <br />
                    Distance: {trip.distance} km
                    <br />
                    Price: {trip.price ? `${trip.price}€` : 'N/A'}
                  </li>
                ))
              ) : (
                <p>No trips available for this driver.</p>
              )}
            </ul>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Driver;