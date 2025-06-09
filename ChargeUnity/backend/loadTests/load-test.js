import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
    stages: [
        { duration: "20s", target: 100 },
        { duration: "20s", target: 250 },
        { duration: "40s", target: 500 },
        { duration: "25s", target: 250 },
        { duration: "10s", target: 0 },
    ],
};

// Here is where we would handle authentication if we had it
// export function setup() {
// }

export default function (data) {
    // const headers = {
    //     "AuthorizationHeader": `accessToken=${data.accessToken}`
    // };
    
    const endpoints = [
        "/api/v1/driver",
        "/api/v1/driver/3",
        "/api/v1/driver/3/bookings",
        "/api/v1/charger",
        "/api/v1/station",
        "/api/v1/station/1",
        "/api/v1/station/city/Lisbon",
        "/api/v1/station/coordinates/38.736946/-9.142685/20",
        "/api/v1/operator/1/station",
    ];
    
    // Random endpoints to test
    const endpoint = endpoints[Math.floor(Math.random() * endpoints.length)];
    
    const response = http.get(`http://localhost:8080${endpoint}`);
    check(response, {
        "status is 200": (r) => r.status === 200,
        "response has data": (r) => r.body.length > 0,
    });
    
    sleep(Math.random() * 0.5); // Add some randomness to the requests
}