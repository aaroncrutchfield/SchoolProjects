<?php

$api_url = 'http://96.126.107.46/exam/ajcrutchfield/3ee1e597/status';
//call api
$result = file_get_contents($api_url);

//decode api
$decoded = json_decode($result, true);

echo "<pre>";
print_r($decoded);
echo "<pre>";

$api_url2 = 'http://96.126.107.46/exam/ajcrutchfield/3ee1e597/submit';
//call api
$result2 = file_get_contents($api_url2);

//decode api
$decoded2 = json_decode($result2, true);

echo "<pre>";
print_r($decoded2);
echo "<pre>";
