<?php

class Round
{
    
    private $teams = array();
    private $games = array();
    private $nextRound;
    
    public function play(){
        $teamNum = count($this->teams);
        $message = "Starting a round with $teamNum teams...</br>";
        
        //create games
        for($i = 0; $i < $teamNum; $i+=2){
            $teamA = $this->teams[$i];
            $teamB = $this->teams[$i+1];
            $game = new Game($teamA, $teamB);
            $this->games[] = $game;
        }
        $gamesNum = count($this->games);
        $message .= "Playing $gamesNum games...";
        
        //play games
        foreach($this->games as $game){
            $string = $game->play();
            $message .= $string.'</br>';
        }
       
        //another round
        if ($gamesNum > 1){
            $this->nextRound = new Round();
            foreach($this->games as $game){
                $winner = $game->getWinner();
                $this->nextRound->addTeam($winner);
            }
            $string = $this->nextRound->play();
            $message .= $string;
        }     
        return $message;
    }
    
    public function addTeam(Team $team){
        $this->teams[] = $team;
    }
    
    public function draw(){
        $str = '';
        //draw all games
        $n = count ($this->games); 
        $i = 0;
        foreach($this->games as $game){
            $str .= $game->draw($n, $i);
            $i++;
        }
        
        //draw next round
        if ($this->nextRound){
            $str .= $this->nextRound->draw();
        }
        return $str;
    }
}
