CREATE TABLE roles (
                       id character varying(255) PRIMARY KEY,
                       name character varying(255) NOT NULL UNIQUE,
                       isdefault BOOLEAN DEFAULT FALSE
);

insert into roles (id, name, isdefault) values ('9df1a424-afe1-45bc-adf8-5c4aaf4fedf9','Developer',TRUE);
insert into roles (id, name, isdefault) values ('5c1e624-bda1-45bc-adf9-5c4ff4fedf9','Product Owner',FALSE);
insert into roles (id, name, isdefault) values ('8ae1e424-bda1-45bc-bde8-5c4aed4fedf9','Tester',FALSE);