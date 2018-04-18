package nc.impl.yuyuan.bp;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.yuyuan.SupplierDTOForWMS;
import net.sf.json.JSONArray;

public class SupplierBPForWMS {
	String flagForSupplier = "def20";
	private BaseDAO baseDAO;

	/**
	 * 查询数据
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<SupplierDTOForWMS> querySupplierForWMS(String pk_supplier)
			throws BusinessException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("	select distinct t.code        code,	");
		stringBuffer.append("	       t.name        name,	");
		stringBuffer.append("	       t.shortname   shortname,	");
		stringBuffer.append("	       t2.name       bd_linkman_name,	");
		stringBuffer.append("	       t2.cell       bd_linkman_cell,	");
		stringBuffer.append("	       t.email       bd_linkman_email,	");
		stringBuffer.append("	       t2.phone      bd_linkman_phone,	");
		stringBuffer.append("	       t3.detailinfo corpaddress,	");
		stringBuffer.append("	       t.enablestate enablestate	");
		stringBuffer.append("	  from bd_supplier t	");
		stringBuffer.append("	  left join (select * from bd_suplinkman  where isdefault = 'Y' ) t1	");
		stringBuffer.append("	    on t.pk_supplier = t1.pk_supplier	");
		stringBuffer.append("	  left join bd_linkman t2	");
		stringBuffer.append("	    on t1.pk_linkman = t2.pk_linkman	");
		stringBuffer.append("	  left join bd_address t3	");
		stringBuffer.append("	    on t.corpaddress = t3.pk_address	");
		stringBuffer.append("	 where nvl(t.dr, 0) = 0	");
		stringBuffer.append("	   and nvl(t1.dr, 0) = 0	");
		stringBuffer.append("	   and nvl(t2.dr, 0) = 0	");
		stringBuffer.append("	   and nvl(t3.dr, 0) = 0	");
		stringBuffer.append("	   and (t.ts > t.def18 or t.def18 is null or t.def18 = '~' )	");
		stringBuffer.append("      and t.pk_supplier in ( select m.cvendorid from ct_pu m  ) ");
		if (pk_supplier != null) {
			stringBuffer.append(" and t.pk_supplier in (");
			stringBuffer.append(pk_supplier+")");
		}
		String querySql = stringBuffer.toString();
		List<SupplierDTOForWMS> list = (List<SupplierDTOForWMS>) this
				.getBaseDAO().executeQuery(querySql,
						new BeanListProcessor(SupplierDTOForWMS.class));
		return list;
	}

	/**
	 * json数据转换,分批发送
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String getSendJson(List<SupplierDTOForWMS> list)
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
