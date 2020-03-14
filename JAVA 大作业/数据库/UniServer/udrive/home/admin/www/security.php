<?php
/*
####################################################
# Name: The Uniform Server Security
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
	$AHandle = fopen($aphtpasswd, 'w');
	fwrite($AHandle, 'root:root');
	fclose($AHandle);
}

if ( !(file_exists($whtpasswd)) ) {
	$WHandle = fopen($whtpasswd, 'w');
	fwrite($WHandle, 'root:root');
	fclose($WHandle);
}

if ( !(file_exists($whtpasswds)) ) {
	$sslWHandle = fopen($whtpasswds, 'w');
	fwrite($sslWHandle, 'root:root');
	fclose($sslWHandle);
}

if ( !(file_exists($mysqlpwd)) ) {
	$SHandle = fopen($mysqlpwd, 'w');
	fwrite($SHandle, root);
	fclose($SHandle);
}

// Check user generated new server certificate and key.
// Check for existance of server certificate file server.crt
if ( !(file_exists($ssl_file)) ) {
  $unsecure_cert = TRUE;
}
else{
   $unsecure_cert = FALSE; 
}

// Admin Panel's .htpasswd
	$tfile = fopen($aphtpasswd, "r");
	$fcontents = fgets($tfile);
	$ucontents = explode(":", $fcontents);	

// Private Server's .htpasswd
	$wfile = fopen($whtpasswd, "r");
	$pcontents = fgets($wfile);
	$pscontents = explode(":", $pcontents);

// Private SSL Server's .htpasswd
	$sslwfile = fopen($whtpasswds, "r");
	$sslpcontents = fgets($sslwfile);
	$sslpscontents = explode(":", $sslpcontents);

// mysql_password
	$mfile = fopen($mysqlpwd, "r");
	$scontents = fgets($mfile);

	fclose($tfile);
	fclose($wfile);
	fclose($sslwfile);
	fclose($mfile);
?>

	<div id="main">
		<h2>&#187; <?php echo  $US['secure-head']?></h2>

		<h3><?php echo  $US['secure-sub-0']?></h3>
		<p><?php echo  $US['secure-text-0']?></p>
		
		<table width="100%">
			<tr>
				<td width="75%"><b><?php echo  $US['secure-text-1']?></b></td>
				<td><b><?php echo  $US['secure-text-2']?></b></td>
			</tr>
			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-3']?></b>
				<br />
				<?php echo  $US['secure-text-X']?>
				</p></td>
				<td><p>
				<?php if (($ucontents[0] == "root") || ($ucontents[1] == "root")) { echo "<a href=\"apsetup.php\"><font color=\"red\"><u>".$US['secure-unsecure']."</font></u></a>"; }
		   			else { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }?>
				</p></td>
			</tr>
			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-p']?></b> 
				<br />
				<?php echo  $US['secure-text-X']?>
				</p></td>
				<td><p>
				<?php if (($pscontents[0] == "root") || ($pscontents[1] == "root")) { echo "<a href=\"psetup.php\"><font color=\"red\"><u>".$US['secure-unsecure']."</font></u></a>"; }
		   			else { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }?>
				</p></td>
			</tr>


			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-sslp']?></b> 
				<br />
				<?php echo  $US['secure-text-X']?>
				</p></td>
				<td><p>
				<?php if (($sslpscontents[0] == "root") || ($sslpscontents[1] == "root")) { echo "<a href=\"sslpsetup.php\"><font color=\"red\"><u>".$US['secure-unsecure']."</font></u></a>"; }
		   			else { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }?>
				</p></td>
			</tr>

			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-sslcertp']?></b> 
				<br />
				<?php echo  $US['secure-text-sslX']?>
				</p></td>
				<td><p>
				<?php if (( !$unsecure_cert)) { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }
		   			else { echo "<a href=\"keycert.php\"><font color=\"red\"><u>".$US['secure-unsecure']."</font></u></a>"; }?>
				</p></td>
			</tr>


			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-s']?></b> 
				<br />
				<?php echo  $US['secure-text-7']?>
				</p></td>
				<td><p>
				<?php if ($scontents !== "root") { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }
		   			else { echo "<a href=\"mqsetup.php\"><font color=\"red\"><u>".$US['secure-unsecure']."</font></u></a>"; }?>
				</p></td>
			</tr>
		</table>

		<h3><?php echo  $US['secure-sub-1']?></h3>
		<p><?php echo  $US['secure-text-8']?></p>
		
		<table width="100%">
			<tr>
				<td width="75%"><b><?php echo  $US['secure-text-1']?></b></td>
				<td><b><?php echo  $US['secure-text-2']?></b></td>
			</tr>
			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-view']?></b>
				<br />
				<?php echo  $US['secure-look']?>
				</p></td>
				<td><p>
				<?php if (getenv("REMOTE_ADDR") == "127.0.0.1") { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }
		   			else { echo "<font color=\"red\">".$US['secure-unsecure']."</font>"; }?>
				</p></td>
			</tr>
			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-9']?></b>
				<br />
				<?php echo  $US['secure-text-10']?>
				</p></td>
				<td><p>
				<?php if (ini_get('safe_mode') == true) { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }
		   			else { echo "<a href=\"pconfig.php\"><font color=\"red\"><u>".$US['secure-unsecure']."</font></u></a>"; }?>
				</p></td>
			</tr>
<?php
	$usfap = strpos(file_get_contents("$apanel_path/.htaccess"), '#Require valid-user');
	$usfps = strpos(file_get_contents("$server_path/.htaccess"), '#Require valid-user');
	$usfps_ssl = strpos(file_get_contents("$server_path_ssl/.htaccess"), '#Require valid-user');
?>
			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-11']?></b>
				<br />
				<?php echo  $US['secure-text-13']?>
				</p></td>
				<td><p>
				<?php if ($usfap === false) { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }
		   			else { echo "<font color=\"red\">".$US['secure-unsecure']."</font>"; }?>
				</p></td>
			</tr>
			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-12']?></b>
				<br />
				<?php echo  $US['secure-text-14']?>
				</p></td>
				<td><p>
				<?php if ($usfps === false) { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }
		   			else { echo "<font color=\"red\">".$US['secure-unsecure']."</font>"; }?>
				</p></td>
			</tr>

			<tr valign="top">
				<td width="75%"><p>
				<b><?php echo  $US['secure-text-12ssl']?></b>
				<br />
				<?php echo  $US['secure-text-14ssl']?>
				</p></td>
				<td><p>
				<?php if ($usfps_ssl === false) { echo "<font color=\"green\">".$US['secure-secure']."</font>"; }
		   			else { echo "<font color=\"red\">".$US['secure-unsecure']."</font>"; }?>
				</p></td>
			</tr>


		</table>
	</div>

<?php
// Footer
include "includes/footer.php";
?>