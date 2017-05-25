<?php
//**HOW TO MATCH CHARACTERS**//


//for convenience
$br = "</br>";

//How to match special characters
//****************************************//
//     \\     Backslash character
//     \/     Forward slash
//     \t     Tab
//     \n     New line
//     \r     Carriage return
//     \f     Form feed
//     \xhh   The Latin-l character whose valuse is the two hexadecimal digits 
//****************************************//


//Special characters
$string = "© 2010 Mike's Musc. \ All rights reserved (5/2010).";
echo preg_match('/\xa9/', $string) .$br;      //Matches © and returns 1
echo preg_match('///', $string) .$br;         //Returns false and issues a warning
echo preg_match('/\//', $string) .$br;        //Matches / and returns 1
echo preg_match('/\\\\/', $string) .$br;      //matches \ and returns 1


//How to match types of characters
//****************************************//
//     -        Any single character except a new line character
//              (use \. to match a period)
//     \w       Any letter, number, or the underscore
//     \W       Any character that's not a letter, number or the underscore
//     \d
//     \
//     \
//     \