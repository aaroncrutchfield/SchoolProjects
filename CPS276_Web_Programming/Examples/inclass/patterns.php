<?php

echo "design patterns! </br>";

//singleton example
//because we can't make an instance of this class, we can only call static methods
class Counter{
    
    //the only instance of this class that can exist
    protected static $instance;

    //close off constructor
    private function __construct() {}
    
    //clone is another way of making an instance of a class
    private function __clone() {}
    
    //method to get the one instance of the class
    public static function get(){
        if (!isset(self::$instance)){
            //creates an object of this class without using the construct method
            self::$instance = new self();
        }
        return self::$instance;
    }
    
    //Counter object code
    private $count = 0;
    public function increment(){
        $this->count++;
    }
    
    public function getCount(){
        return $this->count;
    }
    
}

$c = Counter::get();
$c->increment();
//echo $c->getCount() .'</br>';
$c->increment();
$c = null;

//even though we lose the counter, when we get it back, it retains its count value
$d = Counter::get();
$d->increment();
//echo $d->getCount().'</br>';




//data mapper
//$fred = new Client();
//$fred->name = "fred";
//$fred->email = "fred@fred.com";
//$fred->save();
//
//$sql = "INSERT INTO clients SET name='fred',"
//        . "email = 'fred@fred.com'";
//$pdo->query($sql);



//factory example
abstract class Person{
    public $coolness;
}

class Doctor extends Person{
    public $coolness = 5;
}

class Lawyer extends Person{
        public $coolness = -5;
}

class SoftwareDeveloper extends Person{
        public $coolness = 1000;
}


class PersonFactory{
    public static function makePerson(){
        $r = rand(1, 3);
        if($r = 1){
            return new Doctor();
        }
        else if ($r = 2){
            return new Lawyer();
        }
        else {
            return new SoftwareDeveloper();
        }
    }
}

$person = PersonFactory::makePerson();
//print_r($person);



//vistor example

class Visitor{
    public $lowest = 999999;
    public $highest = 0;
    public $totalValue;
    public $houseCount;
    
    
}

class House {
    
    private $price;
    
    public function __construct() {
        $this->price = rand(50000, 750000);
    }
    
    public function getPrice(){
        return $this->price;
    }
    
    //method to take visitor
    public function process(Visitor $v){
        if ($this->price < $v->lowest){
            $v->lowest = $this->price;
        }
        if ($this->price > $v->highest){
            $v->highest = $this->price;
        }
        
        $v->totalValue += $this->price;
        $v->houseCount++;
    }
}


$arr = array();
for($i = 0; $i < 10; $i++){
    $arr[] = new House();
}

$visitor = new Visitor();

foreach ($arr as $house){
    echo $house->getPrice().'</br>';
    
    //pass the visitor to each house
    $house->process($visitor);
}

echo "Lowest price is $". number_format($visitor->lowest) ."</br>";
echo "Highest price is $". number_format($visitor->highest) ."</br>";

$a = $visitor->totalValue / $visitor->houseCount;
echo "Average price is $" . number_format($a);


echo "<hr/>";
echo "FizzBuzz (simple method)<br/>";

for($i = 1; $i <= 1000; $i++){
    $f = true;
    if($i % 5 == 0){
        echo "fizz ";
        $f = false;
    }
    if($i % 7 == 0){
        echo "buzz ";
        $f = false;
    }
    if ($f){
        echo $i. ' ';
    }
}

echo "<hr/>";
echo "FizzBuzz (visitor method)<br/>";

class Node {
    private $num;
            
    public function __construct($num) {
        $this->num = $num;
    }
    
    public function __toString(){
        return $this->num . ' ';
    }
    
    public function process(Fizzer $f){
        $result = $f->increment;
    }
}

class Fizzer{
    private $fizzCount = 0;
    private $buzzCount = 0;
    
    public function increment(){
         
    }
}

$arr = array();
for ($i = 1; $i <= 1000; $i++){
    $arr[] = new Node($i);
}

foreach ($arr as $node){
    echo $node;
    
}