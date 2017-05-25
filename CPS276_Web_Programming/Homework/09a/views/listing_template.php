<h1>Inventory</h1>


<?php

//******add filter if time permits*****//



//show all inventory
if($query != NULL){
    echo "<br>Results found: ". $query->rowCount();
}
else {
    echo "<br>Results Found: 0";
    exit();
}

?>


<table border="1">
    <tr>
        <th>Part Number</th>
        <th>Total Quantity</th>
        <th>Description</th>
        <th>Tote Size</th>
        
    </tr>
    
    <?php while($row=$query->fetch(PDO::FETCH_ASSOC)): ?>

        <tr>
            <td><?=$row['partNumber']?></td>
            <td><?=$row['quantity']?></td>
            <td><?=$row['description']?></td>
            <td><?=$row['toteSize']?></td>
            <td><a href="details/<?=$row['partNumber']?>">Details</a>
        </tr>
        
    <?php endwhile;?>
</table>
<a href="add">Add Part</a>
    