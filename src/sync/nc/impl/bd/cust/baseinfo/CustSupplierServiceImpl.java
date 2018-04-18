package nc.impl.bd.cust.baseinfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.bs.bd.baseservice.busilog.BDBusiLogUtil;
import nc.bs.bd.baseservice.busilog.IBusiOperateConst;
import nc.bs.bd.baseservice.md.BDSingleBaseService;
import nc.bs.bd.baseservice.validator.RefPkExistsValidator;
import nc.bs.bd.cache.CacheProxy;
import nc.bs.bd.cust.baseinfo.validator.CustClassDisableValidator;
import nc.bs.bd.cust.baseinfo.validator.CustLinkmanValidator;
import nc.bs.bd.cust.baseinfo.validator.CustSupFinanceorgValidator;
import nc.bs.bd.cust.baseinfo.validator.CustVatValidator;
import nc.bs.bd.cust.baseinfo.validator.CusttaxtypesValidator;
import nc.bs.bd.cust.baseinfo.validator.FreeCustValidator;
import nc.bs.bd.cust.baseinfo.validator.IntCustomerValidator;
import nc.bs.bd.supplier.baseinfo.validator.IntSupplierValidator;
import nc.bs.bd.supplier.baseinfo.validator.SupClassIsDisableValidator;
import nc.bs.bd.supplier.baseinfo.validator.SupVatValidator;
import nc.bs.bd.supplier.baseinfo.validator.SupcountrytaxesValidator;
import nc.bs.bd.supplier.baseinfo.validator.SupplierItemsValidator;
import nc.bs.bd.supplier.baseinfo.validator.SupplierLinkmanDefaultValidator;
import nc.bs.bd.supplier.baseinfo.validator.SupplierNotNullValidator;
import nc.bs.bd.supplier.baseinfo.validator.SupplierRepeatValidator;
import nc.bs.bd.supplier.baseinfo.validator.SupplierSetfreecustValidator;
import nc.bs.businessevent.EventDispatcher;
import nc.bs.businessevent.IEventType;
import nc.bs.businessevent.bd.BDCommonEvent;
import nc.bs.businessevent.bd.BDCommonEventUtil;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.uap.bd.supplier.ISupplierConst;
import nc.bs.uif2.BusinessExceptionAdapter;
import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.NullValueValidator;
import nc.bs.uif2.validation.ValidationException;
import nc.bs.uif2.validation.ValidationFrameworkUtil;
import nc.bs.uif2.validation.Validator;
import nc.itf.bd.bankacc.cust.ICustSupBankaccShareService;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoQueryService;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoService;
import nc.itf.bd.cust.baseinfo.ICustSupQueryService;
import nc.itf.bd.cust.baseinfo.ICustSupplierService;
import nc.itf.bd.pub.IBDMetaDataIDConst;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoQryService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoService;
import nc.itf.uap.IUAPQueryBS;
import nc.itf.uap.bd.refcheck.IReferenceCheck;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.generator.SequenceGenerator;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.util.SQLHelper;
import nc.pub.billcode.itf.IBillcodeManage;
import nc.pub.billcode.vo.BillCodeContext;
import nc.pubitf.bd.accessor.GeneralAccessorFactory;
import nc.pubitf.bd.accessor.IGeneralAccessor;
import nc.vo.bd.accessor.IBDData;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.countryzone.CountryZoneVO;
import nc.vo.bd.cust.CustSupplierVO;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.cust.ICustConst;
import nc.vo.bd.cust.ICustomerSupplierConst;
import nc.vo.bd.cust.areaclass.AreaclassVO;
import nc.vo.bd.cust.custclass.CustClassVO;
import nc.vo.bd.errorlog.ErrLogReturnValue;
import nc.vo.bd.errorlog.ErrorLogUtil;
import nc.vo.bd.format.FormatDocVO;
import nc.vo.bd.pub.DistributedAddBaseValidator;
import nc.vo.bd.pub.IPubEnumConst;
import nc.vo.bd.pub.SingleDistributedUpdateValidator;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.bd.supplier.supplierclass.SupplierclassVO;
import nc.vo.bd.tableref.RefRelationVO;
import nc.vo.bd.timezone.TimezoneVO;
import nc.vo.ml.MainNCLangRes;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.pub.SqlBuilder;
import nc.vo.trade.voutils.VOUtil;
import nc.vo.util.BDAuditInfoUtil;
import nc.vo.util.BDPKLockUtil;
import nc.vo.util.BDUniqueRuleValidate;
import nc.vo.util.BDVersionValidationUtil;
import nc.vo.util.bizlock.BizlockDataUtil;

import org.apache.commons.lang.StringUtils;

/**
 * ���̺�̨����ʵ���� ��������:(2010-7-13)
 * 
 * @author guohuia
 */
public class CustSupplierServiceImpl extends
		BDSingleBaseService<CustSupplierVO> implements ICustSupplierService {

	/** �ͻ���̨��ѯ���� */
	private ICustBaseInfoQueryService custQryService;

	/** �ͻ���̨���� */
	private ICustBaseInfoService custService;

	/** ���̺�̨��ѯ���� */
	private ICustSupQueryService custSupQryService;

	private BaseDAO dao = new BaseDAO();

	/** ��Ӧ�̺�̨���� */
	private ISupplierBaseInfoService supplierService;

	/** ��Ӧ�̺�̨��ѯ���� */
	private ISupplierBaseInfoQryService supQryService;

	private IBillcodeManage billcodeManage = null;

	private final Map<String, BillCodeContext> supOrgID_billCodeContext_map = new HashMap<String, BillCodeContext>();

	private final Map<String, BillCodeContext> custOrgID_billCodeContext_map = new HashMap<String, BillCodeContext>();

	private CountryZoneVO countryZone = null;

	private CountryZoneVO getDefaultCountryZone()
	/*     */{
		/* 400 */if (this.countryZone == null) {
			/*     */try {
				/* 402 */this.countryZone = queryCountryZoneByCode("CN");
				/*     */} catch (BusinessException e) {
				/* 404 */Logger.error(e.getMessage(), e);
				/*     */}
			/*     */}
		/* 407 */return this.countryZone;
		/*     */}

	/*     */
	/*     */private CountryZoneVO queryCountryZoneByCode(String code)
			throws BusinessException
	/*     */{
		/* 412 */IUAPQueryBS querySer = (IUAPQueryBS) NCLocator.getInstance()
				.lookup(IUAPQueryBS.class);
		/* 413 */Collection<CountryZoneVO> col = querySer.retrieveByClause(
				CountryZoneVO.class, "code = '" + code + "'");
		/*     */
		/* 415 */return (col == null) || (col.size() == 0) ? null
				: (CountryZoneVO) col.iterator().next();
		/*     */}

	/*     */
	public CustSupplierServiceImpl() {
		super(IBDMetaDataIDConst.CUSTSUPPLIER);
	}

	@Override
	public void deleteCustSupVOByPK(String custSupPK) throws BusinessException {
		if (StringUtils.isBlank(custSupPK)) {
			return;
		}
		CustSupplierVO custSupVO = this.getCustSupQryService()
				.queryCustSupVOByPk(custSupPK);
		super.deleteVO(custSupVO);
	}

	@Override
	public Map<String, SuperVO> insertCustAndRelaToSup(CustomerVO insertVO,
			SupplierVO supplierVO) throws BusinessException {
		// ����������У�飬��Ӧ������֯��һ��ʱ���������
		this.doRelaCheck(insertVO, supplierVO);
		Map<String, SuperVO> map = new HashMap<String, SuperVO>();
		BDPKLockUtil.lockObject(supplierVO);
		SupplierVO newSup = this.getSupQryService().querySupplierVOByPk(
				supplierVO.getPrimaryKey());
		if (newSup != null) {
			if (newSup.getCorcustomer() != null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes().getStrByID("10140custsup",
								"010140custsup0065"));
				// ��Ӧ�������ɿͻ������ظ����ɡ�
			}
		}
		// �����ͻ�
		insertVO.setIssupplier(UFBoolean.TRUE);
		insertVO.setPk_supplier(supplierVO.getPrimaryKey());
		insertVO.setPrimaryKey(supplierVO.getPrimaryKey());
		try {
			CustomerVO newCustVO = this.getCustService()
					.insertCustomerVOForCreate(insertVO, true);
			// ���¹�Ӧ��
			supplierVO.setIscustomer(UFBoolean.TRUE);
			supplierVO.setCorcustomer(insertVO.getPrimaryKey());
			SupplierVO newSupVO = this.synSupplierInfo(supplierVO);
			// ���¿���
			CustSupplierVO custSupVO = this.getCustSupQryService()
					.queryCustSupVOByPk(supplierVO.getPrimaryKey());
			custSupVO.setCustsuptype(ICustomerSupplierConst.CUSTSUP_CUSTSUP);
			custSupVO.setPk_custclass(insertVO.getPk_custclass());
			this.updateCustSupVO(custSupVO);
			// дҵ����־
			this.getBusiLogUtil().writeBusiLog(
					"relate",
					MainNCLangRes.getInstance().getStrByID("10140custsup",
							"010140custsup0067")/* "[xx]��Ӧ�̹���[xx]�ͻ�" */,
					custSupVO);
			fireCreateCustEvent(newCustVO, newSupVO);
			map.put(ICustomerSupplierConst.CUSTOMER, newCustVO);
			map.put(ICustomerSupplierConst.SUPPLIER, newSupVO);
			if (ISupplierConst.SUPPLIERTYPE_INNER != newCustVO.getCustprop()
					.intValue())
				getCustBankaccShareService()
						.createCustOrSupShareBankaccRelations(
								newCustVO.getPk_customer(),
								ICustomerSupplierConst.CUSTSUP_CUSTSUP);

		} catch (BusinessException e) {
			throw e;// throw new
					// BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("10140custsup","010140custsup0065"));//
					// "��Ӧ�������ɿͻ������ظ����ɡ�");
		}
		return map;
	}

	private void fireCreateCustEvent(CustomerVO custvo, SupplierVO supvo)
			throws BusinessException {
		EventDispatcher.fireEvent(new BDCommonEvent(
				IBDMetaDataIDConst.SUPPLIER, "1090", new Object[] { custvo,
						supvo }));
	}

	private void fireCreateSupEvent(CustomerVO custvo, SupplierVO supvo)
			throws BusinessException {
		EventDispatcher.fireEvent(new BDCommonEvent(
				IBDMetaDataIDConst.CUSTOMER, "1091", new Object[] { custvo,
						supvo }));
	}

	private void fireRelateCustEvent(CustomerVO custvo, SupplierVO supvo)
			throws BusinessException {
		EventDispatcher.fireEvent(new BDCommonEvent(
				IBDMetaDataIDConst.SUPPLIER, "1092", new Object[] { custvo,
						supvo }));
	}

	private void fireRelateSupEvent(CustomerVO custvo, SupplierVO supvo)
			throws BusinessException {
		EventDispatcher.fireEvent(new BDCommonEvent(
				IBDMetaDataIDConst.CUSTOMER, "1093", new Object[] { custvo,
						supvo }));
	}

	@Override
	public String insertCustSupVO(CustSupplierVO custSupVO)
			throws BusinessException {
		if (custSupVO == null) {
			return null;
		}
		insertValidateVO(custSupVO);
		// ����ǰ�¼�֪ͨ
		this.fireBeforeInsertEvent(custSupVO);

		// �����
		String pk = this.dbInsertVO(custSupVO);
		custSupVO.setPrimaryKey(pk);

		// ֪ͨ���»���
		this.notifyVersionChangeWhenDataInserted(custSupVO);

		// ���¼����������VO
		custSupVO = this.retrieveVO(custSupVO.getPrimaryKey());

		// �����¼���֪ͨ
		this.fireAfterInsertEvent(custSupVO);

		// ҵ����־
		this.writeInsertBusiLog(custSupVO);

		return pk;
	}

	@Override
	public Map<String, SuperVO> insertSupAndRelaToCust(SupplierVO insertVO,
			CustomerVO customerVO) throws BusinessException {
		// ����������У�飬��Ӧ������֯��һ��ʱ���������
		this.doRelaCheck(customerVO, insertVO);
		Map<String, SuperVO> map = new HashMap<String, SuperVO>();
		BDPKLockUtil.lockObject(customerVO);
		CustomerVO[] newCust = this.getCustQryService().queryDataByPkSet(
				new String[] { customerVO.getPrimaryKey() });
		if (newCust != null && newCust.length == 1) {
			if (newCust[0].getPk_supplier() != null) {
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes().getStrByID("10140custsup",
								"010140custsup0066"));
				// �ͻ������ɹ�Ӧ�̲����ظ����ɡ�
			}
		}
		// ������Ӧ��
		insertVO.setIscustomer(UFBoolean.TRUE);
		insertVO.setCorcustomer(customerVO.getPrimaryKey());
		insertVO.setPrimaryKey(customerVO.getPrimaryKey());
		try {
			SupplierVO newSupVO = this.getSupplierService()
					.insertSupplierVOForCreate(insertVO, false);
			// ���¿ͻ�
			customerVO.setIssupplier(UFBoolean.TRUE);
			customerVO.setPk_supplier(insertVO.getPrimaryKey());
			CustomerVO newCustVO = this.synCustomerInfo(customerVO);
			// ���¿���
			CustSupplierVO custSupVO = this.getCustSupQryService()
					.queryCustSupVOByPk(customerVO.getPrimaryKey());
			custSupVO.setCustsuptype(ICustomerSupplierConst.CUSTSUP_CUSTSUP);
			custSupVO.setPk_supplierclass(insertVO.getPk_supplierclass());
			this.getBusiLogUtil().writeBusiLog(
					"relate",
					MainNCLangRes.getInstance().getStrByID("10140custsup",
							"010140custsup0068")/* "[xx]�ͻ�����[xx]��Ӧ��" */,
					custSupVO);
			this.updateCustSupVO(custSupVO);
			fireCreateSupEvent(newCustVO, newSupVO);
			map.put(ICustomerSupplierConst.CUSTOMER, newCustVO);
			map.put(ICustomerSupplierConst.SUPPLIER, newSupVO);
			if (ISupplierConst.SUPPLIERTYPE_INNER != newCustVO.getCustprop()
					.intValue())
				getCustBankaccShareService()
						.createCustOrSupShareBankaccRelations(
								newCustVO.getPk_customer(),
								ICustomerSupplierConst.CUSTSUP_CUST);
		} catch (BusinessException e) {
			throw e;// throw new
					// BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("10140custsup","010140custsup0066"));//
					// "�ͻ������ɹ�Ӧ�̲����ظ����ɡ�");
		}
		return map;
	}

	@Override
	public Map<String, SuperVO> relaCustToSup(String pk_customer,
			SupplierVO supplierVO) throws BusinessException {
		if (StringUtils.isBlank(pk_customer) || supplierVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0004")/*
												 * @res "�����ͻ�ʱ���ͻ��͹�Ӧ�̾�����Ϊ��"
												 */);
		}
		BDPKLockUtil.lockString(new String[] { pk_customer,
				supplierVO.getPrimaryKey() });
		// ��Ӧ�̰汾У��
		BDVersionValidationUtil.validateSuperVO(supplierVO);
		CustomerVO customerVO = (CustomerVO) this.dao.retrieveByPK(
				CustomerVO.class, pk_customer);
		if (customerVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0005")/*
												 * @res "�������Ŀͻ��ѱ�ɾ������ˢ���������²�����"
												 */);
		}
		// ҵ���߼�У��
		boolean custIsSup = customerVO.getIssupplier() == null ? false
				: customerVO.getIssupplier().booleanValue();
		if (custIsSup) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0001")/* @res "�������Ŀͻ�����Ϊ���̡�" */);
		}
		// �����������Ŀͻ��͹�Ӧ���Ƿ�Ϊɢ��
		this.checkCustAndSupAreFreecust(customerVO, supplierVO);
		// ����У��
		this.doRelaCheck(customerVO, supplierVO);
		boolean isCust = supplierVO.getIscustomer() == null ? false
				: supplierVO.getIscustomer().booleanValue();
		if (isCust) {
			// �Ƚ�������������µĹ���
			SupplierVO newSupVO = (SupplierVO) this.releaseCustSupRela(
					supplierVO.getCorcustomer(), supplierVO).get(
					ICustomerSupplierConst.SUPPLIER);
			return this.dealCustSupRela(customerVO, newSupVO,
					ICustomerSupplierConst.CUSTSUP_CUSTSUP);
		} else {
			return this.dealCustSupRela(customerVO, supplierVO,
					ICustomerSupplierConst.CUSTSUP_CUSTSUP);
		}
	}

	@Override
	public Map<String, SuperVO> relaSupToCust(String pk_supplier,
			CustomerVO customerVO) throws BusinessException {
		if (StringUtils.isBlank(pk_supplier) || customerVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0002")/*
												 * @res "�������Ŀͻ��͹�Ӧ�̾�����Ϊ�ա�"
												 */);
		}
		SupplierVO supplierVO = (SupplierVO) this.dao.retrieveByPK(
				SupplierVO.class, pk_supplier);
		if (supplierVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0003") /* "�������Ĺ�Ӧ���ѱ�ɾ������ˢ���������²�����" */);
		}
		// ��������
		BDPKLockUtil.lockString(new String[] { pk_supplier,
				customerVO.getPrimaryKey() });
		// �汾У��
		BDVersionValidationUtil.validateSuperVO(customerVO);
		// ҵ���߼�У��
		boolean isCustomer = supplierVO.getIscustomer() == null ? false
				: supplierVO.getIscustomer().booleanValue();
		if (isCustomer) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0001")/* @res "�������Ŀͻ�����Ϊ���̡�" */);
		}
		// �����������Ŀͻ��͹�Ӧ���Ƿ�Ϊɢ��
		this.checkCustAndSupAreFreecust(customerVO, supplierVO);
		// ����У��
		this.doRelaCheck(customerVO, supplierVO);
		boolean isSup = customerVO.getIssupplier() == null ? false : customerVO
				.getIssupplier().booleanValue();
		if (isSup) {
			// �Ƚ���������µĹ���
			CustomerVO newCustVO = (CustomerVO) this.releaseCustSupRela(
					customerVO, customerVO.getPk_supplier()).get(
					ICustomerSupplierConst.CUSTOMER);
			return this.dealCustSupRela(newCustVO, supplierVO,
					ICustomerSupplierConst.CUSTSUP_CUST);
		} else {
			return this.dealCustSupRela(customerVO, supplierVO,
					ICustomerSupplierConst.CUSTSUP_CUST);
		}
	}

	@Override
	public Map<String, SuperVO> releaseCustSupRela(CustomerVO customerVO,
			String pk_supplier) throws BusinessException {
		if (StringUtils.isBlank(pk_supplier) || customerVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0007")/*
												 * @res "��������̹�ϵ�Ŀͻ��͹�Ӧ�̾�����Ϊ�ա�"
												 */);
		}
		// ��ѯ�ͻ�
		SupplierVO supplierVO = (SupplierVO) this.dao.retrieveByPK(
				SupplierVO.class, pk_supplier);
		if (customerVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0003")/*
												 * @res "�������Ĺ�Ӧ���ѱ�ɾ������ˢ���������²�����"
												 */);
		}
		// ��������
		BDPKLockUtil.lockString(new String[] { pk_supplier,
				customerVO.getPrimaryKey() });
		// �汾У��
		BDVersionValidationUtil.validateSuperVO(customerVO);
		// ҵ���߼�У��

		// �������ǰ�¼�
		EventDispatcher.fireEvent(new BDCommonEvent(this.getMDId(),
				IEventType.TYPE_PKSPLIT_BEFORE, new String[] { customerVO
						.getPrimaryKey() }, null));

		Map<String, SuperVO> map = new HashMap<String, SuperVO>();
		// ���¿ͻ�
		customerVO.setIssupplier(UFBoolean.FALSE);
		customerVO.setPk_supplier(null);
		CustomerVO newCustVO = this.synCustomerInfo(customerVO);
		// ���¹�Ӧ��
		supplierVO.setIscustomer(UFBoolean.FALSE);
		supplierVO.setCorcustomer(null);
		SupplierVO newSupVO = this.synSupplierInfo(supplierVO);
		// �ı乩Ӧ�̵�����
		String newPK = this.generateNewPk();
		SupplierVO returnSupVO = this.getSupplierService().changeSupplierPK(
				newSupVO, newPK);
		// ���¿��̣����½������ǰ�ͻ��ڿ��̱�ļ�¼�����ӽ��������Ӧ���ڿ��̱�ļ�¼
		CustSupplierVO cust_custSupVO = this.getCustSupQryService()
				.queryCustSupVOByPk(customerVO.getPrimaryKey());
		// �����̱���Ϳͻ����벻һ��ʱ�����¿��̱���Ϊ�ͻ�����
		if (!cust_custSupVO.getCode().equals(customerVO.getCode())) {
			cust_custSupVO.setCode(customerVO.getCode());
		}
		cust_custSupVO.setCustsuptype(ICustomerSupplierConst.CUSTSUP_CUST);
		cust_custSupVO.setPk_supplierclass(null);
		this.updateCustSupVO(cust_custSupVO);
		CustSupplierVO sup_custSupVO = new CustSupplierVO(returnSupVO);
		this.insertCustSupVO(sup_custSupVO);
		// ������ֺ��¼�
		EventDispatcher.fireEvent(new BDCommonEvent(this.getMDId(),
				IEventType.TYPE_PKSPLIT_AFTER, new String[] { customerVO
						.getPrimaryKey() }, new String[] {
						customerVO.getPrimaryKey(),
						sup_custSupVO.getPrimaryKey() }));
		map.put(ICustomerSupplierConst.CUSTOMER, newCustVO);
		map.put(ICustomerSupplierConst.SUPPLIER, returnSupVO);
		if (ISupplierConst.SUPPLIERTYPE_INNER != newCustVO.getCustprop()
				.intValue())
			getCustBankaccShareService().unShareCustSupBankaccs(
					newCustVO.getPk_customer(), returnSupVO.getPk_supplier(),
					ICustomerSupplierConst.CUSTSUP_CUST);
		return map;
	}

	@Override
	public Map<String, SuperVO> releaseCustSupRela(String pk_customer,
			SupplierVO supplierVO) throws BusinessException {
		if (StringUtils.isBlank(pk_customer) || supplierVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0007")/*
												 * @res "��������̹�ϵ�Ŀͻ��͹�Ӧ�̾�����Ϊ�ա�"
												 */);
		}
		// ��ѯ�ͻ�
		CustomerVO customerVO = (CustomerVO) this.dao.retrieveByPK(
				CustomerVO.class, pk_customer);
		if (customerVO == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0005")/*
												 * @res "�������Ŀͻ��ѱ�ɾ������ˢ���������²�����"
												 */);
		}
		// ��������
		BDPKLockUtil.lockString(new String[] { supplierVO.getPrimaryKey(),
				pk_customer });
		// �汾У��
		BDVersionValidationUtil.validateSuperVO(supplierVO);
		// �������ǰ�¼�
		EventDispatcher.fireEvent(new BDCommonEvent(this.getMDId(),
				IEventType.TYPE_PKSPLIT_BEFORE, new String[] { customerVO
						.getPrimaryKey() }, null));
		Map<String, SuperVO> map = new HashMap<String, SuperVO>();
		// ���¿ͻ�
		customerVO.setIssupplier(UFBoolean.FALSE);
		customerVO.setPk_supplier(null);
		CustomerVO newCustVO = this.synCustomerInfo(customerVO);
		// ���¹�Ӧ��
		supplierVO.setIscustomer(UFBoolean.FALSE);
		supplierVO.setCorcustomer(null);
		SupplierVO newSupVO = this.synSupplierInfo(supplierVO);
		// �ı乩Ӧ�̵�����
		String newPK = this.generateNewPk();
		SupplierVO returnSupVO = this.getSupplierService().changeSupplierPK(
				newSupVO, newPK);
		// ���¿��̣����½������ǰ�ͻ��ڿ��̱�ļ�¼�����ӽ��������Ӧ���ڿ��̱�ļ�¼
		CustSupplierVO cust_custSupVO = this.getCustSupQryService()
				.queryCustSupVOByPk(customerVO.getPrimaryKey());
		// �����̱���Ϳͻ����벻һ��ʱ�����¿��̱���Ϊ�ͻ�����
		if (!cust_custSupVO.getCode().equals(customerVO.getCode())) {
			cust_custSupVO.setCode(customerVO.getCode());
		}
		cust_custSupVO.setCustsuptype(ICustomerSupplierConst.CUSTSUP_CUST);
		cust_custSupVO.setPk_supplierclass(null);
		this.getBusiLogUtil().writeBusiLog("derelate", null, cust_custSupVO);
		this.updateCustSupVO(cust_custSupVO);
		CustSupplierVO sup_custSupVO = new CustSupplierVO(returnSupVO);
		this.insertCustSupVO(sup_custSupVO);
		// ������ֺ��¼�
		EventDispatcher.fireEvent(new BDCommonEvent(this.getMDId(),
				IEventType.TYPE_PKSPLIT_AFTER, new String[] { customerVO
						.getPrimaryKey() }, new String[] {
						customerVO.getPrimaryKey(),
						sup_custSupVO.getPrimaryKey() }));
		map.put(ICustomerSupplierConst.CUSTOMER, newCustVO);
		map.put(ICustomerSupplierConst.SUPPLIER, returnSupVO);
		if (ISupplierConst.SUPPLIERTYPE_INNER != newCustVO.getCustprop()
				.intValue())
			getCustBankaccShareService().unShareCustSupBankaccs(
					returnSupVO.getPk_supplier(), newCustVO.getPk_customer(),
					ICustomerSupplierConst.CUSTSUP_CUSTSUP);
		return map;
	}

	@Override
	public CustomerVO synCustomerInfo(CustomerVO customerVO)
			throws BusinessException {
		// ����
		BDPKLockUtil.lockSuperVO(customerVO);
		BizlockDataUtil.lockDataByBizlock(customerVO);
		// У��汾
		BDVersionValidationUtil.validateSuperVO(customerVO);
		// oldVO
		CustomerVO oldVO = (CustomerVO) this.dao.retrieveByPK(CustomerVO.class,
				customerVO.getPrimaryKey());

		// ��Ӧ������֯У��
		// ����Ψһ��У�� ����by litingk
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(new Validator[] {
						new BDUniqueRuleValidate(),
						new SingleDistributedUpdateValidator(),
						new CustSupFinanceorgValidator() });

		validateService.validate(customerVO);
		// �����Ϣ
		BDAuditInfoUtil.updateData(customerVO);
		// ����ǰ�¼�
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(
				IBDMetaDataIDConst.CUSTOMER);
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateBeforeEvent(customerVO);
		// ����������¿ͻ������¿��̱�
		this.dao.updateVO(customerVO);
		new BDBusiLogUtil(IBDMetaDataIDConst.CUSTOMER).writeBusiLog("syncust",
				null, customerVO);
		// ����֪ͨ
		CacheProxy.fireDataUpdated(customerVO.getTableName(),
				customerVO.getPrimaryKey());
		// ���º��¼�
		customerVO = this.getCustQryService().queryDataByPkSet(
				new String[] { customerVO.getPrimaryKey() })[0];
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateAfterEvent(customerVO);
		return customerVO;
	}

	@Override
	public CustSupplierVO[] synDisableState(CustSupplierVO[] custSupVOs,
			String enableFeild) throws BusinessException {
		if (custSupVOs == null || enableFeild == null) {
			return null;
		}
		// �Ӽ�����
		BDPKLockUtil.lockSuperVO(custSupVOs);
		// �汾У��
		BDVersionValidationUtil.validateSuperVO(custSupVOs);
		// ��������״̬
		this.setDisableState(custSupVOs, enableFeild);
		// �O��VO��B
		this.setVOStatus(custSupVOs, VOStatus.UPDATED);
		// ���������Ϣ
		this.setDisableAuditInfo(custSupVOs);
		// ��������
		this.dao.updateVOArray(custSupVOs);
		// ȡ������
		CustSupplierVO[] returnVOs = this.retrieveVOs(custSupVOs);
		// ����֪ͨ
		CacheProxy.fireDataUpdated(CustSupplierVO.getDefaultTableName(), null);
		// ��¼��־
		this.getBusiLogUtil().writeBusiLog(IBusiOperateConst.EDIT, null,
				returnVOs);

		return returnVOs;
	}

	@Override
	public CustSupplierVO[] synEnableState(CustSupplierVO[] custSupVOs,
			String enableFeild) throws BusinessException {
		if (custSupVOs == null || enableFeild == null) {
			return null;
		}
		// �Ӽ�����
		BDPKLockUtil.lockSuperVO(custSupVOs);
		// �汾У��
		BDVersionValidationUtil.validateSuperVO(custSupVOs);
		// ��������״̬
		this.setEnableState(custSupVOs, enableFeild);
		// ���������Ϣ
		this.setEnableAuditInfo(custSupVOs);
		// �O�à�B
		this.setVOStatus(custSupVOs, VOStatus.UPDATED);
		// ��������
		this.dao.updateVOArray(custSupVOs);
		// ȡ������
		CustSupplierVO[] returnVOs = this.retrieveVOs(custSupVOs);
		// ����֪ͨ
		CacheProxy.fireDataUpdated(CustSupplierVO.getDefaultTableName(), null);
		// ��¼��־
		this.getBusiLogUtil().writeBusiLog(IBusiOperateConst.EDIT, null,
				returnVOs);

		return returnVOs;
	}

	@Override
	public SupplierVO synSupplierInfo(SupplierVO supplierVO)
			throws BusinessException {
		// ����
		BDPKLockUtil.lockSuperVO(supplierVO);
		BizlockDataUtil.lockDataByBizlock(supplierVO);
		// У��汾
		BDVersionValidationUtil.validateSuperVO(supplierVO);
		// oldVO
		SupplierVO oldVO = (SupplierVO) this.dao.retrieveByPK(SupplierVO.class,
				supplierVO.getPrimaryKey());

		// ��Ӧ������֯У��
		// ����Ψһ��У�� ����by litingk
		IValidationService validateService = ValidationFrameworkUtil
				.createValidationService(new Validator[] {
						new BDUniqueRuleValidate(),
						new SingleDistributedUpdateValidator(),
						new CustSupFinanceorgValidator() });

		validateService.validate(supplierVO);
		supplierVO.setPk_oldsupplier(oldVO.getPrimaryKey());
		// �����Ϣ
		BDAuditInfoUtil.updateData(supplierVO);
		// ����ǰ�¼�
		BDCommonEventUtil eventUtil = new BDCommonEventUtil(
				IBDMetaDataIDConst.SUPPLIER);
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateBeforeEvent(supplierVO);
		// �����
		this.dao.updateVO(supplierVO);

		// ����֪ͨ
		CacheProxy.fireDataUpdated(supplierVO.getTableName(),
				supplierVO.getPrimaryKey());
		// ���º��¼�
		supplierVO = this.getSupQryService().querySupplierVOByPk(
				supplierVO.getPrimaryKey());
		new BDBusiLogUtil(IBDMetaDataIDConst.SUPPLIER).writeBusiLog(
				"synsupplier", null, supplierVO);
		eventUtil.setOldObjs(new Object[] { oldVO });
		eventUtil.dispatchUpdateAfterEvent(supplierVO);
		return supplierVO;
	}

	@Override
	public Map<String, SuperVO> updateCustAndRelaToSup(CustomerVO updateVO,
			SupplierVO supplierVO) throws BusinessException {
		// ����
		BDPKLockUtil.lockString(new String[] { updateVO.getPrimaryKey(),
				supplierVO.getPrimaryKey() });
		// �汾У��
		BDVersionValidationUtil.validateSuperVO(updateVO);
		BDVersionValidationUtil.validateSuperVO(supplierVO);
		// ҵ���߼�У��
		boolean isCustomer = supplierVO.getIscustomer() == null ? false
				: supplierVO.getIscustomer().booleanValue();
		if (isCustomer) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0000")/*
												 * @res "�������Ĺ�Ӧ�̲���Ϊ���̡�"
												 */);
		}
		updateVO = this.getCustService().updateCustomerVO(updateVO, true);
		fireRelateSupEvent(updateVO, supplierVO);
		return this.dealCustSupRela(updateVO, supplierVO,
				ICustomerSupplierConst.CUSTSUP_CUST);
	}

	@Override
	public CustSupplierVO updateCustSupVO(CustSupplierVO custSupVO)
			throws BusinessException {
		if (custSupVO == null) {
			return null;
		}

		// ����ʱ�ļ�������
		this.updatelockOperate(custSupVO);

		// У��汾
		BDVersionValidationUtil.validateSuperVO(custSupVO);

		// ��ȡ����ǰ��OldVO
		CustSupplierVO oldVO = this.retrieveVO(custSupVO.getPrimaryKey());

		// ҵ��У���߼�
		this.updateValidateVO(oldVO, custSupVO);

		// ����ǰ�¼�����
		this.fireBeforeUpdateEvent(oldVO, custSupVO);

		// �����
		this.dbUpdateVO(custSupVO);

		// ���»���
		this.notifyVersionChangeWhenDataUpdated(custSupVO);

		// ���¼�����������
		custSupVO = this.retrieveVO(custSupVO.getPrimaryKey());

		// ���º��¼�֪ͨ
		this.fireAfterUpdateEvent(oldVO, custSupVO);

		// ҵ����־
		this.writeUpdatedBusiLog(oldVO, custSupVO);

		return custSupVO;

	}

	@Override
	public Map<String, SuperVO> updateSupAndRelaToCust(SupplierVO updateVO,
			CustomerVO customerVO) throws BusinessException {
		// ����
		BDPKLockUtil.lockString(new String[] { updateVO.getPrimaryKey(),
				customerVO.getPrimaryKey() });
		// �汾У��
		BDVersionValidationUtil.validateSuperVO(updateVO);
		BDVersionValidationUtil.validateSuperVO(customerVO);
		// ҵ���߼�У��
		boolean isSupplier = customerVO.getIssupplier() == null ? false
				: customerVO.getIssupplier().booleanValue();
		if (isSupplier) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0001")/* @res "�������Ŀͻ�����Ϊ���̡�" */);
		}
		updateVO = this.getSupplierService().updateSupplierVO(updateVO, false);
		fireRelateCustEvent(customerVO, updateVO);
		return this.dealCustSupRela(customerVO, updateVO,
				ICustomerSupplierConst.CUSTSUP_CUSTSUP);
	}

	protected CustSupplierVO[] retrieveVOs(CustSupplierVO[] vos)
			throws BusinessException {
		List<String> pkList = VOUtil.extractFieldValues(vos,
				CustSupplierVO.PK_CUST_SUP, null);
		CustSupplierVO[] vo = this.getCustSupQryService().queryCustSupVO(
				pkList.toArray(new String[0]));
		return vo;
	}

	private void checkCustAndSupAreFreecust(CustomerVO customerVO,
			SupplierVO supplierVO) throws BusinessException {
		boolean custIsFreecust = customerVO.getIsfreecust() == null ? false
				: customerVO.getIsfreecust().booleanValue();
		boolean supIsFreecust = supplierVO.getIsfreecust() == null ? false
				: supplierVO.getIsfreecust().booleanValue();
		if (custIsFreecust || supIsFreecust) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0006")/*
												 * @res "���������Ŀͻ��͹�Ӧ�̾�����Ϊɢ����"
												 */);
		}
	}

	@SuppressWarnings("boxing")
	private Map<String, SuperVO> dealCustSupRela(CustomerVO customerVO,
			SupplierVO supplierVO, int accclass) throws BusinessException {
		Map<String, SuperVO> map = new HashMap<String, SuperVO>();
		// ���ͻ��͹�Ӧ�̹���
		this.doRelaCheck(customerVO, supplierVO);
		// ���¿ͻ�
		customerVO.setIssupplier(UFBoolean.TRUE);
		customerVO.setPk_supplier(customerVO.getPrimaryKey());
		CustomerVO newCustVO = this.synCustomerInfo(customerVO);
		// ���¹�Ӧ��
		supplierVO.setIscustomer(UFBoolean.TRUE);
		supplierVO.setCorcustomer(customerVO.getPrimaryKey());
		SupplierVO newSupVO = this.synSupplierInfo(supplierVO);
		// �ı乩Ӧ������
		SupplierVO returnSupVO = this.getSupplierService().changeSupplierPK(
				newSupVO, customerVO.getPrimaryKey());
		// �����ϲ�ǰ�¼�
		EventDispatcher.fireEvent(new BDCommonEvent(this.getMDId(),
				IEventType.TYPE_PKMERGE_BEFORE,
				new String[] { customerVO.getPrimaryKey(),
						supplierVO.getPrimaryKey() }, new String[] { customerVO
						.getPrimaryKey() }));
		// ���¿��̣�ɾ������ǰ��Ӧ���ڿ��̱�ļ�¼�����¹���ǰ�ͻ��ڿ��̱�ļ�¼
		this.deleteCustSupplierVO(supplierVO.getPrimaryKey());
		CustSupplierVO custSupVO = this.getCustSupQryService()
				.queryCustSupVOByPk(customerVO.getPrimaryKey());
		custSupVO.setCustsuptype(ICustomerSupplierConst.CUSTSUP_CUSTSUP);
		custSupVO.setPk_custclass(customerVO.getPk_custclass());
		custSupVO.setPk_supplierclass(supplierVO.getPk_supplierclass());
		this.getBusiLogUtil().writeBusiLog(
				"relate",
				MainNCLangRes.getInstance().getStrByID("10140custsup",
						"010140custsup0068")/* "[xx]�ͻ�����[xx]��Ӧ��" */, custSupVO);
		this.updateCustSupVO(custSupVO);
		// �����ϲ����¼�
		EventDispatcher.fireEvent(new BDCommonEvent(this.getMDId(),
				IEventType.TYPE_PKMERGE_AFTER,
				new String[] { customerVO.getPrimaryKey(),
						supplierVO.getPrimaryKey() }, new String[] { customerVO
						.getPrimaryKey() }));
		// ���¿�������
		this.replaceRefrelationPK(supplierVO.getPrimaryKey(),
				custSupVO.getPrimaryKey());
		map.put(ICustomerSupplierConst.CUSTOMER, newCustVO);
		map.put(ICustomerSupplierConst.SUPPLIER, returnSupVO);
		shareCustSupBankaccRelations(newCustVO.getPk_customer(), accclass);
		return map;
	}

	/**
	 * 
	 * <p>
	 * ˵�������������˻�����
	 * <li></li>
	 * </p>
	 * 
	 * @param pk_custsup
	 * @param accclass
	 * @throws BusinessException
	 * @date 2014��11��8�� ����1:02:01
	 * @since NC6.36
	 */
	private void shareCustSupBankaccRelations(String pk_custsup, int accclass)
			throws BusinessException {
		getCustBankaccShareService().shareCustSupBankaccs(pk_custsup, accclass);
	}

	private void doRelaCheck(CustomerVO customerVO, SupplierVO supplierVO)
			throws BusinessException {
		String custFinanceorg = customerVO.getPk_financeorg();
		String supFinanceorg = supplierVO.getPk_financeorg();
		if (!StringUtils.equals(custFinanceorg, supFinanceorg)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("10140custsup",
							"010140custsup0008")/*
												 * @res
												 * "�ͻ��͹�Ӧ�̵Ķ�Ӧ������֯��һ�£��޷���������"
												 */);
		}
	}

	private String generateNewPk() {
		String corpPk = SQLHelper.getCorpPk();
		String newPk = new SequenceGenerator(InvocationInfoProxy.getInstance()
				.getUserDataSource()).generate(corpPk);
		return newPk;
	}

	private ICustBaseInfoQueryService getCustQryService() {
		if (this.custQryService == null) {
			this.custQryService = NCLocator.getInstance().lookup(
					ICustBaseInfoQueryService.class);
		}
		return this.custQryService;
	}

	private ICustBaseInfoService getCustService() {
		if (this.custService == null) {
			this.custService = NCLocator.getInstance().lookup(
					ICustBaseInfoService.class);
		}
		return this.custService;
	}

	private ICustSupQueryService getCustSupQryService() {
		if (this.custSupQryService == null) {
			this.custSupQryService = NCLocator.getInstance().lookup(
					ICustSupQueryService.class);
		}
		return this.custSupQryService;
	}

	private ISupplierBaseInfoService getSupplierService() {
		if (this.supplierService == null) {
			this.supplierService = NCLocator.getInstance().lookup(
					ISupplierBaseInfoService.class);
		}
		return this.supplierService;
	}

	private ISupplierBaseInfoQryService getSupQryService() {
		if (this.supQryService == null) {
			this.supQryService = NCLocator.getInstance().lookup(
					ISupplierBaseInfoQryService.class);
		}
		return this.supQryService;
	}

	private void setDisableState(CustSupplierVO[] custSupVOs, String enableFeild) {
		for (CustSupplierVO custsupVO : custSupVOs) {
			custsupVO.setAttributeValue(enableFeild,
					IPubEnumConst.ENABLESTATE_DISABLE);
		}
	}

	private void setEnableState(CustSupplierVO[] custSupVOs, String enableFeild) {
		for (CustSupplierVO custsupVO : custSupVOs) {
			custsupVO.setAttributeValue(enableFeild,
					IPubEnumConst.ENABLESTATE_ENABLE);
		}
	}

	private void setVOStatus(SuperVO[] vos, int status) {
		if (vos == null) {
			return;
		}
		for (SuperVO vo : vos) {
			vo.setStatus(status);
		}
	}

	private void replaceRefrelationPK(String sourcepk, String targetpk)
			throws BusinessException {
		// ��ѯ������Ϣ
		IReferenceCheck refChecker = NCLocator.getInstance().lookup(
				IReferenceCheck.class);
		List<RefRelationVO> refRelationList = refChecker.getRefList(
				CustSupplierVO.getDefaultTableName(), null, null);
		for (int i = 0; i < refRelationList.size(); i++) {
			RefRelationVO currentRelationVO = refRelationList.get(i);
			String columnName = currentRelationVO.getReferencingtablecolumn();
			String refTableName = currentRelationVO.getReferencingtablename();
			// ִ�������滻
			StringBuilder updateSQL = new StringBuilder("update ")
					.append(refTableName).append(" set ").append(columnName)
					.append(" = ?").append(" where ").append(columnName)
					.append(" = ?");
			SQLParameter param = new SQLParameter();
			param.addParam(targetpk);
			param.addParam(sourcepk);
			try {
				new BaseDAO().executeUpdate(updateSQL.toString(), param);
			} catch (BusinessException e) {
				Logger.error("�����滻ʧ�ܣ�ԭ��" + e.getMessage(), e);
			}
		}
	}

	/**
	 * �����ںϲ�ʱ����ɾ�����̱��еĹ�Ӧ�̻��߿ͻ���¼ʱ��������У�鲻�������⣬���Բ���ֱ�Ӵ����ݿ�ɾ��
	 * 
	 * @param pk_cust_sup
	 * @throws BusinessException
	 */
	private void deleteCustSupplierVO(String pk_cust_sup)
			throws BusinessException {
		BaseDAO baseDAO = new BaseDAO();
		baseDAO.deleteByPK(CustSupplierVO.class, pk_cust_sup);
	}

	/**
	 * ���������˻��������
	 */
	private ICustSupBankaccShareService getCustBankaccShareService() {
		return NCLocator.getInstance()
				.lookup(ICustSupBankaccShareService.class);
	}

	private CustomerVO[] getNeedCreateSupCustomers(CustomerVO[] vos) {
		List<CustomerVO> list = new ArrayList<CustomerVO>();
		for (CustomerVO vo : vos) {
			if (vo.getIssupplier() != null
					&& !vo.getIssupplier().booleanValue()) {
				list.add(vo);
			}
		}
		return list.toArray(new CustomerVO[0]);
	}

	private SupplierVO[] getInserSupplier(Map<String, String> supClassMap,
			CustomerVO[] vos) {
		SupplierVO[] supvos = new SupplierVO[vos.length];
		for (int i = 0; i < vos.length; i++) {
			SupplierVO supvo = new SupplierVO();
			// supvo.setCode(vos[i].getCode());
			supvo.setName(vos[i].getName());
			supvo.setPk_group(vos[i].getPk_group());
			supvo.setPk_org(vos[i].getPk_org());
			supvo.setName2(vos[i].getName2());
			supvo.setName3(vos[i].getName3());
			supvo.setName4(vos[i].getName4());
			supvo.setName5(vos[i].getName5());
			supvo.setName6(vos[i].getName6());
			supvo.setShortname(vos[i].getShortname());
			supvo.setPk_supplierclass(supClassMap.get(vos[i].getPk_customer()));
			supvo.setCorcustomer(vos[i].getPk_customer());
			supvo.setPk_country(vos[i].getPk_country());
			supvo.setPk_format(vos[i].getPk_format());
			supvo.setPk_timezone(vos[i].getPk_timezone());
			supvo.setPk_financeorg(vos[i].getPk_financeorg());
			supvo.setPk_areacl(vos[i].getPk_areacl());
			supvo.setMnecode(vos[i].getMnecode());
			supvo.setTaxpayerid(vos[i].getTaxpayerid());
			supvo.setTel1(vos[i].getTel1());
			supvo.setTel2(vos[i].getTel2());
			supvo.setTel3(vos[i].getTel3());
			supvo.setDef1(vos[i].getDef1());
			supvo.setDef2(vos[i].getDef2());
			supvo.setDef3(vos[i].getDef3());
			supvo.setDef4(vos[i].getDef4());
			supvo.setDef5(vos[i].getDef5());
			supvo.setDef6(vos[i].getDef6());
			supvo.setDef7(vos[i].getDef7());
			supvo.setDef8(vos[i].getDef8());
			supvo.setDef9(vos[i].getDef9());
			supvo.setDef10(vos[i].getDef10());
			supvo.setDef11(vos[i].getDef11());
			supvo.setDef12(vos[i].getDef12());
			supvo.setDef13(vos[i].getDef13());
			supvo.setDef14(vos[i].getDef14());
			supvo.setDef15(vos[i].getDef15());
			supvo.setDef16(vos[i].getDef16());
			supvo.setDef17(vos[i].getDef17());
			supvo.setDef18(vos[i].getDef18());
			supvo.setDef19(vos[i].getDef19());
			supvo.setDef20(vos[i].getDef20());
			supvo.setDef21(vos[i].getDef21());
			supvo.setDef22(vos[i].getDef22());
			supvo.setDef23(vos[i].getDef23());
			supvo.setDef24(vos[i].getDef24());
			supvo.setDef25(vos[i].getDef25());
			supvo.setDef26(vos[i].getDef26());
			supvo.setDef27(vos[i].getDef27());
			supvo.setDef28(vos[i].getDef28());
			supvo.setDef29(vos[i].getDef29());
			supvo.setDef30(vos[i].getDef30());
			supvos[i] = supvo;
			String code = getSupplierCodeByBillCodeContext(supvos[i]);
			if (StringUtils.isEmpty(code)) {
				supvos[i].setCode(vos[i].getCode());
			} else {
				supvos[i].setCode(code);
			}
		}
		return supvos;
	}

	private Validator[] getCreateNewSupValidator() {
		// �ǿ�
		Validator notNullValidator = new SupplierNotNullValidator();
		// Ψһ��
		Validator uniqueValidator = new BDUniqueRuleValidate();
		Validator disValidator = new DistributedAddBaseValidator();
		// ��Ӧ�̻�������
		Validator stopValidator = new SupClassIsDisableValidator();
		Validator repeatValidator = new SupplierRepeatValidator();
		Validator itemsValidator = new SupplierItemsValidator();
		Validator intSupplierValidator = new IntSupplierValidator(null);
		Validator custSupFinanceorgValidator = new CustSupFinanceorgValidator();
		Validator linkmanValidator = new SupplierLinkmanDefaultValidator();
		Validator freecustValidator = new SupplierSetfreecustValidator();
		Validator supvatValidator = new SupVatValidator(null);
		Validator supcountryvatValidator = new SupcountrytaxesValidator(null);
		Validator refPkExistValidator = new RefPkExistsValidator(
				SupplierVO.PK_SUPPLIERCLASS);
		Validator[] validators = new Validator[] { notNullValidator,
				uniqueValidator, disValidator, stopValidator, repeatValidator,
				itemsValidator, intSupplierValidator,
				custSupFinanceorgValidator, linkmanValidator,
				supcountryvatValidator, supvatValidator, freecustValidator,
				refPkExistValidator };

		return validators;
	}

	private Validator[] getSynCustomerValidator() {
		return new Validator[] { new SingleDistributedUpdateValidator(),
				new CustSupFinanceorgValidator() };
	}

	/**
	 * ��ʱû�п��ǿ��̵�У��
	 * 
	 * @param cvos
	 * @param svos
	 * @param util
	 * @throws BusinessException
	 */
	private void validatorSupsAndRelaToCust(CustomerVO[] cvos,
			SupplierVO[] svos, ErrorLogUtil util) throws BusinessException {
		IValidationService validateSupService = ValidationFrameworkUtil
				.createValidationService(getCreateNewSupValidator());
		IValidationService validateCusService = ValidationFrameworkUtil
				.createValidationService(getSynCustomerValidator());
		for (int i = 0; i < svos.length; i++) {
			try {
				validateSupService.validate(svos[i]);
				validateCusService.validate(cvos[i]);
				this.insertSupAndRelaToCust(svos[i], cvos[i]);
			} catch (ValidationException e) {
				util.addLogMsgArrayBatch("����[{" + svos[i].getCode()
						+ "}]�Ŀͻ����ɹ�Ӧ��ʧ�ܣ�" + e.getMessage());
			}
		}
		util.writeLogMsgBatch();

	}

	@Override
	public ErrLogReturnValue insertSupsAndRelaToCust(String pk_suplierclass,
			CustomerVO[] vos) throws BusinessException {
		String pk_user = InvocationInfoProxy.getInstance().getUserId();
		CustomerVO[] needCreateCustomer = getNeedCreateSupCustomers(vos);
		ErrorLogUtil util = new ErrorLogUtil(IBDMetaDataIDConst.CUSTOMER,
				pk_user, "�������ɹ�Ӧ��", false);
		if (needCreateCustomer == null || needCreateCustomer.length == 0) {
			return util.getErrLogReturnValue(null, vos.length);
		}

		Map<String, String> custClassMap = getSupClassByCustomer(needCreateCustomer);
		CustomerVO[] cvos = getCouldCreateCust(needCreateCustomer, custClassMap);

		SupplierVO[] supvos = getInserSupplier(custClassMap, cvos);
		// ��������ǰУ��
		validatorSupsAndRelaToCust(cvos, supvos, util);
		return util.getErrLogReturnValue(cvos, cvos.length);
	}

	private SupplierVO[] getNeedCreateCustSupplier(SupplierVO[] vos) {
		List<SupplierVO> list = new ArrayList<SupplierVO>();
		for (SupplierVO vo : vos) {
			if (vo.getIscustomer() != null
					&& !vo.getIscustomer().booleanValue()) {
				list.add(vo);
			}
		}
		return list.toArray(new SupplierVO[0]);
	}

	private CustomerVO[] getInsertCustomer(Map<String, String> custClassMap,
			SupplierVO[] vos) {
		CustomerVO[] cvos = new CustomerVO[vos.length];
		for (int i = 0; i < vos.length; i++) {
			CustomerVO cvo = new CustomerVO();
			cvo.setName(vos[i].getName());
			cvo.setPk_group(vos[i].getPk_group());
			cvo.setPk_org(vos[i].getPk_org());
			cvo.setName2(vos[i].getName2());
			cvo.setName3(vos[i].getName3());
			cvo.setName4(vos[i].getName4());
			cvo.setName5(vos[i].getName5());
			cvo.setName6(vos[i].getName6());
			cvo.setShortname(vos[i].getShortname());
			cvo.setPk_custclass(custClassMap.get(vos[i].getPk_supplier()));
			cvo.setPk_country(vos[i].getPk_country());
			cvo.setPk_timezone(vos[i].getPk_timezone());
			cvo.setPk_format(vos[i].getPk_format());
			cvo.setPk_financeorg(vos[i].getPk_financeorg());
			cvo.setPk_areacl(vos[i].getPk_areacl());
			cvo.setMnecode(vos[i].getMnecode());
			cvo.setTaxpayerid(vos[i].getTaxpayerid());
			cvo.setTel1(vos[i].getTel1());
			cvo.setTel2(vos[i].getTel2());
			cvo.setTel3(vos[i].getTel3());
			cvo.setTel1(vos[i].getTel1());
			cvo.setTel2(vos[i].getTel2());
			cvo.setTel3(vos[i].getTel3());
			cvo.setDef1(vos[i].getDef1());
			cvo.setDef2(vos[i].getDef2());
			cvo.setDef3(vos[i].getDef3());
			cvo.setDef4(vos[i].getDef4());
			cvo.setDef5(vos[i].getDef5());
			cvo.setDef6(vos[i].getDef6());
			cvo.setDef7(vos[i].getDef7());
			cvo.setDef8(vos[i].getDef8());
			cvo.setDef9(vos[i].getDef9());
			cvo.setDef10(vos[i].getDef10());
			cvo.setDef11(vos[i].getDef11());
			cvo.setDef12(vos[i].getDef12());
			cvo.setDef13(vos[i].getDef13());
			cvo.setDef14(vos[i].getDef14());
			cvo.setDef15(vos[i].getDef15());
			cvo.setDef16(vos[i].getDef16());
			cvo.setDef17(vos[i].getDef17());
			cvo.setDef18(vos[i].getDef18());
			cvo.setDef19(vos[i].getDef19());
			cvo.setDef20(vos[i].getDef20());
			cvo.setDef21(vos[i].getDef21());
			cvo.setDef22(vos[i].getDef22());
			cvo.setDef23(vos[i].getDef23());
			cvo.setDef24(vos[i].getDef24());
			cvo.setDef25(vos[i].getDef25());
			cvo.setDef26(vos[i].getDef26());
			cvo.setDef27(vos[i].getDef27());
			cvo.setDef28(vos[i].getDef28());
			cvo.setDef29(vos[i].getDef29());
			cvo.setDef30(vos[i].getDef30());
			cvos[i] = cvo;
			String code = getCustCodeByBillCodeContext(cvos[i]);
			if (StringUtils.isEmpty(code)) {
				cvos[i].setCode(vos[i].getCode());
			} else {
				cvos[i].setCode(code);
			}
		}
		return cvos;
	}

	/**
	 * ��ʱû�п��ǿ��̵�У��
	 * 
	 * @param cvos
	 * @param svos
	 * @param util
	 * @throws BusinessException
	 */
	private void validatorCustAndRelaToSup(SupplierVO[] svos,
			CustomerVO[] cvos, ErrorLogUtil util) throws BusinessException {
		IValidationService validateCusService = ValidationFrameworkUtil
				.createValidationService(getCreateNewCustValidator());
		IValidationService validateSupService = ValidationFrameworkUtil
				.createValidationService(getSynSupplierValidator());
		for (int i = 0; i < svos.length; i++) {
			try {
				validateSupService.validate(svos[i]);
				validateCusService.validate(cvos[i]);
				this.insertCustAndRelaToSup(cvos[i], svos[i]);
			} catch (ValidationException e) {
				util.addLogMsgArrayBatch("����[{" + svos[i].getCode()
						+ "}]�Ĺ�Ӧ�����ɿͻ�ʧ�ܣ�" + e.getMessage());
			}
		}
		util.writeLogMsgBatch();

	}

	private Validator[] getSynSupplierValidator() {
		return new Validator[] { new SingleDistributedUpdateValidator(),
				new CustSupFinanceorgValidator() };
	}

	private Validator[] getCreateNewCustValidator() {
		// �ǿ�У��
		NullValueValidator notNullValidator = NullValueValidator
				.createMDNullValueValidator(CustomerVO.class.getName(), Arrays
						.asList(CustomerVO.PK_GROUP, CustomerVO.PK_ORG,
								CustomerVO.CODE, CustomerVO.NAME,
								CustomerVO.ISSUPPLIER, CustomerVO.PK_CUSTCLASS,
								CustomerVO.ENABLESTATE));
		// ɢ��У��
		FreeCustValidator freeCustValidator = new FreeCustValidator();
		// ��ϵ��У��
		CustLinkmanValidator linkmanValidator = new CustLinkmanValidator(null);
		// �ͻ��������У��
		CustClassDisableValidator custClassDisableValidator = new CustClassDisableValidator();
		// �ڲ��ͻ�У��
		IntCustomerValidator intCustomerValidator = new IntCustomerValidator(
				null);
		// ��Ӧ������֯ΨһУ��
		CustSupFinanceorgValidator custSupFinanceorgValidator = new CustSupFinanceorgValidator();
		// �ͻ�����˰��Ψһ��У��
		CusttaxtypesValidator custtaxtypesvalidator = new CusttaxtypesValidator(
				null);

		// �ͻ�VATΨһ��У��
		CustVatValidator custvatvalidator = new CustVatValidator(null);

		// �������У��
		RefPkExistsValidator refPkExistsValidator = new RefPkExistsValidator(
				CustomerVO.PK_CUSTCLASS);
		List<Validator> list = new ArrayList<Validator>();
		list.addAll(Arrays.asList(super.getInsertValidator()));
		list.addAll(Arrays
				.asList(new Validator[] { notNullValidator, freeCustValidator,
						linkmanValidator, custClassDisableValidator,
						intCustomerValidator, custSupFinanceorgValidator,
						custtaxtypesvalidator, custvatvalidator,
						refPkExistsValidator }));
		return list.toArray(new Validator[0]);
	}

	@Override
	public ErrLogReturnValue insertCustsAndRelaToSups(String pk_custclass,
			SupplierVO[] vos) throws BusinessException {
		String pk_user = InvocationInfoProxy.getInstance().getUserId();
		SupplierVO[] needCreateSupplier = getNeedCreateCustSupplier(vos);
		for (int i = 0; i < needCreateSupplier.length; i++) {
			SupplierVO vo = needCreateSupplier[i];
			Logger.debug("��̨��õĹ�Ӧ�̵�pk" + vo.getPk_supplier());
		}
		ErrorLogUtil util = new ErrorLogUtil(IBDMetaDataIDConst.SUPPLIER,
				pk_user, "�������ɿͻ�", false);
		if (needCreateSupplier == null || needCreateSupplier.length == 0) {
			return util.getErrLogReturnValue(null, vos.length);
		}
		Map<String, String> custclassMap = getCustClassBySupplier(needCreateSupplier);
		SupplierVO[] filterSupplierVOs = getCouldCreateSup(needCreateSupplier,
				custclassMap);
		CustomerVO[] cvos = getInsertCustomer(custclassMap, filterSupplierVOs);
		// ��������ǰУ��
		validatorCustAndRelaToSup(filterSupplierVOs, cvos, util);
		return util.getErrLogReturnValue(filterSupplierVOs,
				filterSupplierVOs.length);
	}

	private Map<String, String> getCustClassBySupplier(SupplierVO[] supplierVOs) {
		Map<String, String> custClassMap = new HashMap<String, String>();
		for (SupplierVO supplierVO : supplierVOs) {
			IGeneralAccessor supclassAccessor = GeneralAccessorFactory
					.getAccessor(IBDMetaDataIDConst.SUPPLIERCLASS);
			IBDData supClass = supclassAccessor.getDocByPk(supplierVO
					.getPk_supplierclass());
			String supClassPk_org = supClass.getPk_org();
			String supClasscode = supClass.getCode();
			IGeneralAccessor custclassAccessor = GeneralAccessorFactory
					.getAccessor(IBDMetaDataIDConst.CUSTCLASS);
			IBDData custClass = custclassAccessor.getDocByCode(supClassPk_org,
					supClasscode);
			if (custClass != null) {
				custClassMap
						.put(supplierVO.getPk_supplier(), custClass.getPk());
			}
		}
		return custClassMap;
	}

	private SupplierVO[] getCouldCreateSup(SupplierVO[] supplierVOs,
			Map<String, String> custClassMap) {
		List<SupplierVO> list = new ArrayList<SupplierVO>();
		for (int i = 0; i < supplierVOs.length; i++) {
			if (custClassMap.get(supplierVOs[i].getPk_supplier()) != null) {
				list.add(supplierVOs[i]);
			}
		}
		return list.toArray(new SupplierVO[0]);
	}

	private Map<String, String> getSupClassByCustomer(CustomerVO[] customerVOs) {
		Map<String, String> supClassMap = new HashMap<String, String>();
		for (CustomerVO customerVO : customerVOs) {
			IGeneralAccessor custclassAccessor = GeneralAccessorFactory
					.getAccessor(IBDMetaDataIDConst.CUSTCLASS);
			IBDData custClass = custclassAccessor.getDocByPk(customerVO
					.getPk_custclass());
			String custClassPk_org = custClass.getPk_org();
			String custClassCode = custClass.getCode();
			IGeneralAccessor supclassAccessor = GeneralAccessorFactory
					.getAccessor(IBDMetaDataIDConst.SUPPLIERCLASS);
			IBDData supclass = supclassAccessor.getDocByCode(custClassPk_org,
					custClassCode);
			if (supclass != null) {
				supClassMap.put(customerVO.getPk_customer(), supclass.getPk());
			}
		}
		return supClassMap;
	}

	private CustomerVO[] getCouldCreateCust(CustomerVO[] customerVOs,
			Map<String, String> supClassMap) {
		List<CustomerVO> list = new ArrayList<CustomerVO>();
		for (int i = 0; i < customerVOs.length; i++) {
			if (supClassMap.get(customerVOs[i].getPk_customer()) != null) {
				list.add(customerVOs[i]);
			}
		}
		return list.toArray(new CustomerVO[0]);
	}

	private IBillcodeManage getBillcodeManage() {
		if (this.billcodeManage == null) {
			this.billcodeManage = NCLocator.getInstance().lookup(
					IBillcodeManage.class);
		}
		return this.billcodeManage;
	}

	/**
	 * ������֯������ȡ��Ӧ�̵ı������������
	 * 
	 * @param pk_org
	 * @return
	 */
	private BillCodeContext getSupplierBillCodeContextByPk_org(String pk_group,
			String pk_org) {
		BillCodeContext billCodeContext = this.supOrgID_billCodeContext_map
				.get(pk_org);
		if (billCodeContext == null) {
			try {
				billCodeContext = getBillcodeManage().getBillCodeContext(
						ISupplierConst.SUPPLIER_NBCRCODE, pk_group, pk_org);
				this.supOrgID_billCodeContext_map.put(pk_org, billCodeContext);
			} catch (BusinessException e) {
				throw new BusinessExceptionAdapter(e);
			}
		}
		return billCodeContext;
	}

	/**
	 * 
	 * <p>
	 * ˵������ȡ��Ӧ�̵ı���
	 * <li></li>
	 * </p>
	 * 
	 * @param supplierVO
	 * @return
	 * @date 2014��11��14�� ����3:57:02
	 * @since NC6.31
	 */
	private String getSupplierCodeByBillCodeContext(SupplierVO supplierVO) {
		BillCodeContext billCodeContext = getSupplierBillCodeContextByPk_org(
				supplierVO.getPk_group(), supplierVO.getPk_org());
		if (billCodeContext == null) {
			return null;
		}
		String code = null;
		// ����뱣���ʱ��ᴦ�����ﲻ�ٴ���
		if (billCodeContext.isPrecode()) {
			try {
				// ǰ����ʱ����ȡ�Զ�����
				code = getBillcodeManage().getPreBillCode_RequiresNew(
						ISupplierConst.SUPPLIER_NBCRCODE,
						supplierVO.getPk_group(), supplierVO.getPk_org());
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
				throw new BusinessExceptionAdapter(e);
			}
		}
		return code;
	}

	/**
	 * ������֯������ȡ�ͻ��ı������������
	 * 
	 * @param pk_org
	 * @return
	 */
	private BillCodeContext getCustBillCodeContextByPk_org(String pk_group,
			String pk_org) {
		BillCodeContext billCodeContext = this.custOrgID_billCodeContext_map
				.get(pk_org);
		if (billCodeContext == null) {
			try {
				billCodeContext = getBillcodeManage().getBillCodeContext(
						ICustConst.BILLCODE_CUSTOMER, pk_group, pk_org);
				this.custOrgID_billCodeContext_map.put(pk_org, billCodeContext);
			} catch (BusinessException e) {
				throw new BusinessExceptionAdapter(e);
			}
		}
		return billCodeContext;
	}

	/**
	 * 
	 * <p>
	 * ˵������ȡ�ͻ��ı���
	 * <li></li>
	 * </p>
	 * 
	 * @param supplierVO
	 * @return
	 * @date 2014��11��14�� ����3:57:02
	 * @since NC6.31
	 */
	private String getCustCodeByBillCodeContext(CustomerVO customerVO) {
		BillCodeContext billCodeContext = getCustBillCodeContextByPk_org(
				customerVO.getPk_group(), customerVO.getPk_org());
		if (billCodeContext == null) {
			return null;
		}
		String code = null;
		// ����뱣���ʱ��ᴦ�����ﲻ�ٴ���
		if (billCodeContext.isPrecode()) {
			try {
				// ǰ����ʱ����ȡ�Զ�����
				code = getBillcodeManage().getPreBillCode_RequiresNew(
						ICustConst.BILLCODE_CUSTOMER, customerVO.getPk_group(),
						customerVO.getPk_org());
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
				throw new BusinessExceptionAdapter(e);
			}
		}
		return code;
	}

	private Map<String, String> countryZoneDoc;
	private Map<String, String> timeZoneDoc;
	private Map<String, String> formatDoc;
	// private Map<String, String> nc57Org2nc65Org;
	private Map<String, String> custClassDoc;
	private Map<String, String> supplierclassDoc;
	private Map<String, String> areaClassDoc;

	/**
	 * ���̵���ͬ��57->65 <br>
	 * ����57���̵����Զ�����3�洢ͬ��ʱ���, <br>
	 * 1.ƥ��57-65����֯ӳ���ϵ-�Զ������(�������룺KSDZB)<br>
	 * 2.�ֲ�ͻ����� <br>
	 * 3.�ֲ�Ӧ������<br>
	 */
	@Override
	public void syncCustProcess() throws BusinessException {
		// ��������
		ArrayList<CustomerVO> customerInsertList = new ArrayList<CustomerVO>();
		ArrayList<CustSupplierVO> custSupplierVOInsertList = new ArrayList<CustSupplierVO>();
		// �޸�����
		ArrayList<CustomerVO> customerUpdateList = new ArrayList<CustomerVO>();
		ArrayList<CustSupplierVO> custSupplierVOUpdate = new ArrayList<CustSupplierVO>();
		ArrayList<SupplierVO> supplierUpdateList = new ArrayList<SupplierVO>();
		// ����У������
		// �ظ����̱���
		HashSet<String> repeatCustCodeSet = new HashSet<String>();
		// �ظ���˰�˵ǼǺ�
		HashSet<String> repeateTaxpayeridSet = new HashSet<String>();
		BaseDAO baseDAO57 = new BaseDAO("yuyuan57");
		// if (1 == 1) {
		// String sql = "update shyy.bd_cubasdoc t set t.def3 = ''";
		// baseDAO57.executeUpdate(sql);
		// return;
		// }
		// 57���̵����������ݲ�ѯ����װ
		String insertSql = getInserQuerySql();
		// 57���̵����޸����ݲ�ѯ����װ
		String updatesql = getUpdateQuerySql();
		@SuppressWarnings("unchecked")
		// ��ѯ����������57���̵�������
		List<CustBasVO> insertCustBasVOList = (List<CustBasVO>) baseDAO57
				.executeQuery(insertSql, new BeanListProcessor(CustBasVO.class));
		// ��ѯ�����޸ĵ�57���̵�������
		@SuppressWarnings("unchecked")
		List<CustBasVO> updateCustBasVOList = (List<CustBasVO>) baseDAO57
				.executeQuery(updatesql, new BeanListProcessor(CustBasVO.class));

		HashMap<String, CustBasVO> code2custBasVOInsert = new HashMap<String, CustBasVO>();
		// HashMap<String, CustBasVO> code2custBasVOUpdate = new HashMap<String,
		// CustBasVO>();
		// ��ѯ���ݿ���ʷ�ͻ�����
		HashMap<String, CustomerVO> code2HistoryCustomerVO = queryHistoryCustomer("custcode");
		HashMap<String, CustomerVO> taxpayerid2HistoryCustomerVO = queryHistoryCustomer("taxpayerid");
		HashMap<String, String> custcode2custpk = new HashMap<String, String>();
		// ����������
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		String corpPk = SQLHelper.getCorpPk();
		// 1.��������
		if (insertCustBasVOList != null && insertCustBasVOList.size() > 0) {
			// 1.1���ɿͻ�
			for (CustBasVO cbvo : insertCustBasVOList) {
				custcode2custpk.put(cbvo.getCustcode(), cbvo.getPk_cubasdoc());
				// ����У��-�ظ��ͻ�����
				if (code2HistoryCustomerVO.keySet()
						.contains(cbvo.getCustcode())) {
					repeatCustCodeSet.add(cbvo.getCustcode());
					continue;
				} else {
					// Ŀ��Ϊ��57�޸ĵ�����У��
					code2HistoryCustomerVO.put(cbvo.getCustcode(), null);
				}

				// ����У��-�ظ���˰�˵ǼǺ�
				if (cbvo.getTaxpayerid() != null
						&& !"".equals(cbvo.getTaxpayerid())
						&& taxpayerid2HistoryCustomerVO.keySet().contains(
								cbvo.getTaxpayerid())) {
					repeateTaxpayeridSet.add(cbvo.getTaxpayerid());
					continue;
				} else {
					taxpayerid2HistoryCustomerVO
							.put(cbvo.getTaxpayerid(), null);
				}
				// ��������
				String pk_customer = sequenceGenerator.generate(corpPk);
				// ����CustomerVO
				CustomerVO customer = custBasVO2CustomerVOAdd(cbvo);
				customer.setPk_customer(pk_customer);
				code2custBasVOInsert.put(cbvo.getCustcode(), cbvo);
				// ����CustSupplierVO
				CustSupplierVO custSupplierVO = custBasVO2CustSupplierVOAdd(
						customer, cbvo.getPk_areacl());
				custSupplierVOInsertList.add(custSupplierVO);
				code2custBasVOInsert.put(cbvo.getCustcode(), cbvo);
				customerInsertList.add(customer);
			}
		}
		// ��������־û�����-����
		NCLocator
				.getInstance()
				.lookup(nc.itf.bd.cust.baseinfo.ICustSupplierService.class)
				.syncCustProcessInsert_RequiresNew(custSupplierVOInsertList,
						customerInsertList, code2custBasVOInsert);
		// 2.�޸�����
		if (updateCustBasVOList != null && updateCustBasVOList.size() > 0) {
			Map<String, CustSupplierVO> queryHistoryCustSupplierVO = queryHistoryCustSupplierVO();
			Map<String, SupplierVO> queryHistorySupplier = queryHistorySupplier();
			HashMap<String, CustomerVO> pk_custBasDoc2HistoryCustomerVO = new HashMap<String, CustomerVO>();
			for (String key : code2HistoryCustomerVO.keySet()) {
				// ��ֹ��ָ��
				CustomerVO customer = code2HistoryCustomerVO.get(key);
				if (customer != null) {
					pk_custBasDoc2HistoryCustomerVO.put(customer.getDef11(),
							customer);
				} else {
					String pk_cust = custcode2custpk.get(key);
					pk_custBasDoc2HistoryCustomerVO.put(pk_cust, customer);
				}
			}
			// 1.1���ɿͻ�
			for (CustBasVO cbvo : updateCustBasVOList) {
				// ����У��-�ظ��ͻ�����-���������޸ĵĿ��̱��벻�����ظ����
				// ����У��-�ظ���˰�˵ǼǺ�,����Ϊ��
				// 57���̵������̱��������޸ģ�����Ϊ�˱�֤57���̱����޸�֮���������65ͬ�����������ݲ���������ϵ
				// ��Ҫ��65��������ͬ��ʱ�����Զ�����洢ӳ���57���̵������������ݶ�Ϊ�ͻ����������Զ�����11
				CustomerVO historyCustomerVO = pk_custBasDoc2HistoryCustomerVO
						.get(cbvo.getPk_cubasdoc());
				if (historyCustomerVO == null) {
					continue;
				}
				String historyTaxpayerid = historyCustomerVO.getTaxpayerid();
				if (historyTaxpayerid == null) {
					historyTaxpayerid = "";
				}
				// ��˰�˵ǼǺ�Ϊ�ջ�����˰�˵ǼǺ�û�иı����������ظ�У��
				if (!"".equals(historyTaxpayerid)
						&& !historyTaxpayerid.equals(cbvo.getTaxpayerid())
						&& historyTaxpayerid != null
						&& taxpayerid2HistoryCustomerVO.keySet().contains(
								cbvo.getTaxpayerid())) {
					repeateTaxpayeridSet.add(cbvo.getTaxpayerid());
					continue;
				} else {
					taxpayerid2HistoryCustomerVO
							.put(cbvo.getTaxpayerid(), null);
				}
				// ����CustomerVO
				CustomerVO customer = custBasVO2CustomerVOUpdate(cbvo,
						historyCustomerVO);
				if(customer!=null){
					customerUpdateList.add(customer);
					// code2custBasVOUpdate.put(cbvo.getCustcode(), cbvo);
					// ����CustSupplierVO
					CustSupplierVO historyCustSupplierVO = queryHistoryCustSupplierVO
							.get(historyCustomerVO.getPk_customer());
					if(historyCustSupplierVO!=null){
						CustSupplierVO custSupplierVO = custBasVO2CustSupplierVOUpdate(
								customer, historyCustSupplierVO, cbvo.getPk_areacl());
						custSupplierVOUpdate.add(custSupplierVO);
					}
					// ����SupplierVO
					SupplierVO historySupplierVO = queryHistorySupplier
							.get(historyCustomerVO.getPk_customer());
					if(historySupplierVO!=null){
						SupplierVO supplierVO = custBasVO2SupplierVOUpdate(customer,
								historySupplierVO, cbvo.getPk_areacl());
						supplierUpdateList.add(supplierVO);	
					}
				}
			}
		}
		// ��������־û�����-�޸�
		NCLocator
				.getInstance()
				.lookup(nc.itf.bd.cust.baseinfo.ICustSupplierService.class)
				.syncCustProcessUpdate_RequiresNew(customerUpdateList,
						supplierUpdateList, custSupplierVOUpdate,
						updateCustBasVOList);
		// ǰ̨�쳣��Ϣ����
		StringBuffer message = new StringBuffer();
		if (repeatCustCodeSet.size() > 0) {
			message.append("���̱����ظ������̱���Ϊ��");
			Iterator<String> iterator = repeatCustCodeSet.iterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				message.append(next);
				if (iterator.hasNext())
					message.append(",");
			}
			message.append("\n");
		}
		if (repeateTaxpayeridSet.size() > 0) {
			message.append("��˰�˵ǼǺ��ظ������̱���Ϊ��");
			Iterator<String> iterator = repeateTaxpayeridSet.iterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				message.append(next);
				if (iterator.hasNext())
					message.append(",");
			}
			message.append("\n");
		}
		if (message.length() > 0) {
			throw new BusinessException(message.toString());
		}
	}

	/**
	 * ��ѯ65���ݿ���ʷ���̵�������
	 * 
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, CustSupplierVO> queryHistoryCustSupplierVO()
			throws BusinessException {
		String queryCustSupplierSql = "select * from bd_cust_supplier t where nvl(t.dr,0)=0";
		@SuppressWarnings("unchecked")
		List<CustSupplierVO> custSupplierVO65List = (List<CustSupplierVO>) this.dao
				.executeQuery(queryCustSupplierSql, new BeanListProcessor(
						CustSupplierVO.class));// ��ѯ65�ͻ�����
		if (custSupplierVO65List.size() == 0) {
			return null;
		}
		HashMap<String, CustSupplierVO> pk_custSupplier2CustSupplierVO = new HashMap<String, CustSupplierVO>();
		if (custSupplierVO65List != null && custSupplierVO65List.size() > 0) {
			for (CustSupplierVO custSupplierVO : custSupplierVO65List) {
				pk_custSupplier2CustSupplierVO.put(
						custSupplierVO.getPk_cust_sup(), custSupplierVO);
			}
		}
		return pk_custSupplier2CustSupplierVO;
	}

	/**
	 * ������̵�����������
	 * 
	 * @param customer
	 * @return
	 * @throws BusinessException
	 */
	private CustSupplierVO custBasVO2CustSupplierVOAdd(CustomerVO customer,
			String areaclCode) throws BusinessException {
		CustSupplierVO custSupplierVO = new CustSupplierVO();
		custSupplierVO.setCode(customer.getCode());
		// ��������״̬-δ���ã�1��
		custSupplierVO.setCustenablestate(1);
		// ��������-�ⲿ��λ��0��
		custSupplierVO.setCustenablestate(0);
		// ��������-�������ͣ�3��
		custSupplierVO.setCustsuptype(3);
		// �ֲ�ʽ-����������0��
		custSupplierVO.setDataoriginflag(0);
		custSupplierVO.setName(customer.getName());
		custSupplierVO.setPk_areacl(customer.getPk_areacl());
		custSupplierVO.setPk_custclass(getCustClassDoc().get(areaclCode));
		custSupplierVO.setPk_group(AppContext.getInstance().getPkGroup());
		custSupplierVO.setPk_org(AppContext.getInstance().getPkGroup());
		custSupplierVO.setPk_supplierclass(getSupplierclassDoc()
				.get(areaclCode));
		custSupplierVO.setShortname(customer.getName());
		// ��Ӧ������״̬-1(δ����)
		custSupplierVO.setSupenablestate(1);
		custSupplierVO.setTaxpayerid(customer.getTaxpayerid());
		custSupplierVO.setPk_cust_sup(customer.getPk_customer());
		//
		custSupplierVO.setCustsuptype(ICustomerSupplierConst.CUSTSUP_CUSTSUP);
		return custSupplierVO;
	}

	/**
	 * ������̵����޸�����
	 * 
	 * @param customer
	 * @param areaclCode
	 * @return
	 * @throws BusinessException
	 */
	private CustSupplierVO custBasVO2CustSupplierVOUpdate(CustomerVO customer,
			CustSupplierVO custSupplierVO, String areaclCode)
			throws BusinessException {
		custSupplierVO.setCode(customer.getCode());
		// ��������״̬-δ���ã�1��
		custSupplierVO.setCustenablestate(1);
		// ��������-�ⲿ��λ��0��
		custSupplierVO.setCustenablestate(0);
		// ��������-�������ͣ�3��
		custSupplierVO.setCustsuptype(3);
		// �ֲ�ʽ-����������0��
		custSupplierVO.setDataoriginflag(0);
		custSupplierVO.setName(customer.getName());
		custSupplierVO.setPk_areacl(customer.getPk_areacl());
		custSupplierVO.setPk_custclass(getCustClassDoc().get(areaclCode));
		custSupplierVO.setPk_group(AppContext.getInstance().getPkGroup());
		custSupplierVO.setPk_org(AppContext.getInstance().getPkGroup());
		custSupplierVO.setPk_supplierclass(getSupplierclassDoc()
				.get(areaclCode));
		custSupplierVO.setShortname(customer.getName());
		// ��Ӧ������״̬-1(δ����)
		custSupplierVO.setSupenablestate(2);
		custSupplierVO.setTaxpayerid(customer.getTaxpayerid());
		return custSupplierVO;
	}

	/**
	 * ��ѯ65���ݿ�ͻ���ʷ����
	 * 
	 * @param key
	 * @return
	 * @throws BusinessException
	 * @throws DAOException
	 */
	private HashMap<String, CustomerVO> queryHistoryCustomer(String key)
			throws BusinessException, DAOException {
		String queryCustomerSql = " select * from bd_customer b where nvl(b.dr,0)=0 ";
		@SuppressWarnings("unchecked")
		List<CustomerVO> coustomer65List = (List<CustomerVO>) this.dao
				.executeQuery(queryCustomerSql, new BeanListProcessor(
						CustomerVO.class));// ��ѯ65�ͻ�����
		HashMap<String, CustomerVO> code2CustomerVO = new HashMap<String, CustomerVO>();
		HashMap<String, CustomerVO> taxpayerid2CustomerVO = new HashMap<String, CustomerVO>();
		if (coustomer65List != null && coustomer65List.size() > 0) {
			for (CustomerVO customer : coustomer65List) {
				code2CustomerVO.put(customer.getCode(), customer);
				taxpayerid2CustomerVO.put(customer.getTaxpayerid(), customer);
			}
		}
		if ("custcode".equals(key)) {
			return code2CustomerVO;
		}
		if ("taxpayerid".equals(key)) {
			return taxpayerid2CustomerVO;
		}
		return null;
	}

	/**
	 * ��ѯ65���ݿ⹩Ӧ����ʷ����
	 * 
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, SupplierVO> queryHistorySupplier()
			throws BusinessException {
		String querySupplierSql = "select  * from bd_supplier t where nvl(t.dr,0)=0 ";
		@SuppressWarnings("unchecked")
		List<SupplierVO> supplier65List = (List<SupplierVO>) this.dao
				.executeQuery(querySupplierSql, new BeanListProcessor(
						SupplierVO.class));// ��ѯ65��Ӧ�̵���
		if (supplier65List == null || supplier65List.size() == 0) {
			return null;
		}
		HashMap<String, SupplierVO> pk_supplier2supplierVo = new HashMap<String, SupplierVO>();
		for (SupplierVO supplierVO : supplier65List) {
			pk_supplier2supplierVo.put(supplierVO.getPk_supplier(), supplierVO);
		}
		return pk_supplier2supplierVo;
	}

	/**
	 * ��д57���̵���def3��ǰ��������ʱ��� ����Ϊ���������,ԭ���̵������ӹ����������ֶ�
	 * 
	 * @param baseDAO57
	 * @param collection
	 * @throws BusinessException
	 * @throws DAOException
	 */
	private void writeBack57CubasdocUpdateTs(Collection<CustBasVO> collection)
			throws BusinessException, DAOException {
		BaseDAO baseDAO57 = new BaseDAO("yuyuan57");
		if (collection.size() > 0) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			UFDateTime serverTime = nc.vo.pubapp.AppContext.getInstance()
					.getServerTime();
			String time = serverTime.toString(null, simpleDateFormat);
			// ����ѭ��800�����ݸ���һ��,���������Դ����ʹ����ʱ�������
			int size = collection.size();
			ArrayList<CustBasVO> param = new ArrayList<CustBasVO>();
			param.addAll(collection);
			ArrayList<String> custpkList = new ArrayList<String>();
			SqlBuilder sqlBuilder2 = new SqlBuilder();
			// ѭ������
			int loopCount = size / 800;
			int remainCount = size % 800;
			if (loopCount > 0) {
				for (int i = 0; i < loopCount; i++) {
					int j = i * 800;
					int conditionCount = (i + 1) * 800;
					for (int k = j; k < conditionCount; k++) {
						CustBasVO custBasVO = param.get(k);
						custpkList.add(custBasVO.getPk_cubasdoc());
					}
					sqlBuilder2
							.append("update shyy.bd_cubasdoc t set t.def3 = '");
					sqlBuilder2.append(time);
					sqlBuilder2.append("' where ");
					sqlBuilder2.append(nc.pubitf.bd.reportitem.pub.SqlUtils
							.getInStr("t.pk_cubasdoc", custpkList, false));
					String allUpdateSql = sqlBuilder2.toString();
					baseDAO57.executeUpdate(allUpdateSql);
					// ���ö����ظ�ʹ��
					sqlBuilder2.reset();
					custpkList.clear();

				}
			}
			if (remainCount > 0) {
				int j = size - remainCount;
				int conditionCount = size;
				for (int k = j; k < conditionCount; k++) {
					CustBasVO custBasVO = param.get(k);
					custpkList.add(custBasVO.getPk_cubasdoc());
				}
				sqlBuilder2.append("update shyy.bd_cubasdoc t set t.def3 = '");
				sqlBuilder2.append(time);
				sqlBuilder2.append("' where ");
				sqlBuilder2.append(nc.pubitf.bd.reportitem.pub.SqlUtils
						.getInStr("t.pk_cubasdoc", custpkList, false));
				String allUpdateSql = sqlBuilder2.toString();
				baseDAO57.executeUpdate(allUpdateSql);
				// ���ö����ظ�ʹ��
				// sqlBuilder2.reset();
				// custpkList.clear();
			}
			// ע��ԭ��:��ʱ�������Դ�޷�ʵ��
			// baseDAO57.updateVOArray(
			// collection.toArray(new CustBasVO[collection.size()]),
			// new String[] { "def3" });
		}
	}

	/**
	 * 57���̵����޸����ݲ�ѯ����װ
	 * 
	 * @return
	 */
	private String getUpdateQuerySql() {
		SqlBuilder updateSqlBuilder = new SqlBuilder();
		updateSqlBuilder.append("	select mm.custcode,	");
		updateSqlBuilder.append("	       mm.bp1,	");
		updateSqlBuilder.append("	       mm.bp2,	");
		updateSqlBuilder.append("	       mm.bp3,	");
		updateSqlBuilder.append("	       mm.conaddr,	");
		updateSqlBuilder.append("	       mm.correspondunit,	");
		updateSqlBuilder.append("	       mm.createtime,	");
		updateSqlBuilder.append("	       mm.creator,	");
		updateSqlBuilder.append("	       mm.creditmny,	");
		updateSqlBuilder.append("	       mm.accdefault,	");
		updateSqlBuilder.append("	       mm.custname,	");
		updateSqlBuilder.append("	       mm.custprop,	");
		updateSqlBuilder.append("	       mm.custshortname,	");
		updateSqlBuilder.append("	       mm.def1,	");
		updateSqlBuilder.append("	       mm.def10,	");
		updateSqlBuilder.append("	       mm.def11,	");
		updateSqlBuilder.append("	       mm.def12,	");
		updateSqlBuilder.append("	       mm.def13,	");
		updateSqlBuilder.append("	       mm.def14,	");
		updateSqlBuilder.append("	       mm.def15,	");
		updateSqlBuilder.append("	       mm.def16,	");
		updateSqlBuilder.append("	       mm.def17,	");
		updateSqlBuilder.append("	       mm.def18,	");
		updateSqlBuilder.append("	       mm.def19,	");
		updateSqlBuilder.append("	       mm.def2,	");
		updateSqlBuilder.append("	       mm.def20,	");
		updateSqlBuilder.append("	       mm.def3,	");
		updateSqlBuilder.append("	       mm.def4,	");
		updateSqlBuilder.append("	       mm.def5,	");
		updateSqlBuilder.append("	       mm.def6,	");
		updateSqlBuilder.append("	       mm.def7,	");
		updateSqlBuilder.append("	       mm.def8,	");
		updateSqlBuilder.append("	       mm.def9,	");
		updateSqlBuilder.append("	       mm.dr,	");
		updateSqlBuilder.append("	       mm.drpnodeflag,	");
		updateSqlBuilder.append("	       mm.ecotypesincevfive,	");
		updateSqlBuilder.append("	       mm.email,	");
		updateSqlBuilder.append("	       mm.engname,	");
		updateSqlBuilder.append("	       mm.fax1,	");
		updateSqlBuilder.append("	       mm.fax2,	");
		updateSqlBuilder.append("	       mm.freecustflag,	");
		updateSqlBuilder.append("	       mm.isconnflag,	");
		updateSqlBuilder.append("	       mm.legalbody,	");
		updateSqlBuilder.append("	       mm.linkman1,	");
		updateSqlBuilder.append("	       mm.linkman2,	");
		updateSqlBuilder.append("	       mm.linkman3,	");
		updateSqlBuilder.append("	       mm.memo,	");
		updateSqlBuilder.append("	       mm.mnecode,	");
		updateSqlBuilder.append("	       mm.mobilephone1,	");
		updateSqlBuilder.append("	       mm.mobilephone2,	");
		updateSqlBuilder.append("	       mm.mobilephone3,	");
		updateSqlBuilder.append("	       mm.modifier,	");
		updateSqlBuilder.append("	       mm.modifytime,	");
		updateSqlBuilder.append("	       mm.phone1,	");
		updateSqlBuilder.append("	       mm.phone2,	");
		updateSqlBuilder.append("	       mm.phone3,	");
		updateSqlBuilder
				.append("	       (select areaclcode from  shyy.bd_areacl where pk_areacl = mm.pk_areacl) as pk_areacl,	");
		updateSqlBuilder.append("	       mm.pk_corp,	");
		updateSqlBuilder
				.append("	       (select unitcode from shyy.bd_corp where pk_corp = mm.pk_corp) as pk_corp1,	");
		updateSqlBuilder.append("	       mm.pk_cubasdoc,	");
		updateSqlBuilder.append("	       mm.pk_cubasdoc1,	");
		updateSqlBuilder.append("	       mm.pk_pricegroup,	");
		updateSqlBuilder.append("	       mm.registerfund,	");
		updateSqlBuilder.append("	       mm.saleaddr,	");
		updateSqlBuilder.append("	       mm.sealflag,	");
		updateSqlBuilder.append("	       mm.taxpayerid,	");
		updateSqlBuilder.append("	       mm.trade,	");
		updateSqlBuilder.append("	       mm.ts,	");
		updateSqlBuilder.append("	       mm.url,	");
		updateSqlBuilder.append("	       mm.zipcode	");
		updateSqlBuilder.append("	  from shyy.bd_cubasdoc mm	");
		updateSqlBuilder
				.append("	 where mm.pk_cubasdoc in (select t.pk_cubasdoc	");
		updateSqlBuilder
				.append("	                            from shyy.bd_cubasdoc t	");
		updateSqlBuilder
				.append("	                           inner join shyy.bd_cumandoc t1	");
		updateSqlBuilder
				.append("	                              on t.pk_cubasdoc = t1.pk_cubasdoc	");
		updateSqlBuilder
				.append("	                           inner join shyy.bd_corp t2	");
		updateSqlBuilder
				.append("	                              on t1.pk_corp = t2.pk_corp	");
		updateSqlBuilder
				.append("	                           where nvl(t.dr, 0) = 0	");
		updateSqlBuilder
				.append("	                             and t.ts>t.def3	");
		updateSqlBuilder
				.append("	                             and nvl(t1.dr, 0) = 0	");
		updateSqlBuilder
				.append("	                             and t1.frozenflag != 'Y'	");
		updateSqlBuilder
				.append("	                             and nvl(t2.dr, 0) = 0	");
		updateSqlBuilder
				.append("	                             and t2.unitcode like '0104%'	");
		updateSqlBuilder
				.append("	                           group by t.pk_cubasdoc)	");
		String updatesql = updateSqlBuilder.toString();
		return updatesql;
	}

	/**
	 * 57���̵����������ݲ�ѯ����װ<br>
	 * ����:57�������Ϊ0104��ͷ��ҵ��Ԫ
	 * 
	 * @return
	 */
	private String getInserQuerySql() {
		SqlBuilder insertSqlBuilder = new SqlBuilder();
		insertSqlBuilder.append("	select mm.custcode,	");
		insertSqlBuilder.append("	       mm.bp1,	");
		insertSqlBuilder.append("	       mm.bp2,	");
		insertSqlBuilder.append("	       mm.bp3,	");
		insertSqlBuilder.append("	       mm.conaddr,	");
		insertSqlBuilder.append("	       mm.correspondunit,	");
		insertSqlBuilder.append("	       mm.createtime,	");
		insertSqlBuilder.append("	       mm.creator,	");
		insertSqlBuilder.append("	       mm.creditmny,	");
		insertSqlBuilder.append("	       mm.accdefault,	");
		insertSqlBuilder.append("	       mm.custname,	");
		insertSqlBuilder.append("	       mm.custprop,	");
		insertSqlBuilder.append("	       mm.custshortname,	");
		insertSqlBuilder.append("	       mm.def1,	");
		insertSqlBuilder.append("	       mm.def10,	");
		insertSqlBuilder.append("	       mm.def11,	");
		insertSqlBuilder.append("	       mm.def12,	");
		insertSqlBuilder.append("	       mm.def13,	");
		insertSqlBuilder.append("	       mm.def14,	");
		insertSqlBuilder.append("	       mm.def15,	");
		insertSqlBuilder.append("	       mm.def16,	");
		insertSqlBuilder.append("	       mm.def17,	");
		insertSqlBuilder.append("	       mm.def18,	");
		insertSqlBuilder.append("	       mm.def19,	");
		insertSqlBuilder.append("	       mm.def2,	");
		insertSqlBuilder.append("	       mm.def20,	");
		insertSqlBuilder.append("	       mm.def3,	");
		insertSqlBuilder.append("	       mm.def4,	");
		insertSqlBuilder.append("	       mm.def5,	");
		insertSqlBuilder.append("	       mm.def6,	");
		insertSqlBuilder.append("	       mm.def7,	");
		insertSqlBuilder.append("	       mm.def8,	");
		insertSqlBuilder.append("	       mm.def9,	");
		insertSqlBuilder.append("	       mm.dr,	");
		insertSqlBuilder.append("	       mm.drpnodeflag,	");
		insertSqlBuilder.append("	       mm.ecotypesincevfive,	");
		insertSqlBuilder.append("	       mm.email,	");
		insertSqlBuilder.append("	       mm.engname,	");
		insertSqlBuilder.append("	       mm.fax1,	");
		insertSqlBuilder.append("	       mm.fax2,	");
		insertSqlBuilder.append("	       mm.freecustflag,	");
		insertSqlBuilder.append("	       mm.isconnflag,	");
		insertSqlBuilder.append("	       mm.legalbody,	");
		insertSqlBuilder.append("	       mm.linkman1,	");
		insertSqlBuilder.append("	       mm.linkman2,	");
		insertSqlBuilder.append("	       mm.linkman3,	");
		insertSqlBuilder.append("	       mm.memo,	");
		insertSqlBuilder.append("	       mm.mnecode,	");
		insertSqlBuilder.append("	       mm.mobilephone1,	");
		insertSqlBuilder.append("	       mm.mobilephone2,	");
		insertSqlBuilder.append("	       mm.mobilephone3,	");
		insertSqlBuilder.append("	       mm.modifier,	");
		insertSqlBuilder.append("	       mm.modifytime,	");
		insertSqlBuilder.append("	       mm.phone1,	");
		insertSqlBuilder.append("	       mm.phone2,	");
		insertSqlBuilder.append("	       mm.phone3,	");
		insertSqlBuilder
				.append("	       (select areaclcode from shyy.bd_areacl where pk_areacl = mm.pk_areacl) as pk_areacl,	");
		insertSqlBuilder.append("	       mm.pk_corp,	");
		insertSqlBuilder
				.append("	       (select unitcode from shyy.bd_corp where pk_corp = mm.pk_corp) as pk_corp1,	");
		insertSqlBuilder.append("	       mm.pk_cubasdoc,	");
		insertSqlBuilder.append("	       mm.pk_cubasdoc1,	");
		insertSqlBuilder.append("	       mm.pk_pricegroup,	");
		insertSqlBuilder.append("	       mm.registerfund,	");
		insertSqlBuilder.append("	       mm.saleaddr,	");
		insertSqlBuilder.append("	       mm.sealflag,	");
		insertSqlBuilder.append("	       mm.taxpayerid,	");
		insertSqlBuilder.append("	       mm.trade,	");
		insertSqlBuilder.append("	       mm.ts,	");
		insertSqlBuilder.append("	       mm.url,	");
		insertSqlBuilder.append("	       mm.zipcode	");
		insertSqlBuilder.append("	  from shyy.bd_cubasdoc mm	");
		insertSqlBuilder
				.append("	 where mm.pk_cubasdoc in (select t.pk_cubasdoc	");
		insertSqlBuilder
				.append("	                            from shyy.bd_cubasdoc t	");
		insertSqlBuilder
				.append("	                           inner join shyy.bd_cumandoc t1	");
		insertSqlBuilder
				.append("	                              on t.pk_cubasdoc = t1.pk_cubasdoc	");
		insertSqlBuilder
				.append("	                           inner join shyy.bd_corp t2	");
		insertSqlBuilder
				.append("	                              on t1.pk_corp = t2.pk_corp	");
		insertSqlBuilder
				.append("	                           where nvl(t.dr, 0) = 0	");
		insertSqlBuilder
				.append("	                             and t.def3 is null	");
		insertSqlBuilder
				.append("	                             and nvl(t1.dr, 0) = 0	");
		insertSqlBuilder
				.append("	                             and t1.frozenflag != 'Y'	");
		insertSqlBuilder
				.append("	                             and nvl(t2.dr, 0) = 0	");
		insertSqlBuilder.append("	     and t2.unitcode like '0104%'		");
		// insertSqlBuilder
		// .append("	                             and t.custcode = 'test20170516'	");
		insertSqlBuilder
				.append("	                           group by t.pk_cubasdoc)	");
		// sealflag - �������δ����
		String insertSql = insertSqlBuilder.toString();
		return insertSql;
	}

	/**
	 * 57���̵���->65�ͻ�����,��װ�����Ŀͻ�����<br>
	 * 
	 * @param custBasVO
	 * @return
	 */
	private CustomerVO custBasVO2CustomerVOAdd(CustBasVO custBasVO)
			throws BusinessException {
		CustomerVO customerVO = new CustomerVO();
		customerVO.setCode(custBasVO.getCustcode());
		customerVO.setName(custBasVO.getCustname());
		customerVO.setShortname(custBasVO.getCustshortname());
		// �ͻ�����
		customerVO.setCustprop(custBasVO.getCustprop() == null ? 0 : custBasVO
				.getCustprop());
		customerVO.setEcotypesincevfive(custBasVO.getEcotypesincevfive());
		customerVO.setEmail(custBasVO.getEmail());
		customerVO.setFax1(custBasVO.getFax1());
		customerVO.setFax2(custBasVO.getFax2());
		customerVO.setIsfreecust(custBasVO.getIsconnflag());
		customerVO.setLegalbody(custBasVO.getLegalbody());
		customerVO.setCuststate(1);// ��׼
		// ��˰�˵ǼǺ�
		customerVO.setTaxpayerid(custBasVO.getTaxpayerid());
		customerVO.setTrade(custBasVO.getTrade());
		customerVO.setMemo(custBasVO.getMemo());
		customerVO.setCreationtime(nc.vo.pubapp.AppContext.getInstance()
				.getServerTime());
		customerVO.setModifiedtime(nc.vo.pubapp.AppContext.getInstance()
				.getServerTime());
		customerVO.setModifier(nc.vo.pubapp.AppContext.getInstance()
				.getPkUser());
		customerVO
				.setCreator(nc.vo.pubapp.AppContext.getInstance().getPkUser());
		customerVO.setCreationtime(nc.vo.pubapp.AppContext.getInstance()
				.getServerTime());
		// ����״̬-Ĭ������(2)-δ����(1)
		customerVO.setEnablestate(1);
		// ��֯
		String pk_corp = AppContext.getInstance().getPkGroup();
		customerVO.setPk_org(pk_corp);
		// ����
		customerVO.setPk_group(nc.vo.pubapp.AppContext.getInstance()
				.getPkGroup());
		// ����/���� Ĭ���й�������CN
		String pk_countryzone = this.getCountryZoneDoc().get("CN");
		customerVO.setPk_country(pk_countryzone);
		// ʱ��-G8ʱ��
		String newPk_timezone = this.getTimezoneDoc().get("P0800");
		customerVO.setPk_timezone(newPk_timezone);
		// ���ݸ�ʽ-��������
		String pk_fomat = this.getFormatDoc().get("ZH-CN");
		customerVO.setPk_format(pk_fomat);
		// �ͻ���������-(57��������)
		String pk_custclass = getCustClassDoc().get(custBasVO.getPk_areacl());
		customerVO.setPk_custclass(pk_custclass);
		// ��������
		customerVO
				.setPk_areacl(getAreaClassDoc().get(custBasVO.getPk_areacl()));
		// �����Զ�����11����57���̵���������ά��57���̵����޸�֮������ݹ�ϵ
		customerVO.setDef11(custBasVO.getPk_cubasdoc());
		// ������Ӧ����Ϣ
		customerVO.setIssupplier(UFBoolean.TRUE);
		customerVO.setPk_supplier(customerVO.getPrimaryKey());
		return customerVO;
	}

	/**
	 * 57���̵���->65�ͻ�����,ƥ���޸ĵĿͻ�����<br>
	 * 
	 * @param custBasVO
	 * @return
	 */
	private CustomerVO custBasVO2CustomerVOUpdate(CustBasVO custBasVO,
			CustomerVO historyCustomerVO) throws BusinessException {
		historyCustomerVO.setCode(custBasVO.getCustcode());
		historyCustomerVO.setName(custBasVO.getCustname());
		historyCustomerVO.setShortname(custBasVO.getCustshortname());
		historyCustomerVO.setCustprop(custBasVO.getCustprop());
		historyCustomerVO
				.setEcotypesincevfive(custBasVO.getEcotypesincevfive());
		historyCustomerVO.setEmail(custBasVO.getEmail());
		historyCustomerVO.setFax1(custBasVO.getFax1());
		historyCustomerVO.setFax2(custBasVO.getFax2());
		historyCustomerVO.setIsfreecust(custBasVO.getIsconnflag());
		historyCustomerVO.setLegalbody(custBasVO.getLegalbody());
		// ��˰�˵ǼǺ�
		historyCustomerVO.setTaxpayerid(custBasVO.getTaxpayerid());
		historyCustomerVO.setTrade(custBasVO.getTrade());
		historyCustomerVO.setMemo(custBasVO.getMemo());
		historyCustomerVO.setCreationtime(nc.vo.pubapp.AppContext.getInstance()
				.getServerTime());
		historyCustomerVO.setModifiedtime(nc.vo.pubapp.AppContext.getInstance()
				.getServerTime());
		historyCustomerVO.setModifier(nc.vo.pubapp.AppContext.getInstance()
				.getPkUser());
		historyCustomerVO.setCreator(nc.vo.pubapp.AppContext.getInstance()
				.getPkUser());
		historyCustomerVO.setCreationtime(nc.vo.pubapp.AppContext.getInstance()
				.getServerTime());
		// ����
		historyCustomerVO.setPk_group(nc.vo.pubapp.AppContext.getInstance()
				.getPkGroup());
		historyCustomerVO.setPk_org(nc.vo.pubapp.AppContext.getInstance()
				.getPkGroup());
		// ����/���� Ĭ���й�������CN
		String pk_countryzone = this.getCountryZoneDoc().get("CN");
		historyCustomerVO.setPk_country(pk_countryzone);
		// ʱ��-G8ʱ��
		String newPk_timezone = this.getTimezoneDoc().get("P0800");
		historyCustomerVO.setPk_timezone(newPk_timezone);
		// ���ݸ�ʽ-��������
		String pk_fomat = this.getFormatDoc().get("ZH-CN");
		historyCustomerVO.setPk_format(pk_fomat);
		// �ͻ���������-(57��������)
		String pk_custclass = getCustClassDoc().get(custBasVO.getPk_areacl());
		historyCustomerVO.setPk_custclass(pk_custclass);
		return historyCustomerVO;
	}

	/**
	 * 57���̵���->65�ͻ�����<br>
	 * ƥ���޸ĵĹ�Ӧ�̵���
	 * 
	 * @param custBasVO
	 * @return
	 */
	private SupplierVO custBasVO2SupplierVOUpdate(CustomerVO customerVO,
			SupplierVO supplierVO, String areaCode) throws BusinessException {
		supplierVO.setName(customerVO.getName());
		supplierVO.setPk_group(customerVO.getPk_group());
		supplierVO.setPk_org(customerVO.getPk_org());
		supplierVO.setCreationtime(customerVO.getModifiedtime());
		supplierVO.setCreator(customerVO.getModifier());
		supplierVO.setName2(customerVO.getName2());
		supplierVO.setName3(customerVO.getName3());
		supplierVO.setName4(customerVO.getName4());
		supplierVO.setName5(customerVO.getName5());
		supplierVO.setName6(customerVO.getName6());
		supplierVO.setShortname(customerVO.getShortname());
		String pk_supplierclass = getSupplierclassDoc().get(areaCode);
		supplierVO.setPk_supplierclass(pk_supplierclass);
		supplierVO.setIscustomer(UFBoolean.TRUE);
		supplierVO.setCode(customerVO.getCode());
		supplierVO.setDataoriginflag(0);
		supplierVO.setCorcustomer(customerVO.getPk_customer());
		supplierVO.setPk_country(customerVO.getPk_country());
		supplierVO.setPk_format(customerVO.getPk_format());
		supplierVO.setPk_timezone(customerVO.getPk_timezone());
		supplierVO.setPk_areacl(areaCode);
		supplierVO.setMnecode(customerVO.getMnecode());
		supplierVO.setTaxpayerid(customerVO.getTaxpayerid());
		supplierVO.setTel1(customerVO.getTel1());
		supplierVO.setTel2(customerVO.getTel2());
		supplierVO.setTel3(customerVO.getTel3());
		supplierVO.setDef1(customerVO.getDef1());
		supplierVO.setDef2(customerVO.getDef2());
		supplierVO.setDef3(customerVO.getDef3());
		supplierVO.setDef4(customerVO.getDef4());
		supplierVO.setDef5(customerVO.getDef5());
		supplierVO.setDef6(customerVO.getDef6());
		supplierVO.setDef7(customerVO.getDef7());
		supplierVO.setDef8(customerVO.getDef8());
		supplierVO.setDef9(customerVO.getDef9());
		supplierVO.setDef10(customerVO.getDef10());
		supplierVO.setDef11(customerVO.getDef11());
		supplierVO.setDef12(customerVO.getDef12());
		supplierVO.setDef13(customerVO.getDef13());
		supplierVO.setDef14(customerVO.getDef14());
		supplierVO.setDef15(customerVO.getDef15());
		supplierVO.setDef16(customerVO.getDef16());
		supplierVO.setDef17(customerVO.getDef17());
		supplierVO.setDef18(customerVO.getDef18());
		supplierVO.setDef19(customerVO.getDef19());
		supplierVO.setDef20(customerVO.getDef20());
		supplierVO.setDef21(customerVO.getDef21());
		supplierVO.setDef22(customerVO.getDef22());
		supplierVO.setDef23(customerVO.getDef23());
		supplierVO.setDef24(customerVO.getDef24());
		supplierVO.setDef25(customerVO.getDef25());
		supplierVO.setDef26(customerVO.getDef26());
		supplierVO.setDef27(customerVO.getDef27());
		supplierVO.setDef28(customerVO.getDef28());
		supplierVO.setDef29(customerVO.getDef29());
		supplierVO.setDef30(customerVO.getDef30());
		return supplierVO;
	}

	/**
	 * ���ҵ�����������
	 * 
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, String> getCountryZoneDoc() throws BusinessException {
		String queryCount = "select count(*) from bd_countryzone t where nvl(t.dr,0)=0";
		Integer count = Integer.parseInt((this.dao.executeQuery(queryCount,
				new ColumnProcessor())).toString());
		if (countryZoneDoc == null || countryZoneDoc.keySet().size() == 0
				|| countryZoneDoc.keySet().size() != count) {
			countryZoneDoc = new HashMap<String, String>();
			String sql = "select * from bd_countryzone t where nvl(t.dr,0)=0";
			@SuppressWarnings("unchecked")
			List<CountryZoneVO> countryZoneVOList = (List<CountryZoneVO>) this.dao
					.executeQuery(sql, new BeanListProcessor(
							CountryZoneVO.class));
			if (countryZoneVOList != null && countryZoneVOList.size() > 0) {
				for (CountryZoneVO countryZoneVO : countryZoneVOList) {
					countryZoneDoc.put(countryZoneVO.getCode(),
							countryZoneVO.getPk_country());
				}
			}
		}
		return countryZoneDoc;
	}

	/**
	 * ʱ����������
	 * 
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, String> getTimezoneDoc() throws BusinessException {
		String queryCount = "select count(*) from bd_timezone t where nvl(t.dr,0)=0";
		Integer count = Integer.parseInt((this.dao.executeQuery(queryCount,
				new ColumnProcessor())).toString());
		if (timeZoneDoc == null || timeZoneDoc.keySet().size() == 0
				|| timeZoneDoc.keySet().size() != count) {
			timeZoneDoc = new HashMap<String, String>();
			String sql = "select * from bd_timezone t where nvl(t.dr,0)=0";
			@SuppressWarnings("unchecked")
			List<TimezoneVO> timeZoneVOList = (List<TimezoneVO>) this.dao
					.executeQuery(sql, new BeanListProcessor(TimezoneVO.class));
			if (timeZoneVOList != null && timeZoneVOList.size() > 0) {
				for (TimezoneVO timezoneVO : timeZoneVOList) {
					timeZoneDoc.put(timezoneVO.getCode(),
							timezoneVO.getPk_timezone());
				}
			}
		}
		return timeZoneDoc;
	}

	/**
	 * ���ݸ�ʽ��������
	 * 
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, String> getFormatDoc() throws BusinessException {
		String queryCount = "select count(*) from bd_formatdoc t where nvl(t.dr,0)=0";
		Integer count = Integer.parseInt((this.dao.executeQuery(queryCount,
				new ColumnProcessor())).toString());
		if (formatDoc == null || formatDoc.keySet().size() == 0
				|| formatDoc.keySet().size() != count) {
			formatDoc = new HashMap<String, String>();
			String sql = "select * from bd_formatdoc t where nvl(t.dr,0)=0";
			@SuppressWarnings("unchecked")
			List<FormatDocVO> formatDocVOList = (List<FormatDocVO>) this.dao
					.executeQuery(sql, new BeanListProcessor(FormatDocVO.class));
			if (formatDocVOList != null && formatDocVOList.size() > 0) {
				for (FormatDocVO formatDocVO : formatDocVOList) {
					formatDoc.put(formatDocVO.getCode(),
							formatDocVO.getPk_formatdoc());
				}
			}
		}
		return formatDoc;
	}

	/**
	 * �ͻ���������
	 * 
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, String> getCustClassDoc() throws BusinessException {
		String queryCount = "select count(*) from bd_custclass t where nvl(t.dr,0)=0";
		Integer count = Integer.parseInt((this.dao.executeQuery(queryCount,
				new ColumnProcessor())).toString());
		if (custClassDoc == null || custClassDoc.keySet().size() == 0
				|| custClassDoc.keySet().size() != count) {
			custClassDoc = new HashMap<String, String>();
			String sql = "select * from bd_custclass t where nvl(t.dr,0)=0";
			@SuppressWarnings("unchecked")
			List<CustClassVO> custClassVOList = (List<CustClassVO>) this.dao
					.executeQuery(sql, new BeanListProcessor(CustClassVO.class));
			if (custClassVOList != null && custClassVOList.size() > 0) {
				for (CustClassVO custClassVO : custClassVOList) {
					custClassDoc.put(custClassVO.getCode(),
							custClassVO.getPk_custclass());
				}
			}
		}
		return custClassDoc;
	}

	/**
	 * ��Ӧ�̻�������
	 * 
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, String> getSupplierclassDoc() throws BusinessException {
		String queryCount = "select count(*) from bd_supplierclass t where nvl(t.dr,0)=0";
		Integer count = Integer.parseInt((this.dao.executeQuery(queryCount,
				new ColumnProcessor())).toString());
		if (supplierclassDoc == null || supplierclassDoc.keySet().size() == 0
				|| supplierclassDoc.keySet().size() != count) {
			supplierclassDoc = new HashMap<String, String>();
			String sql = "select * from bd_supplierclass t where nvl(t.dr,0)=0";
			@SuppressWarnings("unchecked")
			List<SupplierclassVO> supplierclassVOList = (List<SupplierclassVO>) this.dao
					.executeQuery(sql, new BeanListProcessor(
							SupplierclassVO.class));
			if (supplierclassVOList != null && supplierclassVOList.size() > 0) {
				for (SupplierclassVO sups : supplierclassVOList) {
					supplierclassDoc.put(sups.getCode(),
							sups.getPk_supplierclass());
				}
			}
		}
		return supplierclassDoc;
	}

	/** �������� */
	private Map<String, String> getAreaClassDoc() throws BusinessException {
		String queryCount = "select count(*) from bd_areacl t where nvl(t.dr,0)=0 and t.enablestate = '2'";
		Integer count = Integer.parseInt((this.dao.executeQuery(queryCount,
				new ColumnProcessor())).toString());
		if (areaClassDoc == null || areaClassDoc.keySet().size() == 0
				|| areaClassDoc.keySet().size() != count) {
			areaClassDoc = new HashMap<String, String>();
			String sql = "select * from bd_areacl t where nvl(t.dr,0)=0 and t.enablestate = '2' ";
			@SuppressWarnings("unchecked")
			List<AreaclassVO> areaClassVOList = (List<AreaclassVO>) this.dao
					.executeQuery(sql, new BeanListProcessor(AreaclassVO.class));
			if (areaClassVOList != null && areaClassVOList.size() > 0) {
				for (AreaclassVO areaclassVO : areaClassVOList) {
					areaClassDoc.put(areaclassVO.getCode(),
							areaclassVO.getPk_areacl());
				}
			}
		}
		return areaClassDoc;
	}

	/**
	 * ��������-��������־û�����
	 */
	@Override
	public void syncCustProcessInsert_RequiresNew(
			ArrayList<CustSupplierVO> custSupplierVOInsertList,
			ArrayList<CustomerVO> customerInsertList,
			HashMap<String, CustBasVO> code2custbasVOInsert)
			throws BusinessException {
		if (customerInsertList == null || customerInsertList.size() == 0) {
			return;
		}
		for (CustomerVO customerVO : customerInsertList) {
			// ��Ӧ��Ӧ��
			customerVO.setPk_supplier(customerVO.getPk_customer());
		}
		// ����ͻ�����������˳������
		this.dao.insertVOArrayWithPK(customerInsertList
				.toArray(new CustomerVO[customerInsertList.size()]));
		HashMap<String, String> cutomerCode2cutomerPK = new HashMap<String, String>();
		// �ͻ�����������Ӧ�̷���
		HashMap<String, String> coustomerPk2supplierClass = new HashMap<String, String>();
		for (CustomerVO customer : customerInsertList) {
			String pk_customer = customer.getPk_customer();
			CustBasVO custBasVO = code2custbasVOInsert.get(customer.getCode());
			if (custBasVO == null) {
				continue;
			}
			String pk_supplierClass = getSupplierclassDoc().get(
					custBasVO.getPk_areacl());
			coustomerPk2supplierClass.put(pk_customer, pk_supplierClass);
			cutomerCode2cutomerPK.put(customer.getCode(), pk_customer);
		}

		// ����ϵͳ��׼�ӿ�,���ݹ�Ӧ�̷���,�������ɹ�Ӧ��
		SupplierVO[] supplierVOs = this.getInserSupplier(
				coustomerPk2supplierClass, customerInsertList
						.toArray(new CustomerVO[customerInsertList.size()]));
		HashMap<String, SupplierVO> supplierCode2supplierVO = new HashMap<String, SupplierVO>();
		for (SupplierVO vo : supplierVOs) {
			String pk_supplier = cutomerCode2cutomerPK.get(vo.getCode());
			// ��������
			vo.setPk_oldsupplier(pk_supplier);
			vo.setPk_supplier(pk_supplier);
			vo.setIscustomer(UFBoolean.TRUE);
			// ��׼�ͻ�
			vo.setSupstate(1);
			vo.setPk_org(AppContext.getInstance().getPkGroup());
			// δ����
			vo.setEnablestate(1);
			supplierCode2supplierVO.put(vo.getCode(), vo);
		}
		// ���湩Ӧ�̵���
		this.dao.insertVOArrayWithPK(supplierVOs);
		// �������̹�ϵ
		for (CustSupplierVO custSupplierVO : custSupplierVOInsertList) {
			custSupplierVO.setPk_supplierclass(supplierCode2supplierVO.get(
					custSupplierVO.getCode()).getPk_supplierclass());
		}
		// ������̵���
		this.dao.insertVOArrayWithPK(custSupplierVOInsertList
				.toArray(new CustSupplierVO[custSupplierVOInsertList.size()]));
		// ��д57����
		this.writeBack57CubasdocUpdateTs(code2custbasVOInsert.values());
	}

	/**
	 * �޸�����-��������־û�����
	 */
	@Override
	public void syncCustProcessUpdate_RequiresNew(
			ArrayList<CustomerVO> customerUpdateList,
			ArrayList<SupplierVO> supplierUpdateList,
			ArrayList<CustSupplierVO> custSupplierVOUpdate,
			List<CustBasVO> updateCustBasVOList) throws BusinessException {
		if (customerUpdateList != null && customerUpdateList.size() > 0) {
			this.dao.updateVOArray(customerUpdateList
					.toArray(new CustomerVO[customerUpdateList.size()]));
		}
		if (supplierUpdateList != null && supplierUpdateList.size() > 0) {
			this.dao.updateVOArray(supplierUpdateList
					.toArray(new SupplierVO[supplierUpdateList.size()]));
		}
		if (custSupplierVOUpdate != null && custSupplierVOUpdate.size() > 0) {
			this.dao.updateVOArray(custSupplierVOUpdate
					.toArray(new CustSupplierVO[custSupplierVOUpdate.size()]));
		}
		if (customerUpdateList == null || customerUpdateList.size() == 0) {
			return;
		}
		HashMap<String, CustBasVO> pk_custbasvo2CustBasVO = new HashMap<String, CustBasVO>();
		for (CustBasVO cus : updateCustBasVOList) {
			pk_custbasvo2CustBasVO.put(cus.getPk_cubasdoc(), cus);
		}
		ArrayList<CustBasVO> arrayList = new ArrayList<CustBasVO>();
		for (CustomerVO cus : customerUpdateList) {
			arrayList.add(pk_custbasvo2CustBasVO.get(cus.getDef11()));
		}
		if (arrayList.size() > 0) {
			this.writeBack57CubasdocUpdateTs(arrayList);
		}
	}
}
