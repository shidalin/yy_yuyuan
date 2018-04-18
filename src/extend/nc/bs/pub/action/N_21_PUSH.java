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
 * 订货单推式生成采购订单
 * 
 * @author shidl
 * 
 */

public class N_21_PUSH extends AbstractCompiler2 {

	/**
	 * N_21_SAVEBASE 构造子注解。
	 */
	public N_21_PUSH() {
		super();
	}

	/*
	 * 备注：平台编写原始脚本
	 */
	@Override
	public String getCodeRemark() {
		return " nc.vo.pu.m21.entity.OrderVO[] inObject =\n          (nc.vo.pu.m21.entity.OrderVO[]) this.getVos();\n      nc.vo.pu.m21.context.OrderContext[] ctxs =\n          nc.vo.pu.m21.pub.OrderContextUtil.getCtxs(vo);\n      nc.vo.pu.m21.context.OrderContext ctx = null;\n      if (ctxs != null) {\n        ctx = ctxs[0];\n      }\n      return nc.bs.framework.common.NCLocator.getInstance()\n          .lookup(nc.itf.pu.m21.IOrderMaintain.class).save(inObject, ctx);\n";
	}

	/*
	 * 备注：平台编写规则类 接口执行类
	 */
	@Override
	public Object runComClass(PfParameterVO vo) throws BusinessException {
		try {
			super.m_tmpVo = vo;
			nc.vo.pu.m21.entity.OrderVO[] inObject = (nc.vo.pu.m21.entity.OrderVO[]) this
					.getVos();
			fillData(inObject);
			// 根据表体自定义项5（配送方式拆分aggVO）
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
	 * 根据表体自定义项5（配送方式）拆分aggVo
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
	 * 关联业务逻辑填充数据
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
		// 子表信息
		ArrayList<OrderItemVO> list0 = new ArrayList<OrderItemVO>();
		// 错误日志vo集合
		ArrayList<YyErrorLogVO> list3 = new ArrayList<YyErrorLogVO>();
		// 错误信息校验(前台显示)
		StringBuffer message = new StringBuffer();
		ArrayList<String> materialidList = new ArrayList<String>();
		HashMap<String, List<OrderItemVO>> mterial2vo = new HashMap<String, List<OrderItemVO>>();
		if (bvos == null || bvos.length == 0) {
			return null;
		}
		// 查询配送方式自定义档案表
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
		// 物料主键集合,批量查询
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
		// 1.根据[物料+组织]，在采购合同上获取[供应商+单价]
		// 2.根据物料+组织+供应商，在对照表获取配送方式
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
		// 整单没有关联采购合同或者物料对照表
		if (beanList == null || beanList.size() == 0) {
			// 2017-02-08-修订-增加判断是没有采购合同还是没有物料对照表-同时报错具体物料
			// 配送方式查询
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
			// 关联合同查询
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
			// 获取物料信息
			StringBuffer buffer2 = new StringBuffer();
			for (String str : materialidList) {
				buffer2.append("物料编码:"
						+ nc.pub.yuyuan.utils.MaterialUtil.getCode(str)
						+ ",物料名称:"
						+ nc.pub.yuyuan.utils.MaterialUtil.getName(str) + "\n");
			}
			// 1.全部没有配送方式
			if (styleList.size() == 0) {
				for (String str : materialidList) {
					String msg = "单据号:"
							+ hvo.getVdef2()
							+ "组织编码:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",组织名称:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org()) + ",物料编码:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(str)
							+ ",物料名称:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(str)
							+ ",关联的配送方式对照表数据为空\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("订货单推式生成采购订单错误信息");
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
			// 2.全部没有关联合同
			if (ctList.size() == 0) {
				for (String str : materialidList) {
					String msg = "单据号:"
							+ hvo.getVdef2()
							+ "组织编码:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",组织名称:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org()) + "物料编码:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(str)
							+ ",物料名称:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(str)
							+ "关联的采购合同数据为空\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("订货单推式生成采购订单错误信息");
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
			// 对照表数据和合同数据匹配不上,以合同数据为基准,提示缺少对照表数据
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
					String msg = "单据号:"
							+ hvo.getVdef2()
							+ "组织编码:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(pk_org)
							+ ",组织名称:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(pk_org)
							+ ",物料编码:"
							+ nc.pub.yuyuan.utils.MaterialUtil
									.getCode(cmaterialvid)
							+ ",物料名称:"
							+ nc.pub.yuyuan.utils.MaterialUtil
									.getName(cmaterialvid)
							+ ",关联的配送方式对照表数据为空,请在对照表中维护相应供应商数据,供应商编码："
							+ ((SupplierVO) cachevoQuery
									.query(new String[] { pk_supplier })[0])
									.getCode()
							+ "供应商名称："
							+ ((SupplierVO) cachevoQuery
									.query(new String[] { pk_supplier })[0])
									.getName() + "\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("订货单推式生成采购订单错误信息");
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
			// 调用后台接口存储错误信息,独立事务接口
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
		/*************************** 数据封装 *******************************************/
		HashMap<String, CT_PODTO> pkmm2dto = new HashMap<String, CT_PODTO>();
		for (CT_PODTO dto : beanList) {
			String pk_mm1 = dto.getPk_mm1();
			// String[] splits = pk_mm1.split("_");
			pkmm2dto.put(pk_mm1, dto);
		}
		/*
		 * ===========================封装数据====================================
		 */
		UFDouble ntotalastnum = UFDouble.ZERO_DBL;
		UFDouble ntotalorigmny = UFDouble.ZERO_DBL;
		hvo.setPk_dept(bvos[0].getPk_reqdept());// 需求部门
		hvo.setPk_dept_v(bvos[0].getPk_reqdept_v());// 需求部门
		hvo.setPk_invcsupllier(beanList.get(0).getCvendorid());// 开票供应商
		hvo.setPk_supplier(beanList.get(0).getCvendorid());// 供应商
		ArrayList<String> recvstordocList = new ArrayList<String>();
		// 精度工具
		ScaleUtils scaleUtils = new nc.vo.pubapp.scale.ScaleUtils(
				hvo.getPk_group());
		for (OrderItemVO bvo : bvos) {
			// 供应商有可能不一样，要用物料取供应商
			String key = bvo.getPk_material() + "_" + hvo.getPk_org() + "_"
					+ hvo.getPk_supplier();
			ntotalastnum = ntotalastnum
					.add(bvo.getNnum() == null ? UFDouble.ZERO_DBL : bvo
							.getNnum());
			/* =================填充子表数据=================================== */
			if (pkmm2dto.keySet().contains(key)) {
				CT_PODTO dto = pkmm2dto.get(key);
				/* 增加辅单位校验逻辑 */
				String castunitid_ct = dto.getCastunitid();
				String castunitid_yy02 = bvo.getCastunitid();
				// 采购合同单位为空
				if (castunitid_ct == null) {
					String msg = "组织编码:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",组织名称:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org())
							+ ",行号:"
							+ bvo.getCrowno()
							+ "物料编码:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
									.getPk_material())
							+ ",物料名称:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(bvo
									.getPk_material()) + "关联的采购合同单位为空\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("订货单推式生成采购订单错误信息");
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
				// 订货单单位为空
				if (castunitid_yy02 == null) {
					String msg = "组织编码:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",组织名称:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org())
							+ ",行号:"
							+ bvo.getCrowno()
							+ "物料编码:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
									.getPk_material())
							+ ",物料名称:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(bvo
									.getPk_material()) + "关联的订货单单位为空\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("订货单推式生成采购订单错误信息");
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
				// 订货单与采购合同单位不一致
				if (!castunitid_ct.equals(castunitid_yy02)) {
					String msg = "组织编码:"
							+ nc.pub.yuyuan.utils.OrgUtil.getCode(hvo
									.getPk_org())
							+ ",组织名称:"
							+ nc.pub.yuyuan.utils.OrgUtil.getName(hvo
									.getPk_org())
							+ ",行号:"
							+ bvo.getCrowno()
							+ "物料编码:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
									.getPk_material())
							+ ",物料名称:"
							+ nc.pub.yuyuan.utils.MaterialUtil.getName(bvo
									.getPk_material()) + "关联的采购合同单位与订货单单位不一致\n";
					message.append(msg);
					YyErrorLogVO logvo = new YyErrorLogVO();
					logvo.setErrortyle("订货单推式生成采购订单错误信息");
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
				/* =======财务组织信息========== */
				bvo.setBtriatradeflag(UFBoolean.FALSE);// 三角贸易
				bvo.setCcurrencyid(dto.getCcurrencyid());// 本币币种
				bvo.setChandler(AppContext.getInstance().getPkUser());// 操作员
				bvo.setCrececountryid(dto.getCrececountryid());// 收货国家
				bvo.setCsendcountryid(dto.getCsendcountryid());// 发货国家
				bvo.setCtaxcodeid(dto.getCtaxcodeid());// 税码
				bvo.setCtaxcountryid(dto.getCtaxcountryid());// 报税国家
				// bvo.setDplanarrvdate(AppContext.getInstance().getBusiDate());//
				// 计划到货日期
				bvo.setFbuysellflag(dto.getFbuysellflag());// 购销类型
				// 运营中心名称
				bvo.setVbdef7(dto.getVbdef7());
				/* =============单价数值============================= */
				bvo.setNqtorigprice(dto.getNqtorigprice());// 无税单价Nqtorigprice
				bvo.setNorigprice(dto.getNorigprice());// 主无税单价norigprice
				bvo.setNqtprice(dto.getNqtprice());// 本币无税单价nqtprice
				bvo.setNprice(dto.getNgprice());// 主本币无税单价ngprice

				bvo.setNqtorignetprice(dto.getNqtorigprice());// 无税净价Nqtorigprice
				bvo.setNorignetprice(dto.getNorigprice());// 主无税净价norigprice
				bvo.setNqtnetprice(dto.getNqtprice());// 本币无税净价Nqtprice
				bvo.setNnetprice(dto.getNgprice());// 主本币无税净价ngprice

				bvo.setNqtorigtaxprice(dto.getNqtorigtaxprice());// 含税单价nqtorigtaxprice
				bvo.setNorigtaxprice(dto.getNorigtaxprice());// 主含税单价norigtaxprice
				bvo.setNqttaxprice(dto.getNqttaxprice());// 本币含税单价nqttaxprice
				bvo.setNtaxprice(dto.getNgtaxprice());// 主本币含税单价ngtaxprice

				bvo.setNqtorigtaxnetprc(dto.getNqtorigtaxprice());// 含税净价Nqtorigtaxprice
				bvo.setNorigtaxnetprice(dto.getNorigtaxprice());// 主含税净价norigtaxprice
				bvo.setNqttaxnetprice(dto.getNqttaxprice());// 本币含税净价nqttaxprice
				bvo.setNtaxnetprice(dto.getNgtaxprice());// 主本币含税净价ngtaxprice

				/* ==================换算率====================== */
				// bvo.setNqtunitnum(bvo.getNnum() == null ? UFDouble.ZERO_DBL
				// : bvo.getNnum());// 报价数量
				bvo.setNqtunitnum(bvo.getNastnum() == null ? UFDouble.ZERO_DBL
						: bvo.getNastnum());// 报价数量
				bvo.setNtaxrate(dto.getNtaxrate());// 税率
				bvo.setNvolumn(UFDouble.ZERO_DBL);// 体积
				bvo.setNweight(UFDouble.ZERO_DBL);// 重量
				bvo.setVchangerate(dto.getVchangerate());// 换算率
				bvo.setVqtunitrate(dto.getVqtunitrate());// 报价换算率
				bvo.setNexchangerate(dto.getNexchangerate());// 折本汇率
				bvo.setNitemdiscountrate(new UFDouble(100));// 折扣
				bvo.setNmaxprice(UFDouble.ZERO_DBL);// 最高限价
				bvo.setFtaxtypeflag(1);
				/* ==================计算金额(无外币)====================== */
				// 价税合计=含税净价*数量scaleUtils
				bvo.setNorigtaxmny(scaleUtils.adjustGroupMnyScale((bvo
						.getNastnum() == null ? UFDouble.ZERO_DBL : bvo
						.getNastnum())
						.multiply((dto.getNqtorigtaxprice() == null ? UFDouble.ZERO_DBL
								: dto.getNqtorigtaxprice()))));// 价税合计
				bvo.setNtaxmny(scaleUtils
						.adjustGroupMnyScale((bvo.getNastnum() == null ? UFDouble.ZERO_DBL
								: bvo.getNastnum()).multiply((dto
								.getNqtorigtaxprice() == null ? UFDouble.ZERO_DBL
								: dto.getNqtorigtaxprice()))));// 本币价税合计

				// 无税金额=主单位本币价税合计/(1+税率)
				bvo.setNorigmny(scaleUtils.adjustGroupMnyScale((bvo
						.getNtaxmny()).div(dto.getNtaxrate().div(100).add(1))));// 无税金额
				// bvo.setNorigmny(scaleUtils.adjustGroupMnyScale((bvo
				// .getNastnum() == null ? UFDouble.ZERO_DBL : bvo
				// .getNastnum()).multiply(
				// (dto.getNqtorigtaxprice() == null ? UFDouble.ZERO_DBL
				// : dto.getNqtorigtaxprice())).div(
				// dto.getNtaxrate().div(100).add(1))));// 无税金额

				bvo.setNmny(scaleUtils.adjustGroupMnyScale((bvo.getNtaxmny())
						.div(dto.getNtaxrate().div(100).add(1))));// 本币无税金额

				// 计税金额=无税金额
				bvo.setNcaltaxmny(scaleUtils.adjustGroupMnyScale((bvo
						.getNtaxmny()).div(dto.getNtaxrate().div(100).add(1))));// 计税金额
				// 计成本金额=无税金额
				bvo.setNcalcostmny(scaleUtils.adjustGroupMnyScale((bvo
						.getNtaxmny()).div(dto.getNtaxrate().div(100).add(1))));// 计成本金额

				// 税额=价税合计-无税金额
				bvo.setNtax(scaleUtils.adjustGroupMnyScale(bvo.getNorigtaxmny()
						.sub(bvo.getNorigmny())));// 税额

				// 表头价税合计计算
				ntotalorigmny = ntotalorigmny
						.add(scaleUtils.adjustGroupMnyScale(bvo
								.getNorigtaxmny() == null ? UFDouble.ZERO_DBL
								: bvo.getNorigtaxmny()));
				/* ===============来源单据信息====================== */
				bvo.setCsourcetypecode(dto.getCbilltypecode());
				bvo.setCsourcebid(dto.getPk_ct_pu_b());
				bvo.setCsourceid(dto.getPk_ct_pu());
				bvo.setVsourcecode(dto.getVbillcode());
				bvo.setVsourcetrantype(dto.getCbilltypecode());
				/* =================配送类型-仓库信息=============== */
				bvo.setVbdef1(dto.getDispatchstyle());
				// 仓库信息
				// 3.获取仓库信息，直送取门店仓库，配送取中转仓，VMI配送通过全局参数配置仓库数据取值
				String pk_recvstordoc = "";
				if (code2pkdefdoc.get("01").equals(dto.getDispatchstyle())) {// 直送
					pk_recvstordoc = bvo.getVbdef2();// 门店仓库
					bvo.setVbdef2(null);
				} else if (code2pkdefdoc.get("02").equals(
						dto.getDispatchstyle())) {// 配送
					// pk_recvstordoc = bvo.getVbdef3();// 中转仓库
					// bvo.setVbdef3(null);
					// 20171213取固定仓库编码DIST
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
						dto.getDispatchstyle())) {// VMI配送
				} else if (code2pkdefdoc.get("04").equals(
						dto.getDispatchstyle())) {// 越库
					pk_recvstordoc = bvo.getVbdef2();// 门店仓库
					bvo.setVbdef2(null);
				} else if (code2pkdefdoc.get("05").equals(
						dto.getDispatchstyle())) {// 备货
					// pk_recvstordoc = bvo.getVbdef3();// 中转仓库
					// bvo.setVbdef3(null);
					// 20171213取固定仓库编码STOC
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
						dto.getDispatchstyle())) {// 直送不传WMS
					pk_recvstordoc = bvo.getVbdef2();// 门店仓库
					bvo.setVbdef2(null);
				}
				bvo.setVbdef5(dto.getDispatchstyle());
				bvo.setPk_recvstordoc(pk_recvstordoc);
				list0.add(bvo);
				recvstordocList.add(pk_recvstordoc);
			}
		}
		// 库存组织、采购组织、物流组织
		if (recvstordocList.size() > 0) {
			// 库存组织(pk_org)-需求库存组织、收货库存组织
			// 需求库存组织、收货库存组织（默认）：订货单生成采购订单时，采购订单表体带过来的“收货仓库”属于哪个库存组织，就带哪个库存组织。
			SqlBuilder stocSql = new SqlBuilder();
			stocSql.append("select * from bd_stordoc t where nvl(t.dr,0)=0 and t.enablestate = 2 and ");
			stocSql.append("t.pk_stordoc",
					recvstordocList.toArray(new String[0]));
			// 采购组织-算财务组织（default1）、应付财务组织（default2）
			// 结算财务组织、应付财务组织（默认）：根据采购订单表头的采购组织，找到系统中采购业务委托关系中该采购组织对应的结算财务组织、应付组织
			SqlBuilder puSql = new SqlBuilder();
			puSql.append("select * from org_relation t where nvl(t.dr,0)=0 and t.enablestate = 2 and  t.pk_relationtype = 'PURSTOCKCONSIGN00000' and ");
			puSql.append("t.target", hvo.getPk_org());
			// 物流组织-物流组织（target）、库存组织（sourcer）
			// 3、 物流组织（默认）：根据收货库存组织（按照上述逻辑1取到的），找到系统中物流业务委托关系中该库存组织对应的物流组织。
			SqlBuilder sqlBuilder = new SqlBuilder();
			sqlBuilder
					.append("select * from org_relation t where nvl(t.dr,0)=0 and t.enablestate = 2 and  t.pk_relationtype = 'TRAFFICSTOCKCONSIGN0' and ");
			sqlBuilder.append("t.sourcer in (");
			sqlBuilder
					.append("select pk_org from bd_stordoc t1 where nvl(t1.dr,0)=0 and t1.enablestate = 2 and ");
			sqlBuilder.append("t1.pk_stordoc",
					recvstordocList.toArray(new String[0]));
			sqlBuilder.append(")");
			// 查詢值
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
			// 赋值
			if (list0.size() > 0) {
				for (OrderItemVO bvo : list0) {
					bvo.setPk_reqstoorg(stocmap.get(bvo.getPk_recvstordoc()) == null ? ""
							: stocmap.get(bvo.getPk_recvstordoc()).getPk_org());// 需求库存组织
					String vid1 = nc.pub.yuyuan.utils.OrgUtil.getVid(stocmap
							.get(bvo.getPk_recvstordoc()) == null ? ""
							: stocmap.get(bvo.getPk_recvstordoc()).getPk_org());
					bvo.setPk_reqstoorg_v(vid1);
					bvo.setPk_arrvstoorg(stocmap.get(bvo.getPk_recvstordoc()) == null ? ""
							: stocmap.get(bvo.getPk_recvstordoc()).getPk_org());// 收货库存组织
					String vid2 = nc.pub.yuyuan.utils.OrgUtil.getVid(stocmap
							.get(bvo.getPk_recvstordoc()) == null ? ""
							: stocmap.get(bvo.getPk_recvstordoc()).getPk_org());
					bvo.setPk_arrvstoorg_v(vid2);
					bvo.setPk_psfinanceorg(pumap.get(hvo.getPk_org()) == null ? ""
							: pumap.get(hvo.getPk_org()).getDefault1());// 结算财务组织
					String vid3 = nc.pub.yuyuan.utils.OrgUtil.getVid(pumap
							.get(hvo.getPk_org()) == null ? "" : pumap.get(
							hvo.getPk_org()).getDefault1());
					bvo.setPk_psfinanceorg_v(vid3);
					bvo.setPk_apfinanceorg(pumap.get(hvo.getPk_org()) == null ? ""
							: pumap.get(hvo.getPk_org()).getDefault2());// 应付财务组织
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
		 * ======================填充主表数据======================================
		 */
		hvo.setPk_invcsupllier(beanList.get(0).getCvendorid());// 开票供应商
		hvo.setPk_supplier(beanList.get(0).getCvendorid());// 供应商
		hvo.setBcooptoso(UFBoolean.FALSE);// 已协同生成销售订单
		hvo.setBdirect(UFBoolean.FALSE);// 直运采购
		hvo.setBfinalclose(UFBoolean.FALSE);// 最终关闭
		hvo.setBillmaker(AppContext.getInstance().getPkUser());
		hvo.setBislatest(UFBoolean.FALSE);// 是否最新版本
		hvo.setBreleasedover(UFBoolean.FALSE);// 曾经发布
		hvo.setCorigcurrencyid(beanList.get(0).getCorigcurrencyid());// 币种
		hvo.setCreator(AppContext.getInstance().getPkUser());
		// hvo.setCtrantypeid("0001A110000000002OIY");// 单据类型
		hvo.setDbilldate(AppContext.getInstance().getBusiDate());
		hvo.setNversion(1);// 版本
		// hvo.setPk_busitype("0001A110000000001ADF");//业务类型
		// hvo.setVtrantypecode("21-01");// 交易类型编码
		hvo.setNtotalastnum(ntotalastnum);// 总数量
		hvo.setNtotalorigmny(ntotalorigmny);// 价税合计
		// 交易类型信息设置
		String[] transTypeInfo = getTransTypeInfo(hvo.getPk_busitype(),
				POBillType.Order.getCode());
		if (transTypeInfo == null || transTypeInfo.length == 0) {
			BusinessException exception = new BusinessException(
					"获取当前业务流程中目标单据业务类型失败");
			throw exception;
		}
		hvo.setCtrantypeid(transTypeInfo[1]);// 交易类型主键
		hvo.setVtrantypecode(transTypeInfo[0]);// 交易类型编码

		hvo.setStatus(2);
		// 设置配送方式
		hvo.setVdef5(list0.get(0).getVbdef1());
		agg.setHVO(hvo);
		agg.setBVO(list0.toArray(new OrderItemVO[0]));

		// 调用后台接口存储错误信息,独立事务接口
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
	 * 根据业务流程信息获取下游交易类型信息
	 * 
	 * @param busitype
	 *            业务流pk
	 * @param pk_billtype
	 *            下游单据类型pk
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
