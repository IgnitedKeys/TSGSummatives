DROP DATABASE IF EXISTS HotelReservation;
CREATE DATABASE IF NOT EXISTS HotelReservation;
USE HotelReservation;

CREATE TABLE IF NOT EXISTS RoomType(
     RoomTypeId INT PRIMARY KEY AUTO_INCREMENT,
     RoomTypeName VARCHAR(10) NOT NULL,
     MaxOccupancy INT NOT NULL,
     StandardOccupancy INT NOT NULL,
     BasePrice DECIMAL(5,2) NOT NULL,
     ExtraPerson DECIMAL(5,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Amenity (
     AmenityId INT PRIMARY KEY AUTO_INCREMENT,
     AmenityName VARCHAR(50) NOT NULL,
     PriceAddOn DECIMAL(4,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Guest (
     GuestId INT PRIMARY KEY AUTO_INCREMENT,
     FirstName VARCHAR(50) NOT NULL,
     LastName VARCHAR(50) NOT NULL,
     Phone CHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS Address (
     AddressId INT PRIMARY KEY AUTO_INCREMENT,
     GuestId INT NOT NULL,
     Address VARCHAR(50) NOT NULL,
     City VARCHAR(50) NOT NULL,
     State CHAR(2) NOT NULL,
     ZipCode CHAR(5) NOT NULL,
     FOREIGN KEY `fk_AddressGuest`(GuestId) 
		REFERENCES Guest(GuestId)
);

CREATE TABLE IF NOT EXISTS Room (
     RoomNumber INT PRIMARY KEY,
     RoomTypeId INT NOT NULL,
     AdaAccessible BOOL NOT NULL,
     FOREIGN KEY `fk_RoomRoomType`(RoomTypeId)
          REFERENCES RoomType(RoomTypeId)
);

CREATE TABLE IF NOT EXISTS Reservation (
     ReservationId INT PRIMARY KEY AUTO_INCREMENT,
     GuestId INT NOT NULL,
     StartDate DATE NOT NULL,
     EndDate DATE NOT NULL,
     FOREIGN KEY `fk_ReservationGuest`(GuestId)
          REFERENCES Guest(GuestId)
);

CREATE TABLE IF NOT EXISTS RoomReservationInstance (
     RoomInstanceId INT PRIMARY KEY AUTO_INCREMENT,
     ReservationId INT NOT NULL,
     RoomNumber INT NOT NULL,
     NumOfAdults INT NOT NULL,
     NumOfChildren INT NOT NULL,
     TotalCost DECIMAL(6,2) NOT NULL,
     FOREIGN KEY `fk_InstanceReservation`(ReservationId)
          REFERENCES Reservation(ReservationId),
     FOREIGN KEY `fk_InstanceRoom`(RoomNumber)
          REFERENCES Room(RoomNumber)     
);

CREATE TABLE IF NOT EXISTS RoomAmenity (
     RoomNumber INT NOT NULL,
     AmenityId INT NOT NULL,
     PRIMARY KEY(RoomNumber, AmenityId),
     FOREIGN KEY `fk_RoomAmenity_Amenity` (AmenityId)
          REFERENCES Amenity(AmenityId),
     FOREIGN KEY `fk_RoomAmenity_Room`(RoomNumber)
          REFERENCES Room(RoomNumber)
);

