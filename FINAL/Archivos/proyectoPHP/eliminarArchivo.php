<?php
include 'db_config.php';


$conexion=new mysqli($hostname,$username,$password,$database);

$sn=$_POST['SN'];




$sql="delete from subir where sn = '".$sn."'" ;
$result=mysqli_query($conexion,$sql);
if ($result) {
	echo  "archivo eliminado";
	
}else{
	echo "fallo";
	
}
$result -> close();

?>