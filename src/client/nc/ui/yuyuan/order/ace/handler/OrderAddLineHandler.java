package nc.ui.yuyuan.order.ace.handler;

import nc.bs.framework.common.NCLocator;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.BodyRowEditType;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pubapp.AppContext;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.ui.pubapp.uif2app.model.BillManageModel;

/**
 * 订货单表体增行事件 1.需求日期：业务日期下一天
 * 2.中转仓取值:当前采购组织对应的库存组织下仓库(中转标志,仓库的自定义项1，当自定义项1的值为Y时，就是中转仓)
 * 
 * @author shidl
 * 
 */
public class OrderAddLineHandler implements
		IAppEventHandler<CardBodyAfterRowEditEvent> {

	public void handleAppEvent(CardBodyAfterRowEditEvent e) {
		if (e.getRowEditType().equals(BodyRowEditType.ADDLINE)) {
			this.processAddLine(e);
		}
	}

	private void processAddLine(CardBodyAfterRowEditEvent e) {
		// 1.业务日期取当前日期的下一天
		UFDate date = AppContext.getInstance().getBusiDate().getDateAfter(1);
		// 默认增行为最新一行
		e.getBillCardPanel().setBodyValueAt(date,
				e.getRows()[e.getRows().length - 1], "dneeddate");
		e.getBillCardPanel().setBodyValueAt(
				AppContext.getInstance().getPkGroup(),
				e.getRows()[e.getRows().length - 1], "pk_group");
		e.getBillCardPanel().setBodyValueAt(e.getContext().getPk_org(),
				e.getRows()[e.getRows().length - 1], "pk_org");
		// 2.中转仓
		try {
			String tranStorDoc = NCLocator
					.getInstance()
					.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
					.getTranStorDoc(
							e.getBillCardPanel().getHeadItem("pk_org")
									.getValueObject().toString());
			e.getBillCardPanel().setBodyValueAt(tranStorDoc,
					e.getRows()[e.getRows().length - 1], "pk_transtordoc");
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			ExceptionUtils
					.wrappBusinessException("获取当前采购组织下的中转仓失败,请维护对应的中转仓数据");
		}
	}

}
