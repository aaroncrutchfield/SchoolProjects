<?php
//**HOW TO USE THE CHARACTER CLASS**//

//for convenience
$br = "</br>";

//The character class
$string = 'The product code is MBT-3461';
echo preg_match('/MB[TF]/', $string).$br;        //Matcvhes MBT and returns 1
echo preg_match('/[.]/', $string).$br;           //Matches . and returns 1
echo preg_match('/[13579]/', $string).$br.$br;       //Matches 3 and returns 1

//Metacharacters inside a character class
//****************************************//
//  character   example     meaning
//      ^       [^aeiou]    Negates the list of characters inside the character class           
//      -       [a-z]       Creates a range of characters based on their Latin-l character set values
//****************************************//

//Metacharacters
echo preg_match('/MB[^TF]/', $string).$br;      //Matches nothing and returns 0
echo preg_match('/MBT[^^]/', $string).$br;      //Matches MBT- and returns 1
echo preg_match('/MBT-[1-5]/', $string).$br;    //Matches MBT-3 and returns 1
echo preg_match('/MBT[_*-]/', $string).$br.$br;     //Matches MBT- and retunrs 1

//How to use bracket expressions in a character class
//****************************************//
//  Pattern         Matches
//  [:digit:]       Digits (same as \d)
//  [:lower:]       Lower case letters
//  [:upper:]       Upper case letters
//  [:letter:]      Upper and lower case letters
//  [:alnum:]       Upper ane lower case letters and digits
//  [:word:]        Upper and lower case letters, digits, and the underscores (same as \w)
//  [:print:]       All printable characters including the space
//  [:graph:]       All printable characters excluding the space
//  [:punct:]       All printable characters excluding letters and digits
//****************************************//

//Bracket expressions
echo preg_match('/MBT[[:punct:]]/', $string).$br;    //Matches MBT- and returns 1
echo preg_match('/MBT[[:digit:]]/', $string).$br;   //Matches nothing and returns 0
echo preg_match('/MB[[:upper:]]/', $string).$br;   //Matches MBT and returns 1

//Description
//• A character class lets you match a single character from a list of possible characters. 
//• Most characters with a special meaning lose it inside the brackets. The caret and dash are 
//  the only characters that retain their special meanings, and they are position dependent.
//• You can use a bracket expression inside a character class for several predefined character 
//  ranges, and you can combine several bracket expressions in one character class.