DELETE FROM pub_bcr_candiattr WHERE pk_nbcr = '0001ZZ10000000004AZC';
DELETE FROM pub_bcr_elem WHERE pk_billcodebase in ( select pk_billcodebase from pub_bcr_RuleBase where nbcrcode = 'YY03' );
DELETE FROM pub_bcr_RuleBase WHERE nbcrcode = 'YY03';
DELETE FROM pub_bcr_nbcr WHERE pk_nbcr = '0001ZZ10000000004AZC';
DELETE FROM pub_bcr_OrgRela WHERE pk_billcodebase = '0001ZZ10000000004AZD';
DELETE FROM pub_bcr_RuleBase WHERE pk_billcodebase = '0001ZZ10000000004AZD';
DELETE FROM pub_bcr_elem WHERE pk_billcodeelem = '0001ZZ10000000004AZE';
DELETE FROM pub_bcr_elem WHERE pk_billcodeelem = '0001ZZ10000000004AZF';
DELETE FROM pub_bcr_elem WHERE pk_billcodeelem = '0001ZZ10000000004AZG';
DELETE FROM bd_billtype2 WHERE pk_billtypeid = '0001ZZ10000000004AZ7';
DELETE FROM bd_fwdbilltype WHERE pk_billtypeid = '0001ZZ10000000004AZ7';
DELETE FROM pub_function WHERE pk_billtype = 'YY03';
DELETE FROM pub_billaction WHERE pk_billtypeid = '0001ZZ10000000004AZ7';
DELETE FROM pub_billactiongroup WHERE pk_billtype = 'YY03';
DELETE FROM bd_billtype WHERE pk_billtypeid = '0001ZZ10000000004AZ7';
delete from temppkts;
DELETE FROM sm_rule_type WHERE pk_rule_type = null;
DELETE FROM sm_permission_res WHERE pk_permission_res = null;
DELETE FROM pub_billaction WHERE pk_billaction = '0001ZZ10000000004AZ8';
DELETE FROM pub_billaction WHERE pk_billaction = '0001ZZ10000000004AZ9';
DELETE FROM pub_busiclass WHERE pk_busiclass = '0001ZZ10000000004AZA';
DELETE FROM pub_busiclass WHERE pk_busiclass = '0001ZZ10000000004AZB';
DELETE FROM pub_systemplate_base where pk_systemplate = '0001ZZ10000000004AZ6';
delete from pub_print_datasource where ctemplateid = '0001ZZ10000000004AQX';
delete from pub_print_cell where ctemplateid = '0001ZZ10000000004AQX';
delete from pub_print_line where ctemplateid = '0001ZZ10000000004AQX';
delete from pub_print_variable where ctemplateid = '0001ZZ10000000004AQX';
delete from pub_print_template where ctemplateid = '0001ZZ10000000004AQX';
DELETE FROM pub_systemplate_base where pk_systemplate = '0001ZZ10000000004AQW';
delete from pub_query_condition where pk_templet = '0001ZZ10000000004APJ';
delete from pub_query_templet where id = '0001ZZ10000000004APJ';
DELETE FROM pub_systemplate_base where pk_systemplate = '0001ZZ10000000004API';
delete from pub_billtemplet_b where pk_billtemplet = '0001ZZ10000000004AMR';
delete from pub_billtemplet where pk_billtemplet = '0001ZZ10000000004AMR';
DELETE FROM pub_billtemplet_t WHERE pk_billtemplet = '0001ZZ10000000004AMR';
DELETE FROM sm_menuitemreg WHERE pk_menuitem = '0001ZZ10000000004AMQ';
DELETE FROM sm_funcregister WHERE cfunid = '0001ZZ10000000004AMO';
DELETE FROM sm_paramregister WHERE pk_param = '0001ZZ10000000004AMP';
