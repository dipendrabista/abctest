INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE001', 100, 'LIGHTWEIGHT', 'IDLE', 120);
INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE002', 99, 'MIDDLEWEIGHT', 'IDLE', 220);
INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE003', 88, 'CRUISERWEIGHT', 'IDLE',300);
INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE004', 95, 'HEAVYWEIGHT', 'IDLE', 450);
INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE005', 90, 'HEAVYWEIGHT', 'IDLE', 500);


INSERT INTO MEDICATION (code, name, weight) VALUES('MED001', 'Melanotan', 200);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED002', 'Clemastine', 50);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED003', 'Ibuprofen', 10);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED004', 'Acetaminophen', 100);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED005', 'Adderall', 150);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED006', 'Amitriptyline', 125);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED007', 'Dapagliflozin', 150);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED008', 'Detrol', 25);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED009', 'Felodipine', 100);
INSERT INTO MEDICATION (code, name, weight) VALUES('MED0010', 'Zaleplon', 50);

--LOAD DRONE WITH MEDICATION
INSERT INTO DRONE_LOAD (ID,serial_number, code) VALUES(1,'DRONE001', 'MED001');
INSERT INTO DRONE_LOAD (ID,serial_number, code) VALUES(2,'DRONE001', 'MED002');
