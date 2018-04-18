package nc.bs.arap.util;

public interface RelationCalItem {
	/**
	 * 返回 【主数量】字段
	 * 
	 * @return
	 */
	public String getNnum();

	/**
	 * 返回 【含税单价】字段
	 * 
	 * @return
	 */
	public String getNTaxPrice();

	/**
	 * 返回 【含税金额】字段
	 * 
	 * @return
	 */
	public String getNTaxMny();

	/**
	 * 返回 【税率】字段
	 * 
	 * @return
	 */
	public String getNTaxRate();

	/**
	 * 返回 【税额】字段
	 * 
	 * @return
	 */
	public String getNTax();

	/**
	 * 返回 【无税金额】字段
	 * 
	 * @return
	 */
	public String getNMny();

	/**
	 * 返回 【无税单价】字段
	 * 
	 * @return
	 */
	public String getNPrice();

	/**
	 * pos接口标志
	 * 
	 * @return
	 */
	public String getPosFlag();

}
