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
include "config.inc.php";
include "$apanel_path/includes/lang/".file_get_contents("$apanel_path/includes/.lang").".php"; 
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title><?php echo  $US['title']?> <?php include('.version'); ?></title>
<meta name="author" content="Olajide Olaolorun" />
<meta http-equiv="page-enter" content="blendtrans(duration=0.1)" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<script type="text/javascript" src="<?php echo  $apanel; ?>/js/main.js"></script>
<link href="<?php echo  $apanel; ?>/css/main.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<?php echo  $apanel; ?>/favicon.ico" />
	  <link title="Homepage" href="./index.php" rel="top" />
	  <link title="Up" href="./index.php" rel="up" />
 	  <link title="First page" href="./index.php" rel="first" />
	  <link title="Previous page" href="./index.php" rel="previous" />
	  <link title="Next page" href="./index.php" rel="next" />
	  <link title="Last page" href="./index.php" rel="last" />
	  <link title="Table of contents" href="./index.php" rel="toc" />
	  <link title="Site map" href="./index.php" rel="index" />
</head>

<body>

