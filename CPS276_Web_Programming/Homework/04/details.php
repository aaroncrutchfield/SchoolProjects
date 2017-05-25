<?php

//need id of chess game
$id = $_REQUEST['id'];


// access db
include_once 'db_connect.php';

$query = "SELECT * FROM matches WHERE id=$id;";

//print_r($query);
$result = $pdo->query($query);
//print_r($result);

$row2=$result->fetch(PDO::FETCH_ASSOC);

$outcome = array(
    'any' => "Any",
    '1' => "Player 1",
    '2' => "Player 2",
    'D' => "Draw");

?>

<a href=index.php>Back</a>
<br><br>

<h1><?=$row2['event']?></h1>


<table border="1" width="500">
    <tr>
        <th>Event Name</th>
        <td><?=$row2['event']?></td>
    </tr>
    <tr>
        <th>Event Site</th>
        <td><?=$row2['site']?></td>
    </tr>
    <tr>
        <th>Date</th>
        <td><?php echo date("m/d/Y", strtotime($row2['matchDate']))?></td>
    </tr>
    <tr>
        <th>Round Number</th>
        <td><?=$row2['round']?></td>
    </tr>
    <tr>
        <th>Player 1 Name</th>
        <td><?=$row2['player1']?></td>
    </tr>
    <tr>
        <th>Player 1 ELO</th>
        <td><?=$row2['player1Elo']?></td>
    </tr>
    <tr>
        <th>Player 2 Name</th>
        <td><?=$row2['player2']?></td>
    </tr>
    <tr>
        <th>Player 2 ELO</th>
        <td><?=$row2['player2Elo']?></td>
    </tr>
    <tr>
        <th>Result</th>
        <td><?php echo $outcome[$row2['result']]?></td>
    </tr>
    <tr>
        <th>Opening Move</th>
        <td><?=$row2['opening']?></td>
    </tr>
    <tr>
        <th>ECO</th>
        <td><?=$row2['eco']?></td>
    </tr>
    <tr>
        <th>Moves</th>
        <td><?=$row2['moves']?></td>
    </tr>
</table>
    


