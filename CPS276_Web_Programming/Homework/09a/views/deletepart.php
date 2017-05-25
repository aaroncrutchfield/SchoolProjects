<?php
if(isset($_POST['confirm'])){   
    $confirm = $_POST['confirm'];
}
else {
    $confirm = '';
}
//if yes is selected, delete part and return to inventory listing
if ($confirm == "yes"){
    $query->execute();
    redirect("inventory/listing");
}
//if no is selected, go back to part details
else if ($confirm == "no"){
    redirect("inventory/details/$mypart->partNumber");
}
else{
    
}
?>
<html>
   <head>
      <BASE HREF='http://<?=PROJECT_URL?>'/>
   </head>
   <body>

        <h1>Delete</h1>
        <h3>Part: <?=$mypart->partNumber?><br/>
            Quantity: <?=$mypart->quantity?><br/>
            Date: <?=$mypart->date?><br/>
            Location: <?=$mypart->location?></h3>
        
        
        <form method="post">
            Are you sure you want to delete this entry?</br>
            <input type="radio" name="confirm" value="yes">Yes
            <input type="radio" name="confirm" value="no" checked="checked">No</br>
            <input type="submit" value="Submit">
        </form>
   </body>
</html>