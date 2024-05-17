DROP TABLE IF EXISTS Accounts CASCADE;
DROP TABLE IF EXISTS Blocks CASCADE;
DROP TABLE IF EXISTS Neighbors CASCADE;
DROP TABLE IF EXISTS Neighbor CASCADE;
DROP TABLE IF EXISTS Usermember CASCADE;
DROP TABLE IF EXISTS Friendship CASCADE;
DROP TABLE IF EXISTS Membership CASCADE;
DROP TABLE IF EXISTS neighborhoods CASCADE;
DROP TABLE IF EXISTS accountprofile CASCADE;
DROP TABLE IF EXISTS threads CASCADE;

CREATE TABLE Neighbors (
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
	accountname varchar,--build the check function make sure there is no same AccountName before sign up this accountName
	passwords varchar,
	email varchar,
	firstname varchar,
	lastname varchar,
	address varchar,
	latitude float,
    longitude float,
	block_bid int,
	PRIMARY KEY(accountname),
	FOREIGN KEY (block_bid) REFERENCES Blocks(bid)
);

Create Table Friendship(
	id int,
	username varchar,
	friendname varchar,
	Status char(10),
	PRIMARY KEY(id)
);

--once user sign up, it automatic require approve from the block memeber for be the member
Create Table Usermember(
	
	accountname varchar,
	membershipstatus varchar,
	bid int,
	FOREIGN KEY (accountname) REFERENCES Accounts,
	FOREIGN KEY (bid) REFERENCES Blocks
)

