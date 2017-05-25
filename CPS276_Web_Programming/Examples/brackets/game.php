<?php

class Game 
{
    
    private $teamA;
    private $teamB;
    private $winner=0;
    private $teamAScore=0;
    private $teamBScore=0;
    
    /**
     * Class to represent a basketball game
     * @param Team $teamA
     * @param Team $teamB
     */
    public function __construct(Team $teamA, Team $teamB){
        $this->teamA = $teamA;
        $this->teamB = $teamB;
    }
    
    /**
     * Play the game!
     * @return string
     */
    public function play(){
        // playing the game
        $commentary="</br>Starting a game<br/>";
        $commentary.= $this->teamA->name." is playing "
                      .$this->teamB->name."<br/>";
        do {
            $this->teamAScore = $this->teamA->makeScore();
            $this->teamBScore = $this->teamB->makeScore();
        } while($this->teamAScore == $this->teamBScore);
        
        $commentary.="Final Score: ".$this->teamA->name." ".$this->teamAScore;
        $commentary.=" ".$this->teamB->name." ".$this->teamBScore."<br/>";
        
        if($this->teamAScore > $this->teamBScore){
            $this->winner = 1;
            $commentary .= $this->teamA->name." wins!";
        } else {
            $this->winner = 2;
            $commentary .= $this->teamB->name." wins!";
        }
        return $commentary;
    }
    
    public function getWinner(){
        if ($this->winner == 1){
            return $this->teamA;
        }
        elseif ($this->winner == 2){
            return $this->teamB;
        }
    }
    
    public function draw($n, $i){
        $str = '';
        
        $str .= $this->teamA->draw($n, $i, 1, $this->teamAScore);
        $str .= $this->teamB->draw($n, $i, 2, $this->teamBScore);
        return $str;
    }
    
}

