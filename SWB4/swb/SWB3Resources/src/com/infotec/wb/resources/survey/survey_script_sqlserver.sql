CREATE TABLE sr_answer (
  answerid bigint  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  questionid bigint  NOT NULL default '0',
  responseid bigint  NOT NULL default '0',
  stringxml text,
  score float  default '0',
  mark tinyint  default NULL,
  secuentialid tinyint  default '0',
  created datetime NOT NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (answerid,idtm)
) ;
CREATE INDEX ind_questionid
   ON sr_answer (questionid);
CREATE INDEX ind_responseid
   ON sr_answer (responseid);
CREATE TABLE sr_validatecode (
  codeid int  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) NOT NULL default '',
  validationcode text NOT NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (codeid,idtm)
) ;
CREATE TABLE sr_controlcatalog (
  controlid int  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) default NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (controlid,idtm)
) ;
CREATE TABLE sr_freqanswer (
  freqanswerid bigint  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(255) NOT NULL default '',
  stringxml text,
  groupaid int  NOT NULL default '0',
  isreuse tinyint  NOT NULL default '0',
  created datetime NOT NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (freqanswerid,idtm)
) ;
CREATE TABLE sr_groupanswer (
  groupaid int  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) default NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (groupaid,idtm)
) ;
CREATE TABLE sr_category (
  categoryid int  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) default NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (categoryid,idtm)
) ;
CREATE TABLE sr_orderquestion (
  surveyid bigint  NOT NULL default '0',
  questionid bigint  NOT NULL default '0',
  subjectid int  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  ordernum int NOT NULL default '0',
  isactive tinyint  default '0',
  isdata tinyint  default '0',
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (surveyid,questionid,subjectid,idtm)
) ;
CREATE TABLE sr_question (
  questionid bigint  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  question text,
  instruction varchar(255) NOT NULL default '',
  codeid int  NOT NULL default '0',
  validated tinyint default NULL,
  required tinyint default NULL,
  validoptions tinyint default NULL,
  controlid int  NOT NULL default '0',
  freqanswerid bigint  NOT NULL default '0',
  stringxml text,
  categoryid int  NOT NULL default '0',
  isreuse tinyint  NOT NULL default '0',
  created datetime NOT NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (questionid,idtm)
) ;
CREATE INDEX ind_textid
   ON sr_question (codeid);
CREATE INDEX ind_controlid
   ON sr_question (controlid);
CREATE INDEX ind_freqanswerid
   ON sr_question (freqanswerid);
CREATE TABLE sr_relatedquestion (
  parentquestion bigint  NOT NULL default '0',
  sonquestion bigint  NOT NULL default '0',
  surveyid bigint  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (parentquestion,sonquestion,surveyid,idtm)
) ;
CREATE TABLE sr_responseuser (
  responseid bigint  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  surveyid bigint  NOT NULL default '0',
  wbuser varchar(50) NOT NULL default '',
  currentquestion tinyint default NULL,
  correctanswer tinyint default NULL,
  wronganswer tinyint default NULL,
  score float  default '0',
  review tinyint default NULL,
  statistic tinyint default '1',
  comments text,
  finished tinyint default NULL,
  created datetime NOT NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (responseid,idtm)
) ;
CREATE INDEX ind_surveyid
   ON sr_responseuser (surveyid);
CREATE INDEX ind_email
   ON sr_responseuser (wbuser);
CREATE TABLE sr_subject (
  subjectid int  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  title varchar(50) NOT NULL default '',
  description varchar(255) default NULL,
  created datetime NOT NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (subjectid,idtm)
) ;
CREATE TABLE sr_survey (
  surveyid bigint  NOT NULL default '0',
  idtm varchar(50) NOT NULL default '',
  res_id varchar(50) NOT NULL default '',
  typeid tinyint  NOT NULL default '0',
  min_score float  default '0',
  max_answer tinyint  NOT NULL default '0',
  time_answer int  default NULL,
  created datetime NOT NULL,
  lastupdate datetime NOT NULL,
  PRIMARY KEY  (surveyid,idtm)
) ;
CREATE INDEX ind_res_id
   ON sr_survey (res_id);