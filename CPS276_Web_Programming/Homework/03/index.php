<?php

if(isset($_POST['term'])){
    $term = $_POST['term'];
}
else{
    $term = '';
}

?>
<form method="post" action="index.php">
    Enter a term:
    <input type="text" name="term" value="<?=$term?>"/>
    <input type="submit" value="Submit"/>
</form>

<?php
//don't need api if no term is entered
if ($term == ''){
    exit();
}

$api_key = 'ajcrutchfield';
$api_url = 'http://96.126.107.46/food/?apikey='.$api_key.'&term='.$term;

//echo $api_url;

//call api
$result = file_get_contents($api_url);

//decode api
$decoded = json_decode($result, true);

//output decoded data
//echo "<pre>";
//print_r($decoded);
//echo "<pre>";

//echo $decoded['results']['0']['product_name'];
if ($decoded['total'] > 0 ){
    echo 'Found '.$decoded['total'] .' result(s).';
    echo '</br></br>';
}
 else {
    echo 'Sorry no results found.';
    exit();
}
?>



<table border='1'>
    <tr>
        <th>Product Name</th>
        <th>Category</th>
        <th>Serving Size</th>
        <th>Calories</th>
        <th>Fat</th>
        <th>Saturated Fat</th>
        <th>Sodium</th>
    </tr>
    <?php
        foreach($decoded['results'] as $product){
            $product_name = $product['product_name'];
            $category = $product['category'];
            $servings = $product['serving_size'];
            $calories = $product['calories'];
            $fat = number_format($product['fat'], 2) . 'g';
            $sat_fat = number_format($product['saturated_fat'], 2) . 'g';
            $sodium = number_format($product['sodium'], 2) . 'g';
            
            echo "<tr>";
            echo "<td>$product_name</td>";
            echo "<td>$category</td>";
            echo "<td>$servings</td>";
            echo "<td>$calories</td>";
            echo "<td>$fat</td>";
            echo "<td>$sat_fat</td>";
            echo "<td>$sodium</td>";
            echo "<tr>";
        }
    
    ?>
</table>