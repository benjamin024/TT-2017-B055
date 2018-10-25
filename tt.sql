-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 25-10-2018 a las 00:23:32
-- Versión del servidor: 5.7.23-0ubuntu0.18.04.1
-- Versión de PHP: 7.2.10-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tt`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aviso`
--

CREATE TABLE `aviso` (
  `id_aviso` int(6) NOT NULL,
  `aviso` text,
  `fecha_publicacion` date DEFAULT NULL,
  `fecha_termino` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `aviso`
--

INSERT INTO `aviso` (`id_aviso`, `aviso`, `fecha_publicacion`, `fecha_termino`) VALUES
(2, 'Este aviso vencerá pronto :D', '2018-10-06', '2018-10-08'),
(3, 'El aviso se creo desde ESCOM', '2018-10-22', '2018-10-31');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentario`
--

CREATE TABLE `comentario` (
  `id_comentario` int(6) NOT NULL,
  `correo_usuario` varchar(320) DEFAULT NULL,
  `comentario` text,
  `fecha` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `comentario`
--

INSERT INTO `comentario` (`id_comentario`, `correo_usuario`, `comentario`, `fecha`) VALUES
(1, 'prueba@gmail.com', 'Pesimo servicio', '2018-10-22 12:00:00'),
(2, 'prueba@gmail.com', 'Ruta lenta', '2018-10-20 10:00:00'),
(3, 'prueba@gmail.com', 'Unidad sucia', '2018-10-09 10:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conductor`
--

CREATE TABLE `conductor` (
  `rfc_conductor` varchar(13) DEFAULT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `sueldo` float(6,2) DEFAULT NULL,
  `no_licencia` varchar(16) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `conductor`
--

INSERT INTO `conductor` (`rfc_conductor`, `nombre`, `sueldo`, `no_licencia`, `telefono`) VALUES
('DOGB961024I24', 'Benjamín Dorantes García', 6500.00, '1478965', '5537366575');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estacion`
--

CREATE TABLE `estacion` (
  `id_estacion` int(4) NOT NULL,
  `nombre` varchar(150) DEFAULT NULL,
  `latitud` double(12,8) DEFAULT NULL,
  `longitud` double(12,8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `estacion`
--

INSERT INTO `estacion` (`id_estacion`, `nombre`, `latitud`, `longitud`) VALUES
(1, 'Politecnico', 19.50096400, -99.14918100),
(2, 'Instituto del Petróleo', 19.48969600, -99.14469400),
(3, 'Autobuses del Norte', 19.47921400, -99.14062100),
(4, 'La Raza', 19.46988900, -99.13652200),
(5, 'Misterios', 19.46358300, -99.13045200),
(6, 'Valle Gómez', 19.45891600, -99.11991400),
(7, 'Consulado', 19.45537600, -99.11294900),
(8, 'Eduardo Molina', 19.45116500, -99.10544600),
(9, 'Aragón', 19.45151400, -99.09626000),
(10, 'Oceanía', 19.44490900, -99.08661400),
(11, 'Terminal Aérea', 19.43397200, -99.08769900),
(12, 'Hangares', 19.42463500, -99.08828600),
(13, 'Pantitlán', 19.41650100, -99.07427700),
(14, 'Martín Carrera', 19.48309800, -99.10716900),
(15, 'La Villa-Basílica', 19.48152000, -99.11764000),
(16, 'Deportivo 18 de Marzo', 19.48402900, -99.12555800),
(17, 'Lindavista', 19.48814500, -99.13464500),
(18, 'Instituto del Petróleo', 19.49116900, -99.14857100),
(19, 'Vallejo', 19.49010700, -99.15603800),
(20, 'Norte 45', 19.48846900, -99.16246500),
(21, 'Ferrería/Arena Ciudad de México', 19.49065300, -99.17412700),
(22, 'Azcapotzalco', 19.49106800, -99.18626200),
(23, 'Tezozomoc', 19.49458800, -99.19590700),
(24, 'El Rosario', 19.50496400, -99.19967300),
(25, 'El Rosario', 19.50510600, -99.20040200),
(26, 'Aquiles Serdán', 19.49085300, -99.19531800),
(27, 'Camarones', 19.47911000, -99.18971800),
(28, 'Refinería', 19.46962300, -99.19001800),
(29, 'Tacuba', 19.45859900, -99.18850600),
(30, 'San Joaquín', 19.44525500, -99.19175600),
(31, 'Polanco', 19.43387400, -99.19090900),
(32, 'Auditorio', 19.42521300, -99.19191700),
(33, 'Constituyentes', 19.41213200, -99.19140700),
(34, 'Tacubaya', 19.40239800, -99.18619200),
(35, 'Sn. Pedro de los Pinos', 19.39175800, -99.18583000),
(36, 'San Antonio', 19.38517000, -99.18684900),
(37, 'Mixcoac', 19.37654700, -99.18758900),
(38, 'Barranca del Muerto', 19.36164800, -99.18923100),
(39, 'Tacubaya', 19.40195900, -99.18732100),
(40, 'Patriotismo', 19.40597600, -99.17897400),
(41, 'Chilpancingo', 19.40623900, -99.16853500),
(42, 'Centro Médico', 19.40663400, -99.15498400),
(43, 'Lázaro Cárdenas', 19.40721100, -99.14435200),
(44, 'Chabacano', 19.40884900, -99.13360100),
(45, 'Jamaica', 19.40925300, -99.12223900),
(46, 'Mixiuhca', 19.40860600, -99.11321600),
(47, 'Velódromo', 19.40881800, -99.10315200),
(48, 'Ciudad Deportiva', 19.40873700, -99.09128600),
(49, 'Puebla', 19.40754300, -99.08239200),
(50, 'Pantitlán', 19.41555700, -99.07220000),
(51, 'Bulevar Guadalupano, 1150-1152', 21.90669200, -102.25846075),
(52, 'Bulevar Guadalupano, 938', 21.90557636, -102.26143181),
(53, 'Bulevar Guadalupano, 302', 21.90351526, -102.26780702),
(54, 'Av. Aguascalientes Oriente SN', 21.90186437, -102.27034313),
(55, 'Nazario Ortiz Garza, 701', 21.89971419, -102.27148039),
(56, 'Nazario Ortiz Garza, 102', 21.89648461, -102.27782118),
(57, 'Av. de la Convención de 1914 Oriente, 602', 21.90100673, -102.28244731),
(58, 'Av. de la Convención de 1914 Norte, 109', 21.90077432, -102.29202094),
(59, 'Petróleos Mexicanos, 326', 21.89181254, -102.29450785),
(60, 'México 305', 21.89042285, -102.29397125),
(61, 'Gral. José María Arteaga, 412', 21.88939994, -102.29378581),
(62, 'Jardín de Zaragoza, 604', 21.88672802, -102.29629818),
(63, 'Dr. Jesús Díaz de León, 111', 21.87977747, -102.29402950),
(64, 'Av. Paseo de la Cruz, Lb', 21.87228304, -102.29207222),
(65, 'José Ma. Chavez 30', 21.87035146, -102.29500568),
(66, 'José Ma. Chavez 1121f', 21.86691427, -102.29444403),
(67, 'Av. de la Convención de 1914 Sur 101a', 21.86560181, -102.29548559),
(69, 'De los maestros 1908', 21.86565953, -102.31013838),
(70, 'De los maestros 704 ', 21.85564109, -102.31617627),
(71, 'Av. Convención 1002', 21.85212609, -102.31197057),
(72, 'Av. Ojocaliente 301', 21.88632098, -102.25582543),
(73, 'Av. Ojocaliente 117', 21.89084476, -102.25749057),
(74, 'Miguel Ángel Barberena Vega 718', 21.89716620, -102.25914281),
(75, 'Av. Aguascalientes Oriente 1949', 21.89489760, -102.26779304),
(76, 'Av. de la Convención de 1914 Norte, 1401', 21.89791849, -102.30197295),
(77, 'Av. de la Convención de 1914 Norte, 2128a', 21.89628487, -102.30798717),
(78, 'Av. de la Convención de 1914 Poniente, 1404', 21.89232278, -102.31247182),
(79, 'Av. de la Convención de 1914 Poniente, 1215', 21.88637297, -102.31249448),
(80, 'Av. de la Convención de 1914 Poniente, 420', 21.87796565, -102.31224176),
(81, 'Av. de la Convención de 1914 Poniente, 132', 21.87021182, -102.31070545),
(82, 'Av. de la Convención de 1914 Sur 101a', 21.86560181, -102.29548559),
(83, 'Av. de la Convención de 1914 Sur 1005', 21.86741195, -102.30619017),
(84, 'Encarnación de Díaz - Aguascalientes, 1108', 21.86529280, -102.29296456),
(85, 'Av. de la Convención de 1914 Sur, 1503', 21.86620884, -102.28352319),
(86, 'Av. de la Convención de 1914 Oriente, 837', 21.87086096, -102.27118834),
(87, 'Av. de la Convención de 1914 Oriente, 202a', 21.88019663, -102.27181422),
(88, 'Av. de la Convención de 1914 Oriente SN', 21.89048365, -102.27404071),
(89, 'Lic. Miguel de la Madrid 56', 21.95209970, -102.33507731),
(90, 'Primavera 108', 21.94591270, -102.33011965),
(91, 'Lic. Miguel de la Madrid 998', 21.93974270, -102.32591394),
(92, 'Lic. Miguel de la Madrid 1904', 21.93147981, -102.32121565),
(93, 'Lic. Miguel de la Madrid 1926', 21.92527973, -102.31801143),
(94, 'Av. Universidad 1704', 21.92211469, -102.31634846),
(95, 'Av. Universidad 900', 21.91451066, -102.31303650),
(96, 'Av. Universidad 612', 21.90829948, -102.31024700),
(97, 'Av. Universidad 110', 21.89783991, -102.30502072),
(98, 'José Ma. Chavez, 1531', 21.86464628, -102.29408897),
(99, 'José Ma. Chavez, 1605a', 21.85735752, -102.29282297),
(100, 'José Ma. Chavez, Lb', 21.85073841, -102.29171177),
(101, 'José Ma. Chavez, 3005', 21.84351699, -102.29046722),
(102, 'Av. Siglo XXI 149', 21.86814030, -102.24427655),
(103, 'Av. Tecnológico 511', 21.86812038, -102.24575713),
(104, 'Av. Tecnológico 203', 21.86989269, -102.24882557),
(105, 'Av. Tecnológico 204', 21.87210306, -102.25245192),
(106, 'Av. Tecnológico SN', 21.87536324, -102.25717830),
(107, 'Lic. Adolfo López Mateos Oriente 1714', 21.87854923, -102.26263208),
(108, 'Lic. Adolfo López Mateos Oriente 1610', 21.87776270, -102.26665539),
(109, 'Lic. Adolfo López Mateos Oriente 1317', 21.87811116, -102.27325362),
(110, 'Lic. Adolfo López Mateos Oriente 610', 21.87910677, -102.28665394),
(111, 'Lic. Adolfo López Mateos Oriente 303', 21.87756897, -102.29911192),
(112, 'Lic. Adolfo López Mateos Poniente 713', 21.87450243, -102.31046303),
(113, 'Calvillo - Aguascalientes 1508', 21.87247250, -102.31615262),
(114, 'Calvillo - Aguascalientes 1383', 21.87119806, -102.32160287),
(115, 'Calvillo - Aguascalientes 112a', 21.87339316, -102.33294799),
(116, 'Calvillo - Aguascalientes 9', 21.87611780, -102.34356461),
(117, 'Calvillo - Aguascalientes 2012', 21.87972192, -102.35655723),
(118, 'Calvillo - Aguascalientes 5343', 21.88230247, -102.36655073);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `forma_cobro`
--

CREATE TABLE `forma_cobro` (
  `id_forma_cobro` int(2) NOT NULL,
  `descripcion` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `forma_cobro`
--

INSERT INTO `forma_cobro` (`id_forma_cobro`, `descripcion`) VALUES
(1, 'tarjeta'),
(2, 'efectivo'),
(3, 'boleto');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mantenimiento`
--

CREATE TABLE `mantenimiento` (
  `id_mantenimiento` int(6) NOT NULL,
  `id_unidad` varchar(10) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `responsable` varchar(60) DEFAULT NULL,
  `comentarios` text,
  `costo` float(7,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `mantenimiento`
--

INSERT INTO `mantenimiento` (`id_mantenimiento`, `id_unidad`, `fecha`, `responsable`, `comentarios`, `costo`) VALUES
(1, 'ABC123', '2018-10-08', 'Benjamín Dorantes García', 'Se rompió un fierro muy grande', 12000.00),
(3, 'ABC123', '2018-10-21', 'null', 'null', 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro`
--

CREATE TABLE `registro` (
  `id_registro` int(6) NOT NULL,
  `id_viaje_unidad` int(6) DEFAULT NULL,
  `id_estacion` int(4) DEFAULT NULL,
  `no_pasajeros` int(3) DEFAULT NULL,
  `no_pasajeros_especial` int(3) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `registro`
--

INSERT INTO `registro` (`id_registro`, `id_viaje_unidad`, `id_estacion`, `no_pasajeros`, `no_pasajeros_especial`, `fecha_hora`) VALUES
(1, 1, 72, 67, 48, '2018-10-26 02:10:00'),
(2, 1, 73, 17, 49, '2018-10-26 02:15:00'),
(3, 1, 74, 56, 88, '2018-10-26 02:20:00'),
(4, 1, 75, 19, 104, '2018-10-26 02:25:00'),
(5, 1, 55, 47, 132, '2018-10-26 02:27:00'),
(6, 1, 56, 59, 138, '2018-10-26 02:31:00'),
(7, 1, 57, 43, 154, '2018-10-26 02:33:00'),
(8, 1, 58, 63, 156, '2018-10-26 02:37:00'),
(9, 1, 76, 12, 157, '2018-10-26 02:39:00'),
(10, 1, 77, 65, 205, '2018-10-26 02:42:00'),
(11, 1, 78, 55, 209, '2018-10-26 02:45:00'),
(12, 1, 79, 11, 211, '2018-10-26 02:51:00'),
(13, 1, 80, 53, 211, '2018-10-26 02:57:00'),
(14, 1, 81, 25, 211, '2018-10-26 03:01:00'),
(15, 1, 82, 25, 211, '2018-10-26 03:04:00'),
(16, 1, 83, 38, 223, '2018-10-26 03:06:00'),
(17, 1, 84, 67, 239, '2018-10-26 03:08:00'),
(18, 1, 85, 23, 239, '2018-10-26 03:12:00'),
(19, 1, 86, 19, 252, '2018-10-26 03:18:00'),
(20, 1, 87, 67, 304, '2018-10-26 03:20:00'),
(21, 1, 88, 73, 304, '2018-10-26 03:26:00'),
(22, 1, 56, 25, 306, '2018-10-26 03:31:00'),
(23, 1, 55, 76, 333, '2018-10-26 03:36:00'),
(24, 1, 75, 59, 333, '2018-10-26 03:39:00'),
(25, 1, 74, 31, 336, '2018-10-26 03:41:00'),
(26, 1, 73, 17, 337, '2018-10-26 03:45:00'),
(27, 1, 72, 39, 369, '2018-10-26 03:48:00'),
(28, 2, 72, 18, 10, '2018-10-26 03:48:00'),
(29, 2, 72, 23, 13, '2018-10-26 03:54:00'),
(30, 2, 73, 75, 40, '2018-10-26 03:57:00'),
(31, 2, 74, 53, 40, '2018-10-26 04:02:00'),
(32, 2, 75, 46, 43, '2018-10-26 04:08:00'),
(33, 2, 55, 12, 50, '2018-10-26 04:13:00'),
(34, 2, 56, 36, 56, '2018-10-26 04:18:00'),
(35, 2, 88, 37, 56, '2018-10-26 04:24:00'),
(36, 2, 87, 50, 61, '2018-10-26 04:29:00'),
(37, 2, 86, 75, 69, '2018-10-26 04:32:00'),
(38, 2, 85, 35, 70, '2018-10-26 04:36:00'),
(39, 2, 84, 29, 76, '2018-10-26 04:39:00'),
(40, 2, 83, 13, 81, '2018-10-26 04:44:00'),
(41, 2, 82, 45, 85, '2018-10-26 04:49:00'),
(42, 2, 81, 54, 86, '2018-10-26 04:55:00'),
(43, 2, 80, 60, 86, '2018-10-26 04:58:00'),
(44, 2, 79, 33, 93, '2018-10-26 05:01:00'),
(45, 2, 78, 41, 103, '2018-10-26 05:03:00'),
(46, 2, 77, 19, 107, '2018-10-26 05:05:00'),
(47, 2, 76, 57, 149, '2018-10-26 05:07:00'),
(48, 2, 58, 19, 149, '2018-10-26 05:13:00'),
(49, 2, 57, 66, 192, '2018-10-26 05:17:00'),
(50, 2, 56, 79, 198, '2018-10-26 05:20:00'),
(51, 2, 55, 1, 198, '2018-10-26 05:25:00'),
(52, 2, 75, 17, 204, '2018-10-26 05:29:00'),
(53, 2, 74, 65, 222, '2018-10-26 05:33:00'),
(54, 2, 73, 59, 222, '2018-10-26 05:36:00'),
(55, 2, 72, 43, 222, '2018-10-26 05:41:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta`
--

CREATE TABLE `ruta` (
  `id_ruta` int(11) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `combustible` float(6,3) DEFAULT NULL,
  `id_forma_cobro` varchar(30) DEFAULT NULL,
  `color` varchar(7) DEFAULT NULL,
  `frecuencia_ida` int(2) DEFAULT NULL,
  `frecuencia_vuelta` int(2) NOT NULL,
  `tiempo_recorrido` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ruta`
--

INSERT INTO `ruta` (`id_ruta`, `nombre`, `combustible`, `id_forma_cobro`, `color`, `frecuencia_ida`, `frecuencia_vuelta`, `tiempo_recorrido`) VALUES
(1, 'linea 5', 30.200, '1', '#fddf00', 10, 8, NULL),
(2, 'linea 6', 23.510, '1', '#0064a8', 10, 10, NULL),
(3, 'linea 7', 49.030, '1', '#ff6309', 10, 11, NULL),
(4, 'linea 9', 37.312, '1', '#5b2c2a', 10, 9, NULL),
(5, 'Ruta 9', 32.120, '2', '#352e81', 10, 12, 42),
(6, 'Ruta 20', 36.847, '2', '#ad960c', 10, 9, 58),
(7, 'Ruta 33', 28.147, '2', '#931616', 10, 10, 39),
(8, 'Ruta 41', 22.887, '2', '#1d8968', 10, 7, 50);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta_estacion`
--

CREATE TABLE `ruta_estacion` (
  `id_ruta` int(4) DEFAULT NULL,
  `id_estacion` int(4) DEFAULT NULL,
  `sig_estacion` int(2) DEFAULT NULL,
  `transbordo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ruta_estacion`
--

INSERT INTO `ruta_estacion` (`id_ruta`, `id_estacion`, `sig_estacion`, `transbordo`) VALUES
(1, 1, 2, ''),
(1, 2, 3, '18'),
(1, 3, 4, ''),
(1, 4, 5, ''),
(1, 5, 6, ''),
(1, 6, 7, ''),
(1, 7, 8, ''),
(1, 8, 9, ''),
(1, 9, 10, ''),
(1, 10, 11, ''),
(1, 11, 12, ''),
(1, 12, 13, ''),
(2, 14, 15, ''),
(2, 15, 16, ''),
(2, 16, 17, ''),
(2, 17, 18, ''),
(2, 18, 19, '2'),
(2, 19, 20, ''),
(2, 20, 21, ''),
(2, 21, 22, ''),
(2, 22, 23, ''),
(2, 23, 24, ''),
(3, 25, 26, '24'),
(3, 26, 27, ''),
(3, 27, 28, ''),
(3, 28, 29, ''),
(3, 29, 30, ''),
(3, 30, 31, ''),
(3, 31, 32, ''),
(3, 32, 33, ''),
(3, 33, 34, ''),
(3, 34, 35, '39'),
(3, 35, 36, ''),
(3, 36, 37, ''),
(3, 37, 38, ''),
(4, 39, 40, '34'),
(4, 40, 41, ''),
(4, 41, 42, ''),
(4, 42, 43, ''),
(4, 43, 44, ''),
(4, 44, 45, ''),
(4, 45, 46, ''),
(4, 46, 47, ''),
(4, 47, 48, ''),
(4, 48, 49, ''),
(4, 49, 50, ''),
(1, 13, 0, ''),
(2, 24, 0, '25'),
(3, 38, 0, ''),
(4, 50, 0, ''),
(5, 51, 52, ''),
(5, 52, 53, ''),
(5, 53, 54, ''),
(5, 54, 55, ''),
(5, 55, 56, ''),
(5, 56, 57, ''),
(5, 57, 58, ''),
(5, 58, 59, ''),
(5, 59, 60, ''),
(5, 60, 61, ''),
(5, 61, 62, ''),
(5, 62, 63, ''),
(5, 63, 64, ''),
(5, 64, 65, ''),
(5, 65, 66, ''),
(5, 66, 82, ''),
(5, 82, 83, ''),
(5, 83, 69, ''),
(5, 69, 70, ''),
(5, 70, 71, ''),
(6, 72, 73, ''),
(6, 73, 74, ''),
(6, 74, 75, ''),
(6, 75, 55, ''),
(6, 55, 56, ''),
(6, 56, 57, ''),
(6, 57, 58, ''),
(6, 58, 76, ''),
(6, 76, 77, ''),
(6, 77, 78, ''),
(6, 78, 79, ''),
(6, 79, 80, ''),
(6, 80, 81, ''),
(6, 81, 82, ''),
(6, 82, 83, ''),
(6, 83, 84, ''),
(6, 84, 85, ''),
(6, 85, 86, ''),
(6, 86, 87, ''),
(6, 87, 88, ''),
(6, 88, 56, ''),
(6, 56, 55, ''),
(6, 55, 75, ''),
(6, 75, 74, ''),
(6, 74, 73, ''),
(6, 73, 72, ''),
(7, 89, 90, ''),
(7, 90, 91, ''),
(7, 91, 92, ''),
(7, 92, 93, ''),
(7, 93, 94, ''),
(7, 94, 95, ''),
(7, 95, 96, ''),
(7, 96, 97, ''),
(7, 97, 77, ''),
(7, 77, 78, ''),
(7, 78, 79, ''),
(7, 79, 80, ''),
(7, 80, 81, ''),
(7, 81, 82, ''),
(7, 82, 83, ''),
(7, 83, 98, ''),
(7, 98, 99, ''),
(7, 99, 100, ''),
(7, 100, 101, ''),
(8, 102, 103, ''),
(8, 103, 104, ''),
(8, 104, 105, ''),
(8, 105, 106, ''),
(8, 106, 107, ''),
(8, 107, 108, ''),
(8, 108, 109, ''),
(8, 109, 110, ''),
(8, 110, 111, ''),
(8, 111, 112, ''),
(8, 112, 113, ''),
(8, 113, 114, ''),
(8, 114, 115, ''),
(8, 115, 116, ''),
(8, 116, 117, ''),
(8, 117, 118, ''),
(5, 71, 0, NULL),
(6, 72, 0, NULL),
(7, 101, 0, NULL),
(8, 118, 0, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta_horario`
--

CREATE TABLE `ruta_horario` (
  `id_ruta` int(4) DEFAULT NULL,
  `dia` int(1) DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_termino` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ruta_horario`
--

INSERT INTO `ruta_horario` (`id_ruta`, `dia`, `hora_inicio`, `hora_termino`) VALUES
(5, 0, '06:00:00', '23:00:00'),
(5, 1, '06:00:00', '23:00:00'),
(5, 2, '07:00:00', '23:59:00'),
(5, 3, '05:45:00', '21:30:00'),
(5, 4, '07:00:00', '23:59:00'),
(5, 5, '06:00:00', '23:00:00'),
(5, 6, '06:00:00', '23:00:00'),
(6, 0, '06:00:00', '21:00:00'),
(6, 1, '06:00:00', '21:00:00'),
(6, 2, '05:00:00', '23:59:00'),
(6, 3, '05:45:00', '21:30:00'),
(6, 4, '05:00:00', '23:59:00'),
(6, 5, '06:00:00', '23:00:00'),
(6, 6, '06:00:00', '23:00:00'),
(7, 0, '06:00:00', '21:00:00'),
(7, 1, '06:00:00', '21:00:00'),
(7, 2, '08:00:00', '23:59:00'),
(7, 3, '08:45:00', '23:30:00'),
(7, 4, '08:00:00', '23:59:00'),
(7, 5, '06:00:00', '21:30:00'),
(7, 6, '06:00:00', '23:00:00'),
(8, 0, '07:00:00', '21:00:00'),
(8, 1, '07:00:00', '21:00:00'),
(8, 2, '07:00:00', '21:00:00'),
(8, 3, '07:00:00', '21:00:00'),
(8, 4, '07:00:00', '21:00:00'),
(8, 5, '07:00:00', '21:00:00'),
(8, 6, '07:00:00', '21:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta_tarifa`
--

CREATE TABLE `ruta_tarifa` (
  `id_ruta` int(4) DEFAULT NULL,
  `id_tarifa` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ruta_tarifa`
--

INSERT INTO `ruta_tarifa` (`id_ruta`, `id_tarifa`) VALUES
(5, 2),
(5, 3),
(6, 2),
(7, 2),
(7, 3),
(8, 1),
(8, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarifa`
--

CREATE TABLE `tarifa` (
  `id_tarifa` int(3) NOT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `costo` float(5,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tarifa`
--

INSERT INTO `tarifa` (`id_tarifa`, `descripcion`, `costo`) VALUES
(1, 'Tarifa estándar', 7.50),
(2, 'Tarifa preferencial', 2.50),
(3, 'Tarifa excenta', 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidad`
--

CREATE TABLE `unidad` (
  `id_unidad` varchar(10) DEFAULT NULL,
  `capacidad` int(3) DEFAULT NULL,
  `id_ruta` int(4) DEFAULT NULL,
  `inicio_operaciones` date DEFAULT NULL,
  `responsable` varchar(60) DEFAULT NULL,
  `fin_operaciones` date DEFAULT NULL,
  `latitud` double(12,8) DEFAULT NULL,
  `longitud` double(12,8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `unidad`
--

INSERT INTO `unidad` (`id_unidad`, `capacidad`, `id_ruta`, `inicio_operaciones`, `responsable`, `fin_operaciones`, `latitud`, `longitud`) VALUES
('ABC123', 80, 9, '2018-10-05', 'Benjamín Dorantes', '1000-01-01', 0.00000000, 0.00000000),
('ABC123', 70, 6, '2018-10-20', 'Benjamín Dorantes García', '1000-01-01', 0.00000000, 0.00000000),
('ADE2919', 75, 5, '2018-10-08', 'Pedro Jauregui Arellano ', '1000-01-01', 0.00000000, 0.00000000),
('FRT3918', 81, 5, '2018-10-13', 'Luis Baeza Robledo', '1000-01-01', 0.00000000, 0.00000000),
('KAJ9817', 68, 5, '2018-10-13', 'Sebastián Alaba Guerrero', '1000-01-01', 0.00000000, 0.00000000),
('AER1028', 76, 5, '2018-10-29', 'Alberto Del Valle Muela', '1000-01-01', 0.00000000, 0.00000000),
('POD1928', 85, 5, '2018-10-05', 'Pedro Carrasco Cuevas', '1000-01-01', 0.00000000, 0.00000000),
('EJF1938', 80, 6, '2018-10-20', 'Enrique Robles Prieto', '1000-01-01', 0.00000000, 0.00000000),
('FJA1627', 75, 6, '2018-10-25', 'Agustín Rivera Ávila', '1000-01-01', 0.00000000, 0.00000000),
('TAY4749', 74, 6, '2018-10-24', 'Diego Mendez Torres', '1000-01-01', 0.00000000, 0.00000000),
('GEA5728', 78, 6, '2018-10-15', 'Alonso Calvo Márquez', '1000-01-01', 0.00000000, 0.00000000),
('HEF9472', 70, 7, '2018-10-13', 'José Manuel Ortiz Caballero', '1000-01-01', 0.00000000, 0.00000000),
('RAO8492', 75, 7, '2018-10-10', 'Aaron Vidal Muñoz', '1000-01-01', 0.00000000, 0.00000000),
('FOA7520', 81, 7, '2018-10-18', 'Arturo Torres Ortega', '1000-01-01', 0.00000000, 0.00000000),
('BOQ6190', 76, 7, '2018-10-09', 'Jesús Santos Moya', '1000-01-01', 0.00000000, 0.00000000),
('ISP9947', 78, 7, '2018-10-06', 'Adrián Mora Duran', '1000-01-01', 0.00000000, 0.00000000),
('GAQ7599', 78, 8, '2018-10-11', 'Darío Cabrera Vidal', '1000-01-01', 0.00000000, 0.00000000),
('JEQ1609', 70, 8, '2018-10-27', 'Joaquín Jimenez Cabrera', '1000-01-01', 0.00000000, 0.00000000),
('UYE1879', 79, 8, '2018-10-22', 'Juan José Reyes Gomez', '1000-01-01', 0.00000000, 0.00000000),
('BAY1875', 79, 8, '2018-10-16', 'Gabriel Torres Fuentes', '1000-01-01', 0.00000000, 0.00000000),
('HELP1511', 73, 8, '2018-11-15', 'Sergio Vidal Jimenez', '1000-01-01', 0.00000000, 0.00000000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `correo_usuario` varchar(320) DEFAULT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `tipo` int(2) DEFAULT NULL,
  `no_tarjeta` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`correo_usuario`, `nombre`, `password`, `tipo`, `no_tarjeta`) VALUES
('prueba@gmail.com', 'Pruebas', 'prueba', 1, '3849'),
('pr@gmail.com', 'Prueba 2', 'ok', 1, '836');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `viaje_unidad`
--

CREATE TABLE `viaje_unidad` (
  `id_viaje_unidad` int(6) NOT NULL,
  `id_unidad` varchar(10) DEFAULT NULL,
  `rfc_conductor` varchar(13) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora_salida` time DEFAULT NULL,
  `hora_termino` time DEFAULT NULL,
  `total_pasajeros` int(3) DEFAULT NULL,
  `direccion` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `viaje_unidad`
--

INSERT INTO `viaje_unidad` (`id_viaje_unidad`, `id_unidad`, `rfc_conductor`, `fecha`, `hora_salida`, `hora_termino`, `total_pasajeros`, `direccion`) VALUES
(1, 'EJF1938', 'DOGB961024I24', '2018-10-26', '02:10:00', '03:48:00', 658, 1),
(2, 'EJF1938', 'DOGB961024I24', '2018-10-26', '03:48:00', '05:41:00', 568, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `viaje_usuario`
--

CREATE TABLE `viaje_usuario` (
  `id_viaje_usuario` int(6) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `correo_usuario` varchar(320) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `viaje_usuario_estacion`
--

CREATE TABLE `viaje_usuario_estacion` (
  `id_viaje_usuario` int(6) DEFAULT NULL,
  `id_estacion` int(4) DEFAULT NULL,
  `no_estacion` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aviso`
--
ALTER TABLE `aviso`
  ADD PRIMARY KEY (`id_aviso`);

--
-- Indices de la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD PRIMARY KEY (`id_comentario`);

--
-- Indices de la tabla `estacion`
--
ALTER TABLE `estacion`
  ADD PRIMARY KEY (`id_estacion`);

--
-- Indices de la tabla `forma_cobro`
--
ALTER TABLE `forma_cobro`
  ADD PRIMARY KEY (`id_forma_cobro`);

--
-- Indices de la tabla `mantenimiento`
--
ALTER TABLE `mantenimiento`
  ADD PRIMARY KEY (`id_mantenimiento`);

--
-- Indices de la tabla `registro`
--
ALTER TABLE `registro`
  ADD PRIMARY KEY (`id_registro`);

--
-- Indices de la tabla `ruta`
--
ALTER TABLE `ruta`
  ADD PRIMARY KEY (`id_ruta`);

--
-- Indices de la tabla `tarifa`
--
ALTER TABLE `tarifa`
  ADD PRIMARY KEY (`id_tarifa`);

--
-- Indices de la tabla `viaje_unidad`
--
ALTER TABLE `viaje_unidad`
  ADD PRIMARY KEY (`id_viaje_unidad`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aviso`
--
ALTER TABLE `aviso`
  MODIFY `id_aviso` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `comentario`
--
ALTER TABLE `comentario`
  MODIFY `id_comentario` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `estacion`
--
ALTER TABLE `estacion`
  MODIFY `id_estacion` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;
--
-- AUTO_INCREMENT de la tabla `forma_cobro`
--
ALTER TABLE `forma_cobro`
  MODIFY `id_forma_cobro` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `mantenimiento`
--
ALTER TABLE `mantenimiento`
  MODIFY `id_mantenimiento` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `registro`
--
ALTER TABLE `registro`
  MODIFY `id_registro` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;
--
-- AUTO_INCREMENT de la tabla `ruta`
--
ALTER TABLE `ruta`
  MODIFY `id_ruta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `tarifa`
--
ALTER TABLE `tarifa`
  MODIFY `id_tarifa` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `viaje_unidad`
--
ALTER TABLE `viaje_unidad`
  MODIFY `id_viaje_unidad` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
