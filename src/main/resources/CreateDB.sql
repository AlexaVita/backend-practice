CREATE TABLE Sector(
	id bigserial PRIMARY KEY,
	name varchar(128),
	active boolean,
	signcode varchar(32),
	checkIp boolean,
	allowedIps varchar(1024)
);


CREATE TABLE SectorSettingsMap(
	id bigserial PRIMARY KEY,
	sector_id bigint ,
	name varchar(256),
	value varchar(1024),
	payment boolean,
	binList varchar(1024), --?
	guapPay boolean,
	
	FOREIGN KEY (sector_id) REFERENCES Sector(id)
);


CREATE TABLE Fee(
	id bigserial,
	sector_id bigint,
	paymentSystem varchar(64), --?
	percent varchar(32),
	fix varchar(32),
	notLess varchar(32),
	
	FOREIGN KEY (sector_id) REFERENCES Sector(id)
);


CREATE TABLE Operation(
	id bigserial PRIMARY KEY,
	sector_id bigint,
	date timestamp,
	amount numeric(19,0),
	fee numeric(19,0),
	description varchar(512),
	email varchar(128),
	state varchar(64),
	type varchar(64)
);