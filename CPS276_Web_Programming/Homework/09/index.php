<?php

//access db
include_once 'db_connect.php';

//******add filter if time permits*****//



//show all inventory by default
$query = "SELECT * FROM inventory ORDER BY partNumber";

print_r($query);
$statement = $pdo->prepare($query);
$result = $statement->execute();


if($result != NULL){
    echo "<br>Results found: ". $result->rowCount();
    if ($result->rowCount() == 0){
        exit();
    }
}
else {
    echo "<br>Results Found: 0";
    exit();
}

?>

<!--table for matches-->
<table border="1">
    <tr>
        <th>Part Number</th>
        <th>Last Produced</th>
        <th>Total Quantity</th>
        <th>Description</th>
        <th>Location</th>
        <th>Tote Size</th>
    </tr>
    
    <?php
    //need to set default timezone
    //http://php.net/manual/en/function.date-default-timezone-set.php
    //date_default_timezone_set('America/Detroit');
    
    while($row=$result->fetch(PDO::FETCH_ASSOC)){

        echo "<tr>";
        echo "<td>".$row['partNumber']."</td>";
        //need logic for most recent date
        echo "<td>".$row['date']."</td>";
        //need logic for total quantity
        echo "<td>".$row['quantity']."</td>";
        echo "<td>".$row['description']."</td>";
        //maybe leave this out for now
        echo "<td>".$row['location']."</td>";
        echo "<td>".$row['toteSize']."</td>";
        //redo code for link to details
        //echo "<td><a href=details.php?id=".$row['id'].">View Record</a>";
        echo "</tr>";
    }
    
    ?>