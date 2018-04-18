package nc.bs.yuyuan.order.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.pf.BillStatusEnum;

/**
 * 标准单据送审的BP
 */
public class AceYy_orderSendApproveBP {
	/**
	 * 送审动作
	 * 
	 * @param vos
	 *            单据VO数组
	 * @param script
	 *            单据动作脚本对象
	 * @return 送审后的单据VO数组
	 */

	public AggYyOrderVO[] sendApprove(AggYyOrderVO[] clientBills,
			AggYyOrderVO[] originBills) {
		for (AggYyOrderVO clientFullVO : clientBills) {
			clientFullVO.getParentVO().setAttributeValue("${vmObject.billstatus}",
					BillStatusEnum.COMMIT.value());
			clientFullVO.getParentVO().setStatus(VOStatus.UPDATED);
		}
		// 数据持久化
		AggYyOrderVO[] returnVos = new BillUpdate<AggYyOrderVO>().update(
				clientBills, originBills);
		return returnVos;
	}
}
