<?php

Class Rabbit { public $name; public function __construct($name){ $this->name=$name;} public function __toString(){ return $this->name; } } $fred = new Rabbit('Fred'); $bill = $fred; $bill->name = 'Bill'; echo $fred;