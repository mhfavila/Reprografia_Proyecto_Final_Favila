-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-05-2022 a las 11:07:59
-- Versión del servidor: 8.0.27
-- Versión de PHP: 8.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `proyecto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `correo` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `precioFotocopia` varchar(50) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `telefono` int NOT NULL,
  `path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`correo`, `pass`, `nombre`, `precioFotocopia`, `direccion`, `telefono`, `path`) VALUES
('empresa1@gmail.com', '0', '0', '40 cent/hoja', '1', 1, 'http://83.58.179.222/proyectoPHP/upload/empresa1@gmail.com.jpg'),
('empresa11@gmail.com', '1234', '12340', '1234 cent/hoja', '1234', 1234, ''),
('empresa12@gmail.com', '1234', 'Empresa 12', '2 cent/hoja', 'francisco 34 ,Valladolid', 122344433, ''),
('empresa13@gmail.com', '1234', 'Empresa 13', '10 cent/hoja', 'Madrid 4 ,Valladolid', 222111333, ''),
('empresa14@gmail.com', '1234', 'Empresa 14', '16 cent/hoja', 'Barcelona 89 ,Valladolid', 657483920, ''),
('empresa2@gmail.com', '1234', 'Empresa 2', '10 cent/hoja', 'av Colon 17 ,Valladolid', 888888888, 'http://83.58.179.222/proyectoPHP/upload/empresa2@gmail.com.jpg'),
('empresa20@gmail.com', '1234', 'Empresa 20', '6 cent/hoja', ' luis  44 ,Valladolid', 192837465, 'http://83.58.179.222/proyectoPHP/upload/empresa20@gmail.com.jpg'),
('empresa21@gmail.com', '1234', 'empresa 21', '10  cent/hoja', 'mayor 12', 259398522, 'http://83.58.179.222/proyectoPHP/upload/empresa21@gmail.com.jpg'),
('empresa3@gmail.com', '1234', 'empresa 3', '30  cent/hoja', 'paseo Zorrilla 100 , Valladolid ', 777777777, 'http://mhfavila.000webhostapp.com/proyectoPHP/upload/empresa3@gmail.com.jpg'),
('empresa31@gmail.com', '1234', 'empresa 21 ', '23  cent/hoja', 'mayor 12 ', 258369147, 'http://83.58.179.222/proyectoPHP/upload/empresa31@gmail.com.jpg'),
('empresa4@gmail.com', '1234', 'Empresa 4', '13 cent/hoja', ' reyes 40 ,Valladolid', 888888888, 'http://83.58.179.222/proyectoPHP/upload/empresa4@gmail.com.jpg'),
('empresa6@gmail.com', '1234', 'Empresa 6', '15 cent/hoja', 'av Colon 56 ,Valladolid', 888888888, ''),
('empresa7@gmail.com', '1234', 'Empresa 7', '25 cent/hoja', 'av Mayo 79 ,Valladolid', 123458888, ''),
('empresa8@gmail.com', '1234', 'Empresa 8', '5 cent/hoja', 'catolica 12 ,Valladolid', 111222333, 'http://83.58.179.222/proyectoPHP/upload/empresa8@gmail.com.jpg'),
('empresafavila@gmail.com', '12', '5555', '5 cent/hoja', '6', 12, 'http://83.58.179.222/proyectoPHP/upload/empresafavila@gmail.com.jpg'),
('empresanueva@gmail.com', '1234', 's', '1 cent/hoja', 'qw', 786494, 'http://83.58.179.222/proyectoPHP/upload/empresanueva@gmail.com.jpg'),
('empresaprueba1@gmail.com', 'q234', 'gs', '1cent/hoja', 'qdbd', 69732, 'http://83.58.179.222/proyectoPHP/upload/empresaprueba1@gmail.com.jpg'),
('empresapruebavideo@gmail.com', '1234', 'empresa prueba\n\n', '10 cent/hoja', 'mayor 12 ', 369258147, 'http://83.58.179.222/proyectoPHP/upload/empresapruebavideo@gmail.com.jpg'),
('favila.montero@sanviatorvalladolid.com', '1234', 'dff', '25 cent/hoja', 'fff', 8856, 'http://83.58.179.222/proyectoPHP/upload/favila.montero@sanviatorvalladolid.com.jpg'),
('favilaEmpresa@gmail.com', '1234', 'empresa favila', '12 cent/hoja', 'na', 365, 'http://83.58.179.222/proyectoPHP/upload/favilaEmpresa@gmail.com.jpg'),
('favilapruebavideo@gmail.com', '12345', 'favila 12', '0 cent/hoja', '0', 99999999, 'http://83.58.179.222/proyectoPHP/upload/favilapruebavideo@gmail.com.jpg'),
('v@gmail.com', '1234', 'v', '12 cent/hoja', 'vvg', 2558, 'http://83.58.179.222/proyectoPHP/upload/v@gmail.com.jpg');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`correo`);
COMMIT;