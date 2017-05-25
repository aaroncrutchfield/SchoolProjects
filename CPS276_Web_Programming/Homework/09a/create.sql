CREATE TABLE `inventory`(
`partID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`partNumber` VARCHAR(11) NOT NULL, 
`date` DATE NOT NULL,
`quantity` INT(8) NOT NULL,
`description` VARCHAR(20) NULL,
`toteSize` ENUM('small', 'meduim', 'large') NULL,
`location` VARCHAR(3) NOT NULL
) ENGINE=MyIsam;
