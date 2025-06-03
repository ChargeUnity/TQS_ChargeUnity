import React from 'react';
import { GoogleMap, LoadScript, Marker } from '@react-google-maps/api';

const containerStyle = {
  width: '100%',
  height: '200px',
  borderRadius: '8px',
  marginTop: '0.5rem'
};

const StationMap = ({ lat, lng }) => {
  const center = { lat: parseFloat(lat), lng: parseFloat(lng) };

  const apiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

  return (
    <LoadScript googleMapsApiKey={apiKey}>
      <GoogleMap mapContainerStyle={containerStyle} center={center} zoom={14}>
        <Marker position={center} />
      </GoogleMap>
    </LoadScript>
  );
};

export default StationMap;