<?php

//controller for managing board games
class Game {
    
    public $mygame;
    public $games;
    //apart of the controller because these are actions that people work with
    
    //CRUD
    //create, read, update, delete
    
    
    public function listing() {
        //show a list of games
        
        //prepare a collection of all games
        $this->games = new boardgameCollection();
        //pass this to the view
        View::setTemplate('gamelisting');
    }
    
    public function add(){
        //a form to add a game
        
    }
    
    public function edit($id){
        //to edit a game known to us as $id
        
        //load that record
        
    }
    
    public function details($id){
        //data about the game known to us as $id
        $g = new boardgame($id);
//        $title = $g->title;     //get with magic getter
//        $g->subtitle = "this is a fun game";    //set with magic setter
//        $g->save();     //saves the object as is in the database
        
        //load that record
        $this->mygame = $g;
        View::setTemplate('gamedetails');
    }
    
    public function delete($id){
        //delete a record
    }
}