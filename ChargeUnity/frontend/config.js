const isDevelopment = window.location.hostname === 'localhost';

const CONFIG = {
    API_URL: (import.meta.env.VITE_APP_API_URL || (isDevelopment
        ? 'http://localhost:8080'
        : 'http://172.18.13.232:8080')) + '/api/v1'
};

export default CONFIG;