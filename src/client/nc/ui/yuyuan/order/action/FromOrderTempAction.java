package nc.ui.yuyuan.order.action;

import java.awt.event.ActionEvent;

import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pubapp.uif2app.actions.AbstractReferenceAction;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.ui.pubapp.uif2app.model.BillManageModel;

/**
 * 订货单-参照订货模板按钮
 * 
 * @author shidl
 * 
 */
public class FromOrderTempAction extends AbstractReferenceAction {
	private static final long serialVersionUID = 1L;

	private BillForm editor;

	private BillManageModel model;

	@Override
	public void doAction(ActionEvent e) throws Exception {
		PfUtilClient.childButtonClicked(getSourceBillType(), getModel()
				.getContext().getPk_group(), getModel().getContext()
				.getPk_loginUser(), "YY02", getModel().getContext()
				.getEntranceUI(), null, null);
		if (PfUtilClient.isCloseOK()) {
			AggYyOrderVO[] vos = (AggYyOrderVO[]) PfUtilClient.getRetVos();
			this.getTransferViewProcessor().processBillTransfer(vos);
		}

	}

	public BillForm getEditor() {
		return this.editor;
	}

	public BillManageModel getModel() {
		return this.model;
	}

	public void setEditor(BillForm editor) {
		this.editor = editor;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
		model.addAppEventListener(this);
	}

	@Override
	protected boolean isActionEnable() {
		return this.model.getUiState() == UIState.NOT_EDIT;
	}

	public FromOrderTempAction() {
		this.setBtnName("参照订货模板");
		this.setCode("fromOrderTempAction");
	}

}
