package nc.ui.yuyuan.yy_ordertemp.ace.handler;

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
		// 设置主组织默认值
		panel.setHeadItem("pk_group", pk_group);
		panel.setHeadItem("pk_org", pk_org);
		// 设置单据状态、单据业务日期默认值
		panel.setHeadItem("vbillstatus", BillStatusEnum.FREE.value());
		// 生效状态默认为生效
		panel.setHeadItem("fproduceflag", "Y");
		panel.setHeadItem("pk_billtypecode", "YY01");
		panel.setHeadItem("pk_billtypeid",
				nc.vo.am.common.util.BillTypeUtils.getPKByCode("YY01"));
		panel.setHeadItem("dmakedate", AppContext.getInstance().getBusiDate());
		panel.setHeadItem("billmaker", AppContext.getInstance().getPkUser());
	}
}
