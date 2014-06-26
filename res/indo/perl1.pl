#Name : Rahmat Hidayah SB
#NPM  : 1006666753
#Colaborator : Nisrina Luthfiyati
#			   M. Afour Satria

open(IN, "GEO_1COUNTRY+.lst");
open(OUT, ">GEO_1COUNTRY#.lst");

print "--------------------------------------------\n";
print "Processing corpus : korpus".$files.".txt...\n";

while($line = <IN>){
	$line =~s/^\s*|\s*$//g;
	print OUT "$line;translasi $line\n";
}

close(IN);
close(OUT);

print "Processing corpus done\n";
print "--------------------------------------------\n";
