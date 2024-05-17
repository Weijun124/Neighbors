DROP TRIGGER AfterEnterAddress ON accounts;
DROP TRIGGER AutoMembership ON Membership;
DROP TRIGGER vetoAutoMembership ON Membership;
DROP TRIGGER AfterInsertAccount ON Accounts;

CREATE EXTENSION postgis;

-- For user block generating
CREATE OR REPLACE FUNCTION UserBlockGenerate()
RETURNS TRIGGER AS $$
DECLARE 
    user_point GEOGRAPHY; 
    userBlocks INT;
BEGIN
    user_point := ST_SetSRID(ST_MakePoint(NEW.Longitude, NEW.Latitude), 4326);

    SELECT INTO userBlocks bid
    FROM Blocks
    WHERE ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), 
        user_point, 
        radius
					 )
    LIMIT 1;

    IF userBlocks IS NOT NULL THEN
        NEW.bid := userBlocks; 
	ELSE
		RAISE NOTICE 'No block found for the coordinates (%, %)', NEW.Latitude, NEW.Longitude;
        NEW.bid := NULL;
	NEW.introduction:=NULL;
    END IF;

    RETURN NEW;  
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER AfterEnterAddress
BEFORE INSERT ON Accounts
FOR EACH ROW
EXECUTE FUNCTION UserBlockGenerate();






-- For User Membership (less than 3)
CREATE OR REPLACE FUNCTION Membershipauto()
RETURNS TRIGGER AS $$
DECLARE
    member_count INT;
BEGIN
    -- Count the number of members in the block
    SELECT COUNT(*) INTO member_count
    FROM Membership
    WHERE blockid = NEW.blockid AND memberstatus = 'member';

    -- If there are fewer than 3 members, set the new user's membership status to 'member'
    IF member_count < 3 AND THEN
        NEW.memberstatus := 'member';
    ELSE
        NEW.memberstatus := 'pending';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER AutoMembership
BEFORE INSERT ON Membership
FOR EACH ROW
EXECUTE FUNCTION Membershipauto();








-- For User Membership (vote)
CREATE OR REPLACE FUNCTION voteMembership()
RETURNS TRIGGER AS $$
DECLARE
    member_count INT;
BEGIN
    SELECT COUNT(*) INTO member_count
    FROM Membership
    WHERE blockid = NEW.blockid;
    IF member_count >= 3 THEN
        NEW.memberstatus := 'member';  
    ELSE
        NEW.memberstatus := 'pending'; 
	END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER vetoAutoMembership
BEFORE UPDATE ON Membership
FOR EACH ROW
EXECUTE FUNCTION voteMembership();  

