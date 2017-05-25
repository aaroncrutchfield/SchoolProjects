CREATE TABLE `inventory`(
`partID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`partNumber` VARCHAR(12) NOT NULL,
`date` DATE NOT NULL,
`quantity` INT(6) NOT NULL,
`description` VARCHAR(20) NULL,
`toteSize` ENUM(small, medium, large) NULL,
`customer` ENUM(Franklin, Shelby, BU1, BU2, BU3, Service) NULL
) ENGINE=MyIsam;

