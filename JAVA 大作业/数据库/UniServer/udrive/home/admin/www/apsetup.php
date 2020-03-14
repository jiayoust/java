<?php
/*
####################################################
# Name: The Uniform Server Setup
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


if ( !(file_exists($aphtpasswd)) ) {
	$FHandle = fopen($aphtpasswd, 'w');
	fclose($FHandle);
}


if ($_POST['submit']) {

	$nwuser = $_POST['apuser'];
	$nwpass = $_POST['appass'];

	$urdata = "$nwuser:$nwpass";

	$wfile = fopen($aphtpasswd, 'w') ;
	fwrite($wfile, $urdata);
	fclose($wfile);
?>

	<div id="main">
	<h2>&#187; <?php echo  $US['apsetup-head']?></h2>
	<h3><?php echo  $US['apsetup-sub-0']?></h3>
	<p>
	<?php echo  $US['apsetup-success']?>
	<br />
	<br />
	<?php echo  $US['apsetup-user']?>: <?php echo $_POST['apuser'];?>
	<br />
	<?php echo  $US['apsetup-pass']?>: <?php echo $_POST['appass'];?>
	</p>
	</div>

<?php
}

else {
	$tfile = fopen($aphtpasswd, "r");

	$fcontents = fgets($tfile);
	$ucontents = explode(":", $fcontents);
?>

	<div id="main">
		<h2>&#187; <?php echo  $US['apsetup-head']?></h2>
		<h3><?php echo  $US['apsetup-sub-0']?></h3>

		<p><?php echo  $US['apsetup-text-0']?></p>
		<form action="<?php echo $PHP_SELF?>" method="post">
		<table width="100%">
		<tr>
		<td width="80">
		<p><?php echo  $US['apsetup-user']?></p>
		</td>
		<td>
		<input type="text" name="apuser" value="<?php echo $ucontents[0]?>" />
		</td>
		</tr>
		<tr>
		<td width="80">
		<p><?php echo  $US['apsetup-pass']?></p>
		</td>
		<td>
		<input type="text" name="appass" value="<?php echo $ucontents[1]?>" />
		</td>
		</tr>
		</table>
		<br />
		<input type="submit" name="submit" value="<?php echo  $US['apsetup-change']?>" />
		</form>
	</div>

<?php
	fclose($tfile);
}

// Footer
include "includes/footer.php";
?>