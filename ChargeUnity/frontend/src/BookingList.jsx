import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import config from '../config';

function BookingList() {
    const { id } = useParams(); // Driver ID from URL
    const [bookings, setBookings] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const res = await fetch(`${config.API_URL}/bookings/driver/${id}`);
                if (!res.ok) throw new Error('Failed to fetch bookings.');
                const data = await res.json();
                setBookings(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchBookings();
    }, [id]);

    const startCharging = async (bookingId) => {
        try {
            const res = await fetch(`${config.API_URL}/bookings/${bookingId}/start`, {
                method: 'PATCH',
            });
            if (!res.ok) throw new Error('Failed to start charging.');
            const updated = await res.json();
            setBookings((prev) =>
                prev.map((b) => (b.id === bookingId ? updated : b))
            );
        } catch (err) {
            alert(err.message);
        }
    };

    const stopCharging = async (bookingId) => {
        try {
            const res = await fetch(`${config.API_URL}/bookings/${bookingId}/stop`, {
                method: 'PATCH',
            });
            if (!res.ok) throw new Error('Failed to stop charging.');
            const updated = await res.json();
            setBookings((prev) =>
                prev.map((b) => (b.id === bookingId ? updated : b))
            );
        } catch (err) {
            alert(err.message);
        }
    };

    if (loading) return <div className="app-container"><h2>Loading...</h2></div>;
    if (error) return <div className="app-container"><p>Error: {error}</p></div>;

    return (
        <div className="app-container">
            <h1>My Bookings</h1>
            {bookings.length === 0 ? (
                <p>No bookings found.</p>
            ) : (
                <ul className="booking-list" style={{ listStyle: 'none', paddingLeft: 0 }}>
                    {bookings.map((booking) => (
                        <li key={booking.id} className="card">
                            <p><strong>Start:</strong> {new Date(booking.startTime).toLocaleString()}</p>
                            <p><strong>End:</strong> {new Date(booking.endTime).toLocaleString()}</p>
                            <p><strong>Price:</strong> {booking.price != null ? `${booking.price} €` : 'N/A'}</p>
                            <p><strong>Status:</strong> {booking.status}</p>

                            {booking.status === 'WAITING' && (
                                <button onClick={() => startCharging(booking.id)}>
                                    Start Charging
                                </button>
                            )}

                            {booking.status === 'CHARGING' && (
                                <button onClick={() => stopCharging(booking.id)}>
                                    Stop Charging
                                </button>
                            )}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default BookingList;
