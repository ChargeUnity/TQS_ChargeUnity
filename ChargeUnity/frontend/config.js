const isDevelopment = window.location.hostname === 'localhost';

const CONFIG = {
    API_URL: isDevelopment 
        ? 'http://localhost:5005/api/v1'
        : 'http://deti-tqs-08.ua.pt:5005/api/v1'
};

export default CONFIG;