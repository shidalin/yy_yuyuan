package nc.bs.yuyuan.yy_ordertemp.ace.bp;

import nc.bs.yuyuan.order.plugin.bpplugin.Yy_orderPluginPoint;
import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.impl.pubapp.pattern.data.bill.template.UpdateBPTemplate;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.vo.pub.VOStatus;
import nc.vo.yuyuan.yy_ordertemp.AggYyOrderTempVO;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;

/**
 * 标准单据弃审的BP
 */
public class AceYy_ordertempUnApproveBP {

	public AggYyOrderTempVO[] unApprove(AggYyOrderTempVO[] clientBills,
			AggYyOrderTempVO[] originBills) {
		for (AggYyOrderTempVO clientBill : clientBills) {
			clientBill.getParentVO().setStatus(VOStatus.UPDATED);
		}
		// this.addBeforeRule(bp.getAroundProcesser());
		// BillUpdate<AggYyOrderTempVO> update = new
		// BillUpdate<AggYyOrderTempVO>();
		// AggYyOrderTempVO[] returnVos = update.update(clientBills,
		// originBills);
		// return returnVos;
		// 调用修改模板
		UpdateBPTemplate<AggYyOrderTempVO> bp = new UpdateBPTemplate<AggYyOrderTempVO>(
				Yy_orderPluginPoint.UPDATE);
		// 执行前规则
		this.addBeforeRule(bp.getAroundProcesser());
		// 执行后规则
		// this.addAfterRule(bp.getAroundProcesser());
		return bp.update(clientBills, originBills);
	}

	private void addBeforeRule(
			CompareAroundProcesser<AggYyOrderTempVO> processer) {
		// TODO 新增前规则
		IRule<AggYyOrderTempVO> rule = null;
		//rule = new nc.bs.yuyuan.yy_ordertemp.ace.rule.CheckYY02Rule();
		processer.addBeforeRule(rule);
	}
}
