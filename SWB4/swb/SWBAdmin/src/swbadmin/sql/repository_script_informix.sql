CREATE TABLE resrepository (
  rep_docId INTEGER DEFAULT 0 NOT NULL,
  resId VARCHAR(255) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  topic VARCHAR(101) DEFAULT '' NOT NULL,
  rep_email VARCHAR(99),
  rep_title VARCHAR(99),
  rep_description VARCHAR(255),
  rep_lastVersion SMALLINT,
  rep_xml TEXT IN TABLE,
  rep_status SMALLINT,
  rep_emailCheckOut VARCHAR(99),
  rep_deleted SMALLINT,
  rep_create DATETIME YEAR to FRACTION NOT NULL,
  PRIMARY KEY  (rep_docId,idtm)
) ;

CREATE TABLE resrepositorylog (
  rep_docId INTEGER DEFAULT 0 NOT NULL,
  rep_action VARCHAR(15),
  rep_email VARCHAR(99),
  rep_user VARCHAR(50),
  rep_name VARCHAR(255) DEFAULT '' NOT NULL,
  rep_topicid VARCHAR(50),
  rep_topicmapid VARCHAR(50),
  rep_objectid INTEGER DEFAULT 0 NOT NULL,
  rep_dateaction DATETIME YEAR to FRACTION NOT NULL,
  rep_description VARCHAR(255),
  rep_isfile SMALLINT  DEFAULT 0 NOT NULL,
  rep_ipuser VARCHAR(15) DEFAULT '127.0.0.1' NOT NULL
) ;

CREATE TABLE resrepositorynotify (
  rep_docId INTEGER DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) NOT NULL,
  topic VARCHAR(101),
  rep_email VARCHAR(99),
  rep_role VARCHAR(50)  DEFAULT '0' NOT NULL,
  PRIMARY KEY (rep_docId,idtm,topic)
) ;

CREATE TABLE resrepositoryversions (
  rep_docId INTEGER  DEFAULT 0 NOT NULL,
  rep_fileVersion SMALLINT  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) NOT NULL,
  rep_email VARCHAR(99),
  rep_fileName VARCHAR(255)  DEFAULT '' NOT NULL,
  rep_fileSize INTEGER  DEFAULT 0 NOT NULL,
  rep_fileDate DATETIME YEAR to FRACTION NOT NULL,
  rep_fileType VARCHAR(100),
  rep_comment VARCHAR(255),
  rep_create DATETIME YEAR to FRACTION NOT NULL,
  PRIMARY KEY  (rep_docId,rep_fileVersion,idtm)
) ;