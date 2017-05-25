<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of CalendarEvent
 *
 * @author Aaron
 */
class CalendarEvent {
    private $title;
    private $location;
    private $start;
    private $end;
    private $cancelled;
    private $day;
    

    
    public function setTitle($title){
        $this->title = $title;
    }
    
    function setLocation($location){
        $this->location = $location;
    }
    
    function setStart($timeformat){
//        2016-03-02 10:00:00
        $this->start = $timeformat;
    }
    
    function setEnd($timeformat){
        $this->end = $timeformat;
    }
    
    function setCancelled($cancelled){
        $this->cancelled = $cancelled;
    }
    
    public function setDay() {
        $timestamp = strtotime($this->start); 
        $this->day = date('j', $timestamp); 
    }
    
    public function getTitle() {
        return $this->title;
    }
    
    public function getLocation() {
        return $this->location;
    }
    
    public function getStart() {
        return $this->start;
    }
    
    public function getEnd() {
        return $this->end;
    }
    
    public function getCancelled() {
        return $this->cancelled;
    }
    
    public function getDay() {
        return $this->day;
    }
    
    public function toString() {
        $timestamp1 = strtotime($this->start); 
        $startTime = date('g:ia', $timestamp1); 
        
        $timestamp2 = strtotime($this->end); 
        $endTime = date('g:ia', $timestamp2); 
         
        //Title: Event
        //srtTime - endTime
        $eventString = $this->title.': '.$this->location.'</br>'.$startTime.
                ' - '.$endTime.'</br>';
        return $eventString;
    }
}
