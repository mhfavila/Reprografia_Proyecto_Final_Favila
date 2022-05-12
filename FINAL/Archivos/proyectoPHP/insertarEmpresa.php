<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
include 'db_config.php';


$conexion=new mysqli($hostname,$username,$password,$database);

$correo=$_POST['correo'];
$password=$_POST['pass'];
$nombre=$_POST['nombre'];
$precioFotocopia=$_POST['precioFotocopia'];
$direccion=$_POST['direccion'];
$telefono =$_POST['telefono'];
$imageData = $_POST['path'];
$ruta = $_POST['ruta'];

$imagePath = "upload/$correo.jpg";
$SERVER_URL = "$ruta$imagePath";



$sql="insert into empresa values('".$correo."','".$password."','".$nombre."','".$precioFotocopia."','".$direccion."','".$telefono."','".$SERVER_URL."')";
$result=mysqli_query($conexion,$sql);
if ($result) {
	file_put_contents($imagePath,base64_decode($imageData)); 
	echo  "Datos Insertados";
	
}else{
	echo "Error no se pudo ingresar";
	
}
 $mysql->close();
}

?>