DELETE FROM pub_bcr_candiattr WHERE pk_nbcr = '0001ZZ100000000049X7';
DELETE FROM pub_bcr_elem WHERE pk_billcodebase in ( select pk_billcodebase from pub_bcr_RuleBase where nbcrcode = 'YY01' );
DELETE FROM pub_bcr_RuleBase WHERE nbcrcode = 'YY01';
DELETE FROM pub_bcr_nbcr WHERE pk_nbcr = '0001ZZ100000000049X7';
DELETE FROM pub_bcr_OrgRela WHERE pk_billcodebase = '0001ZZ100000000049X8';
DELETE FROM pub_bcr_RuleBase WHERE pk_billcodebase = '0001ZZ100000000049X8';
DELETE FROM pub_bcr_elem WHERE pk_billcodeelem = '0001ZZ100000000049X9';
DELETE FROM pub_bcr_elem WHERE pk_billcodeelem = '0001ZZ100000000049XA';
DELETE FROM pub_bcr_elem WHERE pk_billcodeelem = '0001ZZ100000000049XB';