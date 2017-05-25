<?php

class boardgameCollection {
    
    public $games = array();
    
    public function __construct() {
        //fill games array
        
        //get database
        $pdo = DB::get();
        //make a query
        $sql = "SELECT * FROM games ORDER BY title";
        $query = $pdo->query($sql);
        //create boardgames
        while($g = $query->fetchObject('boardgame')){   //instead of an associative array, the row is converted to the class specified
            $this->games[] = $g;
        }
        
    }
}