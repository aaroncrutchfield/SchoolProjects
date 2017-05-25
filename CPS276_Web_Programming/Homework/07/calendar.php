<?php
/**
 * Calendar Class Definitions
 * 
 * Create your class definition in this file
 * 
 */
Class Calendar{
    private $month;
    private $year;
    private $event = array();
    
    public function __construct($month, $year) {
        $this->month = $month;
        $this->year = $year;
    }
    
    //add an event to the calendar
    public function addEvent($event){
        $start = $event->getStart();
        
        //if event is in the same year/month as calendar, *create day class
        //http://stackoverflow.com/questions/26448385/php-check-date-if-month-
        //is-equal-to-something
        $timestamp = strtotime($start); 
        $month = date('m', $timestamp); 
        
        //don't add event if it isn't in this month
        if ($month == $this->month) {
            $event->setDay();
            $this->event[] = $event;
        }
        
        
        

    }
    
    //return HTML code for a complete calendar
    public function draw(){
        //get date and year for calendar 
        $date = date_create($this->year.'-'.$this->month.'-'.'01');        
        $calDate = date_format($date, 'F Y');
        $numDays = date_format($date, 't');
        $firstDay = date_format($date, 'w');
//        echo $numDays;
        ?>
        
        <!--display month and year-->
        <table border="1">
            <tr>
                <th colspan="7"><?=$calDate?></th>
            </tr>        
        <!--display days of the week-->
            <tr>
                <th width="400px">Sunday</th>
                <th width="400px">Monday</th>
                <th width="400px">Tuesday</th>
                <th width="400px">Wednesday</th>
                <th width="400px">Thursday</th>
                <th width="400px">Friday</th>
                <th width="400px">Saturday</th>
            </tr>    
        <!--loop through array to echo day numbers and event beneath them-->
            <?php for ($i=1; $i <= $numDays+$firstDay; $i++):
                $dayEvents = '';
                $eventsArray = array();?>
                <?php foreach ($this->event as $value) {
                    
                    if ($value->getDay() == $i-$firstDay &&
                            $value->getCancelled() != true){
                        $eventsArray[] = $value;
                    }
                    
                }
                //sort array of events by date
                //http://stackoverflow.com/questions/7127764/sort-array-of-
                //objects-by-date-field
                usort($eventsArray, function($a, $b) {
                    return strtotime($a->getStart()) - strtotime($b->getStart());
                });
                
                /*
                 * $startA = $a->getStart();
                 * $startB = $b->getStart();
                 * if ($startA == $startB){
                 *      return 0;
                 * }
                 * elseif ($startA < $startB){
                 *      return -1;
                 * }
                 * else {
                 *      return 1;
                 * }
                 */
                
                foreach ($eventsArray as $event){
                    $dayEvents .= $event->toString().'</br>';
                }
                
//                echo $value->getDay().': '.$dayEvents;
//                        echo $i-$firstDay. '</br>';
                        ?>
                <?php if($i %7 == 1):?>
                    <tr>
                <?php endif;?>
                <?php if($i <= $firstDay):?>
                    <th height="150px">&nbsp;</th>
                <?php else: ?>
                    <th height="150px" align="left" valign="top">
                        <?=$i-$firstDay?><hr><?=$dayEvents?></th>
                <?php endif;?>
                <?php if($i %7 == 0):?>
                    </tr>
                    
                <?php endif;?>
            <?php endfor;?>   
        <?php
    }
    
    //returns an array of events with the specific name
    public function getEventsByName($name){
        $eventMatches = array();
        foreach ($this->event as $value) {
            $title = $value->getTitle();
            
            if ($title == $name){
                $eventMatches[] = $value;
            }
        }
        
        return $eventMatches;
    }    
}
