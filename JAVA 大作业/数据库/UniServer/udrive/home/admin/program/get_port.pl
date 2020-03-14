#####################################################
# File Name: ssl_gen.pl
# Server certificate and key generation
# By The Uniform Server Development Team
# Version 1.0
# 21-1-2009
# Edited Last By: Mike Gleaves (ric)
# Comment: Obtains port number from config files
# Input:   Takes a single argument apache, ssl, mysql
# Output:  Returns port number as exit code.
######################################################

# == Get absolute path to this script
use File::Basename;
use File::Spec;

my $abs_file=File::Spec->rel2abs( __FILE__ ); # Path including file name
my $abs_path = dirname($abs_file);            # Absolute path

# == Change location
chdir $abs_path;                              # Change to this folder
chdir "../../../usr/local";                   # Change to local folder

# == Check number of args give up if zero
if ($#ARGV +1 eq "0"){
 exit (0);
}

# == Find Apache standard port
if ($ARGV[0] eq "apache"){
  my $file = 'apache2/conf/httpd.conf'; # File to search
  my $var = '';                         # Set var 
  open(R_FILE,"<$file") or die $!;      # Open file
   while(my $line = <R_FILE>){          # Read each line 
    if($line =~ /^Listen/){             # Line starts with
      ($var) = $line =~ /(\d+)/;        # Extract digits
       last;                            # End while loop
    }
   }
  close R_FILE;                         # Close file
  exit ($var);                          # Return port to batch file
}                            

# == Find Apache SSL port
if ($ARGV[0] eq "ssl"){
  my $file = 'apache2/conf/ssl.conf';   # File to search
  my $var = '';                         # Set var 
  open(R_FILE,"<$file") or die $!;      # Open file
   while(my $line = <R_FILE>){          # Read each line 
    if($line =~ /^Listen/){             # Line starts with
      ($var) = $line =~ /(\d+)/;        # Extract digits
       last;                            # End while loop
    }
   }
  close R_FILE;                         # Close file
  exit ($var);                          # Return port to batch file
}

# == Find MySQL SSL port
if ($ARGV[0] eq "mysql"){
  my $file = 'mysql/my.cnf';            # File to search
  my $var = '';                         # Set var 
  open(R_FILE,"<$file") or die $!;      # Open file
   while(my $line = <R_FILE>){          # Read each line 
    if($line =~ /^port=/){              # Line starts with
      ($var) = $line =~ /(\d+)/;        # Extract digits
       last;                            # End while loop
    }
   }
  close R_FILE;                         # Close file
  exit ($var);                          # Return port to batch file
}

exit (0);


