DROP DATABASE IF EXISTS heroSighting;
CREATE DATABASE heroSighting;

USE heroSighting;

CREATE TABLE `organization`(
	organizationId INT PRIMARY KEY AUTO_INCREMENT,
    organizationName VARCHAR(50) NOT NULL,
    organizationDescription VARCHAR(255),
    contact VARCHAR(50),
    address VARCHAR(50)
);

CREATE TABLE superpower(
	superpowerId INT PRIMARY KEY AUTO_INCREMENT,
    superpowerName VARCHAR(30) NOT NULL
);

CREATE TABLE hero(
	heroId INT PRIMARY KEY AUTO_INCREMENT,
	heroName VARCHAR(30) NOT NULL,
	heroDescription VARCHAR(255),
	isVillian BOOL
);

CREATE TABLE superpower_Hero (
heroId INT NOT NULL,
superpowerId INT NOT NULL,
PRIMARY KEY(heroId, superpowerId),
FOREIGN KEY(heroId) REFERENCES hero(heroId),
FOREIGN KEY(superpowerId) REFERENCES superpower(superpowerId)
);

CREATE TABLE location(
	locationId INT PRIMARY KEY AUTO_INCREMENT,
    locationName VARCHAR(50) NOT NULL,
    locationDescription VARCHAR(255),
    latitude DECIMAL(7,4),
    longitude DECIMAL(7,4)
);

CREATE TABLE sighting(
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
    sightingDate DATE NOT NULL,
    heroId INT NOT NULL,
    locationId INT NOT NULL,
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (locationId) REFERENCES location(locationId)
);

CREATE TABLE hero_Organization(
	heroId INT NOT NULL,
    organizationId INT NOT NULL,
	PRIMARY KEY(heroId, organizationId),
	FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (organizationId) REFERENCES organization(organizationId)
);