DELETE FROM bd_billtype2 WHERE pk_billtypeid = '0001ZZ10000000004AM6';
DELETE FROM bd_fwdbilltype WHERE pk_billtypeid = '0001ZZ10000000004AM6';
DELETE FROM pub_function WHERE pk_billtype = 'YY02';
DELETE FROM pub_billaction WHERE pk_billtypeid = '0001ZZ10000000004AM6';
DELETE FROM pub_billactiongroup WHERE pk_billtype = 'YY02';
DELETE FROM bd_billtype WHERE pk_billtypeid = '0001ZZ10000000004AM6';
delete from temppkts;
DELETE FROM sm_rule_type WHERE pk_rule_type = null;
DELETE FROM sm_permission_res WHERE pk_permission_res = null;
DELETE FROM pub_billaction WHERE pk_billaction = '0001ZZ10000000004AM7';
DELETE FROM pub_billaction WHERE pk_billaction = '0001ZZ10000000004AM8';
DELETE FROM pub_billaction WHERE pk_billaction = '0001ZZ10000000004AM9';
DELETE FROM pub_billaction WHERE pk_billaction = '0001ZZ10000000004AMA';
DELETE FROM pub_billaction WHERE pk_billaction = '0001ZZ10000000004AMB';
DELETE FROM pub_billaction WHERE pk_billaction = '0001ZZ10000000004AMC';
DELETE FROM pub_busiclass WHERE pk_busiclass = '0001ZZ10000000004AMD';
DELETE FROM pub_busiclass WHERE pk_busiclass = '0001ZZ10000000004AME';
DELETE FROM pub_busiclass WHERE pk_busiclass = '0001ZZ10000000004AMF';
DELETE FROM pub_busiclass WHERE pk_busiclass = '0001ZZ10000000004AMG';
DELETE FROM pub_busiclass WHERE pk_busiclass = '0001ZZ10000000004AMH';
DELETE FROM pub_busiclass WHERE pk_busiclass = '0001ZZ10000000004AMI';
