<h3><?php echo  $US['title']?> <?php include('.version') ?></h3>
<a target="content" onClick="h(this);" href="start.php"><?php echo  $US['nav-home']?></a>
<a target="content" onclick="h(this);" href="security.php"><?php echo  $US['nav-secure']?></a>
<a target="content" onclick="h(this);" href="phpinfo.php"><?php echo  $US['nav-phpinfo']?></a>
<a target="content" onclick="h(this);" href="<?php echo $apanel?>/cgi-bin/includes/lang/<?php echo file_get_contents("includes/.lang");?>/cgienv.cgi"><?php echo  $US['nav-cgienv']?></a>
<a target="_status" onclick="h(this);" href="/server-status"><?php echo  $US['nav-status']?></a>
<a target="_info"   onclick="h(this);" href="/server-info"><?php echo  $US['nav-info']?></a>
<a target="content" onclick="h(this);" href="update.php"><?php echo  $US['nav-update']?></a>