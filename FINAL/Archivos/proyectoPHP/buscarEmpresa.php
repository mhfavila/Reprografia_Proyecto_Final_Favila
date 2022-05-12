<?php
include 'db_config.php';

$conexion=new mysqli($hostname,$username,$password,$database);

$correo=$_GET['correo'];

$consulta = "SELECT * FROM empresa where correo = '$correo'";
$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()){
	$empresas[] = array_map('utf8_encode',$fila);
}

echo json_encode($empresas);
$resultado -> close();

?>