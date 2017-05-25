<?php

class Team
{
    public $name;
    public $seed;
    
    /**
     * Class to represent a Team
     * @param String $name
     * @param Integer $seed
     */
    public function __construct($name=null, $seed=null){
        $this->name = $name;
        $this->seed = $seed;
    }
    
    /**
     * Generate a random game score
     * @return Integer
     */
    public function makeScore(){
        $n=0;
        // generate a random score for each half, skewed by seed
        $n += rand(16,50) - $this->seed;
        $n += rand(16,50) - $this->seed;
        return $n;
    }
    
    public function draw($n, $i, $t, $score) {
        $str = '';
        $cname = "p{$n}-{$i}-{$t}";
        $str .= "<div class= 'bracket $cname'>";
        $str .="<span>$score</span>";
        $str .= $this->name ." (".$this->seed.")";
        $str .= "</div>";
        
        return $str;
        
    }
    
}

