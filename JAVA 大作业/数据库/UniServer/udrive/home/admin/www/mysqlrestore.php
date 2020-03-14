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
	<h2>&#187; <?php echo  $US['mysql-head']?></h2>
	<h3><?php echo  $US['mysql-verify1']?></h3>
	<p>
	<?php echo  $US['mysql-verify2']?>
	<br />
	<br />
	<a href="<?php echo $apanel?>/cgi-bin/includes/lang/<?php echo file_get_contents("includes/.lang");?>/mysql_restore.cgi"><h2><?php echo  $US['mysql-verify3']?></h2></a>
	</p>
</div>

<?php
// Footer
include "includes/footer.php";
?>

