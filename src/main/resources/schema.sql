
CREATE TABLE IF NOT EXISTS seats (
    id INT PRIMARY KEY,
    row INT,
    col INT,
    price INT,
    booked BOOLEAN
);

CREATE TABLE IF NOT EXISTS statistics (
    total_seats INT,
    booked_seats INT,
    earnings INT,
    percentage_booked DECIMAL(2)
);