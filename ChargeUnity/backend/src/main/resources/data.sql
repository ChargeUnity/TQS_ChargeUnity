-- Operators (inserted into the `users` table with dtype='Operator')
INSERT INTO users (id, name, dtype) VALUES 
(1, 'Galp', 'Operator'), 
(2, 'EDP', 'Operator')
ON DUPLICATE KEY UPDATE name = VALUES(name), dtype = VALUES(dtype);

-- Stations
INSERT INTO station (id, name, city, address, latitude, longitude, operator_id) VALUES
(1, 'ChargeUnity Lisboa', 'Lisbon', 'Rua do Carregador 1', '38.736946', '-9.142685', 1),
(2, 'Porto Powerhub', 'Porto', 'Rua da Energia 42', '41.14961', '-8.61099', 2)
ON DUPLICATE KEY UPDATE 
    name = VALUES(name), 
    city = VALUES(city), 
    address = VALUES(address), 
    latitude = VALUES(latitude), 
    longitude = VALUES(longitude), 
    operator_id = VALUES(operator_id);

-- Drivers (inserted into the `users` table with dtype='Driver')
INSERT INTO users (id, name, dtype, balance) VALUES 
(3, 'Manuel Silva', 'Driver', 55.75), 
(4, 'Maria Costa', 'Driver', 120.30)
ON DUPLICATE KEY UPDATE 
    name = VALUES(name), 
    dtype = VALUES(dtype), 
    balance = VALUES(balance);

-- Cars
INSERT INTO car (id, driver_id, brand, model, battery_capacity, battery_percentage, consumption_per_km, kilometers) VALUES
(1, 3, 'Tesla', 'Model S', 100.0, 80.0, 0.2, 15000),
(2, 4, 'Nissan', 'Leaf', 62.0, 50.0, 0.15, 40000)
ON DUPLICATE KEY UPDATE 
    driver_id = VALUES(driver_id), 
    brand = VALUES(brand), 
    model = VALUES(model), 
    battery_capacity = VALUES(battery_capacity), 
    battery_percentage = VALUES(battery_percentage), 
    consumption_per_km = VALUES(consumption_per_km), 
    kilometers = VALUES(kilometers);

-- Chargers
INSERT INTO charger (id, station_id, status, type, price_perkwh) VALUES
(1, 1, 'AVAILABLE', 'ECONOMY', 0.15),
(2, 2, 'UNAVAILABLE', 'FAST', 0.30)
ON DUPLICATE KEY UPDATE 
    station_id = VALUES(station_id), 
    status = VALUES(status), 
    type = VALUES(type), 
    price_perkwh = VALUES(price_perkwh);

-- Trips
INSERT INTO trip (id, driver_id, start_coordinates, end_coordinates, start_time, duration_seconds, distance, price) VALUES
(1, 3, '38.736946,-9.142685', '41.14961,-8.61099', '2023-09-01T10:00:00', '7200', 313.0, 40.0)
ON DUPLICATE KEY UPDATE 
    driver_id = VALUES(driver_id), 
    start_coordinates = VALUES(start_coordinates), 
    end_coordinates = VALUES(end_coordinates), 
    start_time = VALUES(start_time), 
    duration_seconds = VALUES(duration_seconds), 
    distance = VALUES(distance), 
    price = VALUES(price);

-- Bookings
INSERT INTO booking (id, trip_id, driver_id, charger_id, car_id, start_time, end_time, price, status) VALUES
(1, 1, 3, 1, 1, '2023-09-01T10:00:00', '2023-09-01T12:00:00', 40.0, 'COMPLETED')
ON DUPLICATE KEY UPDATE 
    trip_id = VALUES(trip_id), 
    driver_id = VALUES(driver_id), 
    charger_id = VALUES(charger_id), 
    car_id = VALUES(car_id), 
    start_time = VALUES(start_time), 
    end_time = VALUES(end_time), 
    price = VALUES(price), 
    status = VALUES(status);