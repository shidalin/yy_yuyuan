package nc.ui.yuyuan.yy_ordertemp.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.ct.action.HelpAction;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.vo.ct.uitl.ValueUtil;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.pf.BillStatusEnum;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.uap.rbac.profile.FunctionPermProfileManager;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

/**
 * 订货模板分配组织按钮 存在一个问题：已经处理了当前登录用户所属权限的采购组织，怎么保证物料的组织也是权限之内？
 * 解决方案（客户讨论之后）：按采购组织过滤表体物料并进行界面提示
 * 
 * @author shidl
 * 
 */
public class OrderTempContrlAction extends HelpAction {
	private ShowUpableBillForm cardForm = null;
	private OrderTempCtrlScopeDlg m_dlgCtrlScope = null;

	public ShowUpableBillForm getCardForm() {
		return cardForm;
	}

	public void setCardForm(ShowUpableBillForm cardForm) {
		this.cardForm = cardForm;
	}

	public OrderTempCtrlScopeDlg getM_dlgCtrlScope() {
		if (this.m_dlgCtrlScope == null) {
			this.m_dlgCtrlScope = new OrderTempCtrlScopeDlg(this.getModel()
					.getContext().getPk_group());
			this.m_dlgCtrlScope.setReset(true);
			this.m_dlgCtrlScope.setResizable(true);
		}

		return this.m_dlgCtrlScope;
	}

	public void setM_dlgCtrlScope(OrderTempCtrlScopeDlg m_dlgCtrlScope) {
		this.m_dlgCtrlScope = m_dlgCtrlScope;
	}

	public OrderTempContrlAction() {
		this.setBtnName("单据复制");
		this.setCode("orderTempContrlAction");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		AggYyOrderTempVO agg = (AggYyOrderTempVO) this.getModel()
				.getSelectedData();
		if (ValueUtil.isEmpty(agg)
				|| ValueUtil.isEmpty(agg.getParentVO().getPk_ordertemp())) {
			ExceptionUtils.wrappBusinessException(NCLangRes4VoTransl
					.getNCLangRes().getStrByID("4020003_0", "04020003-0053"));
		}
		this.initOrderTempCtrlScope(agg);
	}

	/**
	 * 初始化组织分配操作弹框，准备数据
	 * 
	 * @param agg
	 * @throws BusinessException
	 */
	private void initOrderTempCtrlScope(AggYyOrderTempVO agg)
			throws BusinessException {
		// 1.查询登录人关联的组织
		String[] pk_orgs = FunctionPermProfileManager
				.getInstance()
				.getProfile(
						WorkbenchEnvironment.getInstance().getLoginUser()
								.getUser_code()).getPermPkorgs();
		// 2.过滤采购组织数据
		List<String> purchaseList = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_ordertempMaintain.class)
				.queryPurchaseOrgByOrg(pk_orgs);
		// 3.去除当前单据采购组织
		if (purchaseList != null
				&& purchaseList.contains(agg.getParentVO().getPk_org())) {
			purchaseList.remove(agg.getParentVO().getPk_org());
			// 增加逻辑处理-过滤下游已分配的组织
			List<String> repeatOrgList = NCLocator
					.getInstance()
					.lookup(nc.itf.yuyuan.IYy_ordertempMaintain.class)
					.queryRepeatPurchaseOrg(agg.getParentVO().getPk_ordertemp());
			if (repeatOrgList.size() > 0) {
				purchaseList.removeAll(repeatOrgList);
			}
			if (purchaseList.size() > 0) {
				String[] purchaseOrgs = purchaseList
						.toArray(new String[purchaseList.size()]);
				this.getM_dlgCtrlScope().initCorpList(purchaseOrgs, true);
				if (this.getM_dlgCtrlScope().showModal() == 1) {
					this.updateOrderTempCtrlScope(agg);
				}
			} else {
				ShowStatusBarMsgUtil.showStatusBarMsg("当前用户无其他可分配采购组织", this
						.getModel().getContext());
			}
		} else {
			ShowStatusBarMsgUtil.showStatusBarMsg("当前用户无其他可分配采购组织", this
					.getModel().getContext());
		}
	}

	/**
	 * 根据分配的采购组织，构造新的模板数据
	 * 
	 * @param agg
	 * @throws BusinessException
	 */
	private void updateOrderTempCtrlScope(AggYyOrderTempVO agg)
			throws BusinessException {
		// 新分配的采购组织
		String[] sCtrlScopeNew = this.getM_dlgCtrlScope().getM_sCtCtrlScope();
		if (sCtrlScopeNew != null && sCtrlScopeNew.length > 0) {
			List<AggYyOrderTempVO> newaggs = new ArrayList<AggYyOrderTempVO>();
			StringBuffer message = new StringBuffer();
			for (String newpkorg : sCtrlScopeNew) {
				AggYyOrderTempVO newagg = (AggYyOrderTempVO) agg.clone();
				newagg.getParentVO().setPk_org(newpkorg);
				String pk_org_v = nc.vo.am.common.util.OrgUtils
						.getCurVidByPkOrg(newpkorg);
				newagg.getParentVO().setAttributeValue("vbillcode", null);
				newagg.getParentVO().setAttributeValue("pk_org_v", pk_org_v);
				newagg.getParentVO().setAttributeValue("vbillstatus",
						BillStatusEnum.FREE.value());
				newagg.getParentVO().setAttributeValue("dmakedate",
						AppContext.getInstance().getBusiDate());
				newagg.getParentVO().setAttributeValue("approver", null);
				newagg.getParentVO().setAttributeValue("dapprovetime", null);
				newagg.getParentVO().setAttributeValue("creator", null);
				newagg.getParentVO().setAttributeValue("creationtime", null);
				newagg.getParentVO().setAttributeValue("modifier", null);
				newagg.getParentVO().setAttributeValue("modifiedtime", null);
				newagg.getParentVO().setAttributeValue("pk_ordertemp", null);
				// 设置上游单据主键
				newagg.getParentVO().setAttributeValue("vdef1",
						agg.getParentVO().getPk_ordertemp());
				CircularlyAccessibleValueObject[] childrenVOs = newagg
						.getChildrenVO();
				for (CircularlyAccessibleValueObject bvo : childrenVOs) {
					bvo.setAttributeValue("pk_org", newpkorg);
					bvo.setAttributeValue("pk_ordertemp", null);
					bvo.setAttributeValue("pk_ordertemp_b", null);
					bvo.setAttributeValue("pk_org_v", pk_org_v);
				}
				Map<AggYyOrderTempVO, List<CircularlyAccessibleValueObject>> checkData = checkData(newagg);
				if (checkData.entrySet().iterator().next().getKey()
						.getChildrenVO() != null) {
					newaggs.add(checkData.entrySet().iterator().next().getKey());
				} else {
					// message.append("分配采购组织【"
					// + this.getM_dlgCtrlScope()
					// .getM_mapAssCorpsKeyPk()
					// .get(checkData.entrySet().iterator().next()
					// .getKey().getParentVO().getPk_org())
					// + "】失败,表体物料没有【"
					// + this.getM_dlgCtrlScope()
					// .getM_mapAssCorpsKeyPk()
					// .get(checkData.entrySet().iterator().next()
					// .getKey().getParentVO().getPk_org())
					// + "】采购组织权限\n");
					for (CircularlyAccessibleValueObject bvo : newagg
							.getChildrenVO()) {
						message.append("行号【"
								+ bvo.getAttributeValue("crowno")
								+ "】物料编码【"
								+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
										.getAttributeValue("cmaterialvid")
										.toString())
								+ "】没有【"
								+ this.getM_dlgCtrlScope()
										.getM_mapAssCorpsKeyPk()
										.get(checkData.entrySet().iterator()
												.next().getKey().getParentVO()
												.getPk_org()) + "】采购组织权限\n");
					}
				}
				if (checkData.entrySet().iterator().next().getValue() != null) {
					for (CircularlyAccessibleValueObject bvo : checkData
							.entrySet().iterator().next().getValue()) {
						message.append("行号【"
								+ bvo.getAttributeValue("crowno")
								+ "】物料编码【"
								+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
										.getAttributeValue("cmaterialvid")
										.toString())
								+ "】没有【"
								+ this.getM_dlgCtrlScope()
										.getM_mapAssCorpsKeyPk()
										.get(checkData.entrySet().iterator()
												.next().getKey().getParentVO()
												.getPk_org()) + "】采购组织权限\n");
					}
				}
			}
			if (message.length() > 0) {
				ExceptionUtils.wrappBusinessException(message.toString());
			}
			AggYyOrderTempVO[] results = NCLocator.getInstance()
					.lookup(nc.itf.yuyuan.IYy_ordertempMaintain.class)
					.insert(newaggs.toArray(new AggYyOrderTempVO[0]), null);
			message.append("成功分配模板数据条数为:" + results.length + "\n");
			ShowStatusBarMsgUtil.showStatusBarMsg(message.toString(), this
					.getModel().getContext());
		}

	}

	/**
	 * 对表体物料进行数据校验：按采购组织权限进行数据处理
	 * 
	 * @param agg
	 * @return
	 * @throws BusinessException
	 */
	private Map<AggYyOrderTempVO, List<CircularlyAccessibleValueObject>> checkData(
			AggYyOrderTempVO agg) throws BusinessException {
		return NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_ordertempMaintain.class)
				.checkMaterialPUOrg(agg);
	}

	protected boolean isActionEnable() {
		AggYyOrderTempVO aggvo = (AggYyOrderTempVO) this.getModel()
				.getSelectedData();
		return !ValueUtil.isEmpty(aggvo)
				&& !ValueUtil.isEmpty(this.getModel().getSelectedOperaDatas()) ? this
				.getModel().getUiState() != UIState.ADD
				&& this.getModel().getUiState() != UIState.EDIT : false;
	}
}
