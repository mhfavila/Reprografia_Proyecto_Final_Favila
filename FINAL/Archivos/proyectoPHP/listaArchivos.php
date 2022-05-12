<?php

include 'db_config.php';


$nombreEmpresa=$_GET['empresa'];

$json=array();

		$conexion=new mysqli($hostname,$username,$password,$database);

		$consulta="select * from subir where empresa = '$nombreEmpresa' ";
		$resultado= mysqli_query($conexion,$consulta);
		while ($registro = mysqli_fetch_array($resultado)) {
			$json['subir'][]=$registro;

		}

		mysqli_close($conexion);
		echo json_encode($json);




?>