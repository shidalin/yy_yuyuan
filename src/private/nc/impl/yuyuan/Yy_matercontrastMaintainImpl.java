package nc.impl.yuyuan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.impl.pub.ace.AceYy_matercontrastPubServiceImpl;
import nc.itf.yuyuan.IYy_matercontrastMaintain;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.pub.SqlBuilder;
import nc.vo.yuyuan.yy_matercontrast.AggYyMaterContrastVO;

public class Yy_matercontrastMaintainImpl extends
		AceYy_matercontrastPubServiceImpl implements IYy_matercontrastMaintain {

	@Override
	public void delete(AggYyMaterContrastVO[] vos) throws BusinessException {
		super.pubdeleteBills(vos);
	}

	@Override
	public AggYyMaterContrastVO[] insert(AggYyMaterContrastVO[] vos)
			throws BusinessException {
		return super.pubinsertBills(vos);
	}

	@Override
	public AggYyMaterContrastVO[] update(AggYyMaterContrastVO[] vos)
			throws BusinessException {
		return super.pubupdateBills(vos);
	}

	@Override
	public AggYyMaterContrastVO[] query(IQueryScheme queryScheme)
			throws BusinessException {
		return super.pubquerybills(queryScheme);
	}

	@Override
	public Map<String, String> queryDispatchstyale() throws BusinessException {
		BaseDAO baseDAO = new BaseDAO();
		SqlBuilder sqlBuilder = new SqlBuilder();
		sqlBuilder.append("	    select t1.code code, t1.pk_defdoc	pk_defdoc");
		sqlBuilder.append("	      from bd_defdoc t1	");
		sqlBuilder.append("	     inner join bd_defdoclist t2	");
		sqlBuilder.append("	        on t1.pk_defdoclist = t2.pk_defdoclist	");
		sqlBuilder.append("	     where t2.code = 'PO_01'	");
		sqlBuilder.append("	     and  t1.enablestate  = '2'	");
		List list = (List) baseDAO.executeQuery(sqlBuilder.toString(),
				new MapListProcessor());
		HashMap<String, String> hashMap = new HashMap<String, String>();
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				Map map = (Map) obj;
				if (map != null) {
					String code = map.get("code") == null ? "" : map
							.get("code").toString();
					String pk_defdoc = map.get("pk_defdoc") == null ? "" : map
							.get("pk_defdoc").toString();
					hashMap.put(code, pk_defdoc);
				}
			}
		}
		return hashMap;
	}

}
