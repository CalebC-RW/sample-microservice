DROP TABLE IF EXISTS PERSON;

CREATE TABLE PERSON (
	person_id varchar(40) NOT NULL,
	first_name varchar(200) NULL,
	last_name varchar(200) NOT NULL,
	CONSTRAINT PERSON_PK PRIMARY KEY (person_id)
);
