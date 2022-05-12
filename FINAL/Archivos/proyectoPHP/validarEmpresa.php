<?php
include 'db_config.php';

$conexion=new mysqli($hostname,$username,$password,$database);

$correo=$_POST['correo'];
$pass=$_POST['pass'];
//$correo="favilamontero@gmail.com";
//$pass="favila";

$sentencia=$conexion->prepare("SELECT * FROM empresa WHERE correo=? AND pass=?");
$sentencia->bind_param('ss', $correo,$pass);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()){
	echo json_encode($fila,JSON_UNESCAPED_UNICODE);

}
$sentencia->close();
$conexion->close();

?>