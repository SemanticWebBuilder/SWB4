CREATE TABLE resrepository (
  rep_docId NUMBER(20) NOT NULL,
  resId VARCHAR2(255) NOT NULL,
  idtm VARCHAR2(50) NOT NULL,
  topic VARCHAR2(101) NOT NULL,
  rep_email VARCHAR2(99) NULL,
  rep_title VARCHAR2(99) NULL,
  rep_description VARCHAR2(255) NULL,
  rep_lastVersion NUMBER(10) NULL,
  rep_xml LONG,
  rep_status NUMBER(3) NULL,
  rep_emailCheckOut VARCHAR2(99) NULL,
  rep_deleted NUMBER(3)  NULL,
  rep_create DATE NOT NULL
);

ALTER TABLE resrepository 
       ADD  ( PRIMARY KEY  (rep_docId,idtm) ) ;

CREATE TABLE resrepositorylog (
  rep_docId NUMBER(20) NOT NULL,
  rep_action VARCHAR2(15) NULL,
  rep_email VARCHAR2(99) NULL,
  rep_user VARCHAR2(50) NULL,
  rep_name VARCHAR2(255) NOT NULL,
  rep_topicid VARCHAR2(50) NULL,
  rep_topicmapid VARCHAR2(50) NULL,
  rep_objectid NUMBER(20) NOT NULL,
  rep_dateaction DATE NOT NULL,
  rep_description VARCHAR2(255) NULL,
  rep_isfile NUMBER(3) NOT NULL,
  rep_ipuser VARCHAR2(15) DEFAULT '127.0.0.1'
);

CREATE TABLE resrepositorynotify (
  rep_docId NUMBER(20) NULL,
  idtm VARCHAR2(50) NULL,
  topic VARCHAR2(101) NULL,
  rep_email VARCHAR2(99) NULL,
  rep_role VARCHAR2(50) DEFAULT '0' 
);

ALTER TABLE resrepositorynotify 
       ADD  ( PRIMARY KEY (rep_docId,idtm,topic) ) ;

CREATE TABLE resrepositoryversions (
  rep_docId NUMBER(20) NOT NULL,
  rep_fileVersion NUMBER(3) NOT NULL,
  idtm VARCHAR2(50) NOT NULL,
  rep_email VARCHAR2(99) NULL,
  rep_fileName VARCHAR2(255) NOT NULL,
  rep_fileSize NUMBER(20) NOT NULL,
  rep_fileDate DATE NOT NULL,
  rep_fileType VARCHAR2(100) NULL,
  rep_comment VARCHAR2(255) NULL,
  rep_create DATE NOT NULL
);

ALTER TABLE resrepositoryversions 
       ADD  ( PRIMARY KEY  (rep_docId,rep_fileVersion,idtm) ) ;
