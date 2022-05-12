<?php
include 'db_config.php';

$conexion=new mysqli($hostname,$username,$password,$database);

$correo=$_POST['correo'];

$sentencia=$conexion->prepare("SELECT * FROM cliente WHERE correo=?");
$sentencia->bind_param('s',$correo);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);

}
$sentencia->close();
$conexion->close();

?>