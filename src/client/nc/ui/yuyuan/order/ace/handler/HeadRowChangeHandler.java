package nc.ui.yuyuan.order.ace.handler;

import nc.ui.ct.util.ScaleUtil;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.list.ListHeadRowChangedEvent;

/**
 * 订货单-列表-表头行切换事件-精度处理
 * 
 * @author shidl
 * 
 */
public class HeadRowChangeHandler implements
		IAppEventHandler<ListHeadRowChangedEvent> {
	private String tableCode = null;

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	@Override
	public void handleAppEvent(ListHeadRowChangedEvent e) {
		int oldrow = e.getOldRow();
		int row = e.getRow();
		BillListPanel blp = (BillListPanel) e.getBillListPanel();
		Object oldOrg = blp.getHeadBillModel().getValueAt(oldrow, "pk_org_ID");
		Object newOrg = blp.getHeadBillModel().getValueAt(row, "pk_org_ID");
		if (newOrg.equals(oldOrg))
			return;
		new ScaleUtil(tableCode)
				.setListScale(blp, e.getContext().getPk_group());

	}

}
