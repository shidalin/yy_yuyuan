package nc.ui.yuyuan.yy_ordertemp.action;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JPanel;

import nc.desktop.ui.WorkbenchEnvironment;
import nc.itf.scmpub.reference.uap.org.PurchaseOrgForScopeService;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIListToList;
import nc.ui.pub.beans.UIPanel;
import nc.vo.ct.uitl.ValueUtil;
import nc.vo.ml.MultiLangContext;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.org.PurchaseOrgVO;

/**
 * 订货模板组织分配编辑框
 * 
 * @author shidl
 * 
 */
public class OrderTempCtrlScopeDlg extends UIDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private UIButton m_btnCancel = null;
	private UIButton m_btnOK = null;
	private JPanel m_dialogContentPane = null;
	private UIListToList m_ltlCorp2Corp = null;
	private HashMap<String, String> m_mapAssCorpsKeyName = null;
	private HashMap<String, String> m_mapAssCorpsKeyPk = null;
	private UIPanel m_panelSouth = null;
	private String[] m_sCtCtrlScope = null;
	private String m_sLogingroup = null;

	public OrderTempCtrlScopeDlg(String pk_group) {
		super(WorkbenchEnvironment.getInstance().getWorkbench());
		this.m_sLogingroup = pk_group;
		this.initCtrlScopeDlg();
		this.initAssociatedOrg(this.m_sLogingroup);
	}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.getBtnCancel()) {
			this.closeCancel();
		}

		if (e.getSource() == this.getBtnOK()) {
			Object[] oSelectedCorps = this.getLtlCorp2Corp().getRightData();
			if (oSelectedCorps != null && oSelectedCorps.length > 0) {
				this.m_sCtCtrlScope = new String[oSelectedCorps.length];

				for (int i = 0; i < oSelectedCorps.length; ++i) {
					this.m_sCtCtrlScope[i] = (String) this.m_mapAssCorpsKeyName
							.get(oSelectedCorps[i]);
				}
			}

			this.closeOK();
		}

	}

	public String[] getM_sCtCtrlScope() {
		return this.m_sCtCtrlScope;
	}

	
	public HashMap<String, String> getM_mapAssCorpsKeyPk() {
		return m_mapAssCorpsKeyPk;
	}


	public void initCorpList(String[] strCorpsInCtrlScope, boolean isEditable) {
		String[] aryCorpsCodeNameLeft = new String[strCorpsInCtrlScope.length];
		Iterator keyValuePairs1 = this.m_mapAssCorpsKeyPk.entrySet().iterator();
		Entry entry = null;

		int sCorpsCodeNameLeft;
		for (sCorpsCodeNameLeft = 0; sCorpsCodeNameLeft < strCorpsInCtrlScope.length; ++sCorpsCodeNameLeft) {
			aryCorpsCodeNameLeft[sCorpsCodeNameLeft] = (String) this.m_mapAssCorpsKeyPk
					.get(strCorpsInCtrlScope[sCorpsCodeNameLeft]);
		}

		this.getLtlCorp2Corp().setRightData(null);
		this.getLtlCorp2Corp().setLeftData(aryCorpsCodeNameLeft);
		this.getLtlCorp2Corp().setEnabled(isEditable);
	}

	/*
	 * public void initCorpList(String[] strCorpsInCtrlScope, boolean
	 * isEditable) { String[] sCorpsCodeNameRight = new
	 * String[strCorpsInCtrlScope.length]; ArrayList aryCorpsCodeNameLeft = new
	 * ArrayList(); Iterator keyValuePairs1 =
	 * this.m_mapAssCorpsKeyPk.entrySet().iterator(); Entry entry = null;
	 * 
	 * int sCorpsCodeNameLeft; for (sCorpsCodeNameLeft = 0; sCorpsCodeNameLeft <
	 * this.m_mapAssCorpsKeyPk .size(); ++sCorpsCodeNameLeft) { entry = (Entry)
	 * keyValuePairs1.next(); aryCorpsCodeNameLeft.add((String)
	 * entry.getValue()); }
	 * 
	 * for (sCorpsCodeNameLeft = 0; sCorpsCodeNameLeft <
	 * strCorpsInCtrlScope.length; ++sCorpsCodeNameLeft) {
	 * sCorpsCodeNameRight[sCorpsCodeNameLeft] = (String)
	 * this.m_mapAssCorpsKeyPk .get(strCorpsInCtrlScope[sCorpsCodeNameLeft]);
	 * aryCorpsCodeNameLeft .remove(sCorpsCodeNameRight[sCorpsCodeNameLeft]); }
	 * 
	 * String[] arg7 = null; if (aryCorpsCodeNameLeft.size() != 0) { arg7 = new
	 * String[aryCorpsCodeNameLeft.size()]; aryCorpsCodeNameLeft.toArray(arg7);
	 * }
	 * 
	 * this.getLtlCorp2Corp().setRightData(sCorpsCodeNameRight);
	 * this.getLtlCorp2Corp().setLeftData(arg7);
	 * this.getLtlCorp2Corp().setEnabled(isEditable); }
	 */
	public void setM_sCtCtrlScope(String[] ctCtrlScope) {
		this.m_sCtCtrlScope = ctCtrlScope;
	}

	private UIButton getBtnCancel() {
		if (this.m_btnCancel == null) {
			this.m_btnCancel = new UIButton();
			this.m_btnCancel.setName("BtnCancel");
			this.m_btnCancel.setPreferredSize(new Dimension(33, 22));
			this.m_btnCancel.setText(NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("common", "UC001-0000008"));
			this.m_btnCancel.setActionCommand("BtnCancel");
			this.m_btnCancel.addActionListener(this);
		}

		return this.m_btnCancel;
	}

	private UIButton getBtnOK() {
		if (this.m_btnOK == null) {
			this.m_btnOK = new UIButton();
			this.m_btnOK.setName("BtnOK");
			this.m_btnOK.setPreferredSize(new Dimension(33, 22));
			this.m_btnOK.setText(NCLangRes4VoTransl.getNCLangRes().getStrByID(
					"common", "UC001-0000044"));
			this.m_btnOK.setActionCommand("m_btnOK");
			this.m_btnOK.addActionListener(this);
		}

		return this.m_btnOK;
	}

	private UIListToList getLtlCorp2Corp() {
		if (this.m_ltlCorp2Corp == null) {
			this.m_ltlCorp2Corp = new UIListToList();
			this.m_ltlCorp2Corp.setName("LtlCorp2Corp");
			this.m_ltlCorp2Corp.setDisplayTitle(true);
			this.m_ltlCorp2Corp.setLeftText(NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("4020003_0", "04020003-0072"));
			this.m_ltlCorp2Corp.setRightText("目的分配组织");
		}

		return this.m_ltlCorp2Corp;
	}

	private String getNameX() {
		Integer icul = MultiLangContext.getInstance().getCurrentLangSeq();
		String nameX = "name";
		if (icul.intValue() > 1) {
			nameX = nameX + icul;
		}

		return nameX;
	}

	private Component getPnlSouth() {
		if (this.m_panelSouth == null) {
			this.m_panelSouth = new UIPanel();
			this.m_panelSouth.setName("PnlSouth");
			this.m_panelSouth.add(this.getBtnOK(), this.getBtnOK().getName());
			this.m_panelSouth.add(this.getBtnCancel(), this.getBtnCancel()
					.getName());
		}

		return this.m_panelSouth;
	}

	private Container getUIDialogContentPane() {
		if (this.m_dialogContentPane == null) {
			this.m_dialogContentPane = new JPanel();
			this.m_dialogContentPane.setName("UIDialogContentPane");
			this.m_dialogContentPane.setLayout(new BorderLayout());
			this.m_dialogContentPane.add(this.getPnlSouth(), "South");
			this.m_dialogContentPane.add(this.getLtlCorp2Corp(), "Center");
		}

		return this.m_dialogContentPane;
	}

	private void initAssociatedOrg(String sLogingroup) {
		PurchaseOrgVO[] vos = null;
		vos = PurchaseOrgForScopeService
				.queryAllPurchaseOrgVOSByGroup(sLogingroup);
		if (vos != null) {
			String nameField = this.getNameX();
			this.m_mapAssCorpsKeyPk = new HashMap();
			this.m_mapAssCorpsKeyName = new HashMap();

			for (int i = 0; i < vos.length; ++i) {
				Object nameValue = vos[i].getAttributeValue(nameField);
				if (ValueUtil.isEmpty(nameValue)) {
					nameValue = vos[i].getAttributeValue("name");
				}

				if (null == nameValue) {
					nameValue = "";
				}

				String strAssCorpsName = vos[i].getCode() + " " + nameValue;
				this.m_mapAssCorpsKeyPk.put(vos[i].getPk_purchaseorg(),
						strAssCorpsName);
				this.m_mapAssCorpsKeyName.put(strAssCorpsName,
						vos[i].getPk_purchaseorg());
			}
		}

	}

	private void initCtrlScopeDlg() {
		this.setName("CtrlScopeDialog");
		this.setDefaultCloseOperation(2);
		this.setTitle("订货模板单据复制");
		this.setBounds(37, 563, 600, 400);
		this.setResizable(false);
		this.setContentPane(this.getUIDialogContentPane());
	}
}
