INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE001', 15, 'LIGHTWEIGHT', 'IDLE', 300);
INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE002', 60, 'LIGHTWEIGHT', 'IDLE', 300);
INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE003', 42, 'LIGHTWEIGHT', 'LOADING', 300);
INSERT INTO DRONE (serial_number, battery_capacity, model, state, weight_limit) VALUES('DRONE004', 42, 'LIGHTWEIGHT', 'IDLE', 300);


INSERT INTO MEDICATION (code, name, weight) VALUES('MEDICATION001', 'Melanotan', 80);
INSERT INTO MEDICATION (code, name, weight) VALUES('MEDICATION002', 'Clemastine', 50);
INSERT INTO MEDICATION (code, name, weight) VALUES('MEDICATION003', 'Ibuprofen', 110);

--LOAD DRONE WITH MEDICATION
INSERT INTO DRONE_LOAD (ID,serial_number, code) VALUES(1,'DRONE001', 'MEDICATION001');
INSERT INTO DRONE_LOAD (ID,serial_number, code) VALUES(2,'DRONE001', 'MEDICATION002');
