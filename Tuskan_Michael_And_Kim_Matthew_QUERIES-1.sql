/****************************************************/
/* Michael Tuskan & Matthew Kim                     */
/* TCSS 445 A - Spring                              */
/* Intermidate Project Submission                   */
/* 05-31-2023                                       */
/*                                                  */
/****************************************************/

/*  Create Database *******************************************************************************************************************/
Create DataBase FORAGERDB;
GO 


USE FORAGERDB;
GO

/*  Create Tables *******************************************************************************************************************/
CREATE TABLE THE_DATE (
	DateID	    Integer		NOT NULL IDENTITY(1,1),  /*PK*/
	TheMonth	Integer		NULL, 
	TheDay		Integer		NULL,
	TheYear	    Integer		NULL,
	PRIMARY KEY (DateID),
    );

CREATE TABLE THE_TIME(
	TimeID     Integer NOT NULL IDENTITY(1,1), /*PK*/
	TheHours   Integer NULL,      
	TheMinutes Integer NULL,
	TheSeconds Integer NULL,
	PRIMARY KEY (TimeID),
    );

CREATE TABLE TERRAIN(
	TerrainID   Integer   NOT NULL IDENTITY(1,1), /*PK*/
	TerrainType Char (10) NULL,
	PRIMARY KEY (TerrainID),
    );

CREATE TABLE PHOTO(
	PhotoID          Integer         NOT NULL IDENTITY(1,1), /*PK*/
	PhotoFile        VARBINARY(MAX)  NULL,
	PhotoSize        Integer         NULL,
	SpeciesType      Char (20)       NULL,
	PhotoDescription Char (40)       NULL,
	PRIMARY KEY (PhotoID),
    );

CREATE TABLE SEASON(
	SeasonID    Integer   NOT NULL IDENTITY(1,1), /*PK*/
	SeasonType  Char(20) NULL,
	PRIMARY KEY (SeasonID),
	);

CREATE TABLE THE_LOCATION(
	LocationID Integer  NOT NULL IDENTITY(1,1),   /*PK*/
	Longitude  Float    NULL,
	Latitude   Float    NULL,
	PRIMARY KEY (LocationID),
    );


CREATE TABLE THE_LOGIN(
	LoginID   Integer    NOT NULL IDENTITY(1,1), /*PK*/
	Username  Char (20)  NULL,                 
	PRIMARY KEY (LoginID),
    );

CREATE TABLE EXPERTISE (
	ExpertiseID     Integer  NOT NULL IDENTITY(1,1), /*PK*/
	ExperienceLevel Integer  NULL,
	PRIMARY KEY (ExpertiseID),
	);

CREATE TABLE CONTACT (
ContactID			Integer			NOT NULL IDENTITY(1,1),
FirstName			Char(50)		NOT NULL,
LastName			Char(50)		NOT NULL,
Email				Char(50)		NULL,
PhoneNumber			Char(50)		NULL,
CONSTRAINT			ContactID_PK	PRIMARY KEY (ContactID)
);

CREATE TABLE BIOGRAPHY (
BiographyID			Integer			NOT NULL IDENTITY(1,1),
BioText				Char(500)		NOT NULL,
CONSTRAINT			BiographyID_PK	PRIMARY KEY (BiographyID)
);

CREATE TABLE FORAGER(  
	ForagerID        Integer   NOT NULL IDENTITY(1,1), /*PK*/
	Age              Integer   NULL,
	ContactID        Integer   NULL,
	FieldJournalID   Integer   NULL,
	LoginID          Integer   NULL,
	ExpertiseID      Integer   NULL,
	BiographyID		 Integer    NULL,	
	PRIMARY KEY (ForagerID),
	FOREIGN KEY (ExpertiseID) REFERENCES EXPERTISE(ExpertiseID)
		ON UPDATE NO ACTION
		ON DELETE NO ACTION,
	FOREIGN KEY (LoginID) REFERENCES THE_LOGIN(LoginID)
		ON UPDATE NO ACTION
		ON DELETE NO ACTION,
	FOREIGN KEY (ContactID) REFERENCES CONTACT(ContactID)
		ON UPDATE NO ACTION
		ON DELETE NO ACTION,
	FOREIGN KEY (BiographyID) REFERENCES BIOGRAPHY(BiographyID)
		ON UPDATE NO ACTION
		ON DELETE NO ACTION,
    ); 

CREATE TABLE FIELD_JOURNAL(
	FieldJournalID  Integer  NOT NULL IDENTITY(1,1), /*PK*/
	ForagerID       Integer  NOT NULL,
	PRIMARY KEY (FieldJournalID),
	FOREIGN KEY (ForagerID) REFERENCES FORAGER(ForagerID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
    );


CREATE TABLE KINGDOM (                                                                      
KingdomID			Integer			NOT NULL IDENTITY(1,1),
KingdomName			Char(20)		NOT NULL,
CONSTRAINT			KingdomID_PK	PRIMARY KEY (KingdomID)
);

CREATE TABLE PLANT_SPECIES(                                                         
KingdomID			Integer						NOT NULL,
PlantSpeciesID		Integer					    NOT NULL IDENTITY(1,1),
PlantSpeciesName    Char(30)                    NOT NULL,
EdibleStatus		Char(10)					NULL,
CONSTRAINT			PlantSpeciesID_PK			PRIMARY KEY (PlantSpeciesID),
CONSTRAINT			PS_Kingdom_Relationship		FOREIGN KEY (KingdomID)
				REFERENCES	KINGDOM (KingdomID),
);

CREATE TABLE MUSHROOM_SPECIES(                                                        
KingdomID			Integer			NOT NULL,
MushroomSpeciesID	Integer	        NOT NULL IDENTITY(1,1),
MushroomSpeciesName Char(30)        NOT NULL,
EdibleStatus		Char(10)		NULL,
CONSTRAINT			MushroomSpeciesID_PK		PRIMARY KEY (MushroomSpeciesID),
CONSTRAINT			MS_Kingdom_Relationship		FOREIGN KEY (KingdomID)
				REFERENCES	KINGDOM (KingdomID),
);

CREATE TABLE PLANT(                                                                 
KingdomID			Integer			NOT NULL,
PlantSpeciesID		Integer			NOT NULL,
PlantID				Integer			NOT NULL IDENTITY(1,1),
PlantName			Char(50)		NOT NULL,
PlantType           Char(20)        NOT NULL,
SizeID				Integer			NOT NULL,				
LifeHistory         Char(20)        NOT NULL,
CONSTRAINT			PlantID_PK					        PRIMARY KEY (PlantID),
CONSTRAINT			Mushroom_Kingdom_Relationship1		FOREIGN KEY (KingdomID)
				REFERENCES	KINGDOM (KingdomID),
CONSTRAINT			Plant_Species_Relationship		    FOREIGN KEY (PlantSpeciesID)
				REFERENCES	PLANT_SPECIES (PlantSpeciesID),
);

CREATE TABLE MUSHROOM (                                                                     
KingdomID			Integer			NOT NULL,
MushroomSpeciesID	Integer			NOT NULL,
MushroomID			Integer			NOT NULL IDENTITY(1,1),
MushroomName		Char(50)		NOT NULL,
Stem				Char(50)		NOT NULL,
Cap					Char(50)		NOT NULL,
Gill				Char(50)		NOT NULL,
SporeColor			Char(50)		NOT NULL,
CONSTRAINT			MushroomID_PK					    PRIMARY KEY (MushroomID),
CONSTRAINT			Mushroom_Kingdom_Relationship		FOREIGN KEY (KingdomID)
				REFERENCES	KINGDOM (KingdomID),
CONSTRAINT			Mushroom_Species_Relationship		FOREIGN KEY (MushroomSpeciesID)
				REFERENCES	MUSHROOM_SPECIES (MushroomSpeciesID),
); 

CREATE TABLE SIZE(
SizeID	Int		NOT NULL IDENTITY(1,1),
Height	Int		NOT NULL,
Width	Int		NOT NULL,
CONSTRAINT		SizeID_PK		PRIMARY KEY(SizeID)
);

CREATE TABLE JOURNAL_PAGE(                                                                       
	ForagerID      Integer    NOT NULL,
	FieldJournalID Integer    NOT NULL IDENTITY(1,1),  /*PK*/              
	JournalPageID  Integer    NOT NULL,               
	TheDate	       Integer    NULL,
	TheTime        Integer    NULL,
	Comments       Char (20)  NULL,
	TheLocation    Integer    NULL,
	Photo		   Integer    NULL,
	Season		   Integer    NULL,
	Terrain        Integer    NULL,
	Mushroom       Integer    NULL,
	Plant          Integer    NULL,
	PRIMARY KEY (FieldJournalID),
	FOREIGN KEY (ForagerID) REFERENCES FORAGER(ForagerID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (FieldJournalID) REFERENCES FIELD_JOURNAL(FieldJournalID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (TheDate) REFERENCES THE_DATE(DateID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (TheTime) REFERENCES THE_TIME(TimeID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (TheLocation) REFERENCES THE_LOCATION(LocationID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (Photo) REFERENCES PHOTO(PhotoID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (Season) REFERENCES SEASON(SeasonID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (Terrain) REFERENCES TERRAIN(TerrainID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (Mushroom) REFERENCES MUSHROOM(MushroomID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
	FOREIGN KEY (Plant) REFERENCES PLANT(PlantID)
			ON UPDATE NO ACTION
			ON DELETE NO ACTION,
    );

/* Scenario Queries *******************************************************************************************************************/

--1. Find a Mushroom Species
SELECT M.MushroomName, MS.MushroomSpeciesName
	FROM MUSHROOM M, MUSHROOM_SPECIES MS
	WHERE MS.MushroomSpeciesName= 'Pleurotus Ostreatus' and MS.MushroomSpeciesID = M.MushroomID;
	

--2. Find a Plant Species
SELECT P.PlantName, PS.PlantSpeciesName
	FROM PLANT P, PLANT_SPECIES PS
	WHERE PS.PlantSpeciesName= 'Prunus persica' and PS.PlantSpeciesID = P.PlantID;

--3. Forager Login, shows connection with forger and login
SELECT F.ForagerID, L.Username
FROM FORAGER F
INNER JOIN THE_LOGIN L ON F.LoginID = L.LoginID;

--4. Journal Page, shows journal entries with data from other tables
SELECT
    JP.JournalPageID,
    JP.TheDate,
	    (
        SELECT TD.TheMonth
        FROM THE_DATE TD
        WHERE TD.DateID = JP.TheDate
    ) AS TheMonth,
    (
        SELECT TD.TheDay
        FROM THE_DATE TD
        WHERE TD.DateID = JP.TheDate
    ) AS TheDay,
    (
        SELECT TD.TheYear
        FROM THE_DATE TD
        WHERE TD.DateID = JP.TheDate
    ) AS TheYear,
    (
        SELECT TT.TheHours
        FROM THE_TIME TT
        WHERE TT.TimeID = JP.TheTime
    ) AS TheHours,
    (
        SELECT TT.TheMinutes
        FROM THE_TIME TT
        WHERE TT.TimeID = JP.TheTime
    ) AS TheMinutes,
    (
        SELECT TT.TheSeconds
        FROM THE_TIME TT
        WHERE TT.TimeID = JP.TheTime
    ) AS TheSeconds,
    JP.Comments,
	(
		SELECT TL.Longitude
		FROM THE_LOCATION TL
		WHERE TL.LocationID = JP.TheLocation
	) 
	AS TheLongtiude,
	(
		SELECT TL.Latitude
		FROM THE_LOCATION TL
		WHERE TL.LocationID = JP.TheLocation
	) AS TheLatitude,	(
		SELECT P.PhotoDescription
		FROM PHOTO P
		WHERE P.PhotoID = JP.Photo
	) AS PhotoDescription,  (
		SELECT P.PhotoFile
		FROM PHOTO P
		WHERE P.PhotoID = JP.Photo
	) AS PhotoFile, (
		SELECT P.PhotoSize
		FROM PHOTO P
		WHERE P.PhotoID = JP.Photo
	) AS PhotoSize,  (
		SELECT P.SpeciesType
		FROM PHOTO P
		WHERE P.PhotoID = JP.Photo
	) AS SpeciesType,
    (
        SELECT SeasonType
        FROM SEASON
        WHERE SeasonID = JP.Season
    ) AS SeasonName,
    (
        SELECT TerrainType
        FROM TERRAIN
        WHERE TerrainID = JP.Terrain
    ) AS TerrainType,
    (
        SELECT MushroomName
        FROM MUSHROOM M
        WHERE MushroomID = JP.Mushroom 
    ) AS MushroomName,
	    (
        SELECT Gill
        FROM MUSHROOM M
        WHERE MushroomID = JP.Mushroom
    ) AS Gill,
	    (
        SELECT Stem
        FROM MUSHROOM M
        WHERE MushroomID = JP.Mushroom
    ) AS Stem,
		(
        SELECT Cap
        FROM MUSHROOM M
        WHERE MushroomID = JP.Mushroom
    ) AS Cap,
			(
        SELECT SporeColor
        FROM MUSHROOM M
        WHERE MushroomID = JP.Mushroom
    ) AS SporeColor,
    (
        SELECT PlantName
        FROM PLANT 
        WHERE PlantID = JP.Plant
    ) AS PlantName,
	    (
        SELECT LifeHistory
        FROM PLANT
        WHERE PlantID = JP.Plant
    ) AS LifeHistory
FROM
    JOURNAL_PAGE JP
    INNER JOIN FORAGER F ON JP.ForagerID = F.ForagerID;

--5. Upload photos of findings
SELECT JournalPageID, PhotoID
FROM JOURNAL_PAGE, PHOTO
WHERE JOURNAL_PAGE.Photo = PHOTO.PhotoID

--6. Shows Journal Page Before add
SELECT *
FROM JOURNAL_PAGE

--7. Adds new Journal Page
INSERT INTO THE_DATE VALUES (05,23,23);
INSERT INTO THE_TIME VALUES (18, 59, 32);
INSERT INTO TERRAIN VALUES ('Forest');
INSERT INTO PHOTO VALUES (7600, 1700, 'Pleurotus Ostreatus', 'test5');
INSERT INTO SEASON VALUES ('Winter');
INSERT INTO THE_LOCATION VALUES (0,0 );
INSERT INTO SIZE VALUES(7,7);
INSERT INTO MUSHROOM VALUES (1, 1,'Blue Oyster', 'Solid', 'Fan Shaped', 'Broad', 'White',5);
INSERT INTO JOURNAL_PAGE (ForagerID, FieldJournalID, TheDate, TheTime, Comments, TheLocation, Photo, Season, Terrain, Mushroom, Plant) VALUES (1,1,5,5, 'COMMENT5',5,5,5,5,NULL,2);

--8. Shows Journal Page After add
SELECT *
FROM JOURNAL_PAGE

DELETE FROM JOURNAL_PAGE
WHERE JournalPageID = 2;

--9. Shows Journal Page After delete
SELECT *
FROM JOURNAL_PAGE


/* Analytical Queries *******************************************************************************************************************/

--1. Find which species are edible
SELECT *
	FROM MUSHROOM_SPECIES
	WHERE EdibleStatus = 'Edible';

SELECT *
	FROM PLANT_SPECIES
	WHERE EdibleStatus = 'Edible';

--2. Find Photos of all mushroom species: Blue Oyster
SELECT DISTINCT M.MushroomName, MS.MushroomSpeciesName, P.Photo
	FROM MUSHROOM M, MUSHROOM_SPECIES MS, JOURNAL_PAGE P
	WHERE M.MushroomName LIKE 'Blue Oyster' 
	      and  MS.MushroomSpeciesID = M.MushroomID
		  and M.MushroomID = P.JournalPageID; 


--3. Find photos of all plant species; Blueberry
SELECT DISTINCT P.PlantName, PS.PlantSpeciesName, JP.Photo
	FROM PLANT P, PLANT_SPECIES PS, JOURNAL_PAGE JP
	WHERE P.PlantName LIKE 'Blueberry'
		and PS.PlantSpeciesID = P.PlantID
		and P.PlantID = JP.JournalPageID

--4. Find all species found in a specifc season type
SELECT PS.PlantSpeciesName, JP.Season
	FROM PLANT_SPECIES PS, SEASON S, JOURNAL_PAGE JP
	WHERE S.SeasonType= 'Winter' and JP.Season = S.SeasonID and PS.PlantSpeciesID = JP.Plant;

SELECT DISTINCT MS.MushroomSpeciesName, JP.Season
	FROM MUSHROOM_SPECIES MS, SEASON S, JOURNAL_PAGE JP
	WHERE S.SeasonType= 'Summer' and JP.Season = S.SeasonID and MS.MushroomSpeciesID = JP.Mushroom;

--5. Find all species found in a specific ecosystem type 'Forest'.
SELECT DISTINCT PHOTO.SpeciesType
FROM JOURNAL_PAGE
JOIN TERRAIN ON JOURNAL_PAGE.Terrain = TERRAIN.TerrainID
JOIN PHOTO ON JOURNAL_PAGE.Photo = PHOTO.PhotoID
WHERE TERRAIN.TerrainType = 'Forest';



/* INSERT - ADD DATA TO TABLES *******************************************************************************************************************/

-- Kingdom 
Insert into KINGDOM values ('Fungi'); /*Mushroom Kingdom*/
Insert into KINGDOM values ('Plante'); /*Plant Kingdom*/

-- Mushroom Species
Insert into MUSHROOM_SPECIES values (1,'Pleurotus Ostreatus', 'Edible'); /*Oyster Mushroom*/
Insert into MUSHROOM_SPECIES values (1,'Hericium Erinaceus', 'Edible'); /*Lion's Mane*/

-- Mushroom
Insert into MUSHROOM values (1, 1,'Blue Oyster', 'Solid', 'Fan Shaped', 'Broad', 'White');
Insert into MUSHROOM values (1, 2,'Lion''s Mane','No Stem','No Cap','Hairy','White');
--Insert into MUSHROOM values (1,1, 'Pink Oyster','Solid', 'Fan Shaped', 'Broad', 'White');

-- Plant Species
Insert into PLANT_SPECIES values (2,'Vaccinium','Edible'); /*Blueberry*/
Insert into PLANT_SPECIES values (2,'Prunus persica','Edible'); /*Peach*/

-- Plant Size/ Plant
Insert into SIZE values (2, 4); --sizeID: 1
Insert into PLANT values (2, 1,'Blueberry', 'Fruit Bush', 1, 'Perennial'); --PlantSpeciesName: Vaccinium(1), sizeID: 1
Insert into SIZE values (3, 6); --sizeID: 2
Insert into PLANT values (2,2, 'Peach', 'Fruit Tree', 2,'Perennial'); --PlantSpeciesName: Prunus Persica(2), sizeID: 2

--Extra Mushroom Species
--Insert into MUSHROOM_SPECIES values ('1','Coprinopsis Atramentaria', 'False'); /*Inky Cap*/ 
--Insert into MUSHROOM_SPECIES values ('1','Amanita Muscaria', 'False'); /*Fly Agaric*/

--Extra Plant Species
--Insert into PLANT_SPECIES values ('2','Urtica dioica','True'); /*Common Nettle*/ 
--Insert into PLANT_SPECIES values ('2','Lavandula angustifolia','True'); /*English Lavender*/ 
--Insert into PLANT_SPECIES values ('2','Taraxacum','True'); /*Dandelion*/ 

--THE_DATE
INSERT INTO THE_DATE VALUES (1,20,1994);
INSERT INTO THE_DATE VALUES (11, 2, 2012);
INSERT INTO THE_DATE VALUES (7,1,1996);
INSERT INTO THE_DATE VALUES (2, 28, 2000);

--THE_TIME
INSERT INTO THE_TIME VALUES (04, 12, 32);
INSERT INTO THE_TIME VALUES (16, 01, 45);
INSERT INTO THE_TIME VALUES (03, 16, 02);
INSERT INTO THE_TIME VALUES (22, 23, 15);

--TERRAIN
INSERT INTO TERRAIN VALUES ('Forest');
INSERT INTO TERRAIN VALUES ('Park');
INSERT INTO TERRAIN VALUES ('Garden');
INSERT INTO TERRAIN VALUES ('Forest');

--PHOTO
INSERT INTO PHOTO VALUES (8000, 1400, 'example1', 'test1');
INSERT INTO PHOTO VALUES (7900, 1500, 'example2', 'test2');
INSERT INTO PHOTO VALUES (7800, 1600, 'example3', 'test3');
INSERT INTO PHOTO VALUES (7700, 1700, 'example4', 'test4');

--SEASON
INSERT INTO SEASON VALUES ('Winter');
INSERT INTO SEASON VALUES ('Fall');
INSERT INTO SEASON VALUES ('Summer');
INSERT INTO SEASON VALUES ('Spring');

--THE_LOCATION
INSERT INTO THE_LOCATION VALUES (38.8951,-77.0364 );
INSERT INTO THE_LOCATION VALUES (-38.8951,-77.0364 );
INSERT INTO THE_LOCATION VALUES (38.8951,77.0364 );
INSERT INTO THE_LOCATION VALUES (-38.8951,77.0364 );

--THE_LOGIN
INSERT INTO THE_LOGIN VALUES ('Test1');
INSERT INTO THE_LOGIN VALUES ('Test2');
INSERT INTO THE_LOGIN VALUES ('Test3');
INSERT INTO THE_LOGIN VALUES ('Test4');

--EXPERTISE
INSERT INTO EXPERTISE VALUES (3);
INSERT INTO EXPERTISE VALUES (6);
INSERT INTO EXPERTISE VALUES (5);
INSERT INTO EXPERTISE VALUES (6);

--BIOGRAPHY
INSERT INTO BIOGRAPHY VALUES ('This is a test example');
INSERT INTO BIOGRAPHY VALUES ('This is a test1 example');
INSERT INTO BIOGRAPHY VALUES ('This is a test2 example');
INSERT INTO BIOGRAPHY VALUES ('This is a test3 example');

--CONTACT
INSERT INTO CONTACT VALUES ('John', 'Smith', 'Jsmith@email.com', '2532532533');
INSERT INTO CONTACT VALUES ('Bob', 'Smith', 'Bsmith@email.com', '2532532534');
INSERT INTO CONTACT VALUES ('Henry', 'Smith', 'Hsmith@email.com', '2532532535');
INSERT INTO CONTACT VALUES ('Bill', 'Smith', 'Bsmith@email.com', '2532532536');

--FORAGER
INSERT INTO FORAGER VALUES (25,1,1,1,1,1);
INSERT INTO FORAGER VALUES (27,2,2,2,2,2);
INSERT INTO FORAGER VALUES (35,3,3,3,3,3);
INSERT INTO FORAGER VALUES (38,4,4,4,4,4);

--FIELD_JOURNAL
INSERT INTO FIELD_JOURNAL VALUES (1);
INSERT INTO FIELD_JOURNAL VALUES (2);
INSERT INTO FIELD_JOURNAL VALUES (3);
INSERT INTO FIELD_JOURNAL VALUES (4);

--JOURNAL_PAGE
INSERT INTO JOURNAL_PAGE VALUES (1,1,1,1,'COMMENT1',1,1,1,1,NULL,1);
INSERT INTO JOURNAL_PAGE VALUES (2,2,2,2, 'COMMENT2',2,2,2,2,NULL,2);
INSERT INTO JOURNAL_PAGE VALUES (3,3,3,3, 'COMMENT3',3,3,3,3,1,NULL);
INSERT INTO JOURNAL_PAGE VALUES (4,4,4,4, 'COMMENT4',4,4,4,4,2,NULL);




/* IMPORTANT UTILITY FUNCTIONS -- USE WITH CAUTION! ************************************************************************************/
--DELETE FROM [TABLE_NAME]; --Deletes all rows FROM (this table)
--DBCC CHECKIDENT ('[TABLE_NAME]', RESEED, 0); --If Deleted rows and need to reset IDENTITY(1,1) counter back to 0
--GO
