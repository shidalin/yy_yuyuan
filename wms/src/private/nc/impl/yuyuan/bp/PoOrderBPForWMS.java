package nc.impl.yuyuan.bp;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.pub.SqlBuilder;
import nc.vo.yuyuan.PoOrderBDTOForWMS;
import nc.vo.yuyuan.PoOrderDTOForWMS;
import nc.vo.yuyuan.PoOrderHDTOForWMS;
import nc.vo.yuyuan.PoOrderViewVOForWMS;
import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtilsBean;

public class PoOrderBPForWMS {
	String flagForPoOrder = "vdef20";
	private BaseDAO baseDAO;

	/**
	 * 查询数据
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<PoOrderDTOForWMS> queryPoOrderForWMS(String pk_order)
			throws BusinessException, IllegalAccessException,
			InvocationTargetException {
		// 查询配送方式自定义档案
		SqlBuilder defdocSqlBuilder = new SqlBuilder();
		defdocSqlBuilder.append("	select tt.pk_defdoc pk_defdoc	");
		defdocSqlBuilder.append("	  from bd_defdoc tt	");
		defdocSqlBuilder.append("	 inner join bd_defdoclist tt1	");
		defdocSqlBuilder
				.append("	    on tt.pk_defdoclist = tt1.pk_defdoclist	");
		defdocSqlBuilder.append("	 where tt1.code = 'PO_01'	");
		defdocSqlBuilder.append("	   and nvl(tt.dr, 0) = 0	");
		defdocSqlBuilder.append("	   and nvl(tt1.dr, 0) = 0	");
		defdocSqlBuilder.append("	   and tt.enablestate = 2	");
		defdocSqlBuilder.append("	   and tt.code = '99'	");

		// 查询运营中心自定义档案
		SqlBuilder defdocSqlBuildelFordef7 = new SqlBuilder();
		defdocSqlBuildelFordef7
				.append("	select ss.pk_defdoc pk_defdocfordef7,ss.name defdocnamefordef7	");
		defdocSqlBuildelFordef7.append("	  from bd_defdoc ss	");
		defdocSqlBuildelFordef7.append("	 inner join bd_defdoclist ss1	");
		defdocSqlBuildelFordef7
				.append("	    on ss.pk_defdoclist = ss1.pk_defdoclist	");
		defdocSqlBuildelFordef7.append("	 where ss1.code = 'WMS02'	");
		defdocSqlBuildelFordef7.append("	   and nvl(ss.dr, 0) = 0	");
		defdocSqlBuildelFordef7.append("	   and nvl(ss1.dr, 0) = 0	");
		defdocSqlBuildelFordef7.append("	   and ss.enablestate = 2	");

		SqlBuilder defdocSqlBuilderAll = new SqlBuilder();
		defdocSqlBuilderAll
				.append("	select tm.pk_defdoc pk_defdoc,tm.code code	");
		defdocSqlBuilderAll.append("	  from bd_defdoc tm	");
		defdocSqlBuilderAll.append("	 inner join bd_defdoclist tm1	");
		defdocSqlBuilderAll
				.append("	    on tm.pk_defdoclist = tm1.pk_defdoclist	");
		defdocSqlBuilderAll.append("	 where tm1.code = 'PO_01'	");
		defdocSqlBuilderAll.append("	   and nvl(tm.dr, 0) = 0	");
		defdocSqlBuilderAll.append("	   and nvl(tm1.dr, 0) = 0	");
		defdocSqlBuilderAll.append("	   and tm.enablestate = 2	");
		// 增加过滤
		// 订单类型等于 21-Cxx-01
		// 配送方式 vdef5 !=99
		// 非退货 breturn ！= ‘Y’
		// 最终关闭 bfinalclose ！=‘Y’ 20180225
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("	  select distinct t8.defdocnamefordef7 managementOffice,	");
		stringBuffer.append("	                  t.dbilldate dbilldate,	");
		stringBuffer.append("	                  t2.code pk_supplier,	");
		stringBuffer.append("	                  t.vbillcode vbillcode,	");
		stringBuffer.append("	                  t.vmemo vmemo,	");
		stringBuffer
				.append("	                  t3.code||'_'||t5.code pk_org,	");
		stringBuffer.append("	                  t6.code vdef5,	");
		stringBuffer.append("	                  'SHZC1098' customerCode,	");
		stringBuffer
				.append("	                  t1.dplanarrvdate dplanarrvdate,	");
		stringBuffer.append("	                  t.vdef2 vdef2,	");
		stringBuffer.append("	                  t1.nastnum nastnum,	");
		stringBuffer.append("	                  t4.code pk_material,	");
		stringBuffer.append("	                  t1.crowno crowno,	");
		stringBuffer.append("	                  t7.name cunitid,	");
		stringBuffer.append("	                  t1.nnum nnum,	");
		stringBuffer.append("	                  '' vendor_price	");
		stringBuffer.append("	    from po_order t	");
		stringBuffer.append("	    left join po_order_b t1	");
		stringBuffer.append("	      on t.pk_order = t1.pk_order	");
		stringBuffer.append("	    left join bd_supplier t2	");
		stringBuffer.append("	      on t.pk_supplier = t2.pk_supplier	");
		stringBuffer.append("	    left join org_orgs t3	");
		stringBuffer.append("	      on t.pk_org = t3.pk_org	");
		stringBuffer.append("	    left join bd_material t4	");
		stringBuffer.append("	      on t1.pk_material = t4.pk_material	");
		stringBuffer.append("	    left join org_dept t5	");
		stringBuffer.append("	      on t.pk_dept = t5.pk_dept	");
		stringBuffer.append("	    left join (" + defdocSqlBuilderAll.toString()
				+ ") t6	");
		stringBuffer.append("	      on t.vdef5 = t6.pk_defdoc	");
		stringBuffer.append("	    left join bd_measdoc t7	");
		stringBuffer.append("	      on t1.cunitid = t7.pk_measdoc	");
		stringBuffer.append("	    left join (" + defdocSqlBuildelFordef7
				+ ") t8	");
		stringBuffer.append("	      on t1.vbdef7 = t8.pk_defdocfordef7	");
		stringBuffer.append("	   where nvl(t.dr, 0) = 0	");
		stringBuffer.append("	     and nvl(t1.dr, 0) = 0	");
		stringBuffer.append("	     and nvl(t2.dr, 0) = 0	");
		stringBuffer.append("	     and nvl(t3.dr, 0) = 0	");
		stringBuffer.append("	     and nvl(t4.dr, 0) = 0	");
		stringBuffer.append("	     and nvl(t5.dr, 0) = 0	");
		stringBuffer.append("	     and nvl(t7.dr, 0) = 0	");
		// 审批通过
		stringBuffer.append("	     and t.forderstatus = 3	");
		stringBuffer.append("	     and t.vdef5 is not null ");
		stringBuffer.append("	     and t.vdef5 != '~'	");
		stringBuffer.append("	     and t.vtrantypecode = '21-Cxx-01'	");
		stringBuffer.append("	     and t.vdef5 not in ("
				+ defdocSqlBuilder.toString() + ")	");
		stringBuffer.append("	     and t.breturn != 'Y'	");
		stringBuffer.append("	   and t.vdef20 !='Y'	");
		// 增加最终关闭过滤
		stringBuffer.append("	   and t.bfinalclose !='Y'	");
		if (pk_order != null) {
			stringBuffer.append(" and t.pk_order in (");
			stringBuffer.append(pk_order + ")");
		}
		// 增加过滤条件

		String querySql = stringBuffer.toString();
		List<PoOrderViewVOForWMS> list = (List<PoOrderViewVOForWMS>) this
				.getBaseDAO().executeQuery(querySql,
						new BeanListProcessor(PoOrderViewVOForWMS.class));
		List<PoOrderDTOForWMS> result = this.changeVO(list);
		return result;
	}

	// public static void main(String[] args) {
	// PoOrderBPForWMS poOrderBPForWMS = new PoOrderBPForWMS();
	// try {
	// poOrderBPForWMS.queryPoOrderForWMS(null);
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (BusinessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	/**
	 * 数据转换
	 * 
	 * @param list
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<PoOrderDTOForWMS> changeVO(List<PoOrderViewVOForWMS> list)
			throws BusinessException, IllegalAccessException,
			InvocationTargetException {
		if (list != null && list.size() > 0) {
			HashMap<String, List<PoOrderViewVOForWMS>> code2viewvo = new HashMap<String, List<PoOrderViewVOForWMS>>();
			ArrayList<PoOrderDTOForWMS> result = new ArrayList<PoOrderDTOForWMS>();
			for (PoOrderViewVOForWMS vo : list) {
				Set<String> keys = code2viewvo.keySet();
				String vbillcode = vo.getVbillcode();
				List<PoOrderViewVOForWMS> values = (List<PoOrderViewVOForWMS>) code2viewvo
						.get(vbillcode);
				if (keys.contains(vbillcode)) {
					if (values != null) {
						values.add(vo);
					} else {
						values = new ArrayList<PoOrderViewVOForWMS>();
						values.add(vo);
					}
				} else {
					values = new ArrayList<PoOrderViewVOForWMS>();
					values.add(vo);
				}
				code2viewvo.put(vo.getVbillcode(), values);
			}
			for (String vbillcode : code2viewvo.keySet()) {
				List<PoOrderViewVOForWMS> values = code2viewvo.get(vbillcode);
				if (values != null && values.size() > 0) {
					PoOrderHDTOForWMS poOrderHDTOForWMS = new PoOrderHDTOForWMS();
					BeanUtilsBean.getInstance().copyProperties(
							poOrderHDTOForWMS, values.get(0));
					ArrayList<PoOrderBDTOForWMS> blist = new ArrayList<PoOrderBDTOForWMS>();
					for (PoOrderViewVOForWMS value : values) {
						PoOrderBDTOForWMS poOrderBDTOForWMS = new PoOrderBDTOForWMS();
						BeanUtilsBean.getInstance().copyProperties(
								poOrderBDTOForWMS, value);
						blist.add(poOrderBDTOForWMS);
					}
					PoOrderDTOForWMS poOrderDTOForWMS = new PoOrderDTOForWMS();
					poOrderDTOForWMS.setSlipHeader(poOrderHDTOForWMS);
					poOrderDTOForWMS.setSlipDetail(blist);
					result.add(poOrderDTOForWMS);
				}
			}
			if (result.size() > 0) {
				return result;
			}
		}
		return null;
	}

	/**
	 * json数据转换,分批发送
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String getSendJson(List<PoOrderDTOForWMS> list)
			throws BusinessException {
		if (list != null && list.size() > 0) {
			JSONArray jsonParam = JSONArray.fromObject(list);
			String result = jsonParam.toString();
			return result;
		}
		return null;
	}

	public BaseDAO getBaseDAO() {
		if (baseDAO == null) {
			baseDAO = new BaseDAO();
		}
		return baseDAO;
	}
}
