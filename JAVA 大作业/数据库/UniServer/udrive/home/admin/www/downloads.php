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
include "includes/secure.php";
?>

<div id="main">
	<h2>&#187; <?php echo  $US['down-head']?></h2>
	<h3><?php echo  $US['down-aval']?></h3>
	<p><?php echo  $US['down-text']?></p>
</div>

<?php
// Footer
include "includes/footer.php";
?>
