<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Card {
    private $type;
    
    public function __construct($type) {
        switch ($type) {
            case 1:
                $this->type = 'COM';
                break;
            case 3:
                $this->type = 'UNC';
                break;
            case 7:
                $this->type = 'RAR';
                break;
            case $type > 7:
                $this->type = 'LEG';
                break;
        }
    }
    
    public function getType() {
        return $this->type;
    }
    
}