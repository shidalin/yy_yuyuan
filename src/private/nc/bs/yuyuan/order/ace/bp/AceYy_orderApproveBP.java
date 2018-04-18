package nc.bs.yuyuan.order.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.pub.VOStatus;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;

/**
 * 标准单据审核的BP
 */
public class AceYy_orderApproveBP {

	/**
	 * 审核动作
	 * 
	 * @param vos
	 * @param script
	 * @return
	 */
	public AggYyOrderVO[] approve(AggYyOrderVO[] clientBills,
			AggYyOrderVO[] originBills) {
		for (AggYyOrderVO clientBill : clientBills) {
			clientBill.getParentVO().setStatus(VOStatus.UPDATED);
		}
		BillUpdate<AggYyOrderVO> update = new BillUpdate<AggYyOrderVO>();
		AggYyOrderVO[] returnVos = update.update(clientBills, originBills);
		return returnVos;
	}

}
