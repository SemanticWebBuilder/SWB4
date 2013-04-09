CREATE TABLE resrepository (
  rep_docId bigint NOT NULL default '0',
  resId varchar(255) NOT NULL default '0',
  idtm varchar(50) NOT NULL,
  topic varchar(101) NOT NULL default '',
  rep_email varchar(99) default NULL,
  rep_title varchar(99) default NULL,
  rep_description varchar(255) default NULL,
  rep_lastVersion int default NULL,
  rep_xml text,
  rep_status tinyint default NULL,
  rep_emailCheckOut varchar(99) default NULL,
  rep_deleted tinyint default NULL,
  rep_create datetime NOT NULL,
  PRIMARY KEY  (rep_docId)
) ;

CREATE TABLE resrepositorylog (
  rep_docId bigint NOT NULL default '0',
  rep_action varchar(15) default NULL,
  rep_email varchar(99) default NULL,
  rep_user varchar(50) default NULL,
  rep_name varchar(255) NOT NULL default '',
  rep_topicid varchar(50) default NULL,
  rep_topicmapid varchar(50) default NULL,
  rep_objectid bigint NOT NULL default '0',
  rep_dateaction datetime NOT NULL,
  rep_description varchar(255) default NULL,
  rep_isfile tinyint  NOT NULL default '0',
  rep_ipuser varchar(15) default '127.0.0.1'
) ;

CREATE TABLE resrepositorynotify (
  rep_docId bigint NOT NULL default '0',
  idtm varchar(50) NULL,
  topic varchar(101) default NULL,
  rep_email varchar(99) default NULL,
  rep_role varchar(50) default '0',
  PRIMARY KEY (rep_docId,idtm,topic)
) ;

CREATE TABLE resrepositoryversions (
  rep_docId bigint NOT NULL,
  rep_fileVersion int NOT NULL default '0',
  idtm varchar(50) NOT NULL,
  rep_email varchar(99) default NULL,
  rep_fileName varchar(255) NOT NULL default '',
  rep_fileSize bigint NOT NULL default '0',
  rep_fileDate datetime NOT NULL,
  rep_fileType varchar(100) default NULL,
  rep_comment varchar(255) default NULL,
  rep_create datetime NOT NULL,
  PRIMARY KEY  (rep_docId,rep_fileVersion,idtm)
) ;