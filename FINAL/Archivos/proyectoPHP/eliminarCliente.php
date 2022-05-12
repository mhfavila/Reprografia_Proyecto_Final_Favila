<?php
include 'db_config.php';



$conexion=new mysqli($hostname,$username,$password,$database);

$correo=$_POST['correo'];
$sql="delete from cliente where codigo = '".$correo"'";




$sql="delete ";
$result=mysqli_query($conexion,$sql);
if ($result) {
	echo  "Datos Insertados";
	
}else{
	echo "Error no se pudo ingresar";
	
}

?>