<?php

//model
class part {
    
    private $partID;
    private $partNumber;
    private $date;
    private $quantity;
    private $description;
    private $toteSize;
    private $location;
    
    public function __construct($id=null) {
        //if we provide an id number, we will load that 
        //id from the from the database
        if ($id > 0){
            //load data from database
            
            $pdo = DB::get();   //get database connection
            
            $sql = "SELECT * FROM inventory WHERE partID=?";
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
    
    //return an array of parts with given partNumber
    public function getParts($partNumber){
        $pdo = DB::get();   //get database 
        $parts = array();
            
        $sql = "SELECT * FROM inventory WHERE partNumber=?";
        $query = $pdo->prepare($sql);
        $query->execute(array($partNumber));    //always have to pass array when using execute
        if ($query->rowCount() > 0){   //if the query returns a result
            $assoc = $query->fetchAll(PDO::FETCH_ASSOC);   //store the results into an assoc array
            for($i =0; $i < $query->rowCount(); $i++){
                $p = new part($assoc[$i]['partID']);
                $parts[] = $p;
            }
        }

        return $parts;
    }
    
    
    public function __get($key) {
        
        return $this->{$key};     //easy way

    }
    
    public function __set($key, $value) {
        //if the input is a date, format the date
        if ($key == "date"){
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
        if($this->partID > 0){
            //update database
            $sql="UPDATE inventory SET partNumber=?, "
                    . "date=?, "
                    . "quantity=?, "
                    . "description=?, "
                    . "toteSize=?, "
                    . "location=? ";
            $params = array($this->partNumber,
                            $this->date,
                            $this->quantity,
                            $this->description,
                            $this->toteSize,
                            $this->location);
            $query = $pdo->prepare($sql);
            $query->execute($params);
                    
        } else {
            //insert
            $sql="INSERT INTO inventory SET partNumber=?, "
                    . "date=?, "
                    . "quantity=?, "
                    . "description=?, "
                    . "toteSize=?, "
                    . "location=? ";
            $params = array($this->partNumber,
                            $this->date,
                            $this->quantity,
                            $this->description,
                            $this->toteSize,
                            $this->location);
            $query = $pdo->prepare($sql);
            $query->execute($params);
            $this->partID= $pdo->lastInsertId();    //returns the id we just inserted into the database
//            echo $pdo->lastInsertId();
            
            
        }
    }
    
    //gets stuff out of the database and put stuff back
    
    
    
    //need methods to retreive the data
}