package nc.bs.yuyuan.order.ace.bp;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.impl.pubapp.pattern.data.bill.template.UpdateBPTemplate;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.vo.yuyuan.yy_order.AggYyOrderVO;
import nc.vo.pub.VOStatus;

/**
 * ��׼���������BP
 */
public class AceYy_orderUnApproveBP {

	public AggYyOrderVO[] unApprove(AggYyOrderVO[] clientBills,
			AggYyOrderVO[] originBills) {
		for (AggYyOrderVO clientBill : clientBills) {
			clientBill.getParentVO().setStatus(VOStatus.UPDATED);
		}
		// BillUpdate<AggYyOrderVO> update = new BillUpdate<AggYyOrderVO>();
		// AggYyOrderVO[] returnVos = update.update(clientBills, originBills);
		// �����޸�ģ��
		UpdateBPTemplate<AggYyOrderVO> bp = new UpdateBPTemplate<AggYyOrderVO>(
				Yy_orderPluginPoint.UPDATE);
		// ִ��ǰ����
		this.addBeforeRule(bp.getAroundProcesser());
		// ִ�к����
		this.addAfterRule(bp.getAroundProcesser());
		return bp.update(clientBills, originBills);
		// return returnVos;
	}

	private void addAfterRule(CompareAroundProcesser<AggYyOrderVO> processer) {
		IRule<AggYyOrderVO> rule = null;
		rule = new nc.bs.yuyuan.order.ace.rule.checkPuOrderRule();
		processer.addBeforeRule(rule);
	}

	private void addBeforeRule(
			CompareAroundProcesser<AggYyOrderVO> aroundProcesser) {
		// TODO Auto-generated method stub

	}
}
