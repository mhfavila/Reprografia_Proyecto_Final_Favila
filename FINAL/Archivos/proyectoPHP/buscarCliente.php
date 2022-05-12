<?php
include 'db_config.php';

$conexion=new mysqli($hostname,$username,$password,$database);

$correo=$_GET['correo'];

$consulta = "SELECT * FROM cliente where correo = '$correo'";
$resultado = $conexion -> query($consulta);

while($fila=$resultado -> fetch_array()){
	$clientes[] = array_map('utf8_encode',$fila);
}

echo json_encode($clientes);
$resultado -> close();

?>