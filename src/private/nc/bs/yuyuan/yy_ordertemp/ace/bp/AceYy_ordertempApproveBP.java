package nc.bs.yuyuan.yy_ordertemp.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.pub.VOStatus;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

/**
 * ��׼������˵�BP
 */
public class AceYy_ordertempApproveBP {

	/**
	 * ��˶���
	 * 
	 * @param vos
	 * @param script
	 * @return
	 */
	public AggYyOrderTempVO[] approve(AggYyOrderTempVO[] clientBills,
			AggYyOrderTempVO[] originBills) {
		for (AggYyOrderTempVO clientBill : clientBills) {
			clientBill.getParentVO().setStatus(VOStatus.UPDATED);
		}
		BillUpdate<AggYyOrderTempVO> update = new BillUpdate<AggYyOrderTempVO>();
		AggYyOrderTempVO[] returnVos = update.update(clientBills, originBills);
		return returnVos;
	}

}
