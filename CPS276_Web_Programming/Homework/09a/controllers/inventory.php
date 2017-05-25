<?php

//controller for managing parts
class inventory {
    
    //apart of the controller because these are actions that people work with
    
    //CRUD
    //create, read, update, delete
    public $result;
    public $listingCount;
    public $query;
    public $mypart;
    public $parts;
    
    public function listing() {
        //show a list of parts
        $pdo = DB::get();   //get database connection
        
        //sum the `quantity` in the sql statement
        $sql = "SELECT partNumber, description, toteSize, SUM(quantity) as quantity 
            FROM inventory 
            GROUP BY partNumber";
        $this->query = $pdo->prepare($sql);
        $this->query->execute();
        
        //set the view
        View::setTemplate('listing_template');
    }
    
    public function add(){
        //a form to add a game
        $p = new part();
        $this->mypart = $p;
        View::setTemplate('addpart');
        
        
        
    }
    
    public function edit($id){
        //to edit a game known to us as $id
        
        //load that record
        View::setTemplate('addpart');
    }
    
    public function details($partNumber){
        //details on all parts with matching number
        $this->parts = part::getParts($partNumber);
//        echo '<pre>';
//        print_r($this->parts);
//        echo '</pre>';
//        
        
        
        //load that record
//        $this->mypart = $p;
        View::setTemplate('partdetails');
    }
    
    //TODO
    //generates 'delete' class not found error
    public function delete($id){
        //delete a record
        $pdo = DB::get();
        
        $this->mypart = new part($id);
        
        $sql = "DELETE FROM inventory "
             . "WHERE partID=$id";
        $this->query = $pdo->prepare($sql);
        
        
        //set the view
        View::setTemplate('deletepart');
    }
    
}