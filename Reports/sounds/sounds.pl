#!/usr/bin/perl -w
use strict;

use DBI;
use utf8;
use File::Copy qw(copy);


my $database = "dbb2";
my $hostname = "localhost";
my $port = "3306";
my $user="monty";
my $password = "12345";


my $dsn = "DBI:mysql:database=$database;host=$hostname;port=$port";

my $dbh = DBI->connect($dsn, $user, $password  );

$dbh->{'mysql_enable_utf8'} = 1;
$dbh->do('SET NAMES utf8');
 

my $sql = "select uniqueid from cdr"; 
 
my $sth = $dbh->prepare($sql);
    
   
$sth->execute();  

my $old_name = "template.mp3";

# $old_name =~ s/\.mp3//;

 print  $old_name."\n";
 
while (my $ref = $sth->fetchrow_hashref())
{
 
#   my $new_name = "../../sounds/".$ref->{'uniqueid'}.".mp3";
   my $new_name = $ref->{'uniqueid'}.".mp3";
  
   copy $old_name, $new_name;
  
   print  $ref->{'uniqueid'}."\n";
   

 
}
