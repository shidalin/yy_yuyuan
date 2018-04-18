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
 * ����У�����У���߼�[��Ӧ��+����+�ŵ�]ȷ��[���ͷ�ʽ]Ψһ��
 * ����У�飺1.�������ݡ�����+���ͷ�ʽ��Ψһ2.��ʷ���ݡ���Ӧ��+�ŵ�+����+���ͷ�ʽ��������
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
			// ��Ӧ��
			String pk_supplier = agg.getParentVO().getPk_supplier();
			// �ŵ�
			String pk_org = agg.getParentVO().getPk_org();
			CircularlyAccessibleValueObject[] bvos = agg.getChildrenVO();
			Map<String, CircularlyAccessibleValueObject> bvoMap = new HashMap<String, CircularlyAccessibleValueObject>();
			Map<String, List<CircularlyAccessibleValueObject>> errorBvoMap = new HashMap<String, List<CircularlyAccessibleValueObject>>();
			List<String> errorMsg = null;
			// У��һ:�������ݴ���
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

			// У���:���ݿ�����
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
							logvo.setErrortyle("���϶��ձ����ͷ�ʽΨһ��У��");
							logvo.setErrordate(AppContext.getInstance()
									.getBusiDate());
							String message = "��֯:"
									+ agg.getParentVO().getPk_org()
									+ ",��Ӧ��:"
									+ agg.getParentVO().getPk_supplier()
									+ ",���ϱ���:"
									+ nc.pub.yuyuan.utils.MaterialUtil
											.getCode(bvo.getCmaterialvid())
									+ ",���ͷ�ʽ:" + bvo.getDispatchstyle()
									+ ",Ψһ��У�����\n";
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

		// ���ú�̨�ӿڴ洢������Ϣ,��������ӿ�
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
		 * ����չ�ִ�����Ϣ
		 */
		if (msg.length() > 0) {
			ExceptionUtils.wrappBusinessException(msg.toString());
		}

	}

	/**
	 * ����������Ϣ
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
						String message = "��֯����:"
								+ nc.pub.yuyuan.utils.OrgUtil.getCode(agg
										.getParentVO().getPk_org())
								+ ",��Ӧ�̱���:"
								+ supplier_code
								+ ",���ϱ���:"
								+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
										.getCmaterialvid()) + ",���ͷ�ʽ:"
								+ pkdefdoc2code.get(bvo.getDispatchstyle())
								+ ",Ψһ��У�����\n";
						list2.add(message);
					}
				}
			}
		}
		return list2;
	}
}
