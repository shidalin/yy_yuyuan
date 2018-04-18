package nc.ui.yuyuan.order.ace.handler;

import nc.ui.ct.util.ScaleUtil;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.OrgChangedEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.pubapp.uif2app.view.util.BillPanelUtils;
import nc.vo.uif2.LoginContext;

/**
 * <b> ��֯�л��¼� </b>
 * 
 * @author author
 * @version tempProject version
 */
public class AceOrgChangeHandler implements IAppEventHandler<OrgChangedEvent> {

	private BillForm billForm;

	@Override
	public void handleAppEvent(OrgChangedEvent e) {
		if (this.billForm.isEditable()) {
			// �ڱ༭״̬�£�����֯�л�ʱ����ս������ݣ��Զ��������У��������к�
			this.billForm.addNew();
		}
		LoginContext context = this.billForm.getModel().getContext();
		// ���в��չ���
		BillPanelUtils.setOrgForAllRef(this.billForm.getBillCardPanel(),
				context);
		// ��Ƭ���澫�ȴ���
		try {
			ScaleUtil scaleUtil = new ScaleUtil("pk_order_b");
			scaleUtil.orgChgScale((ShowUpableBillForm)getBillForm());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1= new Exception("���������ȴ���:�����׼�����ֶ�ȱ�٣���Ӱ��ʹ��");
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
