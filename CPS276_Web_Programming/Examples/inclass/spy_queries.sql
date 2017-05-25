SELECT l.*, s.name 
FROM spy_locations l 
LEFT JOIN spy s ON s.locationID=l.locationID 
WHERE l.locationID=3;

# spies on side A and their cities
SELECT s.name, s.side, l.name 
FROM spy s 
JOIN spy_locations l ON l.locationID=s.locationID
WHERE s.side='A';

# disguises of spy #3
SELECT s.name, d.label 
FROM spy s
LEFT JOIN spy_disguises sd
    ON sd.spyID = s.spyID 
LEFT JOIN disguises d
    ON d.disguiseID = sd.disguiseID 
WHERE s.spyID=3;


# disguises of all spies
# you lose data from group by
# GROUP_CONCAT prevents this

SELECT s.name, GROUP_CONCAT(d.label) as disguises
FROM spy s
LEFT JOIN spy_disguises sd
    ON sd.spyID = s.spyID 
LEFT JOIN disguises d
    ON d.disguiseID = sd.disguiseID
GROUP BY s.spyID;


# number of diguises

SELECT s.name, COUNT(d.label) as disguises
FROM spy s
LEFT JOIN spy_disguises sd
    ON sd.spyID = s.spyID 
LEFT JOIN disguises d
    ON d.disguiseID = sd.disguiseID
GROUP BY s.spyID;


# insert a location
INSERT INTO spy_locations 
SET name=AES_ENCRYPT('Paris', 'password1');



# extract encrypted data
SELECT AES_DECRYPT(name, 'password1') 
FROM spy_locations 
WHERE locationID=6;

# always keep the key in PHP as a variable