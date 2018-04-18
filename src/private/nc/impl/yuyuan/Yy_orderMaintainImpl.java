package nc.impl.yuyuan;

import java.util.ArrayList;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.impl.pub.ace.AceYy_orderPubServiceImpl;
import nc.itf.yuyuan.IYy_orderMaintain;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.ct.purdaily.entity.AggCtPuVO;
import nc.vo.ct.purdaily.entity.CTDto;
import nc.vo.ct.purdaily.entity.CtPuBVO;
import nc.vo.ct.purdaily.entity.CtScopeVo;
//import nc.vo.org.FinanceOrgVO;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.pub.SqlBuilder;
import nc.vo.yuyuan.yy_errorlog.YyErrorLogVO;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

public class Yy_orderMaintainImpl extends AceYy_orderPubServiceImpl implements
		IYy_orderMaintain {

	@Override
	public void delete(AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills)
			throws BusinessException {
		super.pubdeleteBills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderVO[] insert(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		return super.pubinsertBills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderVO[] update(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		return super.pubupdateBills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderVO[] query(IQueryScheme queryScheme)
			throws BusinessException {
		return super.pubquerybills(queryScheme);
	}

	@Override
	public AggYyOrderVO[] save(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		return super.pubsendapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderVO[] unsave(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		return super.pubunsendapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderVO[] approve(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		return super.pubapprovebills(clientFullVOs, originBills);
	}

	@Override
	public AggYyOrderVO[] unapprove(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException {
		return super.pubunapprovebills(clientFullVOs, originBills);
	}

	/**
	 * 根据库存组织获取中转仓
	 */
	@Override
	public String getTranStorDoc(String pk_orgstor) throws BusinessException {
		// 自定义项1维护中转标志Y
		String sql = "select * from bd_stordoc t where nvl(t.dr,0)=0 and t.def1 = 'Y' and t.pk_org = '"
				+ pk_orgstor + "'";
		BaseDAO dao = new BaseDAO();
		Object query = dao.executeQuery(sql, new ColumnProcessor());
		if (query != null) {
			return query.toString();
		}
		return null;
	}

	@Override
	public List<CTDto> CTMaterialCheckerProcess(AggCtPuVO agg,
			List<String> pkorgList) throws BusinessException {
		List<String> keywordList = new ArrayList<String>();
		if (pkorgList == null) {
			pkorgList = new ArrayList<String>();
		}
		pkorgList.add(agg.getParentVO().getPk_org());
		String cvendorid = agg.getParentVO().getCvendorid();
		for (String pk_org : pkorgList) {
			for (CtPuBVO bvo : agg.getCtPuBVO()) {
				String keyword = pk_org + "_" + bvo.getPk_material();
				keywordList.add(keyword);
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("	select t1.pk_org || '_' || t2.pk_material keyword,	")
				.append("	       t1.pk_ct_pu,	")
				.append("	       t2.pk_ct_pu_b,	")
				.append("	       t1.vbillcode,	")
				.append("	       t1.pk_org,	")
				.append("	       t1.cvendorid,	")
				.append("	       t1.valdate,	")
				.append("	       t1.invallidate,	")
				.append("	       t2.pk_material,	")
				.append("	       t3.code material_code,	")
				.append("	       t4.code supplier_code,	")
				.append("	       t5.code org_code	")
				.append("	  from ct_pu t1	")
				.append("	 inner join ct_pu_b t2	")
				.append("	    on t1.pk_ct_pu = t2.pk_ct_pu	")
				.append("	 inner join bd_material t3	")
				.append("	    on t2.pk_material = t3.pk_material	")
				.append("	 inner join bd_supplier t4	")
				.append("	    on t1.cvendorid = t4.pk_supplier	")
				.append("	 inner join org_financeorg t5	")
				.append("	    on t1.pk_org = t5.pk_financeorg	")
				.append("	 where nvl(t1.dr, 0) = 0	")
				.append("	   and nvl(t2.dr, 0) = 0	")
				.append("	   and nvl(t3.dr, 0) = 0	")
				.append("	   and nvl(t4.dr, 0) = 0	")
				.append("	   and nvl(t5.dr, 0) = 0	")
				.append("	   and t3.enablestate = 2	")
				// 启用状态
				.append("	   and t4.enablestate = 2	")
				.append("	   and t5.enablestate = 2	")
				.append("	   and t5.islastversion = 'Y'	")
				.append("	   and t1.fstatusflag = 1	")
				// 生效状态
				.append("	   and t1.blatest = 'Y'	")
				// 是否最新版本
				.append("	   and t1.pk_ct_pu != '")
				.append(agg.getParentVO().getPk_ct_pu())
				.append("'	   and t2.pk_ct_pu != '")
				.append(agg.getParentVO().getPk_ct_pu())
				.append("' and  ")
				.append(nc.pubitf.bd.reportitem.pub.SqlUtils
						.getInStr("t1.pk_org || '_' || t2.pk_material",
								keywordList, true));
		String sql = sb.toString();
		BaseDAO dao = new BaseDAO();
		List<CTDto> executeQuery = (List<CTDto>) dao.executeQuery(sql,
				new BeanListProcessor(CTDto.class));
		return executeQuery;
	}

	@Override
	public void insertLogVO_RequiresNew(ArrayList<YyErrorLogVO> list)
			throws BusinessException {
		BaseDAO dao = new BaseDAO();
		dao.insertVOList(list);
	}

	@Override
	public List<nc.vo.pu.m21.entity.OrderHeaderVO> queryPOOrder(AggYyOrderVO agg)
			throws BusinessException {
		String sql = "select * from po_order t where nvl(t.dr,0)=0 and t.vdef1 = '"
				+ agg.getParentVO().getPk_order()
				+ "' order by t.vbillcode asc";
		BaseDAO dao = new BaseDAO();
		List<nc.vo.pu.m21.entity.OrderHeaderVO> query = (List<nc.vo.pu.m21.entity.OrderHeaderVO>) dao
				.executeQuery(sql, new BeanListProcessor(
						nc.vo.pu.m21.entity.OrderHeaderVO.class));
		return query;
	}

	@Override
	public String queryVOByCode(String code) throws BusinessException {
		SqlBuilder sqlBuilder = new SqlBuilder();
		// TODO Auto-generated method stub
		sqlBuilder.append("	select t.pk_financeorg	");
		sqlBuilder.append("	  from org_financeorg t	");
		sqlBuilder.append("	 where nvl(t.dr, 0) = 0	");
		sqlBuilder.append("	   and t.enablestate = '2'	");
		sqlBuilder.append("	   and t.islastversion = 'Y'	");
		sqlBuilder.append("	   and t.code = '" + code + "'	");
		String sql = sqlBuilder.toString();
		BaseDAO dao = new BaseDAO();
		Object executeQuery = dao.executeQuery(sql, new ColumnProcessor());
		if (executeQuery != null) {
			return executeQuery.toString();
		}
		return null;
	}

	@Override
	public ArrayList<CtScopeVo> queryCTScopeVOByPK(String pk_ct_pu)
			throws BusinessException {
		String sql = "select  * from ct_scope t where nvl(t.dr,0)=0 and t.pk_ct_pu = '"
				+ pk_ct_pu + "'";
		BaseDAO dao = new BaseDAO();
		ArrayList<CtScopeVo> executeQuery = (ArrayList<CtScopeVo>) dao
				.executeQuery(sql, new BeanListProcessor(CtScopeVo.class));
		return executeQuery;
	}
}
