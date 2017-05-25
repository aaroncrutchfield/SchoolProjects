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

$result1 = $pdo->query($query);

//Display header
if($result1 != NULL && $result1->rowCount() != 0){
    
    while($row1=$result1->fetch(PDO::FETCH_ASSOC)){
        $lat1 = $row1['latitude'];
        $long1 = $row1['longitude'];
        echo "<h1>".$row1['location_name'].", ".$row1['state']." ".$row1['zipcode']."</h1>";
        echo "lat: ". $lat1 . "</br>";
        echo "long: ". $long1;
        echo "<hr>";
    }
}
else {
    echo "<h1>Invalid zip code.</h1>";
    exit();
}

$query = "SELECT p.person_name, p.provider_number, GROUP_CONCAT(s.subject_label) AS subject, l.location_name, l.state, l.zipcode,
        SQRT(POWER($lat1 - l.latitude, 2) + POWER($long1 - l.longitude, 2)) * 69 AS distance
FROM a5_locations l
Left join a5_people p
	On p.locationID=l.locationID
Left join a5_people_subject ps
	On ps.personID=p.personID
Left join a5_subject s
	On s.subjectID=ps.subjectID
Where SQRT(POWER($lat1 - l.latitude, 2) + POWER($long1 - l.longitude, 2)) * 69 <= 25
Group by p.person_name
Order by distance;";

$result2 = $pdo->query($query);

?> 


<?php if($result2 != NULL && $result2->rowCount() != 0) : ?>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Provider #</th>
            <th>Subjects</th>
            <th>Location</th>
            <th>Zip Code</th>
            <th>Distance</th>
        </tr>
    <?php while($row2=$result2->fetch(PDO::FETCH_ASSOC)) : 
        $distance = $row2['distance']; 
        
        if ($distance <= 25 && $row2['person_name'] != NULL): ?> 

        <tr>
            <td><?php echo $row2['person_name']  ?></td>
            <td><?php echo $row2['provider_number']  ?></td>
            <td><?php echo $row2['subject']  ?></td>
            <td><?php echo $row2['location_name'] . ", " . $row2['state'] ?></td>
            <td><?php echo $row2['zipcode']  ?></td>
            <td><?php echo number_format($distance, 2) . " miles" ?></td>
        </tr>
       
       <?php 
       endif;
       endwhile;

       
       echo "<pre>";
       print_r($row2);
       echo "</pre>";

       endif; ?>
    


