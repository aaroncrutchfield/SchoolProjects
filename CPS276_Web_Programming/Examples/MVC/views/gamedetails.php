<html>
   <head>
      <BASE HREF='http://<?=PROJECT_URL?>'/>
   </head>
   <body>
       
       <table border="1">
           <tr>
               <th>Title</th>
               <td><?=$mygame->title?></td>
           </tr>
           <tr>
               <th>Subtitle</th>
               <td><?=$mygame->subtitle?></td>
           </tr>
           <tr>
               <th>Min Players</th>
               <td><?=$mygame->minPlayers?></td>
           </tr>
           <tr>
               <th>Max Players</th>
               <td><?=$mygame->maxPlayers?></td>
           </tr>
           <tr>
               <th>Release Date</th>
               <td><?=$mygame->releaseDate?></td>
           </tr>
           <tr>
               <th>Type</th>
               <td><?=$mygame->type?></td>
           </tr>
           <tr>
               <th>Publisher</th>
               <td><?=$mygame->publisher?></td>
           </tr>
           <tr>
               <th>Description</th>
               <td><?=$mygame->description?></td>
           </tr>
           <tr>
               <th>Age Rating</th>
               <td><?=$mygame->ageRating?></td>
           </tr>
       </table>
     
      <?php 
      
      echo "<pre>";
      print_r($mygame);
      echo "</pre>";
      ?>
      
   </body>
</html>