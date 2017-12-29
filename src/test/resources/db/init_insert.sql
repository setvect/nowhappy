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
INSERT INTO TBDA_NOTE_CATEGORY(CATEGORY_SEQ, PARENT_ID, NAME, REG_DATE, DELETE_F) VALUES(1, 0, 'HaHa', now(), 'N');

INSERT INTO TBDB_NOTE(NOTE_SEQ, CATEGORY_SEQ, TITLE, CONTENT, UPT_DATE, REG_DATE, DELETE_F) VALUES(1, 1, '노트1', '내용', now(), now(), 'N');

INSERT INTO TBYC_CODE(CODE_SEQ, MAJOR_CODE, MINOR_CODE, CODE_VALUE, ORDER_NO) VALUES(0, '_TOP', 'ROOT', 'ROOT코드', 1);