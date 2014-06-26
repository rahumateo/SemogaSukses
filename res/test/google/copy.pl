#Name : Rahmat Hidayah SB
#NPM  : 1006666753

	open(IN, "clefGoogleA.txt");
	open(EXPORT, ">lol.csv");

	$cc = 0;
	while($line = <IN>){
		if($line =~/*** Reading Question/){
			$cc++;
			print EXPORT "$cc,";
		}
		if($line =~/[1]	/){
			$line = s/[1]	//;
			$line = s/,/-/;
			print $line;
			print EXPORT "$line,";
		}
		if($line =~/*** Time spent : /){
			$line = s/*** Time spent : //;
			$line = s/ seconds ***//;
			print $line."\n";
			print EXPORT "$line\n";
		}
	}


	close(IN);
	close(EXPORT);