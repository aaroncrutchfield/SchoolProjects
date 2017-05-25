<?php
include_once 'col_data.php';


if (isset($_GET['location_a']) && isset($_GET['location_b'])){
    $state_a = $_GET['location_a'];
    $state_b = $_GET['location_b'];
}
else{
    $state_a = '';
    $state_b = '';
}

if (isset($_GET['wages_a'])){
    $wages_a = $_GET['wages_a'];
}
else{
    $wages = '';
}

?>

<form method="get" action="index.php">
    <table border="1">
        <tr>
            <th colspan="2">Cost of Living Calculator</th>
        </tr>
        <tr>
            <td colspan="2">This application compares the relative cost of living between two locations.</td>
        </tr>
        <tr>
            <td>Location A:</td>
            <td><select name="location_a">
                    <option value="0">(Select a location)</option>
                    <?php
                    foreach ($COL_array as $state => $income){
                        if ($state == $state_a){
                            $s = selected;
                            $col_index_a = $income;
                        }
                        else{
                            $s = '';
                        }
                        echo "<option value='$state' $s>$state</option>";
                    }
                    
                    ?>
                </select>
            </td>
        </tr>
        <tr>
            <td>Wages in location A:</td>
            <td>
                $<input type="text" name="wages_a" value='<?=$wages_a?>'/>
            </td>
        </tr>
        <tr>
            <td>Location B:</td>
            <td><select name="location_b">
                    <option value="0">(Select a location)</option>
                    <?php
                    foreach ($COL_array as $state => $income){
                        if ($state == $state_b){
                            $s = selected;
                            $col_index_b = $income;
                        }
                        else{
                            $s = '';
                        }
                        echo "<option value='$state' $s>$state</option>";
                    }
                    
                    ?>
                </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" value="Calculate Wages"/>
            </td>
        </tr>
    </table>
    

        
</form>
<hr></hr>

<?php

if ($col_index_a > 0 && $col_index_b > 0 && $wages_a > 0){
    $continue = TRUE;
}
else {
    $continue = FALSE;
}


if ($continue){
    $state_a = substr($state_a, 5);
    $state_b = substr($state_b, 5);

    $wages_b = number_format(($wages_a / $col_index_a) * $col_index_b, 0);
    $wages_a = number_format($wages_a);
    
    echo "</hr>Making <b>$$wages_a</b> in $state_a is the same as making <b>$$wages_b</b> in $state_b.";
}


?>
