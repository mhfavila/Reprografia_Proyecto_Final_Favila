<?php

include 'db_config.php';


$json=array();

		$conexion=new mysqli($hostname,$username,$password,$database);

		$consulta="select * from empresa";
		$resultado= mysqli_query($conexion,$consulta);
		while ($registro = mysqli_fetch_array($resultado)) {
			$json['empresa'][]=$registro;

		}

		mysqli_close($conexion);
		echo json_encode($json);




?>