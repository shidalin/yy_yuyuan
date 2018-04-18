package nc.bs.yuyuan.order.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.pf.BillStatusEnum;

/**
 * ��׼���������BP
 */
public class AceYy_orderSendApproveBP {
	/**
	 * ������
	 * 
	 * @param vos
	 *            ����VO����
	 * @param script
	 *            ���ݶ����ű�����
	 * @return �����ĵ���VO����
	 */

	public AggYyOrderVO[] sendApprove(AggYyOrderVO[] clientBills,
			AggYyOrderVO[] originBills) {
		for (AggYyOrderVO clientFullVO : clientBills) {
			clientFullVO.getParentVO().setAttributeValue("${vmObject.billstatus}",
					BillStatusEnum.COMMIT.value());
			clientFullVO.getParentVO().setStatus(VOStatus.UPDATED);
		}
		// ���ݳ־û�
		AggYyOrderVO[] returnVos = new BillUpdate<AggYyOrderVO>().update(
				clientBills, originBills);
		return returnVos;
	}
}
