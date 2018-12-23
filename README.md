# Hyperplane-Ad-Tag-Server
Ad Server

Currently Uber relies on third-party ad servers such as DCM and Celtra for In-app, mobile web (mWeb) and desktop web (Web) display advertising ad serving. Uber spends upwards of $200 million annually on digital display media. Because of the massive scale, cost of ad serving is high through both DCM and Celtra.   
 
Ad servers with large market-share such as DCM primarily focus on serving basic ad server vanilla use cases. These Ad servers don’t meet more sophisticated (personalization, experimentation) uses cases that Uber has. Smaller ad server such as Celtra is willing to build custom features for Uber, however, Celtra charges premium platform fees in exchange.
 
Additionally, Uber will have to share its first-party data with DCM and Celtra to leverage their targeting, personalization and experimentation capabilities. Uber is not comfortable sharing its data because of data privacy, security and competitive reasons.
 
Considering this, we evaluated build vs. buy decision (link here) one month ago and ultimately decided to build our own ad server. This document provides use cases and requirements for MVP launch of Uber’s ad server. Please refer to this document (link here: still WIP) for Ad server narrative and complete use cases/feature list.

Technology 

SpringBoot

Vertx.x (Port 8080)

Akka

MySql

Redis

Velocity

MapDb

Nginx (Port 80)

Athena

Graphana

API 


http://ec2-18-191-183-129.us-east-2.compute.amazonaws.com:8091/ad-services/cache/state/list

Server

http://ec2-18-191-183-129.us-east-2.compute.amazonaws.com/ads?guid=99016f97-1f41-4092-96c0-af03726e1c5d
