DROP TABLE IF EXISTS Accounts CASCADE;
DROP TABLE IF EXISTS Blocks CASCADE;
DROP TABLE IF EXISTS Neighbors CASCADE;
DROP TABLE IF EXISTS Neighbor CASCADE;
DROP TABLE IF EXISTS Friendship CASCADE;
DROP TABLE IF EXISTS Membership CASCADE;
DROP TABLE IF EXISTS neighborhoods CASCADE;
DROP TABLE IF EXISTS accountprofile CASCADE;
DROP TABLE IF EXISTS threads CASCADE;
DROP TABLE IF EXISTS blockfollow CASCADE;


CREATE TABLE Neighborhoods (
    nid int,
    name VARCHAR(255),
	PRIMARY KEY (nid)
);

CREATE TABLE Blocks (
    bid INT,
    nid INT,
    name VARCHAR(255),
    latitude float,
    longitude float,
    radius float, -- Radius in meters
	PRIMARY KEY (bid),
    FOREIGN KEY (nid) REFERENCES Neighbors
);
Create Table Accounts(
	accountname varchar,
	passwords varchar,
	email varchar,
	firstname varchar,
	lastname varchar,
	address varchar,
	latitude float,
    longitude float,
	block_bid int,
	introduction text,
	PRIMARY KEY(accountname),
	FOREIGN KEY (block_bid) REFERENCES Blocks(bid)
);

Create Table Friendship(
	id int,
	username varchar,
	friendname varchar,
	Status char(10),
	PRIMARY KEY(id),
	FOREIGN KEY (username) REFERENCES Accounts(accountname),
	FOREIGN KEY (friendname) REFERENCES Accounts(accountname)
);

Create Table Membership(
	blockid int,
	requesterid varchar,
	memberstatus varchar,
	requeststatus boolean,
	count int,
	FOREIGN KEY (requesterid) REFERENCES Accounts(accountname),
	FOREIGN KEY (blockid) REFERENCES Blocks(bid)
)

Create Table Blockfollow(
	accountname varchar,
	bid int,
	FOREIGN KEY (accountname) REFERENCES Accounts,
	FOREIGN KEY (bid) REFERENCES Blocks
)

Create Table Neighbors(
	userid varchar,
	neighborid varchar,
	FOREIGN KEY (userid) REFERENCES Accounts(accountname),
	FOREIGN KEY (neighborid) REFERENCES Accounts(accountname)
)

Create Table Threads(
	threadid int,
	blockid int,
	body text,
	feedType char[10],
	receiver varchar,
	subject varchar,
	time timestamp,
	accountname varchar,
	Primary Key (threadid),
	FOREIGN KEY (accountname) REFERENCES Accounts,
	FOREIGN KEY (receiver) REFERENCES Accounts(accountname)
)

Create Table Messages(
	InitialThreadID int,
	ReplyID int,
	Reply text,
	postTime timestamp,
	FOREIGN KEY(InitialThreadID) REFERENCES Threads(threadid),
	FOREIGN KEY(ReplyID) REFERENCES Accounts(Accountname)
);

Create Table UserReads(
	InitialThreadID int,
	AccountID int,
	LastRead timestamp,
	FOREIGN KEY(InitialThreadID) REFERENCES Threads(threadid),
	FOREIGN KEY(AccountID) REFERENCES Accounts(accountname)	
);

