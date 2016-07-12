DROP TABLE TBAA_USER;
DROP TABLE TBAB_AUTH;
DROP TABLE TBAC_AUTH_MAP;
DROP TABLE TBBA_BOARD_MANAGER;
DROP TABLE TBBB_BOARD_ARTICLE;
DROP TABLE TBCA_CTMEMO;
DROP TABLE TBCB_WORKSPACE;
DROP TABLE TBDA_NOTE_CATEGORY;
DROP TABLE TBDB_NOTE;
DROP TABLE TBGA_COMMENT;
DROP TABLE TBYA_ATTACH_FILE;

CREATE TABLE TBAA_USER( 
    USER_ID VARCHAR(20) NOT NULL, 
    NAME VARCHAR(50) NOT NULL, 
    PASSWD VARCHAR(50) NOT NULL, 
    EMAIL VARCHAR(100), 
    ADMIN_F VARCHAR(1) NOT NULL, 
    DELETE_F VARCHAR(1) NOT NULL,
    PRIMARY KEY (USER_ID)
);

CREATE TABLE TBAB_AUTH( 
    AUTH_SEQ INT NOT NULL, 
    NAME VARCHAR(100) NOT NULL, 
    URL VARCHAR(200) NOT NULL, 
    PARAMETER VARCHAR(100) NOT NULL,
    PRIMARY KEY (AUTH_SEQ) 
);

CREATE TABLE TBAC_AUTH_MAP( 
    AUTH_SEQ INT NOT NULL, 
    USER_ID VARCHAR(20) NOT NULL,
    PRIMARY KEY (AUTH_SEQ, USER_ID) 
);

CREATE TABLE TBBA_BOARD_MANAGER( 
    BOARD_CODE VARCHAR(20) NOT NULL, 
    NAME VARCHAR(50) NOT NULL, 
    UPLOAD_LIMIT INT NOT NULL, 
    REPLY_F VARCHAR(1) NOT NULL, 
    COMMENT_F VARCHAR(1) NOT NULL, 
    ATTACH_F VARCHAR(1) NOT NULL, 
    ENCODE_F VARCHAR(1) NOT NULL, 
    DELETE_F VARCHAR(1) NOT NULL,
    PRIMARY KEY (BOARD_CODE)
);

CREATE TABLE TBBB_BOARD_ARTICLE( 
    ARTICLE_SEQ INT NOT NULL, 
    BOARD_CODE VARCHAR(20) NOT NULL, 
    USER_ID VARCHAR(20), 
    IDX1 INT NOT NULL, 
    IDX2 INT NOT NULL, 
    IDX3 INT NOT NULL, 
    DEPTH_LEVEL INT NOT NULL, 
    TITLE VARCHAR(200) NOT NULL, 
    NAME VARCHAR(50) NOT NULL, 
    EMAIL VARCHAR(100), 
    PASSWD VARCHAR(10), 
    CONTENT TEXT NOT NULL, 
    IP VARCHAR(20) NOT NULL, 
    HIT_COUNT INT NOT NULL, 
    ENCODE_F VARCHAR(1) NOT NULL, 
    REG_DATE DATETIME NOT NULL, 
    DELETE_F VARCHAR(1) NOT NULL,
    PRIMARY KEY (ARTICLE_SEQ) 
);

CREATE TABLE TBGA_COMMENT( 
    COMMENT_SEQ INT NOT NULL, 
    MODULE_NAME VARCHAR(20) NOT NULL, 
    MODULE_ID VARCHAR(50) NOT NULL, 
    USER_ID VARCHAR(20) NOT NULL, 
    CONTENT VARCHAR(4000) NOT NULL, 
    REG_DATE DATETIME NOT NULL,
    PRIMARY KEY (COMMENT_SEQ) 
);

CREATE TABLE TBYA_ATTACH_FILE( 
    ATTACH_FILE_SEQ INT NOT NULL, 
    MODULE_NAME VARCHAR(20) NOT NULL, 
    MODULE_ID VARCHAR(50) NOT NULL, 
    USER_ID VARCHAR(20), 
    ORIGINAL_NAME VARCHAR(250) NOT NULL, 
    SAVE_NAME VARCHAR(250) NOT NULL, 
    SIZE INT NOT NULL, 
    REG_DATE DATETIME NOT NULL,
    PRIMARY KEY (ATTACH_FILE_SEQ) 
);

CREATE TABLE TBDB_NOTE( 
    NOTE_SEQ INT NOT NULL, 
    CATEGORY_SEQ INT NOT NULL, 
    TITLE VARCHAR(200) NOT NULL, 
    CONTENT TEXT NOT NULL, 
    UPT_DATE DATETIME NOT NULL, 
    REG_DATE DATETIME NOT NULL, 
    DELETE_F VARCHAR(1) NOT NULL,
    PRIMARY KEY (NOTE_SEQ) 
);

CREATE TABLE TBCB_WORKSPACE( 
    WORKSPACE_SEQ INT NOT NULL, 
    TITLE VARCHAR(200) NOT NULL,
    PRIMARY KEY (WORKSPACE_SEQ) 
);

CREATE TABLE TBDA_NOTE_CATEGORY( 
    CATEGORY_SEQ INT NOT NULL, 
    NAME VARCHAR(50) NOT NULL, 
    REG_DATE DATETIME NOT NULL, 
    DELETE_F VARCHAR(1) NOT NULL, 
    PARENT_ID INT NOT NULL,
    PRIMARY KEY (CATEGORY_SEQ) 
);

CREATE TABLE TBCA_CTMEMO( 
		CTMEMO_SEQ INT NOT NULL, 
		CONTENT VARCHAR(4000) NOT NULL, 
		FONT_CSS VARCHAR(20) NOT NULL, 
		BG_CSS VARCHAR(20) NOT NULL, 
		Z_INDEX INT NOT NULL, 
		WIDTH INT NOT NULL, 
		HEIGHT INT NOT NULL, 
		POSITION_X INT NOT NULL, 
		POSITION_Y INT NOT NULL, 
		UPT_DATE DATETIME NOT NULL, 
		REG_DATE DATETIME NOT NULL, 
		DELETE_F VARCHAR(1) NOT NULL, 
		WORKSPACE_SEQ INT NOT NULL,
		PRIMARY KEY (CTMEMO_SEQ) 
);

CREATE TABLE TBEA_KNOWLEDGE(
		KNOWLEDGE_SEQ INT NOT NULL,
		CLASSIFY_C VARCHAR(20) NOT NULL, 
		PROBLEM TEXT NOT NULL, 
		SOLUTION TEXT, 
		REG_DATE DATETIME NOT NULL,
		DELETE_F VARCHAR(1) NOT NULL,
		PRIMARY KEY (KNOWLEDGE_SEQ) 
);

CREATE TABLE TBYC_CODE(
		CODE_SEQ INT NOT NULL,
		MAJOR_CODE VARCHAR(20) NOT NULL, 
		MINOR_CODE VARCHAR(20) NOT NULL, 
		CODE_VALUE VARCHAR(100) NOT NULL, 
		ORDER_NO INT NOT NULL,
		PRIMARY KEY (CODE_SEQ) 
);

ALTER TABLE TBAC_AUTH_MAP ADD FOREIGN KEY(USER_ID) REFERENCES TBAA_USER(USER_ID);
ALTER TABLE TBDB_NOTE ADD FOREIGN KEY(CATEGORY_SEQ) REFERENCES TBDA_NOTE_CATEGORY(CATEGORY_SEQ);
ALTER TABLE TBDA_NOTE_CATEGORY ADD FOREIGN KEY(PARENT_ID) REFERENCES TBDA_NOTE_CATEGORY(CATEGORY_SEQ);
ALTER TABLE TBBB_BOARD_ARTICLE ADD FOREIGN KEY(BOARD_CODE) REFERENCES TBBA_BOARD_MANAGER(BOARD_CODE);
ALTER TABLE TBAC_AUTH_MAP ADD FOREIGN KEY(AUTH_SEQ) REFERENCES TBAB_AUTH(AUTH_SEQ);
ALTER TABLE TBCA_CTMEMO ADD FOREIGN KEY(WORKSPACE_SEQ) REFERENCES TBCB_WORKSPACE(WORKSPACE_SEQ);

INSERT INTO TBAA_USER(USER_ID, NAME, PASSWD, EMAIL, ADMIN_F, DELETE_F) VALUES('setvect', '문학소년', '81dc9bdb52d04dc20036dbd8313ed055', '', 'Y', 'N');

INSERT INTO TBAB_AUTH(AUTH_SEQ, NAME, URL, PARAMETER) VALUES(1, '게시판관리', '/app/board_manager/*', '*');
INSERT INTO TBAB_AUTH(AUTH_SEQ, NAME, URL, PARAMETER) VALUES(2, '게시판관리', '/app/board/write.do', '*');
INSERT INTO TBAB_AUTH(AUTH_SEQ, NAME, URL, PARAMETER) VALUES(3, '게시판관리', '/app/board/update.do', '*');
INSERT INTO TBAB_AUTH(AUTH_SEQ, NAME, URL, PARAMETER) VALUES(4, '게시판관리', '/app/board/delete.do', '*');
INSERT INTO TBAB_AUTH(AUTH_SEQ, NAME, URL, PARAMETER) VALUES(5, '이미지', '/image/upload.do', '*');
INSERT INTO TBAB_AUTH(AUTH_SEQ, NAME, URL, PARAMETER) VALUES(6, '복슬메모장', '/ctmemo/*', '*');
INSERT INTO TBAB_AUTH(AUTH_SEQ, NAME, URL, PARAMETER) VALUES(7, '복슬노트', '/note/*', '*');
INSERT INTO TBAB_AUTH(AUTH_SEQ, NAME, URL, PARAMETER) VALUES(8, '복슬지식방', '/knowledge/*', '*');

INSERT INTO TBCB_WORKSPACE(WORKSPACE_SEQ, TITLE) VALUES(1, '생활');
INSERT INTO TBCB_WORKSPACE(WORKSPACE_SEQ, TITLE) VALUES(2, '생각');
INSERT INTO TBCB_WORKSPACE(WORKSPACE_SEQ, TITLE) VALUES(3, '일');
INSERT INTO TBCB_WORKSPACE(WORKSPACE_SEQ, TITLE) VALUES(4, '잡동사니');

INSERT INTO TBDA_NOTE_CATEGORY(CATEGORY_SEQ, PARENT_ID, NAME, REG_DATE, DELETE_F) VALUES(0, 0, 'ROOT', now(), 'N');

INSERT INTO TBYC_CODE(CODE_SEQ, MAJOR_CODE, MINOR_CODE, CODE_VALUE, ORDER_NO) VALUES(0, '_TOP', 'ROOT', 'ROOT코드', 1);