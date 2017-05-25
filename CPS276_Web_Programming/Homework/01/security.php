<?php
session_start();
function secure_my_page(){
    if(isset($_SESSION['userid']) && $_SESSION['userid'] != NULL){
    //this is a valid user
    
    } else {
        $loggedin = false;
        $message = '';
        if(isset($_POST['username']) && isset($_POST['password'])){
            //check username and password
            $hash = hash('sha256', $_POST['password']);     //hash the password to protect data
            
//insert query here
            $passed = true;
            $id = 5;            //get the actual number from database
            if($passed){
                $loggedin = true;
                $_SESSION['userid'] = $id;
            } else {
                $message = 'Username and password is invalid.';
            }
        
        }
        if(!$loggedin){
            ?>
<form method="post">
    Username: <input type="text" name="username"/>
    <br/>
    Password: <input type="password" name="password"/>
    <br/>
    <input type="submit" value="Log In"/>
    
</form>

<?php
            //show the log in form
            exit();
        }
    }
}