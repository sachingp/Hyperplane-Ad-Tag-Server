--
-- Dumping data for table `account_type`
--

LOCK TABLES `account_type` WRITE;
/*!40000 ALTER TABLE `account_type` DISABLE KEYS */;
INSERT INTO `account_type` VALUES (1,'Advertiser'), (2,'Agency'),(3,'Publisher');

/*!40000 ALTER TABLE `account_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


--
-- Dumping data for table `address_type`
--

LOCK TABLES `address_type` WRITE;
/*!40000 ALTER TABLE `address_type` DISABLE KEYS */;
INSERT INTO `address_type` VALUES (1,'Business'), (2,'Home');

/*!40000 ALTER TABLE `address_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


--
-- Dumping data for table `ad_partner_type`
--

LOCK TABLES `ad_partner_type` WRITE;
/*!40000 ALTER TABLE `ad_partner_type` DISABLE KEYS */;
INSERT INTO `ad_partner_type` VALUES (1,'Supply Partner'), (2,'Demand Partner');

/*!40000 ALTER TABLE `ad_partner_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


--
-- Dumping data for table `asset_size`
--

LOCK TABLES `asset_size` WRITE;
/*!40000 ALTER TABLE `asset_size` DISABLE KEYS */;
INSERT INTO `asset_size` VALUES (1,'300x250'), (2,'336x280'), (3,'728x90'), (4,'300x600'), (5,'320x100');

/*!40000 ALTER TABLE `asset_size` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;




--
-- Dumping data for table `tag_type`
--

LOCK TABLES `tag_type` WRITE;
/*!40000 ALTER TABLE `tag_type` DISABLE KEYS */;
INSERT INTO `tag_type` VALUES (1,'Mraid'), (2,'Vast'), (3,'Banner'), (4,'Vpaid'), (5,'Native');

/*!40000 ALTER TABLE `tag_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Admin','all account rights'), (2,'Trafficker','Read,Write and manage account campaigns'), (3,'Analyst','read only access');

/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Dumping data for table `os`
--

LOCK TABLES `os` WRITE;
/*!40000 ALTER TABLE `os` DISABLE KEYS */;
INSERT INTO `os` VALUES (1,'ios','apple inc'), (2,'android','google inc');

/*!40000 ALTER TABLE `os` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;



--
-- Dumping data for table `objective_type`
--

LOCK TABLES `objective_type` WRITE;
/*!40000 ALTER TABLE `objective_type` DISABLE KEYS */;
INSERT INTO `objective_type` VALUES (1,'Acquisition'), (2,'Experiment'), (3,'Performance'), (4,'Test');

/*!40000 ALTER TABLE `objective_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;




--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,'download','media download'), (2,'impression','media impression/display'), (3,'click','media click'), (4,'conversion','media conversion'), (5,'25%','video media 25%'), (6,'50%','video media 50%'), (7,'75%','video media 75%'), (8,'completion','video media 100%'), (9,'install','app install');

/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;





--
-- Dumping data for table `creative_size`
--

LOCK TABLES `creative_size` WRITE;
/*!40000 ALTER TABLE `creative_size` DISABLE KEYS */;
INSERT INTO `creative_size` VALUES (1,'300x250'), (2,'336x280'), (3,'728x90'), (4,'300x600'), (5,'320x100');

/*!40000 ALTER TABLE `creative_size` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;




--
-- Dumping data for table `creative_format`
--

LOCK TABLES `creative_format` WRITE;
/*!40000 ALTER TABLE `creative_format` DISABLE KEYS */;
INSERT INTO `creative_format` VALUES (1,'Rich Media'), (2,'Video'), (3,'Interactive Video'), (4,'Facebook');

/*!40000 ALTER TABLE `creative_format` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;



--
-- Dumping data for table `billing_type`
--

LOCK TABLES `billing_type` WRITE;
/*!40000 ALTER TABLE `billing_type` DISABLE KEYS */;
INSERT INTO `billing_type` VALUES (1,'Credit Card'), (2,'ACH'), (3,'Paypal');

/*!40000 ALTER TABLE `billing_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;



--
-- Dumping data for table `billing_cycle`
--

LOCK TABLES `billing_cycle` WRITE;
/*!40000 ALTER TABLE `billing_cycle` DISABLE KEYS */;
INSERT INTO `billing_cycle` VALUES (1,'Weekly'), (2,'Monthly'), (3,'Quarterly');

/*!40000 ALTER TABLE `billing_cycle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;





--
-- Dumping data for table `asset_type`
--

LOCK TABLES `asset_type` WRITE;
/*!40000 ALTER TABLE `asset_type` DISABLE KEYS */;
INSERT INTO `asset_type` VALUES (1,'video','mp4','video/mp4'), (2,'script','js','application/javascript'), (3,'image','jpg','image/jpeg'), (4,'image','png','image/png'), (5,'mjpeg','image','video/x-motion-jpeg');

/*!40000 ALTER TABLE `asset_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (10,'ad request','ad request');

/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;