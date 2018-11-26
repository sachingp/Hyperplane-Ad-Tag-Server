-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: ad_server
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_guid` varchar(100) NOT NULL,
  `account_type_id` int(11) NOT NULL,
  `account_name` varchar(100) NOT NULL,
  `account_email` varchar(200) NOT NULL,
  `account_website` varchar(500) NOT NULL,
  `address_id` int(11) NOT NULL,
  `logo_url` varchar(500) NOT NULL,
  `time_zone_id` smallint(6) NOT NULL,
  `currency_id` smallint(6) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`),
  KEY `acc_type_fk_idx` (`account_type_id`),
  KEY `acc_addr_fk_idx` (`address_id`),
  KEY `acc_status_fk_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account_event_trackers`
--

DROP TABLE IF EXISTS `account_event_trackers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_event_trackers` (
  `account_event_tracker_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `tracker_url` varchar(250) NOT NULL,
  `append_macros` tinyint(1) NOT NULL DEFAULT '1',
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`account_event_tracker_id`),
  KEY `acc_trc_fk1_idx` (`account_id`),
  KEY `acc_trc_fk2_idx` (`event_id`),
  KEY `acc_trc_fk3_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_type` (
  `account_type_id` int(11) NOT NULL,
  `account_type_label` varchar(50) NOT NULL DEFAULT 'Advertiser',
  PRIMARY KEY (`account_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ad_partner`
--

DROP TABLE IF EXISTS `ad_partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_partner` (
  `ad_partner_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address_id` int(11) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `ad_partner_type_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`ad_partner_id`),
  KEY `ad_partner_type_fk_idx` (`ad_partner_type_id`),
  KEY `ad_partner_status_fk_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ad_partner_type`
--

DROP TABLE IF EXISTS `ad_partner_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ad_partner_type` (
  `ad_partner_type_id` int(11) NOT NULL,
  `ad_partner_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`ad_partner_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `address_id` int(11) NOT NULL,
  `address_type_id` int(11) NOT NULL,
  `address_line1` varchar(250) NOT NULL,
  `address_line2` varchar(45) DEFAULT NULL,
  `country_id` int(11) NOT NULL,
  `state_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  `postal_code_id` int(11) NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `addr_addr_type_fk_idx` (`address_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `address_type`
--

DROP TABLE IF EXISTS `address_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address_type` (
  `address_type_id` int(11) NOT NULL,
  `address_type_desc` varchar(50) NOT NULL DEFAULT 'Business',
  PRIMARY KEY (`address_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `advertiser`
--

DROP TABLE IF EXISTS `advertiser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertiser` (
  `advertiser_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `advertiser_name` varchar(100) NOT NULL,
  `advertiser_email` varchar(200) NOT NULL,
  `advertiser_website` varchar(500) NOT NULL,
  `address_id` int(11) NOT NULL,
  `logo_url` varchar(500) NOT NULL,
  `time_zone_id` smallint(6) NOT NULL,
  `currency_id` smallint(6) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`advertiser_id`),
  KEY `acc_adv_fk_idx` (`account_id`),
  KEY `adv_addr_fk_idx` (`address_id`),
  KEY `adv_status_fk_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `advertiser_event_trackers`
--

DROP TABLE IF EXISTS `advertiser_event_trackers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertiser_event_trackers` (
  `advertiser_tracker_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `advertiser_id` int(11) NOT NULL,
  `tracker_url` varchar(250) NOT NULL,
  `append_macros` tinyint(1) NOT NULL DEFAULT '1',
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`advertiser_tracker_id`),
  KEY `adv_trc_fk1_idx` (`advertiser_id`),
  KEY `adv_trc_fk2_idx` (`event_id`),
  KEY `adv_trc_fk3_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_size`
--

DROP TABLE IF EXISTS `asset_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_size` (
  `asset_size_id` int(11) NOT NULL,
  `size` varchar(45) NOT NULL,
  PRIMARY KEY (`asset_size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset_type`
--

DROP TABLE IF EXISTS `asset_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset_type` (
  `asset_type_id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `ext` varchar(50) DEFAULT NULL,
  `mime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`asset_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `billing`
--

DROP TABLE IF EXISTS `billing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing` (
  `billing_id` int(11) NOT NULL,
  `billing_owner_id` int(11) NOT NULL,
  `billing_payment_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`billing_id`),
  KEY `billing_user_fk_idx` (`billing_owner_id`),
  KEY `billing_status_fk_idx` (`status_id`),
  KEY `billing_payment_id_idx` (`billing_payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `billing_cycle`
--

DROP TABLE IF EXISTS `billing_cycle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing_cycle` (
  `billing_cycle_id` int(11) NOT NULL,
  `billing_cycle_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`billing_cycle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `billing_payment_details`
--

DROP TABLE IF EXISTS `billing_payment_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing_payment_details` (
  `billing_payment_details_id` int(11) NOT NULL,
  `billing_id` int(11) NOT NULL,
  `billing_type_id` int(11) NOT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `bank_account_number` int(11) DEFAULT NULL,
  `bank_routing_number` int(11) DEFAULT NULL,
  `credit_card_number` int(11) DEFAULT NULL,
  `credit_card_expiry_date` varchar(45) DEFAULT NULL,
  `credit_card_cvv` varchar(45) DEFAULT NULL,
  `billing_cycle_id` int(11) DEFAULT NULL,
  `billing_date` datetime DEFAULT NULL,
  PRIMARY KEY (`billing_payment_details_id`),
  KEY `billing_cycle_fk_idx` (`billing_cycle_id`),
  KEY `billing_type_fk_idx` (`billing_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `billing_type`
--

DROP TABLE IF EXISTS `billing_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing_type` (
  `billing_type_id` int(11) NOT NULL,
  `billing_type_label` varchar(50) NOT NULL DEFAULT 'ACH',
  PRIMARY KEY (`billing_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign` (
  `campaign_id` int(11) NOT NULL,
  `advertiser_id` int(11) NOT NULL,
  `campaign_name` varchar(200) NOT NULL,
  `objective_type_id` int(11) NOT NULL,
  `custom_attrbutes` varchar(5000) DEFAULT NULL,
  `target_id` int(11) NOT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`campaign_id`),
  KEY `acc_campaign_fk_idx` (`advertiser_id`),
  KEY `campaign_obj_fk_idx` (`objective_type_id`),
  KEY `campaign_status_fk_idx` (`status_id`),
  KEY `campaign_target_fk_idx` (`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `city_id` int(11) NOT NULL,
  `geoname_id` int(11) NOT NULL,
  `state_id` int(11) NOT NULL,
  `city_name` varchar(100) NOT NULL,
  `city_code` varchar(5) DEFAULT NULL,
  `postal_code` varchar(15) DEFAULT NULL,
  `census_data_dma_id` int(11) NOT NULL,
  PRIMARY KEY (`city_id`),
  KEY `city_state_fk_idx` (`state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `country_id` int(11) NOT NULL,
  `geoname_id` smallint(6) NOT NULL,
  `region_id` smallint(6) NOT NULL,
  `currency_id` smallint(6) NOT NULL,
  `country_code` varchar(3) NOT NULL,
  `country_code_2` varchar(2) NOT NULL,
  `country_name` varchar(100) NOT NULL,
  `local_name` varchar(100) NOT NULL,
  PRIMARY KEY (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creative`
--

DROP TABLE IF EXISTS `creative`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative` (
  `creative_id` int(11) NOT NULL,
  `campaign_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `target_id` int(11) NOT NULL,
  `creative_size_id` int(11) DEFAULT NULL,
  `creative_format_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`creative_id`),
  KEY `cr_cmp_fk_idx` (`campaign_id`),
  KEY `cr_format_fk_idx` (`creative_format_id`),
  KEY `cr_target_fk_idx` (`target_id`),
  KEY `cr_size_fk_idx` (`creative_size_id`),
  KEY `cr_status-fk_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creative_assets`
--

DROP TABLE IF EXISTS `creative_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative_assets` (
  `creative_asset_id` int(11) NOT NULL,
  `creative_id` int(11) NOT NULL,
  `asset_size_id` int(11) NOT NULL,
  `asset_type` int(11) NOT NULL,
  `asset_url` varchar(250) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`creative_asset_id`),
  KEY `asset_cr_fk_idx` (`creative_id`),
  KEY `asset_type_fk_idx` (`asset_type`),
  KEY `asset_size_fk_idx` (`asset_size_id`),
  KEY `asset_status_fk_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creative_format`
--

DROP TABLE IF EXISTS `creative_format`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative_format` (
  `creative_format_id` int(11) NOT NULL,
  `format` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`creative_format_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creative_group`
--

DROP TABLE IF EXISTS `creative_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative_group` (
  `creative_group_id` int(11) NOT NULL,
  `creative_group_name` varchar(100) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`creative_group_id`,`creative_group_name`),
  KEY `cr_group_status_fk_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creative_group_coll`
--

DROP TABLE IF EXISTS `creative_group_coll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative_group_coll` (
  `creative_group_coll_id` int(11) NOT NULL,
  `creative_group_id` int(11) NOT NULL,
  `creative_id` int(11) NOT NULL,
  `distribution_percen` float DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`creative_group_coll_id`,`creative_id`),
  KEY `group_fk1_idx` (`creative_group_id`),
  KEY `group_fk2_idx` (`creative_id`),
  KEY `group_fk3_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creative_size`
--

DROP TABLE IF EXISTS `creative_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative_size` (
  `creative_size_id` int(11) NOT NULL,
  `size` varchar(50) NOT NULL,
  PRIMARY KEY (`creative_size_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `creative_tag`
--

DROP TABLE IF EXISTS `creative_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creative_tag` (
  `creative_tag_id` int(11) NOT NULL,
  `ad_partner_id` int(11) NOT NULL,
  `tag_type_id` int(11) NOT NULL,
  `creative_id` int(11) NOT NULL,
  `tag_guid` varchar(300) NOT NULL,
  `creative_markup_url` varchar(200) NOT NULL,
  PRIMARY KEY (`creative_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custom_attributes_keys`
--

DROP TABLE IF EXISTS `custom_attributes_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_attributes_keys` (
  `custom_attributes_id` int(11) NOT NULL,
  `key_name` varchar(100) NOT NULL,
  `kay_label` varchar(150) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`custom_attributes_id`),
  KEY `csattr_keys_fk_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custom_attributes_values`
--

DROP TABLE IF EXISTS `custom_attributes_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_attributes_values` (
  `custom_attributes_values_id` int(11) NOT NULL,
  `key_id` int(11) NOT NULL,
  `value` varchar(200) NOT NULL,
  PRIMARY KEY (`custom_attributes_values_id`),
  KEY `csattr_values_fk_idx` (`key_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `day_part`
--

DROP TABLE IF EXISTS `day_part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `day_part` (
  `day_part_id` int(11) NOT NULL,
  `day_part` varchar(100) NOT NULL,
  PRIMARY KEY (`day_part_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events` (
  `event_id` int(11) NOT NULL,
  `event_name` varchar(50) NOT NULL,
  `event_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `macros`
--

DROP TABLE IF EXISTS `macros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `macros` (
  `macros_id` int(11) NOT NULL,
  `macro_name` varchar(100) NOT NULL,
  `macro_value` varchar(100) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `advertiser_id` int(11) DEFAULT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`macros_id`),
  KEY `macros_acc_fk_idx` (`account_id`),
  KEY `macros_adv_fk_idx` (`advertiser_id`),
  KEY `macros_partner_fk_idx` (`partner_id`),
  KEY `macros_status_fk_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `objective_type`
--

DROP TABLE IF EXISTS `objective_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objective_type` (
  `objective_type_id` int(11) NOT NULL,
  `objective_name` varchar(50) NOT NULL,
  PRIMARY KEY (`objective_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `os`
--

DROP TABLE IF EXISTS `os`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `os` (
  `os_id` int(11) NOT NULL,
  `os_name` varchar(50) NOT NULL,
  `os_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`os_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `postal_code`
--

DROP TABLE IF EXISTS `postal_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postal_code` (
  `postal_code_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  `postal_code` varchar(50) NOT NULL,
  PRIMARY KEY (`postal_code_id`),
  KEY `postal_cc_city_fk_idx` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `role_type` varchar(50) NOT NULL DEFAULT 'Trafficker',
  `role_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `segment`
--

DROP TABLE IF EXISTS `segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segment` (
  `segment_id` int(11) NOT NULL,
  `segment_name` varchar(100) NOT NULL,
  `partner_name` int(11) NOT NULL,
  `partner_segment_taxonomy_id` varchar(45) DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`segment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `state_id` int(11) NOT NULL,
  `state_unique_id` int(11) NOT NULL,
  `country_id` int(11) NOT NULL,
  `state_long_name` varchar(100) NOT NULL,
  `state_code` varchar(10) NOT NULL,
  `fips_code` varchar(5) NOT NULL,
  `time_zone_id` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`state_id`),
  KEY `state_coun_fk_idx` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `status_id` int(11) NOT NULL,
  `status_desc` varchar(50) NOT NULL DEFAULT 'Active',
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_type`
--

DROP TABLE IF EXISTS `tag_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_type` (
  `tag_type_id` int(11) NOT NULL,
  `tag_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tag_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `target`
--

DROP TABLE IF EXISTS `target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `target` (
  `target_id` int(11) NOT NULL,
  `target_name` varchar(100) DEFAULT NULL,
  `by_os` varchar(512) DEFAULT NULL,
  `by_day_part` varchar(512) DEFAULT NULL,
  `by_segment` blob,
  `by_geo` blob,
  PRIMARY KEY (`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(50) NOT NULL,
  `password_salt` varchar(50) DEFAULT NULL,
  `profile_photo_file_manager_id` varchar(200) DEFAULT NULL,
  `locale_id` varchar(45) DEFAULT NULL,
  `last_login_ip_address` varchar(50) DEFAULT NULL,
  `last_login_timestamp` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_accounts`
--

DROP TABLE IF EXISTS `user_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_accounts` (
  `user_accounts_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `user_role_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`user_accounts_id`),
  KEY `user_accounts_fk1_idx` (`account_id`),
  KEY `user_accounts_fk3_idx` (`status_id`),
  KEY `user_accounts_fk2_idx` (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_roles_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`user_roles_id`),
  KEY `user_roles_fk1_idx` (`user_id`),
  KEY `user_roles_fk2_idx` (`roles_id`),
  KEY `user_roles_fk3_idx` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-25 19:10:37
