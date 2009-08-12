create table sr_answer 
(
  answerid numeric (20)  not null ,
  idtm varchar(50) not null ,
  questionid numeric (20)  not null ,
  responseid numeric (20)  not null ,
  stringxml text null,
  score float  default 0,
  mark numeric (3)  null,
  secuentialid numeric (3)  default 0,
  created datetime not null,
  lastupdate datetime not null
) ;
alter table sr_answer 
	add
		primary key (answerid,idtm);
create index ind_questionid
   on sr_answer (questionid);
create index ind_responseid
   on sr_answer (responseid);
create table sr_validatecode 
(
  codeid numeric (10) not null ,
  idtm varchar(50) not null ,
  title varchar(50) not null ,
  description varchar(255) not null ,
  validationcode text not null,
  lastupdate datetime not null
) ;
alter table sr_validatecode
	add
		primary key (codeid,idtm) ; 
create table sr_controlcatalog 
(
  controlid numeric (10) not null ,
  idtm varchar(50) not null ,
  title varchar(50) not null ,
  description varchar(255) null,
  lastupdate datetime not null
) ;
alter table sr_controlcatalog
	add
		primary key (controlid,idtm) ; 
create table sr_freqanswer (
  freqanswerid numeric (20)  not null ,
  idtm varchar(50) not null ,
  title varchar(255) not null ,
  stringxml text null,
  groupaid numeric (10) not null ,
  isreuse numeric (3)  default 0 ,
  created datetime not null,
  lastupdate datetime not null
) ;
alter table sr_freqanswer
	add
		primary key (freqanswerid,idtm) ; 
create table sr_groupanswer (
  groupaid numeric (10) not null ,
  idtm varchar(50) not null ,
  title varchar(50) not null ,
  description varchar(255) null,
  lastupdate datetime not null
) ;
alter table sr_groupanswer
	add
		primary key (groupaid,idtm) ; 
create table sr_category (
  categoryid numeric (10) not null ,
  idtm varchar(50) not null ,
  title varchar(50) not null ,
  description varchar(255) null,
  lastupdate datetime not null
) ;
alter table sr_category
	add
		primary key (categoryid,idtm) ; 
create table sr_orderquestion (
  surveyid numeric (20)  not null ,
  questionid numeric (20)  not null ,
  subjectid numeric (10) not null ,
  idtm varchar(50) not null ,
  ordernum numeric (10)not null ,
  isactive numeric (3) default 0 ,
  isdata numeric (3) default 0 ,
  lastupdate datetime not null
) ;
alter table sr_orderquestion
	add
		primary key (surveyid,questionid,subjectid,idtm) ; 
create table sr_question (
  questionid numeric (20)  not null ,
  idtm varchar(50) not null ,
  question text null,
  instruction varchar(255) not null ,
  codeid numeric(10) not null ,
  validated numeric (3) null,
  required numeric (3) null,
  validoptions numeric (3) null,
  controlid numeric (10) not null ,
  freqanswerid numeric (20)  not null ,
  stringxml text null,
  categoryid numeric (10) not null ,
  isreuse numeric (3)  default 0 ,
  created datetime not null,
  lastupdate datetime not null
) ;
alter table sr_question
	add
		primary key (questionid,idtm) ; 
create index ind_textid
   on sr_question (codeid);
create index ind_controlid
   on sr_question (controlid);
create index ind_freqanswerid
   on sr_question (freqanswerid);
create table sr_relatedquestion (
  parentquestion numeric (20)  not null ,
  sonquestion numeric (20)  not null ,
  surveyid numeric (20)  not null ,}
  idtm varchar(50) not null ,
  lastupdate datetime not null
) ;
alter table sr_relatedquestion
	add
		primary key (parentquestion,sonquestion,surveyid,idtm) ; 
create table sr_responseuser (
  responseid numeric (20)  not null ,
  idtm varchar(50) not null ,
  surveyid numeric (20)  not null ,
  wbuser varchar (50) not null ,
  currentquestion numeric (3) null,
  correctanswer numeric (3) null,
  wronganswer numeric (3) null,
  score float  default 0,
  review numeric (3) null,
  statistic numeric (3) default 1,
  comments text null,
  finished numeric (3) default null,
  created datetime not null,
  lastupdate datetime not null
) ;
alter table sr_responseuser
	add
		primary key (responseid, idtm) ; 
create index ind_surveyid
   on sr_responseuser (surveyid);
create index ind_email
   on sr_responseuser (wbuser);
create table sr_subject (
  subjectid numeric (10) not null ,
  idtm varchar(50) not null ,
  title varchar(50) not null ,
  description varchar(255) null,
  created datetime not null,
  lastupdate datetime not null
) ;
alter table sr_subject
	add
		primary key (subjectid,idtm) ; 
create table sr_survey (
  surveyid numeric (20)  not null ,
  idtm varchar(50) not null ,
  res_id varchar(50) not null ,
  typeid numeric (3)  not null ,
  min_score float default 0 ,
  max_answer numeric (3)  default 0 ,
  time_answer numeric (10) null,
  created datetime not null,
  lastupdate datetime not null
) ;
alter table sr_survey
	add
		primary key (surveyid,idtm) ; 
create index ind_res_id
   on sr_survey (res_id);