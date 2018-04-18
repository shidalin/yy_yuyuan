package nc.ui.yuyuan.order.action;

import java.awt.event.ActionEvent;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.ui.pubapp.uif2app.actions.pflow.ScriptPFlowAction;
import nc.vo.pub.pf.BillStatusEnum;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

/**
 * ������-���ɲɹ�������ť
 * 
 * @author shidl
 * 
 */
public class ToPoOrderAction extends ScriptPFlowAction {

	public ToPoOrderAction() {
		this.setBtnName("���ɲɹ�����");
		this.setCode("PUSHPO");
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		checkData();
		super.doAction(e);

	}

	/**
	 * ��ťȨ�ޣ�������������
	 */
	@Override
	protected boolean isActionEnable() {
		AggYyOrderVO agg = (AggYyOrderVO) this.getModel().getSelectedData();
		if (agg != null
				&& BillStatusEnum.APPROVED.value().equals(
						agg.getParentVO().getVbillstatus())) {
			return true;
		}
		return false;
	}

	/**
	 * ����У��
	 * 
	 * @throws Exception
	 */
	private void checkData() throws Exception {
		AggYyOrderVO agg = (AggYyOrderVO) this.getModel().getSelectedData();
		List<nc.vo.pu.m21.entity.OrderHeaderVO> list = NCLocator.getInstance()
				.lookup(nc.itf.yuyuan.IYy_orderMaintain.class)
				.queryPOOrder(agg);
		if (list != null && list.size() > 0) {
			StringBuffer buffer = new StringBuffer("���βɹ��������ݺ�Ϊ:");
			for (nc.vo.pu.m21.entity.OrderHeaderVO order : list) {
				buffer.append(order.getVbillcode() + ",");
			}
			String codemessage = buffer.toString();
			codemessage = codemessage.substring(0, codemessage.length() - 1);
			ExceptionUtils.wrappBusinessException("��������ʧ��!�õ����Ѵ������βɹ���������;\n"
					+ codemessage);
		}
	}

}
