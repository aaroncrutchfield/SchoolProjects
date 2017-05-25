<html>
   <head>
      <BASE HREF='http://<?=PROJECT_URL?>'/>
   </head>
   <body>
     
      <h3>Game listing</h3>
      <table border="1">
          
          <?php foreach($games->games as $g):?>
          <tr>
              <td><?=$g->title?></td>
              <td><?=$g->minPlayers?></td>
              <td><?=$g->maxPlayers?></td>
              <td><?=$g->releaseDate?></td>
              <td><a href="game/details/<?=$g->gameID?>">Details</td>
              <td><a href="game/edit/<?=$g->gameID?>">Edit</td>
              
          <?php endforeach;?>
      </table>
   </body>
</html>