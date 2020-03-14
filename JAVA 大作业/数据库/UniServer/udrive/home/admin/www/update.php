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
<h2>&#187; <?php echo  $US['update-head']?></h2>
<h3><?php echo  $US['update-check']?></h3>
<p>
<?php
//-------------------------------------------------------------------- 

$available = file ('http://www.uniformserver.com/system/.version');
//$available = file ('includes/.version');
$my = file ('includes/.version');

//--------------------------------------------------------------------
if (rtrim($my[0]) == rtrim($available[0])) {
?>
	<?php echo  $US['update-true']?>
<?php
}

else{
?>
	<?php echo  $US['update-false']?>
	<br />
	<br />
<?php
      echo "". $US['update-new'] .": $available[0]";
	echo "<br />";
      echo "". $US['update-yours'] .": $my[0]";
	echo "<br /><br />";
?>
	<?php echo  $US['update-get']?>
	<br />
	<a href="http://www.uniformserver.com/" target="_blank"><h2><?php echo  $US['nav-web']?></h2></a>
<?php
}
?>
</p>
</div>

<?php
// Footer
include "includes/footer.php";
?>