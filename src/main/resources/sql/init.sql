CREATE TABLE rental_property (
                                 reference_id INT PRIMARY KEY,
                                 description VARCHAR(255) NOT NULL,
                                 town VARCHAR(100) NOT NULL,
                                 address VARCHAR(255) NOT NULL,
                                 property_type VARCHAR(50) NOT NULL,
                                 rent_amount DOUBLE NOT NULL,
                                 security_deposit_amount DOUBLE NOT NULL,
                                 area DOUBLE NOT NULL,
                                 bedrooms_count INT NOT NULL,
                                 floor_number INT NOT NULL,
                                 number_of_floors INT NOT NULL,
                                 construction_year INT NOT NULL,
                                 energy_classification VARCHAR(10),
                                 has_elevator BOOLEAN NOT NULL,
                                 has_intercom BOOLEAN NOT NULL,
                                 has_balcony BOOLEAN NOT NULL,
                                 has_parking_space BOOLEAN NOT NULL
);