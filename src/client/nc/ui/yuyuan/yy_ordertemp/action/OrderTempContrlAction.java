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
 * ����ģ�������֯��ť ����һ�����⣺�Ѿ������˵�ǰ��¼�û�����Ȩ�޵Ĳɹ���֯����ô��֤���ϵ���֯Ҳ��Ȩ��֮�ڣ�
 * ����������ͻ�����֮�󣩣����ɹ���֯���˱������ϲ����н�����ʾ
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
		this.setBtnName("���ݸ���");
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
	 * ��ʼ����֯�����������׼������
	 * 
	 * @param agg
	 * @throws BusinessException
	 */
	private void initOrderTempCtrlScope(AggYyOrderTempVO agg)
			throws BusinessException {
		// 1.��ѯ��¼�˹�������֯
		String[] pk_orgs = FunctionPermProfileManager
				.getInstance()
				.getProfile(
						WorkbenchEnvironment.getInstance().getLoginUser()
								.getUser_code()).getPermPkorgs();
		// 2.���˲ɹ���֯����
		List<String> purchaseList = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_ordertempMaintain.class)
				.queryPurchaseOrgByOrg(pk_orgs);
		// 3.ȥ����ǰ���ݲɹ���֯
		if (purchaseList != null
				&& purchaseList.contains(agg.getParentVO().getPk_org())) {
			purchaseList.remove(agg.getParentVO().getPk_org());
			// �����߼�����-���������ѷ������֯
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
				ShowStatusBarMsgUtil.showStatusBarMsg("��ǰ�û��������ɷ���ɹ���֯", this
						.getModel().getContext());
			}
		} else {
			ShowStatusBarMsgUtil.showStatusBarMsg("��ǰ�û��������ɷ���ɹ���֯", this
					.getModel().getContext());
		}
	}

	/**
	 * ���ݷ���Ĳɹ���֯�������µ�ģ������
	 * 
	 * @param agg
	 * @throws BusinessException
	 */
	private void updateOrderTempCtrlScope(AggYyOrderTempVO agg)
			throws BusinessException {
		// �·���Ĳɹ���֯
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
				// �������ε�������
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
					// message.append("����ɹ���֯��"
					// + this.getM_dlgCtrlScope()
					// .getM_mapAssCorpsKeyPk()
					// .get(checkData.entrySet().iterator().next()
					// .getKey().getParentVO().getPk_org())
					// + "��ʧ��,��������û�С�"
					// + this.getM_dlgCtrlScope()
					// .getM_mapAssCorpsKeyPk()
					// .get(checkData.entrySet().iterator().next()
					// .getKey().getParentVO().getPk_org())
					// + "���ɹ���֯Ȩ��\n");
					for (CircularlyAccessibleValueObject bvo : newagg
							.getChildrenVO()) {
						message.append("�кš�"
								+ bvo.getAttributeValue("crowno")
								+ "�����ϱ��롾"
								+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
										.getAttributeValue("cmaterialvid")
										.toString())
								+ "��û�С�"
								+ this.getM_dlgCtrlScope()
										.getM_mapAssCorpsKeyPk()
										.get(checkData.entrySet().iterator()
												.next().getKey().getParentVO()
												.getPk_org()) + "���ɹ���֯Ȩ��\n");
					}
				}
				if (checkData.entrySet().iterator().next().getValue() != null) {
					for (CircularlyAccessibleValueObject bvo : checkData
							.entrySet().iterator().next().getValue()) {
						message.append("�кš�"
								+ bvo.getAttributeValue("crowno")
								+ "�����ϱ��롾"
								+ nc.pub.yuyuan.utils.MaterialUtil.getCode(bvo
										.getAttributeValue("cmaterialvid")
										.toString())
								+ "��û�С�"
								+ this.getM_dlgCtrlScope()
										.getM_mapAssCorpsKeyPk()
										.get(checkData.entrySet().iterator()
												.next().getKey().getParentVO()
												.getPk_org()) + "���ɹ���֯Ȩ��\n");
					}
				}
			}
			if (message.length() > 0) {
				ExceptionUtils.wrappBusinessException(message.toString());
			}
			AggYyOrderTempVO[] results = NCLocator.getInstance()
					.lookup(nc.itf.yuyuan.IYy_ordertempMaintain.class)
					.insert(newaggs.toArray(new AggYyOrderTempVO[0]), null);
			message.append("�ɹ�����ģ����������Ϊ:" + results.length + "\n");
			ShowStatusBarMsgUtil.showStatusBarMsg(message.toString(), this
					.getModel().getContext());
		}

	}

	/**
	 * �Ա������Ͻ�������У�飺���ɹ���֯Ȩ�޽������ݴ���
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
