--------------------------------------------------------
--  File created - Friday-November-29-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package Body EMP_PACKAGE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "NAILESH"."EMP_PACKAGE" is
 function getDate return VARCHAR2 is
 begin
 RETURN TO_CHAR(sysdate);
 end getDate; 
PROCEDURE insertEmployee1
(in_id IN EMPLOYEE.EMPID%TYPE,
 in_name IN EMPLOYEE.NAME%TYPE,
 in_role IN EMPLOYEE.ROLE%TYPE,
 in_city IN EMPLOYEE.CITY%TYPE,
 in_country IN EMPLOYEE.COUNTRY%TYPE,
 out_result OUT VARCHAR2)
AS
BEGIN
  INSERT INTO EMPLOYEE (EMPID, NAME, ROLE, CITY, COUNTRY) 
  values (in_id,in_name,in_role,in_city,in_country);
  commit;

  out_result := 'TRUE';

EXCEPTION
  WHEN OTHERS THEN 
  out_result := 'FALSE';
  ROLLBACK;
END;
 end emp_package;

/
