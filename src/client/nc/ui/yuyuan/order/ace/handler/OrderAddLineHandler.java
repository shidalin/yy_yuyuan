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
 * ���������������¼� 1.�������ڣ�ҵ��������һ��
 * 2.��ת��ȡֵ:��ǰ�ɹ���֯��Ӧ�Ŀ����֯�²ֿ�(��ת��־,�ֿ���Զ�����1�����Զ�����1��ֵΪYʱ��������ת��)
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
		// 1.ҵ������ȡ��ǰ���ڵ���һ��
		UFDate date = AppContext.getInstance().getBusiDate().getDateAfter(1);
		// Ĭ������Ϊ����һ��
		e.getBillCardPanel().setBodyValueAt(date,
				e.getRows()[e.getRows().length - 1], "dneeddate");
		e.getBillCardPanel().setBodyValueAt(
				AppContext.getInstance().getPkGroup(),
				e.getRows()[e.getRows().length - 1], "pk_group");
		e.getBillCardPanel().setBodyValueAt(e.getContext().getPk_org(),
				e.getRows()[e.getRows().length - 1], "pk_org");
		// 2.��ת��
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
					.wrappBusinessException("��ȡ��ǰ�ɹ���֯�µ���ת��ʧ��,��ά����Ӧ����ת������");
		}
	}

}
