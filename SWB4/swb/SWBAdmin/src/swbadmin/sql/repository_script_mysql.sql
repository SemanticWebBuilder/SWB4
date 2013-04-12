# MySQL-Front Dump 2.5
#
# Host: localhost   Database: wb2
# --------------------------------------------------------
# Server version 4.0.14-nt


#
# Table structure for table 'resrepository'
#

CREATE TABLE resrepository (
  rep_docId bigint(20) unsigned NOT NULL default '0',
  resId varchar(255)  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  topic varchar(101) NOT NULL default '',
  rep_email varchar(99) default NULL,
  rep_title varchar(99) default NULL,
  rep_description varchar(255) default NULL,
  rep_lastVersion int(10) unsigned default NULL,
  rep_xml text,
  rep_status tinyint(3) unsigned default NULL,
  rep_emailCheckOut varchar(99) default NULL,
  rep_deleted tinyint(3) unsigned default NULL,
  rep_create timestamp NOT NULL,
  PRIMARY KEY  (rep_docId,idtm)
) ;



#
# Table structure for table 'resrepositorylog'
#

CREATE TABLE resrepositorylog (
  rep_docId bigint(20) unsigned NOT NULL default '0',
  rep_action varchar(15) default NULL,
  rep_email varchar(99) default NULL,
  rep_user varchar(50) default NULL,
  rep_name varchar(255) NOT NULL default '',
  rep_topicid varchar(50) default NULL,
  rep_topicmapid varchar(50) default NULL,
  rep_objectid bigint(20) unsigned NOT NULL default '0',
  rep_dateaction timestamp NOT NULL,
  rep_description varchar(255) default NULL,
  rep_isfile tinyint(3) unsigned NOT NULL default '0',
  rep_ipuser varchar(15) default '127.0.0.1'
) ;


#
# Table structure for table 'resrepositorynotify'
#

CREATE TABLE resrepositorynotify (
  rep_docId bigint(20) unsigned default NULL,
  idtm varchar(50) default NULL,
  topic varchar(101) default NULL,
  rep_email varchar(99) default NULL,
  rep_role varchar(50) default '0',
  KEY rep_docId (rep_docId,idtm,topic)
) ;



#
# Table structure for table 'resrepositoryversions'
#

CREATE TABLE resrepositoryversions (
  rep_docId bigint(20) unsigned NOT NULL default '0',
  rep_fileVersion int(3) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  rep_email varchar(99) default NULL,
  rep_fileName varchar(255) NOT NULL default '',
  rep_fileSize bigint(20) unsigned NOT NULL default '0',
  rep_fileDate timestamp NOT NULL,
  rep_fileType varchar(100) default NULL,
  rep_comment varchar(255) default NULL,
  rep_create timestamp NOT NULL,
  PRIMARY KEY  (rep_docId,rep_fileVersion,idtm)
) ;