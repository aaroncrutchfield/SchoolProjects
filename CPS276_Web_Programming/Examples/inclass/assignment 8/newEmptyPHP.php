<?php

class Pack {
    public $cards= array();
    
    public function __construct($numCards) {
        for($i = 0; $i < $numCards; $i++){
            $this->cards[] = new Card();
        }
    }
    
    public function getValue(){
        //give value of pack
        $value = 0;
        
        foreach($cards as $card){
            if ($card->type == 'COM') $value += 1;
            if ($card->type == 'UNC') $value += 3;
            if ($card->type == 'RAR') $value += 7;
            if ($card->type == 'LEG') $value += 12;
        }
        
        return $value;
    }
    
    public function draw(){
        //draw all cards plus line break
        foreach ($cards as $card) {
            $card->draw();
        }
        echo "<br/>";
    }
    
    public function takeVisitor($visitor){
        foreach($this->cards as $card){
            $card->takeVisitor($visitor);
        }
        $value = $this->getValue();
        $visitor->storeValue($value);
        
    }
}

class Card {
    public $type = 'COM';
    
    public function draw(){
        echo $this->type . ' ';
    }
    
    public function takeVisitor($visitor){
        //ask the visitor what kind of card we are
        $this->type = $visitor->whatAmI();
    }
}

class Visitor {
    //trigger for different card types
    //figure out value of pack (work on getting avergae value)
    
    private $freqUNC;
    private $freqRAR;
    private $freqLEG;
    private $cUNC = 0;
    private $cRAR = 0;
    private $cLEG = 0;
    
    private $totalValue=0;
    private $totalPacks=0;
    public function __construct($freqUNC, $freqRAR, $freqLEG) {
        $this->freqUNC = $freqUNC;
        $this->freqRAR = $freqRAR;
        $this->freqLEG = $freqLEG;
    }
    
    public function whatAmI(){
        $this->cUNC++;
        $this->cRAR++;
        $this->cLEG++;
        $output = 'COM';
        
        if($this->cUNC == $this->freqUNC){
            $output = 'UNC';
            $this->cUNC = 0;
        }
        
        
        if($this->cRAR == $this->freqRAR){
            if ($output =='UNC'){
                $output = 'LEG';
            } else{
                $output = 'RAR';
                $this->cRAR = 0;
            }
        }
        if($this->cLEG == $this->freqLEG){
            $output = 'UNC';
            $this->cLEG = 0;
        }
        
        return $output;
        
    }
    
    public function storeValue($v){
        $this->totalValue += $v;
        $this->totalPacks ++;
    }
    
    public function getAverage(){
        return $this->totalValue / $this->totalPacks;
    }
    
}

//setup the packs
$packs = array();
for($p = 0; $p < $totalPacks; $p++){
    $packs[] = new Pack($cardnum);
}

//setup visitor
$vistor = new Visitor($freqUNC, $freqRAR, $freqLEG);
foreach ($packs as $pack){
    $pack->takeVisitor($visitor);
}


$avg = $visitor->getAverage();
echo "The average value is $avg";

// output
foreach ($packs as $pack){
    $pack->draw();
}