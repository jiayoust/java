<h3><?php echo  $US['nav-tools']?></h3>
<a target="_phpmyadmin" onclick="h(this);" href="<?php echo $apanel?>/phpMyAdmin/"><?php echo  $US['nav-pma']?></a>
<a target="content" onclick="h(this);" href="downloads.php"><?php echo  $US['nav-down']?></a>
<a target="content" onclick="h(this);" href="<?php echo $apanel?>/cgi-bin/includes/lang/<?php echo file_get_contents("includes/.lang");?>/elog.cgi"><?php echo  $US['nav-elog']?></a>
<a target="content" onclick="h(this);" href="<?php echo $apanel?>/cgi-bin/includes/lang/<?php echo file_get_contents("includes/.lang");?>/u2w.cgi"><?php echo  $US['nav-u2w']?></a>
<a target="content" onclick="h(this);" href="<?php echo $apanel?>/cgi-bin/includes/lang/<?php echo file_get_contents("includes/.lang");?>/dserver.cgi"><?php echo  $US['nav-del']?></a>
<a target="content" onclick="h(this);" href="migrate.php"><?php echo  $US['nav-smig']?></a>
