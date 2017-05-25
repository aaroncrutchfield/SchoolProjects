<?php

print_r($_POST);
//echo "session 2 in class examples";
/*
$kittenNames = array('Gust', 'Mittens', 'Waffles', 'Fuzzy');

//ARRAY_PUSH
array_push($kittenNames, "Fluffy");
$kittenNames[] = "Fluffy";
//echo $kittenNames[2];

//IMPLODE
$kittenString = implode(" and ", $kittenNames);
echo $kittenString;
*/

/*
//FOR LOOP
$n = count($kittenNames);
for($i=0; $i < $n; $i++){
    echo $kittenNames[$i];
    echo '<br/>';
}

//FOREACH
foreach($kittenNames as $name){
    echo $name."<br/>";
}
*/


/*
$kittenPrice = array(
    'Gust' => 1200.00,
    'Mittens' => 27.50,
    'Waffles' => 12.95,
    'Fuzzy' => 'Free!'
);

$kittenPrice['Wally'] = 6;

foreach($kittenPrice as $key => $value){
    echo "the price of $key is ";
    if (is_numeric($value))
            echo '$'.number_format($value, 2);
    else
        echo $value;
    echo "<br/>";
}

//or
//need fixing

foreach($kittenPrice as $key => $value){
    
    if (is_numeric($value))
            $price = '$'.number_format($value, 2);
    else
        $price = $value;
    echo "the price of $key is $value";
    echo "<br/>";
}
*/

/*
echo date("Y-m-d", time());

$now = strtotime("3/1/1991 - 1 day");
echo date("l F j Y", $now);
 */

?>
<table>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Age</th>
    </tr>
    <tr>
        <td>Gus</td>
        <td>1,200.00</td>
        <td>8 months</td>
    </tr>
    <tr>
        <td>Mittens</td>
        <td>9,000.00</td>
        <td>38 years</td>
    </tr>
</table>

<form method="post" action="session02.php">
    
    Name: <input type="text" name="myname"/><br/>
    Email: <input type="text" name="myemail"/><br/>
    May we contact you? 
    <input type="radio" name="contact" value="Y" checked="checked"/>Yes
    <input type="radio" name="contact" value="Y"/>No
    <br/>
    <input type="submit" value="Submit Form"/>
</form>
<?php
echo "<mm:dwdrfml documentRoot=" . __FILE__ .">";$included_files = get_included_files();foreach ($included_files as $filename) { echo "<mm:IncludeFile path=" . $filename . " />"; } echo "</mm:dwdrfml>";
?>