CREATE TABLE sr_answer (
  answerid INTEGER  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  questionid INTEGER  DEFAULT 0 NOT NULL,
  responseid INTEGER  DEFAULT 0 NOT NULL,
  stringxml TEXT IN TABLE,
  score REAL  DEFAULT 0 NOT NULL,
  mark SMALLINT,
  secuentialid SMALLINT  DEFAULT 0 NOT NULL,
  created TIMESTAMP NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (answerid,idtm)
) ;
CREATE INDEX ind_Qid_Rid ON sr_answer (questionid,responseid);

CREATE TABLE sr_validatecode (
  codeid SMALLINT  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  title VARCHAR(50) DEFAULT '' NOT NULL,
  description VARCHAR(255) DEFAULT '' NOT NULL,
  validationcode TEXT IN TABLE NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (codeid,idtm)
) ;
CREATE TABLE sr_controlcatalog (
  controlid SMALLINT  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  title VARCHAR(50) DEFAULT '' NOT NULL,
  description VARCHAR(255) ,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (controlid,idtm)
) ;
CREATE TABLE sr_freqanswer (
  freqanswerid INTEGER  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  title VARCHAR(255) DEFAULT '' NOT NULL,
  stringxml TEXT IN TABLE,
  groupaid SMALLINT  DEFAULT 0 NOT NULL,
  isreuse SMALLINT  DEFAULT 0 NOT NULL,
  created TIMESTAMP NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (freqanswerid,idtm)
) ;
CREATE TABLE sr_groupanswer (
  groupaid SMALLINT  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  title VARCHAR(50) DEFAULT '' NOT NULL,
  description VARCHAR(255) ,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (groupaid,idtm)
) ;
CREATE TABLE sr_category (
  categoryid SMALLINT  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  title VARCHAR(50) DEFAULT '' NOT NULL,
  description VARCHAR(255) ,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (categoryid,idtm)
) ;
CREATE TABLE sr_orderquestion (
  surveyid INTEGER  DEFAULT 0 NOT NULL,
  questionid INTEGER  DEFAULT 0 NOT NULL,
  subjectid SMALLINT  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  ordernum SMALLINT DEFAULT 0 NOT NULL,
  isactive SMALLINT  DEFAULT 0 NOT NULL,
  isdata SMALLINT  DEFAULT 0 NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (surveyid,questionid,subjectid,idtm)
) ;
CREATE TABLE sr_question (
  questionid INTEGER  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  question TEXT IN TABLE,
  instruction VARCHAR(255) DEFAULT '' NOT NULL,
  codeid SMALLINT  DEFAULT 0 NOT NULL,
  validated SMALLINT ,
  required SMALLINT ,
  validoptions SMALLINT ,
  controlid SMALLINT  DEFAULT 0 NOT NULL,
  freqanswerid INTEGER  DEFAULT 0 NOT NULL,
  stringxml TEXT IN TABLE,
  categoryid SMALLINT  DEFAULT 0 NOT NULL,
  isreuse SMALLINT  DEFAULT 0 NOT NULL,
  created TIMESTAMP NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (questionid,idtm)
) ;
CREATE INDEX ind_Tid_Cid_FAid ON sr_question (codeid,controlid,freqanswerid);

CREATE TABLE sr_relatedquestion (
  parentquestion INTEGER  DEFAULT 0 NOT NULL,
  sonquestion INTEGER  DEFAULT 0 NOT NULL,
  surveyid INTEGER  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (parentquestion,sonquestion,surveyid,idtm)
) ;
CREATE TABLE sr_responseuser (
  responseid INTEGER  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  surveyid INTEGER  DEFAULT 0 NOT NULL,
  wbuser VARCHAR(50) DEFAULT '' NOT NULL,
  currentquestion SMALLINT ,
  correctanswer SMALLINT ,
  wronganswer SMALLINT ,
  score REAL  DEFAULT 0 NOT NULL,
  review SMALLINT ,
  statistic SMALLINT DEFAULT '1',
  comments TEXT IN TABLE,
  finished SMALLINT ,
  created TIMESTAMP NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (responseid,idtm)
) ;
CREATE INDEX ind_Sid_Email ON sr_responseuser (surveyid,wbuser);
CREATE TABLE sr_subject (
  subjectid SMALLINT  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  title VARCHAR(50) DEFAULT '' NOT NULL,
  description VARCHAR(255) ,
  created TIMESTAMP NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (subjectid,idtm)
) ;
CREATE TABLE sr_survey (
  surveyid INTEGER  DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT '' NOT NULL,
  res_id VARCHAR(50) DEFAULT '' NOT NULL,
  typeid SMALLINT  DEFAULT 0 NOT NULL,
  min_score REAL  DEFAULT 0 NOT NULL,
  max_answer SMALLINT  DEFAULT 0 NOT NULL,
  time_answer SMALLINT  ,
  created TIMESTAMP NOT NULL,
  lastupdate TIMESTAMP NOT NULL,
  PRIMARY KEY  (surveyid,idtm)
) ;
CREATE INDEX ind_Rid ON sr_survey (res_id);