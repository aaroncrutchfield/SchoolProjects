<?php

if(isset($_POST['zipcode'])){
    //keeps the value that is put in the form
    $zipcode = $_POST['zipcode'];
}
else {
    $zipcode='';
}

?>

<form method="post" action="weather.php">
    Enter a zip code:
    <input type="text" name="zipcode" value="<?=$zipcode?>"/>
    <input type="submit" value="Get Weather"/>
    
</form>

<?php

//don't need api with no zip code entered
if($zipcode == ''){
    exit();
}



$api_key = "a0b897f833c15a40d1be152d5f834288";

//build api call (make sure to add http://)
//intval ensures the zip is a number
$api_url = "http://api.openweathermap.org/data/2.5/weather?zip=".intval($zipcode).",us&APPID=".$api_key;

//echo $api_url;

//call api
$result = file_get_contents($api_url);

//true returns an array, false returns an object
$decoded = json_decode($result, true);

//output decoded data
echo "<pre>";
print_r($decoded);
echo "<pre>";

//check the status code. 200=OK
$code = $decoded['cod'];
if ($code != 200){
    echo "Sorry , weather is not available";
    //stops application
    exit();
}


$name = $decoded['name'];
$temp = $decoded['main']['temp'];
$temp_f =  KtoF($temp);

echo "Right now it is $temp_f F in $name";


function KtoF($temp){
    
    $f = round($temp * (9/5) - 459.67);
    return $f;
}



?>

