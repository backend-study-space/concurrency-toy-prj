INSERT INTO USERS (USER_NAME, PASSWORD, AGE, USER_STATUS) VALUES ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 10, 'NORMAL');

INSERT INTO AUTHORITY (AUTHORITY_NAME) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) VALUES ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (ID, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');