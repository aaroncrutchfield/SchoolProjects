<?php
interface Nameable{
    public function getName();
}

interface Ageable{
    public function age();
}



abstract class Person implements Nameable, Ageable{
    //private properties are private even to children classes
    //protected is private but available to children classes
    protected $firstName;
    protected $lastName;
    
    public function __construct($firstName, $lastName) {
        $this->firstName = $firstName;
        $this->lastName = $lastName;
    }
    
    public function getName(){
        return $this->firstName .' '. $this->lastName;
    }
    
    public function __get($name) {
        if ($name == 'firstName'){
            return $this->firstName;
        }
        if ($name == 'lastName'){
            return $this->lastName;
        }
        
    }
    
    public function __set($name, $value) {
        if ($name =='firstName'){
            $this->firstName = ucfirst($value);
        }
        if ($name =='lastName'){
            $this->lastName = ucfirst($value);
        }
    }
    
    public function __call($name, $arguments) {
        return "OK!";
    }
    
    public function __toString() {
        return $this->getName();
    }

}



class Doctor extends Person{
    //no constructor here so it uses one from a class this one extends
    public function getName() {
        $name = parent::getName();
        return $name .' M.D.';
    }

    public function age() {
        
    }

}


class Place implements Nameable{
    public function getName() {
        
    }

}

class Container {
    public function addThing(Nameable $thing){
        $str = $thing->getName();
    }
}

$me = new Doctor("fred", "rabbit");
//echo $me->getName();
//$me->lastName = 'jones';
//echo $me->getName();

echo $me->jump();