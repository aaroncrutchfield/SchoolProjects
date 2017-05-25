
<!--A query method that returns a result set of two or more rows-->
<?php
$query = 'SELECT productCode, productName, listPrice'
        . 'FROM products'
        . 'WHERE categoryID = :category_id;';

$statement = $$db->prepare($query);
$statement->bindValue(":category_id", $category_id);
$statement->execute();
$products = $statement->fetchAll();
$statement->closeCursor();
?>


<!--How to use a foreach statement to display the result set in an HTML table-->
<?php foreach ($products as $product){ ?>
<tr>
    <td><?php echo $product['productCode']; ?></td>
    <td><?php echo $product['productName']; ?></td>
    <td><?php echo $product['listPrice']; ?></td>
</tr>
<?php } ?>


<!--Another syntax for the foreach statement that works better within PHP tags-->
<?php foreach ($products as $product) : ?>
<tr>
    <td><?php echo $product['productCode']; ?></td>
    <td><?php echo $product['productName']; ?></td>
    <td><?php echo $product['listPrice']; ?></td>
</tr>
<?php endforeach; ?>


<!--Description
    -When a PDOStatement object contains a result set, you can use the fetchAll 
    method to return an array that contains that result set. Then, you can use the
    closeCursor method to close the connection to the database.
    
    -To loop through the elements in an array, you can use a foreach statement to 
    define a foreach loop that processes on element of the array each time through
    the loop.
    
    -The second syntax for the foreach statement makes it easier to use teh foreach 
    statement with other control statements because it doesn't require the use of braces.
    
    -When you use the second syntax with other control statements, you code a colon after
    the clause that starts each control statement, and you end each control statement with 
    an appropriate end statement. For example, you end an if statement with and endif
    statement.-->