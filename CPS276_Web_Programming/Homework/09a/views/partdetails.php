<html>
   <head>
      <BASE HREF='http://<?=PROJECT_URL?>'/>
   </head>
   <body>
       
       <h1><?=$parts[0]->partNumber?>: <?=$parts[0]->description?></h1>
       
       <table border="1">
            <tr>
               <th>Location</th>
               <th>Quantity</th>
               <th>Date</th>
           </tr>
               
       <?php for($i=0; $i < count($parts); $i++):?>
       
           <?php
                $time = strtotime($parts[$i]->date);
                $formattedDate = date("m/d/y", $time);
           ?>
               <tr>
                   <td><?=$parts[$i]->location?></td>
                   <td><?=$parts[$i]->quantity?></td>
                   <td><?=$formattedDate?></td>
                   <td><a href="inventory/edit/<?=$parts[$i]->partID?>">Edit</a></td>
                   <td><a href="inventory/delete/<?=$parts[$i]->partID?>">Delete</a></td> 
               
               </tr>
               
       <?php endfor;?>
               
       </table>
       <a href="inventory/listing">Back</a>
            
      <?php 
      
//      echo "<pre>";
//      print_r($mypart);
//      echo "</pre>";
      ?>
      
   </body>
</html>

<!--how to use a button to go to link-->
<!--http://stackoverflow.com/questions/6393827/can-i-nest-a-button-element-inside-an-a-using-html5-->
<!--<form style="display: inline" action="http://example.com/" method="get">
  <button>Visit Website</button>
</form>-->