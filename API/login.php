<?php

    session_start();
    
    //Mendapatkan Nilai Dari Variable yang ingin ditampilkan
    $nik = $_GET['nik'];
    $password = $_GET['password'];

	//Import file koneksi
    require_once('koneksi.php');
	
	//Membuat SQL Query dengan user yang ditentukan secara spesifik
    $sql = "SELECT * FROM user WHERE nik='$nik' AND password='$password'";
    
    //Mendapatkan Hasil
    $r = mysqli_query($con,$sql);

    //Mengecek hasil
    if ($result = mysqli_query($con,$sql)){
        $rowcount = mysqli_num_rows($result);
        if($rowcount > 0){
            //Memasukan hasil ke dalam Array
            $row = mysqli_fetch_array($r);
            $hasil [] = array(
                "nama"=>$row['nama'],
                "nik"=>$row['nik'],
                "pilihan"=>$row['pilihan']
                );

            //Menampilkan hasil ke json
            echo json_encode(array('result'=>$hasil));
        }else{
            echo "Login Gagal";
        }
    }else{
        echo "Login Gagal";
    }
    
    mysqli_close($con);  
?>