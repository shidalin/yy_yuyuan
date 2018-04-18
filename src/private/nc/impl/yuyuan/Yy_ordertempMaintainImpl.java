package nc.impl.yuyuan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.impl.pub.ace.AceYy_ordertempPubServiceImpl;
import nc.impl.pubapp.pattern.data.view.SchemeViewQuery;
import nc.itf.yuyuan.IYy_ordertempMaintain;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.query2.sql.process.QuerySchemeProcessor;
import nc.vo.pubapp.util.CombineViewToAggUtil;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;
import nc.vo.yuyuan.yy_ordertemp.OrderTempViewVO;
import nc.vo.yuyuan.yy_ordertemp.YyOrderTempBVO;
import nc.vo.yuyuan.yy_ordertemp.YyOrderTempVO;

import org.apache.commons.lang.ArrayUtils;

public class Yy_ordertempMaintainImpl extends AceYy_ordertempPubServiceImpl
		implements IYy_ordertempMaintain {

	@Override
	public void delete(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		super.pubdeleteBills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderTempVO[] insert(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		return super.pubinsertBills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderTempVO[] update(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		return super.pubupdateBills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderTempVO[] query(IQueryScheme queryScheme)
			throws BusinessException {
		return super.pubquerybills(queryScheme);
	}

	@Override
	public AggYyOrderTempVO[] save(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		return super.pubsendapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderTempVO[] unsave(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		return super.pubunsendapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderTempVO[] approve(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		return super.pubapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderTempVO[] unapprove(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException {
		return super.pubunapprovebills(clientFullVOs, originBills);
	}

	/**
	 * 拉单查询接口实现类
	 */
	@Override
	public AggYyOrderTempVO[] queryTempForOrder(IQueryScheme queryScheme)
			throws BusinessException {

		QuerySchemeProcessor processor = new QuerySchemeProcessor(queryScheme);
		// 过滤数据生效状态的模板数据
		String whereSql = " and yy_ordertemp.pk_ordertemp in (select t.pk_ordertemp from yy_ordertemp t where nvl(t.dr,0) =0 and t.fproduceflag = 'Y' and t.vbillstatus = 1 ) ";
		processor.appendWhere(whereSql);
		processor.appendRefTrantypeWhere("YY01", "YY02", "pk_billtypecode");
		SchemeViewQuery<OrderTempViewVO> query = new SchemeViewQuery<OrderTempViewVO>(
				OrderTempViewVO.class);
		// 查询viewvo
		OrderTempViewVO[] views = query.query(queryScheme, null);
		if (ArrayUtils.isEmpty(views)) {
			return null;
		}
		String pk_billtype = nc.vo.am.common.util.BillTypeUtils
				.getPKByCode("YY01");
		for (OrderTempViewVO view : views) {
			YyOrderTempVO hvo = view.getHead();
			YyOrderTempBVO bvo = view.getItem();
			hvo.setPk_group(bvo.getPk_group());
			hvo.setPk_org(bvo.getPk_org());
			hvo.setPk_org_v(bvo.getPk_org_v());
			hvo.setPk_billtypeid(pk_billtype);
			hvo.setPk_billtypecode("YY01");
		}
		// 视图VO按照表头主键转换成聚合VO
		AggYyOrderTempVO[] aggvos = new CombineViewToAggUtil<AggYyOrderTempVO>(
				AggYyOrderTempVO.class, YyOrderTempVO.class,
				YyOrderTempBVO.class).combineViewToAgg(views, "pk_ordertemp");
		return aggvos;
	}

	@Override
	public List<String> queryPurchaseOrgByOrg(String[] pkorgs)
			throws BusinessException {
		// TODO Auto-generated method stub
		String sql = "select t.pk_purchaseorg from org_purchaseorg t where nvl(t.dr,0)=0 and t.enablestate=2 and islastversion = 'Y' and "
				+ nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
						"t.pk_purchaseorg", pkorgs, true);
		BaseDAO baseDAO = new BaseDAO();
		List<String> executeQuery = (List<String>) baseDAO.executeQuery(sql,
				new ColumnListProcessor());
		if (executeQuery != null && executeQuery.size() > 0) {
			return executeQuery;
		}
		return null;
	}

	/**
	 * 过滤表体物料采购组织权限
	 */
	@Override
	public Map<AggYyOrderTempVO, List<CircularlyAccessibleValueObject>> checkMaterialPUOrg(
			AggYyOrderTempVO agg) throws BusinessException {
		CircularlyAccessibleValueObject[] childrenVOs = agg.getChildrenVO();
		List<String> pkmaterialList = new ArrayList<String>();
		for (CircularlyAccessibleValueObject childrenVO : childrenVOs) {
			if (childrenVO.getAttributeValue("cmaterialvid") != null)
				pkmaterialList.add(childrenVO.getAttributeValue("cmaterialvid")
						.toString());
		}
		String sql = "select t.pk_material from bd_materialpu t where nvl(t.dr,0)=0  and t.pk_org = '"
				+ agg.getParentVO().getPk_org()
				+ "' and  "
				+ nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
						"t.pk_material", pkmaterialList, true);
		BaseDAO baseDAO = new BaseDAO();
		List<String> executeQuery = (List<String>) baseDAO.executeQuery(sql,
				new ColumnListProcessor());
		Map<AggYyOrderTempVO, List<CircularlyAccessibleValueObject>> map = new HashMap<AggYyOrderTempVO, List<CircularlyAccessibleValueObject>>();
		if (executeQuery != null && executeQuery.size() > 0) {
			List<CircularlyAccessibleValueObject> newbvoList = new ArrayList<CircularlyAccessibleValueObject>();
			List<CircularlyAccessibleValueObject> newbvoList1 = new ArrayList<CircularlyAccessibleValueObject>();
			for (CircularlyAccessibleValueObject childrenVO : childrenVOs) {
				if (childrenVO.getAttributeValue("cmaterialvid") != null
						&& executeQuery.contains(childrenVO.getAttributeValue(
								"cmaterialvid").toString())) {
					newbvoList.add(childrenVO);
				} else {
					newbvoList1.add(childrenVO);
				}
			}
			if (newbvoList.size() > 0) {
				agg.setChildrenVO(newbvoList.toArray(new SuperVO[0]));
			} else {
				agg.setChildrenVO(null);
			}
			map.put(agg, newbvoList1);
		} else {
			agg.setChildrenVO(null);
			map.put(agg, null);
		}
		return map;
	}

	/**
	 * 生效失效操作
	 */
	@Override
	public int FProduceFlagProcess(List<String> list, String flag)
			throws BusinessException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("	update yy_ordertemp t set t.fproduceflag = '")
				.append(flag)
				.append("' where ")
				.append(nc.pubitf.bd.reportitem.pub.SqlUtils.getInStr(
						"t.pk_ordertemp", list, true));
		BaseDAO dao = new BaseDAO();
		int count = dao.executeUpdate(buffer.toString());
		return count;
	}

	@Override
	public List<String> queryRepeatPurchaseOrg(String pk_ordertemp)
			throws BusinessException {
		// TODO Auto-generated method stub
		String sql = "select t.pk_org from yy_ordertemp t where nvl(t.dr,0)=0 and t.vdef3='"
				+ pk_ordertemp + "'";
		BaseDAO dao = new BaseDAO();
		List<String> executeQuery = (List<String>) dao.executeQuery(sql,
				new ColumnListProcessor());
		return executeQuery;
	}

}
