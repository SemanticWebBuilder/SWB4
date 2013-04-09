create table resrepository 
(
  rep_docId numeric(20) default 0 not null,
  resId varchar(255) default "0" not null,
  idtm varchar(50) not null,
  topic varchar(101) not null ,
  rep_email varchar(99) null,
  rep_title varchar(99) null,
  rep_description varchar(255) null,
  rep_lastVersion numeric(10) null,
  rep_xml text null,
  rep_status numeric(3) null,
  rep_emailCheckOut varchar(99) null,
  rep_deleted numeric(3) null,
  rep_create datetime(14) not null
);

alter table resrepository 
	add
		primary key (rep_docId,idtm) ;
		
create table resrepositorylog 
(
  rep_docId numeric(20) default 0 not null,
  rep_action varchar(15) null,
  rep_email varchar(99) null,
  rep_user varchar(50) null,
  rep_name varchar(255) default "" not null,
  rep_topicid varchar(50) null,
  rep_topicmapid varchar(50) null,
  rep_objectid numeric(20) default 0 not null,
  rep_dateaction datetime(14) not null,
  rep_description varchar(255) null,
  rep_isfile numeric(3) default 0 not null,
  rep_ipuser varchar(15) default "127.0.0.1"
);

create table resrepositorynotify 
(
  rep_docId numeric(20) null,
  idtm varchar(50) not null,
  topic varchar(101) null,
  rep_email varchar(99) null,
  rep_role varchar(50) default "0"
);

alter table resrepositorynotify 
	add
		primary key (rep_docId,idtm,topic) ;

create table resrepositoryversions 
(
  rep_docId numeric(20) default 0 not null,
  rep_fileVersion numeric(3) default 0 not null,
  idtm varchar(50) not null,
  rep_email varchar(99) null,
  rep_fileName varchar(255) default "" not null,
  rep_fileSize numeric(20) default 0 not null,
  rep_fileDate datetime(14) not null,
  rep_fileType varchar(100) null,
  rep_comment varchar(255) null,
  rep_create datetime(14) not null
);

alter table resrepositoryversions 
	add
		primary key (rep_docId,rep_fileVersion,idtm) ;