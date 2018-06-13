DROP TABLE ISSUE IF EXISTS ;

CREATE TABLE ISSUE
(
  ID integer AUTO_INCREMENT PRIMARY KEY NOT NULL,
  CREATIONDATE date NOT NULL,
  DESCRIPTION clob NOT NULL,
  STATE varchar(255) NOT NULL,
  TITLE varchar(255) NOT NULL,
  AUTHOR varchar(255)
);

DROP TABLE COMMENT IF EXISTS ;

CREATE TABLE COMMENT
(
  ID integer AUTO_INCREMENT PRIMARY KEY NOT NULL,
  CREATIONDATE timestamp NOT NULL,
  TEXT clob NOT NULL,
  AUTHOR varchar(255) NOT NULL,
  ISSUEID integer NOT NULL,
  TRANSITION varchar(255),
  CONSTRAINT COMMENTS_ISSUE_ID_FK FOREIGN KEY (ISSUEID) REFERENCES ISSUE (ID) ON DELETE CASCADE,
  CONSTRAINT FK13S10FAUDTMUGRJX6UCXO0624 FOREIGN KEY (ISSUEID) REFERENCES ISSUE (ID)
);


DROP TABLE USER IF EXISTS ;

CREATE TABLE USER
(
  USERID integer  AUTO_INCREMENT PRIMARY KEY NOT NULL,
  USERNAME varchar(255) NOT NULL,
  HASHED_PASSWORD varchar(255) NOT NULL,
  SALT varchar(255) NOT NULL
);
CREATE UNIQUE INDEX USER_USERNAME_UINDEX ON USER (USERNAME);