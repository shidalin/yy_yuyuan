package nc.ui.yuyuan.order.ace.handler;

import nc.ui.ct.util.ScaleUtil;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.OrgChangedEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.pubapp.uif2app.view.util.BillPanelUtils;
import nc.vo.uif2.LoginContext;

/**
 * <b> 组织切换事件 </b>
 * 
 * @author author
 * @version tempProject version
 */
public class AceOrgChangeHandler implements IAppEventHandler<OrgChangedEvent> {

	private BillForm billForm;

	@Override
	public void handleAppEvent(OrgChangedEvent e) {
		if (this.billForm.isEditable()) {
			// 在编辑状态下，主组织切换时，清空界面数据，自动表体增行，并设置行号
			this.billForm.addNew();
		}
		LoginContext context = this.billForm.getModel().getContext();
		// 进行参照过滤
		BillPanelUtils.setOrgForAllRef(this.billForm.getBillCardPanel(),
				context);
		// 卡片界面精度处理
		try {
			ScaleUtil scaleUtil = new ScaleUtil("pk_order_b");
			scaleUtil.orgChgScale((ShowUpableBillForm)getBillForm());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1= new Exception("订货单精度处理:表体标准精度字段缺少，不影响使用");
			e1.printStackTrace();
		}
	}

	public BillForm getBillForm() {
		return billForm;
	}

	public void setBillForm(BillForm billForm) {
		this.billForm = billForm;
	}

}
