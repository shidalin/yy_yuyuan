package nc.ui.ct.purdaily.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import nc.bs.framework.common.NCLocator;
import nc.itf.ct.purdaily.IPuContrl;
import nc.ui.ct.action.HelpAction;
import nc.ui.ct.purdaily.view.CtCtrlScopeDlg;
import nc.ui.ct.purdaily.view.PurdailyBillForm;
import nc.ui.scmpub.action.SCMActionInitializer;
import nc.ui.uif2.UIState;
import nc.vo.ct.purdaily.entity.AggCtPuVO;
import nc.vo.ct.purdaily.entity.CtScopeVo;
import nc.vo.ct.rule.ActionStateRule;
import nc.vo.ct.uitl.ArrayUtil;
import nc.vo.ct.uitl.ValueUtil;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

/**
 * 
 * 采购合同：管控范围按钮
 * 
 */
public class PuContrlAction extends HelpAction {
	private static final long serialVersionUID = 7263688937825522309L;
	private PurdailyBillForm cardForm = null;
	private CtCtrlScopeDlg m_dlgCtrlScope = null;

	public PuContrlAction() {
		SCMActionInitializer.initializeAction(this, "CTControlScope");
	}

	public void doAction(ActionEvent e) throws Exception {
		AggCtPuVO aggvo = (AggCtPuVO) this.getModel().getSelectedData();
		if (ValueUtil.isEmpty(aggvo)
				|| ValueUtil.isEmpty(aggvo.getParentVO().getPk_ct_pu())) {
			ExceptionUtils.wrappBusinessException(NCLangRes4VoTransl
					.getNCLangRes().getStrByID("4020003_0", "04020003-0053"));
		}

		this.checkVersion(aggvo);
		this.checkBracketOrder(aggvo);
		this.initCtCtrlScope(aggvo.getParentVO().getPk_ct_pu());
	}

	public PurdailyBillForm getCardForm() {
		return this.cardForm;
	}

	public CtCtrlScopeDlg getM_dlgCtrlScope() {
		if (this.m_dlgCtrlScope == null) {
			this.m_dlgCtrlScope = new CtCtrlScopeDlg(this.getModel()
					.getContext().getPk_group());
			this.m_dlgCtrlScope.setReset(true);
			this.m_dlgCtrlScope.setResizable(true);
		}

		return this.m_dlgCtrlScope;
	}

	public void setCardForm(PurdailyBillForm cardForm) {
		this.cardForm = cardForm;
	}

	public void setM_dlgCtrlScope(CtCtrlScopeDlg ctrlScope) {
		this.m_dlgCtrlScope = ctrlScope;
	}

	private void checkBracketOrder(AggCtPuVO aggvo) {
		UFBoolean bBracketOrder = aggvo.getParentVO().getBbracketOrder();
		if (bBracketOrder.booleanValue()) {
			ExceptionUtils.wrappBusinessException(NCLangRes4VoTransl
					.getNCLangRes().getStrByID("4020003_0", "04020003-0302"));
		}

	}

	private void checkVersion(AggCtPuVO aggvo) {
		UFBoolean bshowlatest = aggvo.getParentVO().getBshowLatest();
		if (UFBoolean.FALSE.equals(bshowlatest)) {
			ExceptionUtils
					.wrappBusinessException(NCLangRes4VoTransl.getNCLangRes()
							.getStrByID(
									"4020003_0",
									"04020003-0341",
									(String) null,
									new String[] { aggvo.getParentVO()
											.getVbillcode() }));
		}

	}

	private void initCtCtrlScope(String pk_ct_pu) throws Exception {
		ArrayList sCtrlScope = new ArrayList();
		IPuContrl service = (IPuContrl) NCLocator.getInstance().lookup(
				IPuContrl.class);
		CtScopeVo[] vos = service.queryCtScope(pk_ct_pu);
		if (ArrayUtil.isEmpty(vos)) {
			ExceptionUtils.wrappBusinessException(NCLangRes4VoTransl
					.getNCLangRes().getStrByID("4020003_0", "04020003-0400"));
		} else {
			for (int isCtrlScopeEditable = 0; isCtrlScopeEditable < vos.length; ++isCtrlScopeEditable) {
				sCtrlScope.add(vos[isCtrlScopeEditable].getPk_scopeorg());
			}

			boolean arg6 = false;
			ActionStateRule rule = new ActionStateRule();
			if (!rule.isHaveFree(this.getModel().getSelectedData())
					&& !rule.isHaveValidate(this.getModel().getSelectedData())
					&& !rule.isHaveApprove(this.getModel().getSelectedData())
					&& !rule.isHaveApproving(this.getModel().getSelectedData())) {
				arg6 = false;
			} else {
				arg6 = true;
			}

			this.getM_dlgCtrlScope()
					.initCorpList(
							(String[]) sCtrlScope.toArray(new String[sCtrlScope
									.size()]), arg6);
			if (this.getM_dlgCtrlScope().showModal() == 1) {
				this.updateCtCtrlScope(pk_ct_pu, vos, (String[]) sCtrlScope
						.toArray(new String[sCtrlScope.size()]));
			}

		}
	}

	private void updateCtCtrlScope(String pk_ct_pu, CtScopeVo[] vos,
			String[] sCtrlScopeOld) {
		try {
			String[] sCtrlScopeNew = this.getM_dlgCtrlScope()
					.getM_sCtCtrlScope();
			//校验数据-2017/01/14
			AggCtPuVO agg = (AggCtPuVO) this.getModel().getSelectedData();
			new nc.ui.ct.purdaily.handler.CTMaterialChecker().processContrl(
					agg, sCtrlScopeNew, sCtrlScopeOld);
			if (sCtrlScopeNew != null && sCtrlScopeNew.length > 0) {
				IPuContrl e = (IPuContrl) NCLocator.getInstance().lookup(
						IPuContrl.class);
				e.updateCtCtrlScope(pk_ct_pu, sCtrlScopeNew, sCtrlScopeOld, vos);
			}
		} catch (BusinessException arg5) {
			ExceptionUtils.wrappException(arg5);
		}

	}

	protected boolean isActionEnable() {
		AggCtPuVO aggvo = (AggCtPuVO) this.getModel().getSelectedData();
		return !ValueUtil.isEmpty(aggvo)
				&& !ValueUtil.isEmpty(this.getModel().getSelectedOperaDatas()) ? this
				.getModel().getUiState() != UIState.ADD
				&& this.getModel().getUiState() != UIState.EDIT : false;
	}
}