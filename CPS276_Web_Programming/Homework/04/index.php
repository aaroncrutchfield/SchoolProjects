<?php

// access db
include_once 'db_connect.php';


//need to set default timezone
//http://php.net/manual/en/function.date-default-timezone-set.php
date_default_timezone_set('America/Detroit');

$default = TRUE;

//date range filter
if(isset($_POST['date1'])&& $_POST['date1'] != ''){
    //format date to pass in query
    $date1 = date("Y/m/d", strtotime($_POST['date1']));
    
    if(isset($_POST['date2']) && $_POST['date2'] != ''){
        //format date to pass in query
        $date2 = date("Y/m/d",  strtotime($_POST['date2']));
        
        $query = "SELECT * FROM matches WHERE matchDate BETWEEN '$date1' AND '$date2'";
        
        //format dates to display in text input field
        $date2 = date("m/d/Y", strtotime($date2));
        $date1 = date("m/d/Y", strtotime($date1));
    }
    else{
        $query = "SELECT * FROM matches WHERE matchDate >= '$date1'";
        
         //format dates to display in text input field
        $date1 = date("m/d/Y", strtotime($date1));
        
        $date2 = NULL;
    }
    
    $default = FALSE;
}
else if(isset($_POST['date2']) && $_POST['date2'] != ''){
     //format date to pass in query
     $date2 = date("Y/m/d",  strtotime($_POST['date2']));
     
     $query = "SELECT * FROM matches WHERE matchDate <= '$date2'";
     
     //format dates to display in text input field
     $date2 = date("m/d/Y", strtotime($date2));
     
     $date1 = '';
     $default = FALSE;
}
 else {
     $date1 = '';
     $date2 = '';
}


//name filter
if(isset($_POST['name']) && $_POST['name'] != ''){
    $name = $_POST['name'];
    //if date range filter is already applied append to query
    if (!$default){
        $query = $query . " AND (player1 LIKE '%$name%' OR player2 LIKE '%$name%')"; //ORDER BY matchDate DESC LIMIT 250
    }
    else{
        $query = "SELECT * FROM matches WHERE (player1 LIKE '%$name%' OR player2 LIKE '%$name%')";
    }
    $default = FALSE;
}
else{
    $name = ''; 
}

//match result filter
if(isset($_POST['matchResult'])){
    $matchResult = $_POST['matchResult'];
    echo "<br><br>";
    if ($matchResult === 'any'){
        //don't need to do anything
    }
    else {
        //if date range filter is already applied append to query
        if (!$default){
            $query = $query . " AND result='$matchResult'"; //ORDER BY matchDate DESC LIMIT 250
        }
        else{
            $query = "SELECT * FROM matches WHERE result='$matchResult'";
        }

        $default = FALSE;
    }
    
}



//default query of 250. Order by date descending 
if($default){
    $query = "SELECT * FROM matches ORDER BY matchDate DESC LIMIT 250;";
}
else {
    $query = $query . " ORDER BY matchDate DESC LIMIT 250;";
}

$result = $pdo->query($query);

//possible match outcome
$outcome = array(
    'any' => "Any",
    '1' => "Player 1",
    '2' => "Player 2",
    'D' => "Draw");
?>

<!--limit width of fieldset-->
<!--http://stackoverflow.com/questions/2302495/is-there-any-way-to-have-a-fieldset-width-only-be-as-wide-as-the-controls-in-the-->
<style type="text/css">
    .fieldset-auto-width {
         display: inline-block;
    }
</style>

<div>
    <fieldset class="fieldset-auto-width">
        <legend>Filter:</legend>
        <form method="post" action="index.php">
            Date Range <br>
            <input type="text" name="date1" value="<?=$date1?>">
            <input type="text" name="date2" value="<?=$date2?>">
            <br>

            Name Search <br>
            <input type="text" name="name" value="<?=$name?>">
            <br>

            Match Result <br>
            <select name="matchResult">
                <?php
                foreach($outcome as $key => $value){
                    $s ='';
                    if($key == $_POST['matchResult']){
                        $s = "selected";
                    }
                    else {
                        $s = '';
                    }
                    echo "<option value='$key' $s>$value</option>";
                }
                ?>
            </select>
            <br><br>

            <input type="submit">
        </form>
    </fieldset>
<div/>

<?php
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
        <th>Date</th>
        <th>Player 1</th>
        <th>Player 2</th>
        <th>Result</th>
        <th>ECO code</th>
        <th>Details</th>
    </tr>
    
    <?php
    //need to set default timezone
    //http://php.net/manual/en/function.date-default-timezone-set.php
    date_default_timezone_set('America/Detroit');
    
    while($row2=$result->fetch(PDO::FETCH_ASSOC)){
        $matchDate = date("m/d/Y", strtotime($row2['matchDate']));
        //$id = $row['id'];
        echo "<tr>";
        echo "<td>".$matchDate."</td>";
        echo "<td>".$row2['player1']."</td>";
        echo "<td>".$row2['player2']."</td>";
        echo "<td>".$row2['result']."</td>";
        echo "<td>".$row2['eco']."</td>";
        echo "<td><a href=details.php?id=".$row2['id'].">View Record</a>";
        //TODO
        //details page link
        echo "</tr>";
    }
    
    ?>
    
</table>