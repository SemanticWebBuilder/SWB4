CREATE TABLE sr_answer(
  answerid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  questionid NUMBER(20) DEFAULT 0 NOT NULL,
  responseid NUMBER(20) DEFAULT 0 NOT NULL,
  stringxml LONG,
  score NUMBER(10) DEFAULT 0 NOT NULL,
  mark NUMBER(1) DEFAULT 0,
  secuentialid NUMBER(3) DEFAULT 0,
  created DATE NOT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_answer 
ADD (PRIMARY KEY (answerid,idtm));
CREATE INDEX ind_questionid ON sr_answer(
  questionid ASC);
CREATE INDEX ind_responseid ON sr_answer(
  responseid ASC);
CREATE TABLE sr_validatecode(
  codeid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  title VARCHAR2(50) DEFAULT ' ' NOT NULL,
  description VARCHAR2(255) DEFAULT ' ' NOT NULL,
  validationcode LONG,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_validatecode
ADD (PRIMARY KEY (codeid,idtm));
CREATE TABLE sr_controlcatalog(
  controlid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  title VARCHAR2(50) DEFAULT ' ' NOT NULL,
  description VARCHAR2(255) DEFAULT ' ' NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_controlcatalog
ADD (PRIMARY KEY (controlid,idtm));
CREATE TABLE sr_freqanswer(
  freqanswerid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  title VARCHAR2(255) DEFAULT ' ' NOT NULL,
  stringxml LONG,
  groupaid NUMBER(20) DEFAULT 0 NOT NULL,
  isreuse NUMBER(1) DEFAULT 0 NOT NULL,
  created DATE NOT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_freqanswer
ADD (PRIMARY KEY (freqanswerid,idtm));
CREATE TABLE sr_groupanswer(
  groupaid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  title VARCHAR2(50) DEFAULT ' ' NOT NULL,
  description VARCHAR2(255) DEFAULT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_groupanswer
ADD (PRIMARY KEY (groupaid,idtm));
CREATE TABLE sr_category (
  categoryid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  title VARCHAR2(50) DEFAULT ' ' NOT NULL,
  description VARCHAR2(255) DEFAULT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_category
ADD (PRIMARY KEY (categoryid,idtm));
CREATE TABLE sr_orderquestion(
  surveyid NUMBER(20) DEFAULT 0 NOT NULL,
  questionid NUMBER(20) DEFAULT 0 NOT NULL,
  subjectid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  ordernum NUMBER(4) DEFAULT 0 NOT NULL,
  isactive NUMBER(1) DEFAULT 0 NOT NULL,
  isdata NUMBER(1) DEFAULT 0 NOT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_orderquestion
ADD (PRIMARY KEY (surveyid,questionid,subjectid,idtm));
CREATE TABLE sr_question(
  questionid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  question varchar2(4000) DEFAULT ' ' NOT NULL,  
  instruction varchar2(255) DEFAULT NULL,               
  codeid NUMBER(20) DEFAULT 0 NOT NULL,
  validated NUMBER(1) DEFAULT 0 NOT NULL, 
  required NUMBER(1) DEFAULT 0 NOT NULL,
  validoptions NUMBER(10) DEFAULT 0 NOT NULL,
  controlid NUMBER(20) DEFAULT 0 NOT NULL,
  freqanswerid NUMBER(20) DEFAULT 0 NOT NULL,
  stringxml LONG,
  categoryid NUMBER(20) DEFAULT 0 NOT NULL,
  isreuse NUMBER(1) DEFAULT 0 NOT NULL,
  created DATE NOT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_question 
ADD (PRIMARY KEY (questionid,idtm));
CREATE INDEX ind_textid ON sr_question(
  codeid ASC);
CREATE INDEX ind_controlid ON sr_question(
  controlid ASC);
CREATE INDEX ind_freqanswerid ON sr_question(
  freqanswerid ASC);
CREATE TABLE sr_relatedquestion(
  parentquestion NUMBER(20) DEFAULT 0 NOT NULL,
  sonquestion NUMBER(20) DEFAULT 0 NOT NULL,
  surveyid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_relatedquestion 
ADD (PRIMARY KEY (parentquestion,sonquestion,surveyid,idtm));
CREATE TABLE sr_responseuser(   
  responseid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  surveyid NUMBER(20) DEFAULT 0 NOT NULL,
  wbuser VARCHAR2(50) DEFAULT ' ' NOT NULL,
  currentquestion NUMBER(10) DEFAULT 0 NOT NULL,
  correctanswer NUMBER(10) DEFAULT 0 NOT NULL,
  wronganswer NUMBER(10) DEFAULT 0 NOT NULL,
  score NUMBER(10) DEFAULT 0 NOT NULL,
  review NUMBER(1) DEFAULT 0 NOT NULL,
  statistic NUMBER(1) DEFAULT 1 NOT NULL,
  comments varchar2(4000) DEFAULT NULL,
  finished NUMBER(1) DEFAULT 0 NOT NULL,
  created DATE NOT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_responseuser
ADD (PRIMARY KEY (responseid,idtm));
CREATE INDEX ind_surveyid ON sr_responseuser(
  surveyid ASC);
CREATE INDEX ind_email ON sr_responseuser(
  wbuser ASC);
CREATE TABLE sr_subject(
  subjectid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  title VARCHAR2(50) DEFAULT ' ' NULL,
  description VARCHAR2(255) DEFAULT NULL,
  created DATE NOT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_subject 
ADD (PRIMARY KEY (subjectid,idtm));
CREATE TABLE sr_survey(
  surveyid NUMBER(20) DEFAULT 0 NOT NULL,
  idtm VARCHAR(50) DEFAULT ' ' NOT NULL,
  res_id VARCHAR(50) DEFAULT ' ' NOT NULL,
  typeid NUMBER(10) DEFAULT 0 NOT NULL,
  min_score DECIMAL(10,2) DEFAULT NULL,
  max_answer NUMBER(3) DEFAULT 0,
  time_answer NUMBER(4) DEFAULT NULL,
  created DATE NOT NULL,
  lastupdate DATE NOT NULL);
ALTER TABLE sr_survey 
ADD (PRIMARY KEY (surveyid,idtm));
CREATE INDEX ind_res_id ON sr_survey(
  res_id ASC);