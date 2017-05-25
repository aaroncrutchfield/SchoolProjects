<?php


//NOT NULL
//echo $_POST['partNumber'];
$complete = TRUE;

if(isset($_POST['partNumber'])){
    $mypart->__set('partNumber', $_POST['partNumber']);
    $partNumber = $_POST['partNumber'];
    $ePartNumber = '';
} else {
    $partNumber = '';
    $ePartNumber = "*Insert valid part number" . "</br>";
    $complete = FALSE;
}

//NOT NULL
if(isset($_POST['date']) && $_POST['date'] != ''){
    $mypart->__set('date', $_POST['date']);
    $date = $_POST['date'];
} else {
    $date = '';
    $complete = FALSE;
    $eDate= "*Insert valid date" . "</br>";
}

//NOT NULL
if(isset($_POST['quantity']) && $_POST['quantity'] != ''){
    $mypart->__set('quantity', $_POST['quantity']);
    $quantity = $_POST['quantity'];
} else {
    $quantity = '';
    $complete = FALSE;
    $eQuantity= "*Insert valid quantity" . "</br>";
}

if(isset($_POST['description'])){
    $mypart->__set('description', $_POST['description']);
    $description = $_POST['description'];
} else{
    $description = '';
}

if(isset($_POST['toteSize'])){
    $mypart->__set('toteSize', $_POST['toteSize']);
    $toteSize = $_POST['toteSize'];
} else {
    $toteSize = '';
}

//NOT NULL
if(isset($_POST['location']) && $_POST['location'] != ''){
    $mypart->__set('location', $_POST['location']);
    $location = $_POST['location'];
} else {
    $location = '';
    $complete = FALSE;
    $eLocation= "*Insert valid location" . "</br>";
}

if($complete){
    $mypart->save();
    redirect('inventory/listing');
}

?>

<!--TODO-->
<!--Prevent errors from generating when page is first loaded-->
<html>
   <head>
      <BASE HREF='http://<?=PROJECT_URL?>'/>
   </head>
   <body>
       
       <h1>Add Part</h1>
       
       <form  method="post">
           Part Number: </br>
           <input type="text" name="partNumber" value="<?=$partNumber?>">
           <font color="red"><?=$ePartNumber?></font></br></br>

           Date: </br>
           <input type="text" name="date" value="<?=$date?>">
           <font color="red"><?=$eDate?></font></br></br>

           Quantity: </br>
           <input type="number" name="quantity" value="<?=$quantity?>">
           <font color="red"><?=$eQuantity?></font></br></br>

           Description: </br>
           <input type="text" name="description" value="<?=$description?>"></br></br>

           Tote Size: </br>
           <input type="radio" name="toteSize" value="small">Small</br>
           <input type="radio" name="toteSize" value="medium">Medium</br>
           <input type="radio" name="toteSize" value="large">Large </br></br>
           
           Location: </br>
           <input type="text" name="location" value="<?=$location?>">
           <font color="red"><?=$eLocation?></font></br></br>
           
           <input type="submit" value="Submit">
       </form>
       <a href="inventory/listing">Back</a>
     
      <?php 
      
      echo "<pre>";
      print_r($mypart);
      echo "</pre>";
      
      ?>
      
   </body>
</html>
