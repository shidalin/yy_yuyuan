--��������ʽ���ɲɹ������������ɹ���ͬsql
select *
  from (select t2.pk_material || '_' || t1.pk_org || '_' || t1.cvendorid pk_mm1, --����1
               t1.cvendorid, --��Ӧ��
               t2.ngprice, --��������˰���� 
               t2.ngtaxprice, --�����Һ�˰����
               t1.pk_ct_pu, --��Դ��������
               t2.pk_ct_pu_b, --��Դ�����ӱ�����
               t1.cbilltypecode, --��Դ�������ͱ���
               t1.ctrantypeid -- ��Դ��������
          from ct_pu t1 --�ɹ���ͬ����
          left join ct_pu_b t2 --�ɹ���ͬ�ӱ�
            on t1.pk_ct_pu = t2.pk_ct_pu
         where nvl(t1.dr, 0) = 0
           and nvl(t2.dr, 0) = 0
           and t1.fstatusflag = 1 --��Ч״̬
           and t1.blatest = 'Y' --���°汾
        --           and t1.pk_org = '' --��֯
        --           and t2.pk_material = ''--����
        ) mm1

  right join (select t4.cmaterialvid || '_' || t3.pk_org || '_' ||
                    t3.pk_supplier pk_mm2, --����2
                    t4.dispatchstyle --���ͷ�ʽ
               from yy_matercontrast t3 --���϶��ձ�����
               left join yy_matercontrast_b t4 --���϶��ձ��ӱ�
                 on t3.pk_matercontrast = t4.pk_matercontrast
              where nvl(t3.dr, 0) = 0
                and nvl(t4.dr, 0) = 0) mm2
    on mm1.pk_mm1 = mm2.pk_mm2
