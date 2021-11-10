CREATE DATABASE  IF NOT EXISTS `mnp_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mnp_db`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: mnp_db
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `operator`
--

DROP TABLE IF EXISTS `operator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operator` (
  `header` varchar(45) NOT NULL,
  `prefix` varchar(3) NOT NULL,
  PRIMARY KEY (`header`),
  UNIQUE KEY `prefix_UNIQUE` (`prefix`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operator`
--

LOCK TABLES `operator` WRITE;
/*!40000 ALTER TABLE `operator` DISABLE KEYS */;
INSERT INTO `operator` VALUES ('vodafone','010'),('etisalat','011'),('orange','012');
/*!40000 ALTER TABLE `operator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone_number`
--

DROP TABLE IF EXISTS `phone_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phone_number` (
  `number` varchar(15) NOT NULL,
  `operator` varchar(45) NOT NULL,
  PRIMARY KEY (`number`),
  UNIQUE KEY `number_UNIQUE` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone_number`
--

LOCK TABLES `phone_number` WRITE;
/*!40000 ALTER TABLE `phone_number` DISABLE KEYS */;
INSERT INTO `phone_number` VALUES ('01146111420','orange'),('01146115420','orange'),('01156519420','vodafone'),('01256515420','vodafone'),('01256515421','vodafone');
/*!40000 ALTER TABLE `phone_number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `recipient` varchar(45) NOT NULL,
  `donor` varchar(45) NOT NULL,
  `number` varchar(15) NOT NULL,
  `status` varchar(45) NOT NULL,
  `expired_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_operator_idx` (`recipient`,`donor`),
  KEY `fk_donor_idx` (`donor`),
  CONSTRAINT `fk_donor` FOREIGN KEY (`donor`) REFERENCES `operator` (`header`),
  CONSTRAINT `fk_recipient` FOREIGN KEY (`recipient`) REFERENCES `operator` (`header`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES (1,'vodafone','orange','01156515420','REJECTED','2021-11-07 19:46:26'),(2,'vodafone','orange','01256515420','ACCEPTED','2021-11-07 19:50:27'),(3,'vodafone','orange','01256515421','ACCEPTED','2021-11-07 22:05:06'),(4,'vodafone','orange','01296515421','CANCELED','2021-11-07 22:05:56'),(5,'vodafone','orange','01256575421','CANCELED','2021-11-07 23:38:02'),(6,'vodafone','etisalat','01156519420','ACCEPTED','2021-11-07 23:59:30'),(7,'vodafone','etisalat','01153519420','REJECTED','2021-11-08 00:00:27'),(8,'vodafone','etisalat','01153519520','CANCELED','2021-11-08 00:01:01'),(9,'vodafone','etisalat','01156219420','CANCELED','2021-11-08 12:59:26'),(10,'orange','etisalat','01156515420','CANCELED','2021-11-08 13:01:16'),(11,'orange','etisalat','01146115420','CANCELED','2021-11-09 14:03:47'),(12,'orange','etisalat','01146115420','ACCEPTED','2021-11-09 14:06:29'),(13,'orange','etisalat','01146111420','ACCEPTED','2021-11-10 05:58:02'),(14,'orange','etisalat','01141111420','REJECTED','2021-11-10 06:09:06');
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-10  6:35:30
