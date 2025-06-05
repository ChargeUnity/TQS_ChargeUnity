const hostname = window.location.hostname;

// Dynamically determine the API URL
const CONFIG = {
  API_URL: hostname === 'localhost'
    ? 'http://localhost:8080/api/v1'
    : __API_URL__ || `http://${hostname}:8080/api/v1`,
};

export default CONFIG;