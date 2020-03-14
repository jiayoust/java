##################################################
# File Name: ssl_gen.pl
# Server certificate and key generation
# By The Uniform Server Development Team
# Version 1.0
# 11-1-2009
# Edited Last By: Mike Gleaves (ric)
# Comment: Creates a server certificate and key.
#          Installs these to Apache's config folder
###################################################

print " \n ##############################################################################\n";
print " #                                                                            #\n";
print " # Uniform Server: Certificate and Key generator                              #\n";
print " #                                                                            #\n";
print " #----------------------------------------------------------------------------#\n";
print " # This script creates and installs a new self-signed certificate and key.    #\n";
print " #                                                                            #\n";
print " # 1) CN Common Name. Change this to your full domain name e.g. www.fred.com  #\n";
print " #    If you do not have a domain name use the default by pressing eneter.    #\n";
print " #                                                                            #\n";
print " # 2) To change any of the three defaults edit file:                          #\n";
print " #    uniform_server/udrive/plugins/key_cert_gen/ssl_gen.pl                   #\n";
print " #    search for the edit section and replace accordingly.                    #\n";
print " #                                                                            #\n";
print " #----------------------------------------------------------------------------#\n\n";

#== Set working folder
chdir "./gen";

#== Set environment variable
$ENV{'OPENSSL_CONF'} = 'openssl.cnf';

# Get user input
#********* Edit defaults **********************************************************

$str1 = &promptUser("  CN Common Name. Your full domain name ", "localhost");
$str2 = &promptUser("  OU Organization Unit (eg, section)  ", "Secure demo");
$str3 = &promptUser("  O  Organization Name (eg, company)    ", "UniServer");
print "\n ";

#********* Do not Edit below this line **********************************************

#== Create a private key and signing request
system ("openssl req -new -sha1 -newkey rsa:1024 -nodes -keyout server.key -out server.csr -subj \"/O=".$str3."/OU=".$str2."/CN=".$str1."\"");

#== Self-sign Certificate 
system ("openssl x509 -in server.csr -out server.crt -req -signkey server.key -days 3650");

#=== Copying new certificate and key to Server
print "\n #----------------------------------------------------------------------------#\n";
print " # Copying new certificate and key to Server                                  #\n";
print " # Location:                                                                  #\n";
print " #           Certificate:  usr/local/apache2/conf/ssl.crt/server.crt          #\n";
print " #           Key:          usr/local/apache2/conf/ssl.key/server.key          #\n";
print " #                                                                            #\n";
print " # Certificate Signing Request                                                #\n";
print " # Location:                                                                  #\n";
print " #           Certificate:  plugins/key_cert_gen/server.csr                    #\n";
print " #----------------------------------------------------------------------------#\n";
print " #                                                                            #\n";
print " # You must now restart the servers to enable the new configuration.          #\n";
print " #                                                                            #\n";
print " ##############################################################################\n\n";

coppy("server.crt", "../../../usr/local/apache2/conf/ssl.crt/server.crt");
coppy("server.key", "../../../usr/local/apache2/conf/ssl.key/server.key");
coppy("server.csr", "../server.csr");

#== Clean folder "gen" by deleting files:
unlink("server.key");
unlink("server.crt");

#== Clean environment variable
$ENV{'OPENSSL_CONF'} = '';

#== Enable SSL in Apache's config file \usr\local\apache2\conf\httpd.conf

@mpg=();                                                  # clear array
push(@mpg,"../../../usr/local/apache2/conf/httpd.conf");  # filepath
push(@mpg,"#LoadModule ssl_module modules/mod_ssl.so");   # search for
push(@mpg,"LoadModule ssl_module modules/mod_ssl.so");    # replace with
update_files(@mpg);                                       # search replace save file
#== END Enable SSL in Apache's config file

system (pause);
exit;

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
   $line=~s/$str1/$str2/ig;          # yes do the match again and replace
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



