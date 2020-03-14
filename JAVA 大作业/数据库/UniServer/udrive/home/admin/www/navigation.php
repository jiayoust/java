<?php
/*
####################################################
# Name: The Uniform Server Admin Panel 2.0
# Developed By: The Uniform Server Development Team
# Modified Last By: Olajide Olaolorun (empirex) 
# Web: http://www.uniformserver.com
####################################################
*/

// Includes
include "includes/config.inc.php";
include "$apanel_path/includes/lang/".file_get_contents("includes/.lang").".php"; 
include "includes/header.php";
?>

<div id="navbar">
	<?php include "$apanel_path/includes/basic.inc"; ?>
	<?php include "$apanel_path/includes/server.inc"; ?>
	<?php include "$apanel_path/includes/config.inc"; ?>
	<?php include "$apanel_path/includes/tool.inc"; ?>
	<?php include "$apanel_path/includes/plugin.inc"; ?>
	<?php include "$apanel_path/includes/python.inc"; ?>
	<?php include "$apanel_path/includes/java.inc"; ?>
	<?php include "$apanel_path/includes/misc.inc"; ?>
	<?php include "$apanel_path/includes/doc.inc"; ?>
	<?php include "$apanel_path/includes/lang.inc"; ?>
</div>

</body>
</html>

