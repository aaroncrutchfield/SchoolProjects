<?php

// Regular Expressions

#Example 1
//string that contains $pattern
$pattern = '/gr.y/';
$string = "I saw a grey owl";

echo "trying pattern '$pattern' against string '$string' </br>";

$result = preg_match($pattern, $string);
if($result){
    echo "matched";
}
else{
    echo "did not match!";
}
echo "<hr/>";



#Example 2
//string that starts with $pattern
$pattern = '/^gr.y/';
$string = "I saw a grey owl";

echo "trying pattern '$pattern' against string '$string' </br>";

$result = preg_match($pattern, $string);
if($result){
    echo "matched";
}
else{
    echo "did not match!";
}
echo "<hr/>";



#Example 3
//string that contains a dollar sign followed by a number
//use single quotes around regular expression. Otherwise you have to use double 
//backslashes
$pattern = '/\$\d/';
$string = "I saw a grey owl for sale for $5.99 at Target";

echo "trying pattern '$pattern' against string '$string' </br>";

$result = preg_match($pattern, $string);
if($result){
    echo "matched";
}
else{
    echo "did not match!";
}
echo "<hr/>";



#Example 4
//string that contains a word that starts with a capital letter, has any number
//of letters, and ends with 'ard''
$pattern = '/[A-Z].[a-z0-9]*ard/';
$string = "My favorite Pokemon is Charizard";

echo "trying pattern '$pattern' against string '$string' </br>";

$result = preg_match($pattern, $string);
if($result){
    echo "matched";
}
else{
    echo "did not match!";
}
echo "<hr/>";



#Example 5
//string that contains a date with 1-2 digits, followed by a '/', followed by 
//1-2 digits, followed by a '/', followed by 4 digits
$pattern = '/[0-9]{1,2}\/[0-9]{1,2}\/[0-9]{4}/';
$string = "The date today is 3/12/2016 (Saturday!)";

echo "trying pattern '$pattern' against string '$string' </br>";

$result = preg_match($pattern, $string);
if($result){
    echo "matched";
}
else{
    echo "did not match!";
}
echo "<hr/>";



#Example 6
//string that contains a pound sign followed by 6 characters 'A-F' or 0-9
$pattern = '/#[A-F0-9]{6}/';
$string = "My favorite color is #2266CC";

echo "trying pattern '$pattern' against string '$string' </br>";

$result = preg_match($pattern, $string);
if($result){
    echo "matched";
}
else{
    echo "did not match!";
}
echo "<hr/>";



#Example 7
//string that contains a pound sign followed by 6 characters 'A-F' or 0-9
$pattern = '/#[A-F0-9]{6}/';
$string = "My favorite color is #2266CC. "
        . "Your favorite color is #CC9944. "
        . "Nobody likes #887601 anymore.";

echo "trying pattern '$pattern' against string '$string' </br>";

$matches=array();
$result = preg_match_all($pattern, $string, $matches);
if($result){
    echo "matched</br>";
}
else{
    echo "did not match!</br>";
}
echo "<pre>";
print_r($matches);
echo "<hr/>";



#Example 8
//everything in the parenthasis is what is actually pulled out
$pattern = '/=([0-9a-zA-Z]*)/';
$string = "Let x=5, y=2, z=llamas";

echo "trying pattern '$pattern' against string '$string' </br>";

$matches=array();
$result = preg_match_all($pattern, $string, $matches);
if($result){
    echo "matched</br>";
}
else{
    echo "did not match!</br>";
}
echo "<pre>";
print_r($matches);
echo "<hr/>";


#Example 9
$pattern = '/([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{4})/';
$string = "The date today is 3/12/2016 (Saturday!)";

echo "trying pattern '$pattern' against string '$string' </br>";

$matches=array();
$result = preg_match_all($pattern, $string, $matches);
if($result){
    echo "matched</br>";
}
else{
    echo "did not match!</br>";
}
echo "<pre>";
print_r($matches);
echo "<hr/>";



#Example 10
//everything in the parenthesis will be seperated to be in a different array
$pattern = '/:([a-zA-Z]*)[0-9]*/';
$string = "names are u:smith1 u:jones22 u:smith7";

echo "trying pattern '$pattern' against string '$string' </br>";

$matches=array();
$result = preg_match_all($pattern, $string, $matches);
if($result){
    echo "matched</br>";
}
else{
    echo "did not match!</br>";
}
echo "<pre>";
print_r($matches);
echo "<hr/>";



#Example 11
//cleans up html tags off the actual texts
$pattern = '/\<([^\>]*)\>/';
$string = "<h1>title</h1><p>this is text</p><form>here is a form</form>";

echo "trying pattern '$pattern' against string '$string' </br>";


$result = preg_split($pattern, $string);

echo "<pre>";
print_r($result);
echo "</pre>";
echo "<hr/>";