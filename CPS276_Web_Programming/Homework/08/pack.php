<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of pack
 *
 * @author AaronC
 */
class Pack {
    //put your code here
    private $numCards;
    private $uncommon;
    private $rare;
    private $legendary;
    private $cards = array();
    private $value;
    private $i;
    
    public function __construct($numCards, $uncommon, $rare, $legendary, $i) {
        $this->numCards = $numCards;
        $this->uncommon = $uncommon;
        $this->rare = $rare;
        $this->legendary = $legendary;
        $this->i = $i;
    }
    
    public function createCards() {
        for ($i = $this->i; ($i-$this->i+1) <= $this->numCards; $i++){
            
            $type = 1;
            
            if ($i % $this->uncommon == 0){
                $type += 2;
            }
            if ($i % $this->rare == 0){
                $type += 6;
            }
            if ($i % $this->legendary == 0){
                $type += 11;
            }
            
            $card = new Card($type);
            $this->cards[] = $card;
            
            //add up value of the pack
            $this->value += $type;
        }
//        echo $i. ' ';
        return $i;
    }
    
    public function printCards(){
        foreach($this->cards as $card){
            echo $card->getType() . ' ';
        }
        echo '<br/>';
    }
    
    
}
