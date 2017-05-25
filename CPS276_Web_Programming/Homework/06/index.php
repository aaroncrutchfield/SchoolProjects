<?php

//possible match outcome
$files = array(
    "paper01.txt" => "Paper 1",
    "paper02.txt" => "Paper 2",
    "paper03.txt" => "Paper 3",
    "paper04.txt" => "Paper 4",
    "paper05.txt" => "Paper 5");
?>

<style type="text/css">
    .fieldset-auto-width {
         display: inline-block;
    }
</style>

<div>
    <fieldset class="fieldset-auto-width">
        <legend>Upload File:</legend>
            <!--file uploads must be sent in POST-->
            <form method="post" action="index.php" enctype="multipart/form-data">
                <input type="file" name="myfile"/>
                </br></br>
                <input type="submit" value="Send File"/>
            </form>
    </fieldset>
</div>

</br></br>

<div>
    <fieldset class="fieldset-auto-width">
        <legend>Select File:</legend>
            <!--file uploads must be sent in POST-->
            <form method="post" action="index.php" enctype="multipart/form-data">
                <select name="local_file">
                    <?php
                    foreach($files as $key => $value){
                        $s ='';
                        if($key == $_POST['local_file']){
                            $s = "selected";
                        }
                        else {
                            $s = '';
                        }
                        echo "<option value='$key' $s>$value</option>";                         
                    }
                    ?>
                   
                    </br></br>
                    <input type="submit" value="Send File"/>
                </select>
            </form>
    </fieldset>
</div>


<?php
//*******print file info**********//

//if the file is set, save content to a string
if(isset($_FILES['myfile'])){
    //exit(0);
    $str = file_get_contents($_FILES['myfile']['tmp_name']);
    echo $str;
    //print_r($_FILES['myfile']);
}

if(isset($_POST['local_file'])){
    $str = file_get_contents($_POST['local_file']);
    echo $str;
}
$numbers = array();
//$pattern = '/[1-9]{1,6}\.?[0-9]{1,}/';

//paper05 => '/[\w]=([\d]{1,}\.?[\d]{1,})[\s]*/';


$pattern = '/[\d]{1,}\.?[\d]{1,}/';
$result = preg_match_all($pattern, $str, $numbers);
$d1 = 0; 
$d2 = 0; 
$d3 = 0;
$d4 = 0; 
$d5 = 0; 
$d6 = 0; 
$d7 = 0; 
$d8 = 0; 
$d9 = 0;

foreach ($numbers[0] as $key => $value) {
    $sDigit = substr($value, 0, 1);
    $digit = intval($sDigit);
    //echo "value= ".$value;
    switch ($digit) {
        case 1:
            $d1 += 1;
            break;
        case 2:
            $d2 += 1;
            break;
        case 3:
            $d3 += 1;
            break;
        case 4:
            $d4 += 1;
            break;
        case 5:
            $d5 += 1;
            break;
        case 6:
            $d6 += 1;
            break;
        case 7:
            $d7 += 1;
            break;
        case 8:
            $d8 += 1;
            break;
        case 9:
            $d9 += 1;
            break;
    }
}


$occurrences = [$d1, $d2, $d3,$d4, $d5, $d6, $d7, $d8, $d9];
?>

<table border="1">
    <tr>
        <th>Digit</th>
        <th>Occurrences</th>
        <th>Percentage</th>
    </tr>
        <?php for ($i=1; $i < 10; $i++): ?>
        <tr>
            <td><?=$i?></td>
            <td><?=$occurences[$i]?></td>
            <td><?=$occurences[$i]/$result?></td>
        }
        </tr>
        <?php endfor; ?>
</table>
<!--echo "1 occurs $d1 times</br>"
        . "2 occurs $d2 times</br>"
        . "3 occurs $d3 times</br>"
        . "4 occurs $d4 times</br>"
        . "5 occurs $d5 times</br>"
        . "6 occurs $d6 times</br>"
        . "7 occurs $d7 times</br>"
        . "8 occurs $d8 times</br>"
        . "9 occurs $d9 times</br>";-->

<?php
echo $result;
echo "<pre>";
print_r($numbers);
echo "<hr/>";

