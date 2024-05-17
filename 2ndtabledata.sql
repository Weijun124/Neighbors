delete from accounts CASCADE;
delete from blocks CASCADE;
delete from Friendship CASCADE;
delete from Membership CASCADE;
delete from Neighbors CASCADE;

INSERT INTO Neighborhoods (nid,name)
VALUES 
(1,'Brooklyn'),
(2,'Manhattan'),
(3,'Bronx'),
(4,'Queens');

INSERT INTO blocks (bid,nid, name, latitude, longitude, radius)
VALUES 
(1,1, 'Brooklyn Heights', 40.695810, -73.993790, 300),
(2,1, 'Bushwick', 40.694428, -73.921286, 300),
(3,1, 'Downtown Brooklyn', 40.692530, -73.987473, 300),
(4,1, 'Coney Island', 40.575544, -73.970701, 400),
(5,2, 'Upper East Side', 40.773565, -73.956555, 200),
(6,2, 'Harlem', 40.811550, -73.946477, 250),
(7,2, 'Chelsea', 40.746500, -74.001374, 180),
(8,2, 'Financial District', 40.707491, -74.011276, 220),
(9,3, 'Fordham', 40.862543, -73.888524, 250),
(10,3, 'Riverdale', 40.886936, -73.910998, 300),
(11,3, 'South Bronx', 40.819729, -73.906588, 220),
(12,3, 'Pelham Bay', 40.849671, -73.833084, 350),
(13,4, 'Astoria', 40.764357, -73.923462, 400),
(14,4, 'Flushing', 40.762600, -73.833084, 400),
(15,4, 'Jamaica', 40.702677, -73.788969, 400),
(16,4, 'Long Island City', 40.744679, -73.948542, 280);


INSERT INTO accounts(accountname, passwords, email, firstname, lastname, address, latitude, longitude)
VALUES ('test23saa1r', 'testpass', 'test@email.com', 'Test', 'User', 'Test Address', 40.694428, -73.921286);
INSERT INTO accounts(accountname, passwords, email, firstname, lastname, address, latitude, longitude)
VALUES ('tea11ar', 'testpass', 'test@email.com', 'Test', 'User', 'Test Address', 40.692530, -73.987473);
INSERT INTO accounts(accountname, passwords, email, firstname, lastname, address, latitude, longitude)
VALUES ('tea1r', 'testpass', 'test@email.com', 'Test', 'User', 'Test Address', 40.7647238, -73.8306716);

INSERT INTO Membership (requesterid, blockid, memberstatus, count, requeststatus)
VALUES ('test23saa1r', 14, 'pending', 0, 'True');



