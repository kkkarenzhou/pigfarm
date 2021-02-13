INSERT INTO Manager values ('A001','yoyo','332401');
INSERT INTO Manager values ('A002','cici','332402');

INSERT INTO Farm values ('Beijing','200','A001');
INSERT INTO Farm values ('Vancouver','100','A002');

INSERT INTO Employee values ('C001','Lee','0111','1000','Beijing');
INSERT INTO Employee values ('C002','Zee','0011','1100','Beijing');
INSERT INTO Employee values ('C003','Eee','0001','900','Beijing');
INSERT INTO Employee values ('C004','Qee','1000','2000','Vancouver');
INSERT INTO Employee values ('C005','Cee','1111','1000','Vancouver');
INSERT INTO Employee values ('V002','Peter','7788888888','2000','Vancouver');
INSERT INTO Employee values ('V001','Alice','7786666666','110','Beijing');
INSERT INTO Employee values ('F001','Jason','7780000000','910','Beijing');
INSERT INTO Employee values ('F002','Taylor','7781111111','790','Vancouver');
INSERT INTO Employee values ('F003','Kelly','7789999999','1200','Vancouver');

INSERT INTO QualityCost values ('20','15');
INSERT INTO QualityCost values ('12','30');
INSERT INTO QualityCost values ('40','19');
INSERT INTO QualityCost values ('70','80');

INSERT INTO ExpertiseWage values ('Exudative dermatitis','2000');
INSERT INTO ExpertiseWage values ('Coccidiosis','1800');
INSERT INTO ExpertiseWage values ('Respiratory diseases','1900');
INSERT INTO ExpertiseWage values ('Swine dysentery','2100');
INSERT INTO ExpertiseWage values ('Mastitis','3000');

INSERT INTO PigStyCost values ('20','1000','1100');
INSERT INTO PigStyCost values ('25','1100','1300');
INSERT INTO PigStyCost values ('15','900','970');
INSERT INTO PigStyCost values ('30','1500','1500');

INSERT INTO FodderType values ('Fodder1','12','1000');
INSERT INTO FodderType values ('Fodder2','12','800');
INSERT INTO FodderType values ('Fodder3','20','800');
INSERT INTO FodderType values ('Fodder4','20','800');

INSERT INTO Buys values ('A001','Fodder1');
INSERT INTO Buys values ('A001','Fodder2');
INSERT INTO Buys values ('A001','Fodder3');

INSERT INTO PigSty values ('X0001','brick','30','Beijing');
INSERT INTO PigSty values ('X0002','brick','20','Beijing');
INSERT INTO PigSty values ('X0003','brick','30','Beijing');
INSERT INTO PigSty values ('X0004','brick','30','Beijing');
INSERT INTO PigSty values ('Z0001','wood','25','Vancouver');
INSERT INTO PigSty values ('Z0002','wood','15','Vancouver');
INSERT INTO PigSty values ('Z0003','brick','30','Vancouver');

INSERT INTO Veterinarian values ('V001','1000','Mastitis');
INSERT INTO Veterinarian values ('V002','1200','Exudative dermatitis');

INSERT INTO Cleaner values ('C001','900','900');
INSERT INTO Cleaner values ('C002','900','900');
INSERT INTO Cleaner values ('C003','900','900');
INSERT INTO Cleaner values ('C004','900','900');
INSERT INTO Cleaner values ('C005','900','900');


INSERT INTO Feeder values ('F001','900','900');
INSERT INTO Feeder values ('F002','900','900');
INSERT INTO Feeder values ('F003','900','900');

-- IMPORTANT: The original data for DATE domain, which is 2018-10-09, are making an ORA-01861 error on my end. These insertions
-- supposed to fail on your environment as well. Using TO_DATE seems to fix the problem. Test these changes if they are
-- involved in yor part. Same in MedicalRecords.
INSERT INTO Feeds values ('F001','X0001',TO_DATE('2018/10/10','yyyy/mm/dd'));
INSERT INTO Feeds values ('F002', 'X0001',TO_DATE('2018/11/11','yyyy/mm/dd'));
INSERT INTO Feeds values ('F003', 'X0002',TO_DATE('2018/11/11','yyyy/mm/dd'));

INSERT INTO Cleans values ('C003','X0001');

INSERT INTO BreedFodder values ('piglet','Fodder1');
INSERT INTO BreedFodder values ('boar','Fodder2');
INSERT INTO BreedFodder values ('market pig','Fodder3');
INSERT INTO BreedFodder values ('sow','Fodder4');



INSERT INTO PigStyMapping values ('F','piglet','Healthy','X0001');
INSERT INTO PigStyMapping values ('M','piglet','Healthy','X0001');
INSERT INTO PigStyMapping values ('F','piglet','Unhealthy','X0002');
INSERT INTO PigStyMapping values ('M','piglet','Unhealthy','X0002');
INSERT INTO PigStyMapping values ('F','market pig','Unhealthy','X0002');
INSERT INTO PigStyMapping values ('M','boar','Healthy','X0003');
INSERT INTO PigStyMapping values ('M','market pig','Healthy','X0004');
INSERT INTO PigStyMapping values ('F','market pig','Healthy','X0004');
INSERT INTO PigStyMapping values ('F','sow','Healthy','Z0001');

<<<<<<< HEAD


=======
>>>>>>> 1599995a0806032e331230ab8e6a7e3bbda32a54
INSERT INTO PigPriceMapping values ('F','1','200','piglet','Healthy','15');
INSERT INTO PigPriceMapping values ('M','1','200','piglet','Healthy','10');
INSERT INTO PigPriceMapping values ('F','1','200','sow','Healthy','7');
INSERT INTO PigPriceMapping values ('F','1','200','piglet','Unhealthy','0');
INSERT INTO PigPriceMapping values ('M','1','200','piglet','Unhealthy','0');
INSERT INTO PigPriceMapping values ('F','1','350','sow','Healthy','8');
INSERT INTO PigPriceMapping values ('F','2','300','sow','Healthy','7');
INSERT INTO PigPriceMapping values ('F','3','400','market pig','Healthy','9');
INSERT INTO PigPriceMapping values ('M','2','400','market pig','Healthy','10');
INSERT INTO PigPriceMapping values ('M','2','400','boar','Healthy','15');
INSERT INTO PigPriceMapping values ('F','3','400','market pig','Unhealthy','0');



INSERT INTO Pig values ('P0001','F','1','200','piglet','N','Healthy');
INSERT INTO Pig values ('P0002','M','1','200','piglet','N','Healthy');
INSERT INTO Pig values ('P0003','F','1','200','piglet','N','Unhealthy');
INSERT INTO Pig values ('P0004','M','1','200','piglet','N','Healthy');
INSERT INTO Pig values ('P0005','F','1','350','sow','N','Healthy');
INSERT INTO Pig values ('P0006','F','2','300','sow','Y','Healthy');
INSERT INTO Pig values ('P0007','M','2','400','boar','N','Healthy');
INSERT INTO Pig values ('P0008','F','3','400','market pig','N','Unhealthy');
INSERT INTO Pig values ('P0009','M','2','400','market pig','N','Healthy');
INSERT INTO Pig values ('P0010','F','3','400','market pig','N','Healthy');



INSERT INTO MedicalRecord values ('M0001','Healthy','no disease','2018-10-09','P0001');
INSERT INTO MedicalRecord values ('M0002','Healthy','no disease','2018-10-09','P0002');
INSERT INTO MedicalRecord values ('M0003','Healthy','no disease','2018-10-09','P0003');
INSERT INTO MedicalRecord values ('M0004','Healthy','no disease','2018-10-09','P0004');
INSERT INTO MedicalRecord values ('M0005','Healthy','no disease','2018-10-09','P0005');
INSERT INTO MedicalRecord values ('M0006','Healthy','no disease','2018-10-09','P0006');
INSERT INTO MedicalRecord values ('M0007','Healthy','no disease','2018-10-09','P0007');
INSERT INTO MedicalRecord values ('M0008','Healthy','no disease','2018-10-09','P0008');
INSERT INTO MedicalRecord values ('M0009','Healthy','no disease','2018-10-09','P0009');
INSERT INTO MedicalRecord values ('M0010','Healthy','no disease','2018-10-09','P0010');
INSERT INTO MedicalRecord values ('M0011','Healthy','no disease','2018-10-09','P0010');



INSERT INTO Creates values ('V001','M0001','P0001');
INSERT INTO Creates values ('V001','M0002','P0002');
INSERT INTO Creates values ('V001','M0003','P0003');
INSERT INTO Creates values ('V001','M0004','P0004');
INSERT INTO Creates values ('V002','M0005','P0005');
INSERT INTO Creates values ('V001','M0005','P0005');
INSERT INTO Creates values ('V001','M0006','P0006');
INSERT INTO Creates values ('V002','M0008','P0008');
INSERT INTO Creates values ('V001','M0007','P0007');
INSERT INTO Creates values ('V002','M0006','P0006');
INSERT INTO Creates values ('V001','M0008','P0008');
INSERT INTO Creates values ('V001','M0009','P0009');
INSERT INTO Creates values ('V001','M0010','P0010');

commit;

