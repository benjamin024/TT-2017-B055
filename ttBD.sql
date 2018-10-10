-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: tt
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aviso`
--

DROP TABLE IF EXISTS `aviso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `aviso` (
  `id_aviso` int(6) NOT NULL AUTO_INCREMENT,
  `aviso` text,
  `fecha_publicacion` date DEFAULT NULL,
  `fecha_termino` date DEFAULT NULL,
  PRIMARY KEY (`id_aviso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aviso`
--

LOCK TABLES `aviso` WRITE;
/*!40000 ALTER TABLE `aviso` DISABLE KEYS */;
/*!40000 ALTER TABLE `aviso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comentario`
--

DROP TABLE IF EXISTS `comentario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comentario` (
  `id_comentario` int(6) NOT NULL AUTO_INCREMENT,
  `correo_usuario` varchar(320) DEFAULT NULL,
  `comentario` text,
  `fecha` datetime DEFAULT NULL,
  PRIMARY KEY (`id_comentario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comentario`
--

LOCK TABLES `comentario` WRITE;
/*!40000 ALTER TABLE `comentario` DISABLE KEYS */;
/*!40000 ALTER TABLE `comentario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conductor`
--

DROP TABLE IF EXISTS `conductor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `conductor` (
  `rfc_conductor` varchar(13) DEFAULT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `sueldo` float(6,2) DEFAULT NULL,
  `no_licencia` varchar(16) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conductor`
--

LOCK TABLES `conductor` WRITE;
/*!40000 ALTER TABLE `conductor` DISABLE KEYS */;
/*!40000 ALTER TABLE `conductor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estacion`
--

DROP TABLE IF EXISTS `estacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `estacion` (
  `id_estacion` int(4) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) DEFAULT NULL,
  `latitud` double(12,8) DEFAULT NULL,
  `longitud` double(12,8) DEFAULT NULL,
  PRIMARY KEY (`id_estacion`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estacion`
--

LOCK TABLES `estacion` WRITE;
/*!40000 ALTER TABLE `estacion` DISABLE KEYS */;
INSERT INTO `estacion` VALUES (1,'Politecnico',19.50096400,-99.14918100),(2,'Instituto del Petróleo',19.48969600,-99.14469400),(3,'Autobuses del Norte',19.47921400,-99.14062100),(4,'La Raza',19.46988900,-99.13652200),(5,'Misterios',19.46358300,-99.13045200),(6,'Valle Gómez',19.45891600,-99.11991400),(7,'Consulado',19.45537600,-99.11294900),(8,'Eduardo Molina',19.45116500,-99.10544600),(9,'Aragón',19.45151400,-99.09626000),(10,'Oceanía',19.44490900,-99.08661400),(11,'Terminal Aérea',19.43397200,-99.08769900),(12,'Hangares',19.42463500,-99.08828600),(13,'Pantitlán',19.41650100,-99.07427700),(14,'Martín Carrera',19.48309800,-99.10716900),(15,'La Villa-Basílica',19.48152000,-99.11764000),(16,'Deportivo 18 de Marzo',19.48402900,-99.12555800),(17,'Lindavista',19.48814500,-99.13464500),(18,'Instituto del Petróleo',19.49116900,-99.14857100),(19,'Vallejo',19.49010700,-99.15603800),(20,'Norte 45',19.48846900,-99.16246500),(21,'Ferrería/Arena Ciudad de México',19.49065300,-99.17412700),(22,'Azcapotzalco',19.49106800,-99.18626200),(23,'Tezozomoc',19.49458800,-99.19590700),(24,'El Rosario',19.50496400,-99.19967300),(25,'El Rosario',19.50510600,-99.20040200),(26,'Aquiles Serdán',19.49085300,-99.19531800),(27,'Camarones',19.47911000,-99.18971800),(28,'Refinería',19.46962300,-99.19001800),(29,'Tacuba',19.45859900,-99.18850600),(30,'San Joaquín',19.44525500,-99.19175600),(31,'Polanco',19.43387400,-99.19090900),(32,'Auditorio',19.42521300,-99.19191700),(33,'Constituyentes',19.41213200,-99.19140700),(34,'Tacubaya',19.40239800,-99.18619200),(35,'Sn. Pedro de los Pinos',19.39175800,-99.18583000),(36,'San Antonio',19.38517000,-99.18684900),(37,'Mixcoac',19.37654700,-99.18758900),(38,'Barranca del Muerto',19.36164800,-99.18923100),(39,'Tacubaya',19.40195900,-99.18732100),(40,'Patriotismo',19.40597600,-99.17897400),(41,'Chilpancingo',19.40623900,-99.16853500),(42,'Centro Médico',19.40663400,-99.15498400),(43,'Lázaro Cárdenas',19.40721100,-99.14435200),(44,'Chabacano',19.40884900,-99.13360100),(45,'Jamaica',19.40925300,-99.12223900),(46,'Mixiuhca',19.40860600,-99.11321600),(47,'Velódromo',19.40881800,-99.10315200),(48,'Ciudad Deportiva',19.40873700,-99.09128600),(49,'Puebla',19.40754300,-99.08239200),(50,'Pantitlán',19.41555700,-99.07220000),(51,'Bulevar Guadalupano, 1150-1152',21.90669200,-102.25846075),(52,'Bulevar Guadalupano, 938',21.90557636,-102.26143181),(53,'Bulevar Guadalupano, 302',21.90351526,-102.26780702),(54,'Av. Aguascalientes Oriente SN',21.90186437,-102.27034313),(55,'Nazario Ortiz Garza, 701',21.89971419,-102.27148039),(56,'Nazario Ortiz Garza, 102',21.89648461,-102.27782118),(57,'Av. de la Convención de 1914 Oriente, 602',21.90100673,-102.28244731),(58,'Av. de la Convención de 1914 Norte, 109',21.90077432,-102.29202094),(59,'Petróleos Mexicanos, 326',21.89181254,-102.29450785),(60,'México 305',21.89042285,-102.29397125),(61,'Gral. José María Arteaga, 412',21.88939994,-102.29378581),(62,'Jardín de Zaragoza, 604',21.88672802,-102.29629818),(63,'Dr. Jesús Díaz de León, 111',21.87977747,-102.29402950),(64,'Av. Paseo de la Cruz, Lb',21.87228304,-102.29207222),(65,'José Ma. Chavez 30',21.87035146,-102.29500568),(66,'José Ma. Chavez 1121f',21.86691427,-102.29444403),(67,'Av. de la Convención de 1914 Sur 101a',21.86560181,-102.29548559),(68,'Av. de la Convención de 1914 Sur 1005',21.86741195,-102.30619017),(69,'De los maestros 1908',21.86565953,-102.31013838),(70,'De los maestros 704 ',21.85564109,-102.31617627),(71,'Av. Convención 1002',21.85212609,-102.31197057),(72,'Av. Ojocaliente 301',21.88632098,-102.25582543),(73,'Av. Ojocaliente 117',21.89084476,-102.25749057),(74,'Miguel Ángel Barberena Vega 718',21.89716620,-102.25914281),(75,'Av. Aguascalientes Oriente 1949',21.89489760,-102.26779304),(76,'Av. de la Convención de 1914 Norte, 1401',21.89791849,-102.30197295),(77,'Av. de la Convención de 1914 Norte, 2128a',21.89628487,-102.30798717),(78,'Av. de la Convención de 1914 Poniente, 1404',21.89232278,-102.31247182),(79,'Av. de la Convención de 1914 Poniente, 1215',21.88637297,-102.31249448),(80,'Av. de la Convención de 1914 Poniente, 420',21.87796565,-102.31224176),(81,'Av. de la Convención de 1914 Poniente, 132',21.87021182,-102.31070545),(82,'Av. de la Convención de 1914 Sur 101a',21.86560181,-102.29548559),(83,'Av. de la Convención de 1914 Sur 1005',21.86741195,-102.30619017),(84,'Encarnación de Díaz - Aguascalientes, 1108',21.86529280,-102.29296456),(85,'Av. de la Convención de 1914 Sur, 1503',21.86620884,-102.28352319),(86,'Av. de la Convención de 1914 Oriente, 837',21.87086096,-102.27118834),(87,'Av. de la Convención de 1914 Oriente, 202a',21.88019663,-102.27181422),(88,'Av. de la Convención de 1914 Oriente SN',21.89048365,-102.27404071),(89,'Lic. Miguel de la Madrid 56',21.95209970,-102.33507731),(90,'Primavera 108',21.94591270,-102.33011965),(91,'Lic. Miguel de la Madrid 998',21.93974270,-102.32591394),(92,'Lic. Miguel de la Madrid 1904',21.93147981,-102.32121565),(93,'Lic. Miguel de la Madrid 1926',21.92527973,-102.31801143),(94,'Av. Universidad 1704',21.92211469,-102.31634846),(95,'Av. Universidad 900',21.91451066,-102.31303650),(96,'Av. Universidad 612',21.90829948,-102.31024700),(97,'Av. Universidad 110',21.89783991,-102.30502072),(98,'José Ma. Chavez, 1531',21.86464628,-102.29408897),(99,'José Ma. Chavez, 1605a',21.85735752,-102.29282297),(100,'José Ma. Chavez, Lb',21.85073841,-102.29171177),(101,'José Ma. Chavez, 3005',21.84351699,-102.29046722),(102,'Av. Siglo XXI 149',21.86814030,-102.24427655),(103,'Av. Tecnológico 511',21.86812038,-102.24575713),(104,'Av. Tecnológico 203',21.86989269,-102.24882557),(105,'Av. Tecnológico 204',21.87210306,-102.25245192),(106,'Av. Tecnológico SN',21.87536324,-102.25717830),(107,'Lic. Adolfo López Mateos Oriente 1714',21.87854923,-102.26263208),(108,'Lic. Adolfo López Mateos Oriente 1610',21.87776270,-102.26665539),(109,'Lic. Adolfo López Mateos Oriente 1317',21.87811116,-102.27325362),(110,'Lic. Adolfo López Mateos Oriente 610',21.87910677,-102.28665394),(111,'Lic. Adolfo López Mateos Oriente 303',21.87756897,-102.29911192),(112,'Lic. Adolfo López Mateos Poniente 713',21.87450243,-102.31046303),(113,'Calvillo - Aguascalientes 1508',21.87247250,-102.31615262),(114,'Calvillo - Aguascalientes 1383',21.87119806,-102.32160287),(115,'Calvillo - Aguascalientes 112a',21.87339316,-102.33294799),(116,'Calvillo - Aguascalientes 9',21.87611780,-102.34356461),(117,'Calvillo - Aguascalientes 2012',21.87972192,-102.35655723),(118,'Calvillo - Aguascalientes 5343',21.88230247,-102.36655073);
/*!40000 ALTER TABLE `estacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forma_cobro`
--

DROP TABLE IF EXISTS `forma_cobro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `forma_cobro` (
  `id_forma_cobro` int(2) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id_forma_cobro`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forma_cobro`
--

LOCK TABLES `forma_cobro` WRITE;
/*!40000 ALTER TABLE `forma_cobro` DISABLE KEYS */;
INSERT INTO `forma_cobro` VALUES (1,'tarjeta'),(2,'efectivo');
/*!40000 ALTER TABLE `forma_cobro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horario`
--

DROP TABLE IF EXISTS `horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `horario` (
  `id_horario` int(2) NOT NULL AUTO_INCREMENT,
  `dias` varchar(60) DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_termino` time DEFAULT NULL,
  `frecuencia` time DEFAULT NULL,
  PRIMARY KEY (`id_horario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario`
--

LOCK TABLES `horario` WRITE;
/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
/*!40000 ALTER TABLE `horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mantenimiento`
--

DROP TABLE IF EXISTS `mantenimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mantenimiento` (
  `id_mantenimiento` int(6) NOT NULL AUTO_INCREMENT,
  `id_unidad` varchar(10) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `responsable` varchar(60) DEFAULT NULL,
  `comentarios` text,
  `costo` float(7,2) DEFAULT NULL,
  PRIMARY KEY (`id_mantenimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mantenimiento`
--

LOCK TABLES `mantenimiento` WRITE;
/*!40000 ALTER TABLE `mantenimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `mantenimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro`
--

DROP TABLE IF EXISTS `registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `registro` (
  `id_registro` int(6) NOT NULL AUTO_INCREMENT,
  `id_viaje_unidad` int(6) DEFAULT NULL,
  `id_estacion` int(4) DEFAULT NULL,
  `no_pasajeros` int(3) DEFAULT NULL,
  `no_pasajeros_especial` int(3) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  PRIMARY KEY (`id_registro`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro`
--

LOCK TABLES `registro` WRITE;
/*!40000 ALTER TABLE `registro` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruta`
--

DROP TABLE IF EXISTS `ruta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ruta` (
  `id_ruta` int(4) DEFAULT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `combustible` float(6,3) DEFAULT NULL,
  `id_forma_cobro` int(2) DEFAULT NULL,
  `color` varchar(7) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruta`
--

LOCK TABLES `ruta` WRITE;
/*!40000 ALTER TABLE `ruta` DISABLE KEYS */;
INSERT INTO `ruta` VALUES (1,'linea 5',30.200,1,'#fddf00'),(2,'linea 6',23.510,1,'#0064a8'),(3,'linea 7',49.030,1,'#ff6309'),(4,'linea 9',37.312,1,'#5b2c2a'),(9,'Ruta 9',32.120,2,'#00ff6b'),(20,'Ruta 20',36.847,2,'#f9ef3d'),(33,'Ruta 33',28.147,2,'#fa3535'),(41,'Ruta 41',22.887,2,'#003f5c');
/*!40000 ALTER TABLE `ruta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruta_estacion`
--

DROP TABLE IF EXISTS `ruta_estacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ruta_estacion` (
  `id_ruta` int(4) DEFAULT NULL,
  `id_estacion` int(4) DEFAULT NULL,
  `sig_estacion` int(2) DEFAULT NULL,
  `transbordo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruta_estacion`
--

LOCK TABLES `ruta_estacion` WRITE;
/*!40000 ALTER TABLE `ruta_estacion` DISABLE KEYS */;
INSERT INTO `ruta_estacion` VALUES (1,1,2,''),(1,2,3,'18'),(1,3,4,''),(1,4,5,''),(1,5,6,''),(1,6,7,''),(1,7,8,''),(1,8,9,''),(1,9,10,''),(1,10,11,''),(1,11,12,''),(1,12,13,''),(2,14,15,''),(2,15,16,''),(2,16,17,''),(2,17,18,''),(2,18,19,'2'),(2,19,20,''),(2,20,21,''),(2,21,22,''),(2,22,23,''),(2,23,24,''),(3,25,26,'24'),(3,26,27,''),(3,27,28,''),(3,28,29,''),(3,29,30,''),(3,30,31,''),(3,31,32,''),(3,32,33,''),(3,33,34,''),(3,34,35,'39'),(3,35,36,''),(3,36,37,''),(3,37,38,''),(4,39,40,'34'),(4,40,41,''),(4,41,42,''),(4,42,43,''),(4,43,44,''),(4,44,45,''),(4,45,46,''),(4,46,47,''),(4,47,48,''),(4,48,49,''),(4,49,50,''),(1,13,0,''),(2,24,0,'25'),(3,38,0,''),(4,50,0,''),(9,51,52,''),(9,52,53,''),(9,53,54,''),(9,54,55,''),(9,55,56,''),(9,56,57,''),(9,57,58,''),(9,58,59,''),(9,59,60,''),(9,60,61,''),(9,61,62,''),(9,62,63,''),(9,63,64,''),(9,64,65,''),(9,65,66,''),(9,66,67,''),(9,67,68,''),(9,68,69,''),(9,69,70,''),(9,70,71,''),(20,72,73,''),(20,73,74,''),(20,74,75,''),(20,75,55,''),(20,55,56,''),(20,56,57,''),(20,57,58,''),(20,58,76,''),(20,76,77,''),(20,77,78,''),(20,78,79,''),(20,79,80,''),(20,80,81,''),(20,81,82,''),(20,82,83,''),(20,83,84,''),(20,84,85,''),(20,85,86,''),(20,86,87,''),(20,87,88,''),(20,88,56,''),(20,56,55,''),(20,55,75,''),(20,75,74,''),(20,74,73,''),(20,73,72,''),(33,89,90,''),(33,90,91,''),(33,91,92,''),(33,92,93,''),(33,93,94,''),(33,94,95,''),(33,95,96,''),(33,96,97,''),(33,97,77,''),(33,77,78,''),(33,78,79,''),(33,79,80,''),(33,80,81,''),(33,81,82,''),(33,82,83,''),(33,83,98,''),(33,98,99,''),(33,99,100,''),(33,100,101,'');
/*!40000 ALTER TABLE `ruta_estacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruta_horario`
--

DROP TABLE IF EXISTS `ruta_horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ruta_horario` (
  `id_ruta` int(4) DEFAULT NULL,
  `id_horario` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruta_horario`
--

LOCK TABLES `ruta_horario` WRITE;
/*!40000 ALTER TABLE `ruta_horario` DISABLE KEYS */;
/*!40000 ALTER TABLE `ruta_horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruta_tarifa`
--

DROP TABLE IF EXISTS `ruta_tarifa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ruta_tarifa` (
  `id_ruta` int(4) DEFAULT NULL,
  `id_tarifa` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruta_tarifa`
--

LOCK TABLES `ruta_tarifa` WRITE;
/*!40000 ALTER TABLE `ruta_tarifa` DISABLE KEYS */;
/*!40000 ALTER TABLE `ruta_tarifa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarifa`
--

DROP TABLE IF EXISTS `tarifa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tarifa` (
  `id_tarifa` int(3) NOT NULL AUTO_INCREMENT,
  `costo` float(5,2) DEFAULT NULL,
  PRIMARY KEY (`id_tarifa`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarifa`
--

LOCK TABLES `tarifa` WRITE;
/*!40000 ALTER TABLE `tarifa` DISABLE KEYS */;
/*!40000 ALTER TABLE `tarifa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidad`
--

DROP TABLE IF EXISTS `unidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `unidad` (
  `id_unidad` varchar(10) DEFAULT NULL,
  `capacidad` int(3) DEFAULT NULL,
  `id_ruta` int(4) DEFAULT NULL,
  `inicio_operaciones` date DEFAULT NULL,
  `responsable` varchar(60) DEFAULT NULL,
  `fin_operaciones` date DEFAULT NULL,
  `latitud` double(12,8) DEFAULT NULL,
  `longitud` double(12,8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidad`
--

LOCK TABLES `unidad` WRITE;
/*!40000 ALTER TABLE `unidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `unidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `correo_usuario` varchar(320) DEFAULT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `tipo` int(2) DEFAULT NULL,
  `no_tarjeta` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('l@gmail.com','Luis','ol',1,'839');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viaje_unidad`
--

DROP TABLE IF EXISTS `viaje_unidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `viaje_unidad` (
  `id_viaje_unidad` int(6) NOT NULL AUTO_INCREMENT,
  `id_unidad` varchar(10) DEFAULT NULL,
  `rfc_conductor` varchar(13) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora_salida` time DEFAULT NULL,
  `hora_termino` time DEFAULT NULL,
  PRIMARY KEY (`id_viaje_unidad`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viaje_unidad`
--

LOCK TABLES `viaje_unidad` WRITE;
/*!40000 ALTER TABLE `viaje_unidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `viaje_unidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viaje_usuario`
--

DROP TABLE IF EXISTS `viaje_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `viaje_usuario` (
  `id_viaje_usuario` int(6) NOT NULL AUTO_INCREMENT,
  `fecha_hora` datetime DEFAULT NULL,
  `correo_usuario` varchar(320) DEFAULT NULL,
  PRIMARY KEY (`id_viaje_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viaje_usuario`
--

LOCK TABLES `viaje_usuario` WRITE;
/*!40000 ALTER TABLE `viaje_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `viaje_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viaje_usuario_estacion`
--

DROP TABLE IF EXISTS `viaje_usuario_estacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `viaje_usuario_estacion` (
  `id_viaje_usuario` int(6) DEFAULT NULL,
  `id_estacion` int(4) DEFAULT NULL,
  `no_estacion` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viaje_usuario_estacion`
--

LOCK TABLES `viaje_usuario_estacion` WRITE;
/*!40000 ALTER TABLE `viaje_usuario_estacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `viaje_usuario_estacion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-09 23:12:33
