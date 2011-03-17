CREATE TABLE sr_answer (
  answerid BIGINT  DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  questionid BIGINT  DEFAULT 0,
  responseid BIGINT  DEFAULT 0,
  stringxml LONGVARCHAR,
  score FLOAT  DEFAULT 0,
  mark TINYINT   NULL,
  secuentialid TINYINT DEFAULT 0,
  created TIMESTAMP not null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (answerid,idtm)
) ;
CREATE INDEX ind_questionid
   ON sr_answer (questionid);
CREATE INDEX ind_responseid
   ON sr_answer (responseid);
CREATE TABLE sr_validatecode (
  codeid INTEGER  DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  title VARCHAR(50) DEFAULT '',
  description VARCHAR(255) DEFAULT '',
  validationcode LONGVARCHAR not null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (codeid,idtm)
) ;
CREATE TABLE sr_controlcatalog (
  controlid INTEGER  DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  title VARCHAR(50) DEFAULT '',
  description VARCHAR(255)  DEFAULT null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (controlid,idtm)
) ;
CREATE TABLE sr_freqanswer (
  freqanswerid BIGINT DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  title VARCHAR(255) DEFAULT '',
  stringxml LONGVARCHAR,
  groupaid INTEGER  DEFAULT 0,
  isreuse TINYINT  DEFAULT 0,
  created TIMESTAMP not null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (freqanswerid,idtm)
) ;
CREATE TABLE sr_groupanswer (
  groupaid INTEGER DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  title VARCHAR(50) DEFAULT '',
  description VARCHAR(255)  DEFAULT null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (groupaid,idtm)
) ;
CREATE TABLE sr_category (
  categoryid INTEGER DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  title VARCHAR(50) DEFAULT '',
  description VARCHAR(255) DEFAULT null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (categoryid,idtm)
) ;
CREATE TABLE sr_orderquestion (
  surveyid BIGINT DEFAULT 0,
  questionid BIGINT DEFAULT 0,
  subjectid INTEGER DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  ordernum INTEGER DEFAULT 0,
  isactive TINYINT  DEFAULT 0,
  isdata TINYINT  DEFAULT 0,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (surveyid,questionid,subjectid,idtm)
) ;
CREATE TABLE sr_question (
  questionid BIGINT DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  question LONGVARCHAR,
  instruction VARCHAR(255) DEFAULT '',
  codeid INTEGER DEFAULT 0,
  validated TINYINT  DEFAULT NULL,
  required TINYINT  DEFAULT NULL,
  validoptions TINYINT  DEFAULT NULL,
  controlid INTEGER DEFAULT 0,
  freqanswerid BIGINT DEFAULT 0,
  stringxml LONGVARCHAR,
  categoryid INTEGER DEFAULT 0,
  isreuse TINYINT DEFAULT 0,
  created TIMESTAMP not null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (questionid,idtm)
) ;
CREATE INDEX ind_LONGVARCHARid
   ON sr_question (codeid);
CREATE INDEX ind_controlid
   ON sr_question (controlid);
CREATE INDEX ind_freqanswerid
   ON sr_question (freqanswerid);
CREATE TABLE sr_relatedquestion (
  parentquestion BIGINT DEFAULT 0,
  sonquestion BIGINT DEFAULT 0,
  surveyid BIGINT DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (parentquestion,sonquestion,surveyid,idtm)
) ;
CREATE TABLE sr_responseuser (
  responseid BIGINT DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  surveyid BIGINT  DEFAULT 0,
  wbuser VARCHAR(50) DEFAULT '',
  currentquestion TINYINT  NULL,
  correctanswer TINYINT  NULL,
  wronganswer TINYINT  NULL,
  score FLOAT  DEFAULT 0,
  review TINYINT  NULL,
  statistic TINYINT  DEFAULT 1,
  comments LONGVARCHAR,
  finished TINYINT DEFAULT NULL,
  created TIMESTAMP not null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (responseid,idtm)
) ;
CREATE INDEX ind_surveyid
   ON sr_responseuser (surveyid);
CREATE INDEX ind_email
   ON sr_responseuser (wbuser);
CREATE TABLE sr_subject (
  subjectid INTEGER DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  title VARCHAR(50) DEFAULT '',
  description VARCHAR(255) DEFAULT NULL,
  created TIMESTAMP not null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (subjectid,idtm)
) ;
CREATE TABLE sr_survey (
  surveyid BIGINT  DEFAULT 0,
  idtm VARCHAR(50) DEFAULT '',
  res_id VARCHAR(50) DEFAULT '',
  typeid TINYINT DEFAULT 0,
  min_score FLOAT  DEFAULT 0,
  max_answer TINYINT DEFAULT 0,
  time_answer INTEGER DEFAULT  NULL,
  created TIMESTAMP not null,
  lastupdate TIMESTAMP not null,
  PRIMARY KEY  (surveyid,idtm)
) ;
CREATE INDEX ind_res_id
   ON sr_survey (res_id);