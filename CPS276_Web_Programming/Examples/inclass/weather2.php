<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

try {

//gps location
//[lon] => -83.61
//[lat] => 42.24

//api key
$api_key = "a0b897f833c15a40d1be152d5f834288";

//api call using gps location
$api_url = "http://api.openweathermap.org/data/2.5/find?lat=42.24&lon=-83.61&cnt=10&APPID=".$api_key;

//get data
$result = file_get_contents($api_url);
//decode data
$decoded = json_decode($result, true);


//output decoded data
//echo "<pre>";
//print_r($decoded);
//echo "<pre>";

$code = $decoded['cod'];
if ($code != 200){
    throw new Exception("Weather api reported code $code");
//    echo "Sorry , weather is not available";
//    //stops application
//    exit();
}

function KtoF($temp){
    
    $f = round($temp * (9/5) - 459.67);
    return $f;
}

?>

<table border="1">
    <tr>
        <th>City</th>
        <th>Temp</th>
        <th>Condition</th>
    </tr>
    <?php
foreach($decoded['list'] as $city){
    $name= $city['name'];
    $temp= KtoF($city['main']['temp']);
    $condition= $city['weather']['0']['main'];
    
    echo "<tr>";
    echo "<td>$name</td>";
    echo "<td>$temp</td>";
    echo "<td>$condition</td>";
    echo "<tr>";
}
    ?>
</table>

<?php

}
catch(Exception $e){
    echo "Sorry but there was an error...";
    echo "<br/>";
    echo $e->getMessage();
}


?>

