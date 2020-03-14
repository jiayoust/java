#!/usr/bin/perl

require header;
require secure;

$basedir = $ENV{'DOCUMENT_ROOT'};                     # z:/***/www
$base    = substr($basedir, 0, length ($basedir)-3);  # z:/***/ 
$drive   = substr($basedir, 0, 2);                    # z:
$root= substr($basedir, 0, -3)."plugins/key_cert_gen/Run.bat";


# If this file exists server running as standard program or disk-root 

system $drive; # Set drive
$file_exists=$base."usr/local/apache2/logs/drive.txt";
if (-e $file_exists)
{
`start $root`;

print <<ENDDD;
<div id="main">
<h2>&#187; Certificate and Key Generation Complete</h2>

<p>Cirtificate location:<br />\\usr\\local\\apache2\\conf\\ssl.crt\\server.crt</p>
<p>Key location:<br />\\usr\\local\\apache2\\conf\\ssl.key\\server.key</p>
<br />

</div>
ENDDD

require footer;
exit;
}
else {
  # Running as service
print <<ENDDD;
<div id="main">
<h2>&#187; Unable to run Certificate and Key Generation</h2>
<p>Services are not allowed to interact with the desktop.</p>
<p>You need to run this script manually:</p>
<p>$root</p>

<br />

</div>
ENDDD

require footer;
exit;
}