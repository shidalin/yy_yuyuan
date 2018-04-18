package nc.bs.gl.voucher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.naming.NamingException;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.core.service.TimeService;
import nc.bs.framework.core.util.ObjectCreator;
import nc.bs.framework.exception.FrameworkRuntimeException;
import nc.bs.gl.busilog.GLBusiLogUtil;
import nc.bs.gl.cashflowcase.CashFlowCaseOpDMO;
import nc.bs.gl.cashflowcase.CashflowcaseDMO;
import nc.bs.gl.fc.rule.ConvertBO;
import nc.bs.gl.pubinterface.ICheckReconcile;
import nc.bs.gl.pubinterface.IVoucherAbandon;
import nc.bs.gl.pubinterface.IVoucherOffer;
import nc.bs.gl.pubinterface.IVoucherSave;
import nc.bs.gl.pubinterface.IVoucherTempSave;
import nc.bs.gl.reconcilecenter.ReconcileCheckBO;
import nc.bs.gl.verifysyn.VerifyUICheckBO;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.bs.ml.NCLangResOnserver;
import nc.bs.pub.SystemException;
import nc.gl.glconst.systemtype.SystemtypeConst;
import nc.gl.utils.BDMultiLangUtil;
import nc.gl.utils.GLMultiLangUtil;
import nc.gl.utils.GLSqlUtil;
import nc.gl.utils.GLVoTools;
import nc.impl.gl.contrast.ContrastedCheckListenerImpl;
import nc.itf.bd.country.ICountryQryService;
import nc.itf.bd.pub.IBDMetaDataIDConst;
import nc.itf.fipub.freevalueset.IFreeMap;
import nc.itf.gl.pub.GLKeyLock;
import nc.itf.gl.pub.ICashFlowCase;
import nc.itf.gl.pub.IFreevaluePub;
import nc.itf.gl.pub.ITransferHistoryPrv;
import nc.itf.gl.pubreconcile.IPubReconcile;
import nc.itf.gl.vatdetail.IVatDetailMngService;
import nc.itf.gl.vatdetail.IVatDetailQryService;
import nc.itf.gl.voucher.pfxx.IVoucherPfxxService;
import nc.itf.glcom.para.GLParaAccessor;
import nc.itf.glcom.para.IGlPara;
import nc.itf.org.IOrgConst;
import nc.pubitf.uapbd.CurrencyRateUtilHelper;
import nc.pubitf.uapbd.IAccountPubService;
import nc.ui.gl.datacache.GLParaDataCacheUseUap;
import nc.ui.pub.formulaparse.FormulaParse;
import nc.vo.bd.account.AccountVO;
import nc.vo.bd.countryzone.CountryZoneExVO;
import nc.vo.bd.countryzone.CountryZoneVO;
import nc.vo.bd.currtype.CurrtypeVO;
import nc.vo.bd.vouchertype.VoucherTypeVO;
import nc.vo.fi.pub.SqlUtils;
import nc.vo.fip.external.FipSaveResultVO;
import nc.vo.fip.service.FipRelationInfoVO;
import nc.vo.fipub.freevalue.Module;
import nc.vo.fipub.utils.StrTools;
import nc.vo.gateway60.accountbook.AccountBookUtil;
import nc.vo.gateway60.accountbook.GlOrgUtils;
import nc.vo.gateway60.itfs.AccountUtilGL;
import nc.vo.gateway60.itfs.Balatype;
import nc.vo.gateway60.itfs.Currency;
import nc.vo.gateway60.itfs.CurrtypeGL;
import nc.vo.gateway60.itfs.VoucherTypeGL;
import nc.vo.gateway60.pub.GlBusinessException;
import nc.vo.gateway60.pub.VoucherTypeDataCache;
import nc.vo.gl.aggvoucher.MDVoucher;
import nc.vo.gl.cashflowcase.CashflowcaseVO;
import nc.vo.gl.exception.VoucherNoDuplicateException;
import nc.vo.gl.fc.rule.ConvertConsatnt;
import nc.vo.gl.fc.rule.ConvertlogVO;
import nc.vo.gl.pubinterface.CheckReconVO;
import nc.vo.gl.pubinterface.VoucherOperateInterfaceVO;
import nc.vo.gl.pubinterface.VoucherSaveInterfaceVO;
import nc.vo.gl.pubreconcile.PubReconcileInfoVO;
import nc.vo.gl.pubvoucher.DetailVO;
import nc.vo.gl.pubvoucher.GLParameterVO;
import nc.vo.gl.pubvoucher.OperationResultVO;
import nc.vo.gl.pubvoucher.VoucherKey;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.gl.sysparam.SystemParamConfig;
import nc.vo.gl.vatdetail.BusinessCodeEnum;
import nc.vo.gl.vatdetail.DirectionEnum;
import nc.vo.gl.vatdetail.VatDetailVO;
import nc.vo.gl.vatdetail.VoucherkindEnum;
import nc.vo.gl.voucher.DealclassVO;
import nc.vo.gl.voucher.DtlfreevalueVO;
import nc.vo.gl.voucher.SmallCorpVO;
import nc.vo.gl.voucher.SubjfreevalueVO;
import nc.vo.gl.voucher.VchfreevalueVO;
import nc.vo.gl.voucher.VoucherCheckConfigVO;
import nc.vo.gl.voucher.VoucherCheckMessage;
import nc.vo.gl.voucher.VoucherUserVO;
import nc.vo.glcom.constant.GLBusiLogOperateConst;
import nc.vo.glcom.constant.GLMetaDataIDConst;
import nc.vo.glcom.constant.GLVoucherKindConst;
import nc.vo.glcom.para.GlDebugFlag;
import nc.vo.glcom.tools.GLPubProxy;
import nc.vo.glcom.wizard.VoWizard;
import nc.vo.glcom.wizard.VoWizard60;
import nc.vo.org.AccountingBookVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.formulaset.FormulaParseFather;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.pf.Pfi18nTools;

import org.apache.commons.lang.StringUtils;

/**
 * Voucher��BO��
 * 
 * �������ڣ�(2001-9-19)
 * 
 * @author��
 */
public class VoucherBO {
	/**
	 * VoucherBO ������ע�⡣
	 */
	public VoucherBO() {
		super();
	}

	private static final String CHECK_RECON_TEMP_DETAIL = GLSqlUtil.tempTableName
			+ "_detail";

	// private static final Log log = Log.getInstance(VoucherBO.class);

	/**
	 * ����
	 * 
	 * @param strVoucherPK
	 * @param strOperator
	 * @param op
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	private OperationResultVO[] abandonByPk(String strVoucherPK,
			String strOperator, Boolean op) throws BusinessException, Exception {
		if (strVoucherPK == null) {
			return null;
		} else {
			OperationResultVO[] result = null;
			VoucherVO vo = null;
			if (op.booleanValue()) {
				VoucherExtendDMO time = new VoucherExtendDMO();
				vo = time.queryByVoucherPk(strVoucherPK);
			} else {
				vo = this.queryByPk(strVoucherPK);
			}

			if (vo == null) {
				throw new BusinessException(NCLangResOnserver.getInstance()
						.getStrByID("20021005", "UPP20021005-000538"));
			} else {
				result = this.checkCanAbandon(vo, strOperator, op);
				long arg25 = System.currentTimeMillis();
				boolean bLockSuccess = false;
				GLKeyLock lock = null;

				try {
					lock = new GLKeyLock();

					for (int e = 0; e < 5; ++e) {
						bLockSuccess = GLKeyLock.lockKey(strVoucherPK,
								strOperator, "gl_voucher");
						if (bLockSuccess) {
							break;
						}

						try {
							Thread.sleep(1000L);
						} catch (Exception arg22) {
							;
						}
					}

					if (!bLockSuccess) {
						throw new BusinessException(NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000539"));
					}

					DealclassDMO arg26 = new DealclassDMO();
					DealclassVO[] dealclassvos = arg26
							.queryByModulgroup("abandon");
					Vector vecabandonclass = new Vector();
					int m;
					if (dealclassvos != null && dealclassvos.length != 0) {
						for (m = 0; m < dealclassvos.length; ++m) {
							if (dealclassvos[m].getModules() != null) {
								try {
									IVoucherAbandon opvo = (IVoucherAbandon) ObjectCreator
											.newInstance(dealclassvos[m]
													.getModules(),
													dealclassvos[m]
															.getClassname());
									vecabandonclass.addElement(opvo);
								} catch (FrameworkRuntimeException arg21) {
									Logger.error(arg21.getMessage(), arg21);
									throw new BusinessException(
											arg21.getMessage(), arg21);
								}
							}
						}
					}

					OperationResultVO[] t_result;
					VoucherOperateInterfaceVO arg27;
					for (m = 0; m < vecabandonclass.size(); ++m) {
						arg27 = new VoucherOperateInterfaceVO();
						arg27.pk_vouchers = new String[] { strVoucherPK };
						arg27.pk_user = strOperator;
						arg27.operatedirection = op;
						t_result = ((IVoucherAbandon) vecabandonclass
								.elementAt(m)).beforeAbandon(arg27);
						if (t_result != null) {
							for (int i = 0; i < t_result.length; ++i) {
								if (t_result[i] != null
										&& t_result[i].m_intSuccess == 2) {
									throw new BusinessException(
											t_result[i].m_strDescription);
								}
							}
						}

						result = OperationResultVO.appendResultVO(result,
								t_result);
					}

					if (op.booleanValue()) {
						m = (new VoucherExtendDMO()).updateAbandon(
								new String[] { strVoucherPK }, strOperator, op);
						if (m != 1) {
							throw new BusinessException(NCLangResOnserver
									.getInstance().getStrByID("20021005",
											"UPP20021005-000540"));
						}
					} else {
						m = (new VoucherExtendDMO()).updateAbandon(
								new String[] { strVoucherPK }, strOperator, op);
						if (m != 1) {
							throw new BusinessException(NCLangResOnserver
									.getInstance().getStrByID("20021005",
											"UPP20021005-000540"));
						}
					}

					for (m = vecabandonclass.size(); m > 0; --m) {
						arg27 = new VoucherOperateInterfaceVO();
						arg27.pk_vouchers = new String[] { strVoucherPK };
						arg27.pk_user = strOperator;
						arg27.operatedirection = op;
						t_result = ((IVoucherAbandon) vecabandonclass
								.elementAt(m - 1)).afterAbandon(arg27);
						result = OperationResultVO.appendResultVO(result,
								t_result);
					}
				} catch (BusinessException arg23) {
					Logger.error(arg23.getMessage(), arg23);
					throw new BusinessException(arg23.getMessage(), arg23);
				} finally {
					if (bLockSuccess) {
						GLKeyLock.freeKey(strVoucherPK, strOperator,
								"gl_voucher");
					}

				}

				return result;
			}
		}
	}

	/**
	 * ��������
	 * 
	 * @param strVoucherPKs
	 * @param strOperator
	 * @param op
	 * @return
	 * @throws BusinessException
	 */
	public OperationResultVO[] abandonByPks(String[] strVoucherPKs,
			String strOperator, Boolean op) throws BusinessException {
		if (strVoucherPKs == null || strVoucherPKs.length == 0)
			return null;
		try {
			OperationResultVO[] r = null;
			for (int i = 0; i < strVoucherPKs.length; i++) {
				r = OperationResultVO.appendResultVO(r,
						abandonByPk(strVoucherPKs[i], strOperator, op));
			}
			if (r != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < r.length; i++) {
					if (r[i] != null)
						switch (r[i].m_intSuccess) {
						case 0:
							break;
						case 1:
							strMsg.append(nc.bs.ml.NCLangResOnserver
									.getInstance().getStrByID("2002100556",
											"UPP2002100556-000119")/*
																	 * @res
																	 * "����:"
																	 */
									+ r[i].m_strDescription + "\n");
							break;
						case 2:
							errflag = true;
							strMsg.append(nc.bs.ml.NCLangResOnserver
									.getInstance().getStrByID("2002100556",
											"UPP2002100556-000120")/*
																	 * @res
																	 * "����:"
																	 */
									+ r[i].m_strDescription + "\n");
							break;
						default:
							strMsg.append(nc.bs.ml.NCLangResOnserver
									.getInstance().getStrByID("2002100556",
											"UPP2002100556-000121")/*
																	 * @res
																	 * "��Ϣ:"
																	 */
									+ r[i].m_strDescription + "\n");
						}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}
			return r;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	private DetailVO[] catAss(DetailVO[] details) throws BusinessException,
			Exception {
		if (details == null || details.length == 0)
			return details;
		String[] Ids = null;
		Vector vecIds = new Vector();
		HashMap tempmap = new HashMap();
		for (int j = 0; j < details.length; j++) {
			if (details[j].getAssid() == null
					|| "".equals(details[j].getAssid()))
				continue;
			if (details[j].getAss() == null
					&& tempmap.get(details[j].getAssid()) == null) {
				vecIds.addElement(details[j].getAssid());
				tempmap.put(details[j].getAssid(), details[j].getAssid());
			}
		}
		if (vecIds.size() == 0) {
			VoWizard wizard = new VoWizard();

			wizard.setMatchingIndex(new int[] { VoucherKey.D_DETAILINDEX },
					null);

			wizard.sort(details, new int[] { VoucherKey.D_DETAILINDEX });
			return details;
		}
		Ids = new String[vecIds.size()];
		vecIds.copyInto(Ids);

		IFreevaluePub dmo = NCLocator.getInstance().lookup(IFreevaluePub.class);
		nc.vo.fipub.freevalue.GlAssVO[] glAssVo = dmo.queryAllByIDs(Ids, null,
				Module.GL);

		if (glAssVo == null) {
			throw new BusinessException("Error AssIDs::" + vecIds);
		}
		HashMap assvocache = new HashMap();
		for (int i = 0; i < glAssVo.length; i++) {
			glAssVo[i].setAssID(glAssVo[i].getAssID().trim());
			assvocache.put(glAssVo[i].getAssID(), glAssVo[i].getAssVos());
		}

		for (int i = 0; i < details.length; i++) {
			if (details[i].getAssid() != null && details[i].getAss() == null) {
				details[i].setAss((nc.vo.glcom.ass.AssVO[]) assvocache
						.get(details[i].getAssid()));
			}
		}

		VoWizard wizard = new VoWizard();

		wizard.setMatchingIndex(new int[] { VoucherKey.D_DETAILINDEX }, null);

		wizard.sort(details, new int[] { VoucherKey.D_DETAILINDEX });

		return details;
	}

	private DetailVO[] catCheckstylename(DetailVO[] details) throws Exception {
		if (details == null || details.length == 0)
			return details;
		nc.vo.bd.balatype.BalaTypeVO[] balatype = getBalatype(details[0]
				.getPk_org());
		if (balatype == null)
			return details;
		HashMap map = new HashMap();
		for (int i = 0; i < balatype.length; i++) {
			map.put(balatype[i].getPk_balatype(), balatype[i].getName());
		}
		for (int i = 0; i < details.length; i++) {
			details[i].setCheckstylename((String) map.get(details[i]
					.getCheckstyle()));
		}
		return details;
	}

	private void catCorpname(VoucherVO[] vouchers, SmallCorpVO[] corps)
			throws BusinessException, Exception {
		// 60x
		// if (vouchers == null || vouchers.length == 0)
		// return;
		// nc.vo.glcom.wizard.VoWizard wizard = new
		// nc.vo.glcom.wizard.VoWizard();
		// wizard.setMatchingIndex(new int[] { VoucherKey.V_PK_FINANCEORG }, new
		// int[] { nc.vo.gl.voucher.SmallCorpKey.PK_CORP });
		// wizard.setAppendIndex(new int[] { VoucherKey.V_CORPNAME }, new int[]
		// { nc.vo.gl.voucher.SmallCorpKey.CORP_NAME });
		// wizard.concat(vouchers, corps, false);
	}

	private DetailVO[] catCurrcode(DetailVO[] details,
			nc.vo.bd.currtype.CurrtypeVO[] currtype) throws BusinessException,
			Exception {
		if (details == null || details.length == 0)
			return details;
		// return details;
		// VoWizard wizard = new VoWizard();
		// int[] intIndexInit = new int[] { VoucherKey.D_PK_CURRTYPE };
		// int[] intIndexCurrtype = new int[] {
		// nc.vo.bd.b20.CurrtypeKey.K_Pk_currtype };

		// wizard.setMatchingIndex(intIndexInit, intIndexCurrtype);
		// wizard.setAppendIndex(new int[] { VoucherKey.D_CURRTYPECODE,
		// VoucherKey.D_CURRTYPENAME }, new int[] {
		// nc.vo.bd.b20.CurrtypeKey.K_Currtypecode,
		// nc.vo.bd.b20.CurrtypeKey.K_Currtypename });

		DetailVO[] result;
		// Object[] obj = wizard.concat(details, currtype);

		VoWizard60<DetailVO> wizard = new VoWizard60<DetailVO>();
		String[] strIndexInit = GLVoTools
				.getStringArray(new int[] { VoucherKey.D_PK_CURRTYPE });
		String[] strIndexCurrtype = new String[] { CurrtypeVO.PK_CURRTYPE };
		wizard.setMatchingField(strIndexInit, strIndexCurrtype);
		wizard.setAppendField(
				GLVoTools.getStringArray(new int[] { VoucherKey.D_CURRTYPECODE,
						VoucherKey.D_CURRTYPENAME }), new String[] {
						CurrtypeVO.CODE, CurrtypeVO.NAME });
		Object[] obj = wizard.concat(details, currtype);

		result = new DetailVO[obj.length];
		System.arraycopy(obj, 0, result, 0, obj.length);
		return result;
	}

	/**
	 * �޸ĵط���1��˫��forѭ�����Ϊ��������forѭ������ѭ���ڲ㽫ֵ����Set�У�Ȼ��ѭ����㣬ͨ��Set��contains(Object
	 * obj)�����ж��Ƿ�������ѭ����ֵ
	 * ˫��forѭ��Ч��Ϊdetails.length*pk_details.length���ĺ�Ч��Ϊdetails
	 * .length+pk_details.length
	 * 2��nc.vo.pub.lang.UFBoolean.TRUE��ΪUFBoolean.TRUE��û��Ҫÿ�ζ�newһ������
	 * 
	 * @param details
	 * @param pk_details
	 * @return
	 * @throws BusinessException
	 */
	private DetailVO[] catDetailMatchingflag(DetailVO[] details,
			String[] pk_details) throws BusinessException {
		if (details == null || details.length == 0)
			return details;
		if (pk_details == null || pk_details.length == 0)
			return details;
		/*
		 * for (int i = 0; i < details.length; i++) { for (int j = 0; j <
		 * pk_details.length; j++) { if
		 * (details[i].getPk_detail().equals(pk_details[j])) {
		 * details[i].setIsmatched(nc.vo.pub.lang.UFBoolean.TRUE); break; } } }
		 */
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < pk_details.length; i++) {
			set.add(pk_details[i]);
		}
		for (int i = 0; i < details.length; i++) {
			if (set.contains(details[i].getPk_detail())) {
				details[i].setIsmatched(UFBoolean.TRUE);
			}
		}
		return details;
	}

	private DetailVO[] catDetailMachForOffer(VoucherVO voucher)
			throws BusinessException {
		DetailVO[] details = voucher.getDetails();
		if (details == null || details.length == 0)
			return details;

		for (int i = 0; i < details.length; i++) {

			if (voucher.getOffervoucher() != null) {
				details[i].setIsmatched(nc.vo.pub.lang.UFBoolean.TRUE);
			}

		}

		return details;
	}

	private VoucherVO[] catDetailMatchingFlagForOffer(VoucherVO[] vouchers)
			throws BusinessException {

		for (int i = 0; i < vouchers.length; i++) {
			VoucherVO voucherVO = vouchers[i];
			for (int j = 0; j < voucherVO.getDetails().length; j++) {
				DetailVO detail = voucherVO.getDetails()[j];
				if (detail.getPk_offerdetail() != null) {
					detail.setIsmatched(nc.vo.pub.lang.UFBoolean.TRUE);

				}
			}
		}

		return vouchers;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-5-28 15:03:34)
	 * 
	 * @return nc.vo.gl.pubvoucher.VoucherVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 * @throws BusinessException
	 */
	private VoucherVO catDetailPk_corp(VoucherVO voucher)
			throws BusinessException {
		// Map<String, FinanceOrgVO> map = new HashMap<String, FinanceOrgVO>();
		DetailVO detail = null;
		HashMap<String, String> versionMap = null;
		String[] detailUnit = new String[voucher.getNumDetails()];
		for (int i = 0; i < voucher.getNumDetails(); i++) {
			detail = voucher.getDetail(i);
			detailUnit[i] = detail.getPk_unit() == null ? voucher.getPk_org()
					: detail.getPk_unit();
		}

		for (int i = 0; i < voucher.getNumDetails(); i++) {
			detail = voucher.getDetail(i);
			detail.setPk_accountingbook(voucher.getPk_accountingbook());
			detail.setPk_group(voucher.getPk_group());
			detail.setPk_glorg(voucher.getPk_org());
			detail.setPk_glbook(voucher.getPk_setofbook());
			// ƾ֤��֯ǰ̨����
			if (SystemtypeConst.GL.equals(voucher.getPk_system())) {
				continue;
			}
			detail.setPk_org(voucher.getPk_org());
			detail.setPk_org_v(voucher.getPk_org_v());
			if (StringUtils.isEmpty(detail.getPk_unit())) {
				detail.setPk_unit(voucher.getPk_org());
				detail.setPk_unit_v(voucher.getPk_org_v());
			}
			if (StringUtils.isEmpty(detail.getPk_unit_v())) {
				versionMap = GlOrgUtils.getNewVIDSByOrgIDSAndDate(detailUnit,
						voucher.getPrepareddate());
				if (versionMap == null
						|| versionMap.get(detail.getPk_unit()) == null) {
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
							.getNCLangRes().getStrByID("voucherprivate_0",
									"02002005-0061")/* @res "�޷���ȡ��¼ҵ��Ԫ�İ汾��Ϣ��" */);
				}
				detail.setPk_unit_v(versionMap.get(detail.getPk_unit()));
			}
			// hurh ��ϵͳƾ֤������ʱ����շ�¼����
			if (StringUtils.isEmpty(voucher.getPk_voucher())) {
				detail.setPk_detail(null);
			}
		}
		return voucher;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-22 9:38:36)
	 * 
	 * @param vouchers
	 *            nc.vo.gl.pubvoucher.VoucherVO[]
	 * @param details
	 *            nc.vo.gl.pubvoucher.DetailVO[]
	 * @BusinessException java.lang.BusinessException �쳣˵����
	 */
	private void catDetails(VoucherVO[] vouchers, DetailVO[] details)
			throws BusinessException {
		if (vouchers == null || vouchers.length == 0)
			return;

		// for (int i = 0; i < vouchers.length; i++) {
		// for (int j = 0; j < details.length; j++) {
		// if (vouchers[i].getPk_voucher().equals(details[j].getPk_voucher())) {
		// vouchers[i].addDetail(details[j]);
		// }
		// }
		// }
		HashMap<String, VoucherVO> voucherhm = new HashMap<String, VoucherVO>();
		for (int i = 0; i < vouchers.length; i++) {
			voucherhm.put(vouchers[i].getPk_voucher(), vouchers[i]);
		}
		for (int i = 0; i < details.length; i++) {
			VoucherVO voucher = voucherhm.get(details[i].getPk_voucher());
			if (voucher != null) {
				voucher.addDetail(details[i]);

			}
		}
	}

	private DetailVO[] catDtlFreevalue(DetailVO[] details)
			throws BusinessException, Exception {
		if (details == null || details.length == 0)
			return details;
		List<String> pk_details = new LinkedList<String>();
		String strTemp = null;
		for (int i = 0; i < details.length; i++) {
			strTemp = details[i].getPk_detail();
			if (strTemp != null) {
				pk_details.add(strTemp);
			}
		}
		DtlfreevalueDMO dmo = new DtlfreevalueDMO();
		DtlfreevalueVO[] app = dmo.queryByDetailPKs(pk_details
				.toArray(new String[0]));

		VoWizard wizard = new VoWizard();
		int[] intIndexInit = new int[] { VoucherKey.D_PK_DETAIL };
		int[] intIndexCurrtype = new int[] { VoucherKey.D_PK_DETAIL };

		wizard.setMatchingIndex(intIndexInit, intIndexCurrtype);
		wizard.setAppendIndex(new int[] { VoucherKey.D_FREEVALUE1,
				VoucherKey.D_FREEVALUE2, VoucherKey.D_FREEVALUE3,
				VoucherKey.D_FREEVALUE4, VoucherKey.D_FREEVALUE5,
				VoucherKey.D_FREEVALUE6, VoucherKey.D_FREEVALUE7,
				VoucherKey.D_FREEVALUE8, VoucherKey.D_FREEVALUE9,
				VoucherKey.D_FREEVALUE10, VoucherKey.D_FREEVALUE11,
				VoucherKey.D_FREEVALUE12, VoucherKey.D_FREEVALUE13,
				VoucherKey.D_FREEVALUE14, VoucherKey.D_FREEVALUE15,
				VoucherKey.D_FREEVALUE16, VoucherKey.D_FREEVALUE17,
				VoucherKey.D_FREEVALUE18, VoucherKey.D_FREEVALUE19,
				VoucherKey.D_FREEVALUE20, VoucherKey.D_FREEVALUE21,
				VoucherKey.D_FREEVALUE22, VoucherKey.D_FREEVALUE23,
				VoucherKey.D_FREEVALUE24, VoucherKey.D_FREEVALUE25,
				VoucherKey.D_FREEVALUE26, VoucherKey.D_FREEVALUE27,
				VoucherKey.D_FREEVALUE28, VoucherKey.D_FREEVALUE29,
				VoucherKey.D_FREEVALUE30 }, new int[] {
				VoucherKey.D_FREEVALUE1, VoucherKey.D_FREEVALUE2,
				VoucherKey.D_FREEVALUE3, VoucherKey.D_FREEVALUE4,
				VoucherKey.D_FREEVALUE5, VoucherKey.D_FREEVALUE6,
				VoucherKey.D_FREEVALUE7, VoucherKey.D_FREEVALUE8,
				VoucherKey.D_FREEVALUE9, VoucherKey.D_FREEVALUE10,
				VoucherKey.D_FREEVALUE11, VoucherKey.D_FREEVALUE12,
				VoucherKey.D_FREEVALUE13, VoucherKey.D_FREEVALUE14,
				VoucherKey.D_FREEVALUE15, VoucherKey.D_FREEVALUE16,
				VoucherKey.D_FREEVALUE17, VoucherKey.D_FREEVALUE18,
				VoucherKey.D_FREEVALUE19, VoucherKey.D_FREEVALUE20,
				VoucherKey.D_FREEVALUE21, VoucherKey.D_FREEVALUE22,
				VoucherKey.D_FREEVALUE23, VoucherKey.D_FREEVALUE24,
				VoucherKey.D_FREEVALUE25, VoucherKey.D_FREEVALUE26,
				VoucherKey.D_FREEVALUE27, VoucherKey.D_FREEVALUE28,
				VoucherKey.D_FREEVALUE29, VoucherKey.D_FREEVALUE30 });

		DetailVO[] result = null;
		Object[] obj = null;
		// 60x
		if (app != null && app.length != 0) {
			obj = wizard.concat(details, app);
			result = new DetailVO[obj.length];
			for (int i = 0; i < obj.length; i++) {
				result[i] = (DetailVO) obj[i];
			}
			// System.arraycopy(obj, 0, result, 0, obj.length);
		} else {
			result = details;
		}
		return result;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-5-28 15:03:34)
	 * 
	 * @return nc.vo.gl.pubvoucher.VoucherVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	private VoucherVO catOppositesubj(VoucherVO voucher) {

		if (voucher.getNumDetails() == 2) {
			voucher.getDetail(0).setOppositesubj(
					voucher.getDetail(1).getPk_accasoa());
			voucher.getDetail(1).setOppositesubj(
					voucher.getDetail(0).getPk_accasoa());
			return voucher;
		}

		Set<String> debitaccsubj = new HashSet<String>();
		Set<String> creditaccsubj = new HashSet<String>();
		String debitaccsubjpks = "";
		String creditaccsubjpks = "";
		for (int i = 0; i < voucher.getNumDetails(); i++) {
			if (null == voucher.getDetail(i).getLocalcreditamount()
					|| voucher.getDetail(i).getLocalcreditamount()
							.equals(new nc.vo.pub.lang.UFDouble(0))) {
				if (debitaccsubj.size() < 9)
					debitaccsubj.add(voucher.getDetail(i).getPk_accasoa());
				else
					continue;
			} else {
				if (creditaccsubj.size() < 9)
					creditaccsubj.add(voucher.getDetail(i).getPk_accasoa());
				else
					continue;
			}
		}
		String[] debitArray = debitaccsubj.toArray(new String[0]);
		if (debitArray != null) {
			for (int i = 0; i < debitArray.length; i++) {
				if (debitArray[i] != null)
					debitaccsubjpks = debitaccsubjpks
							+ debitArray[i].toString() + ",";
			}
		}
		if (debitaccsubjpks.length() > 0)
			debitaccsubjpks = debitaccsubjpks.substring(0,
					debitaccsubjpks.length() - 1);
		String[] creditArray = creditaccsubj.toArray(new String[0]);
		if (creditArray != null) {
			for (int i = 0; i < creditArray.length; i++) {
				if (creditArray[i] != null)
					creditaccsubjpks = creditaccsubjpks
							+ creditArray[i].toString() + ",";
			}
		}
		if (creditaccsubjpks.length() > 0)
			creditaccsubjpks = creditaccsubjpks.substring(0,
					creditaccsubjpks.length() - 1);
		for (int i = 0; i < voucher.getNumDetails(); i++) {
			if (null == voucher.getDetail(i).getLocalcreditamount()
					|| voucher.getDetail(i).getLocalcreditamount()
							.equals(new nc.vo.pub.lang.UFDouble(0))) {
				voucher.getDetail(i).setOppositesubj(creditaccsubjpks);
			} else {
				voucher.getDetail(i).setOppositesubj(debitaccsubjpks);
			}
		}
		return voucher;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-5-28 15:03:34)
	 * 
	 * @return nc.vo.gl.pubvoucher.VoucherVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	private VoucherVO catRegulationPeriod(VoucherVO voucher) {
		if (voucher.getM_adjustperiod() == null
				|| voucher.getM_adjustperiod().trim().length() == 2)
			voucher.setM_adjustperiod(voucher.getPeriod());
		if ((null != voucher.getVoucherkind()
				&& voucher.getVoucherkind().intValue() != 1 && !(voucher
				.getVoucherkind().intValue() == GLVoucherKindConst.PLCF
				&& voucher.getM_adjustperiod() != null && voucher
				.getM_adjustperiod().length() > 2)))
			voucher.setM_adjustperiod(voucher.getPeriod());
		return voucher;
	}

	private VoucherVO catOrgAndBook(VoucherVO voucher) {
		if (voucher.getPk_accountingbook() == null) {
			return voucher;
		}
		// hurh ����ƾ֤��֯��Ϣǰ̨������̨���޸�
		if (SystemtypeConst.GL.equals(voucher.getPk_system())) {
			return voucher;
		}
		try {
			if (voucher.getPk_org() == null
					|| voucher.getPk_org().trim().length() == 0
					|| voucher.getPk_org_v() == null) {
				voucher.setPk_org(AccountBookUtil
						.getPk_orgByAccountBookPk(voucher
								.getPk_accountingbook()));
				HashMap<String, String> versionMap = GlOrgUtils
						.getNewVIDSByOrgIDSAndDate(
								new String[] { voucher.getPk_org() },
								voucher.getPrepareddate());
				if (versionMap == null
						|| versionMap.get(voucher.getPk_org()) == null) {
					// OrgVO orgVO =
					// NCLocator.getInstance().lookup(IOrgUnitQryService.class).getOrg(voucher.getPk_org());
					// throw new BusinessException("�޷���ȡ��֯" + orgVO.getName()+
					// " " + voucher.getPrepareddate().toStdString() +
					// "�汾����֯�汾��Ϣ��");
					throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
							.getNCLangRes().getStrByID("voucherprivate_0",
									"02002005-0062", null,
									new String[] { voucher.getPk_org() })/*
																		 * @res
																		 * "�޷���ȡ��֯{0}�İ汾��Ϣ"
																		 */);
				}
				voucher.setPk_org_v(versionMap.get(voucher.getPk_org()));
			}
			// if (voucher.getPk_setofbook() == null ||
			// voucher.getPk_setofbook().trim().length() == 0)
			// voucher.setPk_setofbook(AccountBookUtil.getFinanceOrgVOByPrimarykey(voucher.getPk_accountingbook()).getPk_financeorg());
			// if (voucher.getPk_financeorg() == null ||
			// voucher.getPk_financeorg().trim().length() == 0)
			// voucher.setPk_financeorg(AccountBookUtil.getPkSetofBookByAccountingBookPk(voucher.getPk_accountingbook()));
		} catch (Exception e) {
			Log.getInstance(this.getClass().getName())
					.info("Attention!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			Log.getInstance(this.getClass().getName())
					.info("Attention!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			Log.getInstance(this.getClass().getName())
					.info("There is an BusinessException at class and method::VoucherVO nc.bs.gl.voucher.VoucherBO.catOrgAndBook(VoucherVO voucher)");
			Log.getInstance(this.getClass().getName())
					.info("It is not a problem for GL system, but it maybe a serious error for other system.");
			Log.getInstance(this.getClass().getName()).info(
					"The BusinessException is ::");
			Logger.error(e.getMessage(), e);
			Log.getInstance(this.getClass().getName())
					.info("Attention!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			Log.getInstance(this.getClass().getName())
					.info("Attention!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return voucher;
	}

	private DetailVO[] catSubjfreevalue(DetailVO[] details) throws Exception {
		if (details == null || details.length == 0)
			return details;
		String[] pk_details = new String[details.length];
		for (int i = 0; i < pk_details.length; i++) {
			pk_details[i] = details[i].getPk_detail();
		}
		SubjfreevalueDMO dmo = new SubjfreevalueDMO();
		SubjfreevalueVO[] app = dmo.queryByDetailPKs(pk_details);

		if (app != null) {
			HashMap cache = new HashMap();
			for (int i = 0; i < app.length; i++) {
				if (app[i] == null)
					continue;
				cache.put(app[i].getPk_detail(), app[i]);
			}
			// 60x
			// for (int i = 0; i < details.length; i++) {
			// details[i].setSubjfreevalue((SubjfreevalueVO)
			// cache.get(details[i].getPk_detail()));
			// }
		}
		return details;
	}

	private DetailVO[] catCashFlows(DetailVO[] details) throws Exception {
		if (details == null || details.length == 0)
			return details;
		String[] pk_details = new String[details.length];
		for (int i = 0; i < pk_details.length; i++) {
			pk_details[i] = details[i].getPk_detail();
		}
		ICashFlowCase icfquery = (ICashFlowCase) NCLocator.getInstance()
				.lookup(ICashFlowCase.class.getName());
		CashflowcaseVO[] app = null;
		app = icfquery.queryByPKDetails(pk_details);
		if (app != null) {
			BDMultiLangUtil.replaceObjNameWithMDID(app, "getPk_cashflow",
					"setCashflowName", IBDMetaDataIDConst.CASHFLOW);
			HashMap cache = new HashMap();
			for (int i = 0; i < app.length; i++) {
				if (app[i] == null)
					continue;
				if (cache.get(app[i].getPk_detail()) == null) {
					Vector<CashflowcaseVO> vec = new Vector<CashflowcaseVO>();
					vec.addElement(app[i]);
					cache.put(app[i].getPk_detail(), vec);
				} else {
					Vector<CashflowcaseVO> vec = (Vector<CashflowcaseVO>) cache
							.get(app[i].getPk_detail());
					vec.addElement(app[i]);
					cache.put(app[i].getPk_detail(), vec);
				}
			}
			for (int i = 0; i < details.length; i++) {
				Vector<CashflowcaseVO> vec = (Vector<CashflowcaseVO>) cache
						.get(details[i].getPk_detail());
				if (vec != null && vec.size() > 0) {
					CashflowcaseVO[] chvos = new CashflowcaseVO[vec.size()];
					chvos = (CashflowcaseVO[]) vec.toArray(chvos);
					details[i].setCashFlow(chvos);
				}
			}
		}
		return details;
	}

	private DetailVO[] catCashFlows4RtDetail(DetailVO[] details)
			throws Exception {
		if (details == null || details.length == 0)
			return details;
		String[] pk_details = new String[details.length];
		for (int i = 0; i < pk_details.length; i++) {
			pk_details[i] = details[i].getPk_detail();
		}
		ICashFlowCase icfquery = (ICashFlowCase) NCLocator.getInstance()
				.lookup(ICashFlowCase.class.getName());
		CashflowcaseVO[] app = null;
		app = icfquery.queryByPKRtDetails(pk_details);
		if (app != null) {
			HashMap cache = new HashMap();
			for (int i = 0; i < app.length; i++) {
				if (app[i] == null)
					continue;
				if (cache.get(app[i].getPk_detail()) == null) {
					Vector<CashflowcaseVO> vec = new Vector<CashflowcaseVO>();
					vec.addElement(app[i]);
					cache.put(app[i].getPk_detail(), vec);
				} else {
					Vector<CashflowcaseVO> vec = (Vector<CashflowcaseVO>) cache
							.get(app[i].getPk_detail());
					vec.addElement(app[i]);
					cache.put(app[i].getPk_detail(), vec);
				}
			}
			for (int i = 0; i < details.length; i++) {
				Vector<CashflowcaseVO> vec = (Vector<CashflowcaseVO>) cache
						.get(details[i].getPk_detail());
				if (vec != null && vec.size() > 0) {
					CashflowcaseVO[] chvos = new CashflowcaseVO[vec.size()];
					chvos = (CashflowcaseVO[]) vec.toArray(chvos);
					details[i].setCashFlow(chvos);
				}
			}
		}
		return details;
	}

	/**
	 * add by CongJinliang
	 * 
	 * @param details
	 * @return
	 * @throws Exception
	 * 
	 */
	private DetailVO[] catSubjName(DetailVO[] details) throws Exception {
		if (details == null || details.length == 0)
			return details;

		// hurh �ȴ���Ŀ����ʵ�ֶ�汾
		String[] pk_accasoas = new String[details.length];
		for (int i = 0; i < details.length; i++) {
			pk_accasoas[i] = details[i].getPk_accasoa();
		}

		AccountVO[] accounts = AccountUtilGL.queryByPks(pk_accasoas, details[0]
				.getPrepareddate().toStdString());
		// ��ѭ��Ƕ�ײ�ֳ�����ѭ��
		HashMap<String, AccountVO> accountMap = new HashMap<String, AccountVO>();
		for (AccountVO account : accounts) {
			accountMap.put(account.getPk_accasoa(), account);
		}

		DetailVO detailTemp = null;
		for (int i = 0; i < details.length; i++) {
			// AccountVO[] account = AccountUtilGL.queryByPks(new String[] {
			// details[i].getPk_accasoa()
			// }, details[i].getPrepareddate().toStdString());
			detailTemp = details[i];
			AccountVO account = accountMap.get(detailTemp.getPk_accasoa());
			if (account != null) {
				details[i].setAccsubjname(account.getDispname());
				details[i].setAccsubjcode((account.getCode()));
			}
		}
		return details;
	}

	/**
	 * modify by CongJinliang
	 * 
	 * @param details
	 * @param year
	 * @param period
	 * @return
	 * @throws Exception
	 */
	private DetailVO[] catSubjName(DetailVO[] details, String stddate)
			throws Exception {
		if (details == null || details.length == 0)
			return details;

		String[] pk_accasoas = new String[details.length];
		for (int i = 0; i < details.length; i++) {
			pk_accasoas[i] = details[i].getPk_accasoa();
		}

		AccountVO[] accounts = AccountUtilGL.queryByPks(pk_accasoas, stddate);
		// ��ѭ��Ƕ�ײ�ֳ�����ѭ��
		HashMap<String, AccountVO> accountMap = new HashMap<String, AccountVO>();
		for (AccountVO account : accounts) {
			accountMap.put(account.getPk_accasoa(), account);
		}

		DetailVO detailTemp = null;
		for (int i = 0; i < details.length; i++) {
			detailTemp = details[i];
			AccountVO account = accountMap.get(detailTemp.getPk_accasoa());
			if (account != null) {
				details[i].setAccsubjname(GLMultiLangUtil
						.getMultiDispName(account));
				details[i].setAccsubjcode((account.getCode()));
			}
		}
		return details;
	}

	private void catSystemname(VoucherVO[] vouchers) throws Exception {
		// if (vouchers == null || vouchers.length == 0)
		// return;
		// DapsystemDMO dmo = new DapsystemDMO();
		// DapsystemVO[] dapsystem = dmo.queryAll(null);
		// VoWizard wizard = new VoWizard();
		// int[] intIndexInit = new int[] {
		// VoucherKey.V_PK_SYSTEM
		// };
		// int[] intIndexCurrtype = new int[] {
		// VoucherKey.V_PK_SYSTEM
		// };
		//
		// wizard.setMatchingIndex(intIndexInit, intIndexCurrtype);
		// wizard.setAppendIndex(new int[] {
		// VoucherKey.V_SYSTEMNAME
		// }, new int[] {
		// VoucherKey.V_SYSTEMNAME
		// });

		// 60x wizard.concat(vouchers, dapsystem);
	}

	private void sort(VoucherVO[] vouchers) {
		if (vouchers == null || vouchers.length == 0)
			return;
		nc.vo.glcom.wizard.VoWizard wizard = new nc.vo.glcom.wizard.VoWizard();
		wizard.setMatchingIndex(new int[] { VoucherKey.V_PK_ACCOUNTINGBOOK,
				VoucherKey.V_YEAR, VoucherKey.V_PERIOD,
				VoucherKey.V_PK_VOUCHERTYPE, VoucherKey.V_NO }, null);
		wizard.sort(vouchers, new int[] { VoucherKey.V_PK_ACCOUNTINGBOOK,
				VoucherKey.V_YEAR, VoucherKey.V_PERIOD,
				VoucherKey.V_PK_VOUCHERTYPE, VoucherKey.V_NO });
	}

	// private void catUsername(VoucherVO[] vouchers, UserVO[] users) throws
	// Exception {
	// if (vouchers == null || vouchers.length == 0)
	// return;
	// if (users == null || users.length == 0)
	// return;
	// nc.vo.glcom.wizard.VoWizard wizard = new nc.vo.glcom.wizard.VoWizard();
	// wizard.setMatchingIndex(new int[] {
	// VoucherKey.V_PK_CASHER
	// }, new int[] {
	// UserKey.PK_USER
	// });
	// wizard.setAppendIndex(new int[] {
	// VoucherKey.V_CASHERNAME
	// }, new int[] {
	// UserKey.USERNAME
	// });
	// Object[] obj1 = wizard.concat(vouchers, users, false);
	//
	// wizard.setMatchingIndex(new int[] {
	// VoucherKey.V_PK_CHECKED
	// }, new int[] {
	// UserKey.PK_USER
	// });
	// wizard.setAppendIndex(new int[] {
	// VoucherKey.V_CHECKEDNAME
	// }, new int[] {
	// UserKey.USERNAME
	// });
	// Object[] obj2 = wizard.concat(vouchers, users, false);
	//
	// wizard.setMatchingIndex(new int[] {
	// VoucherKey.V_PK_MANAGER
	// }, new int[] {
	// UserKey.PK_USER
	// });
	// wizard.setAppendIndex(new int[] {
	// VoucherKey.V_MANAGERNAME
	// }, new int[] {
	// UserKey.USERNAME
	// });
	// Object[] obj3 = wizard.concat(vouchers, users, false);
	//
	// wizard.setMatchingIndex(new int[] {
	// VoucherKey.V_PK_PREPARED
	// }, new int[] {
	// UserKey.PK_USER
	// });
	// wizard.setAppendIndex(new int[] {
	// VoucherKey.V_PREPAREDNAME
	// }, new int[] {
	// UserKey.USERNAME
	// });
	// Object[] obj4 = wizard.concat(vouchers, users, false);
	//
	// }

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-1 13:14:48)
	 * 
	 * @return nc.vo.gl.pubvoucher.VoucherVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 * @BusinessException BusinessException �쳣˵����
	 */
	private VoucherVO[] catVoucherFreeValue(VoucherVO[] voucher)
			throws Exception {
		// voucher.setNo(getCorrectVoucherNo(voucher));
		if (voucher == null || voucher.length == 0)
			return voucher;
		String[] pks = new String[voucher.length];
		for (int i = 0; i < voucher.length; i++) {
			pks[i] = voucher[i].getPk_voucher();
		}
		VchfreevalueVO[] vchf = new VchfreevalueDMO().queryByVoucherPKs(pks);
		if (vchf == null || vchf.length == 0)
			return voucher;

		HashMap cache = new HashMap();
		for (int i = 0; i < vchf.length; i++) {
			if (vchf[i] != null) {
				cache.put(vchf[i].getPk_voucher(), vchf[i]);
			}
		}
		for (int i = 0; i < voucher.length; i++) {
			VchfreevalueVO vf = (VchfreevalueVO) cache.get(voucher[i]
					.getPk_voucher());
			if (vf != null) {
				voucher[i].setFreevalue1(vf.getFreevalue1());
				voucher[i].setFreevalue2(vf.getFreevalue2());
				voucher[i].setFreevalue3(vf.getFreevalue3());
				voucher[i].setFreevalue4(vf.getFreevalue4());
				voucher[i].setFreevalue5(vf.getFreevalue5());
			}
		}
		return voucher;
	}

	private void catVoucherMatchingflag(VoucherVO[] vouchers,
			String[] pk_vouchers) throws BusinessException {
		if (vouchers == null || vouchers.length == 0)
			return;
		if (pk_vouchers == null || pk_vouchers.length == 0)
			return;
		HashMap map = new HashMap();
		for (int i = 0; i < pk_vouchers.length; i++) {
			map.put(pk_vouchers[i], "Y");
		}
		for (int i = 0; i < vouchers.length; i++) {
			if (map.get(vouchers[i].getPk_voucher()) == null)
				vouchers[i].setIsmatched(new Boolean(false));
			else
				vouchers[i].setIsmatched(new Boolean(true));
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-1 13:14:48)
	 * 
	 * @return nc.vo.gl.pubvoucher.VoucherVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 * @BusinessException BusinessException �쳣˵����
	 */
	private VoucherVO catVoucherNo(VoucherVO voucher) throws BusinessException {
		voucher.setNo(getCorrectVoucherNo(voucher));
		return voucher;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-28 11:57:58)
	 * 
	 * @return nc.vo.gl.pubvoucher.VoucherVO[]
	 * @param vouchers
	 *            nc.vo.gl.pubvoucher.VoucherVO[]
	 * @BusinessException BusinessException �쳣˵����
	 */
	public VoucherVO[] catVouchers(VoucherVO[] vouchers)
			throws BusinessException {
		if (vouchers == null || vouchers.length == 0)
			return vouchers;
		try {
			if (vouchers[0].getCorpname() == null)
				catCorpname(vouchers, getCorps());
			if (vouchers[0].getVouchertypename() == null)
				catVouchertypename(vouchers, getVouchertypes(null));
			// if (vouchers[0].getPreparedname() == null)
			// catUsername(vouchers, getUsers(null));

			DetailVO[] details = null;
			// hurh
			List<String> voucherpks = new LinkedList<String>();
			String strTemp = null;
			for (int i = 0; i < vouchers.length; i++) {
				strTemp = vouchers[i].getPk_voucher();
				if (strTemp != null) {
					voucherpks.add(strTemp);
				}

				details = vouchers[i].getDetails();
				if (details != null && details.length > 0
						&& details[0].getCurrtypecode() == null)
					details = catCurrcode(details, getCurrtypes());
				if (details != null && details.length > 0
						&& details[0].getAccsubjname() == null) {
					String[] pk_accsubj = new String[details.length];
					for (int j = 0; j < details.length; j++)
						pk_accsubj[j] = details[j].getPk_accasoa();
					details = catSubjName(details, vouchers[i]
							.getPrepareddate().toStdString());
				}
				if (details != null && details.length > 0
						&& details[0].getAss() == null)
					details = catAss(details);
				details = catDtlFreevalue(details);
				VoWizard wizard = new VoWizard();

				wizard.setMatchingIndex(new int[] { VoucherKey.D_DETAILINDEX },
						null);

				wizard.sort(details, new int[] { VoucherKey.D_DETAILINDEX });
				vouchers[i].setDetails(details);
			}

			// hurh 2011-06-27 ƾ֤pk�������gl_voucher����û��pk��Ӧ��ƾ֤��Ӧ�����pkֵ
			if (voucherpks.size() > 0) {
				VoucherVO[] tempVOS = queryByPks(voucherpks
						.toArray(new String[0]));
				if (tempVOS != null) {
					HashMap<String, VoucherVO> voucherMap = new HashMap<String, VoucherVO>();
					for (VoucherVO voTemp : tempVOS) {
						voucherMap.put(voTemp.getPk_voucher(), voTemp);
					}
					for (VoucherVO voTemp : vouchers) {
						if (voTemp.getPk_voucher() != null
								&& voucherMap.get(voTemp.getPk_voucher()) == null) {
							voTemp.setPk_voucher(null);
						}
					}
				} else {
					for (VoucherVO voTemp : vouchers) {
						voTemp.setPk_voucher(null);
					}
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(
					"VoucherBO::catVouchers(VoucherVO[]) BusinessException!"
							+ e.getMessage(), e);
		}
		return vouchers;
	}

	private void catVouchertypename(VoucherVO[] vouchers, VoucherTypeVO[] types)
			throws Exception {
		if (vouchers == null || vouchers.length == 0)
			return;
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; null != types && i < types.length; i++) {
			map.put(types[i].getPk_vouchertype(), types[i].getName());
		}
		for (int j = 0; j < vouchers.length; j++)
			vouchers[j].setVouchertypename(map.get(vouchers[j]
					.getPk_vouchertype()));
		// VoWizard60 wizard = new VoWizard60();
		// // wizard.set(new int[] { VoucherKey.V_PK_VOUCHERTYPE }, new int[]
		// {VoucherTypeKey.pk_vouchertype });
		// wizard.setMatchingField(new String[]{"pk_vouchertype"},new
		// String[]{types[0].getPKFieldName()});
		// // wizard.setAppendIndex(new int[] { VoucherKey.V_VOUCHERTYPENAME },
		// new int[] { VoucherTypeKey.vouchtypename });
		// wizard.setAppendField(new String[]{vouchers[0].m_vouchertypename},
		// new String[]{types[0].NAME});
		// Object[] obj = wizard.concat(vouchers, types, false);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-7-18 14:06:11)
	 * 
	 * @param voucher
	 *            nc.voucher.gl.pubvoucher.VoucherVO
	 */
	private OperationResultVO[] checkCanAbandon(VoucherVO voucher,
			String strOperator, Boolean op) throws BusinessException, Exception {
		HashMap tempaccsubj = getAccountMap(voucher, null);
		OperationResultVO[] result = new VoucherCheckBO().checkPreparedDate(
				voucher, tempaccsubj);
		if (result != null) {
			StringBuffer strMsg = new StringBuffer();
			boolean errflag = false;
			for (int i = 0; i < result.length; i++) {
				switch (result[i].m_intSuccess) {
				case 0:
					break;
				case 1:
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000119")/*
																			 * @res
																			 * "����:"
																			 */
							+ result[i].m_strDescription + "\n");
					break;
				case 2:
					errflag = true;
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000120")/*
																			 * @res
																			 * "����:"
																			 */
							+ result[i].m_strDescription + "\n");
					break;
				default:
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000121")/*
																			 * @res
																			 * "��Ϣ:"
																			 */
							+ result[i].m_strDescription + "\n");
				}
			}
			if (strMsg.length() > 0 && errflag)
				throw new BusinessException(strMsg.toString());
		}
		if (op.booleanValue()) {
			List<String> allDeatilPks = new LinkedList<String>();
			if (voucher.getNumDetails() > 0) {
				for (int i = 0; i < voucher.getNumDetails(); i++) {
					if (voucher.getDetails()[i].getPk_detail() != null) {
						allDeatilPks
								.add(voucher.getDetails()[i].getPk_detail());
					}
				}
			} else {
				allDeatilPks.addAll(Arrays.asList(new DetailDMO()
						.queryDetailPksByVoucherPk(voucher.getPk_voucher())));
			}
			Set<String> operrationNames = new HashSet<String>();
			String[] pk_details = getRecDetailPKsByVoucherPK(
					new String[] { voucher.getPk_voucher() },
					allDeatilPks.isEmpty() ? null : allDeatilPks
							.toArray(new String[0]), operrationNames);
			if (pk_details != null && pk_details.length != 0)
				throw new BusinessException(
						nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
								"20021005", "UPT2002100573-900055"/**
						 * @res
						 *      "ƾ֤���з�¼��������ҵ�񣬲��������ϡ�"
						 */
						)
								+ (operrationNames.size() > 0 ? operrationNames
										.toString() : ""));
			Boolean isOwnEditable = new Boolean(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.isEditOwnVoucher(voucher.getPk_accountingbook())
					.booleanValue());
			if (isOwnEditable.booleanValue()
					&& !strOperator.equals(voucher.getPk_prepared()))
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000543"/**
								 * @res
								 *      "ƾ֤{0}���趨ֻ���ɱ����޸ģ�����Ȩ���ϱ��˵�ƾ֤��"
								 */
								, null,
								new String[] { voucher.getNo().toString() }));
			else if (voucher.getPk_manager() != null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000544"/**
								 * @res
								 *      "ƾ֤{0}�ѱ����ˣ��������ϡ�������ȡ������Ȼ�������ϸ�ƾ֤��"
								 */
								, null,
								new String[] { voucher.getNo().toString() }));
			else if (voucher.getPk_checked() != null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000545"/**
								 * @res
								 *      "ƾ֤{0}�ѱ���ˣ��������ϡ�������ȡ�����Ȼ�������ϸ�ƾ֤��"
								 */
								, null,
								new String[] { voucher.getNo().toString() }));
			else if (voucher.getPk_casher() != null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000546"/**
								 * @res
								 *      "ƾ֤{0}�ѱ�ǩ�֣��������ϡ�������ȡ��ǩ��Ȼ�������ϸ�ƾ֤��"
								 */
								, null,
								new String[] { voucher.getNo().toString() }));
			else if (voucher.getVoucherkind() != null
					&& voucher.getVoucherkind().intValue() == 1
					&& new VoucherExtendDMO()
							.isExistLaterRegulationVoucher(voucher))
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000547"/**
								 * @res
								 *      "ƾ֤{0}�ǵ�����ƾ֤���������������ڼ��Ѿ��е���ƾ֤�����������ϡ�"
								 */
								, null,
								new String[] { voucher.getNo().toString() }));
			OperationResultVO[] checkresult = null;
			VoucherVO t_voucher = (VoucherVO) voucher.clone();
			t_voucher.setDetails(null);
			// �������
			checkresult = new VoucherCheckBO().checkBalanceControl(t_voucher);
			if (checkresult != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < checkresult.length; i++) {
					switch (checkresult[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ checkresult[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}

			result = OperationResultVO.appendResultVO(result, checkresult);
			//
			checkresult = new VoucherCheckBO().checkAssBalanceControlNew(
					t_voucher, op);
			if (checkresult != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < checkresult.length; i++) {
					switch (checkresult[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ checkresult[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}

			result = OperationResultVO.appendResultVO(result, checkresult);
		} else {
			if (voucher.getVoucherkind() != null
					&& voucher.getVoucherkind().intValue() == 1
					&& new VoucherExtendDMO()
							.isExistLaterRegulationVoucher(voucher))
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000548"/**
								 * @res 
								 *      "ƾ֤{0}�ǵ�����ƾ֤���������������ڼ��Ѿ��е���ƾ֤��������ȡ�����ϡ�"
								 */
								, null,
								new String[] { voucher.getNo().toString() }));
			Boolean isOwnEditable = new Boolean(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.isEditOwnVoucher(voucher.getPk_accountingbook())
					.booleanValue());
			if (isOwnEditable.booleanValue()
					&& !strOperator.equals(voucher.getPk_prepared()))
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000543"/**
								 * @res
								 *      "ƾ֤{0}���趨ֻ���ɱ����޸ģ�����Ȩ���ϱ��˵�ƾ֤��"
								 */
								, null,
								new String[] { voucher.getNo().toString() }));
			VoucherVO t_voucher = (VoucherVO) voucher.clone();
			OperationResultVO[] checkresult = null;
			// �������
			checkresult = new VoucherCheckBO().checkBalanceControl(t_voucher);
			if (checkresult != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < checkresult.length; i++) {
					switch (checkresult[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ checkresult[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}

			result = OperationResultVO.appendResultVO(result, checkresult);

			checkresult = new VoucherCheckBO().checkAssBalanceControlNew(
					t_voucher, op);
			if (checkresult != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < checkresult.length; i++) {
					switch (checkresult[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ checkresult[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}
			result = OperationResultVO.appendResultVO(result, checkresult);
		}

		return result;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-7-18 14:06:11)
	 * 
	 * @param voucher
	 *            nc.voucher.gl.pubvoucher.VoucherVO
	 */
	private OperationResultVO[] checkCanDelete(VoucherVO voucher,
			String strOperator, Boolean isOwnEditable)
			throws BusinessException, Exception {
		OperationResultVO[] result = null;

		List<String> allDeatilPks = new LinkedList<String>();
		if (voucher.getNumDetails() > 0) {
			for (int i = 0; i < voucher.getNumDetails(); i++) {
				if (voucher.getDetails()[i].getPk_detail() != null) {
					allDeatilPks.add(voucher.getDetails()[i].getPk_detail());
				}
			}
		} else {
			allDeatilPks.addAll(Arrays.asList(new DetailDMO()
					.queryDetailPksByVoucherPk(voucher.getPk_voucher())));
		}

		Set<String> operrationNames = new HashSet<String>();
		String[] pk_details = getRecDetailPKsByVoucherPK(
				new String[] { voucher.getPk_voucher(), },
				allDeatilPks.isEmpty() ? null : allDeatilPks
						.toArray(new String[0]), operrationNames);
		if (pk_details != null && pk_details.length != 0)
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes().getStrByID("20021005",
							"UPT2002100573-900042")/*
													 * @res
													 * "ƾ֤���з�¼��������ҵ�񣬲�����ɾ����"
													 */
					+ (operrationNames.size() > 0 ? operrationNames.toString()
							: ""));

		if (isOwnEditable == null) {
			isOwnEditable = new Boolean(NCLocator.getInstance()
					.lookup(IGlPara.class)
					.isEditOwnVoucher(voucher.getPk_accountingbook())
					.booleanValue());
		}
		if (isOwnEditable.booleanValue()
				&& !strOperator.equals(voucher.getPk_prepared()))
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000550"/**
					 * @res
					 *      "ƾ֤{0}���趨ֻ���ɱ����޸ģ�����Ȩɾ�����˵�ƾ֤��"
					 */
					, null, new String[] { voucher.getNo().toString() }));
		else if (voucher.getPk_manager() != null)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000551"/**
					 * @res
					 *      "�ѱ����ˣ�����ɾ����������ȡ������Ȼ����ɾ����ƾ֤��"
					 */
					, null, new String[] { voucher.getNo().toString() }));
		else if (voucher.getPk_checked() != null)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005",
							"UPP20021005-000552"/*
												 * * @res
												 * "�ѱ���ˣ�����ɾ����������ȡ�����Ȼ����ɾ����ƾ֤��"
												 */, null,
							new String[] { voucher.getNo().toString() }));
		else if (voucher.getPk_casher() != null)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000553"/**
					 * @res
					 *      "�ѱ�ǩ�֣�����ɾ����������ȡ��ǩ��Ȼ����ɾ����ƾ֤��"
					 */
					, null, new String[] { voucher.getNo().toString() }));
		else if (voucher.getVoucherkind() != null
				&& voucher.getVoucherkind().intValue() == 1
				&& new VoucherExtendDMO()
						.isExistLaterRegulationVoucher(voucher))
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000554"/**
					 * @res
					 *      "�ǵ�����ƾ֤���������������ڼ��Ѿ��е���ƾ֤��������ɾ����"
					 */
					, null, new String[] { voucher.getNo().toString() }));
		VoucherVO t_voucher = (VoucherVO) voucher.clone();
		t_voucher.setDetails(null);
		OperationResultVO[] checkresult = null;

		// �������
		checkresult = new VoucherCheckBO().checkBalanceControl(t_voucher);
		if (checkresult != null) {
			StringBuffer strMsg = new StringBuffer(16 * 1024);
			boolean errflag = false;
			for (int i = 0; i < checkresult.length; i++) {
				switch (checkresult[i].m_intSuccess) {
				case 0:
					break;
				case 1:
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000119")/*
																			 * @res
																			 * "����:"
																			 */
							+ checkresult[i].m_strDescription + "\n");
					break;
				case 2:
					errflag = true;
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000120")/*
																			 * @res
																			 * "����:"
																			 */
							+ checkresult[i].m_strDescription + "\n");
					break;
				default:
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000121")/*
																			 * @res
																			 * "��Ϣ:"
																			 */
							+ checkresult[i].m_strDescription + "\n");
				}
			}
			if (strMsg.length() > 0 && errflag)
				throw new BusinessException(strMsg.toString());
		}
		result = OperationResultVO.appendResultVO(result, checkresult);
		// �������
		checkresult = new VoucherCheckBO().checkAssBalanceControlNew(t_voucher,
				new Boolean(true));
		if (checkresult != null) {
			StringBuffer strMsg = new StringBuffer(16 * 1024);
			boolean errflag = false;
			for (int i = 0; i < checkresult.length; i++) {
				switch (checkresult[i].m_intSuccess) {
				case 0:
					break;
				case 1:
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000119")/*
																			 * @res
																			 * "����:"
																			 */
							+ checkresult[i].m_strDescription + "\n");
					break;
				case 2:
					errflag = true;
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000120")/*
																			 * @res
																			 * "����:"
																			 */
							+ checkresult[i].m_strDescription + "\n");
					break;
				default:
					strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
							.getStrByID("2002100556", "UPP2002100556-000121")/*
																			 * @res
																			 * "��Ϣ:"
																			 */
							+ checkresult[i].m_strDescription + "\n");
				}
			}
			if (strMsg.length() > 0 && errflag)
				throw new BusinessException(strMsg.toString());
		}
		result = OperationResultVO.appendResultVO(result, checkresult);

		return result;
	}

	private OperationResultVO[] checkSystem(OperationResultVO[] result,
			VoucherVO voucher) {
		if (!(voucher.getPk_system().equalsIgnoreCase("GL") || voucher
				.getPk_system().equalsIgnoreCase("TR"))) {
			OperationResultVO rs = new OperationResultVO();
			rs.m_intSuccess = 2;
			rs.m_strDescription = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("voucherprivate_0", "02002005-0064")/*
																	 * @res
																	 * "������������ƾ֤����ɾ��������"
																	 */;
			result = OperationResultVO.appendResultVO(result,
					new OperationResultVO[] { rs });
		}
		return result;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-7-18 14:06:11)
	 * 
	 * @param voucher
	 *            nc.voucher.gl.pubvoucher.VoucherVO
	 */
	private OperationResultVO[] checkCanSystemTempSave(VoucherVO voucher)
			throws BusinessException, Exception {
		OperationResultVO[] checkresult = null;
		ArrayList<OperationResultVO> ckeck_vec = new ArrayList<OperationResultVO>();

		List<String> allDeatilPks = new LinkedList<String>();
		if (voucher.getNumDetails() > 0) {
			for (int i = 0; i < voucher.getNumDetails(); i++) {
				if (voucher.getDetails()[i].getPk_detail() != null) {
					allDeatilPks.add(voucher.getDetails()[i].getPk_detail());
				}
			}
		} else {
			allDeatilPks.addAll(Arrays.asList(new DetailDMO()
					.queryDetailPksByVoucherPk(voucher.getPk_voucher())));
		}
		Set<String> operrationNames = new HashSet<String>();
		String[] pk_details = getRecDetailPKsByVoucherPK(
				voucher.getPk_voucher() == null ? null
						: new String[] { voucher.getPk_voucher() },
				allDeatilPks.isEmpty() ? null : allDeatilPks
						.toArray(new String[0]), operrationNames);
		if (pk_details != null && pk_details.length != 0)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005",
							"UPT2002100573-900056"/**
					 * @res
					 *      "ƾ֤���з�¼��������ҵ�񣬲�����ϵͳ�ݴ档"
					 */
					)
					+ (operrationNames.size() > 0 ? operrationNames.toString()
							: ""));

		//
		checkError(voucher, null);

		// ������� hurh V60 �ݴ�ƾ֤����������
		if (voucher.getTempsaveflag() != null
				&& voucher.getTempsaveflag().booleanValue()) {
			checkresult = new VoucherCheckBO().checkBalanceControl(voucher);
			if (checkresult != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < checkresult.length; i++) {
					ckeck_vec.add(checkresult[i]);
					switch (checkresult[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ checkresult[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}
			// �������
			OperationResultVO[] checkresult1 = new VoucherCheckBO()
					.checkAssBalanceControlNew(voucher, new Boolean(true));
			if (checkresult1 != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < checkresult1.length; i++) {
					ckeck_vec.add(checkresult1[i]);
					switch (checkresult1[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ checkresult1[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ checkresult1[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ checkresult1[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}
		}

		return ckeck_vec.size() > 0 ? ckeck_vec
				.toArray(new OperationResultVO[0]) : null;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-27 10:51:07)
	 * 
	 * @return java.lang.Boolean
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	// private Boolean checkVoucherNo(VoucherVO voucher) throws
	// BusinessException, Exception {
	// Integer count = new VoucherExtendDMO().getCountVoucherNo(voucher);
	// if (count != null && count.intValue() > 1)
	// throw new BusinessException(new
	// VoucherCheckMessage().getVoucherMessage(VoucherCheckMessage.ErrMsgNOExist));
	// return new Boolean(true);
	// }

	/**
	 * @param voucher
	 * @throws BusinessException
	 */
	public void checkVoucherNumber(VoucherVO voucher) throws BusinessException {
		// getVoucherNumber_RequiresNew(voucher, null, false);
		try {
			updateVoucherNumber(voucher);
		} catch (VoucherNoDuplicateException e) {
			throw new BusinessException(
					new VoucherCheckMessage()
							.getVoucherMessage(VoucherCheckMessage.ErrMsgNOExist));
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-21 10:14:02)
	 * 
	 * @param pks
	 *            java.lang.String[]
	 * @param java
	 *            .lang.Integer
	 */
	private void copy_deletedVoucher(String pk_voucher)
			throws BusinessException, Exception {
		VoucherDMO dmo1 = new VoucherDMO();
		VoucherVO voucher = dmo1.findByPrimaryKey(pk_voucher);
		if (voucher == null)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000555")/*
																				 * @
																				 * res
																				 * "ƾ֤�ѱ�����ɾ����"
																				 */);
		voucher.setFree10(pk_voucher);
		String pk = dmo1.insert(voucher);
		new VoucherExtendDMO().logic_deleteByVoucherPK(pk);

		DetailExtendDMO dmo2 = new DetailExtendDMO();
		DetailVO[] details = dmo2
				.queryByVoucherPks(new String[] { pk_voucher });
		if (details == null || details.length == 0)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000556")/*
																				 * @
																				 * res
																				 * "ƾ֤��ԭʼ�����ѱ��ƻ����޷���ɲ�����"
																				 */);
		for (int i = 0; i < details.length; i++) {
			details[i].setPk_voucher(pk);
		}
		String[] pks = dmo2.insertArray(details);
		dmo2.logic_deleteByVoucherPK(pk);

		String[] oldpks = new String[details.length];
		for (int i = 0; i < details.length; i++) {
			oldpks[i] = details[i].getPk_detail();
		}
		/*
		 * DetailappendDMO dmo3 = new DetailappendDMO(); DetailappendVO[]
		 * detailappends = dmo3.queryByDetailPKs(oldpks); if (detailappends !=
		 * null) for (int i = 0; i < detailappends.length; i++) { for (int j =
		 * 0; j < oldpks.length; j++) { if
		 * (detailappends[i].getPk_detail().equals(oldpks[j])) {
		 * detailappends[i].setPk_detail(pks[j]); break; } } }
		 * dmo3.insertArray(detailappends); dmo3.logic_deleteByVoucherPK(pk);
		 */
	}

	/**
	 * ���߼�ɾ�������ݷŵ�����һ�ű���
	 * 
	 * @param pk_voucher
	 * @throws BusinessException
	 * @throws Exception
	 */
	// private void copy_deletedVoucherNew(String pk_voucher) throws
	// BusinessException, Exception {
	// VoucherDMO voucherDMO = new VoucherDMO();
	// VoucherVO voucher = voucherDMO.findByPrimaryKey(pk_voucher);
	// if (voucher == null)
	// throw new
	// BusinessException(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005",
	// "UPP20021005-000555")/*
	// * @res
	// * "ƾ֤�ѱ�����ɾ����"
	// */);
	// voucher.setFree10(pk_voucher);
	// VoucherBackupDMO voucherBbackupDMO = new VoucherBackupDMO();
	// String pk = voucherBbackupDMO.insert(voucher);
	//
	// DetailExtendDMO detailDMO = new DetailExtendDMO();
	// DetailBackupDMO detailBackupDMO = new DetailBackupDMO();
	// DetailVO[] details = detailDMO.queryByVoucherPks(new String[] {
	// pk_voucher });
	// /**@author zhaozh
	// * @since 5.7 �ݴ���ޱ����ƾ֤�����޸ı���
	// */
	// if (details == null || details.length == 0) {
	// if (voucher.getTempsaveflag() != null &&
	// voucher.getTempsaveflag().booleanValue()) {
	//
	// } else {
	// throw new BusinessException(nc.bs.ml.NCLangResOnserver
	// .getInstance().getStrByID("20021005",
	// "UPP20021005-000556")/*
	// * @res
	// * "ƾ֤��ԭʼ�����ѱ��ƻ����޷���ɲ�����"
	// */);
	// }
	// }else{
	// for (int i = 0; i < details.length; i++) {
	// details[i].setPk_voucher(pk);
	// }
	// detailBackupDMO.insertArray(details);
	// }
	// }

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-21 10:14:02)
	 * 
	 * @param pks
	 *            java.lang.String[]
	 * @param java
	 *            .lang.Integer
	 */
	private String copyVoucher(String pk_voucher) throws BusinessException,
			Exception {
		VoucherDMO dmo1 = new VoucherDMO();
		VoucherVO voucher = dmo1.findByPrimaryKey(pk_voucher);
		if (voucher == null)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000555")/*
																				 * @
																				 * res
																				 * "ƾ֤�ѱ�����ɾ����"
																				 */);
		String pk = dmo1.insert(voucher);

		DetailExtendDMO dmo2 = new DetailExtendDMO();
		DetailVO[] details = dmo2
				.queryByVoucherPks(new String[] { pk_voucher });
		if (details == null || details.length == 0)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000556")/*
																				 * @
																				 * res
																				 * "ƾ֤��ԭʼ�����ѱ��ƻ����޷���ɲ�����"
																				 */);
		for (int i = 0; i < details.length; i++) {
			details[i].setPk_voucher(pk);
		}
		String[] pks = dmo2.insertArray(details);

		String[] oldpks = new String[details.length];
		for (int i = 0; i < details.length; i++) {
			oldpks[i] = details[i].getPk_detail();
		}
		/*
		 * DetailappendDMO dmo3 = new DetailappendDMO(); DetailappendVO[]
		 * detailappends = dmo3.queryByDetailPKs(oldpks); if (detailappends !=
		 * null) for (int i = 0; i < detailappends.length; i++) { for (int j =
		 * 0; j < oldpks.length; j++) { if
		 * (detailappends[i].getPk_detail().equals(oldpks[j])) {
		 * detailappends[i].setPk_detail(pks[j]); break; } } }
		 * dmo3.insertArray(detailappends);
		 */

		return pk;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-21 10:14:02)
	 * 
	 * @param pks
	 *            java.lang.String[]
	 * @param number
	 *            java.lang.Integer
	 */
	public void copyVoucher(VoucherVO voucher, Integer number)
			throws BusinessException {
		try {
			voucher.clearEmptyDetail();
			HashMap tempaccsubj = getAccountMap(voucher, null);
			if (voucher.getPk_voucher() == null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000557")/*
													 * @res "���ܸ�����δ�����ƾ֤��"
													 */);
			else if (voucher.getNumDetails() <= 0)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000558")/*
													 * @res "���Ƶ�ƾ֤��¼����Ϊ�գ�"
													 */);
			else if (voucher.getPk_manager() != null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000559")/*
													 * @res "���������Ѽ��˵�ƾ֤��"
													 */);
			VoucherExtendDMO dmo1 = new VoucherExtendDMO();
			VoucherVO[] vouchers = new VoucherVO[number.intValue()];
			int detailnum = voucher.getNumDetails();
			int no = dmo1.getCorrectVoucherNo(voucher).intValue();
			for (int i = 0; i < number.intValue(); i++) {
				vouchers[i] = (VoucherVO) voucher.clone();
				vouchers[i].setPk_voucher(null);
				vouchers[i].setNo(Integer.valueOf(no + i));
				DetailVO[] details = new DetailVO[detailnum];
				for (int j = 0; j < detailnum; j++) {
					details[j] = (DetailVO) voucher.getDetail(j).clone();
					details[j].setPk_voucher(null);
					details[j].setPk_detail(null);
				}
				vouchers[i].setDetails(details);
			}
			for (int i = 0; i < number.intValue(); i++) {
				saveVoucher(vouchers[i], true, tempaccsubj);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-21 10:14:02)
	 * 
	 * @param pks
	 *            java.lang.String[]
	 * @param number
	 *            java.lang.Integer
	 */
	public void copyVoucherUnChecked(VoucherVO voucher, Integer number)
			throws BusinessException {
		try {
			voucher.clearEmptyDetail();
			if (voucher.getPk_voucher() == null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000557")/*
													 * @res "���ܸ�����δ�����ƾ֤��"
													 */);
			else if (voucher.getNumDetails() <= 0)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000558")/*
													 * @res "���Ƶ�ƾ֤��¼����Ϊ�գ�"
													 */);
			else if (voucher.getPk_manager() != null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000559")/*
													 * @res "���������Ѽ��˵�ƾ֤��"
													 */);
			VoucherExtendDMO dmo1 = new VoucherExtendDMO();
			VoucherVO[] vouchers = new VoucherVO[number.intValue()];
			int detailnum = voucher.getNumDetails();
			int no = dmo1.getCorrectVoucherNo(voucher).intValue();
			for (int i = 0; i < number.intValue(); i++) {
				vouchers[i] = (VoucherVO) voucher.clone();
				vouchers[i].setPk_voucher(null);
				vouchers[i].setNo(Integer.valueOf(no + i + 1));
			}
			String[] pks = dmo1.insertArray(vouchers);
			DetailVO[] details = new DetailVO[number.intValue() * detailnum];
			for (int i = 0; i < number.intValue(); i++) {
				for (int j = 0; j < detailnum; j++) {
					details[i * detailnum + j] = (DetailVO) voucher
							.getDetail(j).clone();
					details[i * detailnum + j].setPk_voucher(pks[i]);
					details[i * detailnum + j].setPk_detail(null);
				}
			}
			DetailExtendDMO dmo2 = new DetailExtendDMO();
			dmo2.saveArray(details);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-12-21 10:14:02)
	 * 
	 * @param pks
	 *            java.lang.String[]
	 * @param java
	 *            .lang.Integer
	 */
	private void copyVoucherVO(VoucherVO voucher) throws BusinessException,
			Exception {
		VoucherExtendDMO dmo1 = new VoucherExtendDMO();
		VoucherVO[] vouchers = new VoucherVO[1];
		int detailnum = voucher.getNumDetails();
		int no = dmo1.getCorrectVoucherNo(voucher).intValue();
		for (int i = 0; i < 1; i++) {
			vouchers[i] = (VoucherVO) voucher.clone();
			vouchers[i].setPk_voucher(null);
			vouchers[i].setNo(Integer.valueOf(no + i + 1));
		}
		String[] pks = dmo1.insertArray(vouchers);
		DetailVO[] details = new DetailVO[1 * detailnum];
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < detailnum; j++) {
				details[i * detailnum + j] = (DetailVO) voucher.getDetail(j)
						.clone();
				details[i * detailnum + j].setPk_voucher(pks[i]);
				details[i * detailnum + j].setPk_detail(null);
			}
		}
		DetailExtendDMO dmo2 = new DetailExtendDMO();
		dmo2.saveArray(details);
	}

	/**
	 * �������������ݿ���ɾ��һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param key
	 *            String
	 * @BusinessException BusinessException �쳣˵����
	 */
	public OperationResultVO[] delete(VoucherVO voucher)
			throws BusinessException {
		try {
			OperationResultVO[] result = null;
			String strPk_voucher = voucher.getPk_voucher();
			String strOperator = voucher.getPk_prepared();
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			DetailExtendDMO dmo1 = new DetailExtendDMO();
			DtlfreevalueDMO dmo3 = new DtlfreevalueDMO();

			VoucherVO vo = dmo.queryByVoucherPk(strPk_voucher);
			strOperator = voucher.getPk_prepared();
			if (vo == null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000538")/*
													 * @res "ƾ֤�ѱ�����ɾ������ˢ�����ݡ�"
													 */);
			else {
				result = checkCanDelete(vo, strOperator, null);
			}
			GLKeyLock lock = null;
			boolean bLockSuccess = false;
			try {
				// nc.bs.pub.lock.LockHome home = (nc.bs.pub.lock.LockHome)
				// getBeanHome(nc.bs.pub.lock.LockHome.class,
				// "nc.bs.pub.lock.LockBO");
				// if (home == null)
				// throw new ClassNotFoundException("nc.bs.pub.lock.LockHome");
				lock = new GLKeyLock();
				for (int i = 0; i < 5; i++) {
					bLockSuccess = lock.lockKey(strPk_voucher, strOperator,
							"gl_voucher");
					if (bLockSuccess)
						break;
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
				if (!bLockSuccess)
					throw new BusinessException(nc.bs.ml.NCLangResOnserver
							.getInstance().getStrByID("20021005",
									"UPP20021005-000539")/*
														 * @res
														 * "�������û��ڲ��������Ժ����ԡ�"
														 */);

				DealclassDMO dealclassdmo = new DealclassDMO();
				DealclassVO[] dealclassvos = dealclassdmo
						.queryByModulgroup("delete");
				Vector vecdeleteclass = new Vector();
				VoucherOperateInterfaceVO opvo = new VoucherOperateInterfaceVO();
				opvo.pk_vouchers = new String[] { strPk_voucher };
				opvo.pk_user = strOperator;
				opvo.userdata = voucher.getUserData();
				if (dealclassvos != null && dealclassvos.length != 0)
					for (int m = 0; m < dealclassvos.length; m++) {
						if (dealclassvos[m].getModules() != null) {
							try {
								nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) ObjectCreator
										.newInstance(
												dealclassvos[m].getModules(),
												dealclassvos[m].getClassname());
								vecdeleteclass.addElement(m_deleteclassall);
							} catch (FrameworkRuntimeException e) {
								Logger.error(e.getMessage(), e);
								throw new BusinessException(e.getMessage(), e);
							}
						}
						/*
						 * try { Class m_classdelete =
						 * java.lang.Class.forName(dealclassvos
						 * [m].getClassname()); Object m_objectdelete =
						 * m_classdelete.newInstance();
						 * nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall
						 * = (nc.bs.gl.pubinterface.IVoucherDelete)
						 * m_objectdelete;
						 * vecdeleteclass.addElement(m_deleteclassall); } catch
						 * (ClassNotFoundException e) { } catch
						 * (NoClassDefFoundError e) { continue; }
						 */
					}

				if (vo.getDeleteclass() != null
						&& !vo.getDeleteclass().trim().equals("")) {
					try {
						Class m_classdelete = java.lang.Class.forName(vo
								.getDeleteclass());
						Object m_objectdelete = m_classdelete.newInstance();
						nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) m_objectdelete;
						vecdeleteclass.addElement(m_deleteclassall);
					} catch (ClassNotFoundException e) {
					} catch (NoClassDefFoundError e) {
					}
				}
				for (int m = 0; m < vecdeleteclass.size(); m++) {
					OperationResultVO[] t_result1 = ((nc.bs.gl.pubinterface.IVoucherDelete) vecdeleteclass
							.elementAt(m)).beforeDelete(opvo);
					result = OperationResultVO
							.appendResultVO(result, t_result1);
				}

				// ƾ֤ɾ��
				dmo.logic_deleteByVoucherPK(strPk_voucher);
				dmo1.logic_deleteByVoucherPK(strPk_voucher);
				dmo3.logic_deleteByVoucherPK(strPk_voucher);
				// return voucher number
				dmo.returnVoucherNoByDelete(vo);

				for (int m = vecdeleteclass.size(); m > 0; m--) {
					OperationResultVO[] t_result1 = ((nc.bs.gl.pubinterface.IVoucherDelete) vecdeleteclass
							.elementAt(m - 1)).afterDelete(opvo);
					result = OperationResultVO
							.appendResultVO(result, t_result1);
				}
			} finally {
				if (bLockSuccess)
					lock.freeKey(strPk_voucher, strOperator, "gl_voucher");
			}
			return result;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * �������������ݿ���ɾ��һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param key
	 *            String
	 * @BusinessException BusinessException �쳣˵����
	 */
	public OperationResultVO[] deleteByPk(String strPk_voucher)
			throws BusinessException {
		OperationResultVO[] result = null;

		GLKeyLock lock = null;
		boolean bLockSuccess = false;
		try {
			lock = new GLKeyLock();
			for (int i = 0; i < 5; i++) {
				bLockSuccess = lock.lockKey(strPk_voucher, null, "gl_voucher");
				if (bLockSuccess)
					break;
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}
			if (!bLockSuccess)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000539")/*
													 * @res "�������û��ڲ��������Ժ����ԡ�"
													 */);
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			VoucherVO vo = dmo.queryByVoucherPk(strPk_voucher);
			DetailExtendDMO dmo1 = new DetailExtendDMO();
			if (vo == null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000538")/*
													 * @res "ƾ֤�ѱ�����ɾ������ˢ�����ݡ�"
													 */);
			else {
				result = checkCanDelete(vo, vo.getPk_prepared(), new Boolean(
						false));
			}
			DealclassDMO dealclassdmo = new DealclassDMO();
			DealclassVO[] dealclassvos = dealclassdmo
					.queryByModulgroup("delete");
			Vector vecdeleteclass = new Vector();
			VoucherOperateInterfaceVO opvo = new VoucherOperateInterfaceVO();
			opvo.pk_vouchers = new String[] { strPk_voucher };
			OperationResultVO[] t_result1 = null;
			if (dealclassvos != null && dealclassvos.length != 0)
				for (int m = 0; m < dealclassvos.length; m++) {
					if (dealclassvos[m].getModules() != null) {
						try {
							nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) ObjectCreator
									.newInstance(dealclassvos[m].getModules(),
											dealclassvos[m].getClassname());
							t_result1 = m_deleteclassall.beforeDelete(opvo);
							result = OperationResultVO.appendResultVO(result,
									t_result1);
							vecdeleteclass.addElement(m_deleteclassall);
						} catch (FrameworkRuntimeException e) {
							Logger.error(e.getMessage(), e);
							throw new BusinessException(e.getMessage(), e);
						}
					}
					/*
					 * try { Class m_classdelete =
					 * java.lang.Class.forName(dealclassvos[m].getClassname());
					 * Object m_objectdelete = m_classdelete.newInstance();
					 * nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall =
					 * (nc.bs.gl.pubinterface.IVoucherDelete) m_objectdelete;
					 * m_deleteclassall.beforeDelete(opvo);
					 * vecdeleteclass.addElement(m_deleteclassall); } catch
					 * (ClassNotFoundException e) { } catch
					 * (NoClassDefFoundError e) { continue; }
					 */
				}
			if (vo.getDeleteclass() != null
					&& !vo.getDeleteclass().trim().equals("")) {
				try {
					Class m_classdelete = java.lang.Class.forName(vo
							.getDeleteclass());
					Object m_objectdelete = m_classdelete.newInstance();
					nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) m_objectdelete;
					t_result1 = m_deleteclassall.beforeDelete(opvo);
					result = OperationResultVO
							.appendResultVO(result, t_result1);
					vecdeleteclass.addElement(m_deleteclassall);
				} catch (ClassNotFoundException e) {
				} catch (NoClassDefFoundError e) {
				}
			}

			// hurh
			boolean errflag = false;
			if (result != null) {
				for (int i = 0; i < result.length; i++) {
					switch (result[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						break;
					case 2:
						errflag = true;
						break;
					default:
					}
				}
			}

			if (!errflag) {
				// DetailappendDMO dmo2 = new DetailappendDMO();
				DtlfreevalueDMO dmo3 = new DtlfreevalueDMO();
				// dmo.logic_deleteByVoucherPK(strPk_voucher);
				// dmo1.logic_deleteByVoucherPK(strPk_voucher);
				dmo3.deleteByVoucherPK(strPk_voucher);

				VchfreevalueDMO vfdmo = new VchfreevalueDMO();
				vfdmo.deleteByVoucherPK(strPk_voucher);

				dmo.deleteByVoucherPK(strPk_voucher);
				dmo1.deleteByVoucherPK(strPk_voucher);
				// dmo2.logic_deleteByVoucherPK(strPk_voucher);
				// dmo3.logic_deleteByVoucherPK(strPk_voucher);
				dmo.returnVoucherNoByDelete(vo);
				GLBusiLogUtil.insertSmartBusiLog(vo, null,
						GLMetaDataIDConst.ID_DETAIL,
						GLBusiLogOperateConst.CODE_DELETE);
				// ˰����ϸ
				NCLocator
						.getInstance()
						.lookup(IVatDetailMngService.class)
						.deleteVatDetailByVouchers(
								new String[] { strPk_voucher });
			}
			for (int m = vecdeleteclass.size(); m > 0; m--) {
				t_result1 = ((nc.bs.gl.pubinterface.IVoucherDelete) vecdeleteclass
						.elementAt(m - 1)).afterDelete(opvo);
				result = OperationResultVO.appendResultVO(result, t_result1);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
			// log.error(e);
			// throw new
			// BusinessException("VoucherBO::delete(VoucherPK) BusinessException!",
			// e);
		} finally {
			if (bLockSuccess)
				lock.freeKey(strPk_voucher, null, "gl_voucher");
			// try
			// {
			// lock.remove();
			// }
			// catch (BusinessException e)
			// {
			// Logger.error(e.getMessage(), e);
			// }
		}

		// hurh �ӿ�ʵ�����е�û������ƾ֤pk������
		if (result != null) {
			for (OperationResultVO r : result) {
				if (r.m_strPK == null) {
					r.m_strPK = strPk_voucher;
				}
			}
		}

		return result;
	}

	/**
	 * �������������ݿ���ɾ��һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param key
	 *            String
	 * @BusinessException BusinessException �쳣˵����
	 */
	public OperationResultVO[] deleteByPks(String[] strPk_vouchers,
			Boolean isneedsyscheck, Map<String, String> pk_voucher2org)
			throws BusinessException {
		OperationResultVO[] result = null;
		try {
			for (int i = 0; i < strPk_vouchers.length; i++) {
				result = OperationResultVO.appendResultVO(
						result,
						deleteByPk(strPk_vouchers[i], isneedsyscheck,
								pk_voucher2org));
			}

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * �������������ݿ���ɾ��һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param key
	 *            String
	 * @BusinessException BusinessException �쳣˵����
	 */
	public OperationResultVO[] deleteReverseVoucherByPk(String strPk_voucher)
			throws BusinessException {
		OperationResultVO[] result = null;

		GLKeyLock lock = null;
		boolean bLockSuccess = false;
		try {
			lock = new GLKeyLock();
			for (int i = 0; i < 5; i++) {
				bLockSuccess = lock.lockKey(strPk_voucher, null, "gl_voucher");
				if (bLockSuccess)
					break;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			if (!bLockSuccess)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000539")/*
													 * @res "�������û��ڲ��������Ժ����ԡ�"
													 */);
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			VoucherVO vo = dmo.queryByVoucherPk(strPk_voucher);
			DetailExtendDMO dmo1 = new DetailExtendDMO();
			if (vo == null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000538")/*
													 * @res "ƾ֤�ѱ�����ɾ������ˢ�����ݡ�"
													 */);
			else {
				result = checkCanDelete(vo, vo.getPk_prepared(), new Boolean(
						false));
			}
			Vector vecdeleteclass = new Vector();
			VoucherOperateInterfaceVO opvo = new VoucherOperateInterfaceVO();
			opvo.pk_vouchers = new String[] { strPk_voucher };
			OperationResultVO[] t_result1 = null;
			DealclassDMO dealclassdmo = new DealclassDMO();
			DealclassVO[] dealclassvos = dealclassdmo
					.queryByModulgroup("delete");
			if (dealclassvos != null && dealclassvos.length != 0) {
				for (int m = 0; m < dealclassvos.length; m++) {

					if (dealclassvos[m].getModules() != null) {
						try {
							nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) ObjectCreator
									.newInstance(dealclassvos[m].getModules(),
											dealclassvos[m].getClassname());
							t_result1 = m_deleteclassall.beforeDelete(opvo);
							result = OperationResultVO.appendResultVO(result,
									t_result1);
							vecdeleteclass.addElement(m_deleteclassall);
						} catch (FrameworkRuntimeException e) {
							Logger.error(e.getMessage(), e);
							throw new BusinessException(e.getMessage(), e);
						}
					}
				}
			}
			// hurh
			boolean errflag = false;
			if (result != null) {
				for (int i = 0; i < result.length; i++) {
					switch (result[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						break;
					case 2:
						errflag = true;
						break;
					default:
					}
				}
			}

			if (!errflag) {
				// DetailappendDMO dmo2 = new DetailappendDMO();
				DtlfreevalueDMO dmo3 = new DtlfreevalueDMO();
				dmo3.deleteByVoucherPK(strPk_voucher);
				VchfreevalueDMO vfdmo = new VchfreevalueDMO();
				vfdmo.deleteByVoucherPK(strPk_voucher);
				dmo.deleteByVoucherPK(strPk_voucher);
				dmo1.deleteByVoucherPK(strPk_voucher);
				// dmo.logic_deleteByVoucherPK(strPk_voucher);
				// dmo1.logic_deleteByVoucherPK(strPk_voucher);
				// dmo2.logic_deleteByVoucherPK(strPk_voucher);
				// dmo3.logic_deleteByVoucherPK(strPk_voucher);
				dmo.returnVoucherNoByDelete(vo);
				GLBusiLogUtil.insertSmartBusiLog(vo, null,
						GLMetaDataIDConst.ID_DETAIL,
						GLBusiLogOperateConst.CODE_DELETE);
				// ˰����ϸ
				NCLocator
						.getInstance()
						.lookup(IVatDetailMngService.class)
						.deleteVatDetailByVouchers(
								new String[] { strPk_voucher });
			}
			for (int m = vecdeleteclass.size(); m > 0; m--) {
				t_result1 = ((nc.bs.gl.pubinterface.IVoucherDelete) vecdeleteclass
						.elementAt(m - 1)).afterDelete(opvo);
				result = OperationResultVO.appendResultVO(result, t_result1);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		} finally {
			if (bLockSuccess)
				lock.freeKey(strPk_voucher, null, "gl_voucher");
			// try
			// {
			// lock.remove();
			// }
			// catch (BusinessException e)
			// {
			// Logger.error(e.getMessage(), e);
			// }
		}

		// hurh �ӿ�ʵ�����е�û������ƾ֤pk������
		if (result != null) {
			for (OperationResultVO r : result) {
				if (r.m_strPK == null) {
					r.m_strPK = strPk_voucher;
				}
			}
		}

		return result;
	}

	/**
	 * �������������ݿ���ɾ��һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param key
	 *            String
	 * @BusinessException BusinessException �쳣˵����
	 */
	public OperationResultVO[] deleteByPk(String strPk_voucher,
			Boolean isneedsyscheck, Map<String, String> pk_voucher2org)
			throws BusinessException {
		OperationResultVO[] result = null;

		GLKeyLock lock = null;
		boolean bLockSuccess = false;
		try {
			// nc.bs.pub.lock.LockHome home = (nc.bs.pub.lock.LockHome)
			// getBeanHome(nc.bs.pub.lock.LockHome.class,
			// "nc.bs.pub.lock.LockBO");
			// if (home == null)
			// throw new ClassNotFoundException("nc.bs.pub.lock.LockHome");
			lock = new GLKeyLock();
			for (int i = 0; i < 5; i++) {
				bLockSuccess = lock.lockKey(strPk_voucher, null, "gl_voucher");
				if (bLockSuccess)
					break;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			if (!bLockSuccess)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000539")/*
													 * @res "�������û��ڲ��������Ժ����ԡ�"
													 */);
			if (pk_voucher2org != null) {
				// ���Ҫ���͵�����ϵͳ���������ֱ�ӷ���
				boolean sendOtherSys = NCLocator.getInstance()
						.lookup(IVoucherPfxxService.class)
						.isSendOtherSys(pk_voucher2org.get(strPk_voucher));
				if (sendOtherSys) {
					return result;
				}
			}

			VoucherExtendDMO dmo = new VoucherExtendDMO();
			VoucherVO vo = dmo.queryByVoucherPk(strPk_voucher);
			DetailExtendDMO dmo1 = new DetailExtendDMO();
			if (vo == null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000538")/*
													 * @res "ƾ֤�ѱ�����ɾ������ˢ�����ݡ�"
													 */);
			else {
				result = checkCanDelete(vo, vo.getPk_prepared(), new Boolean(
						false));
			}
			Vector vecdeleteclass = new Vector();
			VoucherOperateInterfaceVO opvo = new VoucherOperateInterfaceVO();
			opvo.pk_vouchers = new String[] { strPk_voucher };
			OperationResultVO[] t_result1 = null;
			if (isneedsyscheck.booleanValue()) {
				DealclassDMO dealclassdmo = new DealclassDMO();
				DealclassVO[] dealclassvos = dealclassdmo
						.queryByModulgroup("delete");
				if (dealclassvos != null && dealclassvos.length != 0)
					for (int m = 0; m < dealclassvos.length; m++) {

						if (dealclassvos[m].getModules() != null) {
							try {
								nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) ObjectCreator
										.newInstance(
												dealclassvos[m].getModules(),
												dealclassvos[m].getClassname());
								t_result1 = m_deleteclassall.beforeDelete(opvo);
								result = OperationResultVO.appendResultVO(
										result, t_result1);
								vecdeleteclass.addElement(m_deleteclassall);
							} catch (FrameworkRuntimeException e) {
								Logger.error(e.getMessage(), e);
								throw new BusinessException(e.getMessage(), e);
							}
						}
						/*
						 * try { Class m_classdelete =
						 * java.lang.Class.forName(dealclassvos
						 * [m].getClassname()); Object m_objectdelete =
						 * m_classdelete.newInstance();
						 * nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall
						 * = (nc.bs.gl.pubinterface.IVoucherDelete)
						 * m_objectdelete; m_deleteclassall.beforeDelete(opvo);
						 * vecdeleteclass.addElement(m_deleteclassall); } catch
						 * (ClassNotFoundException e) { } catch
						 * (NoClassDefFoundError e) { continue; }
						 */
					}
				if (vo.getDeleteclass() != null
						&& !vo.getDeleteclass().trim().equals("")) {
					try {
						Class m_classdelete = java.lang.Class.forName(vo
								.getDeleteclass());
						Object m_objectdelete = m_classdelete.newInstance();
						nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) m_objectdelete;
						t_result1 = m_deleteclassall.beforeDelete(opvo);
						result = OperationResultVO.appendResultVO(result,
								t_result1);
						vecdeleteclass.addElement(m_deleteclassall);
					} catch (Exception e) {
						Logger.error(e.getMessage(), e);
						throw new BusinessException(e.getMessage(), e);
					}
				}
			}

			// hurh
			boolean errflag = false;
			if (result != null) {
				for (int i = 0; i < result.length; i++) {
					switch (result[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						break;
					case 2:
						errflag = true;
						break;
					default:
					}
				}
			}

			if (!errflag) {
				// DetailappendDMO dmo2 = new DetailappendDMO();
				DtlfreevalueDMO dmo3 = new DtlfreevalueDMO();
				dmo3.deleteByVoucherPK(strPk_voucher);
				VchfreevalueDMO vfdmo = new VchfreevalueDMO();
				vfdmo.deleteByVoucherPK(strPk_voucher);
				dmo.deleteByVoucherPK(strPk_voucher);
				dmo1.deleteByVoucherPK(strPk_voucher);
				// dmo.logic_deleteByVoucherPK(strPk_voucher);
				// dmo1.logic_deleteByVoucherPK(strPk_voucher);
				// dmo2.logic_deleteByVoucherPK(strPk_voucher);
				// dmo3.logic_deleteByVoucherPK(strPk_voucher);
				dmo.returnVoucherNoByDelete(vo);
				GLBusiLogUtil.insertSmartBusiLog(vo, null,
						GLMetaDataIDConst.ID_DETAIL,
						GLBusiLogOperateConst.CODE_DELETE);
				// ˰����ϸ
				NCLocator
						.getInstance()
						.lookup(IVatDetailMngService.class)
						.deleteVatDetailByVouchers(
								new String[] { strPk_voucher });
			}
			if (isneedsyscheck.booleanValue()) {
				for (int m = vecdeleteclass.size(); m > 0; m--) {
					t_result1 = ((nc.bs.gl.pubinterface.IVoucherDelete) vecdeleteclass
							.elementAt(m - 1)).afterDelete(opvo);
					result = OperationResultVO
							.appendResultVO(result, t_result1);
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		} finally {
			if (bLockSuccess)
				lock.freeKey(strPk_voucher, null, "gl_voucher");
			// try
			// {
			// lock.remove();
			// }
			// catch (BusinessException e)
			// {
			// Logger.error(e.getMessage(), e);
			// }
		}

		// hurh �ӿ�ʵ�����е�û������ƾ֤pk������
		if (result != null) {
			for (OperationResultVO r : result) {
				if (r.m_strPK == null) {
					r.m_strPK = strPk_voucher;
				}
			}
		}

		return result;
	}

	/**
	 * �������������ݿ���ɾ��һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param key
	 *            String
	 * @BusinessException BusinessException �쳣˵����
	 */
	private OperationResultVO[] deleteByPk(String strPk_voucher,
			String strOperator, Boolean isOwnEditable) throws Exception {

		OperationResultVO[] result = null;

		VoucherExtendDMO dmo = new VoucherExtendDMO();
		DetailExtendDMO dmo1 = new DetailExtendDMO();
		// DetailappendDMO dmo2 = new DetailappendDMO();
		VoucherVO vo = dmo.queryByVoucherPk(strPk_voucher);
		if (vo == null)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000538")/*
																				 * @
																				 * res
																				 * "ƾ֤�ѱ�����ɾ������ˢ�����ݡ�"
																				 */);
		else {
			result = checkCanDelete(vo, strOperator, isOwnEditable);
		}
		GLKeyLock lock = null;
		boolean bLockSuccess = false;
		try {
			// nc.bs.pub.lock.LockHome home = (nc.bs.pub.lock.LockHome)
			// getBeanHome(nc.bs.pub.lock.LockHome.class,
			// "nc.bs.pub.lock.LockBO");
			// if (home == null)
			// throw new ClassNotFoundException("nc.bs.pub.lock.LockHome");
			lock = new GLKeyLock();
			for (int i = 0; i < 5; i++) {
				bLockSuccess = lock.lockKey(strPk_voucher, strOperator,
						"gl_voucher");
				if (bLockSuccess)
					break;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			if (!bLockSuccess)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000539")/*
													 * @res "�������û��ڲ��������Ժ����ԡ�"
													 */);
			DealclassDMO dealclassdmo = new DealclassDMO();
			DealclassVO[] dealclassvos = dealclassdmo
					.queryByModulgroup("delete");
			Vector vecdeleteclass = new Vector();
			OperationResultVO[] t_result1 = null;
			if (dealclassvos != null && dealclassvos.length != 0)
				for (int m = 0; m < dealclassvos.length; m++) {
					if (dealclassvos[m].getModules() != null) {
						try {
							nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) ObjectCreator
									.newInstance(dealclassvos[m].getModules(),
											dealclassvos[m].getClassname());
							VoucherOperateInterfaceVO opvo = new VoucherOperateInterfaceVO();
							opvo.pk_vouchers = new String[] { strPk_voucher };
							opvo.pk_user = strOperator;
							t_result1 = m_deleteclassall.beforeDelete(opvo);
							if (t_result1 != null && t_result1.length > 0) {
								Logger.debug("*******************************************�ӿڣ�"
										+ m_deleteclassall.getClass());
							}
							result = OperationResultVO.appendResultVO(result,
									t_result1);
							vecdeleteclass.addElement(m_deleteclassall);
						} catch (Exception e) {
							Logger.error(e.getMessage(), e);
							throw new Exception(e.getMessage(), e);
						}
					}
					/*
					 * try { Class m_classdelete =
					 * java.lang.Class.forName(dealclassvos[m].getClassname());
					 * Object m_objectdelete = m_classdelete.newInstance();
					 * nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall =
					 * (nc.bs.gl.pubinterface.IVoucherDelete) m_objectdelete;
					 * VoucherOperateInterfaceVO opvo = new
					 * VoucherOperateInterfaceVO(); opvo.pk_vouchers = new
					 * String[] { strPk_voucher }; opvo.pk_user = strOperator;
					 * m_deleteclassall.beforeDelete(opvo);
					 * vecdeleteclass.addElement(m_deleteclassall); } catch
					 * (ClassNotFoundException e) { } catch
					 * (NoClassDefFoundError e) { continue; }
					 */
				}

			if (vo.getDeleteclass() != null
					&& !vo.getDeleteclass().trim().equals("")) {
				try {
					Class m_classdelete = java.lang.Class.forName(vo
							.getDeleteclass());
					Object m_objectdelete = m_classdelete.newInstance();
					nc.bs.gl.pubinterface.IVoucherDelete m_deleteclassall = (nc.bs.gl.pubinterface.IVoucherDelete) m_objectdelete;
					VoucherOperateInterfaceVO opvo = new VoucherOperateInterfaceVO();
					opvo.pk_vouchers = new String[] { strPk_voucher };
					opvo.pk_user = strOperator;
					t_result1 = m_deleteclassall.beforeDelete(opvo);
					if (t_result1 != null && t_result1.length > 0) {
						Logger.debug("*******************************************�ӿڣ�"
								+ m_deleteclassall.getClass());
					}
					result = OperationResultVO
							.appendResultVO(result, t_result1);
					vecdeleteclass.addElement(m_deleteclassall);
				} catch (Exception e) {
					Logger.error(e.getMessage(), e);
					throw new Exception(e.getMessage(), e);
				}
			}

			// hurh
			boolean errflag = false;
			if (result != null) {
				for (int i = 0; i < result.length; i++) {
					switch (result[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						break;
					case 2:
						errflag = true;
						break;
					default:
					}
				}
			}

			if (!errflag) {
				DtlfreevalueDMO dmo3 = new DtlfreevalueDMO();

				// copy_deletedVoucherNew(strPk_voucher);//����
				dmo3.deleteByVoucherPK(strPk_voucher);// ɾ����¼�Զ�����
				// ɾ��ƾ֤�Զ�����
				VchfreevalueDMO vfdmo = new VchfreevalueDMO();
				vfdmo.deleteByVoucherPK(strPk_voucher);

				dmo1.deleteByVoucherPK(strPk_voucher);// ɾ���ӱ�
				dmo.deleteByVoucherPK(strPk_voucher);// ɾ������

				// dmo.logic_deleteByVoucherPK(strPk_voucher);
				// dmo1.logic_deleteByVoucherPK(strPk_voucher);
				// // dmo2.logic_deleteByVoucherPK(strPk_voucher);
				// dmo3.logic_deleteByVoucherPK(strPk_voucher);

				dmo.returnVoucherNoByDelete(vo);
				GLBusiLogUtil.insertSmartBusiLog(vo, null,
						GLMetaDataIDConst.ID_DETAIL,
						GLBusiLogOperateConst.CODE_DELETE);
				// ˰����ϸ
				NCLocator
						.getInstance()
						.lookup(IVatDetailMngService.class)
						.deleteVatDetailByVouchers(
								new String[] { strPk_voucher });
			}

			for (int m = vecdeleteclass.size(); m > 0; m--) {
				VoucherOperateInterfaceVO opvo = new VoucherOperateInterfaceVO();
				opvo.pk_vouchers = new String[] { strPk_voucher };
				opvo.pk_user = strOperator;
				t_result1 = ((nc.bs.gl.pubinterface.IVoucherDelete) vecdeleteclass
						.elementAt(m - 1)).afterDelete(opvo);
				if (t_result1 != null && t_result1.length > 0) {
					Logger.debug("*******************************************�ӿڣ�"
							+ ((nc.bs.gl.pubinterface.IVoucherDelete) vecdeleteclass
									.elementAt(m - 1)).getClass());
				}
				result = OperationResultVO.appendResultVO(result, t_result1);
			}
		} finally {
			if (bLockSuccess)
				lock.freeKey(strPk_voucher, strOperator, "gl_voucher");
			// try
			// {
			// // lock.remove();
			// }
			// catch (BusinessException e)
			// {
			// Logger.error(e.getMessage(), e);
			// }
		}

		// hurh �ӿ�ʵ�����е�û������ƾ֤pk������
		if (result != null) {
			for (OperationResultVO r : result) {
				if (r.m_strPK == null) {
					r.m_strPK = strPk_voucher;
				}
			}
		}

		return result;
	}

	/**
	 * �������������ݿ���ɾ��һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param key
	 *            String
	 * @BusinessException BusinessException �쳣˵����
	 */
	public OperationResultVO[] deleteByPks(String[] strPk_voucher,
			String strOperator) throws BusinessException {
		OperationResultVO[] result = null;
		try {

			

			// ����ƾ֤���û�дԴƾ֤�Ĳ����� modify By liyongru for V55 at 20081103
			// this.updateSrcVoucherDifFlag(strPk_voucher);

			// ���ͬʱѡ��ԭƾ֤�ͳ��ƾ֤ɾ������ʾ��ƾ֤�ѱ�����ɾ�������κ�һ��ƾ֤��ɾ������������Ҫ����һ�ι���
			Map<String, String> map = NCLocator.getInstance()
					.lookup(ITransferHistoryPrv.class)
					.queryWriteoffVouchersMap(strPk_voucher);
			for (int i = 0; i < strPk_voucher.length; i++) {
				if (strPk_voucher[i] != null) {
					String writeoffpk = map.get(strPk_voucher[i]);
					if (writeoffpk != null) {
						for (int j = 0; j < strPk_voucher.length; j++) {
							if (strPk_voucher[j] != null
									&& strPk_voucher[j].equals(writeoffpk)) {
								strPk_voucher[j] = null;
								break;
							}
						}
					}
				}
			}

			VoucherVO[] voucherVOs = queryByPks(strPk_voucher);
			
			for (int i = 0; i < strPk_voucher.length; i++) {
				if (strPk_voucher[i] != null) {
					result = OperationResultVO.appendResultVO(result,
							deleteByPk(strPk_voucher[i], strOperator, null));
				}
			}

			// ����ɾ������-shidl
			// ���Ҫ���͵�����ϵͳ���������ֱ�ӷ���
			if (voucherVOs != null && voucherVOs.length > 0) {
				for (VoucherVO voucher : voucherVOs) {
					boolean sendOtherSys = NCLocator.getInstance()
							.lookup(IVoucherPfxxService.class)
							.isSendOtherSys(voucher.getPk_org());
					if (sendOtherSys) {
						NCLocator.getInstance()
								.lookup(IVoucherPfxxService.class)
								.delVoucher(voucher);
					} else {
						continue;
					}
				}
			}

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * �������Ŀ���ʲ����ƾ֤��ɾ���� �������Щ�����ʲ������Դƾ֤�Ĳ��컯���ҲӦ�����,��Ϊ�ǲ��컯
	 * 
	 * @param des_vouchers
	 * @throws BusinessException
	 */
	private void updateSrcVoucherDifFlag(String[] des_vouchers)
			throws BusinessException {
		if (des_vouchers == null) {
			return;
		}
		FormulaParseFather fp = new FormulaParse();
		Vector<String> src_vouchers = new Vector<String>();
		String tmp = "";
		String tmp_glorgbook = "";
		if (des_vouchers.length > 0) {
			Hashtable<String, String> map = new Hashtable<String, String>();
			/** ����Ƿ��ǲ��컯 */
			String glorgbook = "srcvoucherpk->getColValue(gl_voucher,pk_glorgbook,pk_voucher,voucherkey)";
			map.put("voucherkey", des_vouchers[0]);
			fp.setExpress(glorgbook);
			fp.setData(map);
			tmp_glorgbook = fp.getValue();
			String isdifflag = "isdifflag->getColValue(gl_soblink,isdifflag,pk_desbook,desbookkey)";
			map.put("desbookkey", tmp_glorgbook);
			fp.setExpress(isdifflag);
			fp.setData(map);
			tmp = fp.getValue();
			if (tmp != null) {
				if (tmp.trim().equals("Y")) {
					String[] pk_vouchers = this.querySrcVouchersBydesVouchers(
							des_vouchers, tmp_glorgbook);
					if (pk_vouchers != null) {
						this.updateVoucherDifflag(pk_vouchers,
								nc.vo.pub.lang.UFBoolean.FALSE);
					}
				}
			}
		}
	}

	/**
	 * ����Ŀ��ƾ֤��ȥ����������Դƾ֤��
	 * 
	 * @param des_vouchers
	 * @param glorgbook
	 * @return
	 * @throws BusinessException
	 */
	private String[] querySrcVouchersBydesVouchers(String[] des_vouchers,
			String glorgbook) throws BusinessException {
		String[] src_vouchers = null;
		try {
			src_vouchers = new VoucherExtendDMO()
					.querySrcVouchersByDesVouchers(des_vouchers, glorgbook);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage(), e);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			Logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessException(e.getMessage());
		}
		return src_vouchers;
	}

	/**
	 * ͨ���������VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO
	 * @param key
	 *            String
	 * @BusinessException BusinessException �쳣˵����
	 */
	public VoucherVO findByPrimaryKey(String key) throws BusinessException {

		VoucherVO voucher = null;
		try {
			VoucherDMO dmo = new VoucherDMO();
			voucher = dmo.findByPrimaryKey(key);
			VoucherVO[] vouchers = new VoucherVO[1];
			vouchers[0] = voucher;
			catCorpname(vouchers, getCorps());
			catSystemname(vouchers);
			catVouchertypename(vouchers,
					getVouchertypes(voucher.getPk_accountingbook()));
			// catUsername(vouchers, getUsers(null));

			DetailExtendDMO dmo1 = new DetailExtendDMO();
			DetailVO[] details = dmo1.queryByVoucherPk(voucher.getPk_voucher());
			details = catCurrcode(details, getCurrtypes());
			String[] pk_details = getRecDetailPKsByVoucherPK(new String[] { key });
			details = catDetailMatchingflag(details, pk_details);
			voucher.setDetails(details);
			details = catDetailMachForOffer(voucher);
			if (pk_details != null && pk_details.length != 0)
				voucher.setIsmatched(new Boolean(true));

			String[] pk_accsubj = new String[details.length];
			for (int j = 0; j < details.length; j++)
				pk_accsubj[j] = details[j].getPk_accasoa();

			details = catSubjName(details, voucher.getPrepareddate()
					.toStdString());
			// details = catAppend(details);
			details = catDtlFreevalue(details);
			details = catCheckstylename(details);
			details = catAss(details);
			voucher.setDetails(details);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(
					"VoucherBean::findByPrimaryKey(VoucherPK) BusinessException!",
					e);
		}
		return voucher;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-28 19:52:02)
	 * 
	 * @return nc.vo.bd.account.AccountVO[]
	 * @param strPk_corp
	 *            java.lang.String
	 */
	private nc.vo.bd.account.AccountVO[] getAccsubj(String[] strPk_accsubj,
			String stddate) throws BusinessException {
		if (strPk_accsubj == null || strPk_accsubj.length == 0)
			return null;
		String[] pk_accsubj = null;
		Vector vecaccsubj = new Vector();
		HashMap tempmap = new HashMap();
		for (int i = 0; i < strPk_accsubj.length; i++) {
			if (strPk_accsubj[i] != null
					&& tempmap.get(strPk_accsubj[i]) == null) {
				vecaccsubj.addElement(strPk_accsubj[i]);
				tempmap.put(strPk_accsubj[i], strPk_accsubj[i]);
			}
		}
		pk_accsubj = new String[vecaccsubj.size()];
		vecaccsubj.copyInto(pk_accsubj);
		nc.vo.bd.account.AccountVO[] accsubj = null;
		try {
			accsubj = new AccountUtilGL().queryByPks(pk_accsubj, stddate);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BusinessException(e.getMessage());

		}

		return accsubj;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-22 9:24:15)
	 * 
	 * @return nc.vo.gl.pubvoucher.UserVO[]
	 */
	private nc.vo.bd.balatype.BalaTypeVO[] getBalatype(String pk_corp)
			throws Exception {
		// nc.bs.bd.b19.BalatypeDMO dmo = new nc.bs.bd.b19.BalatypeDMO();
		// nc.vo.bd.balatype.BalaTypeVO[] tempvos = dmo.queryAll(pk_corp);
		nc.vo.bd.balatype.BalaTypeVO[] tempvos = Balatype.queryAll(pk_corp);
		return tempvos;
	}

	private SmallCorpVO[] getCorps() throws BusinessException {
		// nc.vo.org.FinanceOrgVO[] tempVO = null;
		// try {
		// //60x tempVO = new nc.bs.bd.CorpBO().queryAll(null);
		// } catch (Exception e) {
		// throw new BusinessException(e.getMessage(), e);
		// }
		// SmallCorpVO[] corps = new SmallCorpVO[tempVO.length];
		// for (int i = 0; i < tempVO.length; i++) {
		// corps[i] = new SmallCorpVO();
		// corps[i].setPk_corp(tempVO[i].getPk_corp());
		// corps[i].setUnitcode(tempVO[i].getCode());
		// corps[i].setUnitname(tempVO[i].getName());
		// corps[i].setUnitshortname(tempVO[i].getShortname());
		// }
		// return corps;
		return null;
	}

	public Integer getCorrectVoucherNo(VoucherVO voucher)
			throws BusinessException {
		try {
			if (voucher == null)
				return Integer.valueOf(-1);
			// GLParameterVO glparam = new GLParameterVO();
			// IGlPara dmo = NCLocator.getInstance().lookup(IGlPara.class);
			// VoucherExtendDMO dmo1 = new VoucherExtendDMO();
			// glparam.Parameter_isvouchernoautofill =
			// dmo.isVoucherNOAutoFill(voucher.getPk_accountingbook());
			// if (glparam.Parameter_isvouchernoautofill != null &&
			// glparam.Parameter_isvouchernoautofill.booleanValue()) {
			// if (voucher.getVoucherkind() != null &&
			// voucher.getVoucherkind().intValue() == 1)
			// return dmo1.getCorrectVoucherNo(voucher);
			// else {
			// glparam.Parameter_startvoucherno =
			// dmo.getStartVoucherNO(voucher.getPk_accountingbook());
			// return dmo1.getCorrectVoucherNoAutoMatch(voucher,
			// glparam.Parameter_startvoucherno);
			// }
			// } else {
			// return dmo1.getCorrectVoucherNo(voucher);
			// }
			String[] strs = getVoucherNumber(voucher);
			return Integer.valueOf(strs[1]);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-10-16 11:09:31)
	 * 
	 * @return nc.vo.bd.currtype.CurrtypeVO
	 */
	private nc.vo.bd.currtype.CurrtypeVO[] getCurrtypes() throws Exception {
		// 60x nc.bs.bd.b20.CurrtypeDMO dmo4 = new nc.bs.bd.b20.CurrtypeDMO();
		nc.vo.bd.currtype.CurrtypeVO[] vos = CurrtypeGL.queryAll(null);
		return vos;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-11-6 9:24:26)
	 * 
	 * @return java.lang.String[]
	 * @param pK_vouchers
	 *            java.lang.String[]
	 */
	private String[] getRecDetailPKs(String[] pk_details) throws Exception {

		if (pk_details == null || pk_details.length == 0)
			return null;

		DealclassDMO dealclassdmo = new DealclassDMO();
		DealclassVO[] dealclassvos = dealclassdmo
				.queryByModulgroup("checkrecon");
		Vector veccheckreconclass = new Vector();
		if (dealclassvos != null && dealclassvos.length != 0)
			for (int m = 0; m < dealclassvos.length; m++) {
				if (dealclassvos[m].getModules() != null) {
					try {
						nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall = (nc.bs.gl.pubinterface.ICheckReconcile) ObjectCreator
								.newInstance(dealclassvos[m].getModules(),
										dealclassvos[m].getClassname());
						veccheckreconclass.addElement(m_checkreconclassall);
					} catch (FrameworkRuntimeException e) {
						Logger.error(e.getMessage(), e);
						throw new BusinessException(e.getMessage(), e);
					}
				}
				/*
				 * try { Class m_classcheckrecon =
				 * java.lang.Class.forName(dealclassvos[m].getClassname());
				 * Object m_objectcheckrecon = m_classcheckrecon.newInstance();
				 * nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall =
				 * (nc.bs.gl.pubinterface.ICheckReconcile) m_objectcheckrecon;
				 * veccheckreconclass.addElement(m_checkreconclassall); } catch
				 * (ClassNotFoundException e) { } catch (NoClassDefFoundError e)
				 * { }
				 */
			}

		CheckReconVO checkReconVO = getCheckReconVO(CHECK_RECON_TEMP_DETAIL,
				pk_details);
		Vector vecPk_details = new Vector();
		for (int i = 0; i < veccheckreconclass.size(); i++) {
			CheckReconVO checkVo = ((nc.bs.gl.pubinterface.ICheckReconcile) veccheckreconclass
					.elementAt(i)).getRecDetailPKs((CheckReconVO) checkReconVO
					.clone());
			if (checkVo != null && checkVo.getRt_pks() != null) {
				String[] rt_pks = checkVo.getRt_pks();
				for (int j = 0; j < rt_pks.length; j++) {
					vecPk_details.addElement(rt_pks[j]);
				}
				Logger.error("�ӿ�" + checkVo.getBusiExplain()
						+ veccheckreconclass.elementAt(i));
			}
		}
		String[] rtPk_details = null;
		if (vecPk_details.size() > 0) {
			rtPk_details = new String[vecPk_details.size()];
			vecPk_details.copyInto(rtPk_details);
		}
		return rtPk_details;
	}

	/**
	 * ��ȡƾ֤���vo
	 * 
	 * @param pks
	 * @return
	 */
	private CheckReconVO getCheckReconVO(String tableName, String[] pks) {
		CheckReconVO checkReconVo = new CheckReconVO();
		if (pks != null && pks.length > 0) {
			checkReconVo.setCheck_pks(pks);
			if (pks.length > GLSqlUtil.MAXLENGTH) {
				String tempTable = GLSqlUtil.getTempTable(tableName, pks);
				checkReconVo.setTempTableName(tempTable);
				checkReconVo.setPkFieldName(GLSqlUtil.tempcolname);
			}
		}
		return checkReconVo;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-11-6 9:24:26)
	 * 
	 * @return java.lang.String[]
	 * @param pK_vouchers
	 *            java.lang.String[]
	 */
	private java.lang.String[] getRecDetailPKsByVoucherPK(
			java.lang.String[] pk_vouchers) throws Exception {
		DealclassDMO dealclassdmo = new DealclassDMO();
		DealclassVO[] dealclassvos = dealclassdmo
				.queryByModulgroup("checkrecon");
		Vector veccheckreconclass = new Vector();
		if (dealclassvos != null && dealclassvos.length != 0)
			for (int m = 0; m < dealclassvos.length; m++) {
				if (dealclassvos[m].getModules() != null) {
					try {
						nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall = (nc.bs.gl.pubinterface.ICheckReconcile) ObjectCreator
								.newInstance(dealclassvos[m].getModules(),
										dealclassvos[m].getClassname());
						veccheckreconclass.addElement(m_checkreconclassall);
					} catch (FrameworkRuntimeException e) {
						Logger.error(e.getMessage(), e);
						throw new BusinessException(e.getMessage(), e);
					}
				}
				/*
				 * try { Class m_classcheckrecon =
				 * java.lang.Class.forName(dealclassvos[m].getClassname());
				 * Object m_objectcheckrecon = m_classcheckrecon.newInstance();
				 * nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall =
				 * (nc.bs.gl.pubinterface.ICheckReconcile) m_objectcheckrecon;
				 * veccheckreconclass.addElement(m_checkreconclassall); } catch
				 * (ClassNotFoundException e) { } catch (NoClassDefFoundError e)
				 * { }
				 */
			}
		CheckReconVO checkReconVO = getCheckReconVO(null, pk_vouchers);
		Vector vecPk_details = new Vector();
		for (int i = 0; i < veccheckreconclass.size(); i++) {
			CheckReconVO checkVo = ((nc.bs.gl.pubinterface.ICheckReconcile) veccheckreconclass
					.elementAt(i))
					.getRecDetailPKsByVoucherPK((CheckReconVO) checkReconVO
							.clone());
			if (checkVo != null && checkVo.getRt_pks() != null) {
				for (int j = 0; j < checkVo.getRt_pks().length; j++) {
					vecPk_details.addElement(checkVo.getRt_pks()[j]);
				}
				Logger.error("�ӿ�" + checkVo.getBusiExplain()
						+ veccheckreconclass.elementAt(i));
			}
		}
		String[] rtPk_details = null;
		if (vecPk_details.size() > 0) {
			rtPk_details = new String[vecPk_details.size()];
			vecPk_details.copyInto(rtPk_details);
		}
		return rtPk_details;
	}

	private java.lang.String[] getRecDetailPKsByVoucherPK(
			java.lang.String[] pk_vouchers, String details[]) throws Exception {

		return getRecDetailPKsByVoucherPK(pk_vouchers, details, null);
	}

	private void addOperrationName(Set<String> operrationNames,
			DealclassVO dealclassvo) {
		String module = dealclassvo.getModules();
		if (StringUtils.isNotEmpty(module)) {
			if ("cmp".equalsIgnoreCase(module)) {
				operrationNames.add(Pfi18nTools.i18nSystemtypeName("M3607",
						"�ֽ����"));/* -=notranslate=- */
			} else if ("gl".equalsIgnoreCase(module)
					&& dealclassvo.getClassname().contains(".fip.")) {
				operrationNames.add(Pfi18nTools.i18nSystemtypeName("M1017",
						"���ƽ̨"));/* -=notranslate=- */
			} else {
				if (ReconcileCheckBO.class.getName().equals(
						dealclassvo.getClassname())) {
					operrationNames.add(nc.vo.ml.NCLangRes4VoTransl
							.getNCLangRes().getStrByID("2002100557",
									"UPP2002100557-000043")/* @res "Эͬ" */);
				} else if (VerifyUICheckBO.class.getName().equals(
						dealclassvo.getClassname())) {
					operrationNames.add(nc.vo.ml.NCLangRes4VoTransl
							.getNCLangRes().getStrByID("20021201",
									"UPP20021201-000244")/* @res "����" */);
				} else if (ContrastedCheckListenerImpl.class.getName().equals(
						dealclassvo.getClassname())) {
					operrationNames.add(nc.vo.ml.NCLangRes4VoTransl
							.getNCLangRes().getStrByID("20020401",
									"UPP20020401-000006")/* @res "����" */);
				}
			}
		}
	}

	private java.lang.String[] getRecDetailPKsByVoucherPK(
			java.lang.String[] pk_vouchers, String details[],
			Set<String> operrationNames) throws Exception {
		DealclassDMO dealclassdmo = new DealclassDMO();
		DealclassVO[] dealclassvos = dealclassdmo
				.queryByModulgroup("checkrecon");
		Vector veccheckreconclass = new Vector();
		Map<ICheckReconcile, DealclassVO> reconcile2Dealclass = new HashMap<ICheckReconcile, DealclassVO>();
		if (dealclassvos != null && dealclassvos.length != 0)
			for (int m = 0; m < dealclassvos.length; m++) {
				if (dealclassvos[m].getModules() != null) {
					try {
						nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall = (nc.bs.gl.pubinterface.ICheckReconcile) ObjectCreator
								.newInstance(dealclassvos[m].getModules(),
										dealclassvos[m].getClassname());
						veccheckreconclass.addElement(m_checkreconclassall);
						reconcile2Dealclass.put(m_checkreconclassall,
								dealclassvos[m]);
					} catch (FrameworkRuntimeException e) {
						Logger.error(e.getMessage(), e);
						throw new BusinessException(e.getMessage(), e);
					}
				}
				/*
				 * try { Class m_classcheckrecon =
				 * java.lang.Class.forName(dealclassvos[m].getClassname());
				 * Object m_objectcheckrecon = m_classcheckrecon.newInstance();
				 * nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall =
				 * (nc.bs.gl.pubinterface.ICheckReconcile) m_objectcheckrecon;
				 * veccheckreconclass.addElement(m_checkreconclassall); } catch
				 * (ClassNotFoundException e) { } catch (NoClassDefFoundError e)
				 * { }
				 */
			}
		CheckReconVO checkReconVo = getCheckReconVO(null, pk_vouchers);
		CheckReconVO checkReconVoDetail = getCheckReconVO(
				CHECK_RECON_TEMP_DETAIL, details);
		Vector vecPk_details = new Vector();
		for (int i = 0; i < veccheckreconclass.size(); i++) {
			CheckReconVO checkVo = ((nc.bs.gl.pubinterface.ICheckReconcile) veccheckreconclass
					.elementAt(i))
					.getRecDetailPKsByVoucherPK((CheckReconVO) checkReconVo
							.clone());
			if (checkVo != null && checkVo.getRt_pks() != null) {
				String[] rt_pks = checkVo.getRt_pks();
				for (int j = 0; j < rt_pks.length; j++) {
					vecPk_details.addElement(rt_pks[j]);
				}
				if (operrationNames != null && rt_pks.length > 0) {
					addOperrationName(operrationNames,
							reconcile2Dealclass.get(veccheckreconclass
									.elementAt(i)));
				}
				Logger.error("�ӿ�"
						+ (((nc.bs.gl.pubinterface.ICheckReconcile) veccheckreconclass
								.elementAt(i))).getClass().getName()
						+ "�Ѿ���������ҵ��");
			}
			CheckReconVO checkDetail = ((nc.bs.gl.pubinterface.ICheckReconcile) veccheckreconclass
					.elementAt(i))
					.getRecDetailPKs((CheckReconVO) checkReconVoDetail.clone());
			if (checkDetail != null && checkDetail.getRt_pks() != null) {
				String[] rt_pks = checkDetail.getRt_pks();
				for (int j = 0; j < rt_pks.length; j++) {
					vecPk_details.addElement(rt_pks[j]);
				}
				if (operrationNames != null && rt_pks.length > 0) {
					addOperrationName(operrationNames,
							reconcile2Dealclass.get(veccheckreconclass
									.elementAt(i)));
				}
				Logger.error("�ӿ�"
						+ (((nc.bs.gl.pubinterface.ICheckReconcile) veccheckreconclass
								.elementAt(i))).getClass().getName()
						+ "�Ѿ���������ҵ��");
			}
		}

		String[] rtPk_details = null;
		if (vecPk_details.size() > 0) {
			rtPk_details = new String[vecPk_details.size()];
			vecPk_details.copyInto(rtPk_details);
		}
		return rtPk_details;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-11-6 9:24:26)
	 * 
	 * @return java.lang.String[]
	 * @param pK_vouchers
	 *            java.lang.String[]
	 */
	private java.lang.String[] getRecVoucherPKs(java.lang.String[] pk_vouchers)
			throws java.lang.Exception {
		DealclassDMO dealclassdmo = new DealclassDMO();
		DealclassVO[] dealclassvos = dealclassdmo
				.queryByModulgroup("checkrecon");
		Vector veccheckreconclass = new Vector();
		if (dealclassvos != null && dealclassvos.length != 0)
			for (int m = 0; m < dealclassvos.length; m++) {
				if (dealclassvos[m].getModules() != null) {
					try {
						nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall = (nc.bs.gl.pubinterface.ICheckReconcile) ObjectCreator
								.newInstance(dealclassvos[m].getModules(),
										dealclassvos[m].getClassname());
						veccheckreconclass.addElement(m_checkreconclassall);
					} catch (FrameworkRuntimeException e) {
						Logger.error(e.getMessage(), e);
						throw new BusinessException(e.getMessage(), e);
					} catch (NullPointerException e) {
						Logger.error(e.getMessage(), e);
						throw new BusinessException(e.getMessage(), e);
					}
				}
				/*
				 * try { Class m_classcheckrecon =
				 * java.lang.Class.forName(dealclassvos[m].getClassname());
				 * Object m_objectcheckrecon = m_classcheckrecon.newInstance();
				 * nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall =
				 * (nc.bs.gl.pubinterface.ICheckReconcile) m_objectcheckrecon;
				 * veccheckreconclass.addElement(m_checkreconclassall); } catch
				 * (ClassNotFoundException e) { } catch (NoClassDefFoundError e)
				 * { }
				 */
			}
		CheckReconVO checkReconVo = getCheckReconVO(null, pk_vouchers);
		Vector vecPk_details = new Vector();
		for (int i = 0; i < veccheckreconclass.size(); i++) {
			CheckReconVO checkVo = ((nc.bs.gl.pubinterface.ICheckReconcile) veccheckreconclass
					.elementAt(i)).getRecVoucherPKs((CheckReconVO) checkReconVo
					.clone());
			if (checkVo != null && checkVo.getRt_pks() != null) {
				String[] rt_pks = checkVo.getRt_pks();
				for (int j = 0; j < rt_pks.length; j++) {
					vecPk_details.addElement(rt_pks[j]);
				}
				Logger.error("�ӿ�" + checkVo.getBusiExplain()
						+ veccheckreconclass.elementAt(i));
			}
		}
		String[] rtPk_details = null;
		if (vecPk_details.size() > 0) {
			rtPk_details = new String[vecPk_details.size()];
			vecPk_details.copyInto(rtPk_details);
		}
		return rtPk_details;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-11-6 9:24:26)
	 * 
	 * @return java.lang.String[]
	 * @param pK_vouchers
	 *            java.lang.String[]
	 */
	private java.lang.String[] getRecVoucherPKs4Detail(VoucherVO[] voucherVOs)
			throws java.lang.Exception {
		DealclassDMO dealclassdmo = new DealclassDMO();
		DealclassVO[] dealclassvos = dealclassdmo
				.queryByModulgroup("checkrecon");
		Vector veccheckreconclass = new Vector();
		if (dealclassvos != null && dealclassvos.length != 0)
			for (int m = 0; m < dealclassvos.length; m++) {
				if (dealclassvos[m].getModules() != null) {
					try {
						nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall = (nc.bs.gl.pubinterface.ICheckReconcile) ObjectCreator
								.newInstance(dealclassvos[m].getModules(),
										dealclassvos[m].getClassname());
						veccheckreconclass.addElement(m_checkreconclassall);
					} catch (FrameworkRuntimeException e) {
						Logger.error(e.getMessage(), e);
						throw new BusinessException(e.getMessage(), e);
					} catch (NullPointerException e) {
						Logger.error(e.getMessage(), e);
						throw new BusinessException(e.getMessage(), e);
					}
				}
				/*
				 * try { Class m_classcheckrecon =
				 * java.lang.Class.forName(dealclassvos[m].getClassname());
				 * Object m_objectcheckrecon = m_classcheckrecon.newInstance();
				 * nc.bs.gl.pubinterface.ICheckReconcile m_checkreconclassall =
				 * (nc.bs.gl.pubinterface.ICheckReconcile) m_objectcheckrecon;
				 * veccheckreconclass.addElement(m_checkreconclassall); } catch
				 * (ClassNotFoundException e) { } catch (NoClassDefFoundError e)
				 * { }
				 */
			}
		Vector vecPk_details = new Vector();
		/**
		 * ��ѯ��ÿ��voucher��Ӧ��pk_details Ȼ��ӿ��е���һ������
		 */
		ArrayList<String> list = null;// new ArrayList<String>();queryByPks
		DetailVO detailVOs[] = null;
		String voucherPKs[] = new String[voucherVOs.length];
		for (int i = 0; i < voucherVOs.length; i++) {
			voucherPKs[i] = voucherVOs[i].getPk_voucher();
		}
		voucherVOs = queryByPks(voucherPKs);
		for (int k = 0; k < voucherVOs.length; k++) {

			list = new ArrayList<String>();
			detailVOs = voucherVOs[k].getDetails();
			for (int i = 0; i < detailVOs.length; i++) {
				list.add(detailVOs[i].getPk_detail());
			}

			CheckReconVO checkReconVo = getCheckReconVO(
					CHECK_RECON_TEMP_DETAIL, list.toArray(new String[0]));
			for (int i = 0; i < veccheckreconclass.size(); i++) {
				CheckReconVO checkVo = ((nc.bs.gl.pubinterface.ICheckReconcile) veccheckreconclass
						.elementAt(i))
						.getRecDetailPKs((CheckReconVO) checkReconVo.clone());
				if (checkVo != null && checkVo.getRt_pks() != null) {
					String[] rt_pks = checkVo.getRt_pks();
					for (int j = 0; j < rt_pks.length; j++) {
						vecPk_details.addElement(rt_pks[j]);
					}
					Logger.error("�ӿ�" + checkVo.getBusiExplain()
							+ veccheckreconclass.elementAt(i));
				}
			}
		}
		String[] rtPk_details = null;
		if (vecPk_details.size() > 0) {
			rtPk_details = new String[vecPk_details.size()];
			vecPk_details.copyInto(rtPk_details);
		}
		return rtPk_details;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-22 9:18:32)
	 * 
	 * @return nc.vo.bd.vouchertype.VoucherTypeVO[]
	 * @param pk_corp
	 *            java.lang.String
	 */
	private VoucherTypeVO[] getVouchertypes(String pk_orgbook) throws Exception {
		// VouchertypeBO typeBO = new VouchertypeBO();
		VoucherTypeVO[] type = null;
		if (pk_orgbook == null) {
			type = VoucherTypeGL.queryAllByGlorgbookSimple(null);
		} else {
			// type = VoucherTypeGL.queryAllByGlorgbook(pk_orgbook);
			type = VoucherTypeDataCache.getInstance()
					.getVouchertypeBypkOrgBook(pk_orgbook);
		}
		return type;
	}

	/**
	 * �����ݿ��в���һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param voucher
	 *            nc.vo.gl.voucher.VoucherVO
	 * @return java.lang.String ������VO����������ַ�����
	 * @BusinessException BusinessException �쳣˵����
	 */
	private VoucherVO insert(VoucherVO voucher) throws Exception {
		GLKeyLock lock = null;
		boolean bLockSuccess = false;
		try {
			// ������ڳ�ƾ֤����ȡƾ֤��,��������SQL Server����
			if (voucher.getVoucherkind().intValue() != 2) {
				try {

					if (voucher.getNo() == null
							|| voucher.getNo().intValue() <= 0) {
						getVoucherNumberForSave(voucher);
					}

					else {
						// if (voucher.getPk_voucher() != null) {
						try {
							updateVoucherNumber(voucher);
						} catch (VoucherNoDuplicateException e) {
							throw new BusinessException(
									new VoucherCheckMessage()
											.getVoucherMessage(VoucherCheckMessage.ErrMsgNOExist));
						} catch (Exception e) {
							throw e;
						}
						// }

					}

				} catch (Exception e) {
					Logger.error(e.getMessage(), e);
					throw new BusinessException(e.getMessage());
				}
			}
			// ƾ֤����
			VoucherDMO dmo = new VoucherDMO();
			String pk = "";
			pk = dmo.insert(voucher);
			voucher.setPk_voucher(pk);
			if (voucher.getFreevalue1() != null
					|| voucher.getFreevalue2() != null
					|| voucher.getFreevalue3() != null
					|| voucher.getFreevalue4() != null
					|| voucher.getFreevalue5() != null) {
				VchfreevalueVO vf = new VchfreevalueVO();
				vf.setFreevalue1(voucher.getFreevalue1());
				vf.setFreevalue2(voucher.getFreevalue2());
				vf.setFreevalue3(voucher.getFreevalue3());
				vf.setFreevalue4(voucher.getFreevalue4());
				vf.setFreevalue5(voucher.getFreevalue5());
				vf.setPk_voucher(voucher.getPk_voucher());
				new VchfreevalueDMO().insert(vf);
			}
		} finally {
			if (bLockSuccess)
				lock.freeKey(
						voucher.getPk_accountingbook()
								+ voucher.getPk_vouchertype()
								+ voucher.getYear() + voucher.getPeriod(),
						voucher.getPk_prepared(), "gl_voucher");
		}

		// ƾ֤�ӱ�����¼
		DetailDMO dmo1 = new DetailDMO();

		DetailVO[] details = voucher.getDetails();
		for (DetailVO detail : details) {
			detail.setPk_voucher(voucher.getPk_voucher());
		}
		if (details != null && details.length > 0) {
			String[] pks = dmo1.insertArray(details);
			Vector tempfreevalue = new Vector();
			List<VatDetailVO> vatdetailList = new LinkedList<VatDetailVO>();
			DetailVO detailTemp = null;
			for (int i = 0; i < details.length; i++) {
				detailTemp = details[i];
				detailTemp.setPk_detail(pks[i]);
				if (details[i].getFreevalue1() != null
						|| details[i].getFreevalue2() != null
						|| details[i].getFreevalue3() != null
						|| details[i].getFreevalue4() != null
						|| details[i].getFreevalue5() != null
						|| details[i].getFreevalue6() != null
						|| details[i].getFreevalue7() != null
						|| details[i].getFreevalue8() != null
						|| details[i].getFreevalue9() != null
						|| details[i].getFreevalue10() != null
						|| details[i].getFreevalue11() != null
						|| details[i].getFreevalue12() != null
						|| details[i].getFreevalue13() != null
						|| details[i].getFreevalue14() != null
						|| details[i].getFreevalue15() != null
						|| details[i].getFreevalue16() != null
						|| details[i].getFreevalue17() != null
						|| details[i].getFreevalue18() != null
						|| details[i].getFreevalue19() != null
						|| details[i].getFreevalue20() != null
						|| details[i].getFreevalue21() != null
						|| details[i].getFreevalue22() != null
						|| details[i].getFreevalue23() != null
						|| details[i].getFreevalue24() != null
						|| details[i].getFreevalue25() != null
						|| details[i].getFreevalue26() != null
						|| details[i].getFreevalue27() != null
						|| details[i].getFreevalue28() != null
						|| details[i].getFreevalue29() != null
						|| details[i].getFreevalue30() != null) {
					DtlfreevalueVO tempfree = new DtlfreevalueVO();
					tempfree.setPk_detail(details[i].getPk_detail());
					tempfree.setFreevalue1(details[i].getFreevalue1());
					tempfree.setFreevalue2(details[i].getFreevalue2());
					tempfree.setFreevalue3(details[i].getFreevalue3());
					tempfree.setFreevalue4(details[i].getFreevalue4());
					tempfree.setFreevalue5(details[i].getFreevalue5());
					tempfree.setFreevalue6(details[i].getFreevalue6());
					tempfree.setFreevalue7(details[i].getFreevalue7());
					tempfree.setFreevalue8(details[i].getFreevalue8());
					tempfree.setFreevalue9(details[i].getFreevalue9());
					tempfree.setFreevalue10(details[i].getFreevalue10());
					tempfree.setFreevalue11(details[i].getFreevalue11());
					tempfree.setFreevalue12(details[i].getFreevalue12());
					tempfree.setFreevalue13(details[i].getFreevalue13());
					tempfree.setFreevalue14(details[i].getFreevalue14());
					tempfree.setFreevalue15(details[i].getFreevalue15());
					tempfree.setFreevalue16(details[i].getFreevalue16());
					tempfree.setFreevalue17(details[i].getFreevalue17());
					tempfree.setFreevalue18(details[i].getFreevalue18());
					tempfree.setFreevalue19(details[i].getFreevalue19());
					tempfree.setFreevalue20(details[i].getFreevalue20());
					tempfree.setFreevalue21(details[i].getFreevalue21());
					tempfree.setFreevalue22(details[i].getFreevalue22());
					tempfree.setFreevalue23(details[i].getFreevalue23());
					tempfree.setFreevalue24(details[i].getFreevalue24());
					tempfree.setFreevalue25(details[i].getFreevalue25());
					tempfree.setFreevalue26(details[i].getFreevalue26());
					tempfree.setFreevalue27(details[i].getFreevalue27());
					tempfree.setFreevalue28(details[i].getFreevalue28());
					tempfree.setFreevalue29(details[i].getFreevalue29());
					tempfree.setFreevalue30(details[i].getFreevalue30());
					tempfreevalue.addElement(tempfree);
				}

				if (detailTemp.getVatdetail() != null) {
					detailTemp.getVatdetail().setPk_detail(
							detailTemp.getPk_detail());
					detailTemp.getVatdetail().setPk_voucher(
							detailTemp.getPk_voucher());
					detailTemp.getVatdetail().setVoucherno(voucher.getNo());
					vatdetailList.add(detailTemp.getVatdetail());
				}
				VatDetailVO vatdetail = details[i].getVatdetail();
				if (vatdetail != null) {
					vatdetail.setPk_manager(null);
				}
			}
			// ��¼������
			DtlfreevalueVO[] detailfreevalues = new DtlfreevalueVO[tempfreevalue
					.size()];
			tempfreevalue.copyInto(detailfreevalues);
			if (detailfreevalues.length > 0) {
				DtlfreevalueDMO dmo3 = new DtlfreevalueDMO();
				dmo3.insertArray(detailfreevalues);
			}

			// ˰����ϸ
			if (vatdetailList.size() > 0) {
				VatDetailVO[] vatdetails = NCLocator
						.getInstance()
						.lookup(IVatDetailMngService.class)
						.insertVATDetailVO(
								vatdetailList.toArray(new VatDetailVO[0]));

				HashMap<String, VatDetailVO> vatdetailMap = new HashMap<String, VatDetailVO>();
				for (int i = 0; i < vatdetails.length; i++) {
					vatdetailMap.put(vatdetails[i].getPk_detail(),
							vatdetails[i]);
				}

				for (int i = 0; i < details.length; i++) {
					detailTemp = details[i];
					detailTemp.setVatdetail(vatdetailMap.get(detailTemp
							.getPk_detail()));
				}
			}

			voucher.setDetails(details);
		}
		return voucher;
	}

	/**
	 * �����ݿ��в���һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param voucher
	 *            nc.vo.gl.voucher.VoucherVO
	 * @return java.lang.String ������VO����������ַ�����
	 * @BusinessException BusinessException �쳣˵����
	 */
	public VoucherVO insert(VoucherVO voucher, String dsName) throws Exception {
		GLKeyLock lock = null;
		boolean bLockSuccess = false;
		try {
			// ƾ֤����
			VoucherDMO dmo = new VoucherDMO(dsName);
			String pk = "";
			pk = dmo.insert(voucher);
			voucher.setPk_voucher(pk);
			if (voucher.getFreevalue1() != null
					|| voucher.getFreevalue2() != null
					|| voucher.getFreevalue3() != null
					|| voucher.getFreevalue4() != null
					|| voucher.getFreevalue5() != null) {
				VchfreevalueVO vf = new VchfreevalueVO();
				vf.setFreevalue1(voucher.getFreevalue1());
				vf.setFreevalue2(voucher.getFreevalue2());
				vf.setFreevalue3(voucher.getFreevalue3());
				vf.setFreevalue4(voucher.getFreevalue4());
				vf.setFreevalue5(voucher.getFreevalue5());
				vf.setPk_voucher(voucher.getPk_voucher());
				new VchfreevalueDMO().insert(vf);
			}
		} finally {
			if (bLockSuccess)
				lock.freeKey(
						voucher.getPk_accountingbook()
								+ voucher.getPk_vouchertype()
								+ voucher.getYear() + voucher.getPeriod(),
						voucher.getPk_prepared(), "gl_voucher");
		}

		// ƾ֤�ӱ�����¼
		DetailDMO dmo1 = new DetailDMO(dsName);

		DetailVO[] details = voucher.getDetails();
		for (DetailVO detail : details) {
			detail.setPk_voucher(voucher.getPk_voucher());
		}
		if (details != null && details.length > 0) {
			String[] pks = dmo1.insertArray(details);
			Vector tempfreevalue = new Vector();
			List<VatDetailVO> vatdetailList = new LinkedList<VatDetailVO>();
			DetailVO detailTemp = null;
			for (int i = 0; i < details.length; i++) {
				detailTemp = details[i];
				detailTemp.setPk_detail(pks[i]);
				if (details[i].getFreevalue1() != null
						|| details[i].getFreevalue2() != null
						|| details[i].getFreevalue3() != null
						|| details[i].getFreevalue4() != null
						|| details[i].getFreevalue5() != null
						|| details[i].getFreevalue6() != null
						|| details[i].getFreevalue7() != null
						|| details[i].getFreevalue8() != null
						|| details[i].getFreevalue9() != null
						|| details[i].getFreevalue10() != null
						|| details[i].getFreevalue11() != null
						|| details[i].getFreevalue12() != null
						|| details[i].getFreevalue13() != null
						|| details[i].getFreevalue14() != null
						|| details[i].getFreevalue15() != null
						|| details[i].getFreevalue16() != null
						|| details[i].getFreevalue17() != null
						|| details[i].getFreevalue18() != null
						|| details[i].getFreevalue19() != null
						|| details[i].getFreevalue20() != null
						|| details[i].getFreevalue21() != null
						|| details[i].getFreevalue22() != null
						|| details[i].getFreevalue23() != null
						|| details[i].getFreevalue24() != null
						|| details[i].getFreevalue25() != null
						|| details[i].getFreevalue26() != null
						|| details[i].getFreevalue27() != null
						|| details[i].getFreevalue28() != null
						|| details[i].getFreevalue29() != null
						|| details[i].getFreevalue30() != null) {
					DtlfreevalueVO tempfree = new DtlfreevalueVO();
					tempfree.setPk_detail(details[i].getPk_detail());
					tempfree.setFreevalue1(details[i].getFreevalue1());
					tempfree.setFreevalue2(details[i].getFreevalue2());
					tempfree.setFreevalue3(details[i].getFreevalue3());
					tempfree.setFreevalue4(details[i].getFreevalue4());
					tempfree.setFreevalue5(details[i].getFreevalue5());
					tempfree.setFreevalue6(details[i].getFreevalue6());
					tempfree.setFreevalue7(details[i].getFreevalue7());
					tempfree.setFreevalue8(details[i].getFreevalue8());
					tempfree.setFreevalue9(details[i].getFreevalue9());
					tempfree.setFreevalue10(details[i].getFreevalue10());
					tempfree.setFreevalue11(details[i].getFreevalue11());
					tempfree.setFreevalue12(details[i].getFreevalue12());
					tempfree.setFreevalue13(details[i].getFreevalue13());
					tempfree.setFreevalue14(details[i].getFreevalue14());
					tempfree.setFreevalue15(details[i].getFreevalue15());
					tempfree.setFreevalue16(details[i].getFreevalue16());
					tempfree.setFreevalue17(details[i].getFreevalue17());
					tempfree.setFreevalue18(details[i].getFreevalue18());
					tempfree.setFreevalue19(details[i].getFreevalue19());
					tempfree.setFreevalue20(details[i].getFreevalue20());
					tempfree.setFreevalue21(details[i].getFreevalue21());
					tempfree.setFreevalue22(details[i].getFreevalue22());
					tempfree.setFreevalue23(details[i].getFreevalue23());
					tempfree.setFreevalue24(details[i].getFreevalue24());
					tempfree.setFreevalue25(details[i].getFreevalue25());
					tempfree.setFreevalue26(details[i].getFreevalue26());
					tempfree.setFreevalue27(details[i].getFreevalue27());
					tempfree.setFreevalue28(details[i].getFreevalue28());
					tempfree.setFreevalue29(details[i].getFreevalue29());
					tempfree.setFreevalue30(details[i].getFreevalue30());
					tempfreevalue.addElement(tempfree);
				}

				if (detailTemp.getVatdetail() != null) {
					detailTemp.getVatdetail().setPk_detail(
							detailTemp.getPk_detail());
					detailTemp.getVatdetail().setPk_voucher(
							detailTemp.getPk_voucher());
					detailTemp.getVatdetail().setVoucherno(voucher.getNo());
					vatdetailList.add(detailTemp.getVatdetail());
				}
				VatDetailVO vatdetail = details[i].getVatdetail();
				if (vatdetail != null) {
					vatdetail.setPk_manager(null);
				}
			}
			// ��¼������
			DtlfreevalueVO[] detailfreevalues = new DtlfreevalueVO[tempfreevalue
					.size()];
			tempfreevalue.copyInto(detailfreevalues);
			if (detailfreevalues.length > 0) {
				DtlfreevalueDMO dmo3 = new DtlfreevalueDMO(dsName);
				dmo3.insertArray(detailfreevalues);
			}

			voucher.setDetails(details);
		}
		return voucher;
	}

	/**
	 * �����ݿ��в���һ��VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @param voucher
	 *            nc.vo.gl.voucher.VoucherVO[]
	 * @return java.lang.String[] ������VO��������������ַ������顣
	 * @BusinessException BusinessException �쳣˵����
	 */
	private String[] insertArray(VoucherVO[] vouchers) throws Exception {

		VoucherDMO dmo = new VoucherDMO();
		String[] keys = dmo.insertArray(vouchers);
		return keys;
	}

	public Boolean isExistRegulationVoucher(String pk_glorgbook, String year,
			String period) throws BusinessException {
		try {
			return new Boolean(new VoucherExtendDMO().isExistRegulationVoucher(
					pk_glorgbook, year, period));
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}

	}

	/**
	 * ͨ����λ���뷵��ָ����˾���м�¼VO���顣�����λ����Ϊ�շ������м�¼��
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO[] �鵽��VO��������
	 * @param unitCode
	 *            int
	 * @BusinessException BusinessException �쳣˵����
	 */
	public VoucherVO[] queryAll(String pk_corp) throws BusinessException {
		return null;
	}

	/**
	 * ����VO�����趨�������������з���������VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO[]
	 * @param voucherVO
	 *            nc.vo.gl.voucher.VoucherVO
	 * @param boolean ����¼VO���ǲ�����¼VO
	 * @BusinessException java.sql.SQLBusinessException �쳣˵����
	 */
	public VoucherVO[] queryByConditionVO(
			nc.vo.gl.voucherquery.VoucherQueryConditionVO[] condVoucherVO,
			Boolean isIncludeDetails) throws BusinessException {

		NCLocator
				.getInstance()
				.lookup(IFreeMap.class)
				.getAccAssInfoByOnlyChecktypes(
						new String[] { "1001Z01000000000HQWV" }, Module.GL);
		VoucherVO[] vouchers = null;
		try {
			VoucherVO[] tmpvouchers = null;
			Vector vecVouchers = new Vector();

			VoucherExtendDMO dmo = new VoucherExtendDMO();
			for (int i = 0; i < condVoucherVO.length; i++) {
				tmpvouchers = dmo.queryByConditionVO(condVoucherVO[i]);
				if (tmpvouchers != null) {
					for (int j = 0; j < tmpvouchers.length; j++) {
						vecVouchers.addElement(tmpvouchers[j]);
					}
				}
			}
			if (vecVouchers == null || vecVouchers.size() == 0)
				return null;
			vouchers = new VoucherVO[vecVouchers.size()];
			vecVouchers.copyInto(vouchers);
			String[] pks = new String[vouchers.length];
			Boolean issequencequery = new Boolean(false);
			for (int i = 0; i < vouchers.length; i++) {
				pks[i] = vouchers[i].getPk_voucher();
			}
			for (int i = 0; i < condVoucherVO.length; i++) {
				if (condVoucherVO[i].getIsequencetimequery().booleanValue()) {
					issequencequery = new Boolean(true);
					break;
				}
			}
			if (!issequencequery.booleanValue()) {
				// String[] contrasted = getRecVoucherPKs(pks);
				// catVoucherMatchingflag(vouchers, contrasted);
				catVoucherFreeValue(vouchers);
			}
			// 60x catCorpname(vouchers, getCorps());
			catSystemname(vouchers);
			catVouchertypename(vouchers, getVouchertypes(null));

			// Log.getInstance(this.getClass()).debug("@@@@@@in voucher main
			// query over");
			if (isIncludeDetails.booleanValue()) {
				// Log.getInstance(this.getClass()).debug("@@@@@@in detail query
				// logic");
				Vector vecDetails = new Vector();
				DetailExtendDMO dmo1 = new DetailExtendDMO();
				HashMap<String, VoucherVO> vchmap = new HashMap<String, VoucherVO>();
				for (int i = 0; i < condVoucherVO.length; i++) {

					DetailVO[] tmpdetails = dmo1
							.queryByConditionVO(condVoucherVO[i]);
					// Log.getInstance(this.getClass()).debug("@@@@@@in detail
					// query logic condi " + i);
					if (tmpdetails != null) {
						for (int j = 0; j < tmpdetails.length; j++) {
							vecDetails.addElement(tmpdetails[j]);
						}
					}
				}
				DetailVO[] details = new DetailVO[vecDetails.size()];
				vecDetails.copyInto(details);
				String detailPks[] = new String[details.length];
				for (int i = 0; i < detailPks.length; i++) {
					detailPks[i] = details[i].getPk_detail();
				}
				if (!issequencequery.booleanValue()) {
					String[] pk_details = getRecDetailPKsByVoucherPK(pks,
							detailPks);

					details = catDetailMatchingflag(details, pk_details);
				}
				details = catCurrcode(details, getCurrtypes());

				String[] pk_accsubj = new String[details.length];
				for (int j = 0; j < details.length; j++)
					pk_accsubj[j] = details[j].getPk_accasoa();
				if (condVoucherVO[0].getPowerVO() != null) {
					details = catSubjName(details, condVoucherVO[0]
							.getPowerVO().getBusiDate());
				} else {
					details = catSubjName(details);
				}
				// details = catAppend(details);
				details = catDtlFreevalue(details);
				// details = catSubjfreevalue(details);
				details = catCashFlows(details);
				details = catCheckstylename(details);
				details = catAss(details);
				catDetails(vouchers, details);
			}
			catDetailMatchingFlagForOffer(vouchers);
			// catUsername(vouchers, getUsers(null));
			sort(vouchers);
			vouchers = dealOfferFlag(vouchers);
		} catch (Exception e) {
			// log.error(e);
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return vouchers;
	}

	/**
	 * ���ó�����ʶ
	 * 
	 * @param voucherVOs
	 * @throws BusinessException
	 */
	private VoucherVO[] dealOfferFlag(VoucherVO[] voucherVOs) throws Exception {
		if (voucherVOs == null)
			return null;
		Map<String, VoucherVO> voucherMap = new HashMap<String, VoucherVO>();
		List<String> voucherList = new ArrayList<String>();
		// ���ڴ洢���е�pk_voucher
		List<String> pk_voucherList = new ArrayList<String>();
		for (VoucherVO voucherVo : voucherVOs) {
			voucherMap.put(voucherVo.getPk_voucher(), voucherVo);
			pk_voucherList.add(voucherVo.getPk_voucher());
			if (voucherVo.getOffervoucher() != null) {
				voucherList.add(voucherVo.getOffervoucher());
				voucherVo.setIsOffer(UFBoolean.TRUE);
			}
		}
		for (String pk_voucher : voucherList) {
			VoucherVO voucherVo = voucherMap.get(pk_voucher);
			if (voucherVo != null) {
				voucherVo.setIsOffer(UFBoolean.TRUE);
			}
		}

		// ����pk�ٴ˲�ѯ�Ƿ񱻳���ƾ֤
		VoucherExtendDMO dmo = new VoucherExtendDMO();

		String inStr = SqlUtils.getInStr("offervoucher", pk_voucherList, true);

		// ������ƾ֤
		VoucherVO[] queryByCondition = dmo.queryByCondition(inStr,
				voucherVOs[0].getPk_group());
		if (queryByCondition != null && queryByCondition.length > 0) {
			for (VoucherVO offerVo : queryByCondition) {
				VoucherVO voucherVo = voucherMap.get(offerVo.getOffervoucher());
				if (voucherVo != null) {
					voucherVo.setIsOffer(UFBoolean.TRUE);
				}
			}
		}
		return voucherVOs;
	}

	/**
	 * ͨ����λ���뷵��ָ����˾���м�¼VO���顣�����λ����Ϊ�շ������м�¼��
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO[] �鵽��VO��������
	 * @param unitCode
	 *            int
	 * @BusinessException BusinessException �쳣˵����
	 */
	public VoucherVO queryByPk(String pk_voucher) throws BusinessException {

		VoucherVO voucher = null;
		try {
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			voucher = dmo.queryByVoucherPk(pk_voucher);
			if (voucher == null)
				return null;
			VoucherVO[] vouchers = new VoucherVO[1];
			vouchers[0] = voucher;
			catCorpname(vouchers, getCorps());
			catSystemname(vouchers);
			catVouchertypename(vouchers,
					getVouchertypes(voucher.getPk_accountingbook()));
			catVoucherFreeValue(vouchers);

			DetailExtendDMO dmo1 = new DetailExtendDMO();
			DetailVO[] details = dmo1.queryByVoucherPk(voucher.getPk_voucher());

			// hurh �޷�¼����
			// if ((details == null || details.length == 0) &&
			// voucher.getErrmessage() == null)
			//
			// throw new
			// BusinessException(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005",
			// "UPP20021005-000560")/*
			// * @res "ƾ֤���ݴ���ƾ֤�ı�������Ϊ�գ�"

			// */);

			String detailPks[] = new String[details.length];
			for (int i = 0; i < detailPks.length; i++) {
				detailPks[i] = details[i].getPk_detail();
			}
			if (details != null && details.length > 0) {
				details = catCurrcode(details, getCurrtypes());
				String[] pk_details = /*
									 * getRecDetailPKsByVoucherPK(new String[] {
									 * pk_voucher }); details =
									 * catDetailMatchingflag(details,
									 * pk_details);
									 * 
									 * pk_details=
									 */getRecDetailPKsByVoucherPK(
						new String[] { pk_voucher }, detailPks);

				// if(pk_details != null && pk_details.length > 0){
				// voucher.setIsmatched(true);
				// }

				details = catDetailMatchingflag(details, pk_details);

				// if (pk_details != null && pk_details.length != 0)
				// voucher.setIsmatched(new Boolean(true));

				// String[] pk_accsubj = new String[details.length];
				// for (int j = 0; j < details.length; j++)
				// pk_accsubj[j] = details[j].getPk_accasoa();

				details = catSubjName(details);
				// details = catAppend(details);
				details = catDtlFreevalue(details);
				// details = catSubjfreevalue(details);
				details = catCashFlows(details);
				details = catCheckstylename(details);
				details = catAss(details);
				// ˰����ϸ
				details = catVatDetails(details);
				voucher.setDetails(details);
				catDetailMatchingFlagForOffer(vouchers);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}

		// ����������ʶ
		dealOfferFlag(voucher);

		return voucher;
	}

	/**
	 * ����ƾ֤�Զ������¼�Զ������¼ ��ɾ�������
	 * 
	 * @param voucher
	 * @throws NamingException
	 * @throws SystemException
	 * @throws SQLException
	 */
	private void updateSubDetail(VoucherVO voucher) throws NamingException,
			SystemException, Exception {
		// ɾ��ƾ֤����ӱ������
		VchfreevalueDMO vfdmo = new VchfreevalueDMO();
		vfdmo.deleteByVoucherPK(voucher.getPk_voucher());
		DetailExtendDMO dmo1 = new DetailExtendDMO();
		DtlfreevalueDMO dmo3 = new DtlfreevalueDMO();
		dmo3.deleteByVoucherPK(voucher.getPk_voucher());
		dmo1.deleteByVoucherPK(voucher.getPk_voucher());

		// ������ͬpk�ļ�¼
		if (voucher.getFreevalue1() != null || voucher.getFreevalue2() != null
				|| voucher.getFreevalue3() != null
				|| voucher.getFreevalue4() != null
				|| voucher.getFreevalue5() != null) {
			VchfreevalueVO vf = new VchfreevalueVO();
			vf.setFreevalue1(voucher.getFreevalue1());
			vf.setFreevalue2(voucher.getFreevalue2());
			vf.setFreevalue3(voucher.getFreevalue3());
			vf.setFreevalue4(voucher.getFreevalue4());
			vf.setFreevalue5(voucher.getFreevalue5());
			vf.setPk_voucher(voucher.getPk_voucher());
			vfdmo.insert(vf);
		}
		DetailVO[] details = voucher.getDetails();
		for (int i = 0; i < details.length; i++) {
			details[i].setPk_voucher(voucher.getPk_voucher());
		}

		String[] pks = dmo1.insertArray(details);

		Vector tempfreevalue = new Vector();
		for (int i = 0; i < details.length; i++) {
			details[i].setPk_detail(pks[i]);
			if (details[i].getFreevalue1() != null
					|| details[i].getFreevalue2() != null
					|| details[i].getFreevalue3() != null
					|| details[i].getFreevalue4() != null
					|| details[i].getFreevalue5() != null
					|| details[i].getFreevalue6() != null
					|| details[i].getFreevalue7() != null
					|| details[i].getFreevalue8() != null
					|| details[i].getFreevalue9() != null
					|| details[i].getFreevalue10() != null
					|| details[i].getFreevalue11() != null
					|| details[i].getFreevalue12() != null
					|| details[i].getFreevalue13() != null
					|| details[i].getFreevalue14() != null
					|| details[i].getFreevalue15() != null
					|| details[i].getFreevalue16() != null
					|| details[i].getFreevalue17() != null
					|| details[i].getFreevalue18() != null
					|| details[i].getFreevalue19() != null
					|| details[i].getFreevalue20() != null
					|| details[i].getFreevalue21() != null
					|| details[i].getFreevalue22() != null
					|| details[i].getFreevalue23() != null
					|| details[i].getFreevalue24() != null
					|| details[i].getFreevalue25() != null
					|| details[i].getFreevalue26() != null
					|| details[i].getFreevalue27() != null
					|| details[i].getFreevalue28() != null
					|| details[i].getFreevalue29() != null
					|| details[i].getFreevalue30() != null) {
				DtlfreevalueVO tempfree = new DtlfreevalueVO();
				tempfree.setPk_detail(details[i].getPk_detail());
				tempfree.setFreevalue1(details[i].getFreevalue1());
				tempfree.setFreevalue2(details[i].getFreevalue2());
				tempfree.setFreevalue3(details[i].getFreevalue3());
				tempfree.setFreevalue4(details[i].getFreevalue4());
				tempfree.setFreevalue5(details[i].getFreevalue5());
				tempfree.setFreevalue6(details[i].getFreevalue6());
				tempfree.setFreevalue7(details[i].getFreevalue7());
				tempfree.setFreevalue8(details[i].getFreevalue8());
				tempfree.setFreevalue9(details[i].getFreevalue9());
				tempfree.setFreevalue10(details[i].getFreevalue10());
				tempfree.setFreevalue11(details[i].getFreevalue11());
				tempfree.setFreevalue12(details[i].getFreevalue12());
				tempfree.setFreevalue13(details[i].getFreevalue13());
				tempfree.setFreevalue14(details[i].getFreevalue14());
				tempfree.setFreevalue15(details[i].getFreevalue15());
				tempfree.setFreevalue16(details[i].getFreevalue16());
				tempfree.setFreevalue17(details[i].getFreevalue17());
				tempfree.setFreevalue18(details[i].getFreevalue18());
				tempfree.setFreevalue19(details[i].getFreevalue19());
				tempfree.setFreevalue20(details[i].getFreevalue20());
				tempfree.setFreevalue21(details[i].getFreevalue21());
				tempfree.setFreevalue22(details[i].getFreevalue22());
				tempfree.setFreevalue23(details[i].getFreevalue23());
				tempfree.setFreevalue24(details[i].getFreevalue24());
				tempfree.setFreevalue25(details[i].getFreevalue25());
				tempfree.setFreevalue26(details[i].getFreevalue26());
				tempfree.setFreevalue27(details[i].getFreevalue27());
				tempfree.setFreevalue28(details[i].getFreevalue28());
				tempfree.setFreevalue29(details[i].getFreevalue29());
				tempfree.setFreevalue30(details[i].getFreevalue30());
				tempfreevalue.addElement(tempfree);
			}
		}
		DtlfreevalueVO[] detailfreevalues = new DtlfreevalueVO[tempfreevalue
				.size()];
		tempfreevalue.copyInto(detailfreevalues);
		if (detailfreevalues.length > 0) {
			dmo3.insertArray(detailfreevalues);
		}

		// ɾ��˰����ϸ
		IVatDetailMngService vatService = null;
		vatService = NCLocator.getInstance().lookup(IVatDetailMngService.class);
		vatService.deleteVatDetailByVouchers(new String[] { voucher
				.getPk_voucher() });

		List<VatDetailVO> vatDetailList = new LinkedList<VatDetailVO>();
		DetailVO detailTemp = null;
		for (int i = 0; i < details.length; i++) {
			detailTemp = details[i];
			detailTemp.setPk_detail(pks[i]);
			if (detailTemp.getVatdetail() != null) {
				detailTemp.getVatdetail().setPk_detail(
						detailTemp.getPk_detail());
				detailTemp.getVatdetail().setPk_voucher(
						detailTemp.getPk_voucher());
				detailTemp.getVatdetail().setPk_vatdetail(null);
				detailTemp.getVatdetail().setVoucherno(detailTemp.getNo());
				vatDetailList.add(detailTemp.getVatdetail());
			}
		}

		if (vatDetailList != null && vatDetailList.size() > 0) {
			VatDetailVO[] newVatDetails = vatService
					.insertVATDetailVO(vatDetailList
							.toArray(new VatDetailVO[0]));
			HashMap<String, VatDetailVO> vatMap = new HashMap<String, VatDetailVO>();
			for (VatDetailVO vatdetail : newVatDetails) {
				vatMap.put(vatdetail.getPk_detail(), vatdetail);
			}
			for (int i = 0; i < details.length; i++) {
				detailTemp = details[i];
				detailTemp.setVatdetail(vatMap.get(detailTemp.getPk_detail()));
			}
		}

		voucher.setDetails(details);
	}

	private void dealOfferFlag(VoucherVO voucherVo) throws BusinessException {
		if (voucherVo == null)
			return;
		if (voucherVo.getOffervoucher() != null) {
			voucherVo.setIsOffer(UFBoolean.TRUE);
			return;
		}

		String pk_voucher = voucherVo.getPk_voucher();
		VoucherExtendDMO voucherDMO;
		try {
			voucherDMO = new VoucherExtendDMO();
			VoucherVO[] queryByCondition = voucherDMO.queryByCondition(
					" offervoucher='" + pk_voucher + "'",
					voucherVo.getPk_group());
			if (queryByCondition != null && queryByCondition.length > 0) {
				voucherVo.setIsOffer(UFBoolean.TRUE);
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
	}

	/**
	 * ֻ��ѯƾ֤��ͷ��detailVO û��ע���¼�У��
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO[] �鵽��VO��������
	 * @param unitCode
	 *            int
	 * @BusinessException BusinessException �쳣˵����
	 */
	public VoucherVO queryOnlyVoucherVoByPk(String pk_voucher)
			throws BusinessException {

		VoucherVO voucher = null;
		try {
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			voucher = dmo.queryByVoucherPk(pk_voucher);
			if (voucher == null)
				return null;

			DetailExtendDMO dmo1 = new DetailExtendDMO();
			DetailVO[] details = dmo1.queryByVoucherPk(voucher.getPk_voucher());
			if (details != null && details.length > 0) {
				details = catDtlFreevalue(details);
				details = catCashFlows(details);
				details = catAss(details);
				// ˰����ϸ
				details = catVatDetails(details);
			}
			voucher.setDetails(details);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return voucher;
	}

	/**
	 * ֻ��ѯƾ֤��ͷ��detailVO û��ע���¼�У��
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO[] �鵽��VO��������
	 * @param unitCode
	 *            int
	 * @BusinessException BusinessException �쳣˵����
	 */
	public VoucherVO queryVoucherVoForCheck(String pk_voucher)
			throws BusinessException {

		VoucherVO voucher = null;
		try {
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			voucher = dmo.queryByVoucherPk(pk_voucher);
			if (voucher == null)
				return null;

			DetailExtendDMO dmo1 = new DetailExtendDMO();
			DetailVO[] details = dmo1.queryByVoucherPk(voucher.getPk_voucher());
			if (details != null && details.length > 0) {
				List<String> detailList = new ArrayList<String>();
				for (int i = 0; i < details.length; i++) {
					detailList.add(details[i].getPk_detail());
				}
				String[] recDetailPKs = getRecDetailPKs(detailList
						.toArray(new String[0]));
				catDetailMatchingflag(details, recDetailPKs);
				details = catSubjName(details);
				details = catAss(details);
			}
			voucher.setDetails(details);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return voucher;
	}

	/**
	 * 
	 * ��˰����ϸ
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param details
	 * @return
	 * @throws BusinessException
	 * @see
	 * @since V6.1
	 */
	private DetailVO[] catVatDetails(DetailVO[] details)
			throws BusinessException {
		if (details == null || details.length <= 0) {
			return details;
		}

		String[] pks = new String[details.length];
		for (int i = 0; i < details.length; i++) {
			pks[i] = details[i].getPk_detail();
		}

		VatDetailVO[] vatdetails = NCLocator.getInstance()
				.lookup(IVatDetailQryService.class)
				.queryVatDetailVOByDetail(pks);

		if (vatdetails == null || vatdetails.length <= 0) {
			return details;
		}

		HashMap<String, VatDetailVO> vatMap = new HashMap<String, VatDetailVO>();
		for (VatDetailVO vatdetail : vatdetails) {
			vatMap.put(vatdetail.getPk_detail(), vatdetail);
		}

		for (int i = 0; i < details.length; i++) {
			details[i].setVatdetail(vatMap.get(details[i].getPk_detail()));
		}
		return details;
	}

	/**
	 * ����VO�����趨�������������з���������VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO[]
	 * @param voucherVO
	 *            nc.vo.gl.voucher.VoucherVO
	 * @param isAnd
	 *            boolean ����������ѯ�����Ի�������ѯ
	 * @BusinessException java.sql.SQLBusinessException �쳣˵����
	 */
	public VoucherVO[] queryByPks(String[] strPks) throws BusinessException {

		VoucherVO[] vouchers = null;
		try {
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			vouchers = dmo.queryByPks(strPks);
			if (vouchers == null)
				return null;
			String[] pks = new String[vouchers.length];
			for (int i = 0; i < vouchers.length; i++) {
				pks[i] = vouchers[i].getPk_voucher();
			}
			String[] contrasted = getRecVoucherPKs(pks);
			catVoucherMatchingflag(vouchers, contrasted);
			catCorpname(vouchers, getCorps());
			catSystemname(vouchers);
			catVouchertypename(vouchers,
					getVouchertypes(vouchers[0].getPk_accountingbook()));
			catVoucherFreeValue(vouchers);
			// catUsername(vouchers, getUsers(null));
			catDetailMatchingFlagForOffer(vouchers);

			DetailExtendDMO dmo1 = new DetailExtendDMO();
			DetailVO[] details = dmo1.queryByVoucherPks(strPks);
			details = catCurrcode(details, getCurrtypes());

			String[] pk_accsubj = new String[details.length];
			String detailPKs[] = new String[details.length];
			for (int j = 0; j < details.length; j++) {
				pk_accsubj[j] = details[j].getPk_accasoa();
				detailPKs[j] = details[j].getPk_detail();
			}

			String[] pk_details = getRecDetailPKsByVoucherPK(pks, detailPKs);
			details = catDetailMatchingflag(details, pk_details);

			// String detailPks[] = new String[details.length];
			// for (int i = 0; i < detailPks.length; i++) {
			// detailPks[i] = details[i].getPk_detail();
			// }
			// details = catDetailMatchingflag(details, detailPks);
			details = catSubjName(details);
			// details = catAppend(details);
			details = catDtlFreevalue(details);
			// details = catSubjfreevalue(details);
			details = catCashFlows(details);
			details = catCheckstylename(details);
			details = catAss(details);
			// ˰����ϸ
			details = catVatDetails(details);
			catDetails(vouchers, details);
			sort(vouchers);
			dealOfferFlag(vouchers);
		} catch (Exception e) {
			// log.error(e);
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return vouchers;
	}

	/**
	 * ����VO�����趨�������������з���������VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO[]
	 * @param voucherVO
	 *            nc.vo.gl.voucher.VoucherVO
	 * @param isAnd
	 *            boolean ����������ѯ�����Ի�������ѯ
	 * @BusinessException java.sql.SQLBusinessException �쳣˵���� modified
	 *                    V6.3ֻ��ѯ�������ݣ�ȥ��һЩУ�顢���Ʒ����
	 */
	public VoucherVO[] queryOnlyVoucherVOsByPks(String[] strPks)
			throws BusinessException {

		VoucherVO[] vouchers = null;
		try {
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			vouchers = dmo.queryByPks(strPks);
			if (vouchers == null)
				return null;
			String[] pks = new String[vouchers.length];
			for (int i = 0; i < vouchers.length; i++) {
				pks[i] = vouchers[i].getPk_voucher();
			}
			// String[] contrasted = getRecVoucherPKs(pks);
			// catVoucherMatchingflag(vouchers, contrasted);
			// catCorpname(vouchers, getCorps());
			// catSystemname(vouchers);
			catVouchertypename(vouchers,
					getVouchertypes(vouchers[0].getPk_accountingbook()));
			catVoucherFreeValue(vouchers);
			// catUsername(vouchers, getUsers(null));
			// catDetailMatchingFlagForOffer(vouchers);

			DetailExtendDMO dmo1 = new DetailExtendDMO();
			DetailVO[] details = dmo1.queryByVoucherPks(strPks);
			details = catCurrcode(details, getCurrtypes());

			String[] pk_accsubj = new String[details.length];
			String detailPKs[] = new String[details.length];
			for (int j = 0; j < details.length; j++) {
				pk_accsubj[j] = details[j].getPk_accasoa();
				detailPKs[j] = details[j].getPk_detail();
			}

			// String[] pk_details = getRecDetailPKsByVoucherPK(pks, detailPKs);
			// details = catDetailMatchingflag(details, detailPKs);

			String detailPks[] = new String[details.length];
			for (int i = 0; i < detailPks.length; i++) {
				detailPks[i] = details[i].getPk_detail();
			}
			// details = catDetailMatchingflag(details, detailPks);
			// details = catSubjName(details);
			// details = catAppend(details);
			details = catDtlFreevalue(details);
			// details = catSubjfreevalue(details);
			details = catCashFlows(details);
			// details = catCheckstylename(details);
			details = catAss(details);
			// ˰����ϸ
			details = catVatDetails(details);
			catDetails(vouchers, details);
			sort(vouchers);
		} catch (Exception e) {
			// log.error(e);
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return vouchers;
	}

	/**
	 * ����VO�����趨�������������з���������VO����
	 * 
	 * �������ڣ�(2001-9-19)
	 * 
	 * @return nc.vo.gl.voucher.VoucherVO[]
	 * @param voucherVO
	 *            nc.vo.gl.voucher.VoucherVO
	 * @param isAnd
	 *            boolean ����������ѯ�����Ի�������ѯ
	 * @BusinessException java.sql.SQLBusinessException �쳣˵����
	 */
	public VoucherVO[] queryByVO(VoucherVO condVoucherVO, Boolean isAnd)
			throws BusinessException {

		VoucherVO[] vouchers = null;
		try {
			VoucherDMO dmo = new VoucherDMO();
			vouchers = dmo.queryByVO(condVoucherVO, isAnd);
			if (vouchers == null || vouchers.length == 0)
				return null;
			String[] pks = new String[vouchers.length];
			for (int i = 0; i < vouchers.length; i++) {
				pks[i] = vouchers[i].getPk_voucher();
			}
			String[] contrasted = getRecVoucherPKs(pks);

			catVoucherMatchingflag(vouchers, contrasted);
			catCorpname(vouchers, getCorps());
			catSystemname(vouchers);
			catVouchertypename(vouchers,
					getVouchertypes(condVoucherVO.getPk_accountingbook()));
			catVoucherFreeValue(vouchers);
			// catUsername(vouchers, getUsers(null));
			dealOfferFlag(vouchers);
			sort(vouchers);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return vouchers;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-19 17:54:14)
	 * 
	 * @return nc.vo.gl.pubvoucher.OperationResultVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 * @param isneedcheck
	 *            TODO
	 */
	private OperationResultVO[] saveVoucher(VoucherVO voucher,
			boolean isneedcheck, HashMap tempaccsubj) throws Exception {
		String verifyclass = "nc.bs.gl.verifysyn.InsertBalance";
		String tbClass = "nc.bs.gl.bugetinterface.BugetInterface";
		long tb = System.currentTimeMillis();
		OperationResultVO[] result = null;
		boolean sendOtherSys = ((IVoucherPfxxService) NCLocator.getInstance()
				.lookup(IVoucherPfxxService.class)).isSendOtherSys(voucher
				.getPk_org());
		//shidl
		// if (sendOtherSys) {
		// if (voucher.getPk_voucher() != null) {
		// return null;
		// }
		//
		// this.getVoucherNumberForSave(voucher);
		// result = ((IVoucherPfxxService) NCLocator.getInstance().lookup(
		// IVoucherPfxxService.class)).saveVoucher(voucher);
		// }

		this.appendDetailHead(voucher);
		DealclassDMO dealclassdmo = new DealclassDMO();
		DealclassVO[] dealclassvos = dealclassdmo.queryByModulgroup("save");
		int oldVoucher;
		int isVoucherTimeOrdered;
		VoucherSaveInterfaceVO tt;
		IVoucherSave t_result2;
		//shidl
//		if (sendOtherSys) {
//			DealclassVO[] vecsaveclass = dealclassvos;
//			oldVoucher = dealclassvos.length;
//
//			for (isVoucherTimeOrdered = 0; isVoucherTimeOrdered < oldVoucher; ++isVoucherTimeOrdered) {
//				DealclassVO r_result = vecsaveclass[isVoucherTimeOrdered];
//				if (r_result.getClassname().equals(
//						"nc.impl.fip.pub.gl.FipSaveListener4GL")) {
//					OperationResultVO[] t = null;
//					tt = new VoucherSaveInterfaceVO();
//					tt.pk_user = voucher.getPk_prepared();
//					tt.voucher = voucher;
//					tt.userdata = voucher.getUserData();
//					t_result2 = (IVoucherSave) ObjectCreator.newInstance(
//							r_result.getModules(), r_result.getClassname());
//					t = t_result2.afterSave(tt);
//					result = OperationResultVO.appendResultVO(result, t);
//					return result;
//				}
//			}
//		}

		Vector arg29 = new Vector();
		if (dealclassvos != null && dealclassvos.length != 0) {
			for (oldVoucher = 0; oldVoucher < dealclassvos.length; ++oldVoucher) {
				if (dealclassvos[oldVoucher].getModules() != null) {
					try {
						IVoucherSave arg31;
						if (isneedcheck) {
							arg31 = (IVoucherSave) ObjectCreator.newInstance(
									dealclassvos[oldVoucher].getModules(),
									dealclassvos[oldVoucher].getClassname());
							arg29.addElement(arg31);
						} else {
							if (dealclassvos[oldVoucher].getClassname().equals(
									verifyclass)) {
								arg31 = (IVoucherSave) ObjectCreator
										.newInstance(dealclassvos[oldVoucher]
												.getModules(),
												dealclassvos[oldVoucher]
														.getClassname());
								arg29.addElement(arg31);
							}

							if (dealclassvos[oldVoucher].getClassname().equals(
									tbClass)) {
								arg31 = (IVoucherSave) ObjectCreator
										.newInstance(dealclassvos[oldVoucher]
												.getModules(),
												dealclassvos[oldVoucher]
														.getClassname());
								arg29.addElement(arg31);
							}
						}
					} catch (FrameworkRuntimeException arg27) {
						Logger.error(arg27.getMessage(), arg27);
						throw new BusinessException(arg27.getMessage(), arg27);
					} catch (NullPointerException arg28) {
						Logger.error(arg28.getMessage(), arg28);
						throw new BusinessException(arg28.getMessage(), arg28);
					}
				}
			}
		}

		if (voucher.getTempsaveflag() != null
				&& voucher.getTempsaveflag().booleanValue()) {
			dealclassvos = dealclassdmo.queryByModulgroup("tempsave");
			if (dealclassvos != null && dealclassvos.length != 0) {
				for (oldVoucher = 0; oldVoucher < dealclassvos.length; ++oldVoucher) {
					if (dealclassvos[oldVoucher].getModules() != null) {
						IVoucherTempSave arg33 = (IVoucherTempSave) ObjectCreator
								.newInstance(
										dealclassvos[oldVoucher].getModules(),
										dealclassvos[oldVoucher].getClassname());
						arg29.addElement(arg33);
					}
				}
			}
		}

		if (voucher.getAddclass() != null && !voucher.getAddclass().equals("")) {
			try {
				Class arg30 = Class.forName(voucher.getAddclass());
				Object arg36 = arg30.newInstance();
				IVoucherSave arg32 = (IVoucherSave) arg36;
				if (isneedcheck) {
					arg29.addElement(arg32);
				}
			} catch (ClassNotFoundException arg25) {
				;
			} catch (NoClassDefFoundError arg26) {
				;
			}
		}

		if (GlDebugFlag.$DEBUG) {
			Log.getInstance(this.getClass().getName()).info(
					"SaveVoucher time report::findAllInterface time:"
							+ (System.currentTimeMillis() - tb) + "ms");
		}

		for (oldVoucher = 0; oldVoucher < arg29.size(); ++oldVoucher) {
			VoucherSaveInterfaceVO arg38 = new VoucherSaveInterfaceVO();
			arg38.pk_user = voucher.getPk_prepared();
			arg38.voucher = voucher;
			arg38.userdata = voucher.getUserData();
			long arg35 = System.currentTimeMillis();
			tt = null;
			OperationResultVO[] arg46;
			if (arg29.elementAt(oldVoucher) instanceof IVoucherSave) {
				arg46 = ((IVoucherSave) arg29.elementAt(oldVoucher))
						.beforeSave(arg38);
			} else {
				arg46 = ((IVoucherTempSave) arg29.elementAt(oldVoucher))
						.beforeSave(arg38);
			}

			if (GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::"
								+ arg29.elementAt(oldVoucher).getClass()
								+ ".BeforeSave time:"
								+ (System.currentTimeMillis() - arg35) + "ms");
			}

			result = OperationResultVO.appendResultVO(result, arg46);
			if (GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::"
								+ arg29.elementAt(oldVoucher).getClass()
								+ ".BeforeSave times:"
								+ (System.currentTimeMillis() - arg35) + "ms");
			}
		}

		VoucherVO arg34 = null;
		if (voucher.getPk_voucher() != null) {
			arg34 = this.queryVoucherVoForCheck(voucher.getPk_voucher());
		}

		if (arg34 == null) {
			DealclassVO[] arg40 = dealclassdmo.queryByModulgroup("add");
			Vector arg37 = new Vector();
			Vector arg41 = new Vector();
			int arg48;
			if (arg40 != null && arg40.length != 0) {
				for (arg48 = 0; arg48 < arg40.length; ++arg48) {
					if (arg40[arg48].getModules() != null) {
						try {
							t_result2 = (IVoucherSave) ObjectCreator
									.newInstance(arg40[arg48].getModules(),
											arg40[arg48].getClassname());
							if (isneedcheck) {
								arg37.addElement(t_result2);
							}
						} catch (FrameworkRuntimeException arg24) {
							Logger.error(arg24.getMessage(), arg24);
							throw new BusinessException(arg24.getMessage(),
									arg24);
						}
					}
				}
			}

			long m;
			OperationResultVO[] t1;
			for (arg48 = 0; arg48 < arg37.size(); ++arg48) {
				VoucherSaveInterfaceVO arg49 = new VoucherSaveInterfaceVO();
				arg49.pk_user = voucher.getPk_prepared();
				arg49.voucher = voucher;
				arg49.userdata = voucher.getUserData();
				m = System.currentTimeMillis();
				t1 = ((IVoucherSave) arg37.elementAt(arg48)).beforeSave(arg49);
				if (GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::"
									+ arg37.elementAt(arg48).getClass()
									+ ".BeforeSave time:"
									+ (System.currentTimeMillis() - m) + "ms");
				}

				result = OperationResultVO.appendResultVO(result, t1);
				if (GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::"
									+ arg37.elementAt(arg48).getClass()
									+ ".BeforeSave times:"
									+ (System.currentTimeMillis() - m) + "ms");
				}
			}

			if (voucher.getOffervoucher() != null) {
				DealclassVO[] arg53 = dealclassdmo.queryByModulgroup("offer");
				if (arg53 != null && arg53.length != 0) {
					for (int arg50 = 0; arg50 < arg53.length; ++arg50) {
						if (arg53[arg50].getModules() != null) {
							try {
								IVoucherOffer arg51 = (IVoucherOffer) ObjectCreator
										.newInstance(arg53[arg50].getModules(),
												arg53[arg50].getClassname());
								if (isneedcheck) {
									arg41.addElement(arg51);
								}
							} catch (FrameworkRuntimeException arg23) {
								Logger.error(arg23.getMessage(), arg23);
								throw new BusinessException(arg23.getMessage(),
										arg23);
							}
						}
					}
				}
			}

			for (arg48 = 0; arg48 < arg41.size(); ++arg48) {
				VoucherOperateInterfaceVO arg52 = new VoucherOperateInterfaceVO();
				arg52.pk_user = voucher.getPk_prepared();
				arg52.pk_vouchers = new String[] { voucher.getOffervoucher() };
				arg52.userdata = voucher.getUserData();
				m = System.currentTimeMillis();
				t1 = ((IVoucherOffer) arg41.elementAt(arg48))
						.beforeOffer(arg52);
				if (GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::"
									+ arg41.elementAt(arg48).getClass()
									+ ".BeforeSave time:"
									+ (System.currentTimeMillis() - m) + "ms");
				}

				result = OperationResultVO.appendResultVO(result, t1);
				if (GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::"
									+ arg41.elementAt(arg48).getClass()
									+ ".BeforeSave times:"
									+ (System.currentTimeMillis() - m) + "ms");
				}
			}

			long arg57 = System.currentTimeMillis();
			voucher = this.insert(voucher);

			OperationResultVO[] t_result21;
			int arg54;
			long arg58;
			for (arg54 = 0; arg54 < arg41.size(); ++arg54) {
				VoucherOperateInterfaceVO iSaveVO = new VoucherOperateInterfaceVO();
				iSaveVO.pk_user = voucher.getPk_prepared();
				iSaveVO.pk_vouchers = new String[] { voucher.getPk_voucher() };
				iSaveVO.userdata = voucher.getUserData();
				arg58 = System.currentTimeMillis();
				t_result21 = ((IVoucherOffer) arg41.elementAt(arg54))
						.afterOffer(iSaveVO);
				if (GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::"
									+ arg41.elementAt(arg54).getClass()
									+ ".BeforeSave time:"
									+ (System.currentTimeMillis() - arg58)
									+ "ms");
				}

				result = OperationResultVO.appendResultVO(result, t_result21);
				if (GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::"
									+ arg41.elementAt(arg54).getClass()
									+ ".BeforeSave times:"
									+ (System.currentTimeMillis() - arg58)
									+ "ms");
				}
			}

			if (GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Insert time:"
								+ (System.currentTimeMillis() - arg57) + "ms");
			}

			for (arg54 = arg37.size(); arg54 > 0; --arg54) {
				VoucherSaveInterfaceVO arg56 = new VoucherSaveInterfaceVO();
				arg56.pk_user = voucher.getPk_prepared();
				arg56.voucher = voucher;
				arg56.userdata = voucher.getUserData();
				arg58 = System.currentTimeMillis();
				t_result21 = ((IVoucherSave) arg37.elementAt(arg54 - 1))
						.afterSave(arg56);
				if (GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::"
									+ arg37.elementAt(arg54 - 1).getClass()
									+ ".AfterSave time:"
									+ (System.currentTimeMillis() - arg58)
									+ "ms");
				}

				result = OperationResultVO.appendResultVO(result, t_result21);
				if (GlDebugFlag.$DEBUG) {
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::"
									+ arg37.elementAt(arg54 - 1).getClass()
									+ ".AfterSave times:"
									+ (System.currentTimeMillis() - arg58)
									+ "ms");
				}
			}
		} else {
			long arg43 = System.currentTimeMillis();
			VoucherVO arg44 = (VoucherVO) voucher.clone();
			result = OperationResultVO.appendResultVO(result,
					this.checkVoucherEdit(arg44, arg34));
			if (voucher.getNo() == null || voucher.getNo().intValue() == 0) {
				voucher.setNo(arg34.getNo());
			}

			voucher = this.update(voucher);
			arg44.setNo(voucher.getNo());
			GLBusiLogUtil.insertSmartBusiLog(arg44, arg34,
					"d0a97642-327d-43f3-8f90-bca3e91edbd3",
					GLBusiLogOperateConst.CODE_EDIT);
			if (GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Update time:"
								+ (System.currentTimeMillis() - arg43) + "ms");
			}
		}

		this.updateTs(voucher);

		for (isVoucherTimeOrdered = arg29.size(); isVoucherTimeOrdered > 0; --isVoucherTimeOrdered) {
			VoucherSaveInterfaceVO arg39 = new VoucherSaveInterfaceVO();
			arg39.pk_user = voucher.getPk_prepared();
			arg39.voucher = voucher;
			arg39.userdata = voucher.getUserData();
			long arg45 = System.currentTimeMillis();
			t_result2 = null;
			OperationResultVO[] arg55;
			if (arg29.elementAt(isVoucherTimeOrdered - 1) instanceof IVoucherSave) {
				arg55 = ((IVoucherSave) arg29
						.elementAt(isVoucherTimeOrdered - 1)).afterSave(arg39);
			} else {
				arg55 = ((IVoucherTempSave) arg29
						.elementAt(isVoucherTimeOrdered - 1)).afterSave(arg39);
			}

			if (GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::"
								+ arg29.elementAt(isVoucherTimeOrdered - 1)
										.getClass() + ".AfterSave time:"
								+ (System.currentTimeMillis() - arg45) + "ms");
			}

			result = OperationResultVO.appendResultVO(result, arg55);
			if (GlDebugFlag.$DEBUG) {
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::"
								+ arg29.elementAt(isVoucherTimeOrdered - 1)
										.getClass() + ".AfterSave times:"
								+ (System.currentTimeMillis() - arg45) + "ms");
			}
		}

		UFBoolean arg47 = ((IGlPara) NCLocator.getInstance().lookup(
				IGlPara.class)).isVoucherTimeOrdered(voucher
				.getPk_accountingbook());
		if (arg47 != null && arg47.booleanValue()
				&& voucher.getVoucherkind() != null
				&& voucher.getVoucherkind().intValue() != 2) {
			result = OperationResultVO.appendResultVO(result,
					(new VoucherCheckBO()).checkTimeOrdered(voucher));
		}

		OperationResultVO arg42 = new OperationResultVO();
		arg42.m_intSuccess = 0;
		arg42.m_strDescription = null;
		arg42.m_strPK = voucher.getPk_voucher();
		arg42.m_userIdentical = voucher.clone();
		result = OperationResultVO.appendResultVO(
				new OperationResultVO[] { arg42 }, result);
		if (GlDebugFlag.$DEBUG) {
			Log.getInstance(this.getClass().getName()).info(
					"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Complete Saved time:"
							+ (System.currentTimeMillis() - tb) + "ms");
		}
		// shidl-20170314-�޸ĵ�����֮���ٷ�������
		// ���Ҫ���͵�����ϵͳ���������ֱ�ӷ���
		// boolean sendOtherSys = NCLocator.getInstance()
		// .lookup(IVoucherPfxxService.class)
		// .isSendOtherSys(voucher.getPk_org());
		if (sendOtherSys) {
			NCLocator.getInstance().lookup(IVoucherPfxxService.class)
					.saveVoucher(voucher);

			// return result;

		}
		return result;
	}

	private void updateTs(VoucherVO voucherVo) throws Exception {
		if (voucherVo == null || voucherVo.getPk_voucher() == null)
			return;

		// ��������ts
		VoucherExtendDMO dmo = new VoucherExtendDMO();
		VoucherVO newVoucherVo = dmo
				.queryByVoucherPk(voucherVo.getPk_voucher());

		if (newVoucherVo == null)
			return;

		voucherVo.setTs(newVoucherVo.getTs());
		// �����ӱ�ts
		DetailVO[] details = voucherVo.getDetails();
		if (details != null && details.length > 0) {
			DetailExtendDMO dmo1 = new DetailExtendDMO();

			DetailVO[] newDetailVOs = dmo1.queryByVoucherPk(voucherVo
					.getPk_voucher());

			Map<String, DetailVO> detailMap = new HashMap<String, DetailVO>();

			if (newDetailVOs != null && newDetailVOs.length > 0) {
				for (DetailVO detailVO : newDetailVOs) {
					detailMap.put(detailVO.getPk_detail(), detailVO);
				}
			}

			for (DetailVO detailVO : details) {
				DetailVO newDetailVo = detailMap.get(detailVO.getPk_detail());
				if (newDetailVo == null)
					continue;
				detailVO.setTs(newDetailVo.getTs());
			}
		}
	}

	/**
	 * 
	 * ���ƾ֤�Ƿ����޸�
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param saveBefore
	 * @param oldVoucher
	 * @return
	 * @see
	 * @since V6.1
	 */
	private OperationResultVO[] checkVoucherEdit(VoucherVO saveBefore,
			VoucherVO oldVoucher) {
		List<OperationResultVO> list = new LinkedList<OperationResultVO>();
		OperationResultVO r_result = null;
		if (oldVoucher.getTs() == null
				|| (saveBefore.getTs() != null && !oldVoucher.getTs().equals(
						saveBefore.getTs()))) {
			r_result = new OperationResultVO();
			r_result.m_intSuccess = OperationResultVO.ERROR;
			r_result.m_strDescription = new VoucherCheckMessage()
					.getVoucherMessage(VoucherCheckMessage.ErrMsgRefreshData);
			r_result.m_strPK = saveBefore.getPk_voucher();
			r_result.m_userIdentical = saveBefore.clone();
			list.add(r_result);
		}
		if (saveBefore.getDetails() == null || saveBefore.getNumDetails() <= 0
				|| oldVoucher.getDetails() == null
				|| oldVoucher.getNumDetails() <= 0) {
			return list.toArray(new OperationResultVO[0]);
		}
		HashMap<String, DetailVO> newDetailMap = new HashMap<String, DetailVO>();
		for (DetailVO detail : saveBefore.getDetails()) {
			if (detail.getPk_detail() != null) {
				newDetailMap.put(detail.getPk_detail(), detail);
			}
		}
		DetailVO newDetail = null;
		Object cloneObj = saveBefore.clone();
		for (DetailVO olddetail : oldVoucher.getDetails()) {
			newDetail = newDetailMap.get(olddetail.getPk_detail());
			// ��¼��ɾ��
			if (newDetail == null && olddetail.getIsmatched() != null
					&& olddetail.getIsmatched().booleanValue()) {
				r_result = new OperationResultVO();
				r_result.m_intSuccess = OperationResultVO.ERROR;
				r_result.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgRefreshData);
				r_result.m_strPK = saveBefore.getPk_voucher();
				r_result.m_userIdentical = cloneObj;
				list.add(r_result);
			} else if (olddetail.getTs() == null
					|| (newDetail != null && newDetail.getTs() == null)
					|| (newDetail != null && !newDetail.getTs().equals(
							olddetail.getTs()))) {
				r_result = new OperationResultVO();
				r_result.m_intSuccess = OperationResultVO.ERROR;
				r_result.m_strDescription = new VoucherCheckMessage()
						.getVoucherMessage(VoucherCheckMessage.ErrMsgRefreshData);
				r_result.m_strPK = saveBefore.getPk_voucher();
				r_result.m_userIdentical = cloneObj;
				list.add(r_result);
			}
		}

		return list.toArray(new OperationResultVO[0]);
	}

	private void fixOffsetAmount(VoucherVO voucher) {
		voucher.setTotalcredit(UFDouble.ZERO_DBL.sub(voucher.getTotalcredit()));
		voucher.setTotaldebit(UFDouble.ZERO_DBL.sub(voucher.getTotaldebit()));
	}

	public void runtimeReconcile(VoucherVO voucher) {
		if (GLParaDataCacheUseUap.getRunTimeReconcile(voucher
				.getPk_accountingbook()) == nc.vo.glcom.para.ParaMacro.RUNTIMERECONCILE_YES) {

			IPubReconcile pubreconcile = (IPubReconcile) NCLocator
					.getInstance().lookup(IPubReconcile.class.getName());
			PubReconcileInfoVO reconcileinfo = new PubReconcileInfoVO();
			reconcileinfo.setUserid(voucher.getPk_prepared());
			reconcileinfo.setReconcileDate(voucher.getPrepareddate());
			reconcileinfo.setPk_glorgbook(voucher.getPk_accountingbook());
			try {
				pubreconcile.pubReconcile(voucher, reconcileinfo);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				Logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-19 17:54:14)
	 * 
	 * @return nc.vo.gl.pubvoucher.OperationResultVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO voucher��ƾ֤VO
	 *            isneedcheck���Ƿ���Ҫ���м��
	 */
	public OperationResultVO[] save(VoucherVO voucher, Boolean isneedcheck)
			throws BusinessException {
		// beforeVoucherLog(voucher);
		long tb = System.currentTimeMillis();
		try {
			HashMap tempaccsubj = getAccountMap(voucher, null);
			UFBoolean buStart = GLParaAccessor.isSecondBUStart(voucher
					.getPk_accountingbook());
			if (buStart != null && !buStart.booleanValue()) {
				for (DetailVO vo : voucher.getDetails()) {
					if (!StrTools.isEmptyStr(vo.getPk_unit())) {
						if (vo.getPk_org() != null
								&& !vo.getPk_org().equals(vo.getPk_unit())) {

							throw new BusinessException("ҵ��Ԫ���ݴ��󣬸��˲�δ���ö������㣡");
						}
					}
				}
			}
			checkMaxNum(voucher);
			voucher = catOrgAndBook(voucher);
			voucher = catOppositesubj(voucher);
			voucher = catRegulationPeriod(voucher);
			voucher = catDetailPk_corp(voucher);
			voucher = dealLocalAmount(voucher); // hurh �����š�ȫ�ֱ��ҽ�δ����ʱ�����
			voucher = computeTotal(voucher); // ����ϼ�
			voucher = catDefaultData(voucher, tempaccsubj);

			//
			if (voucher.getFree7() != null
					&& voucher.getFree7().startsWith("dap_save_Action")) {
				voucher.setFree7(null);
			}
			OperationResultVO[] checkresult = null;
			if (isneedcheck != null && isneedcheck.booleanValue()) {
				// ���ƾ֤�ĺϷ���
				checkresult = new VoucherCheckBO().checkVoucher(voucher,
						new VoucherCheckConfigVO(), tempaccsubj);
				if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Check time:"
									+ (System.currentTimeMillis() - tb) + "ms");
				if (checkresult != null) {
					StringBuffer strMsg = new StringBuffer();
					boolean errflag = false;
					for (int i = 0; i < checkresult.length; i++) {
						switch (checkresult[i].m_intSuccess) {
						case 0:
							break;
						case 1:
							strMsg.append(nc.bs.ml.NCLangResOnserver
									.getInstance().getStrByID("2002100556",
											"UPP2002100556-000119")/*
																	 * @res
																	 * "����:"
																	 */
									+ checkresult[i].m_strDescription + "\n");
							break;
						case 2:
							errflag = true;
							strMsg.append(nc.bs.ml.NCLangResOnserver
									.getInstance().getStrByID("2002100556",
											"UPP2002100556-000120")/*
																	 * @res
																	 * "����:"
																	 */
									+ checkresult[i].m_strDescription + "\n");
							break;
						default:
							strMsg.append(nc.bs.ml.NCLangResOnserver
									.getInstance().getStrByID("2002100556",
											"UPP2002100556-000121")/*
																	 * @res
																	 * "��Ϣ:"
																	 */
									+ checkresult[i].m_strDescription + "\n");
						}
					}
					if (strMsg.length() > 0 && errflag)
						throw new BusinessException(strMsg.toString());
				}
			} else {
				checkresult = checkCanSystemTempSave(voucher);
			}

			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName())
						.info("SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Check and Adjust Result time:"
								+ (System.currentTimeMillis() - tb) + "ms");

			OperationResultVO[] saveresult = saveVoucher(voucher, isneedcheck,
					tempaccsubj);

			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Save time:"
								+ (System.currentTimeMillis() - tb) + "ms");
			if (saveresult != null) {
				StringBuffer strMsg = new StringBuffer();
				boolean errflag = false;
				for (int i = 0; i < saveresult.length; i++) {
					switch (saveresult[i].m_intSuccess) {

					case 0:

						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ saveresult[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ saveresult[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ saveresult[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag) {
					throw new BusinessException(strMsg.toString());
				} else {
					// ƾ֤��ʱЭͬ
					// int i
					// runtimeReconcile(voucher);
					//
				}
			}
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName())
						.info("SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Save and Adjust Result time:"
								+ (System.currentTimeMillis() - tb) + "ms");
			OperationResultVO[] results = OperationResultVO.appendResultVO(
					saveresult, checkresult);
			return results;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		} finally {
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Save all done time:"
								+ (System.currentTimeMillis() - tb) + "ms");
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-19 17:54:14)
	 * 
	 * @return nc.vo.gl.pubvoucher.OperationResultVO
	 * @param voucher
	 */
	public OperationResultVO[] saveRtVoucher(List<VoucherVO> vouchers)
			throws BusinessException {
		long tb = System.currentTimeMillis();
		try {
			for (int i = 0; i < vouchers.size(); i++) {
				VoucherVO voucher = vouchers.get(i);
				voucher = catOrgAndBook(voucher);
				voucher = catOppositesubj(voucher);
				voucher = catRegulationPeriod(voucher);
				voucher = catDetailPk_corp(voucher);
				voucher = computeTotal(voucher);
			}
			String[] pk_vouchers = new RtVoucherDMO().insertArray(vouchers
					.toArray(new VoucherVO[0]));
			ArrayList<DetailVO> detailList = new ArrayList<DetailVO>();
			for (int i = 0; i < vouchers.size(); i++) {
				Vector<DetailVO> detailvec = vouchers.get(i).getDetail();
				for (DetailVO detailVO : detailvec) {
					detailVO.setPk_voucher(pk_vouchers[i]);
					if (detailVO.getPrepareddate() == null) {
						detailVO.setPrepareddate(vouchers.get(i)
								.getPrepareddate());
					}
					detailList.add(detailVO);
				}
				vouchers.get(i).setPk_voucher(pk_vouchers[i]);
			}
			DetailVO[] vos = detailList.toArray(new DetailVO[0]);
			new RtDetailDMO().insertArray(vos);
			{
				// �����ֽ�����

				// ɾ������ԭ���ı����¼
				CashflowcaseDMO dmo = new CashflowcaseDMO();
				CashFlowCaseOpDMO ddo = new CashFlowCaseOpDMO();
				CashflowcaseVO deleteVO = new CashflowcaseVO();
				// ddo.deleteByDetails(voucher,vos);
				// for (int i = 0; i < vos.length; i++) {
				// deleteVO.setPk_glorgbook(vos[i].getPk_glorgbook());
				// deleteVO.setPk_detail(vos[i].getPk_detail());
				// ddo.deleteCashflowcase(deleteVO);
				// }

				// ����VAT��Ϣ
				List<VatDetailVO> vatDetailVoList = new ArrayList<VatDetailVO>();

				// ����������ѯ���������ջ������ݣ����ڴ���VAT�Ľ��״���
				Set<String> countrySet = new HashSet<String>();

				// �����µı����¼
				for (int i = 0; i < vos.length; i++) {
					CashflowcaseVO[] cashflows = (CashflowcaseVO[]) vos[i]
							.getCashFlow();
					if (cashflows != null && cashflows.length > 0) {
						for (int j = 0; j < cashflows.length; j++) {
							cashflows[j].setPk_detail(vos[i].getPk_detail());
							cashflows[j].setPk_detail_opp(cashflows[j]
									.getM_pk_currtype());
							cashflows[j].setPk_glorgbook(vos[i]
									.getPk_glorgbook());
						}
						String[] key = dmo.insertArray(cashflows);
					}

					VatDetailVO vatDetailVo = vos[i].getVatdetail();
					if (vatDetailVo != null) {
						vatDetailVo.setPk_voucher(vos[i].getPk_voucher());
						vatDetailVo.setPk_detail(vos[i].getPk_detail());
						vatDetailVo.setPrepareddate(vos[i].getPrepareddate());
						vatDetailVo.setPk_unit(vos[i].getPk_unit());
						vatDetailVo.setPk_group(vos[i].getPk_group());
						vatDetailVo.setPk_accountingbook(vos[i]
								.getPk_accountingbook());
						vatDetailVo.setPk_accasoa(vos[i].getPk_accasoa());
						vatDetailVo.setExplanation(vos[i].getExplanation());
						vatDetailVo.setDetailindex(vos[i].getDetailindex());
						vatDetailVo.setPk_org(vos[i].getPk_org());
						vatDetailVo.setVoucherno(vos[i].getNo());
						vatDetailVo.setVoucherkind(VoucherkindEnum.TMPVOUCHER
								.getEnumValue().getValue());
						vatDetailVoList.add(vatDetailVo);

						UFDouble taxAmount = null;
						if (vos[i].getLocaldebitamount() != null
								&& !UFDouble.ZERO_DBL.equals(vos[i]
										.getLocaldebitamount())) {
							// ��������Ϊ�跽
							vatDetailVo.setDirection(DirectionEnum.DEBIT
									.getEnumValue().getValue());
							taxAmount = vos[i].getLocaldebitamount();
						} else if (vos[i].getLocalcreditamount() != null
								&& !UFDouble.ZERO_DBL.equals(vos[i]
										.getLocalcreditamount())) {
							vatDetailVo.setDirection(DirectionEnum.CREDIT
									.getEnumValue().getValue());
							taxAmount = vos[i].getLocalcreditamount();
						} else {// �����˰��¼��ֵΪ0��ȡ��Ŀ�ķ��� ��������൱���٣���Ч�������ٸ�
							String pk_accasoa = vos[i].getPk_accasoa();
							UFDate prepareddate = vos[i].getPrepareddate();
							AccountVO[] accountVOs = NCLocator
									.getInstance()
									.lookup(IAccountPubService.class)
									.queryAccountVOsByPks(
											new String[] { pk_accasoa },
											prepareddate.toStdString());
							if (accountVOs != null && accountVOs.length > 0) {
								Integer balanorient = accountVOs[0]
										.getBalanorient();
								if (balanorient != null)
									vatDetailVo.setDirection(balanorient
											.toString());
							}
						}
						vatDetailVo.setTaxamount(taxAmount);

						String sendcountryid = vatDetailVo.getSendcountryid();
						String pk_receivecountry = vatDetailVo
								.getPk_receivecountry();

						if (StringUtils.isNotEmpty(sendcountryid)) {
							countrySet.add(sendcountryid);
						}

						if (StringUtils.isNotEmpty(pk_receivecountry)) {
							countrySet.add(pk_receivecountry);
						}
					}
				}

				// ˰����ϸ
				if (vatDetailVoList.size() > 0) {

					// �����߼�Ϊ�˴����״��룬�����ջ��������������´���
					if (countrySet != null && countrySet.size() > 0) {

						CountryZoneVO[] countryVOs = NCLocator
								.getInstance()
								.lookup(ICountryQryService.class)
								.query(SqlUtils.getInStr(
										CountryZoneVO.PK_COUNTRY,
										countrySet.toArray(new String[0]),
										false));

						Map<String, CountryZoneVO> countryMap = new HashMap<String, CountryZoneVO>();

						if (countryVOs != null && countryVOs.length > 0) {
							for (CountryZoneVO countryZoneVO : countryVOs) {
								countryMap.put(countryZoneVO.getPk_country(),
										countryZoneVO);
							}
						}
						// ���״��� ����ջ�����������ͬ����ŷ���� ���ÿգ����������һ����ŷ���ڣ������������ó�ף��ѽ��״����ÿ�
						for (VatDetailVO vatDetailVo : vatDetailVoList) {
							String pk_receivecountry = vatDetailVo
									.getPk_receivecountry();
							String sendcountryid = vatDetailVo
									.getSendcountryid();
							if (StringUtils.isNotEmpty(pk_receivecountry)
									&& StringUtils.isNotEmpty(sendcountryid)) {

								CountryZoneVO receiveVo = countryMap
										.get(pk_receivecountry);
								CountryZoneVO sendVo = countryMap
										.get(sendcountryid);

								if (receiveVo != null && sendVo != null) {
									if (CountryZoneExVO.getIseucountry(
											receiveVo).booleanValue()) {// �ջ���Ϊŷ��
										if (CountryZoneExVO.getIseucountry(
												sendVo).booleanValue()) {// ������Ϊŷ��
											if (pk_receivecountry
													.equals(sendcountryid)) {// ������������ջ�����ͬ
																				// ��ΪD
												vatDetailVo
														.setBusinesscode(BusinessCodeEnum.D
																.getEnumValue()
																.getValue());
											}
										} else {// ��������Ϊŷ��
												// �����Ϊ����ó�� ����Ҫ�����״������
											String businesscode = vatDetailVo
													.getBusinesscode();
											if (businesscode != null
													&& !BusinessCodeEnum.T
															.getEnumValue()
															.getValue()
															.equals(businesscode)) {
												vatDetailVo
														.setBusinesscode(null);
											}
										}
									} else {// �ջ�����Ϊŷ��
										if (CountryZoneExVO.getIseucountry(
												sendVo).booleanValue()) {// ������Ϊŷ��
											// �����Ϊ����ó�� ����Ҫ�����״������
											String businesscode = vatDetailVo
													.getBusinesscode();
											if (businesscode != null
													&& !BusinessCodeEnum.T
															.getEnumValue()
															.getValue()
															.equals(businesscode)) {
												vatDetailVo
														.setBusinesscode(null);
											}
										} else {// ��������Ϊŷ��
											vatDetailVo.setBusinesscode(null);
										}
									}
								}
							}
						}
					}
					VatDetailVO[] vatdetails = NCLocator
							.getInstance()
							.lookup(IVatDetailMngService.class)
							.insertVATDetailVO(
									vatDetailVoList.toArray(new VatDetailVO[0]));
				}

			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
		return null;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-19 17:54:14)
	 * 
	 * @return nc.vo.gl.pubvoucher.OperationResultVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO voucher��ƾ֤VO
	 *            isneedcheck���Ƿ���Ҫ���м��
	 */
	public OperationResultVO[] save(VoucherVO voucher,
			VoucherCheckConfigVO configVO, Object userdata)
			throws BusinessException {
		long tb = System.currentTimeMillis();
		try {
			OperationResultVO[] checkresult = null;
			// ���ƾ֤�ĺϷ���
			HashMap tempaccsubj = getAccountMap(voucher, null);
			checkresult = new VoucherCheckBO().checkVoucher(voucher, configVO,
					tempaccsubj);
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Check time:"
								+ (System.currentTimeMillis() - tb) + "ms");
			if (checkresult != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < checkresult.length; i++) {
					switch (checkresult[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ checkresult[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ checkresult[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName())
						.info("SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Check and Adjust Result time:"
								+ (System.currentTimeMillis() - tb) + "ms");
			voucher = catOrgAndBook(voucher);
			voucher = catDetailPk_corp(voucher);
			voucher = catOppositesubj(voucher);
			voucher = catRegulationPeriod(voucher);
			voucher = catRegulationPeriod(voucher);
			voucher = catDefaultData(voucher, tempaccsubj);
			OperationResultVO[] saveresult = saveVoucher(voucher, true,
					tempaccsubj);
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Save time:"
								+ (System.currentTimeMillis() - tb) + "ms");
			if (saveresult != null) {
				StringBuffer strMsg = new StringBuffer(16 * 1024);
				boolean errflag = false;
				for (int i = 0; i < saveresult.length; i++) {
					switch (saveresult[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ saveresult[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ saveresult[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ saveresult[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName())
						.info("SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Save and Adjust Result time:"
								+ (System.currentTimeMillis() - tb) + "ms");
			OperationResultVO[] results = OperationResultVO.appendResultVO(
					saveresult, checkresult);
			return results;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		} finally {
			GLKeyLock lock = null;
			boolean bLockSuccess = false;
			try {
				lock = new GLKeyLock();
				for (int i = 0; i < 20; i++) {
					bLockSuccess = lock.lockKey(
							"GL_V_N_C_L" + voucher.getPk_accountingbook()
									+ voucher.getPk_vouchertype()
									+ voucher.getYear() + voucher.getPeriod(),
							voucher.getPk_prepared(), "gl_voucher");
					if (bLockSuccess)
						break;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
				if (!bLockSuccess)
					throw new BusinessException(nc.bs.ml.NCLangResOnserver
							.getInstance().getStrByID("20021005",
									"UPP20021005-000561")/*
														 * @res
														 * "�������û��ڲ���ƾ֤�����Ժ����ԡ�"
														 */);
				else {
					if (voucher.getVoucherkind().intValue() != VoucherKey.VOUCHERKIND_INIT) {
						VoucherExtendDMO dmo = new VoucherExtendDMO();
						Integer iCount = dmo.getCountVoucherNo(voucher);
						if (iCount.intValue() > 1) {
							throw new BusinessException(
									new VoucherCheckMessage()
											.getVoucherMessage(VoucherCheckMessage.ErrMsgNOExist));
						}
					}
				}
			} catch (BusinessException e) {
				Logger.error(e.getMessage(), e);
				throw e;
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				throw new BusinessException(e.getMessage(), e);
			} finally {
				try {
					if (bLockSuccess)
						lock.freeKey(
								"GL_V_N_C_L" + voucher.getPk_accountingbook()
										+ voucher.getPk_vouchertype()
										+ voucher.getYear()
										+ voucher.getPeriod(),
								voucher.getPk_prepared(), "gl_voucher");
				} catch (Throwable e) {
					Log.getInstance(this.getClass().getName()).info(
							nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("2002100556",
											"UPP2002100556-000122")/*
																	 * @res
																	 * "�ͷ����쳣:"
																	 */
									+ "GL_V_N_C_L");
					Logger.error(e.getMessage(), e);
				}
			}
			if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
				Log.getInstance(this.getClass().getName()).info(
						"SaveVoucher time report::nc.bs.gl.voucher.VoucherBO.Save all done time:"
								+ (System.currentTimeMillis() - tb) + "ms");
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-19 17:54:14)
	 * 
	 * @return nc.vo.gl.pubvoucher.OperationResultVO
	 * @param voucher
	 *            nc.vo.gl.pubvoucher.VoucherVO
	 */
	public OperationResultVO[] saveError(VoucherVO voucher)
			throws BusinessException {

		try {
			appendDetailHead(voucher);
			HashMap tempaccsubj = getAccountMap(voucher, null);
			catDefaultData4SaveError(voucher, tempaccsubj);
			OperationResultVO[] result = new VoucherCheckBO()
					.checkPreparedDate(voucher, tempaccsubj);
			if (result != null) {
				StringBuffer strMsg = new StringBuffer();
				boolean errflag = false;
				for (int i = 0; i < result.length; i++) {
					switch (result[i].m_intSuccess) {
					case 0:
						break;
					case 1:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000119")/*
																 * @res "����:"
																 */
								+ result[i].m_strDescription + "\n");
						break;
					case 2:
						errflag = true;
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000120")/*
																 * @res "����:"
																 */
								+ result[i].m_strDescription + "\n");
						break;
					default:
						strMsg.append(nc.bs.ml.NCLangResOnserver.getInstance()
								.getStrByID("2002100556",
										"UPP2002100556-000121")/*
																 * @res "��Ϣ:"
																 */
								+ result[i].m_strDescription + "\n");
					}
				}
				if (strMsg.length() > 0 && errflag)
					throw new BusinessException(strMsg.toString());
			}
			if (voucher.getPk_voucher() != null) {

				VoucherVO oldvoucher = new VoucherExtendDMO()
						.findByPrimaryKey(voucher.getPk_voucher());
				checkError(oldvoucher, result);

				// ��������ͬ������ƾ֤�����¼����ɾ��������¼���е���Ӧ���ݡ�
				// new
				// nc.bs.gl.verifybegin.VerifydetailDMO().deleteByVoucherPk(voucher.getPk_voucher());

				if (oldvoucher == null)
					throw new BusinessException(nc.bs.ml.NCLangResOnserver
							.getInstance().getStrByID("20021005",
									"UPP20021005-000562")/*
														 * @res "ƾ֤��ɾ����"
														 */);
				else {
					if (oldvoucher.getPk_checked() != null)
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000563")/*
															 * @res
															 * "ƾ֤����ˣ��޷����"
															 */);
					if (oldvoucher.getPk_manager() != null)
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000564")/*
															 * @res
															 * "ƾ֤�Ѽ��ˣ��޷����"
															 */);
				}
			}

			{
				long tt = System.currentTimeMillis();

				GLKeyLock lock = null;
				boolean bLockSuccess = false;
				try {
					lock = new GLKeyLock();
					for (int i = 0; i < 5; i++) {
						bLockSuccess = lock.lockKey(
								voucher.getPk_voucher() == null ? voucher
										.getPk_accountingbook()
										+ voucher.getPk_vouchertype()
										+ voucher.getYear()
										+ voucher.getPeriod() : voucher
										.getPk_voucher(), voucher
										.getPk_prepared(), "gl_voucher");
						if (bLockSuccess)
							break;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}
					if (!bLockSuccess)
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000539")/*
															 * @res
															 * "�������û��ڲ��������Ժ����ԡ�"
															 */);
					if (voucher.getNo() == null
							|| voucher.getNo().intValue() <= 0)
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000565")/*
															 * @res "�������Ч��ƾ֤�ţ�"
															 */
								+ voucher.getNo());
					else
						// checkVoucherNo(voucher);
						checkVoucherNumber(voucher);
					// ƾ֤����
					VoucherDMO dmo = new VoucherDMO();
					VoucherVO tmpvoucher = dmo.findByPrimaryKey(voucher
							.getPk_voucher());
					if (tmpvoucher == null)
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000566")/*
															 * @res
															 * "�����޸ĵ�ƾ֤�ѱ�����ɾ�����޷��������ݡ�"
															 */);
					else if (tmpvoucher.getDiscardflag() != null
							&& tmpvoucher.getDiscardflag().equals("Y"))
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000567")/*
															 * @res
															 * "�����޸ĵ�ƾ֤�ѱ��������ϡ��޷��������ݡ�"
															 */);
					else if (tmpvoucher.getPk_checked() != null)
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000568")/*
															 * @res
															 * "�����޸ĵ�ƾ֤�ѱ�������ˡ��޷��������ݡ�"
															 */);
					else if (tmpvoucher.getPk_manager() != null)
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000569")/*
															 * @res
															 * "�����޸ĵ�ƾ֤�ѱ����˼��ˡ��޷��������ݡ�"
															 */);
					int iCount = dmo.update(voucher);
					if (iCount == 0)
						throw new BusinessException(nc.bs.ml.NCLangResOnserver
								.getInstance().getStrByID("20021005",
										"UPP20021005-000566")/*
															 * @res
															 * "�����޸ĵ�ƾ֤�ѱ�����ɾ�����޷��������ݡ�"
															 */);
				} finally {
					if (bLockSuccess)
						lock.freeKey(
								voucher.getPk_voucher() == null ? voucher
										.getPk_accountingbook()
										+ voucher.getPk_vouchertype()
										+ voucher.getYear()
										+ voucher.getPeriod() : voucher
										.getPk_voucher(), voucher
										.getPk_prepared(), "gl_voucher");
				}

				// �����ӱ���Ϣ
				updateSubDetail(voucher);

				if (nc.vo.glcom.para.GlDebugFlag.$DEBUG)
					Log.getInstance(this.getClass().getName()).info(
							"SaveVoucher time report::" + this.getClass()
									+ "Update time:"
									+ (System.currentTimeMillis() - tt) + "ms");
			}

			updateTs(voucher);

			OperationResultVO r_result = new OperationResultVO();
			r_result.m_intSuccess = 0;
			r_result.m_strDescription = null;
			r_result.m_strPK = voucher.getPk_voucher();
			r_result.m_userIdentical = voucher.clone();
			result = OperationResultVO.appendResultVO(
					new OperationResultVO[] { r_result }, result);

			return result;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	private void checkError(VoucherVO oldvoucher, OperationResultVO[] result)
			throws Exception {
		if (oldvoucher.getPk_voucher() == null) {
			return;
		}

		String[] pkdetails = getRecDetailPKsByVoucherPK(new String[] { oldvoucher
				.getPk_voucher() });
		if (pkdetails != null && pkdetails.length > 0) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl
					.getNCLangRes()
					.getStrByID("2002gl55", "UPP2002gl55-000675")/*
																 * @res
																 * "�з�¼�Ѿ�����/����/Эͬ�����ܱ��"
																 */);
		}

	}

	public void reloadVoucherUser(String pk_glorgbook) throws BusinessException {
		VoucherUserDAO voucheruser = new VoucherUserDAO();
		voucheruser.reloadVoucherUser(pk_glorgbook,
				VoucherUserVO.User_Type_Prepare);
		voucheruser.reloadVoucherUser(pk_glorgbook,
				VoucherUserVO.User_Type_Check);
		voucheruser.reloadVoucherUser(pk_glorgbook,
				VoucherUserVO.User_Type_Sign);
		voucheruser.reloadVoucherUser(pk_glorgbook,
				VoucherUserVO.User_Type_Tally);

	}

	/**
	 * ��VO���������ֵ�������ݿ⡣
	 * 
	 * ����ƾ֤�ӱ�������ǲ�һ���ģ������жϸü�¼�������Ļ���ԭ�еĸø��µĻ���ԭ�еĸ�ɾ����̫Ӱ��Ч�ʣ�
	 * ���ԣ���ƾ֤�ӱ�ĸ��²���������ɾ��ԭ�м�¼��Ȼ�������ͬ��¼�ķ�ʽ��
	 * 
	 */
	public VoucherVO update(VoucherVO voucher) throws Exception {
		//
		// copy_deletedVoucher(voucher.getPk_voucher());
		// if (voucher.getNo() == null) {
		// voucher = catVoucherNo(voucher);
		// }
		GLKeyLock lock = null;
		boolean bLockSuccess = false;
		try {
			// nc.bs.pub.lock.LockHome home = (nc.bs.pub.lock.LockHome)
			// getBeanHome(nc.bs.pub.lock.LockHome.class,
			// "nc.bs.pub.lock.LockBO");
			// if (home == null)
			// throw new ClassNotFoundException("nc.bs.pub.lock.LockHome");
			lock = new GLKeyLock();
			for (int i = 0; i < 5; i++) {
				bLockSuccess = lock.lockKey(
						voucher.getPk_voucher() == null ? voucher
								.getPk_accountingbook()
								+ voucher.getPk_vouchertype()
								+ voucher.getYear() + voucher.getPeriod()
								: voucher.getPk_voucher(), voucher
								.getPk_prepared(), "gl_voucher");
				if (bLockSuccess)
					break;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
			if (!bLockSuccess)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000539")/*
													 * @res "�������û��ڲ��������Ժ����ԡ�"
													 */);
			if (voucher.getNo() == null || voucher.getNo().intValue() == 0) {
				// voucher = catVoucherNo(voucher);
				// getVoucherNumber(voucher);
				getVoucherNumberForSave(voucher);
				if (voucher.getPk_voucher() != null)
					updateVoucherNumber(voucher);
			} else {
				try {
					updateVoucherNumber(voucher);
				} catch (VoucherNoDuplicateException e) {
					throw new BusinessException(
							new VoucherCheckMessage()
									.getVoucherMessage(VoucherCheckMessage.ErrMsgNOExist));
				} catch (Exception ex) {
					throw ex;
				}
			}

			// copy_deletedVoucher(voucher.getPk_voucher());
			// copy_deletedVoucherNew(voucher.getPk_voucher());

			if (voucher.getNo() == null || voucher.getNo().intValue() <= 0)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000565")/*
													 * @res "�������Ч��ƾ֤�ţ�"
													 */
						+ voucher.getNo());
			// else
			// checkVoucherNo(voucher);
			// ƾ֤����
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			VoucherVO tmpvoucher = dmo
					.queryByVoucherPk(voucher.getPk_voucher());
			if (tmpvoucher == null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000566")/*
													 * @res
													 * "�����޸ĵ�ƾ֤�ѱ�����ɾ�����޷��������ݡ�"
													 */);
			else if (tmpvoucher.getDiscardflag() != null
					&& tmpvoucher.getDiscardflag() == UFBoolean.TRUE)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000567")/*
													 * @res
													 * "�����޸ĵ�ƾ֤�ѱ��������ϡ��޷��������ݡ�"
													 */);
			else if (tmpvoucher.getPk_casher() != null
					&& voucher.getErrmessage() == null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000570")/*
													 * @res
													 * "�����޸ĵ�ƾ֤�ѱ�����ǩ�֡��޷��������ݡ�"
													 */);
			else if (tmpvoucher.getPk_checked() != null) {
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000568")/*
													 * @res
													 * "�����޸ĵ�ƾ֤�ѱ�������ˡ��޷��������ݡ�"
													 */);
				// 60x
				// if
				// (NCLocator.getInstance().lookup(IGlPara.class).getVoucherFlowCTL(tmpvoucher.getPk_accountingbook()).intValue()
				// == ParaMacro.VOUCHER_FLOWCONTROL_SIGNALCHECK)
				// throw new
				// BusinessException(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005",
				// "UPP20021005-000568")/*
				// * @res
				// * "�����޸ĵ�ƾ֤�ѱ�������ˡ��޷��������ݡ�"
				// */);
			} else if (tmpvoucher.getPk_manager() != null)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000569")/*
													 * @res
													 * "�����޸ĵ�ƾ֤�ѱ����˼��ˡ��޷��������ݡ�"
													 */);
			int iCount = dmo.update(voucher);
			if (iCount == 0)
				throw new BusinessException(nc.bs.ml.NCLangResOnserver
						.getInstance().getStrByID("20021005",
								"UPP20021005-000566")/*
													 * @res
													 * "�����޸ĵ�ƾ֤�ѱ�����ɾ�����޷��������ݡ�"
													 */);
		} finally {
			if (bLockSuccess)
				lock.freeKey(
						voucher.getPk_voucher() == null ? voucher
								.getPk_accountingbook()
								+ voucher.getPk_vouchertype()
								+ voucher.getYear() + voucher.getPeriod()
								: voucher.getPk_voucher(), voucher
								.getPk_prepared(), "gl_voucher");
		}

		// ����ƾ֤����ӱ������
		updateSubDetail(voucher);
		return voucher;
	}

	private void appendDetailHead(VoucherVO voucher) {
		DetailVO[] details = voucher.getDetails();
		for (DetailVO detailVO : details) {
			detailVO.setPrepareddate(voucher.getPrepareddate());
			detailVO.setYear(voucher.getYear());
			detailVO.setPeriod(voucher.getPeriod());
			detailVO.setAdjustperiod(voucher.getM_adjustperiod());
			detailVO.setVoucherkind(voucher.getVoucherkind());
			detailVO.setPk_vouchertype(voucher.getPk_vouchertype());
			detailVO.setPk_manager(voucher.getPk_manager());
			detailVO.setDiscardflag(voucher.getDiscardflag());
			detailVO.setErrmessage2(voucher.getErrmessage());
			detailVO.setNo(voucher.getNo());
			if (StringUtils.isEmpty(detailVO.getPk_system()))
				detailVO.setPk_system(voucher.getPk_system());
			detailVO.setIsdifflag(voucher.getIsdifflag());
			detailVO.setPk_prepared(voucher.getPk_prepared());

			// ����VATĬ��ֵ
			VatDetailVO vatDetailVo = detailVO.getVatdetail();
			if (vatDetailVo != null) {
				if (detailVO.getLocaldebitamount() != null
						&& !UFDouble.ZERO_DBL.equals(detailVO
								.getLocaldebitamount())) {
					// ��������Ϊ�跽
					vatDetailVo.setDirection(DirectionEnum.DEBIT.getEnumValue()
							.getValue());
				} else {
					vatDetailVo.setDirection(DirectionEnum.CREDIT
							.getEnumValue().getValue());
				}

				vatDetailVo.setPk_accountingbook(detailVO
						.getPk_accountingbook());
				vatDetailVo.setPk_accasoa(detailVO.getPk_accasoa());
				vatDetailVo.setDetailindex(detailVO.getDetailindex());
				vatDetailVo.setExplanation(detailVO.getExplanation());
				vatDetailVo.setPk_detail(detailVO.getPk_detail());
				vatDetailVo.setPk_group(detailVO.getPk_group());
				vatDetailVo.setPk_org(detailVO.getPk_org());
				vatDetailVo.setPk_unit(detailVO.getPk_unit());
				vatDetailVo.setPk_voucher(detailVO.getPk_voucher());
				vatDetailVo.setPrepareddate(detailVO.getPrepareddate());
				vatDetailVo.setVoucherno(voucher.getNo());
			}
		}
	}

	public boolean updateVoucherDifflag(String[] pk_vouchers,
			UFBoolean direction) throws BusinessException {
		// TODO Auto-generated method stub
		boolean bflag = true;
		try {
			new VoucherExtendDMO().updateDifflag(pk_vouchers, direction);
		} catch (SystemException e) {
			bflag = false;
			// TODO Auto-generated catch block
			Logger.error(e.getMessage(), e);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			bflag = false;
			Logger.error(e.getMessage(), e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bflag = false;
			throw new BusinessException(e.getMessage());
		}
		return bflag;
	}

	/**
	 * @param voucher
	 * @return
	 * @throws BusinessException
	 */
	public String[] getVoucherNumber(VoucherVO voucher)
			throws BusinessException {

		try {
			GLParameterVO glparam = new GLParameterVO();

			IGlPara dmo = NCLocator.getInstance().lookup(IGlPara.class);
			glparam.Parameter_isvouchernoautofill = dmo
					.isVoucherNOAutoFill(voucher.getPk_accountingbook());
			glparam.Parameter_startvoucherno = dmo.getStartVoucherNO(voucher
					.getPk_accountingbook());
			if (voucher.getVoucherkind() != null
					&& voucher.getVoucherkind().intValue() == 1) {
				return getVoucherNumberNoUpdate(voucher, null);
			} else {
				if (glparam.Parameter_isvouchernoautofill != null
						&& glparam.Parameter_isvouchernoautofill.booleanValue()) {
					voucher.setFree9("automatchVoucherNO");
				} else {
					voucher.setFree9(null);
				}
				return getVoucherNumberNoUpdate(voucher,
						glparam.Parameter_startvoucherno);
			}
		} catch (Exception e) {
			Logger.error(e);
			throw new BusinessException(e);
		} finally {
			if (voucher.getFree9() != null
					&& "automatchVoucherNO".equals(voucher.getFree9())) {
				voucher.setFree9(null);
			}
		}
	}

	/**
	 * @param voucher
	 * @return
	 * @throws BusinessException
	 */
	public String[] getVoucherNumberForSave(VoucherVO voucher)
			throws BusinessException {
		try {
			VoucherNoFetch fetch = new VoucherNoFetch();
			GLParameterVO glparam = new GLParameterVO();

			IGlPara dmo = NCLocator.getInstance().lookup(IGlPara.class);
			glparam.Parameter_isvouchernoautofill = dmo
					.isVoucherNOAutoFill(voucher.getPk_accountingbook());
			glparam.Parameter_startvoucherno = dmo.getStartVoucherNO(voucher
					.getPk_accountingbook());

			if (voucher.getVoucherkind() != null
					&& voucher.getVoucherkind().intValue() == 1) {
				return fetch.getVoucherNumber(voucher, null);
			} else {
				// if (voucher.getFree9() == null)
				// voucher.setFree9("automatchVoucherNO");
				if (glparam.Parameter_isvouchernoautofill != null
						&& glparam.Parameter_isvouchernoautofill.booleanValue()) {
					voucher.setFree9("automatchVoucherNO");
				} else {
					voucher.setFree9(null);
				}
				return fetch.getVoucherNumber(voucher,
						glparam.Parameter_startvoucherno);
			}
		} catch (Exception e) {
			Logger.error(e);
			throw new BusinessException(e);
		} finally {
			if (voucher.getFree9() != null
					&& "automatchVoucherNO".equals(voucher.getFree9())) {
				voucher.setFree9(null);
			}
		}
	}

	/**
	 * @param voucher
	 * @throws Exception
	 */
	private void updateVoucherNumber(VoucherVO voucher)
			throws VoucherNoDuplicateException, Exception {

		if (voucher.getPk_voucher() != null
				&& voucher.getPk_voucher().trim().length() > 0) {
			Object[] tmps = new VoucherDMO().getVoucherTypeByPk(voucher
					.getPk_voucher());
			String vt = (String) tmps[0];
			Integer no = (Integer) tmps[1];
			String pkglorgbook = (String) tmps[2];
			String year = (String) tmps[3];
			String period = (String) tmps[4];

			if (null != vt && vt.compareTo(voucher.getPk_vouchertype()) != 0
					|| !pkglorgbook.equals(voucher.getPk_accountingbook())
					|| !year.equals(voucher.getYear())
					|| !period.equals(voucher.getPeriod())) {
				voucher.setNoFactorChanged(true);
			}
			if (null != vt && vt.compareTo(voucher.getPk_vouchertype()) != 0
					|| no.compareTo(voucher.getNo()) != 0
					|| !pkglorgbook.equals(voucher.getPk_accountingbook())
					|| !year.equals(voucher.getYear())
					|| !period.equals(voucher.getPeriod())) {
				VoucherVO oldVoucher = new VoucherVO();
				oldVoucher.setPk_vouchertype(vt);
				oldVoucher.setNo(no);
				oldVoucher.setPk_accountingbook(pkglorgbook);
				oldVoucher.setYear(year);
				oldVoucher.setPeriod(period);
				voucher.setNoFeature(oldVoucher);
				new VoucherNoFetch().updateVoucherNumber(oldVoucher, voucher);
			}
		} else {
			new VoucherNoFetch().updateVoucherNumber(null, voucher);
		}

	}

	private String[] getVoucherNumberNoUpdate(VoucherVO voucher,
			Integer startNumber) throws BusinessException {
		Integer no = null;
		GLKeyLock lock = null;
		boolean bLockSuccess = false;
		String[] rslt = new String[2];
		String[] strs = null;
		try {
			// lock = new GLKeyLock();
			/*
			 * for (int i = 0; i < 5; i++) { bLockSuccess =
			 * lock.lockKey(voucher.getPk_voucher() == null ? "maxno_" +
			 * voucher.getPk_glorgbook() + voucher.getPk_vouchertype() +
			 * voucher.getYear() + voucher.getPeriod() : "maxno_" +
			 * voucher.getPk_voucher(), voucher.getPk_prepared(), "gl_voucher");
			 * if (bLockSuccess) break; try { Thread.sleep(100); } catch
			 * (InterruptedException e) { } } if (!bLockSuccess) throw new
			 * BusinessException
			 * (nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("20021005",
			 * "UPP20021005-000539") @res "�������û��ڲ��������Ժ����ԡ�" );
			 */
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			strs = dmo.getMaxVOucherNo(voucher, false);
			boolean isautoMatch = voucher.getFree9() != null
					&& "automatchVoucherNO".equals(voucher.getFree9());

			if (strs[0] == null) {
				Integer num = dmo.getMaxVoucherNumFromVouchertable(voucher);
				if (num != null) {
					if (isautoMatch) {
						voucher.setNo(dmo.getCorrectVoucherNoAutoMatch(voucher,
								startNumber));
					} else {
						voucher.setNo(num + 1);
					}
				} else {
					if (isautoMatch) {
						voucher.setNo(startNumber == null ? Integer.valueOf(1)
								: startNumber);
					} else {
						voucher.setNo(1);
					}

				}
			} else {
				if (isautoMatch) {
					no = dmo.getNumberfromSub(strs[1], false);
					if (no != null) {
						voucher.setNo(no);
					} else {
						// strs = dmo.getMaxVOucherNo(voucher, true);
						no = Integer.valueOf(strs[0]);
						voucher.setNo(no + 1);
						no++;
					}
				} else {
					no = Integer.valueOf(strs[0]);
					Integer xno = dmo
							.getVoucherNoFromMaxsub(strs[1], no, false);
					if (xno != null) {
						// no = dmo.getNumberfromSub(strs[1], false);
						no = dmo.getCorrectVoucherNo(voucher);
						if (no != null) {
							voucher.setNo(no);
						} else {
							// strs = dmo.getMaxVOucherNo(voucher, true);
							no = Integer.valueOf(strs[0]);
							voucher.setNo(no + 1);
							no++;
						}
					} else {
						voucher.setNo(no + 1);
						no++;
					}

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			throw new BusinessException(e.getMessage());
		} finally {
			/*
			 * if (bLockSuccess) lock.freeKey(voucher.getPk_voucher() == null ?
			 * "maxno_" + voucher.getPk_glorgbook() +
			 * voucher.getPk_vouchertype() + voucher.getYear() +
			 * voucher.getPeriod() : "maxno_" + voucher.getPk_voucher(),
			 * voucher.getPk_prepared(), "gl_voucher");
			 */
		}

		return new String[] { strs[1], voucher.getNo().toString() };
	}

	/**
	 * ����ӻ��ƽ̨��������ƾ֤
	 * 
	 * @param saveResultVO
	 * @return
	 * @throws BusinessException
	 */
	public FipSaveResultVO saveMDVoucher(FipSaveResultVO saveResultVO)
			throws BusinessException {
		FipSaveResultVO result = new FipSaveResultVO();
		if (null != saveResultVO) {
			try {
				Object vo = saveResultVO.getBillVO();
				VoucherVO voucher = (VoucherVO) assembleVoucher(vo,
						saveResultVO);
				removeAmountZeroDetail(voucher);
				save(voucher, Boolean.TRUE);
				vo = assembleVoucher(voucher, saveResultVO);
				result.setBillVO(vo);
				result.setMessageinfo(processMessage(
						saveResultVO.getMessageinfo(), voucher));
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
				throw new BusinessException(e);
			}
		}
		return result;
	}

	/**
	 * @param voucher
	 */
	private void removeAmountZeroDetail(VoucherVO voucher) {
		Vector<DetailVO> detailList = voucher.getDetail();
		List<DetailVO> subList = new ArrayList<DetailVO>();
		Iterator<DetailVO> it = detailList.iterator();
		while (it.hasNext()) {
			DetailVO vo = it.next();
			if ((vo.getCreditamount() == null || vo.getCreditamount().equals(
					UFDouble.ZERO_DBL))
					&& (vo.getDebitamount() == null || vo.getDebitamount()
							.equals(UFDouble.ZERO_DBL)))
				subList.add(vo);
		}
		detailList.removeAll(subList);
		voucher.setDetail(detailList);
	}

	private FipRelationInfoVO processMessage(FipRelationInfoVO messageinfo,
			VoucherVO voucher) {
		messageinfo.setBusidate(voucher.getPrepareddate());
		messageinfo.setPk_billtype("C0");
		messageinfo.setPk_group(voucher.getPk_group());
		messageinfo.setPk_operator(voucher.getPk_prepared());
		messageinfo.setPk_org(voucher.getPk_accountingbook());
		messageinfo.setPk_system("GL");
		messageinfo.setRelationID(voucher.getPrimaryKey());
		return messageinfo;
	}

	/**
	 * Ԫ����ƾ֤��GLƾ֤�Ļ���ת��
	 * 
	 * @param vo
	 * @param saveResultVO
	 * @return
	 */
	private Object assembleVoucher(Object vo, FipSaveResultVO saveResultVO) {
		if (null != vo && vo instanceof SuperVO) {
			MDVoucher mdVoucher = (MDVoucher) vo;
			VoucherVO vouchervo = fip2gl(mdVoucher);
			FipRelationInfoVO messageVO = saveResultVO.getMessageinfo();
			processVoucher(vouchervo, messageVO);
			return vouchervo;
		} else if (null != vo && vo instanceof VoucherVO) {
			VoucherVO vouchervo = (VoucherVO) vo;
			// Object obj = gl2fip(vouchervo);
			return vouchervo;
		}
		return null;
	}

	/**
	 * �ӹ�ƾ֤
	 * 
	 * @param vouchervo
	 * @param messageVO
	 */
	private void processVoucher(VoucherVO vouchervo, FipRelationInfoVO messageVO) {
		vouchervo.setPk_accountingbook(messageVO.getPk_org());
		vouchervo.setPk_group(messageVO.getPk_group());
		vouchervo
				.setPk_prepared(StringUtils.isEmpty(vouchervo.getPk_prepared()) ? messageVO
						.getPk_operator() : vouchervo.getPk_prepared());
		vouchervo
				.setPrepareddate(null == vouchervo.getPrepareddate() ? messageVO
						.getBusidate() : vouchervo.getPrepareddate());
		vouchervo.setPeriod(vouchervo.getPrepareddate().getStrMonth());
		vouchervo.setYear(String.valueOf(vouchervo.getPrepareddate()
				.getLocalYear()));
		vouchervo
				.setPk_system(StringUtils.isEmpty(messageVO.getPk_system()) ? "GL"
						: messageVO.getPk_system());
		vouchervo.setVoucherkind(vouchervo.getVoucherkind() == null ? 0
				: vouchervo.getVoucherkind());
		vouchervo.setExplanation(StringUtils.isEmpty(vouchervo.getDetail(0)
				.getExplanation()) ? nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
				.getStrByID("voucherprivate_0", "02002005-0065")/*
																 * @res
																 * "��ϵͳ����ƾ֤"
																 */: vouchervo
				.getDetail(0).getExplanation());
		;
	}

	private MDVoucher gl2fip(VoucherVO vouchervo) {
		MDVoucher mdVoucher = new MDVoucher();
		String[] names = vouchervo.getAttributeNames();
		for (String name : names) {
			mdVoucher
					.setAttributeValue(name, vouchervo.getAttributeValue(name));
		}
		mdVoucher.setFipInfo(vouchervo.getFipInfo());
		mdVoucher.setControlFlag(vouchervo.getEditflag());
		return mdVoucher;
	}

	private VoucherVO fip2gl(MDVoucher mdVoucher) {
		// VoucherVO voucherVO = new VoucherVO();
		// String[] names = mdVoucher.getAttributeNames();
		// for (String name : names) {
		// voucherVO.setAttributeValue(name, mdVoucher.getAttributeValue(name));
		// }
		// voucherVO.setFipInfo(mdVoucher.getFipInfo());
		// voucherVO.setControlFlag(mdVoucher.getEditflag());
		// return computeTotal(voucherVO);
		return this.convertWithSetterMethod(mdVoucher);
	}

	private VoucherVO convertWithSetterMethod(MDVoucher mdVoucher) {
		VoucherVO voucherVO = new VoucherVO();
		String[] names = mdVoucher.getAttributeNames();
		voucherVO.setAddclass(mdVoucher.getAddclass());
		voucherVO.setAttachment(mdVoucher.getAttachment());
		voucherVO.setContrastflag(mdVoucher.getContrastflag());
		voucherVO.setDeleteclass(mdVoucher.getDeleteclass());
		voucherVO.setDetailmodflag(mdVoucher.getDetailmodflag());
		voucherVO.setDiscardflag(mdVoucher.getDiscardflag());
		voucherVO.setErrmessage(mdVoucher.getErrmessage());

		voucherVO.setExplanation(mdVoucher.getExplanation());
		voucherVO.setFree1(mdVoucher.getFree1());
		voucherVO.setFree10(mdVoucher.getFree10());

		voucherVO.setFree2(mdVoucher.getFree2());
		voucherVO.setFree3(mdVoucher.getFree3());
		voucherVO.setFree4(mdVoucher.getFree4());
		voucherVO.setFree5(mdVoucher.getFree5());
		voucherVO.setFree6(mdVoucher.getFree6());
		voucherVO.setFree7(mdVoucher.getFree7());
		voucherVO.setFree8(mdVoucher.getFree8());
		voucherVO.setFree9(mdVoucher.getFree9());
		voucherVO.setModifyclass(mdVoucher.getModifyclass());
		voucherVO.setModifyflag(mdVoucher.getModifyflag());
		voucherVO.setNo(mdVoucher.getNum());
		voucherVO.setPeriod(mdVoucher.getPeriod());
		voucherVO.setPk_casher(mdVoucher.getPk_casher());
		voucherVO.setPk_checked(mdVoucher.getPk_checked());
		voucherVO.setPk_manager(mdVoucher.getPk_manager());
		voucherVO.setPk_prepared(mdVoucher.getPk_prepared());
		voucherVO.setPk_setofbook(mdVoucher.getPk_setofbook());
		voucherVO.setPk_system(mdVoucher.getPk_system());
		voucherVO.setPk_vouchertype(mdVoucher.getPk_vouchertype());
		voucherVO.setPrepareddate(mdVoucher.getPrepareddate());
		voucherVO.setSignflag(mdVoucher.getSignflag());
		voucherVO.setTallydate(mdVoucher.getTallydate());
		voucherVO.setTotalcredit(mdVoucher.getTotalcredit());
		voucherVO.setTotaldebit(mdVoucher.getTotaldebit());
		voucherVO.setVoucherkind(mdVoucher.getVoucherkind());
		voucherVO.setYear(mdVoucher.getYear());
		voucherVO.setSigndate(mdVoucher.getSigndate());

		voucherVO.setCheckeddate(mdVoucher.getCheckeddate());
		// voucherVO.setBillmaker(mdVoucher.getp);
		// voucherVO.setApprover(mdVoucher);
		voucherVO.setCreator(mdVoucher.getCreator());
		voucherVO.setTempsaveflag(mdVoucher.getTempsaveflag());
		voucherVO.setAttributeValue("aggdetails", mdVoucher.getAggdetails());
		voucherVO.setFipInfo(mdVoucher.getFipInfo());
		voucherVO.setControlFlag(mdVoucher.getEditflag());
		return computeTotal(voucherVO);
	}

	private VoucherVO computeTotal(VoucherVO voucherVO) {
		UFDouble credit = UFDouble.ZERO_DBL;
		UFDouble debit = UFDouble.ZERO_DBL;
		UFDouble creditgroup = UFDouble.ZERO_DBL;
		UFDouble debitgroup = UFDouble.ZERO_DBL;
		UFDouble creditglobal = UFDouble.ZERO_DBL;
		UFDouble debitglobal = UFDouble.ZERO_DBL;
		DetailVO[] vos = voucherVO.getDetails();
		for (int i = 0; null != vos && i < vos.length; i++) {
			credit = credit
					.add(vos[i].getLocalcreditamount() == null ? UFDouble.ZERO_DBL
							: vos[i].getLocalcreditamount());
			debit = debit
					.add(vos[i].getLocaldebitamount() == null ? UFDouble.ZERO_DBL
							: vos[i].getLocaldebitamount());
			creditgroup = creditgroup
					.add(vos[i].getGroupcreditamount() == null ? UFDouble.ZERO_DBL
							: vos[i].getGroupcreditamount());
			debitgroup = debitgroup
					.add(vos[i].getGroupdebitamount() == null ? UFDouble.ZERO_DBL
							: vos[i].getGroupdebitamount());
			creditglobal = creditglobal
					.add(vos[i].getGlobalcreditamount() == null ? UFDouble.ZERO_DBL
							: vos[i].getGlobalcreditamount());
			debitglobal = debitglobal
					.add(vos[i].getGlobaldebitamount() == null ? UFDouble.ZERO_DBL
							: vos[i].getGlobaldebitamount());
		}
		String groupCurr = CurrencyRateUtilHelper.getInstance()
				.getLocalCurrtypeByOrgID(voucherVO.getPk_group());
		String globalCurr = CurrencyRateUtilHelper.getInstance()
				.getLocalCurrtypeByOrgID(IOrgConst.GLOBEORG);
		String localCurrPK = CurrencyRateUtilHelper.getInstance()
				.getLocalCurrtypeByAccountingbookID(
						voucherVO.getPk_accountingbook());

		int[] digitAndRoundtype = nc.itf.fi.pub.Currency
				.getCurrDigitAndRoundtype(localCurrPK);
		int[] groupDigitAndRoundtype = nc.itf.fi.pub.Currency
				.getCurrDigitAndRoundtype(groupCurr);
		int[] globalDigitAndRoundtype = nc.itf.fi.pub.Currency
				.getCurrDigitAndRoundtype(globalCurr);

		if (null != vos && vos.length > 0) {
			credit = credit.add(UFDouble.ZERO_DBL, digitAndRoundtype[0],
					digitAndRoundtype[1]);
			debit = debit.add(UFDouble.ZERO_DBL, digitAndRoundtype[0],
					digitAndRoundtype[1]);

			creditgroup = creditgroup.add(UFDouble.ZERO_DBL,
					groupDigitAndRoundtype[0], groupDigitAndRoundtype[1]);
			debitgroup = debitgroup.add(UFDouble.ZERO_DBL,
					groupDigitAndRoundtype[0], groupDigitAndRoundtype[1]);

			creditglobal = creditglobal.add(UFDouble.ZERO_DBL,
					globalDigitAndRoundtype[0], globalDigitAndRoundtype[1]);
			debitglobal = debitglobal.add(UFDouble.ZERO_DBL,
					globalDigitAndRoundtype[0], globalDigitAndRoundtype[1]);
		}
		voucherVO.setTotalcredit(credit);
		voucherVO.setTotaldebit(debit);
		voucherVO.setTotalcreditgroup(creditgroup);
		voucherVO.setTotaldebitgroup(debitgroup);
		voucherVO.setTotalcreditglobal(creditglobal);
		voucherVO.setTotaldebitglobal(debitglobal);
		if (voucherVO.getPrepareddate() == null && vos != null) {
			voucherVO.setPrepareddate(vos[0].getPrepareddate());
		}
		return voucherVO;
	}

	/**
	 * ��������ɾ��ʵʱƾ֤
	 * 
	 * @param voucherpk
	 * @return
	 * @throws BusinessException
	 */
	public OperationResultVO deleteRtVoucher(String voucherpk)
			throws BusinessException {
		OperationResultVO vo = new OperationResultVO();
		try {
			RtVoucherDMO dmo = new RtVoucherDMO();
			RtDetailDMO detailDMO = new RtDetailDMO();
			detailDMO.deleteByRtVoucherPK(voucherpk);
			int rows = dmo.deleteByRtVoucherPK(voucherpk);
			vo.m_intSuccess = rows;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return vo;
	}

	/**
	 * ��������ɾ��ʵʱƾ֤
	 * 
	 * @param voucherpks
	 * @return
	 * @throws BusinessException
	 */
	public OperationResultVO deleteRtVouchers(String[] voucherpks)
			throws BusinessException {
		// if(null != voucherpks){
		// for (String vpk : voucherpks) {
		// deleteRtVoucher(vpk);
		// }
		// }
		// return null;

		// hurh ����ɾ��
		try {
			RtVoucherDMO dmo = new RtVoucherDMO();
			RtDetailDMO detailDMO = new RtDetailDMO();
			// ˰����ϸ
			NCLocator.getInstance().lookup(IVatDetailMngService.class)
					.deleteVatDetailByVouchers(voucherpks);
			detailDMO.deleteByRtVoucherPK(voucherpks);
			dmo.deleteByRtVoucherPK(voucherpks);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}

		return null;
	}

	public VoucherVO[] queryRTByPks(String[] strPks) throws BusinessException {
		VoucherVO[] vouchers = null;
		try {
			RtVoucherDMO dmo = new RtVoucherDMO();
			vouchers = dmo.queryByPks(strPks);
			if (vouchers == null)
				return null;
			String[] pks = new String[vouchers.length];
			for (int i = 0; i < vouchers.length; i++) {
				pks[i] = vouchers[i].getPk_voucher();
			}
			catCorpname(vouchers, getCorps());
			catSystemname(vouchers);
			catVouchertypename(vouchers,
					getVouchertypes(vouchers[0].getPk_accountingbook()));
			catVoucherFreeValue(vouchers);
			RtDetailDMO dmo1 = new RtDetailDMO();
			DetailVO[] details = dmo1.queryByPks(strPks);
			if (details == null) {
				return null;
			}
			String[] pk_accsubj = new String[details.length];
			for (int j = 0; j < details.length; j++)
				pk_accsubj[j] = details[j].getPk_accasoa();
			details = catSubjName(details, vouchers[0].getPrepareddate()
					.toStdString());
			details = catCashFlows4RtDetail(details);
			details = catCheckstylename(details);
			details = catVatDetails(details);
			details = catAss(details);
			catDetails(vouchers, details);
			sort(vouchers);
		} catch (Exception e) {
			// log.error(e);
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return vouchers;
	}

	/**
	 * 
	 * ����˵����
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param voucher
	 * @return
	 * @see
	 * @since V6.0
	 * @hurh
	 */
	private HashMap getAccountMap(VoucherVO voucher, HashMap tempaccsubj) {
		if (tempaccsubj == null) {
			tempaccsubj = new HashMap();
		}
		if (voucher.getDetails().length <= 0) {
			return tempaccsubj;
		}
		try {
			String[] pk_accsubj = new String[voucher.getNumDetails()];
			for (int i = 0; i < voucher.getNumDetails(); i++) {
				pk_accsubj[i] = voucher.getDetail(i).getPk_accasoa();
			}
			// AccountVO[] acc = getAccsubj(pk_accsubj,
			// voucher.getPrepareddate().toStdString());
			AccountVO[] acc = AccountUtilGL.queryByPks(pk_accsubj,
					new VoucherCheckBO().getStdDateForInitSave(voucher));

			// hurh ���������� �ҵ���Ŀ�ϣ����治�ٲ�ѯ�����Ч��
			// IAccountAssPubService accountassservice = (IAccountAssPubService)
			// NCLocator.getInstance().lookup(IAccountAssPubService.class.getName());
			// Map<String,List<AccAssVO>> asMap =
			// accountassservice.queryAllByAccPKs(pk_accsubj,voucher.getPrepareddate().toStdString());
			AccountVO voTemp = null;
			if (acc != null) {
				for (int i = 0; i < acc.length; i++) {
					voTemp = acc[i];
					// voTemp.setAccass(asMap.get(voTemp.getPk_accasoa()) ==
					// null ? null :
					// asMap.get(voTemp.getPk_accasoa()).toArray(new
					// AccAssVO[0]));
					tempaccsubj.put(voTemp.getPk_accasoa(), voTemp);
				}
			}
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new GlBusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("20021005", "UPP20021005-000582")/*
																				 * @
																				 * res
																				 * "��Ŀ��Ϣ�д�"
																				 */);
		}

		return tempaccsubj;
	}

	/**
	 * 
	 * �����š�ȫ�ֱ��ҽ�δ����ʱ�����
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param voucher
	 * @return
	 * @see
	 * @since V6.0
	 */
	private VoucherVO dealLocalAmount(VoucherVO voucher) {
		boolean clearGroup = !Currency.isStartGroupCurr(voucher.getPk_group());
		boolean clearGlobal = !Currency.isStartGlobalCurr();
		if (clearGroup) {
			voucher.setTotalcreditgroup(UFDouble.ZERO_DBL);
			voucher.setTotaldebitgroup(UFDouble.ZERO_DBL);
		}
		if (clearGlobal) {
			voucher.setTotaldebitglobal(UFDouble.ZERO_DBL);
			voucher.setTotalcreditglobal(UFDouble.ZERO_DBL);
		}

		DetailVO[] details = voucher.getDetails();
		if (details == null || details.length <= 0) {
			return voucher;
		}
		for (DetailVO detail : details) {
			if (clearGroup) {
				detail.setGroupcreditamount(UFDouble.ZERO_DBL);
				detail.setGroupdebitamount(UFDouble.ZERO_DBL);
			}
			if (clearGlobal) {
				detail.setGlobalcreditamount(UFDouble.ZERO_DBL);
				detail.setGlobaldebitamount(UFDouble.ZERO_DBL);
			}

			if (detail.getGroupdebitamount() == null) {
				detail.setGroupdebitamount(UFDouble.ZERO_DBL);
			}
			if (detail.getGroupcreditamount() == null) {
				detail.setGroupcreditamount(UFDouble.ZERO_DBL);
			}
			if (detail.getGlobaldebitamount() == null) {
				detail.setGlobaldebitamount(UFDouble.ZERO_DBL);
			}
			if (detail.getGlobalcreditamount() == null) {
				detail.setGlobalcreditamount(UFDouble.ZERO_DBL);
			}
		}

		return voucher;
	}

	/**
	 * 
	 * ����˵����
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param voucher
	 * @return
	 * @see
	 * @since V6.0
	 */
	private VoucherVO catDefaultData(VoucherVO voucher, HashMap tempaccsubj) {
		// ������
		if (voucher.getCreator() == null) {
			voucher.setCreator(voucher.getPk_prepared());
		}
		if (voucher.getCreationtime() == null) {
			voucher.setCreationtime(new UFDateTime(new Date(TimeService
					.getInstance().getTime())));
		}
		if (voucher.getPk_voucher() != null) {
			// �޸�ʱ��
			voucher.setModifiedtime(new UFDateTime(new Date(TimeService
					.getInstance().getTime())));
			// �޸���
			voucher.setModifier(InvocationInfoProxy.getInstance().getUserId());
		} else {
			// �޸�ʱ��
			voucher.setModifiedtime(null);
			// �޸���
			voucher.setModifier(null);
		}
		// ����
		if (voucher.getPk_accountingbook() != null
				&& StringUtils.isEmpty(voucher.getPk_group())) {
			AccountingBookVO book = AccountBookUtil
					.getAccountingBookVOByPrimaryKey(voucher
							.getPk_accountingbook());
			if (book != null) {
				voucher.setPk_group(book.getPk_group());
			}
		}

		DetailVO[] vos = voucher.getDetails();
		DetailVO detail = null;
		AccountVO account = null;
		boolean preflag = false;
		if (null != vos) {
			for (int i = 0; i < vos.length; i++) {
				detail = vos[i];
				// ҵ������
				if (StringUtils.isEmpty(detail.getVerifydate())) {
					detail.setVerifydate(detail.getPrepareddate() == null ? null
							: detail.getPrepareddate().toString());
				}

				// �����Ŀ��Ϣ
				account = (AccountVO) tempaccsubj.get(detail.getPk_accasoa());
				if (account != null) {
					detail.setPk_accchart(account.getPk_currentchart());
					detail.setPk_account(account.getPk_account());
					detail.setAccountcode(account.getCode());
					detail.setAccsubjname(GLMultiLangUtil.getMultiName(account)); // ����ҵ����־ʹ��
					if (account.getAllowclose() != null
							&& account.getAllowclose().booleanValue()) {
						preflag = true;
					}
				}

				// ����
				if (StringUtils.isEmpty(detail.getPk_group())
						&& !StringUtils.isEmpty(voucher.getPk_group())) {
					detail.setPk_group(voucher.getPk_group());
				}
			}
		}
		// ��¼�Ƿ������ǰ���˿�Ŀ��־
		voucher.setPreaccountflag(UFBoolean.valueOf(preflag));
		return voucher;
	}

	/**
	 * 
	 * ����˵����
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param voucher
	 * @return
	 * @see
	 * @since V6.0
	 */
	private VoucherVO catDefaultData4SaveError(VoucherVO voucher,
			HashMap tempaccsubj) {
		DetailVO[] vos = voucher.getDetails();
		DetailVO detail = null;
		AccountVO account = null;
		boolean preflag = false;
		if (null != vos) {
			for (int i = 0; i < vos.length; i++) {
				detail = vos[i];
				// �����Ŀ��Ϣ
				account = (AccountVO) tempaccsubj.get(detail.getPk_accasoa());
				if (account != null) {
					detail.setPk_accchart(account.getPk_currentchart());
					detail.setPk_account(account.getPk_account());
					detail.setAccountcode(account.getCode());
					if (account.getAllowclose() != null
							&& account.getAllowclose().booleanValue()) {
						preflag = true;
					}
				}
			}
		}
		// ��¼�Ƿ������ǰ���˿�Ŀ��־
		voucher.setPreaccountflag(UFBoolean.valueOf(preflag));
		return voucher;
	}

	/**
	 * @author zhaozh У��ƾ֤�ſ�Խ�Ƿ����
	 * @param voucher
	 * @return
	 * @throws BusinessException
	 */
	private void checkMaxNum(VoucherVO voucher) throws BusinessException {
		if (voucher.getNo() == null) {
			return;
		}
		int realNum = voucher.getNo();
		Integer maxNum = null;
		int paraMaxnum = SystemParamConfig.getInstance()
				.getMaxAllowVoucherNum();
		try {
			maxNum = GLPubProxy.getIRemoteVoucherNo()
					.getMaxVoucherNumFromVouchertable(voucher);
			maxNum = maxNum == null ? 0 : maxNum;
		} catch (BusinessException e) {
			Logger.error("ȡ���ƾ֤�Ŵ���:" + this.getClass().getName());
			Logger.error(e.getMessage(), e);
		}
		if (realNum > maxNum + paraMaxnum) {
			String err = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"2002gl5702", "UPP2002gl5702-000021")/* @res "�����ƾ֤��" */
					+ realNum
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"2002gl5702", "UPP2002gl5702-000022")/*
																 * @res
																 * "���ڴ�ƾ֤����ڱ�����ڼ������"
																 */
					+ maxNum
					+ " "
					+ SystemParamConfig.getInstance().getMaxAllowVoucherNum()
					+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID(
							"2002gl5702", "UPP2002gl5702-000023")/*
																 * @res
																 * "���ϣ���������"
																 */;
			throw new GlBusinessException(err);
		}
	}

	/**
	 * 
	 * ƾ֤���棬�����κ�У�飬��ҪΪ�ֲ�ʽʹ��
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param vouchers
	 * @throws BusinessException
	 * @see
	 * @since V6.1
	 * @author hurh
	 */
	public String[] simpleSaveVoucher(VoucherVO[] vouchers)
			throws BusinessException {
		String[] pks = null;
		try {
			VoucherDMO voucherdmo = new VoucherDMO();
			pks = voucherdmo.insertArrayWithPks(vouchers);

			DetailDMO detaildmo = new DetailDMO();
			List<DetailVO> detailList = new LinkedList<DetailVO>();
			for (VoucherVO voucher : vouchers) {
				if (voucher.getDetails().length > 0) {
					for (DetailVO detail : voucher.getDetails()) {
						detail.setPk_voucher(voucher.getPk_voucher());
						detailList.add(detail);
					}
				}
			}
			detaildmo.insertArray(detailList.toArray(new DetailVO[0]));
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return pks;
	}

	/**
	 * 
	 * ����������ѯƾ֤
	 * <p>
	 * �޸ļ�¼��
	 * </p>
	 * 
	 * @param condition
	 * @param isIncludeDetails
	 * @return
	 * @throws BusinessException
	 * @see
	 * @since V6.1
	 * @author hurh
	 */
	public VoucherVO[] queryByCondition(String condition,
			boolean isIncludeDetails, String pk_group) throws BusinessException {
		VoucherVO[] vouchers = null;
		try {
			VoucherExtendDMO dmo = new VoucherExtendDMO();
			vouchers = dmo.queryByCondition(condition, pk_group);
			if (vouchers == null || vouchers.length == 0) {
				return null;
			}
			if (isIncludeDetails) {
				String[] vpks = new String[vouchers.length];
				for (int i = 0; null != vouchers && i < vouchers.length; i++) {
					vpks[i] = vouchers[i].getPk_voucher();
				}
				DetailExtendDMO detailExtendDMO = new DetailExtendDMO();
				DetailVO[] details = detailExtendDMO.queryByVoucherPks(vpks);

				String[] voucherPks = new String[vouchers.length];
				for (int i = 0; i < vouchers.length; i++) {
					voucherPks[i] = vouchers[i].getPk_voucher();
				}
				String detailPks[] = new String[details.length];
				for (int i = 0; i < detailPks.length; i++) {
					detailPks[i] = details[i].getPk_detail();
				}
				String[] pk_details = getRecDetailPKsByVoucherPK(voucherPks,
						detailPks);
				details = catDetailMatchingflag(details, pk_details);
				details = catDtlFreevalue(details);
				details = catCashFlows(details);
				details = catCheckstylename(details);
				details = catAss(details);
				// ˰����ϸ
				details = catVatDetails(details);
				catDetails(vouchers, details);
			}
			catDetailMatchingFlagForOffer(vouchers);
			sort(vouchers);
			dealOfferFlag(vouchers);
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
		return vouchers;
	}

	/**
	 * ��ѯָ��ƾ֤��Ŀ��ƾ֤
	 * 
	 * @param voucher
	 * @return
	 * @throws BusinessException
	 */
	public VoucherVO[] getConvertVouchers(VoucherVO voucher)
			throws BusinessException {
		if (voucher == null || voucher.getPk_voucher() == null)
			return null;
		String condition = "pk_srcvoucher = '" + voucher.getPk_voucher()
				+ "' and (ISSUCCESS = 'Y'  or failreason = "
				+ ConvertConsatnt.Reason_Enter_NeedConfirm + ")";
		ConvertlogVO[] logs = new ConvertBO().queryLogByCondition(condition);
		int logsCount = logs == null ? 0 : logs.length;
		if (logsCount > 0) {
			String[] desvouchers = new String[logsCount];
			for (int i = 0; i < logsCount; i++) {
				desvouchers[i] = logs[i].getPk_desvoucher();
			}
			return queryByPks(desvouchers);
		}
		return null;
	}
}
