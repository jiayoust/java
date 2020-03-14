#!/usr/bin/perl

require header;
require secure;

$basedir = $ENV{'DOCUMENT_ROOT'};                     # z:/***/www
$base    = substr($basedir, 0, length ($basedir)-3);  # z:/***/ 
$drive   = substr($basedir, 0, 2);                    # z:
$root= substr($basedir, 0, -3)."plugins/restore_mysql_password/Run.bat";


# If this file exists server running as standard program or disk-root 

system $drive; # Set drive
$file_exists=$base."usr/local/apache2/logs/drive.txt";
if (-e $file_exists)
{
`start $root`;

print <<ENDDD;
<div id="main">
<h2>&#187; MySQL password restored.</h2>

<p>Please stop and re-start the servers</p>

<br />

</div>
ENDDD

require footer;
exit;
}

else{
  # Running as service
print <<ENDDD;
<div id="main">
<h2>&#187; MySQL password not restored.</h2>
<p>Services are not allowed to interact with the desktop.</p>
<p>You need to run this script manually:</p>
<p>$root</p>

<br />

</div>
ENDDD

require footer;
exit;
}