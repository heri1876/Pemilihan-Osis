<?php
	//Import File Koneksi Database
	require_once('koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM calon";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Perulangan agar seluru data di database terpassing ke array
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		$result [] = array(
		"nomer"=>$row['nomer'],
		"foto"=>$row['foto'],
		"nama_ketua"=>$row['nama_ketua'],
		"nama_wakil"=>$row['nama_wakil'],
		"visi"=>$row['visi'],
		"misi"=>$row['misi']
		);
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>