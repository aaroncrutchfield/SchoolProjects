<?php
//**HOW TO USE REGULAR EXPRESSIONS**//


//for convenience
$br = "</br>";

//How to create a regular expression
$pattern = '/Harris/';

//Two strings to test
$author = 'Ray Harris';
$editor = 'Joel Murach';

//How to use the preg_match method to search for the pattern
$author_match = preg_match($pattern, $author);      //$author_match is 1
$editor_match = preg_match($pattern, $editor);      //$editor_match is 0
echo " author_match = " . $author_match .$br;
echo " editor_match = " .$editor_match .$br;

//How to test for errors in a regular expression
if ($author_match === false){
    echo 'Error testing author name.'.$br;
}
else if ($author_match === 0) {
    echo 'Author names does not contain Harris'.$br;
}
else {
    echo 'Author name contains Harris'.$br;
}

//A case-insensitive regular expression
$pattern = '/murach/i';

//How to use a case-insensitive regular expression
$editor_match = preg_match($pattern, $editor);        //editor_match is 1
echo " editor_match = " .$editor_match .$br;

//Description
//• A regular expression defines a pattern that can be searched for in a string. 
//  The pattern is case sensitive and enclosed in forward slashes in a text string.
//• To create a case-insensitive regular expression, add an i modifier to the end 
//  of the regular expression. Then, the case is ignored in both the pattern and string.

