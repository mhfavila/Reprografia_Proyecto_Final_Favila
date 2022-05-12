<?php


include 'db_config.php';
$conexion=new mysqli($hostname,$username,$password,$database);

$correo=$_POST['correo'];
$password=$_POST['pass'];
$nombre=$_POST['nombre'];
$precioFotocopia=$_POST['precioFotocopia'];
$direccion=$_POST['direccion'];
$telefono =$_POST['telefono'];



$sql="update empresa set pass = '".$password."',nombre='".$nombre."',precioFotocopia='".$precioFotocopia."',direccion='".$direccion."',telefono ='".$telefono."' where correo = '".$correo."'" ;
$result=mysqli_query($conexion,$sql);
if ($result) {
	echo  "Datos Insertados";
	
}else{
	echo "Error no se pudo ingresar";
	
}
$result -> close();

?>