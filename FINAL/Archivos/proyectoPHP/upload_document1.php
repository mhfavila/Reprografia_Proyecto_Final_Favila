<?php

include 'db_config.php';
$con = mysqli_connect($hostname, $username, $password, $database);

$recievedNombre = $_POST['NOMBRE'];
$recievedCliente = $_POST['CLIENTE'];
$recievedEmpresa = $_POST['EMPRESA'];
$encodedPDF = $_POST['PDF'];


  $pdfTitle = $recievedNombre ;
  $pdfLocation = "documents/$pdfTitle.pdf";

  $sqlQuery = "INSERT INTO `subir`(`nombreArchivo`,`cliente`,`empresa`, `location`) VALUES ('$recievedNombre','$recievedCliente','$recievedEmpresa', '$pdfLocation')";


  if(mysqli_query($con, $sqlQuery)){


    file_put_contents($pdfLocation, base64_decode($encodedPDF));

    $result["status"] = TRUE;
    $result["remarks"] = "document uploaded successfully";

  }else{

    $result["status"] = FALSE;
    $result["remarks"] = "document uploading Failed";

  }

  mysqli_close($con);

  print(json_encode($result));


?>