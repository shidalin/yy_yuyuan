package nc.itf.bd.cust.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.cust.CustSupplierVO;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.cust.custorg.CustOrgVO;
import nc.vo.bd.cust.finance.CustFinanceVO;
import nc.vo.bd.errorlog.ErrLogReturnValue;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

/**
 * 客商后台服务接口 创建日期:(2010-7-13)
 * 
 * @author jingli
 */
public interface ICustSupplierService {

	/**
	 * 新增客商
	 * 
	 * @param custSupVO
	 * @return
	 * @throws BusinessException
	 */
	public String insertCustSupVO(CustSupplierVO custSupVO)
			throws BusinessException;

	/**
	 * 修改客商
	 * 
	 * @param custSupVO
	 * @return
	 * @throws BusinessException
	 */
	public CustSupplierVO updateCustSupVO(CustSupplierVO custSupVO)
			throws BusinessException;

	/**
	 * 删除客商
	 * 
	 * @param custSupVO
	 * @throws BusinessException
	 */
	public void deleteCustSupVOByPK(String custSupPK) throws BusinessException;

	/**
	 * 新增一个供应商并将其关联到指定的客户上
	 * 
	 * @param insertVO
	 * @param customerVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-5-31)
	 */
	public Map<String, SuperVO> insertSupAndRelaToCust(SupplierVO insertVO,
			CustomerVO customerVO) throws BusinessException;

	/**
	 * 修改供应商并将其关联到指定的客户上
	 * 
	 * @param updateVO
	 * @param customerVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-7-27)
	 */
	public Map<String, SuperVO> updateSupAndRelaToCust(SupplierVO updateVO,
			CustomerVO customerVO) throws BusinessException;

	/**
	 * 新增一个客户并将其关联到指定的供应商上
	 * 
	 * @param insertVO
	 * @param supplierVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-6-2)
	 */
	public Map<String, SuperVO> insertCustAndRelaToSup(CustomerVO insertVO,
			SupplierVO supplierVO) throws BusinessException;

	/**
	 * @param updateVO
	 * @param supplierVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-7-27)
	 */
	public Map<String, SuperVO> updateCustAndRelaToSup(CustomerVO updateVO,
			SupplierVO supplierVO) throws BusinessException;

	/**
	 * 关联供应商到指定的客户上
	 * 
	 * @param pk_supplier
	 * @param customerVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-6-1)
	 */
	public Map<String, SuperVO> relaSupToCust(String pk_supplier,
			CustomerVO customerVO) throws BusinessException;

	/**
	 * 关联客户到指定的供应商上
	 * 
	 * @param pk_customer
	 * @param supplierVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-6-2)
	 */
	public Map<String, SuperVO> relaCustToSup(String pk_customer,
			SupplierVO supplierVO) throws BusinessException;

	/**
	 * @param pk_customer
	 * @param supplierVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-7-21)
	 */
	public Map<String, SuperVO> releaseCustSupRela(String pk_customer,
			SupplierVO supplierVO) throws BusinessException;

	/**
	 * @param customerVO
	 * @param pk_supplier
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-7-26)
	 */
	public Map<String, SuperVO> releaseCustSupRela(CustomerVO customerVO,
			String pk_supplier) throws BusinessException;

	/**
	 * 同步客户
	 * 
	 * @param customerVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-6-2)
	 */
	public CustomerVO synCustomerInfo(CustomerVO customerVO)
			throws BusinessException;

	/**
	 * 同步供应商
	 * 
	 * @param supplierVO
	 * @return
	 * @throws BusinessException
	 *             创建日期:(2010-6-2)
	 */
	public SupplierVO synSupplierInfo(SupplierVO supplierVO)
			throws BusinessException;

	/**
	 * 同步客商启用状态
	 * 
	 * 创建日期：2011-4-11
	 * 
	 * @param custSupVOs
	 * @return
	 * @throws BusinessException
	 */
	public CustSupplierVO[] synEnableState(CustSupplierVO[] custSupVOs,
			String enableFeild) throws BusinessException;

	/**
	 * 同步客商停用状态
	 * 
	 * 创建日期：2011-4-11
	 * 
	 * @param custSupVOs
	 * @return
	 * @throws BusinessException
	 */
	public CustSupplierVO[] synDisableState(CustSupplierVO[] custSupVOs,
			String enableFeild) throws BusinessException;

	/**
	 * 批量生成客户
	 * 
	 * @param pk_custclass
	 * @param vos
	 * @return
	 * @throws BusinessException
	 */
	public ErrLogReturnValue insertCustsAndRelaToSups(String pk_custclass,
			SupplierVO[] vos) throws BusinessException;

	/**
	 * 批量生成供应商
	 * 
	 * @param pk_suplierclass
	 * @param vos
	 * @return
	 * @throws BusinessException
	 */
	public ErrLogReturnValue insertSupsAndRelaToCust(String pk_suplierclass,
			CustomerVO[] vos) throws BusinessException;

	/**
	 * 客商档案(57)同步
	 * 
	 * @throws BusinessException
	 */
	public void syncCustProcess() throws BusinessException;

	public void syncCustProcessInsert_RequiresNew(
			ArrayList<CustSupplierVO> custSupplierVOInsertList,
			ArrayList<CustomerVO> customerInsertList,
			HashMap<String, CustBasVO> code2custbasVOInsert)
			throws BusinessException;

	public void syncCustProcessUpdate_RequiresNew(
			ArrayList<CustomerVO> customerUpdateList,
			ArrayList<SupplierVO> supplierUpdateList,
			ArrayList<CustSupplierVO> custSupplierVOUpdate,
			List<CustBasVO> updateCustBasVOList) throws BusinessException;
}
