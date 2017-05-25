<?php

function __autoload($classname){
    //write our own logic for what to do
    //usually look for a file that exists with the class name
    if(file_exists($classname.'.php')){
        include $classname.'.php'; 
    }
}

//autoload is called when php can't find the class
$x = new Cow();