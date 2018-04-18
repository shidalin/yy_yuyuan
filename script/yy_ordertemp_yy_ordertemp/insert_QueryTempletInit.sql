INSERT INTO pub_query_templet (ts, ID, MODEL_CODE, MODEL_NAME, NODE_CODE, PK_CORP, METACLASS, LAYER ) VALUES ('2017-01-05 17:39:03', '0001ZZ100000000049MU', '40400101', '订货单模板主实体', '40400101', '@@@@', 'd21e0505-f850-438b-bdc1-be770a1cac03', 0 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 0, 1, 'pk_ordertemp', '0001ZZ100000000049MV', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 1, 1, 'pk_ordertemp_b', '0001ZZ100000000049MW', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '集团', 5, 2, 1, 'pk_group', '0001ZZ100000000049MX', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, VALUE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '采购组织', 5, 3, 1, 'pk_org', '0001ZZ100000000049MY', 'Y', 'N', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'N', '=@', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, '#mainorg#', 'Y', 'Y', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '采购组织版本', 5, 4, 1, 'pk_org_v', '0001ZZ100000000049MZ', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 5, 1, 'vbillcode', '0001ZZ100000000049N0', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 6, 1, 'ordertempname', '0001ZZ100000000049N1', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', 'IM,2ed33012-890c-4e5f-82a0-40ef0eeb4b45', 6, 7, 1, 'vbillstatus', '0001ZZ100000000049N2', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@<>@', '等于@不等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 8, 1, 'pk_billtypecode', '0001ZZ100000000049N3', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '影响因素单据类型', 5, 9, 1, 'pk_billtypeid', '0001ZZ100000000049N4', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '业务流程', 5, 10, 1, 'pk_busitype', '0001ZZ100000000049N5', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '用户', 5, 11, 1, 'creator', '0001ZZ100000000049N6', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 8, 12, 1, 'creationtime', '0001ZZ100000000049N7', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'between@=@>@>=@<@<=@', '介于@等于@大于@大于等于@小于@小于等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'Y', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '用户', 5, 13, 1, 'billmaker', '0001ZZ100000000049N8', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, VALUE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 3, 14, 0, 'dmakedate', '0001ZZ100000000049N9', 'Y', 'N', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'N', 'between@=@>@>=@<@<=@', '介于@等于@大于@大于等于@小于@小于等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, '#day(0)#,#day(0)#', 'Y', 'Y', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '用户（所有）', 5, 15, 1, 'modifier', '0001ZZ100000000049NA', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 8, 16, 1, 'modifiedtime', '0001ZZ100000000049NB', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'between@=@>@>=@<@<=@', '介于@等于@大于@大于等于@小于@小于等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'Y', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '用户', 5, 17, 1, 'approver', '0001ZZ100000000049NC', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 8, 18, 1, 'dapprovetime', '0001ZZ100000000049ND', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'between@=@>@>=@<@<=@', '介于@等于@大于@大于等于@小于@小于等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'Y', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 19, 1, 'approvenote', '0001ZZ100000000049NE', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 20, 1, 'vmemo', '0001ZZ100000000049NF', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 21, 1, 'vdef1', '0001ZZ100000000049NG', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 22, 1, 'vdef2', '0001ZZ100000000049NH', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 23, 1, 'vdef3', '0001ZZ100000000049NI', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 24, 1, 'vdef4', '0001ZZ100000000049NJ', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 25, 1, 'vdef5', '0001ZZ100000000049NK', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 26, 1, 'vdef6', '0001ZZ100000000049NL', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 27, 1, 'vdef7', '0001ZZ100000000049NM', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 28, 1, 'vdef10', '0001ZZ100000000049NN', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 29, 1, 'vdef11', '0001ZZ100000000049NO', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 30, 1, 'vdef12', '0001ZZ100000000049NP', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 31, 1, 'vdef13', '0001ZZ100000000049NQ', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 32, 1, 'vdef14', '0001ZZ100000000049NR', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 33, 1, 'vdef15', '0001ZZ100000000049NS', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 34, 1, 'vdef16', '0001ZZ100000000049NT', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 35, 1, 'vdef17', '0001ZZ100000000049NU', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 36, 1, 'vdef18', '0001ZZ100000000049NV', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 37, 1, 'vdef19', '0001ZZ100000000049NW', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 38, 1, 'vdef20', '0001ZZ100000000049NX', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 39, 1, 'vdef21', '0001ZZ100000000049NY', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 40, 1, 'vdef22', '0001ZZ100000000049NZ', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 41, 1, 'vdef23', '0001ZZ100000000049O0', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 42, 1, 'vdef24', '0001ZZ100000000049O1', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 43, 1, 'vdef25', '0001ZZ100000000049O2', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 44, 1, 'vdef26', '0001ZZ100000000049O3', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 45, 1, 'vdef27', '0001ZZ100000000049O4', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 46, 1, 'vdef28', '0001ZZ100000000049O5', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 47, 1, 'vdef29', '0001ZZ100000000049O6', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 0, 48, 1, 'vdef30', '0001ZZ100000000049O7', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@like@left like@right like@', '等于@包含@左包含@右包含@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 3, 49, 1, 'dproducedate', '0001ZZ100000000049O8', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'between@=@>@>=@<@<=@', '介于@等于@大于@大于等于@小于@小于等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'Y', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 3, 50, 1, 'dinvalidate', '0001ZZ100000000049O9', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'between@=@>@>=@<@<=@', '介于@等于@大于@大于等于@小于@小于等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'Y', 'N', 9999 );
INSERT INTO pub_query_condition (ts, CONSULT_CODE, DATA_TYPE, DISP_SEQUENCE, DISP_TYPE, FIELD_CODE, ID, IF_AUTOCHECK, IF_DATAPOWER, IF_DEFAULT, IF_GROUP, IF_IMMOBILITY, IF_MUST, IF_ORDER, IF_SUM, IF_USED, IF_SUBINCLUDED, IF_MULTICORPREF, OPERA_CODE, OPERA_NAME, ORDER_SEQUENCE, PK_CORP, PK_TEMPLET, RETURN_TYPE, ISCONDITION, IF_SYSFUNCREFUSED, IF_ATTRREFUSED, LIMITS ) VALUES ('2017-01-05 17:39:03', '-99', 4, 51, 1, 'fproduceflag', '0001ZZ100000000049OA', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', '=@', '等于@', 0, '@@@@', '0001ZZ100000000049MU', 2, 'Y', 'N', 'N', 9999 );
INSERT INTO pub_systemplate_base (ts, nodekey, funnode, layer, moduleid, templateid, pk_systemplate, devorg, tempstyle, pk_country, pk_industry, dr ) VALUES ('2017-01-05 17:39:03', 'qt', '40400101', 0, '4040', '0001ZZ100000000049MU', '0001ZZ100000000049OB', '00001', 1, '~', '~', 0 );