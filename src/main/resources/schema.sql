
CREATE TABLE IF NOT EXISTS seats (
    id INT PRIMARY KEY,
    row INT,
    col INT,
    price INT,
    booked BOOLEAN
);