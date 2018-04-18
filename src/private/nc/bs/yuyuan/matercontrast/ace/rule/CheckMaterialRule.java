package nc.bs.yuyuan.matercontrast.ace.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.pubimpl.bdlayer.cache.CacheVOQuery;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.pub.SqlBuilder;
import nc.vo.yuyuan.yy_errorlog.YyErrorLogVO;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;
import nc.vo.yuyuan.yy_matercontrast.YyMaterContrastBVO;

/**
 * 物料校验规则：校验逻辑[供应商+物料+门店]确定[配送方式]唯一性
 * 两重校验：1.界面数据【物料+配送方式】唯一2.历史数据【供应商+门店+物料+配送方式】不存在
 * 
 * @author shidl
 * 
 */
public class CheckMaterialRule implements IRule<AggYyMaterContrastVO> {

	@Override
	public void process(AggYyMaterContrastVO[] objs) {
		StringBuffer msg = new StringBuffer();
		ArrayList<YyErrorLogVO> list2 = new ArrayList<YyErrorLogVO>();
		for (AggYyMaterContrastVO agg : objs) {
			// 供应商
			String pk_supplier = agg.getParentVO().getPk_supplier();
			// 门店
			String pk_org = agg.getParentVO().getPk_org();
			CircularlyAccessibleValueObject[] bvos = agg.getChildrenVO();
			Map<String, CircularlyAccessibleValueObject> bvoMap = new HashMap<String, CircularlyAccessibleValueObject>();
			Map<String, List<CircularlyAccessibleValueObject>> errorBvoMap = new HashMap<String, List<CircularlyAccessibleValueObject>>();
			List<String> errorMsg = null;
			// 校验一:截面数据处理
			for (CircularlyAccessibleValueObject bvo : bvos) {
				String pk_material = bvo.getAttributeValue("cmaterialvid") == null ? ""
						: bvo.getAttributeValue("cmaterialvid").toString();
				String dispatchstyle = bvo.getAttributeValue("dispatchstyle") == null ? ""
						: bvo.getAttributeValue("dispatchstyle").toString();
				String key = pk_supplier + "_" + pk_org + "_" + pk_material
						+ "_" + dispatchstyle;
				if (!bvoMap.keySet().contains(key)) {
					bvoMap.put(key, bvo);
				} else {
					List<CircularlyAccessibleValueObject> value = null;
					if (errorBvoMap.keySet().contains(key)) {
						value = errorBvoMap.get(key);
					} else {
						value = new ArrayList<CircularlyAccessibleValueObject>();
					}
					value.add(bvo);
					errorBvoMap.put(key, value);
				}
			}

			// 校验二:数据库数据
			List<String> whereParamList = new ArrayList<String>();
			whereParamList.addAll(bvoMap.keySet());
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("	select count(*) count ,t.keyword key ")
						.append("	  from (select t1.pk_supplier || '_' || t1.pk_org || '_' || t2.cmaterialvid || '_' ||	")
						.append("	               t2.dispatchstyle keyword	")
						.append("	          from yy_matercontrast t1	")
						.append("	         inner join yy_matercontrast_b t2	")
						.append("	            on t1.pk_matercontrast = t2.pk_matercontrast	")
						.append("	         where nvl(t1.dr, 0) = 0	")
						.append("	           and nvl(t2.dr, 0) = 0) t	where ")
						.append(nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
								"t.keyword", whereParamList, true))
						.append("	 group by t.keyword	");
				BaseDAO dao = new BaseDAO();
				List<Map<Object, Object>> executeQuery = (List<Map<Object, Object>>) dao
						.executeQuery(sb.toString(), new MapListProcessor());
				if (executeQuery != null && executeQuery.size() > 0) {
					for (int i = 0; i < executeQuery.size(); i++) {
						Map<Object, Object> param = (Map<Object, Object>) executeQuery
								.get(i);
						Integer count = Integer.parseInt(param.get("count")
								.toString());
						String key = param.get("key").toString();
						if (count > 1) {
							YyMaterContrastBVO bvo = (YyMaterContrastBVO) bvoMap
									.get(key);
							List<CircularlyAccessibleValueObject> value = null;
							if (errorBvoMap.keySet().contains(key)) {
								value = errorBvoMap.get(key);
							} else {
								value = new ArrayList<CircularlyAccessibleValueObject>();
							}
							value.add(bvo);
							errorBvoMap.put(key, value);
						}
					}
				}
				if (errorBvoMap.keySet().size() > 0) {
					for (String key : errorBvoMap.keySet()) {
						List<CircularlyAccessibleValueObject> list = errorBvoMap
								.get(key);
						for (CircularlyAccessibleValueObject cirvo : list) {
							YyMaterContrastBVO bvo = (YyMaterContrastBVO) cirvo;
							YyErrorLogVO logvo = new YyErrorLogVO();
							logvo.setErrortyle("物料对照表配送方式唯一性校验");
							logvo.setErrordate(AppContext.getInstance()
									.getBusiDate());
							String message = "组织:"
									+ agg.getParentVO().getPk_org()
									+ ",供应商:"
									+ agg.getParentVO().getPk_supplier()
									+ ",物料编码:"
									+ nc.pub.yuyuan.utils.MaterialUtil
											.getCode(bvo.getCmaterialvid())
									+ ",配送方式:" + bvo.getDispatchstyle()
									+ ",唯一性校验错误\n";
							logvo.setErrorinfo(message);
							logvo.setPk_org(agg.getParentVO().getPk_org());
							logvo.setBillno(agg.getParentVO().getVbillcode());
							logvo.setBill(agg.getParentVO()
									.getPk_billtypecode());
							logvo.setBillmaker(agg.getParentVO().getBillmaker());
							logvo.setBilldate(agg.getParentVO().getDmakedate());
							logvo.setBillbno(bvo.getCrowno());
							logvo.setBillbmaterial(bvo.getCmaterialvid());
							list2.add(logvo);
						}
					}
					errorMsg = getMessage(errorBvoMap, agg);
					msg.append(errorMsg);
				}
			} catch (BusinessException e) {
				ExceptionUtils.wrappBusinessException(e.getMessage());
			}
		}

		// 调用后台接口存储错误信息,独立事务接口
		if (list2.size() > 0) {
			try {
				NCLocator.getInstance()
						.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
						.insertLogVO_RequiresNew(list2);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				ExceptionUtils.wrappBusinessException(e.getMessage());
			}
		}
		/**
		 * 界面展现错误信息
		 */
		if (msg.length() > 0) {
			ExceptionUtils.wrappBusinessException(msg.toString());
		}

	}

	/**
	 * 解析错误信息
	 * 
	 * @param errorBvoMap
	 * @return
	 * @throws BusinessException
	 */
	private List<String> getMessage(
			Map<String, List<CircularlyAccessibleValueObject>> errorBvoMap,
			AggYyMaterContrastVO agg) throws BusinessException {
		ArrayList<String> list2 = new ArrayList<String>();
		if (errorBvoMap.values().size() > 0) {
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
			BaseDAO baseDAO = new BaseDAO();
			List executeQuery = (List) baseDAO.executeQuery(
					defdocSqlBuilder.toString(), new MapListProcessor());
			HashMap<String, String> pkdefdoc2code = new HashMap<String, String>();
			if (executeQuery != null && executeQuery.size() > 0) {
				for (Object obj : executeQuery) {
					Map map = (Map) obj;
					String code = map.get("code") == null ? "" : map
							.get("code").toString();
					String pk_defdoc = map.get("code") == null ? "" : map.get(
							"pk_defdoc").toString();
					pkdefdoc2code.put(pk_defdoc,code);
				}
			}
			CacheVOQuery cachevoQuery = new CacheVOQuery(SupplierVO.class,
					new String[] { "code", "name" });
			for (List<CircularlyAccessibleValueObject> list : errorBvoMap
					.values()) {
				if (list != null && list.size() > 0) {
					for (CircularlyAccessibleValueObject cirvo : list) {
						YyMaterContrastBVO bvo = (YyMaterContrastBVO) cirvo;
						SupplierVO[] suppliervos = (SupplierVO[]) cachevoQuery
								.query(new String[] { agg.getParentVO()
										.getPk_supplier() });
						String supplier_code = "";
						if (suppliervos != null && suppliervos.length > 0) {
							supplier_code = suppliervos[0].getCode();
						}
						String message = "组织编码:"
								+ nc.pub.yuyuan.utils.OrgUtil.getCode(agg
										.getParentVO().getPk_org())
								+ ",供应商编码:"
								+ supplier_code
								+ ",物料编码:"
								+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
										.getCmaterialvid()) + ",配送方式:"
								+ pkdefdoc2code.get(bvo.getDispatchstyle())
								+ ",唯一性校验错误\n";
						list2.add(message);
					}
				}
			}
		}
		return list2;
	}
}
