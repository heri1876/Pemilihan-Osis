<?php
	//Mendapatkan Nilai dari Variable
    $nik = $_GET['nik'];
    $pilihan = $_GET['pilihan'];

    //Pembuatan Syntax SQL
    $sql = "UPDATE user SET pilihan = $pilihan WHERE nik = $nik";

    //Import File Koneksi database
    require_once('koneksi.php');

    //Eksekusi Query database
    if(mysqli_query($con,$sql)){
        echo json_encode("Berhasil");
    }else{
        echo json_encode("Gagal");
    }

    mysqli_close($con);
?>