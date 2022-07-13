-- 1. Return list of reservation that end in July 2023
-- include name of Guest, roomNumbers, reservation dates


SELECT g.FirstName, g.LastName, ri.RoomNumber, r.StartDate, r.EndDate 
FROM Reservation r 
INNER JOIN Guest g ON g.GuestId = r.GuestId  
INNER JOIN RoomReservationInstance ri ON ri.ReservationId = r.ReservationId 
WHERE r.EndDate BETWEEN '2023-07-01' AND '2023-07-31';

-- 4 rows

-- 2. return all reservations with jacuzzi
-- include guest name, roomNumber, dates of reservation


SELECT g.FirstName, g.LastName, ro.RoomNumber, r.StartDate, r.EndDate 
FROM Reservation r 
INNER JOIN Guest g ON g.GuestId = r.GuestId 
INNER JOIN RoomReservationInstance ri ON r.ReservationId = ri.ReservationId 
INNER JOIN Room ro ON ri.RoomNumber = ro.RoomNumber 
INNER JOIN RoomAmenity ra ON ro.RoomNumber = ra.RoomNumber 
INNER JOIN Amenity a ON ra.AmenityId = a.AmenityId 
WHERE a.AmenityName = 'Jacuzzi';
-- 11 rows

-- 3. return all rooms for a specific guest
-- include guest name, rooms reserved, start date, and number of people in reservation
-- choose guest name from existing data
-- Mack Simmer

SELECT g.FirstName, g.LastName, ro.RoomNumber, r.StartDate, SUM(ri.NumOfAdults + ri.NumOfChildren) NumberOfPeople 
FROM Reservation r 
INNER JOIN Guest g ON g.GuestId = r.GuestId 
INNER JOIN RoomReservationInstance ri ON ri.ReservationId = r.ReservationId 
INNER JOIN Room ro ON ro.RoomNumber = ri.RoomNumber 
WHERE g.FirstName = 'Mack' AND g.LastName = 'Simmer' 
GROUP BY g.FirstName, g.LastName, ro.RoomNumber, r.StartDate;
-- 4 rows

-- 4. return list of all rooms
-- include reservationId and per-room-cost for each reservation
-- results should include all rooms, whether or not a reservation is associated with the room

SELECT ro.RoomNumber, ri.ReservationId, ri.TotalCost 
FROM Room ro 
LEFT OUTER JOIN RoomReservationInstance ri ON ri.RoomNumber = ro.RoomNumber 
ORDER BY ro.RoomNumber;
-- 26 rows


-- 5. Write a query that returns all the rooms accommodating at least three guests
-- and that are reserved on any date in April 2023.
-- (accommodation for reservation or room?)

SELECT ro.RoomNumber 
FROM Room ro 
INNER JOIN RoomReservationInstance ri ON ri.RoomNumber = ro.RoomNumber 
INNER JOIN Reservation r ON r.ReservationId = ri.ReservationId 
INNER JOIN RoomType rt ON rt.RoomTypeId = ro.RoomTypeId 
WHERE (r.StartDate BETWEEN '2023-04-01' AND '2023-04-30' OR r.EndDate BETWEEN '2023-04-01' AND '2023-04-30')
AND (ri.NumOfAdults + ri.NumOfChildren) >= 3
GROUP BY ro.RoomNumber
HAVING SUM(ri.NumOfAdults + ri.NumOfChildren) >= 3;
-- empty set

SELECT ro.RoomNumber 
FROM Room ro 
INNER JOIN RoomReservationInstance ri ON ri.RoomNumber = ro.RoomNumber 
INNER JOIN Reservation r ON r.ReservationId = ri.ReservationId 
INNER JOIN RoomType rt ON rt.RoomTypeId = ro.RoomTypeId 
WHERE (r.StartDate BETWEEN '2023-04-01' AND '2023-04-30' OR r.EndDate BETWEEN '2023-04-01' AND '2023-04-30') AND rt.MaxOccupancy > 2 
GROUP BY ro.RoomNumber;
-- 1 row


-- 6.return list of all guestNames
-- include number of reservations per guest
-- sort by guest with most reservations, then by lastName

SELECT g.FirstName, g.LastName,COUNT(r.ReservationId) NumberOfReservations 
FROM Guest g 
INNER JOIN Reservation r ON r.GuestId = g.GuestId 
GROUP BY g.FirstName, g.LastName 
ORDER BY COUNT(r.ReservationId) DESC, g.LastName;
-- 11 rows

-- 7. display name, address, and phone number from guest(based on phoneNumber)
-- choose phone number from existing data
-- (446) 396-6785

SELECT g.FirstName, g.LastName, CONCAT(a.Address,' ',a.City,',',a.State,' ', a.ZipCode) Address, g.Phone 
FROM Guest g 
INNER JOIN Address a ON a.GuestId = g.GuestId 
WHERE g.Phone = '4463966785';
-- 1  row


