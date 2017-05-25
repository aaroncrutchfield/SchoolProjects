<?php

// *********************
// File-level encryption
// *********************

/**
 *	(1) We have some data to encrypt
 */
echo "<h2>Original String</h2>";

$str = "this is a string I want to encrypt"; 
echo $str;

/**
 *	(2) We use a public key to encrypt the data
 *
 *	GPG uses two special keys:
 *		public key
 *		private key
 *
 *	The keys are generated such that one key is used to
 *	encrypt data and another is used to decrypt. We
 *	can distribute the public key to anyone who may need
 *	to send us encrypted data.
 *
 *	Keys are generated in pairs using a special utility
 *	mode, and a user ID is associated with the key pair.
 *	We use this to indentify which key we want to use;
 *	there can be many public keys stored. The user ID
 *	for our key is 'fred rabbit'
 */
echo "<h2>This is the public key I am using</h2>";
$arr=array();

$result=exec("gpg2 --armor --export 'fred rabbit'", $arr);
// gpg2			the program
// --armor		export as ascii text
// --export 	command to show the public key

foreach($arr as $line){
	echo $line.'<br/>';
}

/*
 *	(3) Store the data to be encrypted on the hard drive.
 *	We will delete it later.
 */
echo "<h2>Saving to disk (temporarily)</h2>";
file_put_contents('tmp/tmpdata.txt', $str);	// save to a temporary location
chmod('tmp/tmpdata.txt', 0777);					// make file writable
echo "done";

/*
 *	(4) Encrypt the data
 */
echo "<h2>Encrypting file</h2>";
$arr=array();

$command="gpg2 --always-trust -r 'fred rabbit' -o 'tmp/tmpdata.txt.gpg' --armor --encrypt 'tmp/tmpdata.txt'; echo done";
// --always-trust						PHP tells GPG2 that the key can be trusted
// -r 'fred rabbit'					use fred's public key
// -o 'tmp/tmpdata.txt.gpg'		where to save the encryped file
// --encrypt 'tmp/tmpdata.txt'	which fiel to encrypt

$result=exec($command,$arr);
echo $result;

echo "<h2>Contents of encrypted file</h2>";
$newstr=file_get_contents('tmp/tmpdata.txt.gpg');
echo nl2br($newstr);

/*
 *	(5) The private key is not given out, but will be
 *	installed on the computer or server that is under
 *	our control. Data encrypted with the public key
 *	can only be decrypted with the private key.
 */
echo "<h2>Can only be decrypted with this private key</h2>";
$arr=array();

$command="gpg2 --armor --export-secret-key 'fred rabbit'";
// --export-secret-key		command to show the private key
// under normal circumstance we would NEVER let this be visible

$result=exec($command, $arr);
foreach($arr as $line){
	echo $line.'<br/>';
}

/*
 *	(6) Decrypting the data
 *	A key pair can also include a passphrase.
 *	This can be useful if the private key is leaked;
 *	it would take a while to crack the passphrase
 *	and we can generate and distribute a new key
 *	by then. The passphrase for our private key
 *	is "rabbits". Normally we would use a much much
 *	longer passphrase (20+ characters)
 */
echo "<h2>Decrypting the encrypted file</h2>";
$arr=array();

$command="echo rabbits | gpg2 --batch --passphrase-fd 0 --decrypt 'tmp/tmpdata.txt.gpg'";
// echo rabbits | 			sends the passphrase into GPG2
// --batch						required for PHP, facilitates automatic decryption
// --passphrase-fd 0			states that the passphrase is coming
// --decrypt [filename]		command to decrypt the data		

$result=exec($command, $arr);
foreach($arr as $line){
	echo $line.'<br/>';
}

// cleanup - remove the original and encrypted files
unlink('tmp/tmpdata.txt');
unlink('tmp/tmpdata.txt.gpg');

/*
 *	Final note:
 *	To get this to work on your own server,
 *	you will need to generate a new key pair:
 *
 *	gpg2 --gen-key
 *
 *	Run this at a command line and it will
 *	step through the process. A number of
 *	tutorials are available online for installing
 *	keys.
 *
 */