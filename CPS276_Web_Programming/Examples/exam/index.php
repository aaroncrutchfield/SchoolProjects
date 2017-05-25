<?php

//start with question 1 and increment after each submit
if (isset($_POST['next'])){
    $qn = $_POST['qn'] ;
    echo $_POST['qn'];
}
if (!isset($qn)){
    $qn = 1;
}





//api url
$username = 'ajcrutchfield';
$key = '3ee1e597';
$api_url = "http://96.126.107.46/exam/$username/$key/get/40";

//call api
$result = file_get_contents($api_url);

//decode api
$decoded = json_decode($result, true);

echo $api_url;

//output decoded data
//echo "<pre>";
//print_r($decoded);
//echo "<pre>";

////EXAM STATUS
//$status = "http://96.126.107.46/exam/$username/$key/status']";
//
//$sResult = file_get_contents($status);
//$sDecoded = json_decode($result, true);
//
////show status of answer post
//foreach($sDecoded['questions'] as $question){
//    echo key($question);
//    echo $question;
//}



$question_num = $decoded['question_num'];

?>
<?php 
//if answer is set post it
if (isset($_POST['answer'])){
    $answer = $_POST['answer'];
    //post answer
    $num = $question_num -1;
    $submit = "http://96.126.107.46/exam/$username/$key/post/40/$answer";
    echo "<br/>".$submit;

    $sResult = file_get_contents($submit);
    $sDecoded = json_decode($sResult, true);
    
    //show status of answer post
    echo $sDecoded['message'] ." [$answer]";
}
?>

<!--need a form to view questions and post answers-->
<form method="post">
    <h2>Question <?=$question_num?>.</h2>
    <?=$decoded['question']?><br/>
    <?php foreach ($decoded['options'] as $option): ?>
        <?=$option['letter']?>:<input type="radio" name="answer" value="<?=$option['letter']?>"><?=$option['text']?><br/>
    <?php endforeach;?>
        <input type="submit" value="submit" name="next">
        <input type="hidden" value="<?=$question_num?>" name="qn">
</form>


<!--next question-->
<br/>
<form method="post">
    <input type="submit" value="Next question" name="next">
    
</form>



