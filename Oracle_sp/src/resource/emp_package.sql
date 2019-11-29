--------------------------------------------------------
--  File created - Friday-November-29-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package EMP_PACKAGE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "NAILESH"."EMP_PACKAGE" is
function getDate return VARCHAR2;
procedure insertEmployee1(
in_id IN EMPLOYEE.EMPID%TYPE,
 in_name IN EMPLOYEE.NAME%TYPE,
 in_role IN EMPLOYEE.ROLE%TYPE,
 in_city IN EMPLOYEE.CITY%TYPE,
 in_country IN EMPLOYEE.COUNTRY%TYPE, 
 out_result OUT VARCHAR2);
 end emp_package;

/
