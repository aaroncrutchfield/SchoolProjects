<?php

//Main Listing

$ratings=array(
    'kid' => "For Kids",
    'adult' => "For Adults",
    'all_ages' => "For All Ages"
);


// access db
include_once 'db_connect.php';

// query of games
$sql = "SELECT * FROM games ORDER BY title";
$result = $pdo->query($sql);
echo "results found: ". $result->rowCount();

//make a table

?>

<hr/>
<table border="1">
    <tr>
        <th>Title</th>
        <th>Players</th>
        <th>Type</th>
        <th>Rating</th>
    </tr>
    <?php
    
    while($row2=$result->fetch(PDO::FETCH_ASSOC)){
        if($row2['maxPlayers']>0){
    $players = $row2['minPlayers']."-".$row2['maxPlayers']."</td>";
    
}
 else {
    $players = $row2['minPlayers'].'+';
}
        
        echo "<tr>";
        echo "<td>".$row2['title']."</td>";
        echo "<td>".$players;
        echo "<td>".ucfirst($row2['type'])."</td>";
        echo "<td>".$ratings[$row2['ageRating']]."</td>";

    }
    
    ?>
    
</table>

<br/>
<a href="addrecord.php">Add</a>