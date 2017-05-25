<?php

class boardgame {
    
    private $gameID;
    private $title;
    private $subtitle;
    private $minPlayers;
    private $maxPlayers;
    private $releaseDate;
    private $type;
    private $publisher;
    private $description;
    private $ageRating;
    
    public function __construct($id=null) {
        //if we provide an id number, we will load that 
        //id from the from the database
        if ($id > 0){
            //load data from database
            
            $pdo = DB::get();   //get database connection
            
            $sql = "SELECT * FROM games WHERE gameID=?";
            $query = $pdo->prepare($sql);
            $query->execute(array($id));    //always have to pass array when using execute
            if ($query->rowCount() == 1){   //if the query returns a result
                $assoc = $query->fetch(PDO::FETCH_ASSOC);   //store the results into an assoc array
                foreach ($assoc as $key => $value) {
                    $this->{$key} = $value;     //curly brakets make it easier to read
                                                //we are dynamically populating variable name
                                                //because we used the same variable names as the table
                    
                }
            }
        }
    }
    
    
    public function __get($key) {
        //hard way
        //don't need break in switch statement when only returnig values
        switch ($key) {
            case 'gameID': return $this->gameID;
            case 'title': return $this->title;
            case 'subtitle': return $this->subtitle;
            case 'minPlayers': return $this->minPlayers;
            case 'maxPlayers': return $this->maxPlayers;
            case 'releaseDate': 
                $ts = strtotime($this->releaseDate);
                if(!$ts){ 
                    return "unknown";
                }
                $date = date('n/j/Y', $ts);
                return $date;
            case 'type': return $this->type;
            case 'publisher': return $this->publisher;
            case 'description': return $this->description;
            case 'ageRating': return $this->ageRating;
        }
    }
    
    public function __set($key, $value) {
        //if the input is release date, format the date
        if ($key == "releaseDate"){
            $ts = strtotime($value);
            if (!$ts){
                $value = NULL;
            } else{
                $value = date('Y-m-d', $ts);
            }
        }
        $this->{$key} = $value;     //easy way
    }
    
    //need methods to store the data
    public function save(){
        $pdo = DB::get();
        if($this->gameID > 0){
            //update database
            $sql="UPDATE games SET title=?, "
                    . "subtitle=?, "
                    . "minPlayers=?, "
                    . "minPlayers=?, "
                    . "releaseDate=?, "
                    . "type=?, "
                    . "publisher=?, "
                    . "description=? "
                    . "ageRating=?"
                    . "WHERE gameID=?";
            $params = array($this->title,
                            $this->subtitle,
                            $this->minPlayers,
                            $this->maxPlayers,
                            $this->releaseDate,
                            $this->type,
                            $this->publisher,
                            $this->description,
                            $this->ageRating,
                            $this->gameID);
            $query = $pdo->prepare($sql);
            $query->execute($params);
                    
        } else {
            //insert
            $sql="INSERT INTO games SET title=?, "
                    . "subtitle=?, "
                    . "minPlayers=?, "
                    . "minPlayers=?, "
                    . "releaseDate=?, "
                    . "type=?, "
                    . "publisher=?, "
                    . "description=? "
                    . "ageRating=?";
            $params = array($this->title,
                            $this->subtitle,
                            $this->minPlayers,
                            $this->maxPlayers,
                            $this->releaseDate,
                            $this->type,
                            $this->publisher,
                            $this->description,
                            $this->ageRating);
            $query = $pdo->prepare($sql);
            $query->execute($params);
            $this->gameID= $pdo->lastInsertId();    //returns the id we just inserted into the database
        }
    }
    
    //gets stuff out of the database and put stuff back
    
    
    
    //need methods to retreive the data
    
    
    
}