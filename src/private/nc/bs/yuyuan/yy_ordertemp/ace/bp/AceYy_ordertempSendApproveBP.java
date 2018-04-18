package nc.bs.yuyuan.yy_ordertemp.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.pub.VOStatus;
import nc.vo.pub.pf.BillStatusEnum;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

/**
 * ��׼���������BP
 */
public class AceYy_ordertempSendApproveBP {
	/**
	 * ������
	 * 
	 * @param vos
	 *            ����VO����
	 * @param script
	 *            ���ݶ����ű�����
	 * @return �����ĵ���VO����
	 */

	public AggYyOrderTempVO[] sendApprove(AggYyOrderTempVO[] clientBills,
			AggYyOrderTempVO[] originBills) {
		for (AggYyOrderTempVO clientFullVO : clientBills) {
			clientFullVO.getParentVO().setAttributeValue("${vmObject.billstatus}",
					BillStatusEnum.COMMIT.value());
			clientFullVO.getParentVO().setStatus(VOStatus.UPDATED);
		}
		// ���ݳ־û�
		AggYyOrderTempVO[] returnVos = new BillUpdate<AggYyOrderTempVO>().update(
				clientBills, originBills);
		return returnVos;
	}
}
