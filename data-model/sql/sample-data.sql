-
- Sample Data for campaign creation 
-

insert into address values (2,1,'114 W 41st st, 6th floor','',197,2460,96648,7251);


insert into ad_partner values (1,'Beeswax',2,'info@beeswax.com',2,1);
	
insert into macros values (1,'ord','{CACHE_BUSTER}',null,null,1,1);
	
insert into macros values (2,'extPlId','{PLACEMENT_ID}',null,null,1,1);
	
insert into macros values (3,'extCrId','{CREATIVE_ID}',null,null,1,1);

insert into macros values (4,'exCpId','{CAMPAIGN_ID}',null,null,1,1);
	
insert into macros values (5,'extBnId','{APP_BUNDLE}',null,null,1,1);
				
insert into macros values (6,'extApId','{APP_ID}',null,null,1,1);

insert into macros values (7,'extApNm','{APP_NAME}',null,null,1,1);

insert into macros values (8,'extStId','{SITE_ID}',null,null,1,1);

insert into macros values (9,'gpsLat','{LAT}',null,null,1,1);

insert into macros values (10,'gpsLng','{LONG}',null,null,1,1);

insert into macros values (11,'deviceId','{MOBILE_ID}',null,null,1,1);
		
insert into macros values (12,'buyId','{BUYER_ID}',null,null,1,1);
	
insert into macros values (13,'iosId','{IOS_ID}',null,null,1,1);
	
insert into macros values (14,'androidId','{ANDROID_ID}',null,null,1,1);
		
insert into macros values (15,'extAdServer','{EXTERNAL_AD_SERVER}',null,null,1,1);
	
	
insert into campaign values (1,1,'Test Uber Eat Campaign',3,null,1,'2018-12-01','2018-12-31',now(),now(),1);
		
insert into target values (1,'Test Uber Eat Target',null,null,null,'{"inclusion": ["USA"],"exclusion": []}');
	
insert into creative values (1,1,'{"Test Uber Creative',1,6,1,1);
	
insert into creative_assets values (1,1,6,3,'https://s3.us-east-2.amazonaws.com/hyper-media-assets/creatives/UBR20609-2_Sweater_+40-1_sRGB_sh.jpg',1);
	
	
insert into creative_tag values (1,1,1,1,'99016f97-1f41-4092-96c0-af03726e1c5d','');
	
update creative_assets set click_url='http://www.google.com' where creative_asset_id=1;
		
		
			

	
	
	
	