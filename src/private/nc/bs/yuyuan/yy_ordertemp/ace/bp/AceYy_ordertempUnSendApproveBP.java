package nc.bs.yuyuan.yy_ordertemp.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.pub.VOStatus;
import nc.vo.pub.pf.BillStatusEnum;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

/**
 * ��׼�����ջص�BP
 */
public class AceYy_ordertempUnSendApproveBP {

	public AggYyOrderTempVO[] unSend(AggYyOrderTempVO[] clientBills,
			AggYyOrderTempVO[] originBills) {
		// ��VO�־û������ݿ���
		this.setHeadVOStatus(clientBills);
		BillUpdate<AggYyOrderTempVO> update = new BillUpdate<AggYyOrderTempVO>();
		AggYyOrderTempVO[] returnVos = update.update(clientBills, originBills);
		return returnVos;
	}

	private void setHeadVOStatus(AggYyOrderTempVO[] clientBills) {
		for (AggYyOrderTempVO clientBill : clientBills) {
			clientBill.getParentVO().setAttributeValue("${vmObject.billstatus}",
					BillStatusEnum.FREE.value());
			clientBill.getParentVO().setStatus(VOStatus.UPDATED);
		}
	}
}
