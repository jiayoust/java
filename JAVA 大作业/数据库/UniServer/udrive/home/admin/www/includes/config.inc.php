<?php
/*
####################################################
# Name: The Uniform Server Configuration 1.7
# Developed By: The Uniform Server Development Team
# Modified Last By: Olajide Olaolorun (empirex) 
# Web: http://www.uniformserver.com
# Modified by MPG 28-6-08 
# MPG 28-2-2009 - Added path to MySQL cnf
####################################################
*/

/* Globals */
// Version
$version = "2.1";
$uniserver = file_get_contents("includes/.version");
$unisecure = "0"; //Use secure.php if set to 1
$devmode = "0"; //Developer Mode = 1
$usip = getenv("REMOTE_ADDR");
//$hname = gethostbyaddr($REMOTE_ADDR); //Not being used nowssssss

/* Path variables - NO BACKSLASH */
// Local Variables
$drive = $_ENV['Disk'] . ":";

//MPG When run as a service Disk ENV will not be set. However
//we do not know if a user placed the server in sub-folders
//hence drive must include sub-folders 
if($drive == ":"){ 
  $path = realpath(dirname($_SERVER['config.inc.php'])); 
  $pathArray = explode("\\home",$path); 
  $drive="$pathArray[0]"; //drive letter and any sub-folders 
$drive=str_replace("\\","/",$drive); //change backslash to forward slash
}

$usr = "$drive/usr";
$www = "$drive/www";
$ssl = "$drive/ssl";
$home = "$drive/home";

/* htpasswd Variables - NO BACKSLASH */ 
// Required
$htpasswd = "$drive/htpasswd/";
$aphtpasswd = "$htpasswd/home/admin/www/.htpasswd";
$whtpasswd = "$htpasswd/www/.htpasswd";
$whtpasswds = "$htpasswd/ssl/.htpasswd";
$mysqlpwd = "$home/admin/www/mysql_password";
$ssl_file = "$drive/usr/local/apache2/conf/ssl.crt/server.crt"; // Created when newcert generated
$my_cnf_file = "$drive/usr/local/mysql/my.cnf";                 // MySQL configuration file

/* Web Variables - NO BACKSLASH */ 
// Host
$host = $_SERVER["HTTP_HOST"];
// Server - DO NOT CHANGE
$server = "http://$host";
$server_path = "$www"; //$_SERVER["DOCUMENT_ROOT"]
$server_path_ssl = "$ssl"; //$_SERVER["DOCUMENT_ROOT"]
// Admin Panel
$apanel = "$server/apanel";
$apanel_path = "$home/admin/www";
?>
