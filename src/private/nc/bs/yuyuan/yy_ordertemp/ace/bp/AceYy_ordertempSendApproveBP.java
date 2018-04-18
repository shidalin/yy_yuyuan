package nc.bs.yuyuan.yy_ordertemp.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.pub.VOStatus;
import nc.vo.pub.pf.BillStatusEnum;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;

/**
 * 标准单据送审的BP
 */
public class AceYy_ordertempSendApproveBP {
	/**
	 * 送审动作
	 * 
	 * @param vos
	 *            单据VO数组
	 * @param script
	 *            单据动作脚本对象
	 * @return 送审后的单据VO数组
	 */

	public AggYyOrderTempVO[] sendApprove(AggYyOrderTempVO[] clientBills,
			AggYyOrderTempVO[] originBills) {
		for (AggYyOrderTempVO clientFullVO : clientBills) {
			clientFullVO.getParentVO().setAttributeValue("${vmObject.billstatus}",
					BillStatusEnum.COMMIT.value());
			clientFullVO.getParentVO().setStatus(VOStatus.UPDATED);
		}
		// 数据持久化
		AggYyOrderTempVO[] returnVos = new BillUpdate<AggYyOrderTempVO>().update(
				clientBills, originBills);
		return returnVos;
	}
}
