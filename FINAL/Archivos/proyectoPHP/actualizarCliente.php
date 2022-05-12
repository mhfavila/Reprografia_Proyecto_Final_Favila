<?php
include 'db_config.php';


$conexion=new mysqli($hostname,$username,$password,$database);

$correo=$_POST['correo'];
$password=$_POST['pass'];
$nombre=$_POST['nombre'];
$apellidos=$_POST['apellidos'];
$telefono =$_POST['telefono'];




$sql="update cliente set pass = '".$password."',nombre='".$nombre."',apellidos='".$apellidos."'where correo = '".$correo."'" ;
$result=mysqli_query($conexion,$sql);
if ($result) {
	echo  "Datos Insertados";
	
}else{
	echo "Error no se pudo ingresar";
	
}
$result -> close();

?>