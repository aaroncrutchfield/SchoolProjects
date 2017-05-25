<link rel="stylesheet" type="text/css"href="bracket_style.css"/>
<?php
// define classes
include('game.php');
include('team.php');
include('round.php');

// make two teams and play a match

//$teamA = new Team("Maryland",5);
//$teamB = new Team("South Dakota State",12);
//
//$game = new Game($teamA, $teamB);
//echo $game->play();

$round = new Round();

$team_string = file_get_contents("brackets.txt");
$arr = explode("\n", $team_string);
foreach ($arr as $line){
    $parts = explode(",", $line);
    $seed = $parts[0];
    $name = $parts[1];
    $team = new Team($name, $seed);
    
//    echo "<pre>";
//    print_r($team);
//    echo "</pre>";
    
    $round->addTeam($team);
}

echo "<div class= 'commentary'>".$round->play()."</div>";
echo $round->draw();