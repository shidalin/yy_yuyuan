package nc.itf.yuyuan;

import java.util.ArrayList;
import java.util.List;

import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.ct.purdaily.entity.AggCtPuVO;
import nc.vo.ct.purdaily.entity.CTDto;
import nc.vo.ct.purdaily.entity.CtScopeVo;
import nc.vo.org.FinanceOrgVO;
import nc.vo.pub.BusinessException;
import nc.vo.yuyuan.yy_errorlog.YyErrorLogVO;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

public interface IYy_orderMaintain {

	public void delete(AggYyOrderVO[] clientFullVOs, AggYyOrderVO[] originBills)
			throws BusinessException;

	public AggYyOrderVO[] insert(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException;

	public AggYyOrderVO[] update(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException;

	public AggYyOrderVO[] query(IQueryScheme queryScheme)
			throws BusinessException;

	public AggYyOrderVO[] save(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException;

	public AggYyOrderVO[] unsave(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException;

	public AggYyOrderVO[] approve(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException;

	public AggYyOrderVO[] unapprove(AggYyOrderVO[] clientFullVOs,
			AggYyOrderVO[] originBills) throws BusinessException;

	public String getTranStorDoc(String pk_orgstor) throws BusinessException;

	/**
	 * 采购合同物料唯一性校验
	 * 
	 * @param agg
	 * @param pkorgList
	 * @return
	 * @throws BusinessException
	 */
	public List<CTDto> CTMaterialCheckerProcess(AggCtPuVO agg,
			List<String> pkorgList) throws BusinessException;

	/**
	 * 存储错误日志信息 独立事务接口
	 * 
	 * @throws BusinessException
	 */
	public void insertLogVO_RequiresNew(ArrayList<YyErrorLogVO> list)
			throws BusinessException;

	/**
	 * 查询下游采购订单
	 * 
	 * @param agg
	 * @return
	 */
	public List<nc.vo.pu.m21.entity.OrderHeaderVO> queryPOOrder(AggYyOrderVO agg)
			throws BusinessException;
	
	/**
	 * 查询财务组织信息
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	public String queryVOByCode(String code) throws BusinessException;

	/**
	 * 查询采购合同控制范围
	 * @param pk_ct_pu
	 * @return
	 */
	public ArrayList<CtScopeVo> queryCTScopeVOByPK(String pk_ct_pu)throws BusinessException;
}
