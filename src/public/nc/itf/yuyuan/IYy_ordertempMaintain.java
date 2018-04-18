package nc.itf.yuyuan;

import java.util.List;
import java.util.Map;

import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

public interface IYy_ordertempMaintain {

	public void delete(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException;

	public AggYyOrderTempVO[] insert(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException;

	public AggYyOrderTempVO[] update(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException;

	public AggYyOrderTempVO[] query(IQueryScheme queryScheme)
			throws BusinessException;

	public AggYyOrderTempVO[] save(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException;

	public AggYyOrderTempVO[] unsave(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException;

	public AggYyOrderTempVO[] approve(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException;

	public AggYyOrderTempVO[] unapprove(AggYyOrderTempVO[] clientFullVOs,
			AggYyOrderTempVO[] originBills) throws BusinessException;

	public AggYyOrderTempVO[] queryTempForOrder(IQueryScheme queryScheme)
			throws BusinessException;

	public List<String> queryPurchaseOrgByOrg(String[] pkorgs)
			throws BusinessException;

	public Map<AggYyOrderTempVO, List<CircularlyAccessibleValueObject>> checkMaterialPUOrg(
			AggYyOrderTempVO agg) throws BusinessException;

	/**
	 * 生效失效操作接口
	 * 
	 * @param list
	 * @param flag
	 * @return
	 * @throws BusinessException
	 */
	public int FProduceFlagProcess(List<String> list, String flag)
			throws BusinessException;

	/**
	 * 查询已分配的组织
	 * 
	 * @param pk_ordertemp
	 * @return
	 * @throws BusinessException
	 */
	public List<String> queryRepeatPurchaseOrg(String pk_ordertemp)
			throws BusinessException;
}