INSERT INTO pub_bcr_nbcr (ts, name5, orgtype, name6, name3, name4, pk_nbcr, name, metaid, codelenth, codestyle, codescope, code, name2 ) VALUES ('2017-01-05 17:45:09', null, 'GLOBLE00000000000000', null, null, null, '0001ZZ100000000049X7', 'YY01', 'd21e0505-f850-438b-bdc1-be770a1cac03', 40, 'after', 'global', 'YY01', null );
INSERT INTO pub_bcr_RuleBase (ts, isused, islenvar, nbcrcode, isdefault, rulecode, rulename, isautofill, codescope, format, dataoriginflag, rulename6, rulename5, rulename4, rulename3, rulename2, iseditable, isgetpk, codemode, pk_billcodebase, pk_group ) VALUES ('2017-01-05 17:45:09', 'Y', 'Y', 'YY01', 'N', 'YY01', 'YY01', 'Y', 'g', 'yyyyMMdd', 0, null, null, null, null, null, 'N', 'N', 'after', '0001ZZ100000000049X8', 'GLOBLE00000000000000' );
INSERT INTO pub_bcr_elem (ts, elemvalue, isrefer, elemlenth, fillsign, pk_billcodebase, pk_billcodeelem, eorder, fillstyle, pk_billcodeentity, elemtype ) VALUES ('2017-01-05 17:45:10', 'YY01', 0, 4, null, '0001ZZ100000000049X8', '0001ZZ100000000049X9', 0, 0, '~', 0 );
INSERT INTO pub_bcr_elem (ts, elemvalue, isrefer, elemlenth, fillsign, pk_billcodebase, pk_billcodeelem, eorder, fillstyle, pk_billcodeentity, elemtype ) VALUES ('2017-01-05 17:45:10', '2017-01-05 17:45:09', 0, 8, null, '0001ZZ100000000049X8', '0001ZZ100000000049XA', 1, 0, '~', 2 );
INSERT INTO pub_bcr_elem (ts, elemvalue, isrefer, elemlenth, fillsign, pk_billcodebase, pk_billcodeelem, eorder, fillstyle, pk_billcodeentity, elemtype ) VALUES ('2017-01-05 17:45:10', 'YY01', 0, 8, null, '0001ZZ100000000049X8', '0001ZZ100000000049XB', 2, 0, '~', 3 );