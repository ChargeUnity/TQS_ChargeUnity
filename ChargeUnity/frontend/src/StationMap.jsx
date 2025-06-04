import React from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';

// Corrigir ícones (senão o marcador não aparece)
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl:
    'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
  iconUrl:
    'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  shadowUrl:
    'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png'
});

const containerStyle = {
  width: '100%',
  height: '200px',
  borderRadius: '8px',
  marginTop: '0.5rem'
};

const StationMap = ({ lat, lng }) => {
  const center = [parseFloat(lat), parseFloat(lng)];

  const abrirNoOSM = () => {
    const url = `https://www.openstreetmap.org/?mlat=${lat}&mlon=${lng}#map=18/${lat}/${lng}`;
    window.open(url, '_blank');
  };

  return (
    <div>
      <MapContainer center={center} zoom={14} style={containerStyle}>
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        />
        <Marker position={center}>
          <Popup>Localização</Popup>
        </Marker>
      </MapContainer>

      <div style={{ marginTop: '0.5rem', textAlign: 'center' }}>
        <button
          onClick={abrirNoOSM}
          style={{
            padding: '0.5rem 1rem',
            borderRadius: '6px',
            backgroundColor: '#4caf50',
            color: 'white',
            border: 'none',
            cursor: 'pointer'
          }}
        >
          Ver no OpenStreetMap
        </button>
      </div>
    </div>
  );
};

export default StationMap;
