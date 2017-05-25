<?php

class Hello {
   //public properties are passed into view as local variables
   public $today;
    
   public function world(){
      
      View::setTemplate('hello_template');
      
      $this->today = date('n/j/Y');
      
   }
   
   public function kitty($name=null){
       $this->kittyname = $name;
       View::setTemplate('mynewtemplate');
   }
   
}