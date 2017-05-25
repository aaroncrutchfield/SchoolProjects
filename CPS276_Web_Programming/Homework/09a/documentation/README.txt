-------------------------------
INSTALLING/USING THIS FRAMEWORK
-------------------------------

I. INSTALLATION CHECKLIST
   A. Copy all files to web server or staging area
   B. Modify /.htaccess file:
      Set RewriteBase to the relative path of your project
      Example:
         RewriteBase /~myusername/CPS276/09/
   C. Modify /settings/settings.ini
         1. Set the Project URL to the full web address of the project (www.example.com/path/to/project/)
         2. Set your default controller and method
         3. Add your database connection settings


II. USING THIS FRAMEWORK : THE RULES
   A. Anything that isn't directly part of the MVC structure goes into the /resources/ directory
      Example: images, CSS stylesheets, downloadable files
   B. Make no changes to the contents of the /_core directory
   C. Implement MVC using the provided directories:
      /controllers ==> for all controller classes
      /models ==> for all model classes
      /views ==> for all view templates
   D. The application routing must follow a controller/model/parameter format
      Example:
         http://www.example.com/calendar/view
            controller: Calendar
            method: view()
         http://www.example.com/calendar/view_event/57
            controller: Calendar
            method: view_event(57)
         http://www.example.com/kittens/cuddle/3/mittens
            controller: Kittens
            method: cuddle(3, 'mittens')
   E. Within a controller constructor or method, you can build any models that are loaded into the /models/ directory
      Any public properties of the controller will be local variables in the View
      Example:
         Controller has:            $this->homes = array();
         View has access to it as:  $homes
   F. Within a controller constructor or method, you must specific the View template with the View class (Singleton)
      Example:
         View::setTemplate('my_template')  ==>  corresponds to /views/my_template.php
   G. Database connection is available with the DB class (Singleton PDO)... use DB::get()
      Example:
         $query = DB::get()->prepare($sql);
         $query->execute($params);
   H. Global settings in the /settings/settings.ini file can be access with the Settings class... use Settings::get() 
      Example:
         $db_user = Settings->get()->database_user;
   I. All View templates for HTML pages must contain the following line in order for relative paths to work:
      <html>
         <head>
            <BASE HREF='http://<?=PROJECT_URL?>'/>
   J. If a header redirection is needed, use the framework's redirect() function for clean redirection.
      Example:
         redirect('homes/detils/5');
