package nc.ui.yuyuan.yy_ordertemp.ace.handler;

import java.util.Arrays;
import java.util.List;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.exchange.CalcExchgCardPanelSetter;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardHeadTailBeforeEditEvent;

/**
 * ��ͷ����༭ǰ�¼������Ӱ���֯����
 * 
 * @author shidl
 * 
 */
public class AceHeadTailBeforeEditHandler implements
		IAppEventHandler<CardHeadTailBeforeEditEvent> {

	@Override
	public void handleAppEvent(CardHeadTailBeforeEditEvent e) {
		String key = e.getKey();
		BillCardPanel cardPanel = e.getBillCardPanel();
		String pk_org = cardPanel.getHeadItem("pk_org").getValue();
		// String [] items = new String [] {"pk_psndoc","pk_deptid"};
		List<String> list = Arrays.asList("cmaterialvid");
		// ����ǰ����֯����
		if (list.contains(key)) {
			UIRefPane ref = (UIRefPane) cardPanel.getHeadItem(key)
					.getComponent();
			ref.setPk_org(pk_org);
		}
		// boolean isedit = CalcExchgCardPanelSetter.getEditable(e.getKey());
		e.setReturnValue(Boolean.valueOf(true));
	}
}
