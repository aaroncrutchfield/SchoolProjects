<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class CardCase {
    private $packs;
    private $count = 1;
    
    public function __construct($packs, $numCards, $uncommon, $rare, $legendary) {
        //$this->packs = $packs;
        for ($i = 0; $i < $packs; $i++){
            $pack = new Pack($numCards, $uncommon, $rare, $legendary, $this->count);
            
//            echo $this->count . ' ';
            $this->count = $pack->createCards();
            $pack->printCards();
//            echo $this->count . ' ';
        }
        echo '<hr/>';
    }
    
    public function setPacks(int $packs){
        $this->packs = $packs;
    }
}