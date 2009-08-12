CREATE TABLE sr_answer (
  answerid bigint(20) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  questionid bigint(20) unsigned NOT NULL default '0',
  responseid bigint(20) unsigned NOT NULL default '0',
  stringxml blob,
  score float unsigned zerofill default '000000000000',
  mark tinyint(1) unsigned default NULL,
  secuentialid tinyint(3) unsigned default '0',
  created timestamp(14) NOT NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (answerid,idtm),
  KEY ind_questionid (questionid),
  KEY ind_responseid (responseid)
) TYPE=MyISAM;

CREATE TABLE sr_category (
  categoryid int(10) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) default NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (categoryid,idtm)
) TYPE=MyISAM;

CREATE TABLE sr_controlcatalog (
  controlid int(10) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) default NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (controlid,idtm)
) TYPE=MyISAM;

CREATE TABLE sr_freqanswer (
  freqanswerid bigint(20) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(255) NOT NULL default '',
  stringxml blob,
  groupaid int(10) unsigned NOT NULL default '0',
  isreuse tinyint(3) unsigned NOT NULL default '0',
  created timestamp(14) NOT NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (freqanswerid,idtm)
) TYPE=MyISAM;

CREATE TABLE sr_groupanswer (
  groupaid int(10) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) default NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (groupaid,idtm)
) TYPE=MyISAM;

CREATE TABLE sr_orderquestion (
  surveyid bigint(20) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  questionid bigint(20) unsigned NOT NULL default '0',
  subjectid int(10) unsigned NOT NULL default '0',
  ordernum int(4) NOT NULL default '0',
  isactive tinyint(1) unsigned default '0',
  isdata tinyint(1) unsigned default '0',
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (surveyid,questionid,subjectid,idtm)
) TYPE=MyISAM;

CREATE TABLE sr_question (
  questionid bigint(20) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  question blob,
  instruction varchar(255) NOT NULL default '',
  codeid int(10) unsigned NOT NULL default '0',
  validated tinyint(1) default NULL,
  required tinyint(1) default NULL,
  validoptions tinyint(1) default NULL,
  controlid int(10) unsigned NOT NULL default '0',
  freqanswerid bigint(20) unsigned NOT NULL default '0',
  stringxml blob,
  categoryid int(10) unsigned NOT NULL default '0',
  isreuse tinyint(3) unsigned NOT NULL default '0',
  created timestamp(14) NOT NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (questionid,idtm),
  KEY ind_textid (codeid),
  KEY ind_controlid (controlid),
  KEY ind_freqanswerid (freqanswerid)
) TYPE=MyISAM;

CREATE TABLE sr_relatedquestion (
  parentquestion bigint(20) unsigned NOT NULL default '0',
  sonquestion bigint(20) unsigned NOT NULL default '0',
  surveyid bigint(20) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (parentquestion,sonquestion,surveyid,idtm)
) TYPE=MyISAM;

CREATE TABLE sr_responseuser (
  responseid bigint(20) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  surveyid bigint(20) unsigned NOT NULL default '0',
  wbuser varchar(50) NOT NULL default '',
  currentquestion tinyint(1) default NULL,
  correctanswer tinyint(1) default NULL,
  wronganswer tinyint(1) default NULL,
  score float unsigned zerofill default '000000000000',
  review tinyint(1) default NULL,
  statistic tinyint(1) default '1',
  comments blob,
  finished tinyint(1) default NULL,
  created timestamp(14) NOT NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (responseid,idtm),
  KEY ind_surveyid (surveyid),
  KEY ind_email (wbuser)
) TYPE=MyISAM;

CREATE TABLE sr_subject (
  subjectid int(10) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) default NULL,
  created timestamp(14) NOT NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (subjectid,idtm)
) TYPE=MyISAM;

CREATE TABLE sr_survey (
  surveyid bigint(20) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  res_id varchar(50) NOT NULL default '',
  typeid tinyint(1) unsigned NOT NULL default '0',
  min_score float unsigned zerofill default '000000000000',
  max_answer tinyint(3) unsigned NOT NULL default '0',
  time_answer int(4) unsigned default NULL,
  created timestamp(14) NOT NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (surveyid,idtm),
  KEY ind_res_id (res_id)
) TYPE=MyISAM;

CREATE TABLE sr_validatecode (
  codeid int(10) unsigned NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) NOT NULL default '',
  validationcode blob NOT NULL,
  lastupdate timestamp(14) NOT NULL,
  PRIMARY KEY  (codeid,idtm)
) TYPE=MyISAM;