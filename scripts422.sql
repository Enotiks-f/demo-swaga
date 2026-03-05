CREATE TABLE cars
(
    id SERIAL PRIMARY KEY,
    brand varchar(100),
    model varchar(100),
    price int
)

CREATE TABLE persons
(
    id SERIAL PRIMARY KEY,
    firstName varchar(255),
    age int,
    license BOOLEAN,
    car_id INT,
    constraint person_car_fk FOREIGN KEY(car_id) REFERENCES cars(id)
);
