INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES (1, 'd', 'd', 'd', 'd@d', CURRENT_TIMESTAMP());
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL, CREATE_DATE) VALUES (2, 'dd', 'dd', 'dd', 'dd@dd', CURRENT_TIMESTAMP());

INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATE_DATE, COUNT_OF_ANSWER) VALUES (1, 1, 'ㅇㅅㅇ 1', 'ㅇㅅㅇ 1', CURRENT_TIMESTAMP(), 2);
INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATE_DATE, COUNT_OF_ANSWER) VALUES (2, 2, 'ㅇㅅㅇ 2', 'ㅇㅅㅇ 2', CURRENT_TIMESTAMP(), 2);

INSERT INTO ANSWER (ID, QUESTION_ID, WRITER_ID, CONTENTS, CREATE_DATE) VALUES (1, 1, 1, 'ㅇㅅㅇ 1', CURRENT_TIMESTAMP());
INSERT INTO ANSWER (ID, QUESTION_ID, WRITER_ID, CONTENTS, CREATE_DATE) VALUES (2, 1, 2, 'ㅇㅅㅇ 2', CURRENT_TIMESTAMP());
INSERT INTO ANSWER (ID, QUESTION_ID, WRITER_ID, CONTENTS, CREATE_DATE) VALUES (3, 2, 1, 'ㅇㅅㅇ 1', CURRENT_TIMESTAMP());
INSERT INTO ANSWER (ID, QUESTION_ID, WRITER_ID, CONTENTS, CREATE_DATE) VALUES (4, 2, 2, 'ㅇㅅㅇ 2', CURRENT_TIMESTAMP());