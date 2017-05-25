<?php
session_start();


/**
 * TODO:
 * autoloader
 * database singleton
 * view manager
 * settings singleton
 */


/**
 *  Part I: Gather input
 */

define('PROJECT_URL', Settings::get()->project_url);

// Load command URL
$commandStr = @$_REQUEST['cmd'];
if(!$commandStr) $commandStr = Settings::get()->default_path;

// Split command URL into parts
$commands = explode('/',$commandStr);

/**
 * Part II: Controller (w/ internal Models)
 */


// First parameter = Controller
$controllerClass = array_shift($commands);
$controller = new $controllerClass;

// Second paramter = Method
if(count($commands) > 0){
    $function = array_shift($commands);
    if($function && method_exists($controller, $function)){
        call_user_func_array(array(&$controller, $function), $commands);
    }
}


/**
 *  Part III: View
 */

View::_render($controller);



/**
 * 
 * Service Classes & Functions
 * 
 */




/**
 * The Singleton Settings class is used to load configuration settings (settings/settings.ini)
 * Usage:
 *    Settings::get()->settingname to get a setting
 *    Example:
 *       $db_user = Settings::get()->database_user;
 */
class Settings {
   protected static $instance;
   private $_data;
   protected function __construct(){ }
   protected function __clone(){}
   public static function get(){
      if(!isset(self::$instance)){self::$instance = new self();}
      return self::$instance;
   }
   
   public function __get($k){
      if(!$this->_data){
         $this->_data = parse_ini_file('settings/settings.ini');
      }
      if(array_key_exists($k,$this->_data)) return $this->_data[$k];
      else throw new Exception("Settings not defined: ".$k);
   }
}



/**
 * The Singleton DB holds one PDO database connection
 * Usage:
 *    Use DB::get() to reference the database resource
 *    Example 1:
 *       DB::get()->query($sql);
 *    Example 2:
 *       $query = DB::get()->prepare("SELECT * FROM users WHERE status=?");
 *       $query->execute( array('active') );
 * 
 */
class DB {
   protected static $instance;
   protected function __construct(){}
   protected function __clone(){}
   public static function get(){
      if(!isset(self::$instance)){
         $db_name = Settings::get()->database_name;
         $db_host = Settings::get()->database_host;
         $user = Settings::get()->database_user;
         $pass = Settings::get()->database_password;
         self::$instance = new PDO("mysql:dbname=$db_name;host=$db_host",$user,$pass);
      }
      return self::$instance;
   }
}



class View {
   protected static $instance;
   private $_template;
   protected function __construct(){}
   protected function __clone(){}
   /**
    * Set the output View
    * @param String $template
    * @throws Exception
    */
   public static function setTemplate($template){
      if(!isset(self::$instance)){self::$instance = new self();}
      $template=preg_replace("/[^A-Za-z0-9_]*/",'',$template);
      if(file_exists('views/'.$template.'.php')) self::$instance->_template = $template;
      else throw new Exception("View template not found: ".$template);
   }
   public static function _render($__controller){
      if(!isset(self::$instance)) throw new Exception("View template not defined");
      foreach($__controller as $k => $v){
         $$k = $v;
      }
      include 'views/'.self::$instance->_template.'.php';
   }
}





function redirect($local_url){
   session_write_close();
   header("Location: http://".PROJECT_URL.$local_url);
   exit;
}




/**
 * The Global Magic Autoload function dynamically loads models and controllers.
 * It works magically behind the scenes.
 * Follow these conventions:
 *   1. Save controllers and models into the provided directores
 *   2. One class per file, name the file after the class in lower case
 *       i.e. class Kittens goes into file models/kittens.php
 *   3. Do not use a controller and a model with the same name -- Namespace conflict!
 * 
 * @param type $class
 * @throws Exception
 */
function __autoload($class){
    $fclass = preg_replace("/[^A-Za-z0-9_]*/",'',$class);
    if($fclass=='') throw new Exception("No class specified");
    if(file_exists('controllers/'.$fclass.'.php')) require_once 'controllers/'.$fclass.'.php';
    elseif(file_exists('models/'.$fclass.'.php')) require_once 'models/'.$fclass.'.php';
    else throw new Exception("Class not found: $class");
}

