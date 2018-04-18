package nc.bs.arap.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.util.mmpub.dpub.db.SqlInUtil;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.scale.ScaleUtils;

/**
 * 根据含税金额、数量计算其他金额单价工具类
 * 
 * @author shidalin
 * 
 */
public class MnyCalculateUtil {

	// 精度工具
	private ScaleUtils scale = new ScaleUtils(InvocationInfoProxy.getInstance()
			.getGroupId());

	// 币种
	private String currid;

	// 已知价税合计、数量
	// 1.根据物料查询对应税率
	// 2.计算税额=（价税合计*税率）/(1+税率)
	// 3.计算无税金额=价税合计-税额
	// 4.计算无税单价=无税金额/主数量
	// 5.计算含税单价=价税合计/主数量
	public void mnyCalculate(AggregatedValueObject[] aggsOld)
			throws BusinessException {
		if (aggsOld == null || aggsOld.length == 0) {
			return;
		}
		ArrayList<AggregatedValueObject> oprationDatas = new ArrayList<AggregatedValueObject>();
		// 数据过滤
		for (AggregatedValueObject bill : aggsOld) {
			String pk_billtypecode = bill.getParentVO().getAttributeValue(
					"pk_billtype") == null ? "" : bill.getParentVO()
					.getAttributeValue("pk_billtype").toString();
			if ("F0".equals(pk_billtypecode)) {
				// 应收单处理
				oprationDatas.add(bill);
			}
		}
		if (oprationDatas.size() == 0) {
			return;
		}
		AggregatedValueObject[] aggs = oprationDatas
				.toArray(new AggregatedValueObject[0]);
		// 查询币种
		this.currid = getCurrtypeIdByCode();
		// 查询税率
		Map<String, String> getmattax2taxrate = getmattax2taxrate();
		for (AggregatedValueObject bill : aggs) {
			// 字段映射关系
			RelationCalItem calitem = new RelationCalItemForF0();
			String posFlag = bill.getParentVO().getAttributeValue(
					calitem.getPosFlag()) == null ? "" : bill.getParentVO()
					.getAttributeValue(calitem.getPosFlag()).toString();
			// 数据过滤，自定义项固定值 pos
			if (!"pos".equals(posFlag)) {
				continue;
			}
			// 子表数据处理
			CircularlyAccessibleValueObject[] bvos = bill.getChildrenVO();
			HashSet<String> pk_materials = new HashSet<String>();
			for (CircularlyAccessibleValueObject valueVO : bvos) {
				pk_materials.add(valueVO.getAttributeValue("material")
						.toString());
			}
			HashMap<String, String> material2matttax = getmaterial2matttax(pk_materials);
			for (CircularlyAccessibleValueObject valueVO : bvos) {
				// 设置税率
				String mattax = material2matttax.get(valueVO.getAttributeValue(
						"material").toString());
				String taxparam = getmattax2taxrate.get(mattax);
				// 税码_税率
				String[] taxparams = taxparam.split("_");
				// 设置税率
				valueVO.setAttributeValue(calitem.getNTaxRate(), taxparams[1]);
				// if ("F0".equals(pk_billtypecode)) {
				// 应收单处理,设置税码
				valueVO.setAttributeValue("taxcodeid", taxparams[0]);
				// }
				// 第一步：计算税额
				calTax(valueVO, calitem);
				// 第二步：计算无税金额和无税单价
				calMnyandPrice(valueVO, calitem);
				// 第三步：计算含税单价使用数量
				calNTaxPrice(valueVO, calitem);

			}
		}

	}

	/**
	 * 根据物料主键查询对应的税类
	 * 
	 * @param pk_materials
	 * @return
	 * @throws BusinessException
	 */
	private HashMap<String, String> getmaterial2matttax(
			HashSet<String> pk_materials) throws BusinessException {

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("	select t.pk_material,t.pk_mattaxes from bd_material_v t where nvl(t.dr,0)=0 and t.pk_material 	");
		SqlInUtil sqlInUtil = new SqlInUtil(pk_materials.toArray(new String[0]));
		stringBuffer.append(sqlInUtil.getInSql());
		List list = (List) NCLocator
				.getInstance()
				.lookup(IUAPQueryBS.class)
				.executeQuery(stringBuffer.toString(), new ArrayListProcessor());
		HashMap<String, String> result = new HashMap<String, String>();
		for (Object param : list) {
			Object[] objs = (Object[]) param;
			if (objs[0] == null) {
				continue;
			}
			String key = objs[0].toString();
			if (objs[1] == null) {
				continue;
			}
			String value = objs[1].toString();
			result.put(key, value);
		}
		return result;

	}

	private void calNTaxPrice(CircularlyAccessibleValueObject valueVO,
			RelationCalItem calitem) {
		// 主数量
		UFDouble nnum = getUFDoubleFromObjtoOne(valueVO
				.getAttributeValue(calitem.getNnum()));
		// 价税合计
		UFDouble ntaxmny = getUFDoubleFromObj(valueVO.getAttributeValue(calitem
				.getNTaxMny()));
		// 含税单价
		UFDouble ntaxprice = ntaxmny.div(nnum);
		ntaxprice = getUFDoubleFromObj(this.scale.adjustHslScale(ntaxprice
				.toString()));
		valueVO.setAttributeValue(calitem.getNTaxPrice(), ntaxprice);

	}

	private void calMnyandPrice(CircularlyAccessibleValueObject valueVO,
			RelationCalItem calitem) {

		// 主数量
		UFDouble nnum = getUFDoubleFromObjtoOne(valueVO
				.getAttributeValue(calitem.getNnum()));
		// 价税合计
		UFDouble ntaxmny = getUFDoubleFromObj(valueVO.getAttributeValue(calitem
				.getNTaxMny()));

		// 税额
		UFDouble ntax = getUFDoubleFromObj(valueVO.getAttributeValue(calitem
				.getNTax()));

		UFDouble nmny = ntaxmny.sub(ntax);

		UFDouble nprice = nmny.div(nnum);
		nprice = getUFDoubleFromObj(this.scale
				.adjustHslScale(nprice.toString()));
		valueVO.setAttributeValue(calitem.getNMny(), nmny);
		valueVO.setAttributeValue(calitem.getNPrice(), nprice);

	}

	private void calTax(CircularlyAccessibleValueObject valueVO,
			RelationCalItem calitem) {

		// 价税合计
		UFDouble ntaxmny = getUFDoubleFromObj(valueVO.getAttributeValue(calitem
				.getNTaxMny()));
		// 税率
		UFDouble ntaxrate = getUFDoubleFromObj(valueVO
				.getAttributeValue(calitem.getNTaxRate()));
		ntaxrate=ntaxrate.div(100);
		
		UFDouble ntax = UFDouble.ZERO_DBL;

		ntax = ntaxmny.multiply(ntaxrate).div(UFDouble.ONE_DBL.add(ntaxrate));

		ntax = this.scale.adjustMnyScale(ntax, this.currid);
		valueVO.setAttributeValue(calitem.getNTax(), ntax);

	}

	public UFDouble getUFDoubleFromObj(Object obj) {
		UFDouble value = UFDouble.ZERO_DBL;
		if (obj != null) {
			value = new UFDouble(obj.toString());
		}
		return value;
	}

	public UFDouble getUFDoubleFromObjtoOne(Object obj) {
		UFDouble value = UFDouble.ONE_DBL;
		if (obj != null) {
			value = new UFDouble(obj.toString());
		}
		return value;
	}

	/**
	 * 币种查询
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String getCurrtypeIdByCode() throws BusinessException {
		String querysql = "select t.pk_currtype from bd_currtype t where nvl(t.dr,0)=0 and t.code = 'CNY'";
		Object result = NCLocator.getInstance().lookup(IUAPQueryBS.class)
				.executeQuery(querysql, new ColumnProcessor());
		if (result == null) {
			throw new BusinessException("币种查询错误");
		} else {
			return result.toString();
		}
	}

	/**
	 * 根据物料税类关联查询税码
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, String> getmattax2taxrate() throws BusinessException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("	select t1.pk_mattaxes,  t2.pk_taxcode || '_' || t3.taxrate	");
		stringBuffer.append("	  from bd_mattaxes t1	");
		stringBuffer.append("	  left join bd_taxcode t2	");
		stringBuffer.append("	    on t1.pk_mattaxes = t2.mattaxes	");
		stringBuffer.append("	  left join bd_taxrate t3	");
		stringBuffer.append("	    on t2.pk_taxcode = t3.pk_taxcode	");
		stringBuffer.append("	    where nvl(t1.dr,0)=0 and nvl(t2.dr,0)=0 and nvl(t3.dr,0)=0	");
		List list = (List) NCLocator
				.getInstance()
				.lookup(IUAPQueryBS.class)
				.executeQuery(stringBuffer.toString(), new ArrayListProcessor());
		HashMap<String, String> result = new HashMap<String, String>();
		for (Object param : list) {
			Object[] objs = (Object[]) param;
			if (objs[0] == null) {
				continue;
			}
			String key = objs[0].toString();
//			Double taxrate = Double.valueOf(0.00);
			String value = objs[1].toString();
//			if (objs[1] != null) {
//				String taxparam = objs[1].toString();
//				String[] params = taxparam.split("_");
//				if (params.length != 0) {
//					taxrate = Double.valueOf(params[1]);
//					taxrate = taxrate / 100;
//					value = objs[0].toString() + "_" + taxrate.toString();
//				}
//			}
			result.put(key, value);
		}
		return result;
	}

}

/**
 * 应收单/收款单金额计算字段映射 taxcodeid
 * 
 * @author shidalin
 * 
 */
class RelationCalItemForF0 implements RelationCalItem {

	@Override
	public String getNnum() {
		// TODO Auto-generated method stub
		return "quantity_de";
	}

	@Override
	public String getNTaxPrice() {
		// TODO Auto-generated method stub
		return "taxprice";
	}

	@Override
	public String getNTaxMny() {
		// TODO Auto-generated method stub
		return "local_money_de";
	}

	@Override
	public String getNTaxRate() {
		// TODO Auto-generated method stub
		return "taxrate";
	}

	@Override
	public String getNTax() {
		// TODO Auto-generated method stub
		return "local_tax_de";
	}

	@Override
	public String getNMny() {
		// TODO Auto-generated method stub
		return "local_notax_de";
	}

	@Override
	public String getNPrice() {
		// TODO Auto-generated method stub
		return "price";
	}

	@Override
	public String getPosFlag() {
		// TODO Auto-generated method stub
		return "def1";
	}

}
