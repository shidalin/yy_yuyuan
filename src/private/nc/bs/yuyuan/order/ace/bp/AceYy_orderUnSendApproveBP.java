package nc.bs.yuyuan.order.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.pf.BillStatusEnum;

/**
 * 标准单据收回的BP
 */
public class AceYy_orderUnSendApproveBP {

	public AggYyOrderVO[] unSend(AggYyOrderVO[] clientBills,
			AggYyOrderVO[] originBills) {
		// 把VO持久化到数据库中
		this.setHeadVOStatus(clientBills);
		BillUpdate<AggYyOrderVO> update = new BillUpdate<AggYyOrderVO>();
		AggYyOrderVO[] returnVos = update.update(clientBills, originBills);
		return returnVos;
	}

	private void setHeadVOStatus(AggYyOrderVO[] clientBills) {
		for (AggYyOrderVO clientBill : clientBills) {
			clientBill.getParentVO().setAttributeValue("${vmObject.billstatus}",
					BillStatusEnum.FREE.value());
			clientBill.getParentVO().setStatus(VOStatus.UPDATED);
		}
	}
}
