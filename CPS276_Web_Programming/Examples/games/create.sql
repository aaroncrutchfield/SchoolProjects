CREATE TABLE `games`(
`gameID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
`title` VARCHAR(50) NOT NULL, 
`subtitle` VARCHAR(100) NULL,
`minPlayers` TINYINT(3) UNSIGNED NOT NULL,
`maxPlayers` TINYINT(3) UNSIGNED NULL,
`releaseDate` DATE NOT NULL,
`type` ENUM('board', 'video', 'card', 'table', 'other') NOT NULL DEFAULT 'board',
`publisher` INT(11) UNSIGNED NULL,
`description` TEXT NULL,
`ageRating` ENUM('kid', 'adult', 'all_ages') NOT NULL DEFAULT 'all_ages'
) ENGINE=MyIsam;

