
delete CREATES where PIGID = 'INPUT';
delete PIG where PIGID = 'INPUT';
-- DELETE PIG
-- D3

UPDATE PIG SET AGE = '12' where PIGID = 'P0001';
-- UPDATE PIG (example, simpler query than implemented) - see Pigs.java
-- D4

select DISTINCT E.EPHONENUMBER,E.EMPLOYEEID,E.ENAME,V.AREAOFEXPERTISE
from EMPLOYEE E,PIG P,PIGSTYMAPPING S,MEDICALRECORD M,CREATES C, BREEDFODDER BF,
     VETERINARIAN V
where BF.BREED = S.BREED AND BF.FODDERNAME = 'Fodder1' AND M.PIGID=P.PIGID
  AND P.BREED = S.BREED AND C.PIGID = P.PIGID AND C.EMPLOYEEID = E.EMPLOYEEID
  AND V.EMPLOYEEID = C.EMPLOYEEID;
COMMIT;
-- VETERINATIANS WHO HAVE CREATE MEDICAL RECORD OF PIGS WHICH ATE SPECIFIED FODDER
-- D5


SELECT sum(PP.MARKETPRICE)
from PIG P, PIGPRICEMAPPING PP
where PIGID in (select DISTINCT P.PIGID
                from PIGSTYMAPPING PM,PIG P
                where PM.STYID = 'X0003' AND PM.BREED = P.BREED AND PM.SEX = P.SEX
                  AND PM.HEALTHSTATUS = P.HEALTHSTATUS

               ) and P.HEALTHSTATUS =PP.HEALTHSTATUS and P.WEIGHT = PP.WEIGHT AND P.AGE = PP.AGE AND
      P.BREED=PP.BREED AND P.SEX = PP.SEX;
commit ;
-- TOTAL MARKET PRICE IN SPECIFIC PIGSTY
-- D6

select S.STYID,COUNT(distinct P.PIGID)
from PIGSTYMAPPING S,PIG P
where S.BREED=P.BREED AND S.HEALTHSTATUS=P.HEALTHSTATUS AND S.SEX=P.SEX
group by S.STYID;
COMMIT;
-- NUMBER OF PIGS IN EACH PIG STY
-- D7

select E.EMPLOYEEID, E.ENAME
from CLEANS S,FEEDS D, EMPLOYEE E
WHERE (S.STYID = 'X0002' OR D.STYID ='X0002')
  AND (S.EMPLOYEEID=E.EMPLOYEEID
         OR D.EMPLOYEEID=E.EMPLOYEEID);
COMMIT;

-- LIST OF FEEDERS AND CLEANERS WHO WORK IN SPECIFIED STY
-- D8


update FODDERTYPE
set STOCK = STOCK + 50
where FODDERNAME = 'Fodder1';
commit;
-- update fodder stock
-- a = breedfodder.stock
-- a = a++
-- update FODDERTYPE
-- set STOCK = 'a'
-- where FODDERNAME = input;
-- commit;
-- D9

CREATE OR REPLACE VIEW unhealthypig AS
SELECT pigID, sex, age, breed
FROM pig WHERE HEALTHSTATUS='Unhealthy'

-- CREATE VIEW OF PIGS THAT ARE NOT HEALTHY
-- D10

select STYID, ADDRESS from PIGSTY
where STYID in
(select STYID from (select STYID, MAX(LASTFEDDATE) as LASTFEDDATE from FEEDS group by STYID)
where LASTFEDDATE <= 'lastDate');

-- View all Pigsties to be Fed
-- D11

SELECT * FROM CREATES
WHERE EMPLOYEEID not in ( SELECT EMPLOYEEID FROM (
                                                 (SELECT PIGID, EMPLOYEEID FROM (select PIGID from PIG ) p
                                                                                  cross join
                                                                                    (select distinct EMPLOYEEID from CREATES) sp)
                                                 MINUS
                                                 (SELECT PIGID, EMPLOYEEID FROM CREATES))  r );

-- Division -> which vet have create medical record for all pigs