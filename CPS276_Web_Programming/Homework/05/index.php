<?php

// access db
include_once 'db_connect.php';

if (isset($_POST['zipcode']) && $_POST['zipcode'] != ''){
    $zipcode = $_POST['zipcode'];
}
else{
    $zipcode = '';
    
}
?>

<!--form for zip code input-->

<form method="post" action="index.php">
    Enter zip code 
    <input type="text" name="zipcode" value="<?=$zipcode?>">
    <input type="submit" value="Submit">
    
</form>


<?php

//if zipcode isn't set, do nothing
if ($zipcode == ''){
    exit();
}


//test if zipcode exists. Don't need this but useful for later
//http://stackoverflow.com/questions/1676551/best-way-to-test-if-a-row-exists-in-a-mysql-table
//$query = "SELECT EXISTS(SELECT zipcode FROM a5_locations WHERE zipcode=$zipcode);";
//while($row=$result->fetch(PDO::FETCH_ASSOC)){
//    $key = "EXISTS(SELECT zipcode FROM a5_locations WHERE zipcode=$zipcode)";
//    
//    if ($row[$key] == 1){
//        echo "valid zip";
//    }

$query = "SELECT * FROM a5_locations where zipcode=$zipcode;";

$result = $pdo->query($query);

//Display header
if($result != NULL && $result->rowCount() != 0){
    
    while($row=$result->fetch(PDO::FETCH_ASSOC)){
        $lat1 = $row['latitude'];
        $long1 = $row['longitude'];
        echo "<h1>".$row['location_name'].", ".$row['state']." ".$row['zipcode']."</h1>";
        echo "<hr>";
    }
}
else {
    echo "<h1>Invalid zip code.</h1>";
    exit();
}

$query = "SELECT p.person_name, p.provider_number, GROUP_CONCAT(s.subject_label), l.location_name, l.state, l.zipcode, l.latitude, l.longitude
FROM a5_locations l
Left join a5_people p
	On p.locationID=l.locationID
Left join a5_people_subject ps
	On ps.personID=p.personID
Left join a5_subject s
	On s.subjectID=ps.subjectID
Where SQRT(POWER($lat1 - l.latitude, 2) + POWER($long1 - l.longitude, 2)) * 69 <= 25
Group by p.person_name;";

/*  WORKING QUERY
SELECT p.person_name, p.provider_number, GROUP_CONCAT(s.subject_label), l.location_name, l.state, l.zipcode, 
        SQRT(POWER(42.2776 - l.latitude, 2) + POWER(-83.7409 - l.longitude, 2)) * 69 AS distance
FROM a5_locations l
Left join a5_people p
	On p.locationID=l.locationID
Left join a5_people_subject ps
	On ps.personID=p.personID
Left join a5_subject s
	On s.subjectID=ps.subjectID
Where SQRT(POWER(42.2776 - l.latitude, 2) + POWER(-83.7409 - l.longitude, 2)) * 69 <= 25
Group by p.person_name
Order by distance;
 */

//Where (sqrt(POWER($lat1 - l.latitude, 2) + POWER($long1 - l.longitude, 2)) * 69 <= 25

$result = $pdo->query($query);

//if($result != NULL && $result->rowCount() != 0){
    
    while($row=$result->fetch(PDO::FETCH_ASSOC)){
        $distance = 69 * sqrt(pow($lat1 - $row['latitude'], 2) + pow($long1 - $row['longitude'], 2));
        //echo $distance . " miles, ";
        if ($distance <= 25){
            echo $distance . " miles      |";
            echo $row['person_name'] ."      |";
            echo $row['location_name'] . ", " . $row['state'];
            echo "</br>";
        }
    }
//}

