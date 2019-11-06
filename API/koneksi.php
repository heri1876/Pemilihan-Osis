<?php
 //Mendefinisikan Konstanta/Variable
 define('HOST','localhost');
 define('USER','id10564893_latihan');
 define('PASS','latihan');
 define('DB','id10564893_db_android');
 
 //membuat koneksi dengan database
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
 ?>
