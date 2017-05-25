<?php

//*******form to upload file**********//
?>

<!--file uploads must be sent in POST-->
<form method="post" action="upload.php" enctype="multipart/form-data">
    <input type="file" name="myfile"/>
    <input type="submit" value="Send File!"/>
</form>

<?php
//*******print file info**********//

//stop application if 'myfile' doesn't exist
if(!isset($_FILES['myfile'])){
    exit(0);
}

print_r($_FILES['myfile']);

//*******print file contents**********//
$str = file_get_contents($_FILES['myfile']['tmp_name']);
echo $str;
