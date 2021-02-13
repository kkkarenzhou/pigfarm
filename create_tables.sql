-- Feel free to change the char length for any attributes if need any string comparison.
-- e.g. You might have a styID X0001 from you combobox but it won't match with that styID in our dateset, since
-- it has length 11 (has been changed to 5 tho) which is X0001 plus 6 whitespaces; thus,those two pigsties will
-- not be considered identical.
CREATE TABLE Manager (
  managerID		  CHAR(11),
  mName			    CHAR(20),
  mPhoneNumber	CHAR(12),
  PRIMARY KEY 	(managerID)
);

CREATE TABLE Farm (
  fAddress    CHAR(30),
  farmSize    INTEGER,
  managerID   CHAR(11) NOT NULL,
  FOREIGN KEY (managerID) REFERENCES Manager,
  PRIMARY KEY (fAddress)
);

CREATE TABLE Employee (
  employeeID        CHAR(11),
  eName             CHAR(20),
  ePhoneNumber      CHAR(12),
  currentLivingCost INTEGER,
  address           CHAR(30),
  PRIMARY KEY (employeeID),
  FOREIGN KEY (address) REFERENCES Farm(faddress)
  ON DELETE CASCADE
);

CREATE TABLE QualityCost (
  qualityRating	   INTEGER,
  costPerKg        INTEGER,
  PRIMARY KEY (qualityRating)
);

CREATE TABLE ExpertiseWage (
  areaOfExpertise CHAR(20),
  vWage           INTEGER,
  PRIMARY KEY (areaOfExpertise)
);

CREATE TABLE PigStyCost (
  stySize            INTEGER,
  waterCost          INTEGER,
  electricityCost    INTEGER,
  PRIMARY KEY (stySize)
);

CREATE TABLE FodderType (
  fodderName		CHAR(10),
  qualityRating	INTEGER,
  stock			    INTEGER,
  PRIMARY KEY (fodderName),
  FOREIGN KEY (qualityRating) REFERENCES QualityCost
);

CREATE TABLE Buys (
  managerID  CHAR(11),
  fodderName CHAR(10),
  PRIMARY KEY (managerID, fodderName),
  FOREIGN KEY (managerID) REFERENCES Manager(managerID)
  ON DELETE CASCADE,
  FOREIGN KEY (fodderName) REFERENCES FodderType(fodderName)
  ON DELETE CASCADE
);

CREATE TABLE PigSty (
  styID            CHAR(5),
  buildingMaterial CHAR(10),
  stySize          INTEGER,
  address          CHAR(30) NOT NULL,
  PRIMARY KEY (styID),
  FOREIGN KEY (stySize) REFERENCES PigStyCost(stySize)
  ON DELETE CASCADE,
  FOREIGN KEY (address) REFERENCES Farm
  ON DELETE CASCADE
);

CREATE TABLE Veterinarian (
  employeeID      CHAR(11),
  vBudget         INTEGER,
  areaOfExpertise CHAR(20),
  PRIMARY KEY (employeeID),
  FOREIGN KEY (employeeID) REFERENCES Employee(employeeID)
  ON DELETE CASCADE,
  FOREIGN KEY (areaOfExpertise) REFERENCES ExpertiseWage
  ON DELETE CASCADE
);

CREATE TABLE Cleaner (
  employeeID CHAR(11),
  budget     INTEGER,
  wage       INTEGER,
  PRIMARY KEY (employeeID),
  FOREIGN KEY (employeeID) REFERENCES Employee(employeeID)
  ON DELETE CASCADE
);

CREATE TABLE Feeder (
  employeeID CHAR(11),
  budget     INTEGER,
  wage       INTEGER,
  PRIMARY KEY (employeeID),
  FOREIGN KEY (employeeID) REFERENCES Employee(employeeID)
  ON DELETE CASCADE
);

CREATE TABLE Feeds (
  employeeID  CHAR(11),
  styID       CHAR(5),
  lastFedDate DATE,
  PRIMARY KEY (employeeID, styID),
  FOREIGN KEY (employeeID) REFERENCES Employee(employeeID)
  ON DELETE CASCADE,
  FOREIGN KEY (styID) REFERENCES PigSty(styID)
  ON DELETE CASCADE
);

CREATE TABLE Cleans (
  employeeID CHAR(11),
  styID      CHAR(5),
  PRIMARY KEY (employeeID, styID),
  FOREIGN KEY (employeeID) REFERENCES Employee(employeeID)
  ON DELETE CASCADE,
  FOREIGN KEY (styID) REFERENCES PigSty
  ON DELETE CASCADE
);

CREATE TABLE BreedFodder (
  breed      CHAR(10),
  fodderName CHAR(10),
  PRIMARY KEY (breed),
  FOREIGN KEY (fodderName) REFERENCES FodderType(fodderName)
  --ON DELETE SET DEFAULT
);

CREATE TABLE PigStyMapping (
  sex          CHAR(1),
  breed        CHAR(10),
  healthStatus CHAR(10),
  styID        CHAR(5),
  PRIMARY KEY (sex, breed, healthStatus),
  FOREIGN KEY (styID) REFERENCES PigSty
  ON DELETE CASCADE,
  FOREIGN KEY (breed) REFERENCES BreedFodder(breed)
  ON DELETE CASCADE
);

CREATE TABLE PigPriceMapping (
  sex          CHAR(1),
  age          INTEGER,
  weight       INTEGER,
  breed        CHAR(10),
  healthStatus CHAR(10),
  marketPrice  INTEGER,
  PRIMARY KEY (sex, age, weight, breed, healthStatus),
  FOREIGN KEY (sex, breed, healthStatus) REFERENCES PigStyMapping(sex,breed,healthStatus)
  ON DELETE CASCADE
);


CREATE TABLE Pig (
  -- Fixed format for pigID: PXXXX
  pigID        CHAR(5),
  sex          CHAR(1),
  age          INTEGER,
  weight       INTEGER,
  breed        CHAR(10),
  pregnancy    CHAR(1),
  healthStatus CHAR(10),
  PRIMARY KEY (pigID),
  FOREIGN KEY (sex, age, weight, breed, healthStatus) REFERENCES PigPriceMapping(sex, age, weight, breed, healthStatus)
  ON DELETE CASCADE
);

CREATE TABLE MedicalRecord (
  medID         CHAR(11),
  status        CHAR(10),
  description   CHAR(10),
  mrDate        DATE,
  pigID         CHAR(5),
  PRIMARY KEY (medID, pigID),
  FOREIGN KEY (pigID) REFERENCES Pig(pigID)
  ON DELETE CASCADE
);


CREATE TABLE Creates (
  employeeID  CHAR(11),
  medID       CHAR(11),
  pigID       CHAR(5),
  PRIMARY KEY (employeeID, medID),
  FOREIGN KEY (employeeID) REFERENCES Veterinarian(employeeID),
  --ON DELETE SET DEFAULT
  FOREIGN KEY (medID,pigID) REFERENCES MedicalRecord(medID,pigID)
);

commit;