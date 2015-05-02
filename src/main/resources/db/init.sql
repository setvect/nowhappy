DROP TABLE TBAA_USER;
DROP TABLE TBAA_CTMEMO;

CREATE TABLE TBAA_USER (
  USER_ID varchar(20) NOT NULL,
  NAME varchar(50) NOT NULL,
  PASSWD varchar(50) NOT NULL,
  EMAIL varchar(100),
	ADMIN_F varchar(1) NOT NULL,	 			  
	DELETE_F varchar(1) NOT NULL,	 			  
  PRIMARY KEY  (USER_ID)
);
INSERT INTO TBAA_USER(USER_ID, NAME, PASSWD, EMAIL, ADMIN_F, DELETE_F) VALUES('setvect', '문학소년', '81dc9bdb52d04dc20036dbd8313ed055', '', 'Y', 'N');

CREATE TABLE TBAA_CTMEMO (
	CTMEMO_SEQ int NOT NULL,
	CONTENT varchar(4000) NOT NULL,
	FONT_CSS varchar(20) NOT NULL,
	BG_CSS varchar(20) NOT NULL,
	Z_INDEX int NOT NULL,
	WIDTH int NOT NULL,
	HEIGHT int NOT NULL,
	POSITION_X int NOT NULL,
	POSITION_Y int NOT NULL,
	UPT_DATE datetime NOT NULL,
	REG_DATE datetime NOT NULL,
	DELETE_F varchar(1) NOT NULL,
	PRIMARY KEY  (CTMEMO_SEQ)
);

CREATE TABLE TBGA_COMMENT(
	COMMENT_SEQ		int NOT NULL,
	MODULE_NAME		varchar(20) NOT NULL,
	MODULE_ID			varchar(50) NOT NULL,		
	USER_ID				varchar(20) NOT NULL,
	CONTENT				varchar(4000) NOT NULL,
	REG_DATE			datetime NOT NULL,
  PRIMARY KEY  (COMMENT_SEQ)
)