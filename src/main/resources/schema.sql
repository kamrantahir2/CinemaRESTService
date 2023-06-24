DROP TABLE IF EXISTS seats;

CREATE TABLE seats (
    id INT PRIMARY KEY,
    row INT,
    col INT,
    price INT,
    booked BOOLEAN
);