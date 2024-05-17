DROP INDEX IF EXISTS idx_accounName;

select * from accounts;
select * from Friendship;
select * from Membership;
explain select * from accounts where accountname = 'sadf';
SELECT * from accountprofile
select * from blocks;
select * from neighbors;
select * from neighborhoods;
SELECT UserBlockGenerate();
CREATE INDEX idx_accounName ON Accounts(accountname);
CREATE INDEX idx_blocks_geo ON Blocks
USING GIST (ST_SetSRID(ST_MakePoint(longitude, latitude), 4326));

select * from Membership where blockid=14 and memberstatus='member'


Index Scan using accounts_pkey on accounts  (cost=0.15..8.17 rows=1 width=212)
Seq Scan on accounts  (cost=0.00..1.05 rows=1 width=212)