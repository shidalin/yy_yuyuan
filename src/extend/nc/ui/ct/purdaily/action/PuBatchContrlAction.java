package nc.ui.ct.purdaily.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.itf.ct.purdaily.IPuContrl;
import nc.ui.ct.action.HelpAction;
import nc.ui.ct.purdaily.view.CtBatchCtrlScopeDlg;
import nc.ui.ct.purdaily.view.PurdailyBillForm;
import nc.ui.uif2.UIState;
import nc.vo.ct.purdaily.entity.AggCtPuVO;
import nc.vo.ct.purdaily.entity.CtScopeVo;
import nc.vo.ct.rule.ActionStateRule;
import nc.vo.ct.uitl.ValueUtil;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

/**
 * 
 * 采购合同:管控范围(批量)
 */
public class PuBatchContrlAction extends HelpAction {
	private static final long serialVersionUID = -4550882703989198501L;
	private PurdailyBillForm cardForm = null;
	private CtBatchCtrlScopeDlg m_dlgCtrlScope = null;

	public PuBatchContrlAction() {
		this.setCode("PuBatchContrl");
		this.setBtnName(NCLangRes4VoTransl.getNCLangRes().getStrByID(
				"4020003_0", "04020003-0339"));
	}

	public void doAction(ActionEvent e) throws Exception {
		Object[] objvos = this.getModel().getSelectedOperaDatas();
		ArrayList pk_ct_pus = new ArrayList();
		HashMap puorgmap = new HashMap();
		Object[] arr$ = objvos;
		int len$ = objvos.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Object obj = arr$[i$];
			AggCtPuVO ctpuvo = (AggCtPuVO) obj;
			this.checkVersion(ctpuvo);
			boolean iseditable = this.checkCtCtrlScopeEditable(ctpuvo);
			if (iseditable) {
				pk_ct_pus.add(ctpuvo.getParentVO().getPk_ct_pu());
				puorgmap.put(ctpuvo.getParentVO().getPk_ct_pu(), ctpuvo
						.getParentVO().getPk_org());
			}
		}

		if (pk_ct_pus.size() <= 0) {
			ExceptionUtils.wrappBusinessException(NCLangRes4VoTransl
					.getNCLangRes().getStrByID("4020003_0", "04020003-0053"));
		}

		this.initCtCtrlScope(
				(String[]) pk_ct_pus.toArray(new String[pk_ct_pus.size()]),
				puorgmap);
	}

	public PurdailyBillForm getCardForm() {
		return this.cardForm;
	}

	public CtBatchCtrlScopeDlg getM_dlgCtrlScope() {
		if (this.m_dlgCtrlScope == null) {
			this.m_dlgCtrlScope = new CtBatchCtrlScopeDlg(this.getModel()
					.getContext().getPk_group());
			this.m_dlgCtrlScope.setTitle(NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("4020003_0", "04020003-0339"));
			this.m_dlgCtrlScope.setReset(true);
			this.m_dlgCtrlScope.setResizable(true);
		}

		return this.m_dlgCtrlScope;
	}

	public void setCardForm(PurdailyBillForm cardForm) {
		this.cardForm = cardForm;
	}

	public void setM_dlgCtrlScope(CtBatchCtrlScopeDlg ctrlScope) {
		this.m_dlgCtrlScope = ctrlScope;
	}

	private void batchAddCtsCtrlScope(String[] pk_ct_pus,
			Map<String, List<String>> ctscopemap) {
		ArrayList insertscopevos = new ArrayList();

		try {
			String[] scopeorgs = this.getM_dlgCtrlScope().getM_sCtCtrlScope();
			String[] e = pk_ct_pus;
			int len$ = pk_ct_pus.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String pk_ct_pu = e[i$];
				List ctscopelist = (List) ctscopemap.get(pk_ct_pu);
				String[] arr$ = scopeorgs;
				int len$1 = scopeorgs.length;

				for (int i$1 = 0; i$1 < len$1; ++i$1) {
					String scopeorg = arr$[i$1];
					if (!ctscopelist.contains(scopeorg)) {
						CtScopeVo vo = new CtScopeVo();
						vo.setPk_ct_pu(pk_ct_pu);
						vo.setPk_scopeorg(scopeorg);
						insertscopevos.add(vo);
					}
				}
			}

			if (insertscopevos.size() > 0) {
				IPuContrl arg15 = (IPuContrl) NCLocator.getInstance().lookup(
						IPuContrl.class);
				this.processBatchContrl(insertscopevos);
				arg15.insertCtCtrlScope((CtScopeVo[]) insertscopevos
						.toArray(new CtScopeVo[insertscopevos.size()]));
			}
		} catch (Exception arg14) {
			ExceptionUtils.wrappException(arg14);
		}

	}

	/**
	 * 校验数据-2017/01/14
	 * 
	 * @param insertscopevos
	 * @throws BusinessException
	 */
	private void processBatchContrl(ArrayList insertscopevos)
			throws BusinessException {
		Object[] objvos = this.getModel().getSelectedOperaDatas();
		Map<String, AggCtPuVO> pk2agg = new HashMap<String, AggCtPuVO>();
		Map<String, List<String>> pk2List = new HashMap<String, List<String>>();
		for (Object obj : objvos) {
			AggCtPuVO ctpuvo = (AggCtPuVO) obj;
			// 生效状态
			if (ctpuvo.getParentVO().getFstatusflag() == 1) {
				pk2agg.put(ctpuvo.getParentVO().getPk_ct_pu(), ctpuvo);
			}
		}
		for (Object obj : insertscopevos) {
			CtScopeVo csvo = (CtScopeVo) obj;
			AggCtPuVO ctpuvo = pk2agg.get(csvo.getPk_ct_pu());
			//生效状态
			if (ctpuvo != null && ctpuvo.getParentVO().getFstatusflag() == 1) {
				List<String> value = null;
				if (pk2List.keySet().contains(csvo.getPk_ct_pu())) {
					value = pk2List.get(csvo.getPk_ct_pu());
				} else {
					value = new ArrayList<String>();
				}
				value.add(csvo.getPk_scopeorg());
				pk2List.put(csvo.getPk_ct_pu(), value);
			}
		}
		new nc.ui.ct.purdaily.handler.CTMaterialChecker().processBatchContrl(
				pk2agg, pk2List);
	}

	private void batchDelCtsCtrlScope(String[] pk_ct_pus,
			Map<String, List<String>> ctscopemap, Map<String, String> puorgmap) {
		HashMap delscopemap = new HashMap();
		ArrayList delctpupks = new ArrayList();

		try {
			String[] scopeorgs = this.getM_dlgCtrlScope().getM_sCtCtrlScope();
			String[] e = pk_ct_pus;
			int len$ = pk_ct_pus.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				String pk_ct_pu = e[i$];
				List ctscopelist = (List) ctscopemap.get(pk_ct_pu);
				ArrayList delscopelist = new ArrayList();
				String[] arr$ = scopeorgs;
				int len$1 = scopeorgs.length;

				for (int i$1 = 0; i$1 < len$1; ++i$1) {
					String scopeorg = arr$[i$1];
					if (ctscopelist.contains(scopeorg)
							&& !((String) puorgmap.get(pk_ct_pu)).isEmpty()
							&& !((String) puorgmap.get(pk_ct_pu))
									.equals(scopeorg)) {
						delscopelist.add(scopeorg);
					}
				}

				if (delscopelist.size() > 0) {
					delscopemap.put(pk_ct_pu, delscopelist);
					delctpupks.add(pk_ct_pu);
				}
			}

			if (delscopemap.size() > 0) {
				IPuContrl arg17 = (IPuContrl) NCLocator.getInstance().lookup(
						IPuContrl.class);
				arg17.deleteCtCtrlScopes(delscopemap, (String[]) delctpupks
						.toArray(new String[delctpupks.size()]));
			}
		} catch (Exception arg16) {
			ExceptionUtils.wrappException(arg16);
		}

	}

	private boolean checkCtCtrlScopeEditable(AggCtPuVO aggvo) {
		ActionStateRule rule = new ActionStateRule();
		return !rule.isHaveFree(aggvo) && !rule.isHaveApprove(aggvo)
				&& !rule.isHaveApproving(aggvo) && !rule.isHaveValidate(aggvo) ? false
				: !aggvo.getParentVO().getBbracketOrder().booleanValue();
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

	private void initCtCtrlScope(String[] pk_ct_pus,
			Map<String, String> puorgmap) throws Exception {
		IPuContrl service = (IPuContrl) NCLocator.getInstance().lookup(
				IPuContrl.class);
		Map vosmap = service.queryCtsScope(pk_ct_pus);
		if (vosmap != null && !vosmap.isEmpty()) {
			this.getM_dlgCtrlScope().initCorpList();
			int showmodel = this.getM_dlgCtrlScope().showModal();
			if (showmodel == 3) {
				this.batchAddCtsCtrlScope(pk_ct_pus, vosmap);
			} else if (showmodel == 5) {
				this.batchDelCtsCtrlScope(pk_ct_pus, vosmap, puorgmap);
			}

		} else {
			ExceptionUtils.wrappBusinessException(NCLangRes4VoTransl
					.getNCLangRes().getStrByID("4020003_0", "04020003-0340"));
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