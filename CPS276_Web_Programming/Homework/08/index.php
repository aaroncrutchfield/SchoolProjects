<?php

session_start();

//put this check in a function in a centralized location
//include the file that has the function
//then call the function from the included file
if(isset($_SESSION['userid']) && $_SESSION['userid'] != NULL){
    //this is a valid user
    
} else {
    //not logged in
    include('login.php');
    exit();
    
    //****or****//
    header("location: login.php");  //instructs the browser to go to another location
                                    //not even a back button can get them back to previous page
    exit();
}



include('card.php');
include('cardcase.php');
include('pack.php');

$continue = FALSE;
if(isset($_POST['cases'])){
    $cases = $_POST['cases'];
    $continue = TRUE;
}else{
    $cases = '';
    $continue = FALSE;
}

if(isset($_POST['packs'])){
    $packs = $_POST['packs'];
    $continue = TRUE;
}else{
    $packs = '';
    $continue = FALSE;
}

if(isset($_POST['baseNum'])){
    $cardnum = $_POST['baseNum'];
    $continue = TRUE;
}else{
    $cardnum = '';
    $continue = FALSE;
}

if(isset($_POST['uncommon'])){
    $uncommon = $_POST['uncommon'];
    $continue = TRUE;
}else{
    $uncommon = '';
    $continue = FALSE;
}

if(isset($_POST['rare'])){
    $rare = $_POST['rare'];
    $continue = TRUE;
}else{
    $rare = '';
    $continue = FALSE;
}

if(isset($_POST['legendary'])){
    $legendary = $_POST['legendary'];
    $continue = TRUE;
}else{
    $legendary = '';
    $continue = FALSE;
}
?>

<!--Form for user input-->
<form method="post" action="index.php">
    1. Total number of Cases:
    <input type="number" name="cases" value="<?=$cases?>">
    <br/>
    2. Number of Packs in each case:
    <input type="number" name="packs" value="<?=$packs?>">
    <br/>
    3. Base number of cards in each Pack:
    <input type="number" name="baseNum" value="<?=$cardnum?>">
    <br/>
    4. Frequency of "Uncommon" cards:
    <input type="number" name="uncommon" value="<?=$uncommon?>">
    <br/>
    5. Frequency of "Rare" cards:
    <input type="number" name="rare" value="<?=$rare?>">
    <br/>
    6. Frequency of "Legendary" cards:
    <input type="number" name="legendary" value="<?=$legendary?>">
    <br/>
    <input type="submit" value="Submit">
    
</form>

<?php
//create case
if ($continue){
//    $typeCounts = array();
    for ($i = 0; $i < $cases; $i++){
        $case = new CardCase($packs, $cardnum, $uncommon, $rare, $legendary);

    }

}else{
    exit(0);
}
