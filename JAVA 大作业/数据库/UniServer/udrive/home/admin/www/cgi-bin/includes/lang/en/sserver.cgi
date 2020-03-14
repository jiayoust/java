#!/usr/bin/perl

require header;
require secure;

$basedir = $ENV{'DOCUMENT_ROOT'};                     # z:/***/www
$base    = substr($basedir, 0, length ($basedir)-3);  # z:/***/ 
$drive   = substr($basedir, 0, 2);                    # z:

# If this file exists server running as standard program or disk-root 

system $drive; # Set drive
$file_exists=$base."usr/local/apache2/logs/drive.txt";
if (-e $file_exists)
{
print <<ENDDD;
<div id="main">
<h2>&#187; Shutdown Server</h2>
<h3>Shutting Down Server</h3>
<p>
The server is shutting down.
<br />
<br />
Thank you for using <a href="http://www.uniformserver.com/">The Uniform Server</a>.<br />
<br />
</p>
</div>
ENDDD

#  # Run batch file to kill Apache.exe, Unicontroller and virtual drive
#  # Perl can only kill one before itself is killed

  system $base."home/admin/program/apanel_stop_servers.bat $drive";

  require footer;
  exit;
} 
else{
  # Running as service
print <<ENDDD;
<div id="main">
<h2>&#187; Shutdown Server</h2>
<h3>Unable to Shut Down Server</h3>
<p>
The server is running as a service hence not shut down.<br />Please use Uninstall.bat
<br />
<br />
Thank you for using <a href="http://www.uniformserver.com/">The Uniform Server</a>.<br />
<br />
</p>
</div>
ENDDD

  require footer;
  exit;
}
