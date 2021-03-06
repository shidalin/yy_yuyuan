package nc.bs.arap.plugin;

import nc.bs.arap.bill.ArapBillPubUtil;
import nc.bs.arap.util.ArapFlowUtil;
import nc.bs.arap.util.ArapVOUtils;
import nc.bs.arap.util.BillMoneyVUtils;
import nc.bs.arap.util.IArapBillTypeCons;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.pf.pub.PfDataCache;
import nc.bs.pfxx.ISwapContext;
import nc.itf.arap.initgatheringbill.IArapInitGatheringService;
import nc.itf.arap.initpayablebill.IArapInitPayableService;
import nc.itf.arap.initpaybill.IArapInitPaybillService;
import nc.itf.arap.initreceivable.IArapInitRecService;
import nc.itf.uap.pf.IPFConfig;
import nc.md.persist.framework.MDPersistenceService;
import nc.pubitf.accperiod.AccountCalendar;
import nc.pubitf.arap.bill.IArapBillPubService;
import nc.vo.arap.basebill.BaseAggVO;
import nc.vo.arap.basebill.BaseBillVO;
import nc.vo.arap.basebill.BaseItemVO;
import nc.vo.arap.gathering.AggGatheringBillVO;
import nc.vo.arap.pay.AggPayBillVO;
import nc.vo.arap.payable.AggPayableBillVO;
import nc.vo.arap.pub.BillEnumCollection;
import nc.vo.arap.receivable.AggReceivableBillVO;
import nc.vo.arap.utils.ArrayUtil;
import nc.vo.pfxx.auxiliary.AggxsysregisterVO;
import nc.vo.pfxx.util.PfxxPluginUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.BusinessRuntimeException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.workflownote.WorkflownoteVO;
import nc.vo.pubapp.pflow.PfUserObject;
import nc.vo.pubapp.util.NCPfServiceUtils;

import org.apache.commons.lang.StringUtils;

/**
 * <b> 在此处简要描述此类的功能 </b>
 * 
 * <p>
 * 在此处添加此类的描述信息
 * </p>
 * 
 * @author zhaoruic
 * @version Your Project V60
 */
public class ArapExpPfxxPlugin<T extends BaseAggVO> extends
		nc.bs.pfxx.plugin.AbstractPfxxPlugin {

	private PfUserObject[] userObjs;

	/**
	 * 将由XML转换过来的VO导入NC系统。业务插件实现此方法即可。<br>
	 * 请注意，业务方法的校验一定要充分
	 * 
	 * @param vo
	 *            转换后的vo数据，在NC系统中可能为ValueObject,SuperVO,AggregatedValueObject,
	 *            IExAggVO等。
	 * @param swapContext
	 *            各种交换参数，组织，接受方，发送方，帐套等等
	 * @param aggxsysvo
	 *            辅助信息vo
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	protected Object processBill(Object vo, ISwapContext swapContext,
			AggxsysregisterVO aggxsysvo) throws BusinessException {

		BaseAggVO newBill = null;

		// 1.得到转换后的VO数据,取决于向导第一步注册的VO信息
		BaseAggVO bill = (BaseAggVO) vo;
		BaseBillVO head = setHeaderDefault(bill.getHeadVO());
		if (head.getPk_billtype() == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("2006pub_0", "02006pub-0316")/*
																			 * @res
																			 * "单据的单据类型编码字段不能为空，请输入值"
																			 */);
		}
		if (!head.getPk_billtype().equals(IArapBillTypeCons.F0)
				&& !head.getPk_billtype().equals(IArapBillTypeCons.F1)
				&& !head.getPk_billtype().equals(IArapBillTypeCons.F2)
				&& !head.getPk_billtype().equals(IArapBillTypeCons.F3)) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("2006pub_0", "02006pub-0650")/*
																			 * @res
																			 * "单据的单据类型编码字段错误"
																			 */);
		}
		if (head.getPk_tradetype() == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("2006pub_0", "02006pub-0649")/*
																			 * @res
																			 * "单据的交易类型编码字段不能为空，请输入值"
																			 */);
		}
		if (head.getPk_group() == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("2006pub_0", "02006pub-0317")/*
																			 * @res
																			 * "单据的所属集团字段不能为空，请输入值"
																			 */);
		}
		if (head.getPk_org() == null) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("2006pub_0", "02006pub-0318")/*
																			 * @res
																			 * "单据的财务组织字段不能为空，请输入值"
																			 */);
		}

		// 设置业务流程
		try {
			IPFConfig ipf = NCLocator.getInstance().lookup(IPFConfig.class);
			if (!StringUtils.isEmpty(head.getPk_billtype())
					&& !StringUtils.isEmpty(head.getPk_tradetype())) {
				if (head.getCreator() == null) {
					head.setCreator(InvocationInfoProxy.getInstance()
							.getUserId());
				}
				String pk_busitype = ipf.retBusitypeCanStart(
						head.getPk_billtype(), head.getPk_tradetype(),
						head.getPk_org(), head.getCreator());
				if (pk_busitype == null) {
					throw new BusinessException("busitype is null");
				}
				head.setPk_busitype(pk_busitype);
			}
		} catch (Exception e) {
			String msg = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"2006pub_0", "02006pub-0127")/* @res "交易类型" */
					+ head.getPk_tradetype()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"2006pub_0", "02006pub-0239")/*
														 * @res
														 * "没有找到相应的流程,请在[业务流定义]配置"
														 */
					+ head.getPk_tradetype()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"2006pub_0", "02006pub-0240")/* @res "自制流程" */;
			throw new BusinessRuntimeException(msg);
		}

		// 如果是期初单据，则需要校验业务单元是否设置了业务期初期间，如果没有不允许导入。如果设置了，将日期设为期初期间前一天
		if (head.getIsinit().booleanValue()) {
			boolean isAr = ArapBillPubUtil.isARSysBilltype(head
					.getPk_billtype());
			UFDate billdate = ArapBillPubUtil.getArapCreateDate(isAr,
					head.getPk_org());
			if (null == billdate) {
				throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes()
						.getStrByID("2006pub_0", "02006pub-0007")/*
																 * @res
																 * "业务单元未设置业务期初期间，不允许维护期初数据"
																 */);
			}
			if (head.getBilldate().after(billdate)) {
				head.setBilldate(billdate);
				for (BaseItemVO item : (BaseItemVO[]) bill.getChildrenVO()) {
					item.setBilldate(billdate);
				}
			}

		}

		setBodyDefault(head, (BaseItemVO[]) bill.getChildrenVO());
		BillMoneyVUtils
				.sumBodyToHead(head, (BaseItemVO[]) bill.getChildrenVO());
		// 金额计算-edit by shidl-20180201
		new nc.bs.arap.util.MnyCalculateUtil()
				.mnyCalculate(new AggregatedValueObject[] { (AggregatedValueObject) bill });
		// 2.查询此单据是否已经被导入过
		String oldPk = PfxxPluginUtils.queryBillPKBeforeSaveOrUpdate(
				swapContext.getBilltype(), swapContext.getDocID());
		if (oldPk != null) {

			// 这个判断，好像平台已经过，如果单据已导入，且replace="N"，那么平台就会抛出异常，提示不可重复
			if (swapContext.getReplace().equalsIgnoreCase("N"))
				throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes()
						.getStrByID("2006pub_0", "02006pub-0319")/*
																 * @res
																 * "不允许重复导入单据，请检查是否是操作错误；如果想更新已导入单据，请把数据文件的replace标志设为‘Y’"
																 */);

			BaseAggVO preVO = null;
			if (head.getPk_billtype().equals(IArapBillTypeCons.F2)) {
				preVO = MDPersistenceService.lookupPersistenceQueryService()
						.queryBillOfVOByPK(AggGatheringBillVO.class, oldPk,
								false);
			} else if (head.getPk_billtype().equals(IArapBillTypeCons.F3)) {
				preVO = MDPersistenceService.lookupPersistenceQueryService()
						.queryBillOfVOByPK(AggPayBillVO.class, oldPk, false);
			} else if (head.getPk_billtype().equals(IArapBillTypeCons.F0)) {
				preVO = MDPersistenceService.lookupPersistenceQueryService()
						.queryBillOfVOByPK(AggReceivableBillVO.class, oldPk,
								false);
			} else if (head.getPk_billtype().equals(IArapBillTypeCons.F1)) {
				preVO = MDPersistenceService
						.lookupPersistenceQueryService()
						.queryBillOfVOByPK(AggPayableBillVO.class, oldPk, false);
			}

			if (preVO != null && preVO.getParentVO() != null) {

				if (((BaseBillVO) preVO.getParentVO()).getBillstatus() == null) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
							.getNCLangRes().getStrByID("2006pub_0",
									"02006pub-0320")/* @res "单据状态不存在！" */);
				}
				if (((BaseBillVO) preVO.getParentVO()).getBillstatus() == BillEnumCollection.BillSatus.Audit.VALUE) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
							.getNCLangRes().getStrByID("2006pub_0",
									"02006pub-0321")/* @res "单据已经审核，不允许重复导入单据。" */);
				}

				if (head.getIsinit().booleanValue()) {
					this.deleteInitBill(preVO);
				} else {
					NCLocator.getInstance().lookup(IArapBillPubService.class)
							.delete(preVO);
				}
			}
		}

		ArapExpPfxxValidater.getInstance().validate(bill);

		ArapVOUtils.validateVoCopyRed(bill);

		head.setBillno(null); // 清空billno，以便于重新生成，避免重复
		// 插入数据
		if (head.getIsinit().booleanValue()) {
			newBill = this.insertInitBill(bill);
		} else {
			newBill = this.insertBill(bill);
		}

		String pk = null;
		if (newBill != null) {
			pk = newBill.getParent().getPrimaryKey();
		}
		if (oldPk != null) {
			PfxxPluginUtils.deleteIDvsPKByDocPK(oldPk);
		}
		PfxxPluginUtils.addDocIDVsPKContrast(swapContext.getBilltype(),
				swapContext.getDocID(), pk);
		return pk;
	}

	private BaseAggVO insertBill(BaseAggVO bill) throws BusinessException {
		BaseAggVO res = null;
		if (bill.getHeadVO().getPk_billtype().equals(IArapBillTypeCons.F0)) {
			res = (AggReceivableBillVO) ArrayUtil
					.getFirstInArrays((Object[]) NCPfServiceUtils.processBatch(
							ArapFlowUtil.getCommitActionCode(bill.getHeadVO()
									.getPk_org(), IArapBillTypeCons.F0),
							bill.getHeadVO().getPk_billtype(),
							new AggReceivableBillVO[] { (AggReceivableBillVO) bill },
							getUserObj(), new WorkflownoteVO()));
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F1)) {
			res = (AggPayableBillVO) ArrayUtil
					.getFirstInArrays((Object[]) NCPfServiceUtils.processBatch(
							ArapFlowUtil.getCommitActionCode(bill.getHeadVO()
									.getPk_org(), IArapBillTypeCons.F1), bill
									.getHeadVO().getPk_billtype(),
							new AggPayableBillVO[] { (AggPayableBillVO) bill },
							getUserObj(), new WorkflownoteVO()));
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F2)) {
			res = (AggGatheringBillVO) ArrayUtil
					.getFirstInArrays((Object[]) NCPfServiceUtils.processBatch(
							ArapFlowUtil.getCommitActionCode(bill.getHeadVO()
									.getPk_org(), IArapBillTypeCons.F2),
							bill.getHeadVO().getPk_billtype(),
							new AggGatheringBillVO[] { (AggGatheringBillVO) bill },
							getUserObj(), new WorkflownoteVO()));
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F3)) {
			res = (AggPayBillVO) ArrayUtil
					.getFirstInArrays((Object[]) NCPfServiceUtils.processBatch(
							ArapFlowUtil.getCommitActionCode(bill.getHeadVO()
									.getPk_org(), IArapBillTypeCons.F3), bill
									.getHeadVO().getPk_billtype(),
							new AggPayBillVO[] { (AggPayBillVO) bill },
							getUserObj(), new WorkflownoteVO()));
		}

		return res;
	}

	private BaseAggVO insertInitBill(BaseAggVO bill) throws BusinessException {
		BaseAggVO res = null;
		if (bill.getHeadVO().getPk_billtype().equals(IArapBillTypeCons.F0)) {
			res = NCLocator.getInstance().lookup(IArapInitRecService.class)
					.save((AggReceivableBillVO) bill);
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F1)) {
			res = NCLocator.getInstance().lookup(IArapInitPayableService.class)
					.save((AggPayableBillVO) bill);
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F2)) {
			res = NCLocator.getInstance()
					.lookup(IArapInitGatheringService.class)
					.save((AggGatheringBillVO) bill);
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F3)) {
			res = NCLocator.getInstance().lookup(IArapInitPaybillService.class)
					.save((AggPayBillVO) bill);
		}

		return res;
	}

	public PfUserObject[] getUserObj() {
		if (userObjs == null) {
			userObjs = new PfUserObject[] { new PfUserObject() };
		}
		return userObjs;
	}

	private void deleteInitBill(BaseAggVO bill) throws BusinessException {
		if (bill.getHeadVO().getPk_billtype().equals(IArapBillTypeCons.F0)) {
			NCLocator.getInstance().lookup(IArapInitRecService.class)
					.delete((AggReceivableBillVO) bill);
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F1)) {
			NCLocator.getInstance().lookup(IArapInitPayableService.class)
					.delete((AggPayableBillVO) bill);
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F2)) {
			NCLocator.getInstance().lookup(IArapInitGatheringService.class)
					.delete((AggGatheringBillVO) bill);
		} else if (bill.getHeadVO().getPk_billtype()
				.equals(IArapBillTypeCons.F3)) {
			NCLocator.getInstance().lookup(IArapInitPaybillService.class)
					.delete((AggPayBillVO) bill);
		}
	}

	/**
	 * 设置表头默认信息
	 * 
	 * @param headerVo
	 * @return
	 * @throws BusinessException
	 */
	private BaseBillVO setHeaderDefault(BaseBillVO header)
			throws BusinessException {
		Integer ZERO = Integer.valueOf(0);
		/* 单据状态为未审核 */
		header.setBillstatus(BillEnumCollection.BillSatus.Save.VALUE);
		header.setEffectstatus(BillEnumCollection.InureSign.NOINURE.VALUE);
		header.setDr(ZERO);
		// 来源系统是外部交换平台
		header.setSrc_syscode(BillEnumCollection.FromSystem.WBJHPT.VALUE);
		header.setCreationtime(new UFDateTime());
		header.setCoordflag(null);

		// 设置会计年和会计期间。若根据日期查不到会计期间，则捕获异常，不作处理
		try {
			AccountCalendar ac = AccountCalendar.getInstanceByPk_org(header
					.getPk_org());
			ac.setDate(header.getBilldate());
			header.setBillyear(ac.getYearVO().getPeriodyear());
			header.setBillperiod(ac.getMonthVO().getAccperiodmth());
		} catch (BusinessException ex) {
		}

		// 设置交易类型pk
		header.setPk_tradetypeid(PfDataCache.getBillTypeInfo(
				header.getPk_group(), header.getPk_tradetype())
				.getPk_billtypeid());
		return header;
	}

	void setBodyDefault(BaseBillVO head, BaseItemVO[] items)
			throws BusinessException {
		int len = items == null ? 0 : items.length;

		for (int i = 0; i < len; i++) {
			setBodyDefault(head, items[i]);
		}
	}

	void setBodyDefault(BaseBillVO head, BaseItemVO item)
			throws BusinessException {
		/* 复制单位 */
		item.setPk_org(head.getPk_org());
		item.setDr(Integer.valueOf(0));
		item.setPk_tradetypeid(PfDataCache.getBillTypeInfo(head.getPk_group(),
				head.getPk_tradetype()).getPk_billtypeid());
		item.setCoordflag(null);
	}

}