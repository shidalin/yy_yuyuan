package nc.impl.yuyuan.bp;

import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.pub.SqlBuilder;
import nc.vo.yuyuan.MaterialDTOForWMS;
import net.sf.json.JSONArray;

public class MaterialBPForWMS {
	String flagForMeterial = "def20";
	private BaseDAO baseDAO;

	/**
	 * 查询数据
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public List<MaterialDTOForWMS> queryMaterialForWMS(String pk_material)
			throws BusinessException {
		// 查询温层自定义档案
		SqlBuilder defdocSqlBuilderFordef5 = new SqlBuilder();
		defdocSqlBuilderFordef5.append("	select tt.pk_defdoc pk_defdocfordef5,tt.name defdocnamefordef5	");
		defdocSqlBuilderFordef5.append("	  from bd_defdoc tt	");
		defdocSqlBuilderFordef5.append("	 inner join bd_defdoclist tt1	");
		defdocSqlBuilderFordef5
				.append("	    on tt.pk_defdoclist = tt1.pk_defdoclist	");
		defdocSqlBuilderFordef5.append("	 where tt1.code = 'WMS01'	");
		defdocSqlBuilderFordef5.append("	   and nvl(tt.dr, 0) = 0	");
		defdocSqlBuilderFordef5.append("	   and nvl(tt1.dr, 0) = 0	");
		defdocSqlBuilderFordef5.append("	   and tt.enablestate = 2	");
		
		// 查询运营中心自定义档案
		SqlBuilder defdocSqlBuildelFordef7 = new SqlBuilder();
		defdocSqlBuildelFordef7.append("	select ss.pk_defdoc pk_defdocfordef7,ss.name defdocnamefordef7	");
		defdocSqlBuildelFordef7.append("	  from bd_defdoc ss	");
		defdocSqlBuildelFordef7.append("	 inner join bd_defdoclist ss1	");
		defdocSqlBuildelFordef7
				.append("	    on ss.pk_defdoclist = ss1.pk_defdoclist	");
		defdocSqlBuildelFordef7.append("	 where ss1.code = 'WMS02'	");
		defdocSqlBuildelFordef7.append("	   and nvl(ss.dr, 0) = 0	");
		defdocSqlBuildelFordef7.append("	   and nvl(ss1.dr, 0) = 0	");
		defdocSqlBuildelFordef7.append("	   and ss.enablestate = 2	");
		
		
		// 增加过滤查询逻辑，保质期只取库存组织为乔厂，org_stockorg
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("	select distinct t.code code,	");
		stringBuffer.append("	                t.name name,	");
		stringBuffer.append("	                'SHZC1098' bpCode,	");
		stringBuffer.append("	                '' bpName,	");
		stringBuffer.append("	                t1.qualitynum qualitynum,	");
		stringBuffer.append("	                t5.qualityunit qualityunit,	");
		stringBuffer.append("	                '' baseUomCode,	");
		stringBuffer.append("	                t3.name baseUomName,	");
		stringBuffer.append("	                '1' baseRate,	");
		stringBuffer.append("	                '' baseCaseUomCode,	");
		stringBuffer.append("	                '件虚拟' baseCaseUomName,	");
		stringBuffer.append("	                '1' convRate,	");
		stringBuffer.append("	                t6.defdocnamefordef5 vdef5,	");
		stringBuffer.append("	                t.def19 itemGqsj,	");
		stringBuffer.append("	                '' quantitySafe,	");
		stringBuffer.append("	                t7.defdocnamefordef7 vdef7,	");
		stringBuffer.append("	                t.enablestate enablestate,	");
		stringBuffer.append("	                t.materialspec materialspec	");
		stringBuffer.append("	  from bd_material t	");
		stringBuffer.append("	  left join bd_materialconvert t2	");
		stringBuffer.append("	    on t.pk_material = t2.pk_material	");
		stringBuffer.append("	  left join bd_measdoc t3	");
		stringBuffer.append("	    on t2.pk_measdoc = t3.pk_measdoc	");
		stringBuffer
				.append("	  left join (select t1.pk_material, t1.qualitynum, t1.qualityunit	");
		stringBuffer.append("	               from bd_materialstock t1	");
		stringBuffer.append("	               left join org_stockorg t4	");
		stringBuffer.append("	                 on t1.pk_org = t4.pk_stockorg	");
		stringBuffer.append("	              where t4.code = '1501'	");
		stringBuffer.append("	                and nvl(t1.dr, 0) = 0	");
		stringBuffer.append("	                and nvl(t4.dr, 0) = 0) t1	");
		stringBuffer.append("	    on t.pk_material = t1.pk_material	");
		stringBuffer.append("	   left join bd_materialstock t5	");
		stringBuffer.append("	     on t5.pk_material = t.pk_material	");
		stringBuffer.append("	   left join ("+defdocSqlBuilderFordef5.toString()+") t6	");
		stringBuffer.append("	     on t.def5 = t6.pk_defdocfordef5	");
		stringBuffer.append("	   left join ("+defdocSqlBuildelFordef7.toString()+") t7	");
		stringBuffer.append("	     on t.def7 = t7.pk_defdocfordef7	");
		stringBuffer.append("	 where nvl(t.dr, 0) = 0	");
		stringBuffer.append("	   and nvl(t2.dr, 0) = 0	");
		stringBuffer.append("	   and nvl(t3.dr, 0) = 0	");
		stringBuffer.append("	   and nvl(t5.dr, 0) = 0	");
		stringBuffer.append("	   and t2.ispumeasdoc = 'Y'	");
		stringBuffer.append("	   and (t.ts > t.def18 or t.def18 is null or t.def18 = '~' ) ");
		if (pk_material != null) {
			stringBuffer.append("  and t.pk_material in( ");
			stringBuffer.append(pk_material);
			stringBuffer.append(")");
		}
		// stringBuffer
		// .append("	   and t.code in  ( '1010200010','1030100002','1040300017')	");
		String querySql = stringBuffer.toString();
		List<MaterialDTOForWMS> list = (List<MaterialDTOForWMS>) this
				.getBaseDAO().executeQuery(querySql,
						new BeanListProcessor(MaterialDTOForWMS.class));
		return list;
	}


	public BaseDAO getBaseDAO() {
		if (baseDAO == null) {
			baseDAO = new BaseDAO();
		}
		return baseDAO;
	}

	/**
	 * json数据转换,分批发送
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String getSendJson(List<MaterialDTOForWMS> list)
			throws BusinessException {
		if (list != null && list.size() > 0) {
			JSONArray jsonParam = JSONArray.fromObject(list);
			String result = jsonParam.toString();
			return result;
		}
		return null;
	}

}
