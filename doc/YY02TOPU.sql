--订货单推式生成采购订单，关联采购合同sql
select *
  from (select t2.pk_material || '_' || t1.pk_org || '_' || t1.cvendorid pk_mm1, --主键1
               t1.cvendorid, --供应商
               t2.ngprice, --主本币无税单价 
               t2.ngtaxprice, --主本币含税单价
               t1.pk_ct_pu, --来源单据主键
               t2.pk_ct_pu_b, --来源单据子表主键
               t1.cbilltypecode, --来源单据类型编码
               t1.ctrantypeid -- 来源单据类型
          from ct_pu t1 --采购合同主表
          left join ct_pu_b t2 --采购合同子表
            on t1.pk_ct_pu = t2.pk_ct_pu
         where nvl(t1.dr, 0) = 0
           and nvl(t2.dr, 0) = 0
           and t1.fstatusflag = 1 --生效状态
           and t1.blatest = 'Y' --最新版本
        --           and t1.pk_org = '' --组织
        --           and t2.pk_material = ''--物料
        ) mm1

  right join (select t4.cmaterialvid || '_' || t3.pk_org || '_' ||
                    t3.pk_supplier pk_mm2, --主键2
                    t4.dispatchstyle --配送方式
               from yy_matercontrast t3 --物料对照表主表
               left join yy_matercontrast_b t4 --物料对照表子表
                 on t3.pk_matercontrast = t4.pk_matercontrast
              where nvl(t3.dr, 0) = 0
                and nvl(t4.dr, 0) = 0) mm2
    on mm1.pk_mm1 = mm2.pk_mm2
