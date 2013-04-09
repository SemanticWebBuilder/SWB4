CREATE TABLE resrepository (
  rep_docId BIGINT NOT NULL,
  resId VARCHAR(255) NOT NULL,
  idtm VARCHAR(50) NOT NULL,
  topic VARCHAR(101) NOT NULL,
  rep_email VARCHAR(99) NULL,
  rep_title VARCHAR(99) NULL,
  rep_description VARCHAR(255) NULL,
  rep_lastVersion INTEGER NULL,
  rep_xml LONGVARCHAR,
  rep_status TINYINT NULL,
  rep_emailCheckOut VARCHAR(99) NULL,
  rep_deleted TINYINT  NULL,
  rep_create TIMESTAMP NOT NULL,
  PRIMARY KEY  (rep_docId,idtm)
);


CREATE TABLE resrepositorylog (
  rep_docId BIGINT NOT NULL,
  rep_action VARCHAR(15) NULL,
  rep_email VARCHAR(99) NULL,
  rep_user VARCHAR(50) NULL,
  rep_name VARCHAR(255) NOT NULL,
  rep_topicid VARCHAR(50) NULL,
  rep_topicmapid VARCHAR(50) NULL,
  rep_objectid BIGINT NOT NULL,
  rep_dateaction TIMESTAMP NOT NULL,
  rep_description VARCHAR(255) NULL,
  rep_isfile TINYINT NOT NULL,
  rep_ipuser VARCHAR(15) DEFAULT '127.0.0.1'
);

CREATE TABLE resrepositorynotify (
  rep_docId BIGINT NULL,
  idtm VARCHAR(50) NULL,
  topic VARCHAR(101) NULL,
  rep_email VARCHAR(99) NULL,
  rep_role VARCHAR(50) DEFAULT '0',
  PRIMARY KEY  (rep_docId,idtm,topic)
);

CREATE TABLE resrepositoryversions (
  rep_docId BIGINT NOT NULL,
  rep_fileVersion TINYINT NOT NULL,
  idtm VARCHAR(50) NOT NULL,
  rep_email VARCHAR(99) NULL,
  rep_fileName VARCHAR(255) NOT NULL,
  rep_fileSize BIGINT NOT NULL,
  rep_fileDate TIMESTAMP NOT NULL,
  rep_fileType VARCHAR(100) NULL,
  rep_comment VARCHAR(255) NULL,
  rep_create TIMESTAMP NOT NULL,
  PRIMARY KEY  (rep_docId,rep_fileVersion,idtm)
);

