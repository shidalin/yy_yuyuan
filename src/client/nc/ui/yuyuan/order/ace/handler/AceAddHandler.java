package nc.ui.yuyuan.order.ace.handler;

import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.billform.AddEvent;
import nc.vo.pub.pf.BillStatusEnum;
import nc.vo.pubapp.AppContext;
import nc.ui.pub.bill.BillCardPanel;

public class AceAddHandler implements IAppEventHandler<AddEvent> {

	@Override
	public void handleAppEvent(AddEvent e) {
		String pk_group = e.getContext().getPk_group();
		String pk_org = e.getContext().getPk_org();
		BillCardPanel panel = e.getBillForm().getBillCardPanel();
		// ��������֯Ĭ��ֵ
		panel.setHeadItem("pk_group", pk_group);
		panel.setHeadItem("pk_org", pk_org);
		// ���õ���״̬������ҵ������Ĭ��ֵ
		panel.setHeadItem("vbillstatus", BillStatusEnum.FREE.value());
		panel.setHeadItem("pk_billtypecode", "YY02");
		panel.setHeadItem("pk_billtypeid", nc.vo.am.common.util.BillTypeUtils.getPKByCode("YY02"));
		panel.setHeadItem("dmakedate", AppContext.getInstance().getBusiDate());
	}
}