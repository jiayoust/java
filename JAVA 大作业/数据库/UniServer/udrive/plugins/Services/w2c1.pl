##################################################
# File Name: w2c1.pl# The Uniform Server Service Install Tool
# By The Uniform Server Development Team
# Version 1.5
##################################################
# Version 1.7
# 26-11-2008
# Edited Last By: Olajide Olaolorun (empirex)
# Comment: New static directory for service mode
###################################################

print "This script will install Uniform Server as a Windows service!\n";

# Change this line to drive and folder requiired
# $pathc="e:";  -- Would install to drive e (top-level of disk)
# $pathc="h:/myserver"; -- Would install to drive h sub-folder myserver  

$pathc="c:/UniServer";


$paterns="\/www|\/home|\/cgi-bin|\/usr|\/tmp|\/htpasswd|\/scgi-bin|\/plugins";
$use=".conf|.htaccess|.cgi|.pl|.bat|.cnf|.ini|Config.pm";

mkdir ("$pathc");

convert ("../../","$pathc");
convert ("./files","$pathc");

###################################################################
#==================================================================
# Update Uniserver configuration files
# with new path information.

#=== C:\UniServer\home\admin\www\vhost.php
@mpg=();                                                # clear array
push(@mpg,"$pathc/home/admin/www/vhost.php");           # filepath
push(@mpg,"httpd.conf.sti.=.\\\"");                     # search for
push(@mpg,"httpd_conf_sti = \"$pathc");                 # replace with
update_files(@mpg);                                     # search replace save file

#=== UniServer\home\admin\www\aconfig.php
@mpg=();                                                # clear array
push(@mpg,"$pathc/home/admin/www/aconfig.php");         # filepath
push(@mpg,"apache.=.new.Config.\\\(\\\"");              # search for
push(@mpg,"apache = new Config \(\"$pathc");            # replace with
update_files(@mpg);                                     # search replace save file

#=== C:\UniServer\home\admin\www\pconfig.php
@mpg=();                                                # clear array
push(@mpg,"$pathc/home/admin/www/pconfig.php");         # filepath
push(@mpg,"PHP.=.new.Config.\\\(\\\"");                 # search for
push(@mpg,"PHP = new Config \(\"$pathc");               # replace with
update_files(@mpg);                                     # search replace save file

#=== C:\UniServer\usr\local\apache2\conf\httpd.conf
@mpg=();                                                # clear array
push(@mpg,"$pathc/usr/local/apache2/conf/httpd.conf");  # filepath
push(@mpg,"\\\/docs\\\/\\\"");                          # search for
push(@mpg,"$pathc/docs/\"");                            # replace with
update_files(@mpg);                                     # search replace save file

#=== C:\UniServer\usr\local\apache2\conf\httpd.conf
@mpg=();                                                # clear array
push(@mpg,"$pathc/usr/local/apache2/conf/httpd.conf");  # filepath
push(@mpg,"ScriptAlias $pathc/cgi-bin");                # search for
push(@mpg,"ScriptAlias /cgi-bin");                      # replace with
update_files(@mpg);                                     # search replace save file

#=== C:\UniServer\Install.bat
@mpg=();                                                # clear array
push(@mpg,"$pathc/Install.bat");                        # filepath
push(@mpg,"C..UniServer");                              # search for
push(@mpg,"$pathc");                                    # replace with
update_files(@mpg);                                     # search replace save file

#=== C:\UniServer\usr\local\apache2\conf\ssl.conf
@mpg=();                                                # clear array
push(@mpg,"$pathc/usr/local/apache2/conf/ssl.conf");    # filepath
push(@mpg,"DocumentRoot /ssl");                         # search for
push(@mpg,"DocumentRoot $pathc/ssl");                   # replace with
update_files(@mpg);                                     # search replace save file

#=== C:\UniServer\usr\local\apache2\conf\ssl.conf
@mpg=();                                                # clear array
push(@mpg,"$pathc/usr/local/apache2/conf/ssl.conf");    # filepath
push(@mpg,"Directory \"/ssl\"");                        # search for
push(@mpg,"Directory \"$pathc/ssl\"");                  # replace with
update_files(@mpg);                                     # search replace save file

#=== C:\UniServer\plugins\key_cert_gen\ssl_gen.pl
@mpg=();                                                # clear array
push(@mpg,"$pathc/plugins/key_cert_gen/ssl_gen.pl");    # filepath
push(@mpg,"../../..$pathc");                            # search for
push(@mpg,"../../..");                                  # replace with
update_files(@mpg);                                     # search replace save file

#==================================================================
###################################################################
print "Installing Apache2 as service ...";
$res=`$pathc/usr/local/apache2/bin/Apache.exe -f $pathc/usr/local/apache2/conf/httpd.conf -d $pathc/usr/local/apache2/. -k install -n "Apache2"`;
print " done!\n";
print "Installing MySQL as service ...";
$res=`$pathc/usr/local/mysql/bin/mysqld-opt.exe  --install MySQL --defaults-file=$pathc/usr/local/mysql/my.cnf`;
print " done!\n";
print "Starting Apache2 ...";
$res=`net start Apache2`;
print " done!\n";
print "starting MySQL ...";
$res=`net start mysql`;
print " done!\n";
print "Have a fun\n";

# These are copied however not used hence delete
unlink("$pathc/usb_server_start.bat");
unlink("$pathc/usb_server_stop.bat");

$res=`start $pathc/home/admin/www/redirect.html`;
#system('pause');

exit;

# usage convert (path,path1);
sub convert {
 my @names;
 my $name;
 my ($path)=$_[0] ;
 my ($path1)=$_[1] ;
 opendir DIR,"$path";
 $name=readdir DIR;
 $name=readdir DIR;

 @names=readdir DIR;
 closedir DIR;
 foreach $name (@names){
  if (-d "$path/$name"){mkdir ("$path1/$name"); convert ("$path/$name","$path1/$name")};
  if (!(-d "$path/$name")&&($name=~ /($use)$/)){
   print "$path/$name - converting ...";
   open (FILE,"$path/$name");
   binmode FILE;
   read FILE,$lines,100000;
   close (FILE);
   $lines=~ s/(\W)($paterns)/$1$pathc$2/g;
   open (FILE,">$path1/$name");
   binmode FILE;
   print FILE $lines;
   close (FILE);
   print " done!\n";
   } else {
   print "$path/$name - transferring ...";
   coppy ("$path/$name", "$path1/$name");
   print " done!\n";
   }
  }
 }

sub coppy {
   open (FILE,"$_[0]");
   binmode FILE;
   @lines=<FILE>;
   close (FILE);
   open (FILE,">$_[1]");
   binmode FILE;
   print FILE @lines;
   close (FILE);
}

##########################################################
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

 print "Searching $file for data\n"; # provide feedback to user

 foreach $line (@text){              # loop through array
  if($line=~/$str1/){                # check we have found a match
   $line=~s/$str1/$str2/ig;          # yes do the match again and replace
   $found = 1;                       # set tracking variable
   print " -Found match in file\n";  # print conformation
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







