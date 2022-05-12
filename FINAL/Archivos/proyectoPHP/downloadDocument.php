<?php

	include 'db_config.php';

	$con = mysqli_connect($hostname, $username, $password, $database);

	$receivedSn = $_POST["SN"];

	$sqlQuery = "SELECT * FROM `subir` WHERE sn = '$receivedSn'";

	$result = mysqli_query($con, $sqlQuery);

	$pdfDetails = NULL;
	$pdfnombreArchivo =NULL;

	while ($row = mysqli_fetch_array($result)) {
		
		$pdfDetails["pdfSn"] = $row[0];
		$pdfDetails["pdfnombreArchivo"] = $row[1];
		$pdfnombreArchivo["pdfnombreArchivo"] = $row[1];
		$pdfLocation = $row[4];

		$pdfFile = file_get_contents($pdfLocation);

		$pdfDetails["encodedPDF"] = base64_encode($pdfFile);

	}

	mysqli_close($con);

	print(json_encode($pdfDetails));
	print(json_encode($pdfnombreArchivo));



?>