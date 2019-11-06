<?php

    session_start();
    
    //Mendapatkan Nilai Dari Variable yang ingin ditampilkan
    $pilihan = $_GET['pilihan'];

	//Import file koneksi
    require_once('koneksi.php');
	
	//Membuat SQL Query dengan user yang ditentukan secara spesifik
    $sql = "SELECT * FROM user WHERE pilihan='$pilihan'";
    
    //Mendapatkan Hasil
    $r = mysqli_query($con,$sql);

    //Mengecek hasil
    if ($result = mysqli_query($con,$sql)){
        $rowcount = mysqli_num_rows($result);
        echo $rowcount;
    }else{
        echo "Login Gagal";
    }
    
  
?>