package nc.bs.pub.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.framework.common.NCLocator;
import nc.bs.pu.m21.maintain.OrderSaveBP;
import nc.bs.pub.compiler.AbstractCompiler2;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.pubimpl.bdlayer.cache.CacheVOQuery;
import nc.vo.bd.stordoc.StordocVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.ct.purdaily.entity.CT_PODTO;
import nc.vo.org.orgmodel.OrgRelationVO;
import nc.vo.pu.m21.entity.OrderHeaderVO;
import nc.vo.pu.m21.entity.OrderItemVO;
import nc.vo.pu.m21.entity.OrderVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.compiler.PfParameterVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.pub.SqlBuilder;
import nc.vo.pubapp.scale.ScaleUtils;
import nc.vo.scmpub.res.billtype.POBillType;
import nc.vo.yuyuan.yy_errorlog.YyErrorLogVO;

/**
 * ��������ʽ���ɲɹ�����
 * 
 * @author shidl
 * 
 */

public class N_21_PUSH extends AbstractCompiler2 {

	/**
	 * N_21_SAVEBASE ������ע�⡣
	 */
	public N_21_PUSH() {
		super();
	}

	/*
	 * ��ע��ƽ̨��дԭʼ�ű�
	 */
	@Override
	public String getCodeRemark() {
		return " nc.vo.pu.m21.entity.OrderVO[] inObject =\n          (nc.vo.pu.m21.entity.OrderVO[]) this.getVos();\n      nc.vo.pu.m21.context.OrderContext[] ctxs =\n          nc.vo.pu.m21.pub.OrderContextUtil.getCtxs(vo);\n      nc.vo.pu.m21.context.OrderContext ctx = null;\n      if (ctxs != null) {\n        ctx = ctxs[0];\n      }\n      return nc.bs.framework.common.NCLocator.getInstance()\n          .lookup(nc.itf.pu.m21.IOrderMaintain.class).save(inObject, ctx);\n";
	}

	/*
	 * ��ע��ƽ̨��д������ �ӿ�ִ����
	 */
	@Override
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			nc.vo.pu.m21.entity.OrderVO[] inObject = (nc.vo.pu.m21.entity.OrderVO[]) this
					.getVos();
			fillData(inObject);
			// ���ݱ����Զ�����5�����ͷ�ʽ���aggVO��
			nc.vo.pu.m21.entity.OrderVO[] result = splitAggvo(inObject);
			nc.vo.pu.m21.context.OrderContext[] ctxs = nc.vo.pu.m21.pub.OrderContextUtil
					.getCtxs(vo);
			nc.vo.pu.m21.context.OrderContext ctx = null;
			if (ctxs != null) {
				ctx = ctxs[0];
			}
			OrderSaveBP bp = new OrderSaveBP(ctx);
			result = bp.save(result, null);
			return result;
		} catch (Exception ex) {
			nc.vo.pubapp.pattern.exception.ExceptionUtils
					.wrappBusinessException(ex.getMessage());
		}
		return null;
	}

	/**
	 * ���ݱ����Զ�����5�����ͷ�ʽ�����aggVo
	 * 
	 * @param inObject
	 * @return
	 */
	private OrderVO[] splitAggvo(OrderVO[] inObject) {
		if (inObject != null && inObject.length > 0) {
			ArrayList<OrderVO> result = new ArrayList<OrderVO>();
			for (OrderVO agg : inObject) {
				HashMap<String, List<OrderItemVO>> style2bvolist = new HashMap<String, List<OrderItemVO>>();
				OrderItemVO[] bvos = agg.getBVO();
				for (OrderItemVO bvo : bvos) {
					if (style2bvolist.keySet().contains(bvo.getVbdef5())) {
						style2bvolist.get(bvo.getVbdef5()).add(bvo);
					} else {
						ArrayList<OrderItemVO> bvoList = new ArrayList<OrderItemVO>();
						bvoList.add(bvo);
						style2bvolist.put(bvo.getVbdef5(), bvoList);
					}
				}
				for (String style : style2bvolist.keySet()) {
					OrderVO orderVO = new OrderVO();
					orderVO.setBVO(style2bvolist.get(style).toArray(
							new OrderItemVO[0]));
					OrderHeaderVO hvo = (OrderHeaderVO) agg.getHVO().clone();
					hvo.setVdef5(style);
					orderVO.setHVO(hvo);
					result.add(orderVO);
				}
			}
			return result.toArray(new OrderVO[0]);
		}
		return null;
	}

	/**
	 * ����ҵ���߼��������
	 * 
	 * @param aggs
	 * @throws BusinessException
	 */
	private OrderVO[] fillData(OrderVO[] aggs) throws BusinessException {
		List<OrderVO> list = new ArrayList<OrderVO>();
		if (aggs != null && aggs.length > 0) {
			StringBuffer message = new StringBuffer();
			for (OrderVO agg : aggs) {
				OrderItemVO[] bvos = agg.getBVO();
				OrderHeaderVO hvo = agg.getHVO();
				try {
					agg = fillDateProcess(hvo, bvos);
					list.add(agg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					message.append(e.getMessage() + "\n");
				}
			}
			if (message.length() > 0) {
				throw new BusinessException(message.toString());
			}
		}
		return list.toArray(new OrderVO[0]);
	}

	private OrderVO fillDateProcess(OrderHeaderVO hvo, OrderItemVO[] bvos)
			throws BusinessException {

		List<CT_PODTO> beanList = null;
		OrderVO agg = new OrderVO();
		// �ӱ���Ϣ
		ArrayList<OrderItemVO> list0 = new ArrayList<OrderItemVO>();
		// ������־vo����
		ArrayList<YyErrorLogVO> list3 = new ArrayList<YyErrorLogVO>();
		// ������ϢУ��(ǰ̨��ʾ)
		StringBuffer message = new StringBuffer();
		ArrayList<String> materialidList = new ArrayList<String>();
		HashMap<String, List<OrderItemVO>> mterial2vo = new HashMap<String, List<OrderItemVO>>();
		if (bvos == null || bvos.length == 0) {
			return null;
		}
		// ��ѯ���ͷ�ʽ�Զ��嵵����
		SqlBuilder defdocSqlBuilder = new SqlBuilder();
		defdocSqlBuilder.append("	select t.code code, t.pk_defdoc pk_defdoc	");
		defdocSqlBuilder.append("	  from bd_defdoc t	");
		defdocSqlBuilder.append("	 inner join bd_defdoclist t1	");
		defdocSqlBuilder.append("	    on t.pk_defdoclist = t1.pk_defdoclist	");
		defdocSqlBuilder.append("	 where t1.code = 'PO_01'	");
		defdocSqlBuilder.append("	   and nvl(t.dr, 0) = 0	");
		defdocSqlBuilder.append("	   and nvl(t1.dr, 0) = 0	");
		defdocSqlBuilder.append("	   and t.enablestate = 2	");
		List defdocList = (List) getDao().executeQuery(
				defdocSqlBuilder.toString(), new MapListProcessor());
		HashMap<String, String> code2pkdefdoc = new HashMap<String, String>();
		if (defdocList != null && defdocList.size() > 0) {
			for (Object obj : defdocList) {
				Map map = (Map) obj;
				String code = map.get("code") == null ? "" : map.get("code")
						.toString();
				String pk_defdoc = map.get("code") == null ? "" : map.get(
						"pk_defdoc").toString();
				code2pkdefdoc.put(code, pk_defdoc);
			}
		}
		// ������������,������ѯ
		for (OrderItemVO bvo : bvos) {
			String key = bvo.getPk_material();
			List<OrderItemVO> value = null;
			if (mterial2vo.keySet().contains(key)) {
				value = mterial2vo.get(key);
				value.add(bvo);
			} else {
				value = new ArrayList<OrderItemVO>();
				value.add(bvo);
			}
			mterial2vo.put(key, value);
			materialidList.add(key);
		}
		// 1.����[����+��֯]���ڲɹ���ͬ�ϻ�ȡ[��Ӧ��+����]
		// 2.��������+��֯+��Ӧ�̣��ڶ��ձ��ȡ���ͷ�ʽ
		StringBuffer sb = new StringBuffer();
		sb.append(
				"	select distinct(pk_mm1),cvendorid,nqtorigprice,nqtorigtaxprice,nqtprice,nqttaxprice,pk_ct_pu,vbillcode,cbilltypecode,ctrantypeid,	")
				.append("ntaxrate	,")
				.append("ccurrencyid	,")
				.append("nexchangerate	,")
				.append("ngroupmny	,")
				.append("ngrouptaxmny	,")
				.append("nglobalmny	,")
				.append("castunitid	,")
				.append("nglobaltaxmny	,")
				.append("corigcurrencyid	,")
				.append("csendcountryid	,")
				.append("crececountryid	,")
				.append("ctaxcountryid	,")
				.append("fbuysellflag	,")
				.append("btriatradeflag	,")
				.append("ctaxcodeid	,")
				.append("nnosubtaxrate	,")
				.append("nnosubtax	,")
				.append("ncaltaxmny	,")
				.append("ncalcostmny	,")
				.append("cdevareaid	,")
				.append("cdevaddrid	,")
				.append("pk_receiveaddress	,")
				.append("ngtaxprice	,")
				.append("ngprice	,")
				.append("norigtaxprice	,")
				.append("norigprice	,")
				.append("ntax	,")
				.append("ntaxmny	,")
				.append("nmny	,")
				.append("norigtaxmny	,")
				.append("norigmny	,")
				.append("vchangerate	,")
				.append("vqtunitrate	,")
				.append("cqtunitid	,")
				.append("dispatchstyle	,")
				.append("pk_org	,")
				.append("pk_org_v,	")
				.append("www vbdef7	")
				.append("	  from (select t2.pk_material || '_' || t5.pk_scopeorg || '_' || t1.cvendorid pk_mm1,	")
				.append("	               t1.cvendorid,	")
				.append("	               t2.nqtorigprice,	")
				.append("	               t2.nqtorigtaxprice,	")
				.append("	               t2.castunitid,	")
				.append("	               t2.nqtprice,	")
				.append("	               t2.nqttaxprice,	")
				.append("	               t1.pk_ct_pu,	")
				.append("	               t1.vbillcode,	")
				.append("	               t2.pk_ct_pu_b,	")
				.append("	               t1.cbilltypecode,	")
				.append("	               t1.ctrantypeid,	")
				.append("t2.ntaxrate,	")
				.append("t1.ccurrencyid	,")
				.append("t1.nexchangerate	,")
				.append("t2.ngroupmny	,")
				.append("t2.ngrouptaxmny	,")
				.append("t2.nglobalmny	,")
				.append("t2.nglobaltaxmny	,")
				.append("t1.corigcurrencyid	,")
				.append("t2.csendcountryid	,")
				.append("t2.crececountryid	,")
				.append("t2.ctaxcountryid	,")
				.append("t2.fbuysellflag	,")
				.append("t2.btriatradeflag	,")
				.append("t2.ctaxcodeid	,")
				.append("t2.nnosubtaxrate	,")
				.append("t2.nnosubtax	,")
				.append("t2.ncaltaxmny	,")
				.append("t2.ncalcostmny	,")
				.append("t2.cdevareaid	,")
				.append("t2.cdevaddrid	,")
				.append("t2.pk_receiveaddress	,")
				.append("t2.ngtaxprice	,")
				.append("t2.ngprice	,")
				.append("t2.norigtaxprice	,")
				.append("t2.norigprice	,")
				.append("t2.ntax	,")
				.append("t2.ntaxmny	,")
				.append("t2.nmny	,")
				.append("t2.norigtaxmny	,")
				.append("t2.norigmny	,")
				.append("t2.vchangerate	,")
				.append("t2.vqtunitrate	,")
				.append("t2.cqtunitid	,")
				.append("t2.pk_org	,")
				.append("t2.pk_org_v	")
				.append("	          from ct_pu t1	")
				.append("	          inner join ct_pu_b t2	")
				.append("	            on t1.pk_ct_pu = t2.pk_ct_pu	")
				.append("	          inner join ct_scope t5	")
				.append("	            on t5.pk_ct_pu = t1.pk_ct_pu	")
				.append("	         where nvl(t1.dr, 0) = 0	")
				.append("	           and nvl(t2.dr, 0) = 0	")
				.append("	           and nvl(t5.dr, 0) = 0	")
				.append("	           and t1.fstatusflag = 1	")
				.append("	           and t1.blatest = 'Y'	")
				.append("	           and t1.invallidate >= '" + hvo.getVdef4()
						+ "'	")
				.append("	           and t1.valdate <= '" + hvo.getVdef4()
						+ "'	")
				.append("	           and t5.pk_scopeorg = '")
				.append(hvo.getPk_org())
				.append("' and ")
				.append(nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
						"t2.pk_material", materialidList, true))
				.append(" ) mm1	")
				.append("	 inner join (select t4.cmaterialvid || '_' || t3.pk_org || '_' ||	")
				.append("	                    t3.pk_supplier pk_mm2,	")
				.append("	                    t4.dispatchstyle,t8.def7 www	")
				.append("	               from yy_matercontrast t3	")
				.append("	               left join yy_matercontrast_b t4	")
				.append("	                 on t3.pk_matercontrast = t4.pk_matercontrast	")
				.append("	               left join bd_material t8	")
				.append("	                 on t4.cmaterialvid = t8.pk_material	")
				.append("	              where nvl(t3.dr, 0) = 0	")
				.append("	                and nvl(t8.dr, 0) = 0	")
				.append("	                and nvl(t4.dr, 0) = 0) mm2	")
				.append("	    on mm1.pk_mm1 = mm2.pk_mm2	");
		beanList = (List<CT_PODTO>) getDao().executeQuery(sb.toString(),
				new BeanListProcessor(CT_PODTO.class));
		// ����û�й����ɹ���ͬ�������϶��ձ�
		if (beanList == null || beanList.size() == 0) {
			// 2017-02-08-�޶�-�����ж���û�вɹ���ͬ����û�����϶��ձ�-ͬʱ�����������
			// ���ͷ�ʽ��ѯ
			StringBuffer buffer = new StringBuffer();
			buffer.append(
					"	select t4.cmaterialvid || '_' || t3.pk_org || '_' || t3.pk_supplier keyword  ")
					.append("	  from yy_matercontrast t3	")
					.append("	  inner join yy_matercontrast_b t4	")
					.append("	    on t3.pk_matercontrast = t4.pk_matercontrast	")
					.append("	 where nvl(t3.dr, 0) = 0	")
					.append("	   and nvl(t4.dr, 0) = 0	")
					.append(" and t3.pk_org = '" + hvo.getPk_org() + "' and ")
					.append(nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
							"t4.cmaterialvid", materialidList, true))
					.append("	 group by t4.cmaterialvid || '_' || t3.pk_org|| '_' || t3.pk_supplier	");
			List<Object> styleList = (List<Object>) getDao().executeQuery(
					buffer.toString(), new ArrayListProcessor());
			// ������ͬ��ѯ
			StringBuffer ctbuffer = new StringBuffer();
			ctbuffer.append(
					" select t2.pk_material || '_' || t5.pk_scopeorg || '_' || t1.cvendorid keyword	")
					.append("	          from ct_pu t1	")
					.append("	          inner join ct_pu_b t2	")
					.append("	            on t1.pk_ct_pu = t2.pk_ct_pu	")
					.append("	          inner join ct_scope t5	")
					.append("	            on t5.pk_ct_pu = t1.pk_ct_pu	")
					.append("	         where nvl(t1.dr, 0) = 0	")
					.append("	           and nvl(t2.dr, 0) = 0	")
					.append("	           and nvl(t5.dr, 0) = 0	")
					.append("	           and t1.fstatusflag = 1	")
					.append("	           and t1.blatest = 'Y'	")
					.append("	           and t1.invallidate >= '"
							+ hvo.getVdef4() + "'	")
					.append("	           and t1.valdate <= '" + hvo.getVdef4()
							+ "'	")
					.append("	           and t5.pk_scopeorg = '")
					.append(hvo.getPk_org())
					.append("' and ")
					.append(nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
							"t2.pk_material", materialidList, true))
					.append("  group by t2.pk_material || '_' || t5.pk_scopeorg || '_' || t1.cvendorid	");
			List<Object> ctList = (List<Object>) getDao().executeQuery(
					ctbuffer.toString(), new ArrayListProcessor());
			// ��ȡ������Ϣ
			StringBuffer buffer2 = new StringBuffer();
			for (String str : materialidList) {
				buffer2.append("���ϱ���:"
						+ nc.pub.yuyuan.utils.MaterialUtil.getCode(str)
						+ ",��������:"
						+ nc.pub.yuyuan.utils.MaterialUtil.getName(str) + "\n");
			}
			// 1.ȫ��û�����ͷ�ʽ
			if (styleList.size() == 0) {
				for (String str : materialidList) {
					String msg = "���ݺ�:"
							+ hvo.getVdef2()
							+ "��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org()) + ",���ϱ���:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(str)
							+ ",��������:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(str)
							+ ",���������ͷ�ʽ���ձ�����Ϊ��\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("��������ʽ���ɲɹ�����������Ϣ");
					logvo.setErrordate(AppContext.getInstance().getBusiDate());
					logvo.setErrorinfo(msg);
					logvo.setPk_org(hvo.getPk_org());
					logvo.setBillno(hvo.getVdef2());
					logvo.setBill(hvo.getVdef1());
					logvo.setBillmaker(hvo.getVdef3());
					logvo.setBilldate(new UFDate(hvo.getVdef4()));
					logvo.setBillbno(null);
					logvo.setBillbmaterial(str);
					list3.add(logvo);
				}
			}
			CacheVOQuery cachevoQuery = new CacheVOQuery(SupplierVO.class,
					new String[] { "code", "name" });
			// 2.ȫ��û�й�����ͬ
			if (ctList.size() == 0) {
				for (String str : materialidList) {
					String msg = "���ݺ�:"
							+ hvo.getVdef2()
							+ "��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org()) + "���ϱ���:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(str)
							+ ",��������:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(str)
							+ "�����Ĳɹ���ͬ����Ϊ��\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("��������ʽ���ɲɹ�����������Ϣ");
					logvo.setErrordate(AppContext.getInstance().getBusiDate());
					logvo.setErrorinfo(msg);
					logvo.setPk_org(hvo.getPk_org());
					logvo.setBillno(hvo.getVdef2());
					logvo.setBill(hvo.getVdef1());
					logvo.setBillmaker(hvo.getVdef3());
					logvo.setBilldate(new UFDate(hvo.getVdef4()));
					logvo.setBillbno(null);
					logvo.setBillbmaterial(str);
					list3.add(logvo);
				}
			}
			// ���ձ����ݺͺ�ͬ����ƥ�䲻��,�Ժ�ͬ����Ϊ��׼,��ʾȱ�ٶ��ձ�����
			ArrayList<String> errorKeyList = new ArrayList<String>();
			ArrayList<String> styleStrList = new ArrayList<String>();
			ArrayList<String> ctStrList = new ArrayList<String>();
			for (Object obj : styleList) {
				Object[] objs = (Object[]) obj;
				styleStrList.add(objs[0].toString());
			}
			for (Object obj : ctList) {
				Object[] objs = (Object[]) obj;
				ctStrList.add(objs[0].toString());
			}
			for (String ctKey : ctStrList) {
				if (!styleStrList.contains(ctKey)) {
					errorKeyList.add(ctKey);
				}
			}
			if (errorKeyList.size() > 0) {
				for (String errorKey : errorKeyList) {
					String[] split = errorKey.split("_");
					String cmaterialvid = split[0];
					String pk_org = split[1];
					String pk_supplier = split[2];
					String msg = "���ݺ�:"
							+ hvo.getVdef2()
							+ "��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(pk_org)
							+ ",��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(pk_org)
							+ ",���ϱ���:"
							+ nc.pub.yuyuan.utils.MaterialUtil
									.getCode(cmaterialvid)
							+ ",��������:"
							+ nc.pub.yuyuan.utils.MaterialUtil
									.getName(cmaterialvid)
							+ ",���������ͷ�ʽ���ձ�����Ϊ��,���ڶ��ձ���ά����Ӧ��Ӧ������,��Ӧ�̱��룺"
							+ ((SupplierVO) cachevoQuery
									.query(new String[] { pk_supplier })[0])
									.getCode()
							+ "��Ӧ�����ƣ�"
							+ ((SupplierVO) cachevoQuery
									.query(new String[] { pk_supplier })[0])
									.getName() + "\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("��������ʽ���ɲɹ�����������Ϣ");
					logvo.setErrordate(AppContext.getInstance().getBusiDate());
					logvo.setErrorinfo(msg);
					logvo.setPk_org(hvo.getPk_org());
					logvo.setBillno(hvo.getVdef2());
					logvo.setBill(hvo.getVdef1());
					logvo.setBillmaker(hvo.getVdef3());
					logvo.setBilldate(new UFDate(hvo.getVdef4()));
					logvo.setBillbno(null);
					logvo.setBillbmaterial(cmaterialvid);
					list3.add(logvo);
				}
			}
			// ���ú�̨�ӿڴ洢������Ϣ,��������ӿ�
			if (list3.size() > 0) {
				NCLocator.getInstance()
						.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
						.insertLogVO_RequiresNew(list3);
			}
			if (message.length() > 0) {
				BusinessException exception = new BusinessException(
						message.toString());
				throw exception;
			}
		}
		/*************************** ���ݷ�װ *******************************************/
		HashMap<String, CT_PODTO> pkmm2dto = new HashMap<String, CT_PODTO>();
		for (CT_PODTO dto : beanList) {
			String pk_mm1 = dto.getPk_mm1();
			// String[] splits = pk_mm1.split("_");
			pkmm2dto.put(pk_mm1, dto);
		}
		/*
		 * ===========================��װ����====================================
		 */
		UFDouble ntotalastnum = UFDouble.ZERO_DBL;
		UFDouble ntotalorigmny = UFDouble.ZERO_DBL;
		hvo.setPk_dept(bvos[0].getPk_reqdept());// ������
		hvo.setPk_dept_v(bvos[0].getPk_reqdept_v());// ������
		hvo.setPk_invcsupllier(beanList.get(0).getCvendorid());// ��Ʊ��Ӧ��
		hvo.setPk_supplier(beanList.get(0).getCvendorid());// ��Ӧ��
		ArrayList<String> recvstordocList = new ArrayList<String>();
		// ���ȹ���
		ScaleUtils scaleUtils = new nc.vo.pubapp.scale.ScaleUtils(
				hvo.getPk_group());
		for (OrderItemVO bvo : bvos) {
			// ��Ӧ���п��ܲ�һ����Ҫ������ȡ��Ӧ��
			String key = bvo.getPk_material() + "_" + hvo.getPk_org() + "_"
					+ hvo.getPk_supplier();
			ntotalastnum = ntotalastnum
					.add(bvo.getNnum() == null ? UFDouble.ZERO_DBL : bvo
							.getNnum());
			/* =================����ӱ�����=================================== */
			if (pkmm2dto.keySet().contains(key)) {
				CT_PODTO dto = pkmm2dto.get(key);
				/* ���Ӹ���λУ���߼� */
				String castunitid_ct = dto.getCastunitid();
				String castunitid_yy02 = bvo.getCastunitid();
				// �ɹ���ͬ��λΪ��
				if (castunitid_ct == null) {
					String msg = "��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org())
							+ ",�к�:"
							+ bvo.getCrowno()
							+ "���ϱ���:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
									.getPk_material())
							+ ",��������:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(bvo
									.getPk_material()) + "�����Ĳɹ���ͬ��λΪ��\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("��������ʽ���ɲɹ�����������Ϣ");
					logvo.setErrordate(AppContext.getInstance().getBusiDate());
					logvo.setErrorinfo(msg);
					logvo.setPk_org(hvo.getPk_org());
					logvo.setBillno(hvo.getVdef2());
					logvo.setBill(hvo.getVdef1());
					logvo.setBillmaker(hvo.getVdef3());
					logvo.setBilldate(new UFDate(hvo.getVdef4()));
					logvo.setBillbno(null);
					logvo.setBillbmaterial(bvo.getPk_material());
					list3.add(logvo);
					continue;
				}
				// ��������λΪ��
				if (castunitid_yy02 == null) {
					String msg = "��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org())
							+ ",�к�:"
							+ bvo.getCrowno()
							+ "���ϱ���:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
									.getPk_material())
							+ ",��������:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(bvo
									.getPk_material()) + "�����Ķ�������λΪ��\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("��������ʽ���ɲɹ�����������Ϣ");
					logvo.setErrordate(AppContext.getInstance().getBusiDate());
					logvo.setErrorinfo(msg);
					logvo.setPk_org(hvo.getPk_org());
					logvo.setBillno(hvo.getVdef2());
					logvo.setBill(hvo.getVdef1());
					logvo.setBillmaker(hvo.getVdef3());
					logvo.setBilldate(new UFDate(hvo.getVdef4()));
					logvo.setBillbno(null);
					logvo.setBillbmaterial(bvo.getPk_material());
					list3.add(logvo);
					continue;
				}
				// ��������ɹ���ͬ��λ��һ��
				if (!castunitid_ct.equals(castunitid_yy02)) {
					String msg = "��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",��֯����:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org())
							+ ",�к�:"
							+ bvo.getCrowno()
							+ "���ϱ���:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
									.getPk_material())
							+ ",��������:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(bvo
									.getPk_material()) + "�����Ĳɹ���ͬ��λ�붩������λ��һ��\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("��������ʽ���ɲɹ�����������Ϣ");
					logvo.setErrordate(AppContext.getInstance().getBusiDate());
					logvo.setErrorinfo(msg);
					logvo.setPk_org(hvo.getPk_org());
					logvo.setBillno(hvo.getVdef2());
					logvo.setBill(hvo.getVdef1());
					logvo.setBillmaker(hvo.getVdef3());
					logvo.setBilldate(new UFDate(hvo.getVdef4()));
					logvo.setBillbno(null);
					logvo.setBillbmaterial(bvo.getPk_material());
					list3.add(logvo);
					continue;
				}
				/* =======������֯��Ϣ========== */
				bvo.setBtriatradeflag(UFBoolean.FALSE);// ����ó��
				bvo.setCcurrencyid(dto.getCcurrencyid());// ���ұ���
				bvo.setChandler(AppContext.getInstance().getPkUser());// ����Ա
				bvo.setCrececountryid(dto.getCrececountryid());// �ջ�����
				bvo.setCsendcountryid(dto.getCsendcountryid());// ��������
				bvo.setCtaxcodeid(dto.getCtaxcodeid());// ˰��
				bvo.setCtaxcountryid(dto.getCtaxcountryid());// ��˰����
				// bvo.setDplanarrvdate(AppContext.getInstance().getBusiDate());//
				// �ƻ���������
				bvo.setFbuysellflag(dto.getFbuysellflag());// ��������
				// ��Ӫ��������
				bvo.setVbdef7(dto.getVbdef7());
				/* =============������ֵ============================= */
				bvo.setNqtorigprice(dto.getNqtorigprice());// ��˰����Nqtorigprice
				bvo.setNorigprice(dto.getNorigprice());// ����˰����norigprice
				bvo.setNqtprice(dto.getNqtprice());// ������˰����nqtprice
				bvo.setNprice(dto.getNgprice());// ��������˰����ngprice

				bvo.setNqtorignetprice(dto.getNqtorigprice());// ��˰����Nqtorigprice
				bvo.setNorignetprice(dto.getNorigprice());// ����˰����norigprice
				bvo.setNqtnetprice(dto.getNqtprice());// ������˰����Nqtprice
				bvo.setNnetprice(dto.getNgprice());// ��������˰����ngprice

				bvo.setNqtorigtaxprice(dto.getNqtorigtaxprice());// ��˰����nqtorigtaxprice
				bvo.setNorigtaxprice(dto.getNorigtaxprice());// ����˰����norigtaxprice
				bvo.setNqttaxprice(dto.getNqttaxprice());// ���Һ�˰����nqttaxprice
				bvo.setNtaxprice(dto.getNgtaxprice());// �����Һ�˰����ngtaxprice

				bvo.setNqtorigtaxnetprc(dto.getNqtorigtaxprice());// ��˰����Nqtorigtaxprice
				bvo.setNorigtaxnetprice(dto.getNorigtaxprice());// ����˰����norigtaxprice
				bvo.setNqttaxnetprice(dto.getNqttaxprice());// ���Һ�˰����nqttaxprice
				bvo.setNtaxnetprice(dto.getNgtaxprice());// �����Һ�˰����ngtaxprice

				/* ==================������====================== */
				// bvo.setNqtunitnum(bvo.getNnum() == null ? UFDouble.ZERO_DBL
				// : bvo.getNnum());// ��������
				bvo.setNqtunitnum(bvo.getNastnum() == null ? UFDouble.ZERO_DBL
						: bvo.getNastnum());// ��������
				bvo.setNtaxrate(dto.getNtaxrate());// ˰��
				bvo.setNvolumn(UFDouble.ZERO_DBL);// ���
				bvo.setNweight(UFDouble.ZERO_DBL);// ����
				bvo.setVchangerate(dto.getVchangerate());// ������
				bvo.setVqtunitrate(dto.getVqtunitrate());// ���ۻ�����
				bvo.setNexchangerate(dto.getNexchangerate());// �۱�����
				bvo.setNitemdiscountrate(new UFDouble(100));// �ۿ�
				bvo.setNmaxprice(UFDouble.ZERO_DBL);// ����޼�
				bvo.setFtaxtypeflag(1);
				/* ==================������(�����)====================== */
				// ��˰�ϼ�=��˰����*����scaleUtils
				bvo.setNorigtaxmny(scaleUtils.adjustGroupMnyScale((bvo
						.getNastnum() == null ? UFDouble.ZERO_DBL : bvo
						.getNastnum())
						.multiply((dto.getNqtorigtaxprice() == null ? UFDouble.ZERO_DBL
								: dto.getNqtorigtaxprice()))));// ��˰�ϼ�
				bvo.setNtaxmny(scaleUtils
						.adjustGroupMnyScale((bvo.getNastnum() == null ? UFDouble.ZERO_DBL
								: bvo.getNastnum()).multiply((dto
								.getNqtorigtaxprice() == null ? UFDouble.ZERO_DBL
								: dto.getNqtorigtaxprice()))));// ���Ҽ�˰�ϼ�

				// ��˰���=����λ���Ҽ�˰�ϼ�/(1+˰��)
				bvo.setNorigmny(scaleUtils.adjustGroupMnyScale((bvo
						.getNtaxmny()).div(dto.getNtaxrate().div(100).add(1))));// ��˰���
				// bvo.setNorigmny(scaleUtils.adjustGroupMnyScale((bvo
				// .getNastnum() == null ? UFDouble.ZERO_DBL : bvo
				// .getNastnum()).multiply(
				// (dto.getNqtorigtaxprice() == null ? UFDouble.ZERO_DBL
				// : dto.getNqtorigtaxprice())).div(
				// dto.getNtaxrate().div(100).add(1))));// ��˰���

				bvo.setNmny(scaleUtils.adjustGroupMnyScale((bvo.getNtaxmny())
						.div(dto.getNtaxrate().div(100).add(1))));// ������˰���

				// ��˰���=��˰���
				bvo.setNcaltaxmny(scaleUtils.adjustGroupMnyScale((bvo
						.getNtaxmny()).div(dto.getNtaxrate().div(100).add(1))));// ��˰���
				// �Ƴɱ����=��˰���
				bvo.setNcalcostmny(scaleUtils.adjustGroupMnyScale((bvo
						.getNtaxmny()).div(dto.getNtaxrate().div(100).add(1))));// �Ƴɱ����

				// ˰��=��˰�ϼ�-��˰���
				bvo.setNtax(scaleUtils.adjustGroupMnyScale(bvo.getNorigtaxmny()
						.sub(bvo.getNorigmny())));// ˰��

				// ��ͷ��˰�ϼƼ���
				ntotalorigmny = ntotalorigmny
						.add(scaleUtils.adjustGroupMnyScale(bvo
								.getNorigtaxmny() == null ? UFDouble.ZERO_DBL
								: bvo.getNorigtaxmny()));
				/* ===============��Դ������Ϣ====================== */
				bvo.setCsourcetypecode(dto.getCbilltypecode());
				bvo.setCsourcebid(dto.getPk_ct_pu_b());
				bvo.setCsourceid(dto.getPk_ct_pu());
				bvo.setVsourcecode(dto.getVbillcode());
				bvo.setVsourcetrantype(dto.getCbilltypecode());
				/* =================��������-�ֿ���Ϣ=============== */
				bvo.setVbdef1(dto.getDispatchstyle());
				// �ֿ���Ϣ
				// 3.��ȡ�ֿ���Ϣ��ֱ��ȡ�ŵ�ֿ⣬����ȡ��ת�֣�VMI����ͨ��ȫ�ֲ������òֿ�����ȡֵ
				String pk_recvstordoc = "";
				if (code2pkdefdoc.get("01").equals(dto.getDispatchstyle())) {// ֱ��
					pk_recvstordoc = bvo.getVbdef2();// �ŵ�ֿ�
					bvo.setVbdef2(null);
				} else if (code2pkdefdoc.get("02").equals(
						dto.getDispatchstyle())) {// ����
					// pk_recvstordoc = bvo.getVbdef3();// ��ת�ֿ�
					// bvo.setVbdef3(null);
					// 20171213ȡ�̶��ֿ����DIST
					String paramSql = "select t.pk_stordoc from bd_stordoc t where nvl(t.dr,0)=0 and t.enablestate = 2 and t.code = ?";
					SQLParameter sqlParameter = new SQLParameter();
					sqlParameter.addParam("DIST");
					pk_recvstordoc = NCLocator
							.getInstance()
							.lookup(IUAPQueryBS.class)
							.executeQuery(paramSql, sqlParameter,
									new ColumnProcessor()) == null ? ""
							: NCLocator
									.getInstance()
									.lookup(IUAPQueryBS.class)
									.executeQuery(paramSql, sqlParameter,
											new ColumnProcessor()).toString();
				} else if (code2pkdefdoc.get("03").equals(
						dto.getDispatchstyle())) {// VMI����
				} else if (code2pkdefdoc.get("04").equals(
						dto.getDispatchstyle())) {// Խ��
					pk_recvstordoc = bvo.getVbdef2();// �ŵ�ֿ�
					bvo.setVbdef2(null);
				} else if (code2pkdefdoc.get("05").equals(
						dto.getDispatchstyle())) {// ����
					// pk_recvstordoc = bvo.getVbdef3();// ��ת�ֿ�
					// bvo.setVbdef3(null);
					// 20171213ȡ�̶��ֿ����STOC
					String paramSql = "select t.pk_stordoc from bd_stordoc t where nvl(t.dr,0)=0 and t.enablestate = 2 and t.code = ?";
					SQLParameter sqlParameter = new SQLParameter();
					sqlParameter.addParam("STOC");
					pk_recvstordoc = NCLocator
							.getInstance()
							.lookup(IUAPQueryBS.class)
							.executeQuery(paramSql, sqlParameter,
									new ColumnProcessor()) == null ? ""
							: NCLocator
									.getInstance()
									.lookup(IUAPQueryBS.class)
									.executeQuery(paramSql, sqlParameter,
											new ColumnProcessor()).toString();
				} else if (code2pkdefdoc.get("99").equals(
						dto.getDispatchstyle())) {// ֱ�Ͳ���WMS
					pk_recvstordoc = bvo.getVbdef2();// �ŵ�ֿ�
					bvo.setVbdef2(null);
				}
				bvo.setVbdef5(dto.getDispatchstyle());
				bvo.setPk_recvstordoc(pk_recvstordoc);
				list0.add(bvo);
				recvstordocList.add(pk_recvstordoc);
			}
		}
		// �����֯���ɹ���֯��������֯
		if (recvstordocList.size() > 0) {
			// �����֯(pk_org)-��������֯���ջ������֯
			// ��������֯���ջ������֯��Ĭ�ϣ������������ɲɹ�����ʱ���ɹ���������������ġ��ջ��ֿ⡱�����ĸ������֯���ʹ��ĸ������֯��
			SqlBuilder stocSql = new SqlBuilder();
			stocSql.append("select * from bd_stordoc t where nvl(t.dr,0)=0 and t.enablestate = 2 and ");
			stocSql.append("t.pk_stordoc",
					recvstordocList.toArray(new String[0]));
			// �ɹ���֯-�������֯��default1����Ӧ��������֯��default2��
			// ���������֯��Ӧ��������֯��Ĭ�ϣ������ݲɹ�������ͷ�Ĳɹ���֯���ҵ�ϵͳ�вɹ�ҵ��ί�й�ϵ�иòɹ���֯��Ӧ�Ľ��������֯��Ӧ����֯
			SqlBuilder puSql = new SqlBuilder();
			puSql.append("select * from org_relation t where nvl(t.dr,0)=0 and t.enablestate = 2 and  t.pk_relationtype = 'PURSTOCKCONSIGN00000' and ");
			puSql.append("t.target", hvo.getPk_org());
			// ������֯-������֯��target���������֯��sourcer��
			// 3�� ������֯��Ĭ�ϣ��������ջ������֯�����������߼�1ȡ���ģ����ҵ�ϵͳ������ҵ��ί�й�ϵ�иÿ����֯��Ӧ��������֯��
			SqlBuilder sqlBuilder = new SqlBuilder();
			sqlBuilder
					.append("select * from org_relation t where nvl(t.dr,0)=0 and t.enablestate = 2 and  t.pk_relationtype = 'TRAFFICSTOCKCONSIGN0' and ");
			sqlBuilder.append("t.sourcer in (");
			sqlBuilder
					.append("select pk_org from bd_stordoc t1 where nvl(t1.dr,0)=0 and t1.enablestate = 2 and ");
			sqlBuilder.append("t1.pk_stordoc",
					recvstordocList.toArray(new String[0]));
			sqlBuilder.append(")");
			// ��ԃֵ
			List stocList = (List) getDao().executeQuery(stocSql.toString(),
					new BeanListProcessor(StordocVO.class));
			HashMap<String, StordocVO> stocmap = new HashMap<String, StordocVO>();
			if (stocList.size() > 0) {
				for (Object obj : stocList) {
					StordocVO stvo = (StordocVO) obj;
					stocmap.put(stvo.getPk_stordoc(), stvo);
				}
			}
			List puList = (List) getDao().executeQuery(puSql.toString(),
					new BeanListProcessor(OrgRelationVO.class));
			HashMap<String, OrgRelationVO> pumap = new HashMap<String, OrgRelationVO>();
			if (puList.size() > 0) {
				for (Object obj : puList) {
					OrgRelationVO orvo = (OrgRelationVO) obj;
					pumap.put(orvo.getTarget(), orvo);
				}
			}
			List wlList = (List) getDao().executeQuery(sqlBuilder.toString(),
					new BeanListProcessor(OrgRelationVO.class));
			HashMap<String, OrgRelationVO> wlmap = new HashMap<String, OrgRelationVO>();
			if (wlList.size() > 0) {
				for (Object obj : wlList) {
					OrgRelationVO orvo = (OrgRelationVO) obj;
					wlmap.put(orvo.getSourcer(), orvo);
				}
			}
			// ��ֵ
			if (list0.size() > 0) {
				for (OrderItemVO bvo : list0) {
					bvo.setPk_reqstoorg(stocmap.get(bvo.getPk_recvstordoc()) == null ? ""
							: stocmap.get(bvo.getPk_recvstordoc()).getPk_org());// ��������֯
					String vid1 = nc.pub.yuyuan.utils.OrgUtil.getVid(stocmap
							.get(bvo.getPk_recvstordoc()) == null ? ""
							: stocmap.get(bvo.getPk_recvstordoc()).getPk_org());
					bvo.setPk_reqstoorg_v(vid1);
					bvo.setPk_arrvstoorg(stocmap.get(bvo.getPk_recvstordoc()) == null ? ""
							: stocmap.get(bvo.getPk_recvstordoc()).getPk_org());// �ջ������֯
					String vid2 = nc.pub.yuyuan.utils.OrgUtil.getVid(stocmap
							.get(bvo.getPk_recvstordoc()) == null ? ""
							: stocmap.get(bvo.getPk_recvstordoc()).getPk_org());
					bvo.setPk_arrvstoorg_v(vid2);
					bvo.setPk_psfinanceorg(pumap.get(hvo.getPk_org()) == null ? ""
							: pumap.get(hvo.getPk_org()).getDefault1());// ���������֯
					String vid3 = nc.pub.yuyuan.utils.OrgUtil.getVid(pumap
							.get(hvo.getPk_org()) == null ? "" : pumap.get(
							hvo.getPk_org()).getDefault1());
					bvo.setPk_psfinanceorg_v(vid3);
					bvo.setPk_apfinanceorg(pumap.get(hvo.getPk_org()) == null ? ""
							: pumap.get(hvo.getPk_org()).getDefault2());// Ӧ��������֯
					String vid4 = nc.pub.yuyuan.utils.OrgUtil.getVid(pumap
							.get(hvo.getPk_org()) == null ? "" : pumap.get(
							hvo.getPk_org()).getDefault2());
					bvo.setPk_apfinanceorg_v(vid4);
				}
				for (OrderItemVO bvo : list0) {
					bvo.setPk_flowstockorg(wlmap.get(bvo.getPk_reqstoorg()) == null ? ""
							: wlmap.get(bvo.getPk_reqstoorg()).getTarget());
					String vid5 = nc.pub.yuyuan.utils.OrgUtil.getVid(wlmap
							.get(bvo.getPk_reqstoorg()) == null ? "" : wlmap
							.get(bvo.getPk_reqstoorg()).getTarget());
					bvo.setPk_flowstockorg_v(vid5);
				}
			}
		}

		int i = 1;
		for (OrderItemVO item : list0) {
			item.setCrowno(new Integer(10 * i).toString());
			i++;
		}
		/*
		 * ======================�����������======================================
		 */
		hvo.setPk_invcsupllier(beanList.get(0).getCvendorid());// ��Ʊ��Ӧ��
		hvo.setPk_supplier(beanList.get(0).getCvendorid());// ��Ӧ��
		hvo.setBcooptoso(UFBoolean.FALSE);// ��Эͬ�������۶���
		hvo.setBdirect(UFBoolean.FALSE);// ֱ�˲ɹ�
		hvo.setBfinalclose(UFBoolean.FALSE);// ���չر�
		hvo.setBillmaker(AppContext.getInstance().getPkUser());
		hvo.setBislatest(UFBoolean.FALSE);// �Ƿ����°汾
		hvo.setBreleasedover(UFBoolean.FALSE);// ��������
		hvo.setCorigcurrencyid(beanList.get(0).getCorigcurrencyid());// ����
		hvo.setCreator(AppContext.getInstance().getPkUser());
		// hvo.setCtrantypeid("0001A110000000002OIY");// ��������
		hvo.setDbilldate(AppContext.getInstance().getBusiDate());
		hvo.setNversion(1);// �汾
		// hvo.setPk_busitype("0001A110000000001ADF");//ҵ������
		// hvo.setVtrantypecode("21-01");// �������ͱ���
		hvo.setNtotalastnum(ntotalastnum);// ������
		hvo.setNtotalorigmny(ntotalorigmny);// ��˰�ϼ�
		// ����������Ϣ����
		String[] transTypeInfo = getTransTypeInfo(hvo.getPk_busitype(),
				POBillType.Order.getCode());
		if (transTypeInfo == null || transTypeInfo.length == 0) {
			BusinessException exception = new BusinessException(
					"��ȡ��ǰҵ��������Ŀ�굥��ҵ������ʧ��");
			throw exception;
		}
		hvo.setCtrantypeid(transTypeInfo[1]);// ������������
		hvo.setVtrantypecode(transTypeInfo[0]);// �������ͱ���

		hvo.setStatus(2);
		// �������ͷ�ʽ
		hvo.setVdef5(list0.get(0).getVbdef1());
		agg.setHVO(hvo);
		agg.setBVO(list0.toArray(new OrderItemVO[0]));

		// ���ú�̨�ӿڴ洢������Ϣ,��������ӿ�
		if (list3.size() > 0) {
			NCLocator.getInstance()
					.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
					.insertLogVO_RequiresNew(list3);
		}
		if (message.length() > 0) {
			BusinessException exception = new BusinessException(
					message.toString());
			throw exception;
		}
		return agg;
	}

	private BaseDAO dao;

	public BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	/**
	 * ����ҵ��������Ϣ��ȡ���ν���������Ϣ
	 * 
	 * @param busitype
	 *            ҵ����pk
	 * @param pk_billtype
	 *            ���ε�������pk
	 * @return
	 * @throws BusinessException
	 */
	private String[] getTransTypeInfo(String busitype, String pk_billtype)
			throws BusinessException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("	select n.pk_billtypecode, n.pk_billtypeid	")
				.append("	  from pub_billbusiness m	")
				.append("	  left join bd_billtype n	")
				.append("	    on n.pk_billtypecode = m.transtype	")
				.append("	 where nvl(m.dr, 0) = 0	")
				.append("	   and nvl(n.dr, 0) = 0	")
				.append("	   and m.pk_billtype = '" + pk_billtype + "'	")
				.append("	   and m.pk_businesstype = '" + busitype + "'	");
		Object[] executeQuery = (Object[]) getDao().executeQuery(
				buffer.toString(), new ArrayProcessor());
		if (executeQuery == null || executeQuery.length == 0
				|| executeQuery[0] == null) {
			return null;
		}
		String[] result = new String[executeQuery.length];
		for (int i = 0; i < executeQuery.length; i++) {
			result[i] = executeQuery[i].toString();
		}
		return result;
	}

}
