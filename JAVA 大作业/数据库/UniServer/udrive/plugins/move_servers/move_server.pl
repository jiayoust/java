##################################################
# File Name: move.pl
# Move Server to different ports change service name
# By The Uniform Server Development Team
# Version 1.0
# 28-2-2009
# Edited Last By: Mike Gleaves (ric)
# Comment: Alows multi-servers to run on same PC.
###################################################

print " \n ##############################################################################\n";
print " #                                                                            #\n";
print " # Uniform Server: Move Server to different ports change service name         #\n";
print " #                                                                            #\n";
print " #----------------------------------------------------------------------------#\n";
print " # This script must be run on a clean install of Mona                         #\n";
print " #                                                                            #\n";
print " # 1) To accept defaults [shown in square brackets] press enter               #\n";
print " #                                                                            #\n";
print " #----------------------------------------------------------------------------#\n\n";

#== Set working folder
chdir ".";

# == Var used
my $avoid1 ='135';                # Avoid this port
my $avoid2 ='445';                # Avoid this port

my $Apache_port ='';              # Server port
my $Apache_port_old ='';          # Server port
my $Apache_ssl_port ='';          # Server port
my $Apache_ssl_port_old ='';      # Server port
my $Apache_name ='';              # Apache executable name
my $Apache_name_old ='';          # Apache executable name
my $Unicon_name ='';              # UniController executable name
my $Unicon_name_old ='';          # UniControler executable name
my $Apache_service_name ='';      # Apache service name
my $Apache_service_name_old ='';  # Apache original service name
my $MySQL_port ='';               # Server port
my $MySQL_port_old ='';           # Server port
my $MySQL_service_name ='';       # MySQL service name
my $MySQL_service_name_old ='';   # MySQL original service name
my $service_path ='';             # service folder
my $service_path_old ='';         # service folder original

# == Get Apache port
my $file = "../../usr/local/apache2/conf/httpd.conf";
my $var = '';                             # Set var 
open(R_FILE,"<$file") or die $!;          # Open file
while(my $line = <R_FILE>){               # Read each line 
  if($line =~ /^Listen/){                 # Line starts with
    ($var) = $line =~ /(\d+)/;            # Extract digits
    last;                                 # End while loop
  }
}
close R_FILE;                             # Close file
my $Apache_port_old = $var;               # Set port
my $Apache_port = $var+1;                 # Set proposed port

# Avoid these ports
if($Apache_port eq $avoid1 || $Apache_port eq $avoid2){
  $Apache_port = $Apache_port+1;
}
# == END Get Apache port

# == Get Apache SSL port
my $file = "../../usr/local/apache2/conf/ssl.conf";
my $var = '';                             # Set var 
open(R_FILE,"<$file") or die $!;          # Open file
while(my $line = <R_FILE>){               # Read each line 
  if($line =~ /^Listen/){                 # Line starts with
    ($var) = $line =~ /(\d+)/;            # Extract digits
    last;                                 # End while loop
  }
}
close R_FILE;                             # Close file
my $Apache_ssl_port_old = $var;           # Set port
my $Apache_ssl_port = $var+1;             # Set proposed port

# Avoid these ports
if($Apache_ssl_port eq $avoid1 || $Apache_ssl_port eq $avoid2){
  $Apache_ssl_port = $Apache_ssl_port+1;
}
# == END Get Apache SSL port

# == Get MySQL port
my $file = "../../usr/local/mysql/my.cnf";
my $var = '';                              # Set var 
open(R_FILE,"<$file") or die $!;           # Open file
while(my $line = <R_FILE>){                # Read each line 
  if($line =~ /^port/){                    # Line starts with
    ($var) = $line =~ /(\d+)/;             # Extract digits
    last;                                  # End while loop
  }
}
close R_FILE;                              # Close file
my $MySQL_port_old = $var;                 # Set port
my $MySQL_port = $var+1;                   # Set proposed port

# Avoid these ports
if($MySQL_port eq $avoid1 || $MySQL_port eq $avoid2){
  $MySQL_port = $MySQL_port+1;
}
# == END Get MySQL port

# == Get Apache Executabe name
$dirtoget="../../usr/local/apache2/bin/";                      # Path to folder
opendir(APACHEBIN, $dirtoget) || die("Cannot open directory"); # Get handle
@thefiles= readdir(APACHEBIN);                                 # read its content
closedir(APACHEBIN);                                           # close handle

my $var = '';                   # Set var 
foreach $f (@thefiles){
  if($f =~ /Apache/){           # Line contains Apache
    ($var) = $f =~ /(\d+)/;     # Extract digits
    last;                       # End loop
  }
}  
 $Apache_name_old = "Apache".$var.".exe";
 $Apache_name = "Apache".($var+1).".exe";
# == END Get Apache Executabe name

# == Get Service path
my $file = "../Services/w2c1.pl";
my $var = '';                               # Set var 
open(R_FILE,"<$file") or die $!;            # Open file
while(my $line = <R_FILE>){                 # Read each line 
  if($line =~ /^.pathc=/){                  # Line starts with
    $line =~ /^.pathc=\"(.+[^\d])(\d*)\"/;  # Extract path and number
    $service_path_old =$1.$2;               # set old path
    $service_path =$1.($2+1);               # create new path increment number
    last;                                   # End while loop
  }
}
close R_FILE;                               # Close file
# == END Get Service path

# Original service names are MySQL Apache2

# == Get Apache Service name
my $file = "../Services/files/Uninstall.bat";
my $var = '';                               # Set var 
open(R_FILE,"<$file") or die $!;            # Open file
while(my $line = <R_FILE>){                 # Read each line 
  if($line =~ /^net stop Apache/){          # Line starts with
    $line =~ /^net stop ([A-Za-z]+)(\d+)?/; # Extract service name and number
    $Apache_service_name_old = $1.$2;       # set old path
    $Apache_service_name = $1.($2+1);       # create new path increment number
    last;                                   # End while loop
  }
}
close R_FILE;                               # Close file
# == END Get Apache Service name

# == Get MySQL Service name
my $file = "../Services/files/Uninstall.bat";
my $var = '';                               # Set var 
open(R_FILE,"<$file") or die $!;            # Open file
while(my $line = <R_FILE>){                 # Read each line 
  if($line =~ /^net stop MySQL/){           # Line starts with
    $line =~ /^net stop ([A-Za-z]+)(\d+)?/; # Extract service name and number
    $MySQL_service_name_old = $1.$2;        # set old path
    $MySQL_service_name = $1.($2+1);        # create new path increment number
    last;                                   # End while loop
  }
}
close R_FILE;                               # Close file
# == END Get MySQL Service name

# == Get Unicontroller Executabe name
$dirtoget2="..\\..\\..\\";                   # Path to folder
opendir(UNICON, $dirtoget2) || die("Cannot open directory"); # Get handle
@thefiles2= readdir(UNICON);                                 # read its content
closedir(UNICON);                                            # close handle

my $var2 = '';                   # Set var 
foreach $f2 (@thefiles2){
  if($f2 =~ /UniController/){    # Line contains Apache
    ($var2) = $f2 =~ /(\d+)/;    # Extract digits
    last;                        # End loop
  }
}  
 $Unicon_name_old = "UniController".$var2.".exe";
 $Unicon_name = "UniController".($var2+1).".exe";
# == END Get  Unicontroller  Executabe name

#== Get user inputs

$Apache_port     = &promptUser(" Current Apache port = $Apache_port_old         Proposed port ", $Apache_port);
# Avoid these ports
if($Apache_port eq $avoid1 || $Apache_port eq $avoid2){
 print "\n == Note: You cannot use ports $avoid1 or $avoid2!\n Please enter a new value\n\n";
  while($Apache_port eq $avoid1 || $Apache_port eq $avoid2){
    $Apache_port     = &promptUser(" Current Apache port = $Apache_port_old         Proposed port ", $Apache_port+1);
    print "\n";
  }
}

$Apache_ssl_port = &promptUser(" Current SSL port    = $Apache_ssl_port_old        Proposed port ", $Apache_ssl_port);
# Avoid these ports
if($Apache_ssl_port eq $avoid1 || $Apache_ssl_port eq $avoid2){
 print "\n == Note: You cannot use ports $avoid1 or $avoid2!\n Please enter a new value\n\n";
  while($Apache_ssl_port eq $avoid1 || $Apache_ssl_port eq $avoid2){
    $Apache_ssl_port     = &promptUser(" Current SSL port = $Apache_ssl_port_old         Proposed port ", $Apache_ssl_port+1);
    print "\n";
  }
}

$MySQL_port      = &promptUser(" Current MySQL port  = $MySQL_port_old       Proposed port ", $MySQL_port);
# Avoid these ports
if($MySQL_port eq $avoid1 || $MySQL_port eq $avoid2){
 print "\n == Note: You cannot use ports $avoid1 or $avoid2!\n Please enter a new value\n\n";
  while($MySQL_port eq $avoid1 || $MySQL_port eq $avoid2){
    $MySQL_port     = &promptUser(" Current MySQL port = $MySQL_port_old         Proposed port ", $MySQL_port+1);
    print "\n";
  }
}

# All three ports must be unique abort if not
if($Apache_port eq $Apache_ssl_port or $MySQL_port eq $Apache_port or $MySQL_port eq $Apache_ssl_port){
 print "\n NOTE: All ports must be unique rerun this script again\n\n";
 exit;
}

$Apache_name     = &promptUser(" Current Apache name = $Apache_name_old Proposed name ", $Apache_name);

print "\n ===== SERVICE Path and Service names ==== \n\n";                                            
$service_path        = &promptUser(" Current Path = $service_path_old   Proposed path = ", "$service_path");
$Apache_service_name = &promptUser(" Current Apache name = $Apache_service_name_old Proposed name = ", "$Apache_service_name");
$MySQL_service_name  = &promptUser(" Current MySQL  name = $MySQL_service_name_old Proposed name = ", "$MySQL_service_name");
print "\n ===== Controller Executable name  ==== \n\n"; 
$Unicon_name     = &promptUser(" Current = $Unicon_name_old Proposed ", "$Unicon_name");
print "\n";
print "\n ===== eAccelerator  ==== \n"; 
print "\n If running a single server do not disable eAccelerator.\n For multi-servers enter YES \n\n"; 
$Disable_eAccelerator = &promptUser(" Disable_eAccelerator type YES or N ", "YES");
print "\n";
$commit  = &promptUser(" Commit type Y or N ", "Y");

if($commit ne "Y"){
  print "\nNo action taken\n\n";
  exit;
}

print "\n";

if($Disable_eAccelerator eq "YES"){
#== Disabe eAccelerator php.ini
@mpg=();                                  # clear array
push(@mpg,"../../usr/local/php/php.ini"); # filepath

push(@mpg,"^.eaccelerator");              # search for
push(@mpg,"\; [eaccelerator");            # replace with

push(@mpg,"^extension=eaccelerator");     # search for
push(@mpg,"\; extension=eaccelerator");   # replace with

push(@mpg,"^eaccelerator");               # search for
push(@mpg,"\; eaccelerator");             # replace with

update_files(@mpg);                       # search replace save file
#== END Disabe eAccelerator php.ini
}

#== Rename UniController executable file
rename("../../../$Unicon_name_old", "../../../$Unicon_name");
#== END Rename UniController executable file

#== Update w2c1.pl plugin change folder - option
@mpg=();                                         # clear array
push(@mpg,"../Services/w2c1.pl");                # filepath

push(@mpg,"pathc=\"$service_path_old");          # search for
push(@mpg,"pathc=\"$service_path");              # replace with

push(@mpg,"bin\/$Apache_name_old");              # search for
push(@mpg,"bin\/$Apache_name");                  # replace with

push(@mpg,"$Apache_service_name_old\"");         # search for
push(@mpg,"$Apache_service_name\"");             # replace with

push(@mpg,"install $MySQL_service_name_old");    # search for
push(@mpg,"install $MySQL_service_name");        # replace with

push(@mpg,"net start $Apache_service_name_old"); # search for
push(@mpg,"net start $Apache_service_name");     # replace with

push(@mpg,"net start $MySQL_service_name_old");  # search for
push(@mpg,"net start $MySQL_service_name");      # replace with

push(@mpg,"net start mysql");                    # search for
push(@mpg,"net start $MySQL_service_name");      # replace with

update_files(@mpg);                              # search replace save file
#== END Update w2c1.pl plugin change folder - option

#== Update Install.bat plugin change - options
@mpg=();                                          # clear array
push(@mpg,"../Services/files/Install.bat");       # filepath

push(@mpg,"$Apache_service_name_old\"");          # search for
push(@mpg,"$Apache_service_name\"");              # replace with

push(@mpg,"^net start $Apache_service_name_old"); # search for
push(@mpg,"net start $Apache_service_name");      # replace with

push(@mpg,"install $MySQL_service_name_old");     # search for
push(@mpg,"install $MySQL_service_name");         # replace with

push(@mpg,"start $MySQL_service_name_old");       # search for
push(@mpg,"start $MySQL_service_name");           # replace with

update_files(@mpg);                               # search replace save file
#== END Update Install.bat plugin change - options

#== Update Uninstall.bat plugin change - options
@mpg=();                                         # clear array
push(@mpg,"../Services/files/Uninstall.bat");    # filepath

push(@mpg,"$Apache_service_name_old\"");         # search for
push(@mpg,"$Apache_service_name\"");             # replace with

push(@mpg,"^net stop $Apache_service_name_old"); # search for
push(@mpg,"net stop $Apache_service_name");      # replace with

push(@mpg,"\0x0020$MySQL_service_name_old ");    # search for
push(@mpg," $MySQL_service_name");               # replace with

push(@mpg,"stop $MySQL_service_name_old");       # search for
push(@mpg,"stop $MySQL_service_name");           # replace with

push(@mpg,"remove $MySQL_service_name_old");     # search for
push(@mpg,"remove $MySQL_service_name");         # replace with

update_files(@mpg);                              # search replace save file

#== END Update Uninstall.bat plugin change - options

#== Rename Apache executable file
rename("../../usr/local/apache2/bin/$Apache_name_old", "../../usr/local/apache2/bin/$Apache_name");
#== END Rename Apache executable file

#== Create configuration file for unicontroller
open (UniCon, '>../../usr/local/apache2/bin/unicon.ini') or die $!;  ;
print UniCon "[APACHE]\napache_exe=$Apache_name";
close (UniCon); 
#== END Create configuration file for unicontroller

#== Create Unicon text file
open (UniCon, '>../../home/admin/program/unicon.txt') or die $!;  ;
print UniCon "$Unicon_name";
close (UniCon); 
#== END Create UniCon text file


#== Update Apache config with new port
@mpg=();                                               # clear array
push(@mpg,"../../usr/local/apache2/conf/httpd.conf");  # filepath
push(@mpg,"^Listen");                                  # search for this part of line
push(@mpg,"Listen $Apache_port\n");                    # replace with this line
line_replace(@mpg);                                    # search replace save file

@mpg=();                                               # clear array
push(@mpg,"../../usr/local/apache2/conf/httpd.conf");  # filepath
push(@mpg,"ServerName localhost:$Apache_port_old");    # search for
push(@mpg,"ServerName localhost:$Apache_port");        # replace with
update_files(@mpg);                                    # search replace save file
#== Update Update Apache config with new port

#== Update Apache SSL config with new port
@mpg=();                                               # clear array
push(@mpg,"../../usr/local/apache2/conf/ssl.conf");    # filepath
push(@mpg,"^Listen");                                  # search for this part of line
push(@mpg,"Listen $Apache_ssl_port\n");                # replace with this line
line_replace(@mpg);                                    # search replace save file

@mpg=();                                               # clear array
push(@mpg,"../../usr/local/apache2/conf/ssl.conf");    # filepath
push(@mpg,":$Apache_ssl_port_old");                    # search for
push(@mpg,":$Apache_ssl_port");                        # replace with
update_files(@mpg);                                    # search replace save file
#== Update Update Apache SSL config with new port

#== Update MySQL config with new port
@mpg=();                                    # clear array
push(@mpg,"../../usr/local/mysql/my.cnf");  # filepath
push(@mpg,"^port");                         # search for this part of line
push(@mpg,"port=$MySQL_port\n");            # replace with this line
line_replace(@mpg);                         # search replace save file
#== Update Update MySQL config with new port


#== Update redirect.html to Apanel 
@mpg=();                                                                # clear array
push(@mpg,"../../home/admin/www/redirect.html");                        # filepath
push(@mpg,"^<meta http-equiv=\"refresh");                               # search for this part of line
push(@mpg,"<meta http-equiv=\"refresh\" content=\"1;url=http://localhost:$Apache_port/apanel/\">\n"); # replace with this line
line_replace(@mpg);                                                     # search replace save file
#== Update phpMyAdmin config file with new port


print " Server ports have been changed and services renamed \n\n";

###################################################################
#==================================================================
# Function: Search and replace all string matches in a file.
# Call using: update_files(@myarray) @myarray ordered as follows:
# full file path, search str 1,replace str 1, search str 2,replace str 2..
#...................................................................
 
sub update_files {

my @search_info = @_;                # from flattened array @_ create new array 
@search_info=reverse(@search_info);  # re-order to give file path first
my $file = pop(@search_info);        # get first element of array. File to search

open(INFILE,$file) or (print "Could not open $file for reading: $!\n" and return);
my @text=<INFILE>;                   # we have file handle, load file to array for searching
close(INFILE);                       # close file

my $found = 0;                       # Set if string found in array loaded
while (@search_info !=0){            # loop through replacement array until all data used

 $str1 = pop(@search_info);          # search for
 $str2 = pop(@search_info);          # replace with string

 foreach $line (@text){              # loop through array
  if($line=~/$str1/){                # check we have found a match
   $line=~ s/$str1/$str2/ig;         # yes do the match again and replace
   $found = 1;                       # set tracking variable
  }
 }#end foreach
}#end while

 if($found){                         # A match was found hence write to file
  open(OUTFILE,">$file") or (print "Unable to open $file for writing:" and return);
    foreach $save(@text){            # loop through array set each line to variable $save
     print OUTFILE $save;            # write each line contained in variable $save
    }
  close(OUTFILE);                    # finished close file
 }
}#end sub
#========================================= End sub update_files ====

###################################################################
#==================================================================
# Function: Search and replace a line of text in a file.
# Call using: line_replace(@myarray) @myarray ordered as follows:
# full file path, search str 1,replace str 1, search str 2,replace str 2..
#...................................................................
 
sub line_replace {

my @search_info = @_;                # from flattened array @_ create new array 
@search_info=reverse(@search_info);  # re-order to give file path first
my $file = pop(@search_info);        # get first element of array. File to search

open(INFILE,$file) or (print "Could not open $file for reading: $!\n" and return);
my @text=<INFILE>;                   # we have file handle, load file to array for searching
close(INFILE);                       # close file

my $found = 0;                       # Set if line found in array loaded
while (@search_info !=0){            # loop through replacement array until all data used

 $str1 = pop(@search_info);          # search for line
 $str2 = pop(@search_info);          # replace with string 

 foreach $line (@text){              # loop through array
  if($line=~/$str1/){                # check we have found a match

   $line=$str2;                      # yes replace line
   $found = 1;                       # set tracking variable
  }
 }#end foreach
}#end while

 if($found){                         # A match was found hence write to file
  open(OUTFILE,">$file") or (print "Unable to open $file for writing:" and return);
    foreach $save(@text){            # loop through array set each line to variable $save
     print OUTFILE $save;            # write each line contained in variable $save
    }
  close(OUTFILE);                    # finished close file
 }
}#end sub
#========================================= End sub update_files ====

#######################################################################
#======================================================================
# Function: Prompt user and get user input, returns value input by user.
#           Or if return pressed returns a default if used e.g usage
# $name = &promptUser("Enter your name ");
# $serverName = &promptUser("Enter your server name ", "localhost");
#.......................................................................
sub promptUser {
  local($promptStr,$defaultVal) = @_;         # make input arguments
                                              # local variables
  if ($defaultVal) {                             # If a default set
     print $promptStr, "[", $defaultVal, "] : "; # Print prompt and defaul
  } else {                                    # No default set
     print $promptStr, ": ";                  # Print prompt only
  } 

  $| = 1;                             # force a flush after our print
  $_ = <STDIN>;                       # get the input from STDIN 

  chomp;                              # remove newline character

  if ("$defaultVal") {                # check default is set 
     return $_ ? $_ : $defaultVal;    # return $_ if it has a value
  } else {                            # not set                          
     return $_;                       # just return entered value
  }
}
#========================================= End promptUser ============




